package nl.paisan.babytracker.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun BTnumberTextField(
    initialValue: Int?,
    onChange: (value: Int) -> Unit,
    label: String,
    placeholder: String,
) {

    var number by remember { mutableStateOf(initialValue?.toString() ?: "") }

    OutlinedTextField(
        value = number,
        onValueChange = { newValue ->
            number = newValue
                .replace(",","")
                .replace(".", "")

            if(!number.isNullOrEmpty()) {
                onChange(number.toInt())
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        singleLine = true,
        placeholder = { Text(text = placeholder) },
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth()
    )
}