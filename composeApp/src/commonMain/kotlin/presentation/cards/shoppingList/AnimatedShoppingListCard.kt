package presentation.cards.shoppingList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import domain.models.ShoppingListModel

@Composable
fun AnimatedShoppingListCard(
    shoppingListModel: ShoppingListModel,
    onClick: () -> Unit = {},
    onIconClick: () -> Unit = {}
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(shoppingListModel.id) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                scaleIn(initialScale = 0.5f, animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                scaleOut(targetScale = 0.5f, animationSpec = tween(durationMillis = 300))
    ) {
        ShoppingListCard(
            shoppingListModel = shoppingListModel,
            onClick = onClick,
            onIconClick = onIconClick
        )
    }

}
