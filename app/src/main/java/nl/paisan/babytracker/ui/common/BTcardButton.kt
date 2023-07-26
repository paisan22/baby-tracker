package nl.paisan.babytracker.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BTcardButton(
    onClick: () -> Unit,
    label: String,
    imageVector: ImageVector? = null,
    painter: Painter? = null
) {
    Card(
        onClick = { onClick() },
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = label, fontSize = 28.sp)
            Spacer(modifier = Modifier.padding(vertical = 6.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                imageVector?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.size(75.dp)
                    )
                }
                painter?.let {
                    Icon(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.size(75.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BTcardButton_Preview() {
    BTcardButton(onClick = {}, label = "Nutrition", imageVector = Icons.Outlined.Restaurant)
}