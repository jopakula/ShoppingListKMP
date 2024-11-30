package presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
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

class MainScreen(private val token: String) : Screen {
    @Composable
    override fun Content() {

        val viewModel: MainScreenViewModel = koinViewModel()

        val navigator = LocalNavigator.current
        val shoppingListsState by viewModel.shoppingListsState.collectAsStateWithLifecycle()
        var showBottomSheet by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            viewModel.loadShoppingLists(token = token)
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = "Пользователь: $token"
            )

            when (shoppingListsState) {
                is RequestState.Loading -> CircularProgressIndicator()
                is RequestState.Success -> {
                    if (shoppingListsState.getSuccessData().isEmpty()) {
                        Text(
                            modifier = Modifier
                                .weight(1F)
                                .padding(16.dp),
                            text = "Нет списков с покупками",
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .fillMaxWidth()
                                .weight(1F),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                                        viewModel.removeShoppingList(
                                            token = token,
                                            listId = list.id
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                is RequestState.Error -> Unit
                else -> Unit
            }

            Column(
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )

                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    onClick = { showBottomSheet = true },
                ) {
                    Text(text = "Создать список покупок")
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
}

//3MFKKF