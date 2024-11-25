package presentation.screens.main

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
import presentation.screens.list.ListScreen

class MainScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val lists = listOf(1,2,3,4)
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            lists.forEach {
                Button(onClick = {navigator?.push(ListScreen(listId = it))}){
                    Text(text = "Go to list $it")
                }
            }
        }
    }
}