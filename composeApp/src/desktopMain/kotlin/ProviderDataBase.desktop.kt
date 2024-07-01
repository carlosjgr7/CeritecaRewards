import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.db.AppDatabase
import java.io.File

actual fun ProviderDataBase(): AppDatabase {
    val driver = JdbcSqliteDriver("jdbc:sqlite:members.db")
    if (!File("TaskDatabase.db").exists())  // check if database alread created or not
    {
        AppDatabase.Schema.create(driver)
    }
    return AppDatabase(driver)
}

