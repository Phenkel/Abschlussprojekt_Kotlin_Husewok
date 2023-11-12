package com.example.abschlussprojekt_husewok.ui.theme.layout.listScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.theme.Orange40
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.ui.theme.components.cards.CardWithAnimatedBorder
import com.example.abschlussprojekt_husewok.utils.Dimension
import com.example.abschlussprojekt_husewok.utils.calcDp
import com.example.ssjetpackcomposeswipeableview.SwipeAbleItemView
import com.example.ssjetpackcomposeswipeableview.SwipeDirection

/**
 * Composable function to display a swipeable housework list card.
 *
 * @param housework The housework item to display.
 * @param houseworkList The list of housework items.
 * @param onClick The callback function for when the card is clicked.
 */
@Composable
fun SwipeableHouseworkListCard(
    housework: Housework,
    houseworkList: List<Housework>,
    onClick: (Pair<Int, String>) -> Unit
) {
    SwipeAbleItemView(
        leftViewIcons = arrayListOf(
            Triple(
                painterResource(id = R.drawable.ic_edit),
                if (housework.isLiked) Purple80 else Orange80,
                "Edit"
            )
        ),
        rightViewIcons = arrayListOf(
            Triple(
                painterResource(id = R.drawable.ic_edit),
                if (housework.isLiked) Purple80 else Orange80,
                "Edit"
            )
        ),
        onClick = onClick,
        swipeDirection = SwipeDirection.LEFT,
        leftViewBackgroundColor = Orange40,
        rightViewBackgroundColor = if (housework.isLiked) Purple40 else Orange40,
        position = houseworkList.indexOf(housework),
        leftViewWidth = calcDp(percentage = 0.2f, dimension = Dimension.Width),
        rightViewWidth = calcDp(percentage = 0.2f, dimension = Dimension.Width),
        height = calcDp(percentage = 0.15f, dimension = Dimension.Height),
        cornerRadius = 20.dp,
        leftSpace = (-calcDp(percentage = 0.075f, dimension = Dimension.Width)),
        rightSpace = (-calcDp(percentage = 0.075f, dimension = Dimension.Width))
    ) {
        CardWithAnimatedBorder(
            content = { HouseworkListCard(housework = housework) },
            liked = housework.isLiked
        )
    }
}