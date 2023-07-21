package nl.paisan.babytracker.ui.screen.activity.wizards

import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import nl.paisan.babytracker.R
import nl.paisan.babytracker.data.entities.RestLog
import nl.paisan.babytracker.domain.services.getTime
import nl.paisan.babytracker.ui.common.BTtemporalData
import nl.paisan.babytracker.ui.common.BTwizardDialog
import nl.paisan.babytracker.ui.screen.activity.wizards.shared.WizardStep
import nl.paisan.babytracker.ui.screen.activity.wizards.shared.WizardStepCard

@Composable
fun RestWizard(
    onClose: () -> Unit,
    addRestLog: (start: Long, end: Long) -> Unit,
    lastLog: RestLog? = null
) {
    var uiState by remember { mutableStateOf(RestWizardUiState()) }
    val context = LocalContext.current

    BTwizardDialog(onClose = { onClose() }) {
        when(uiState.currentStep) {
            RestWizardSteps.Start -> {
                val notification =
                    stringResource(R.string.sentence_rest_activity_started)

                WizardStep(cards = listOf<@Composable () -> Unit> (
                    {
                        WizardStepCard(onClick = {
                            uiState = uiState.copy(
                                currentStep = RestWizardSteps.Stop,
                                start = System.currentTimeMillis()
                            )

                            Toast
                                .makeText(
                                    context,
                                    notification,
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }, text = "Start")
                    }, {
                        if(lastLog != null) {
                            BTtemporalData(start = lastLog.start, end = lastLog.end)
                        } else {
                            Text(text = "You haven't registered a rest activity before.")
                        }
                    }
                ))
            }
            RestWizardSteps.Stop -> {
                WizardStep(cards = listOf<@Composable () -> Unit> (
                    {
                        WizardStepCard(onClick = {
                            uiState = uiState.copy(
                                currentStep = RestWizardSteps.Confirm,
                            )
                        }, text = stringResource(R.string.action_stop))
                    }, {
                        val text = stringResource(R.string.action_start_time)
                        Text(text = "$text: ${context.getTime(uiState.start?: 0L)}")
                    }))
            }
            RestWizardSteps.Confirm -> {
                WizardStep(
                    supportiveText = stringResource(R.string.confirm_are_you_sure),
                    cards = listOf<@Composable () -> Unit> (
                        {
                            WizardStepCard(onClick = {
                                uiState = uiState.copy(
                                    end = System.currentTimeMillis()
                                )

                                addRestLog(uiState.start!!,uiState.end!!)

                            }, text = stringResource(R.string.yes))
                        }, {
                            WizardStepCard(onClick = {
                                uiState = uiState.copy(
                                    currentStep = RestWizardSteps.Stop,
                                )
                            }, text = stringResource(R.string.no))
                        }
                    ))
            }
        }
    }
}

private data class RestWizardUiState(
    val currentStep: RestWizardSteps = RestWizardSteps.Start,
    val start: Long? = null,
    val end: Long? = null,
    val confirmedStop: Boolean = false,
)

private enum class RestWizardSteps {
    Start,
    Stop,
    Confirm,
}
