package nl.paisan.babytracker.ui.screen.overviewActivity.overviews.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import nl.paisan.babytracker.R

@Composable
fun ListItemActions(onDelete: () -> Unit) {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.action_delete)
        )
    }
}