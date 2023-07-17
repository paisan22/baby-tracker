package nl.paisan.babytracker.ui.screen.activity.wizards

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import nl.paisan.babytracker.ui.common.BTbutton
import nl.paisan.babytracker.ui.common.BTnumberTextField
import nl.paisan.babytracker.ui.screen.activity.wizards.shared.WizardStep
import nl.paisan.babytracker.ui.screen.activity.wizards.shared.WizardStepCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalMaterial3Api::class)
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

    AlertDialog(
        onDismissRequest = { onClose() }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(750.dp),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {
                when(uiState.currentStep) {
                    NutritionWizardSteps.BreastOrBottle -> {
                        WizardStep(cards = listOf<@Composable () -> Unit>(
                            {
                                WizardStepCard(onClick = {
                                    uiState = uiState.copy(
                                        currentStep = NutritionWizardSteps.LeftOrRightBreast
                                    )

                                }, text = "Breast")
                            },
                            {
                                WizardStepCard(onClick = {
                                    uiState = uiState.copy(
                                        currentStep = NutritionWizardSteps.BottleType
                                    )
                                }, text = "Bottle")
                            },
                        ))
                    }
                    NutritionWizardSteps.LeftOrRightBreast -> {
                        WizardStep(cards = listOf<@Composable () -> Unit>(
                            {
                                WizardStepCard(onClick = {
                                    uiState = uiState.copy(
                                        currentStep = NutritionWizardSteps.BreastFeedingStart,
                                        breastSide = BreastSide.Left
                                    )
                                }, text = "Left breast")
                            },
                            {
                                WizardStepCard(onClick = {
                                    uiState = uiState.copy(
                                        currentStep = NutritionWizardSteps.BreastFeedingStart,
                                        breastSide = BreastSide.Right
                                    )
                                }, text = "Right breast")
                            },
                            {
                                if(lastBreastLog != null) {
                                    // TODO: extract date logic to DateService
                                    val dateFormat =
                                        SimpleDateFormat(
                                            "dd-MM-yyyy HH:mm",
                                            Locale("nl", "NL")
                                        )

                                    val date = Date(lastBreastLog.nutritionLog.startTime)
                                    val startDatetime = dateFormat.format(date)

                                    val delta =
                                         lastBreastLog.nutritionLog.endTime - lastBreastLog.nutritionLog.startTime

                                    val deltaDate = Date(delta)
                                    val deltaDateFormat =
                                        SimpleDateFormat("mm:ss", Locale("nl", "NL"))
                                    val deltaDateFormatted = deltaDateFormat.format(deltaDate)
                                    deltaDate.time.minutes.inWholeMinutes

                                    // TODO: create private reusable composable
                                    Text("Last time: $startDatetime")
                                    Text("Duration: $deltaDateFormatted")

                                    Text("Side: ${lastBreastLog.breastLog?.breastSide?.name}")
                                } else {
                                    Text("Breast not given earlier.")
                                }
                            }
                        ))
                    }
                    NutritionWizardSteps.BreastFeedingStart -> {
                        WizardStep(cards = listOf<@Composable () -> Unit> {
                            WizardStepCard(onClick = {
                                uiState = uiState.copy(
                                    currentStep = NutritionWizardSteps.BreastFeedingStop,
                                    start = System.currentTimeMillis()
                                )

                                Toast
                                    .makeText(context, "Breast feeding started, good luck!", Toast.LENGTH_SHORT)
                                    .show()
                            }, text = "Start")
                        })
                    }
                    NutritionWizardSteps.BreastFeedingStop -> {
                        WizardStep(cards = listOf<@Composable () -> Unit> (
                            {
                                WizardStepCard(onClick = {
                                uiState = uiState.copy(
                                    currentStep = NutritionWizardSteps.BreastFeedingConfirmStop,
                                )
                            }, text = "Stop")
                        }, {
                            val dateFormat =
                                SimpleDateFormat(
                                    "HH:mm:ss",
                                    Locale("nl", "NL")
                                )
                            val date = Date(uiState.start?: 0L)
                            val startDatetime = dateFormat.format(date)

                            Text(text = "Start time: $startDatetime")
                        }))
                    }

                    NutritionWizardSteps.BreastFeedingConfirmStop -> {
                        WizardStep(
                            supportiveText = "Are you sure to stop?",
                            cards = listOf<@Composable () -> Unit>(
                                {
                                    WizardStepCard(onClick = {
                                        uiState = uiState.copy(
                                            end = System.currentTimeMillis()
                                        )

                                        addBreastLog(
                                            uiState.start!!,
                                            uiState.end!!,
                                            uiState.breastSide!!
                                        )
                                    }, text = "Yes")
                                }, {
                                    WizardStepCard(onClick = {
                                        uiState = uiState.copy(
                                            currentStep = NutritionWizardSteps.BreastFeedingStop,
                                        )
                                    }, text = "No")
                                }
                            )
                        )
                    }

                    NutritionWizardSteps.BottleType -> {
                        WizardStep(cards = listOf<@Composable () -> Unit>(
                            {
                                WizardStepCard(onClick = {
                                    uiState = uiState.copy(
                                        currentStep = NutritionWizardSteps.BottleStart,
                                        bottleType = BottleType.Formula,
                                    )
                                }, text = "Formula")
                            }, {
                                WizardStepCard(onClick = {
                                    uiState = uiState.copy(
                                        currentStep = NutritionWizardSteps.BottleStart,
                                        bottleType = BottleType.BreastMilk,
                                    )
                                }, text = "Breast milk")
                            },
                            {
                                if(lastBottleLog != null) {

                                    val dateFormat =
                                        SimpleDateFormat(
                                            "dd-MM-yyyy HH:mm",
                                            Locale("nl", "NL")
                                        )
                                    val date = Date(lastBottleLog.nutritionLog.startTime)
                                    val startDatetime = dateFormat.format(date)

                                    val delta =
                                        lastBottleLog.nutritionLog.endTime - lastBottleLog.nutritionLog.startTime

                                    val deltaDate = Date(delta)
                                    val deltaDateFormat =
                                        SimpleDateFormat("mm:ss", Locale("nl", "NL"))
                                    val deltaDateFormatted = deltaDateFormat.format(deltaDate)
                                    deltaDate.time.minutes.inWholeMinutes

                                    Text("Last time: $startDatetime")
                                    Text("Duration: $deltaDateFormatted")
                                    Text("Bottle type: ${lastBottleLog.bottleLog?.bottleType?.name}")
                                } else {
                                    Text("Bottle not given earlier.")
                                }
                            }
                        ))
                    }
                    NutritionWizardSteps.BottleStart -> {
                        WizardStep(cards = listOf<@Composable () -> Unit> {
                            WizardStepCard(onClick = {
                                uiState = uiState.copy(
                                    currentStep = NutritionWizardSteps.BottleStop,
                                    start = System.currentTimeMillis()
                                )

                                Toast
                                    .makeText(context, "Bottle feeding started, good luck!", Toast.LENGTH_SHORT)
                                    .show()
                            }, text = "Start")
                        })
                    }
                    NutritionWizardSteps.BottleStop -> {
                        WizardStep(cards = listOf<@Composable () -> Unit> (
                            {
                                WizardStepCard(onClick = {
                                    uiState = uiState.copy(
                                        currentStep = NutritionWizardSteps.BottleConfirmStop,
                                    )
                                }, text = "Stop")
                            }, {
                                val dateFormat =
                                    SimpleDateFormat(
                                        "HH:mm:ss",
                                        Locale("nl", "NL")
                                    )
                                val date = Date(uiState.start?: 0L)
                                val startDatetime = dateFormat.format(date)

                                Text(text = "Start time: $startDatetime")
                            }
                                ))
                    }
                    NutritionWizardSteps.BottleConfirmStop -> {
                        WizardStep(
                            supportiveText = "Are you sure to stop?",
                            cards = listOf<@Composable () -> Unit>(
                                {
                                    WizardStepCard(onClick = {
                                        uiState = uiState.copy(
                                            end = System.currentTimeMillis(),
                                            currentStep = NutritionWizardSteps.BottleMillimeters
                                        )
                                    }, text = "Yes")
                                }, {
                                    WizardStepCard(onClick = {
                                        uiState = uiState.copy(
                                            currentStep = NutritionWizardSteps.BottleStop,
                                        )
                                    }, text = "No")
                                }
                            )
                        )
                    }
                    NutritionWizardSteps.BottleMillimeters -> {
                        WizardStep(
                            supportiveText = "Register milli liters",
                            cards = listOf<@Composable () -> Unit> {
                                Column() {
                                    BTnumberTextField(
                                        initialValue = null,
                                        onChange = { uiState = uiState.copy(milliliters = it) },
                                        label = "Milli liters",
                                        placeholder = "Example: 150..."
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
                                        })
                                }
                            }
                        )
                    }
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

private enum class NutritionWizardSteps {
    BreastOrBottle,

    // breast flow
    LeftOrRightBreast,
    BreastFeedingStart,
    BreastFeedingStop,
    BreastFeedingConfirmStop,

    // bottle flow
    BottleType,
    BottleStart,
    BottleStop,
    BottleConfirmStop,
    BottleMillimeters,
}