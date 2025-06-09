package vcmsa.projects.expensetracker.utils

import vcmsa.projects.expensetracker.data.models.Expense
import java.util.Calendar
import java.util.Date

data class ExpenseFilter(
    val category: String? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val minAmount: Double? = null,
    val maxAmount: Double? = null,
    val searchQuery: String? = null
)

object ExpenseFilterUtils {

    fun filterExpenses(expenses: List<Expense>, filter: ExpenseFilter): List<Expense> {
        return expenses.filter { expense ->
            // Category filter
            (filter.category == null || expense.category == filter.category) &&

                    // Date range filter
                    (filter.startDate == null || expense.date.toDate().after(filter.startDate) ||
                            expense.date.toDate().equals(filter.startDate)) &&
                    (filter.endDate == null || expense.date.toDate().before(filter.endDate) ||
                            expense.date.toDate().equals(filter.endDate)) &&

                    // Amount range filter
                    (filter.minAmount == null || expense.amount >= filter.minAmount) &&
                    (filter.maxAmount == null || expense.amount <= filter.maxAmount) &&

                    // Search query filter
                    (filter.searchQuery == null ||
                            expense.title.contains(filter.searchQuery, ignoreCase = true) ||
                            expense.description.contains(filter.searchQuery, ignoreCase = true))
        }
    }

    fun getTodayFilter(): ExpenseFilter {
        val today = Date()
        return ExpenseFilter(
            startDate = DateUtils.getStartOfDay(today),
            endDate = DateUtils.getEndOfDay(today)
        )
    }

    fun getThisWeekFilter(): ExpenseFilter {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val startOfWeek = DateUtils.getStartOfDay(calendar.time)

        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val endOfWeek = DateUtils.getEndOfDay(calendar.time)

        return ExpenseFilter(
            startDate = startOfWeek,
            endDate = endOfWeek
        )
    }

    fun getThisMonthFilter(): ExpenseFilter {
        val today = Date()
        return ExpenseFilter(
            startDate = DateUtils.getStartOfMonth(today),
            endDate = DateUtils.getEndOfMonth(today)
        )
    }


    //Google. (n.d.). Android developers. https://developer.android.com
//
//Google. (n.d.). Android Studio user guide. https://developer.android.com/studio/intro
//
//Google. (n.d.). Android Codelabs. https://developer.android.com/codelabs
//
//Google. (n.d.). Android API reference. https://developer.android.com/reference
//
//Stack Overflow. (n.d.). Android tag – Stack Overflow. https://stackoverflow.com/questions/tagged/android
//
//Google. (n.d.). Android · GitHub. https://github.com/android
//
//JetBrains. (n.d.). Kotlin Playground. https://play.kotlinlang.org
//
//JetBrains. (n.d.). JetBrains Academy. https://www.jetbrains.com/academy/
}
