package main.data.interfaces

import com.db.DaySell
import com.db.GoalMonth
import kotlinx.datetime.LocalDate

interface DayDataSource {

    suspend fun getDayByDate(date:LocalDate): Float

    suspend fun getAllDayByMonth(date: LocalDate): Float

    suspend fun insertOrUpdateSellDay(daySell: LocalDate, amount:Float)

}


