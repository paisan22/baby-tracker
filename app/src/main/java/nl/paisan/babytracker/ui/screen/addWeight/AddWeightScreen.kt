package nl.paisan.babytracker.ui.screen.addWeight

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.paisan.babytracker.ui.common.BTbutton
import nl.paisan.babytracker.ui.common.BTconfirmDialog
import nl.paisan.babytracker.ui.common.BTdecimalNumberTextField

@Composable
fun AddWeightScreen(
    vm: AddWeightMeasurementViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BTdecimalNumberTextField(
            initialValue = null,
            onChange = { vm.onValueChanged(it) },
            label = "Kilogram",
            placeholder = "Example: 3.5"
        )

        BTbutton(name = "Add", onClick = { vm.showConfirm(show = true) })

        if(vm.uiState.showConfirm) {
            BTconfirmDialog(
                onYes = {
                    vm.showConfirm(show = false)
                    vm.onSave()
                    Toast.makeText(
                        context,
                        "Weight measurement added!",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onNo = { vm.showConfirm(show = false) }
            )
        }
    }
}