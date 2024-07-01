package main.presentation


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.db.GoalMonth
import io.wojciechosak.calendar.utils.today
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import main.data.interfaces.DayDataSource
import main.data.interfaces.MonthDataSource
import multiplatformFunctions.toMonthNumber

class MainViewModel(
    private val dayDataSource: DayDataSource,
    private val monthDataSource: MonthDataSource
) : ScreenModel {
    private val today = LocalDate.today()

    fun loadData() {
        screenModelScope.launch {
            getDayByDate(today)
            getAllDayByMonth(today)
            getGoalMonthByDate(today)
        }
    }

    private val _currentGoal: MutableStateFlow<Int> =
        MutableStateFlow(0)
    val currentGoal: StateFlow<Int> = _currentGoal.asStateFlow()

    private val _currentDay: MutableStateFlow<Float> =
        MutableStateFlow(0f)
    val currentDay: StateFlow<Float> = _currentDay.asStateFlow()

    private val _percentSucces: MutableStateFlow<Float> =
        MutableStateFlow(0f)

    val percentSucces: StateFlow<Float> = _percentSucces.asStateFlow()


    suspend fun getDayByDate(date: LocalDate) {
        screenModelScope.launch {
            _currentDay.value = dayDataSource.getDayByDate(date)
        }
    }



    suspend fun getAllDayByMonth(date: LocalDate) {
        val totalDeferred = screenModelScope.async { dayDataSource.getAllDayByMonth(date) }
        val goalDeferred = screenModelScope.async { monthDataSource.getGoalMonthByDate(date) }

        val total = totalDeferred.await()
        val goal = goalDeferred.await()
        if (goal != 0) {
            _percentSucces.value = (total / goal) * 100
        } else {
            _percentSucces.value = 0f
        }
    }

    suspend fun insertOrUpdateSellDay(daySell: LocalDate, amount: Float) {
        screenModelScope.launch {
            dayDataSource.insertOrUpdateSellDay(daySell, amount)
            updatePercentSucces(LocalDate(today.year, daySell.monthNumber, 1))

        }
    }

    suspend fun getGoalMonthByDate(date: LocalDate) {
        screenModelScope.launch {
            _currentGoal.value = monthDataSource.getGoalMonthByDate(date)
        }
    }

    fun updateSellday(daytoSearch: LocalDate) {
        screenModelScope.launch { getDayByDate(daytoSearch) }
    }

    fun updatePercentSucces(daytoSearch: LocalDate) {
        screenModelScope.launch { getAllDayByMonth(daytoSearch) }
    }

    suspend fun insertOrUpdateGoalMonth(localDate: LocalDate, goal: Float) {
        val goalMonth = GoalMonth(
            id = 0,
            month = localDate.monthNumber.toLong(),
            year = localDate.year.toLong(),
            goal = goal.toLong()
        )
        screenModelScope.launch {
            monthDataSource.insertOrUpdateGoalMonth(goalMonth)
        }
    }

    fun saveGoalMonth(daytoSearch: LocalDate, toFloat: Float) {
        screenModelScope.launch {
            insertOrUpdateGoalMonth(daytoSearch, toFloat)
        }
    }

    fun saveDaySell(daytoSearch: LocalDate, amount: Float) {
        screenModelScope.launch {
            insertOrUpdateSellDay(daytoSearch, amount)
        }
    }

    fun updateGoalMonth(localDate: LocalDate) {
        screenModelScope.launch {
            getGoalMonthByDate(localDate)
            getAllDayByMonth(localDate)
        }

    }


}