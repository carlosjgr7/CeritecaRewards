package main.data

import com.db.AppDatabase
import com.db.DaySell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import main.data.interfaces.DayDataSource

class DayDataSourceImpl(
    db: AppDatabase
) : DayDataSource {

    private val dayQueries = db.daySellQueries

    override suspend fun getDayByDate(date: LocalDate): Float {
        return withContext(Dispatchers.IO) {
            dayQueries.selectDaySellByDay(
                day = date.dayOfMonth.toLong(),
                month = date.monthNumber.toLong(),
                year = date.year.toLong()
            ).executeAsOneOrNull()?.toFloat() ?: 0.0f
        }
    }

    override suspend fun getAllDayByMonth(date: LocalDate): Float {
        return withContext(Dispatchers.IO) {
            dayQueries.selectSumOfSalesByMonth(
                month = date.monthNumber.toLong(),
                year = date.year.toLong()
            ).executeAsOneOrNull()?.SUM?.toFloat() ?: 0.0f
        }
    }

    override suspend fun insertOrUpdateSellDay(daySell: LocalDate, amount: Float) {
        withContext(Dispatchers.IO) {
            val existingDaySell = dayQueries.selectDaySellByDay(
                day = daySell.dayOfMonth.toLong(),
                month = daySell.monthNumber.toLong(),
                year = daySell.year.toLong()
            ).executeAsOneOrNull()

            if (existingDaySell != null) {
                dayQueries.updateDaySell(
                    amount = amount.toDouble(),
                    day = daySell.dayOfMonth.toLong(),
                    year = daySell.year.toLong(),
                    month = daySell.monthNumber.toLong()
                )
            } else {
                dayQueries.insertDaySell(
                    day = daySell.dayOfMonth.toLong(),
                    month = daySell.monthNumber.toLong(),
                    year = daySell.year.toLong(),
                    amount = amount.toDouble(),
                )
            }
        }
    }
}