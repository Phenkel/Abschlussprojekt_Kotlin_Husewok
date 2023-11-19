package com.example.abschlussprojekt_husewok.ui.theme.composables.cards

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.theme.Orange40
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.ssjetpackcomposeswipeableview.SwipeAbleItemView
import com.example.ssjetpackcomposeswipeableview.SwipeDirection

/**
 * A composable function that represents a swipeable housework list card.
 *
 * @param housework The housework object to display in the card.
 * @param houseworkList The list of housework items.
 * @param onClick The callback function to invoke when the card is clicked.
 */
@OptIn(ExperimentalMaterialApi::class)
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
        leftViewWidth = calcDp(percentage = 0.2f, dimension = CalcSizes.Dimension.Width),
        rightViewWidth = calcDp(percentage = 0.2f, dimension = CalcSizes.Dimension.Width),
        height = calcDp(percentage = 0.15f, dimension = CalcSizes.Dimension.Height),
        cornerRadius = 20.dp,
        leftSpace = (-calcDp(percentage = 0.075f, dimension = CalcSizes.Dimension.Width)),
        rightSpace = (-calcDp(percentage = 0.075f, dimension = CalcSizes.Dimension.Width))
    ) {
        CardWithAnimatedBorder(
            content = { HouseworkListCard(housework = housework) },
            liked = housework.isLiked
        )
    }
}