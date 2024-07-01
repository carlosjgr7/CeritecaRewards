package main.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import io.wojciechosak.calendar.animation.CalendarAnimator
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarDay
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.number


@Composable
@OptIn(ExperimentalFoundationApi::class)
fun DatePickerCalendar(modifier: Modifier,todayParam:LocalDate=LocalDate.today(), onSelectDate: (LocalDate) -> Unit = {}) {
    val coroutineScope = rememberCoroutineScope()
    var today = todayParam
    var firstDayOfNextMonth by remember { mutableStateOf(getFirstDayOfNextMonth(today)) }
    var firstDayOfLastMonth by remember { mutableStateOf(getFirstDayOfLastMonth(today)) }

    val startDate by remember { mutableStateOf(today) }
    val calendarAnimator by remember { mutableStateOf(CalendarAnimator(startDate)) }
    var selectedDay by remember { mutableStateOf<LocalDate?>(today) }
    var isAnimating by remember { mutableStateOf(false) }




    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        HorizontalCalendarView(
            startDate = startDate,
            calendarAnimator = calendarAnimator,
            modifier = Modifier.pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consumeAllChanges()
                }
            }
        ) { monthOffset ->
            CalendarView(
                day = { dayState ->
                    CalendarDay(
                        state = dayState,
                    )
                },
                config =
                rememberCalendarState(
                    startDate = startDate,
                    monthOffset = monthOffset,
                ),
                isActiveDay = { it == selectedDay },
                onDateSelected = { it ->
                    selectedDay = it.firstOrNull()
                    selectedDay?.let {dateSelected->
                        onSelectDate(dateSelected)

                    }
                },
            )
        }
        Row(Modifier.padding(8.dp)) {
            IconButton(
                modifier = Modifier.size(48.dp),
                enabled = !isAnimating, onClick = {
                    coroutineScope.launch {
                        isAnimating = true
                        calendarAnimator.animateTo(firstDayOfLastMonth)
                        delay(600)
                        today = firstDayOfLastMonth
                        firstDayOfLastMonth = getFirstDayOfLastMonth(today)
                        firstDayOfNextMonth = getFirstDayOfNextMonth(today)
                        isAnimating = false

                    }

                }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null)

            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                modifier = Modifier.size(48.dp),
                enabled = !isAnimating, onClick = {
                    coroutineScope.launch {
                        isAnimating = true
                        calendarAnimator.animateTo(firstDayOfNextMonth)
                        delay(600)
                        today = firstDayOfNextMonth
                        firstDayOfNextMonth = getFirstDayOfNextMonth(today)
                        firstDayOfLastMonth = getFirstDayOfLastMonth(today)
                        isAnimating = false

                    }
                }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)

            }
        }
    }
}

fun getFirstDayOfNextMonth(date: LocalDate): LocalDate {
    return if (date.month == Month.DECEMBER) {
        LocalDate(date.year + 1, Month.JANUARY, 1)
    } else {
        LocalDate(date.year, date.month.number + 1, 1)
    }
}

fun getFirstDayOfLastMonth(date: LocalDate): LocalDate {
    return if (date.month == Month.JANUARY) {
        LocalDate(date.year - 1, Month.DECEMBER, 1)
    } else {
        LocalDate(date.year, date.month.number - 1, 1)
    }
}