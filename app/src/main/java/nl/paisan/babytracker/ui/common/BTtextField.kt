package nl.paisan.babytracker.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun BTtextField(
    initValue: String = "",
    onChange: (newValue: String) -> Unit,
    label: String = "",
    placeholder: String = ""
) {
    val textValue = remember { mutableStateOf(TextFieldValue(initValue)) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = textValue.value,
        onValueChange = { newValue ->
            if(newValue.text != textValue.value.text) {
                onChange(newValue.text)
            }

            textValue.value = newValue
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}