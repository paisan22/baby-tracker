package nl.paisan.babytracker.ui.screen.addActivity.wizards

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.StopCircle
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
import nl.paisan.babytracker.ui.common.BTConfirmText
import nl.paisan.babytracker.ui.common.BTcardButton
import nl.paisan.babytracker.ui.common.BTtemporalData
import nl.paisan.babytracker.ui.common.BTwizardDialog
import nl.paisan.babytracker.ui.common.BTcardColumn

@Composable
fun RestWizard(
    onClose: () -> Unit,
    addRestLog: (start: Long, end: Long) -> Unit,
    lastLog: RestLog? = null
) {
    var uiState by remember { mutableStateOf(RestWizardUiState()) }
    val context = LocalContext.current

    BTwizardDialog(
        onClose = { onClose() },
        title = uiState.currentStep.title
    ) {
        when(uiState.currentStep) {
            RestWizardSteps.Start -> {
                BTcardColumn {
                    val notification =
                        stringResource(R.string.sentence_rest_activity_started)
                    BTcardButton(
                        onClick = {
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
                        },
                        label = stringResource(R.string.action_start),
                        imageVector = Icons.Outlined.PlayCircleOutline
                    )
                    if(lastLog != null) {
                        BTtemporalData(start = lastLog.start, end = lastLog.end)
                    } else {
                        Text(text = stringResource(R.string.sentence_you_haven_t_registered_a_rest_activity_before))
                    }
                }
            }
            RestWizardSteps.Stop -> {
                BTcardColumn {
                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = RestWizardSteps.Confirm,
                            )
                        },
                        label = stringResource(R.string.action_stop),
                        imageVector = Icons.Outlined.StopCircle
                    )
                    val text = stringResource(R.string.action_start_time)
                    Text(text = "$text: ${context.getTime(uiState.start?: 0L)}")
                }
            }
            RestWizardSteps.Confirm -> {
                BTcardColumn {
                    BTConfirmText()
                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                end = System.currentTimeMillis()
                            )

                            addRestLog(uiState.start!!, uiState.end!!)

                        },
                        label = stringResource(R.string.yes),
                        imageVector = Icons.Outlined.CheckCircle
                    )
                    BTcardButton(onClick = {
                        uiState = uiState.copy(
                            currentStep = RestWizardSteps.Stop,
                        )
                    }, label = stringResource(R.string.no), imageVector = Icons.Outlined.Cancel)
                }
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

private enum class RestWizardSteps(val title: String) {
    Start("Start rest activity"),
    Stop("Stop rest activity"),
    Confirm("Confirm"),
}
