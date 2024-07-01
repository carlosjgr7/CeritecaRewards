package main.data.interfaces

import com.db.GoalMonth
import kotlinx.datetime.LocalDate

interface MonthDataSource {
    suspend fun getGoalMonthByDate(date: LocalDate): Int

    suspend fun insertGoalMonth(goalMonth: GoalMonth)

    suspend fun updateGoalMonth(goalMonth: GoalMonth)

    suspend fun insertOrUpdateGoalMonth(goalMonth: GoalMonth)

}
