package vcmsa.projects.expensetracker.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

    fun formatAmount(amount: Double): String {
        return currencyFormat.format(amount)
    }

    fun formatAmountWithoutSymbol(amount: Double): String {
        return String.format(Locale.US, "%.2f", amount)
    }

    fun parseAmount(amountString: String): Double? {
        return try {
            amountString.replace("[^\\d.]".toRegex(), "").toDouble()
        } catch (e: NumberFormatException) {
            null
        }
    }

    fun isValidAmount(amountString: String): Boolean {
        return try {
            val amount = parseAmount(amountString)
            amount != null && amount > 0
        } catch (e: Exception) {
            false
        }
    }
}