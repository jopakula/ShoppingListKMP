package presentation.screens.shoppingList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import presentation.cards.ShoppingListItemCard
import presentation.screenViewModels.ShoppingListScreenViewModel
import presentation.sheets.item.ItemBottomSheet

class ShoppingListScreen(private val listId: Int, private val listName: String) : Screen {

    @Composable
    override fun Content() {
        val viewModel: ShoppingListScreenViewModel = koinViewModel()
        val navigator = LocalNavigator.current

        val shoppingListState by viewModel.shoppingListState.collectAsStateWithLifecycle()
        var showBottomSheet by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(listId) {
            viewModel.loadShoppingList(listId)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Shopping List: $listName")
            when (shoppingListState) {
                is RequestState.Loading -> {
                    CircularProgressIndicator()
                }
                is RequestState.Success -> {
                    val shoppingList = shoppingListState.getSuccessData()

                    if (shoppingList.itemList.isEmpty()) {
                        Text(text = "Список пуст", color = Color.Gray)
                    } else {
                        LazyColumn {
                            items(shoppingList.itemList) { item ->
                                ShoppingListItemCard(item = item)
                            }
                        }
                    }
                }
                is RequestState.Error -> {
                    Text("Error: ${shoppingListState.getErrorMessage()}")
                    Button(onClick = { viewModel.loadShoppingList(listId) }) {
                        Text("Retry")
                    }
                }
                else -> Unit
            }

            Button(onClick = { showBottomSheet = true }) {
                Text(text = "Добавить товар")
            }

            if (showBottomSheet) {
                ItemBottomSheet(
                    onClick = { name, quantity ->
                        viewModel.addItemToShoppingList(listId, name, quantity)
                        showBottomSheet = false
                    },
                    onDismiss = { showBottomSheet = false }
                )
            }

            Button(onClick = { navigator?.pop() }) {
                Text(text = "Назад")
            }
        }
    }
}
