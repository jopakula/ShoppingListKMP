package presentation.screens.shoppingList

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
import presentation.cards.shoppingListItem.AnimatedShoppingListItemCard
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = "Cписок покупок: $listName",
                )

            when (shoppingListState) {

                is RequestState.Loading -> {
                    CircularProgressIndicator()
                }

                is RequestState.Success -> {
                    val shoppingList = shoppingListState.getSuccessData()

                    if (shoppingList.isEmpty()) {
                        Text(
                            modifier = Modifier
                                .weight(1F),
                            text = "Список пуст",
                            color = Color.Gray,
                            )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .fillMaxWidth()
                                .weight(1F),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(shoppingList) { item ->
                                AnimatedShoppingListItemCard(
                                    item = item,
                                    onIconClick = {
                                        viewModel.removeItemFromList(
                                            listId = listId,
                                            itemId = item.id
                                        )
                                    },
                                    onCheckBoxClick = { isChecked ->
                                        if (isChecked != item.isCrossed) {
                                            viewModel.crossOffItem(
                                                itemId = item.id,
                                                listId = listId
                                            )
                                        }
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
                        elevation = 32.dp,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .padding(vertical = 16.dp)
                ,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            )  {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    onClick = { showBottomSheet = true },
                ) {
                    Text(text = "Добавить товар")
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    onClick = { navigator?.pop() },
                ) {
                    Text(text = "Назад")
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
            }
        }
    }
}
