package nl.paisan.babytracker.ui.screen.addActivity.wizards

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Adjust
import androidx.compose.material.icons.outlined.ArrowCircleLeft
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Kitchen
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.paisan.babytracker.R
import nl.paisan.babytracker.data.entities.NutritionLogWithDetails
import nl.paisan.babytracker.domain.enums.BottleType
import nl.paisan.babytracker.domain.enums.BreastSide
import nl.paisan.babytracker.domain.services.getTime
import nl.paisan.babytracker.ui.common.BTConfirmText
import nl.paisan.babytracker.ui.common.BTbutton
import nl.paisan.babytracker.ui.common.BTcardButton
import nl.paisan.babytracker.ui.common.BTnumberTextField
import nl.paisan.babytracker.ui.common.BTtemporalData
import nl.paisan.babytracker.ui.common.BTwizardDialog
import nl.paisan.babytracker.ui.common.BTcardColumn

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
                BTcardColumn {
                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = NutritionWizardSteps.LeftOrRightBreast
                            )
                        },
                        label = stringResource(R.string.noun_breast),
                        painter = painterResource(id = R.drawable.breastfeeding_48px)
                    )

                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = NutritionWizardSteps.BottleType
                            )
                        },
                        label = stringResource(R.string.noun_bottle),
                        painter = painterResource(id = R.drawable.pediatrics_48px)
                    )
                }
            }
            NutritionWizardSteps.LeftOrRightBreast -> {
                BTcardColumn {
                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = NutritionWizardSteps.BreastFeedingStart,
                                breastSide = BreastSide.Left
                            )
                        },
                        label = stringResource(R.string.noun_left_breast),
                        imageVector = Icons.Outlined.ArrowCircleLeft
                    )

                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = NutritionWizardSteps.BreastFeedingStart,
                                breastSide = BreastSide.Right
                            )
                        },
                        label = stringResource(R.string.noun_right_breast),
                        imageVector = Icons.Outlined.ArrowCircleRight
                    )

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
                BTcardColumn {
                    BTcardButton(
                        onClick = {
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
                        },
                        label = stringResource(R.string.action_start),
                        imageVector = Icons.Outlined.PlayCircleOutline
                    )
                }
            }
            NutritionWizardSteps.BreastFeedingStop -> {
                BTcardColumn {
                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = NutritionWizardSteps.BreastFeedingConfirmStop,
                            )
                        },
                        label = stringResource(R.string.action_stop),
                        imageVector = Icons.Outlined.StopCircle
                    )

                    val text = stringResource(R.string.action_start_time)
                    Text(text = "$text: ${context.getTime(uiState.start?: 0L)}")
                }
            }

            NutritionWizardSteps.BreastFeedingConfirmStop -> {
                BTcardColumn {
                    BTConfirmText()
                    BTcardButton(onClick = {
                        uiState = uiState.copy(
                            end = System.currentTimeMillis()
                        )

                        addBreastLog(
                            uiState.start!!,
                            uiState.end!!,
                            uiState.breastSide!!
                        )
                    }, label = stringResource(R.string.yes), imageVector = Icons.Outlined.CheckCircle)
                    BTcardButton(onClick = {
                        uiState = uiState.copy(
                            currentStep = NutritionWizardSteps.BreastFeedingStop,
                        )
                    }, label = stringResource(R.string.no), imageVector = Icons.Outlined.Cancel)
                }
            }

            NutritionWizardSteps.BottleType -> {
                BTcardColumn {
                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = NutritionWizardSteps.BottleStart,
                                bottleType = BottleType.Formula,
                            )
                        },
                        label = stringResource(R.string.noun_formula),
                        imageVector = Icons.Outlined.Kitchen
                    )
                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = NutritionWizardSteps.BottleStart,
                                bottleType = BottleType.BreastMilk,
                            )
                        },
                        label = stringResource(R.string.noun_breast_milk),
                        imageVector = Icons.Outlined.Adjust
                    )
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
                BTcardColumn {
                    val notification =
                        stringResource(R.string.noun_bottle_feeding_started_good_luck)

                    BTcardButton(
                        onClick = {
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
                        },
                        label = stringResource(R.string.action_start),
                        imageVector = Icons.Outlined.PlayCircleOutline
                    )
                }
            }
            NutritionWizardSteps.BottleStop -> {
                BTcardColumn {
                    BTcardButton(
                        onClick = {
                            uiState = uiState.copy(
                                currentStep = NutritionWizardSteps.BottleConfirmStop,
                            )
                        },
                        label = stringResource(R.string.action_stop),
                        imageVector = Icons.Outlined.StopCircle
                    )

                    val text = stringResource(R.string.action_start_time)
                    Text(text = "$text: ${context.getTime(uiState.start?: 0L)}")
                }
            }
            NutritionWizardSteps.BottleConfirmStop -> {
                BTcardColumn {
                    BTConfirmText()
                    BTcardButton(onClick = {
                        uiState = uiState.copy(
                            end = System.currentTimeMillis(),
                            currentStep = NutritionWizardSteps.BottleMillimeters
                        )
                    }, label = stringResource(R.string.yes), imageVector = Icons.Outlined.CheckCircle)
                    BTcardButton(onClick = {
                        uiState = uiState.copy(
                            currentStep = NutritionWizardSteps.BottleStop,
                        )
                    }, label = stringResource(R.string.no), imageVector = Icons.Outlined.Cancel)

                }
            }
            NutritionWizardSteps.BottleMillimeters -> {
                BTcardColumn {
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