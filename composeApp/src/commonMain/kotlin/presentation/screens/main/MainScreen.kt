package presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import presentation.screens.shoppingList.ShoppingListScreen
import presentation.sheets.shoppingList.ShoppingListBottomSheet

class MainScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val lists = listOf(1,2,3,4)
        var showBottomSheet by rememberSaveable { mutableStateOf(false) }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            lists.forEach {
                Button(onClick = {navigator?.push(ShoppingListScreen(listId = it))}){
                    Text(text = "Go to list $it")
                }
            }
            Button(onClick = { showBottomSheet = true}){
                Text(text = "Нижнее меню")
            }

            if (showBottomSheet) {
                ShoppingListBottomSheet(
                    onDismiss = { showBottomSheet = false }
                )
            }
        }
    }
}