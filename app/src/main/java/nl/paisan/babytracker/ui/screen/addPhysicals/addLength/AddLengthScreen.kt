package nl.paisan.babytracker.ui.screen.addPhysicals.addLength

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import nl.paisan.babytracker.R
import nl.paisan.babytracker.domain.enums.PhysicalType
import nl.paisan.babytracker.ui.common.BTbutton
import nl.paisan.babytracker.ui.common.BTconfirmDialog
import nl.paisan.babytracker.ui.common.BTdecimalNumberTextField
import nl.paisan.babytracker.ui.navigation.Destinations

@Composable
fun AddLengthScreen(
    navHostController: NavHostController,
    vm: AddLengthViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = stringResource(R.string.sentence_enter_length_in_centimeters))
        BTdecimalNumberTextField(
            initialValue = null,
            onChange = { vm.onValueChanged(it) },
            label = "Centimeter",
            placeholder = "Example: 51"
        )

        BTbutton(name = stringResource(id = R.string.action_add), onClick = { vm.showConfirm(show = true) })

        if(vm.uiState.showConfirmDialog) {
            BTconfirmDialog(
                onYes = {
                    vm.showConfirm(show = false)
                    vm.onSave()
                    Toast.makeText(
                        context,
                        context.getString(R.string.sentence_length_measurement_added),
                        Toast.LENGTH_SHORT
                    ).show()

                    navHostController.navigate(
                        route = Destinations.OVERVIEW_PHYSICAL_ROUTE
                            .replace("{physicalType}", PhysicalType.Length.name)
                    )
                },
                onNo = { vm.showConfirm(show = false) }
            )
        }
    }
}