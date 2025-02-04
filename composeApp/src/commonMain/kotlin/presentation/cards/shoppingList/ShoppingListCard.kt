package presentation.cards.shoppingList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.models.ShoppingListModel

@Composable
fun ShoppingListCard(
    shoppingListModel: ShoppingListModel,
    onClick: () -> Unit = {},
    onIconClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                text = "Список: ${shoppingListModel.name}",
            )
            Text(
                text = "Дата создания: ${shoppingListModel.created}",
            )
            Button(onClick = onIconClick){
                Text(text = "Удалить")
            }
        }
    }
}
