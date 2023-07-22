package nl.paisan.babytracker.ui.screen.addActivity.wizards

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.paisan.babytracker.R
import nl.paisan.babytracker.data.entities.NutritionLogWithDetails
import nl.paisan.babytracker.domain.enums.BottleType
import nl.paisan.babytracker.domain.enums.BreastSide
import nl.paisan.babytracker.domain.services.getTime
import nl.paisan.babytracker.ui.common.BTConfirmText
import nl.paisan.babytracker.ui.common.BTbutton
import nl.paisan.babytracker.ui.common.BTnumberTextField
import nl.paisan.babytracker.ui.common.BTtemporalData
import nl.paisan.babytracker.ui.common.BTwizardDialog
import nl.paisan.babytracker.ui.screen.addActivity.wizards.shared.WizardStep
import nl.paisan.babytracker.ui.screen.addActivity.wizards.shared.WizardStepCard

@Composable
fun NutritionWizard(
    onClose: () -> Unit,
    addBreastLog: (start: Long, end: Long, breastSide: BreastSide) -> Unit,
    addBottleLog: (start: Long, end: Long, bottleType: BottleType, milliliters: Int) -> Unit,
    lastBreastLog: NutritionLogWithDetails?,
    lastBottleLog: NutritionLogWithDetails?,
) {
    var uiState by remember { mutableStateOf(NutritionWizardUiState()) }
    val context = LocalContext.current

    BTwizardDialog(
        onClose = { onClose() },
        title = uiState.currentStep.title
    ) {
        when(uiState.currentStep) {
            NutritionWizardSteps.BreastOrBottle -> {
                WizardStep {
                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = NutritionWizardSteps.LeftOrRightBreast
                        )

                    }, text = stringResource(R.string.noun_breast))


                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = NutritionWizardSteps.BottleType
                        )
                    }, text = stringResource(R.string.noun_bottle))
                }
            }
            NutritionWizardSteps.LeftOrRightBreast -> {
                    WizardStep {
                        WizardStepCard(onClick = {
                            uiState = uiState.copy(
                                currentStep = NutritionWizardSteps.BreastFeedingStart,
                                breastSide = BreastSide.Left
                            )
                        }, text = stringResource(R.string.noun_left_breast))

                        WizardStepCard(onClick = {
                            uiState = uiState.copy(
                                currentStep = NutritionWizardSteps.BreastFeedingStart,
                                breastSide = BreastSide.Right
                            )
                        }, text = stringResource(R.string.noun_right_breast))

                        lastBreastLog?.let { log ->
                            BTtemporalData(
                                start = log.nutritionLog.startTime,
                                end = log.nutritionLog.endTime
                            ) {
                                val side = stringResource(R.string.noun_side)
                                Text("$side: ${log.breastLog?.breastSide?.name}")
                            }
                        }?: Column(Modifier.padding(8.dp)) {
                            Text(stringResource(R.string.sentence_breast_not_given_earlier))
                        }
                    }
            }
            NutritionWizardSteps.BreastFeedingStart -> {
                val notification =
                    stringResource(R.string.sentence_breast_feeding_started_good_luck)
                WizardStep {
                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = NutritionWizardSteps.BreastFeedingStop,
                            start = System.currentTimeMillis()
                        )

                        Toast
                            .makeText(
                                context,
                                notification,
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }, text = stringResource(R.string.action_start))
                }
            }
            NutritionWizardSteps.BreastFeedingStop -> {
                WizardStep {
                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = NutritionWizardSteps.BreastFeedingConfirmStop,
                        )
                    }, text = stringResource(R.string.action_stop))

                    val text = stringResource(R.string.action_start_time)
                    Text(text = "$text: ${context.getTime(uiState.start?: 0L)}")
                }
            }

            NutritionWizardSteps.BreastFeedingConfirmStop -> {
                WizardStep {
                    BTConfirmText()
                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            end = System.currentTimeMillis()
                        )

                        addBreastLog(
                            uiState.start!!,
                            uiState.end!!,
                            uiState.breastSide!!
                        )
                    }, text = stringResource(R.string.yes))
                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = NutritionWizardSteps.BreastFeedingStop,
                        )
                    }, text = stringResource(R.string.no))
                }
            }

            NutritionWizardSteps.BottleType -> {
                WizardStep {
                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = NutritionWizardSteps.BottleStart,
                            bottleType = BottleType.Formula,
                        )
                    }, text = stringResource(R.string.noun_formula))
                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = NutritionWizardSteps.BottleStart,
                            bottleType = BottleType.BreastMilk,
                        )
                    }, text = stringResource(R.string.noun_breast_milk))
                    if(lastBottleLog != null) {
                        BTtemporalData(
                            start = lastBottleLog.nutritionLog.startTime,
                            end = lastBottleLog.nutritionLog.endTime
                        )
                        val type = stringResource(R.string.noun_bottle_type)
                        Text("$type: ${lastBottleLog.bottleLog?.bottleType?.name}")
                    } else {
                        Text(stringResource(R.string.sentence_bottle_not_given_earlier))
                    }
                }
            }
            NutritionWizardSteps.BottleStart -> {
                WizardStep {
                    val notification =
                        stringResource(R.string.noun_bottle_feeding_started_good_luck)

                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = NutritionWizardSteps.BottleStop,
                            start = System.currentTimeMillis()
                        )

                        Toast
                            .makeText(
                                context,
                                notification,
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }, text = stringResource(R.string.action_start))


                }
            }
            NutritionWizardSteps.BottleStop -> {
                WizardStep {
                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = NutritionWizardSteps.BottleConfirmStop,
                        )
                    }, text = stringResource(R.string.action_stop))

                    val text = stringResource(R.string.action_start_time)
                    Text(text = "$text: ${context.getTime(uiState.start?: 0L)}")
                }
            }
            NutritionWizardSteps.BottleConfirmStop -> {
                WizardStep {
                    BTConfirmText()
                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            end = System.currentTimeMillis(),
                            currentStep = NutritionWizardSteps.BottleMillimeters
                        )
                    }, text = stringResource(R.string.yes))
                    WizardStepCard(onClick = {
                        uiState = uiState.copy(
                            currentStep = NutritionWizardSteps.BottleStop,
                        )
                    }, text = stringResource(R.string.no))

                }
            }
            NutritionWizardSteps.BottleMillimeters -> {
                WizardStep {
                    val supportiveText = stringResource(R.string.action_register_milliliters)
                    Text(text = supportiveText)
                    BTnumberTextField(
                        initialValue = null,
                        onChange = { uiState = uiState.copy(milliliters = it) },
                        label = stringResource(R.string.noun_milliliters),
                        placeholder = stringResource(R.string.label_example_150)
                    )
                    BTbutton(
                        name = stringResource(R.string.action_save),
                        onClick = {
                            addBottleLog(
                                uiState.start!!,
                                uiState.end!!,
                                uiState.bottleType!!,
                                uiState.milliliters ?: 0
                            )
                        }
                    )
                }
            }
        }
    }
}

private data class NutritionWizardUiState(
    val currentStep: NutritionWizardSteps = NutritionWizardSteps.BreastOrBottle,
    val breastSide: BreastSide? = null,
    val start: Long? = null,
    val end: Long? = null,
    val confirmedStop: Boolean = false,

    val bottleType: BottleType? = null,
    val milliliters: Int? = null
)

private enum class NutritionWizardSteps(val title: String) {
    BreastOrBottle("Breast or bottle?"),

    // breast flow
    LeftOrRightBreast("Left or right breast?"),
    BreastFeedingStart("Start breast feeding"),
    BreastFeedingStop("Stop breast feeding"),
    BreastFeedingConfirmStop("Confirm"),

    // bottle flow
    BottleType("Formula or breast?"),
    BottleStart("Start bottle feeding"),
    BottleStop("Stop bottle feeding"),
    BottleConfirmStop("Confirm"),
    BottleMillimeters("How much millimeters?"),
}