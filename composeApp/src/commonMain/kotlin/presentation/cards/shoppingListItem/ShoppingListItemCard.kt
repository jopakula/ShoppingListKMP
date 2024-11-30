package presentation.cards.shoppingListItem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import domain.models.ShoppingListItemModel

@Composable
fun ShoppingListItemCard(
    item: ShoppingListItemModel,
    onIconClick: () -> Unit = {},
    onCheckBoxClick: (Boolean) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isCrossed,
                onCheckedChange = onCheckBoxClick
            )
            Text(text = item.id.toString())
            Text(text = item.isCrossed.toString())
            Text(
                text = item.name,
                textDecoration = if (item.isCrossed) TextDecoration.LineThrough else TextDecoration.None)
            Text(text = item.created)
            Button(onClick = onIconClick){
                Text(text = "Удалить")
            }
        }
    }
}
