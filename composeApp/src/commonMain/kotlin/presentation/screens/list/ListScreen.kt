package presentation.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import presentation.screens.item.ItemScreen

class ListScreen(private val listId: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val items = listOf(1, 2, 3, 4, 5, 6)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "List screen $listId")
            Button(onClick = { navigator?.pop() }) {
                Text(text = "Back")
            }
            items.forEach {
                Button(onClick = { navigator?.push(ItemScreen(itemId = it)) }) {
                    Text(text = "To item screen $it")
                }
            }
        }
    }
}