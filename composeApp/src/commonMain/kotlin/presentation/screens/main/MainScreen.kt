package presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import domain.RequestState
import org.koin.compose.viewmodel.koinViewModel
import presentation.cards.shoppingList.AnimatedShoppingListCard
import presentation.screenViewModels.MainScreenViewModel
import presentation.screens.shoppingList.ShoppingListScreen
import presentation.sheets.shoppingList.ShoppingListBottomSheet

class MainScreen(private val token: String): Screen {
    @Composable
    override fun Content() {

        val viewModel: MainScreenViewModel = koinViewModel()

        val navigator = LocalNavigator.current
        val shoppingListsState by viewModel.shoppingListsState.collectAsStateWithLifecycle()
        val createOperationState by viewModel.createOperationState.collectAsStateWithLifecycle()
        val removeOperationState by viewModel.removeOperationState.collectAsStateWithLifecycle()
        var showBottomSheet by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(Unit){
            viewModel.loadShoppingLists(token = token)
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)) {

            Text(text = token)

            when (createOperationState) {
                is RequestState.Loading -> CircularProgressIndicator()
                is RequestState.Success -> {
                    Text(text = "List created successfully", color = Color.Green)
                }
                is RequestState.Error -> {
                    Text(text = "Error: ${createOperationState.getErrorMessage()}", color = Color.Red)
                }
                else -> Unit
            }

            when (removeOperationState) {
                is RequestState.Loading -> CircularProgressIndicator()
                is RequestState.Success -> {
                    val isRemoved = removeOperationState.getSuccessData()
                    val message = if (isRemoved) "List removed successfully" else "List restored successfully"
                    Text(text = message, color = Color.Green)
                }
                is RequestState.Error -> {
                    Text(text = "Error: ${removeOperationState.getErrorMessage()}", color = Color.Red)
                }
                else -> Unit
            }

            when (shoppingListsState) {
                is RequestState.Loading -> CircularProgressIndicator()
                is RequestState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(shoppingListsState.getSuccessData()) { list ->
                            AnimatedShoppingListCard(
                                shoppingListModel = list,
                                onClick = {
                                    navigator?.push(
                                        ShoppingListScreen(
                                            listId = list.id,
                                            listName = list.name
                                        )
                                    )
                                },
                                onIconClick = {
                                    viewModel.removeShoppingList(token = token, listId = list.id)
                                }
                            )
                        }
                    }
                }
                is RequestState.Error -> {
                    Text(text = "Ошибка загрузки: ${shoppingListsState.getErrorMessage()}")
                }
                else -> Unit
            }


            Box(
                modifier = Modifier
                    .weight(0.1F)
                    .background(Color.Transparent),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row {
                    Button(
                    onClick = { showBottomSheet = true}){
                    Text(text = "Нижнее меню")
                }
                    Button(
                        onClick = { viewModel.loadShoppingLists(token = token)}){
                        Text(text = "Загрузить списки")
                    }
                }
            }

            if (showBottomSheet) {
                ShoppingListBottomSheet(
                    onClick = { name -> viewModel.createShoppingList(token, name) },
                    onDismiss = { showBottomSheet = false }
                )
            }
        }
    }
}
//3MFKKF