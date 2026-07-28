package nl.paisan.babytracker.ui.screen.addActivity.wizards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.paisan.babytracker.R
import nl.paisan.babytracker.data.entities.DiaperLog
import nl.paisan.babytracker.domain.enums.DiaperType
import nl.paisan.babytracker.domain.services.localDateMillis
import nl.paisan.babytracker.domain.services.localDateTimeMillis
import nl.paisan.babytracker.domain.services.utcMillisToYearMonthDay
import nl.paisan.babytracker.ui.common.BTcardButton
import nl.paisan.babytracker.ui.common.BTdatePicker
import nl.paisan.babytracker.ui.common.BTtemporalData
import nl.paisan.babytracker.ui.common.BTtextField
import nl.paisan.babytracker.ui.common.BTtimePicker
import nl.paisan.babytracker.ui.common.BTwizardDialog
import nl.paisan.babytracker.ui.common.BTcardColumn
import java.util.Calendar

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
                BTcardColumn {
                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = DiaperWizardSteps.Confirm,
                                type = DiaperType.Pee
                            )
                        },
                        label = stringResource(R.string.noun_pee),
                        imageVector = Icons.Outlined.WaterDrop
                    )

                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = DiaperWizardSteps.Confirm,
                                type = DiaperType.Poo
                            )
                        },
                        label = stringResource(R.string.noun_poo),
                        painter = painterResource(id = R.drawable.poop)
                    )

                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = DiaperWizardSteps.Confirm,
                                type = DiaperType.PooAndPee
                            )
                        },
                        label = stringResource(R.string.sentence_pee_and_poo),
                        imageVector = Icons.Outlined.WaterDrop,
                        painter = painterResource(
                            id = R.drawable.poop
                        )
                    )

                    BTtextField(
                        onChange = { uiState = uiState.copy(note = it) },
                        placeholder = stringResource(R.string.label_example_the_poop_is_very_soft),
                        label = stringResource(R.string.noun_note),
                    )

                    BTdatePicker(
                        dateToPickName = stringResource(R.string.noun_date),
                        currentDate = localDateMillis(year = uiState.year, month = uiState.month, day = uiState.day),
                        onDateSelection = { utcMillis ->
                            val (year, month, day) = utcMillisToYearMonthDay(utcMillis)
                            uiState = uiState.copy(year = year, month = month, day = day)
                        }
                    )

                    BTtimePicker(
                        timeToPickName = stringResource(R.string.noun_time),
                        currentHour = uiState.hour,
                        currentMinute = uiState.minute,
                        onTimeSelection = { hour, minute ->
                            uiState = uiState.copy(hour = hour, minute = minute)
                        }
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
                BTcardColumn {
                    BTcardButton(
                        onClick = {
                            val start = localDateTimeMillis(
                                year = uiState.year,
                                month = uiState.month,
                                day = uiState.day,
                                hour = uiState.hour,
                                minute = uiState.minute
                            )

                            addDiaperLog(
                                start,
                                uiState.type!!,
                                uiState.note
                            )
                        },
                        label = stringResource(R.string.yes),
                        imageVector = Icons.Outlined.CheckCircle
                    )
                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = DiaperWizardSteps.Save
                            )
                        },
                        label = stringResource(R.string.no),
                        imageVector = Icons.Outlined.Cancel
                    )
                }
            }
        }
    }
}

private data class DiaperWizardUiState(
    val currentStep: DiaperWizardSteps = DiaperWizardSteps.Save,
    val year: Int = Calendar.getInstance().get(Calendar.YEAR),
    val month: Int = Calendar.getInstance().get(Calendar.MONTH),
    val day: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    val hour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    val minute: Int = Calendar.getInstance().get(Calendar.MINUTE),
    val type: DiaperType? = null,
    val note: String? = null,
    val confirmedStop: Boolean = false,
)

private enum class DiaperWizardSteps(val title: String) {
    Save("Choose diaper result"),
    Confirm("Confirm"),
}