package main.data

import com.db.AppDatabase
import com.db.GoalMonth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import main.data.interfaces.MonthDataSource

class MonthDataSourceImpl(
    db: AppDatabase
) : MonthDataSource {

    private val monthQueries = db.goalMonthQueries

    override suspend fun getGoalMonthByDate(date: LocalDate): Int = withContext(Dispatchers.IO) {
        monthQueries.selectGoalByMonthAndYear(
            month = date.monthNumber.toLong(),
            year = date.year.toLong()
        ).executeAsOneOrNull()?.toInt() ?: 0
    }

    override suspend fun insertGoalMonth(goalMonth: GoalMonth) = withContext(Dispatchers.IO) {
        monthQueries.insertGoalMonth(
            month = goalMonth.month,
            year = goalMonth.year,
            goal = goalMonth.goal
        )
    }

    override suspend fun updateGoalMonth(goalMonth: GoalMonth) = withContext(Dispatchers.IO) {
        monthQueries.updateGoalMonth(
            goal = goalMonth.goal,
            month = goalMonth.month,
            year = goalMonth.year
        )
    }

    override suspend fun insertOrUpdateGoalMonth(goalMonth: GoalMonth) =
        withContext(Dispatchers.IO) {
            val existingGoalMonth = monthQueries.selectGoalByMonthAndYear(
                month = goalMonth.month,
                year = goalMonth.year
            ).executeAsOneOrNull()

            if (existingGoalMonth != null) {
                monthQueries.updateGoalMonth(
                    goal = goalMonth.goal,
                    month = goalMonth.month,
                    year = goalMonth.year
                )
            } else {
                monthQueries.insertGoalMonth(
                    month = goalMonth.month,
                    year = goalMonth.year,
                    goal = goalMonth.goal
                )
            }
        }
}