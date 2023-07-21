package nl.paisan.babytracker.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import nl.paisan.babytracker.R

@Composable
fun BTConfirmText() {
    val supportiveText = stringResource(R.string.confirm_are_you_sure)
    Text(text = supportiveText)
}