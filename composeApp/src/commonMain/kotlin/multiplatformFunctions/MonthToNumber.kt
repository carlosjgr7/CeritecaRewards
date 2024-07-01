package multiplatformFunctions

import java.util.Locale

fun String.toMonthNumber(): Int {
    return when (this.lowercase()) {
        "enero" -> 1
        "febrero" -> 2
        "marzo" -> 3
        "abril" -> 4
        "mayo" -> 5
        "junio" -> 6
        "julio" -> 7
        "agosto" -> 8
        "septiembre" -> 9
        "octubre" -> 10
        "noviembre" -> 11
        "diciembre" -> 12
        else -> throw IllegalArgumentException("Invalid month name")
    }
}