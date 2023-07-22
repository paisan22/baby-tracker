package nl.paisan.babytracker.ui.screen.addActivity.wizards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.paisan.babytracker.R
import nl.paisan.babytracker.data.entities.DiaperLog
import nl.paisan.babytracker.domain.enums.DiaperType
import nl.paisan.babytracker.ui.common.BTtemporalData
import nl.paisan.babytracker.ui.common.BTtextField
import nl.paisan.babytracker.ui.common.BTwizardDialog
import nl.paisan.babytracker.ui.screen.addActivity.wizards.shared.WizardStep
import nl.paisan.babytracker.ui.screen.addActivity.wizards.shared.WizardStepCard

@Composable
fun DiaperWizard(
    onClose: () -> Unit,
    addDiaperLog: (start: Long, type: DiaperType, note: String?) -> Unit,
    lastLog: DiaperLog? = null
) {
    var uiState by remember { mutableStateOf(DiaperWizardUiState()) }

    BTwizardDialog(onClose = { onClose() }, title = uiState.currentStep.title) {
        when(uiState.currentStep) {
            DiaperWizardSteps.Save -> {
                WizardStep {
                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = DiaperWizardSteps.Confirm,
                            start = System.currentTimeMillis(),
                            type = DiaperType.Pee
                        )
                    }, text = stringResource(R.string.noun_pee))

                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = DiaperWizardSteps.Confirm,
                            start = System.currentTimeMillis(),
                            type = DiaperType.Poo
                        )
                    }, text = stringResource(R.string.noun_poo))

                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = DiaperWizardSteps.Confirm,
                            start = System.currentTimeMillis(),
                            type = DiaperType.PooAndPee
                        )
                    }, text = stringResource(R.string.sentence_pee_and_poo))

                    BTtextField(
                        onChange = { uiState = uiState.copy(note = it) },
                        placeholder = stringResource(R.string.label_example_the_poop_is_very_soft),
                        label = stringResource(R.string.noun_note),
                    )
                    
                    lastLog?.let { log ->
                        BTtemporalData(start = log.start) {
                            val prefix = stringResource(R.string.noun_diaper_content)
                            Text(text = "$prefix: ${log.type.contentName}")
                            log.note?.let { note ->
                                Text(text = "${stringResource(R.string.noun_note)}:")
                                Text(text = log.note)
                            }
                        }
                    } ?: Column(Modifier.padding(8.dp)) {
                        Text(stringResource(R.string.sentence_no_diaper_history_yet))
                    }
                }
            }
            DiaperWizardSteps.Confirm -> {
                WizardStep {
                    WizardStepCard(
                        onClick = { addDiaperLog(uiState.start!!, uiState.type!!, uiState.note) },
                        text = stringResource(R.string.yes)
                    )
                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = DiaperWizardSteps.Save
                        )
                    }, text = stringResource(R.string.no))
                }
            }
        }
    }
}

private data class DiaperWizardUiState(
    val currentStep: DiaperWizardSteps = DiaperWizardSteps.Save,
    val start: Long? = null,
    val type: DiaperType? = null,
    val note: String? = null,
    val confirmedStop: Boolean = false,
)

private enum class DiaperWizardSteps(val title: String) {
    Save("Choose diaper result"),
    Confirm("Confirm"),
}