package main.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ceritecareward.composeapp.generated.resources.Res
import ceritecareward.composeapp.generated.resources.edit
import ceritecareward.composeapp.generated.resources.save
import io.wojciechosak.calendar.utils.today
import kotlinx.datetime.LocalDate
import main.util.DatePickerCalendar
import main.util.MesesDropdownMenu
import main.util.monthToMes
import multiplatformFunctions.toMonthNumber
import org.jetbrains.compose.resources.painterResource
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale


@Composable
fun DialogScreen(
    viewModel: MainViewModel,
    onClose: () -> Unit,
) {

    val today = LocalDate.today()

    var currentMonth by remember { mutableStateOf(today.month.monthToMes()) }
    var visibleChoseDate by remember { mutableStateOf(false) }
    var daytoSearch by remember { mutableStateOf(LocalDate.today()) }

    var editDay by remember { mutableStateOf(false) }

    val focusGoal = remember { FocusRequester() }
    val focusDaily = remember { FocusRequester() }

    val formatter = NumberFormat.getNumberInstance(Locale.GERMANY)

    val symbols = DecimalFormatSymbols(Locale.US)
    symbols.groupingSeparator = '.'
    val formatter2 = DecimalFormat("#,###", symbols)



    val currentGoalCollect = viewModel.currentGoal.collectAsState()
    val currentDayCollect = viewModel.currentDay.collectAsState()
    val percentageCollect = viewModel.percentSucces.collectAsState()

    var goal by remember { mutableStateOf(currentGoalCollect.value) }
    var editGoal by remember { mutableStateOf(false) }
    var SellOfday by remember { mutableStateOf(currentDayCollect.value) }

    val dayParts = SellOfday.toString().split(".")
    val formattedDay = if (SellOfday > 0.1f) {
        val integerPart = formatter2.format(dayParts[0].toInt())
        val decimalPart = if (dayParts.size > 1) dayParts[1] else ""
        "$integerPart,$decimalPart"
    } else ""



    LaunchedEffect(currentDayCollect.value) {
        SellOfday = currentDayCollect.value
    }
    LaunchedEffect(currentGoalCollect.value) {
        goal = currentGoalCollect.value
    }

    LaunchedEffect(daytoSearch) {
        viewModel.updateSellday(daytoSearch)
    }

    LaunchedEffect(currentMonth) {
        viewModel.updatePercentSucces(LocalDate(today.year, currentMonth.toMonthNumber(), 1))
    }


    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(onClick = { onClose() }) {
                Text("OK")
            }
        },
        title = { Text("Configuracion de Metas") },
        text = {
            Column {
                Text(
                    text = "Meta mensual",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Row {

                    MesesDropdownMenu(currentMonth, Modifier.weight(1f)) {
                        currentMonth = it
                        viewModel.updateGoalMonth(LocalDate(today.year, it.toMonthNumber(), 1))
                    }

                    Spacer(modifier = Modifier.weight(0.1f))

                    OutlinedTextField(
                        modifier = Modifier.weight(1f)
                            .focusRequester(focusGoal),
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold
                        ),
                        value = if (goal > 0) formatter.format(goal) else "",
                        label = { Text(text = "Meta $") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            if (editGoal) goal = it.replace(".", "").toIntOrNull() ?: 0
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.Black,
                            cursorColor = if (editGoal) Color(0xFF027017) else Color(0xFFCDCDCD),
                            focusedBorderColor = if (editGoal) Color(0xFF027017) else Color(
                                0xFFCDCDCD
                            ),
                            unfocusedBorderColor = if (editGoal) Color(0xFF027017) else Color(
                                0xFFCDCDCD
                            )
                        ),
                        trailingIcon = {
                            Row {
                                IconButton(onClick = {
                                    editGoal = !editGoal
                                    if (editGoal) {
                                        focusGoal.requestFocus()
                                        goal = 0
                                    }
                                }) {
                                    Image(
                                        painter = painterResource(Res.drawable.edit),
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )

                                }
                                IconButton(onClick = {
                                    viewModel.saveGoalMonth(
                                        LocalDate(today.year, currentMonth.toMonthNumber(), 1),
                                        goal.toFloat()
                                    )
                                    editGoal = false
                                }) {
                                    Image(
                                        painter = painterResource(Res.drawable.save),
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    "meta de $currentMonth cumplida en un ${String.format("%.2f", percentageCollect.value)}%",
                    fontSize = 12.sp,
                    color = Color(0xFFCDCDCD),
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Ventas Diarias",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Row {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = daytoSearch.toString(),
                        onValueChange = {

                        },
                        label = { Text("Dia") },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { visibleChoseDate = !visibleChoseDate }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.weight(0.1f))



                    OutlinedTextField(
                        modifier = Modifier.weight(1f)
                            .focusRequester(focusDaily),
                        value = formattedDay,
                        label = { Text(text = "Venta $") },
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.Black,
                            cursorColor = if (editDay) Color(0xFF027017) else Color(0xFFCDCDCD),
                            focusedBorderColor = if (editDay) Color(0xFF027017) else Color(
                                0xFFCDCDCD
                            ),
                            unfocusedBorderColor = if (editDay) Color(0xFF027017) else Color(
                                0xFFCDCDCD
                            )
                        ),
                        onValueChange = {
                            if (editDay) {
                                val parts = it.split(",")
                                val integerPart = parts[0].replace(".", "").toIntOrNull() ?: 0
                                val decimalPart = if (parts.size > 1) parts[1] else ""
                                SellOfday = "$integerPart.$decimalPart".toFloatOrNull() ?: 0.0f
                            }

                        },
                        trailingIcon = {
                            Row {
                                IconButton(onClick = {
                                    editDay = !editDay
                                    if (editDay) {
                                        focusDaily.requestFocus()
                                        SellOfday = 0.0f
                                    }
                                }) {
                                    Image(
                                        painter = painterResource(Res.drawable.edit),
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )

                                }
                                IconButton(onClick = {
                                    viewModel.saveDaySell(
                                        daytoSearch,
                                        SellOfday
                                    )
                                    editDay = false
                                }) {
                                    Image(
                                        painter = painterResource(Res.drawable.save),
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    )

                }
                val alpha: Float by animateFloatAsState(
                    targetValue = if (visibleChoseDate) 1f else 0f,
                    animationSpec = tween(durationMillis = 1000)
                )


                if (visibleChoseDate) {
                    Box(
                        Modifier
                            .padding(25.dp)
                            .alpha(alpha)
                    ) {
                        DatePickerCalendar(Modifier, todayParam = daytoSearch) {
                            daytoSearch = it
                            viewModel.updateSellday(daytoSearch)
                            visibleChoseDate = false
                        }
                    }
                }
            }
        }
    )
}

