package vcmsa.projects.expensetracker.utils

object Constants {
    // Firebase Collections
    const val COLLECTION_EXPENSES = "expenses"
    const val COLLECTION_CATEGORIES = "categories"
    const val COLLECTION_USERS = "users"

    // Expense Categories
    val DEFAULT_CATEGORIES = listOf(
        "Food & Dining",
        "Transportation",
        "Shopping",
        "Entertainment",
        "Bills & Utilities",
        "Healthcare",
        "Education",
        "Travel",
        "Groceries",
        "Gas & Fuel",
        "Insurance",
        "Rent/Mortgage",
        "Personal Care",
        "Gifts & Donations",
        "Other"
    )

    // Shared Preferences Keys
    const val PREF_NAME = "expense_tracker_prefs"
    const val PREF_FIRST_LAUNCH = "first_launch"
    const val PREF_CURRENCY_SYMBOL = "currency_symbol"
    const val PREF_DEFAULT_CATEGORY = "default_category"

    // Intent Extras
    const val EXTRA_EXPENSE_ID = "expense_id"
    const val EXTRA_EXPENSE = "expense"
    const val EXTRA_CATEGORY = "category"

    // Date Formats
    const val DATE_FORMAT_DISPLAY = "MMM dd, yyyy"
    const val DATE_FORMAT_SHORT = "dd/MM/yyyy"
    const val DATE_FORMAT_MONTH_YEAR = "MMM yyyy"

    // Animation Durations
    const val ANIMATION_DURATION_SHORT = 200L
    const val ANIMATION_DURATION_MEDIUM = 300L
    const val ANIMATION_DURATION_LONG = 500L

    // Validation
    const val MAX_TITLE_LENGTH = 50
    const val MAX_DESCRIPTION_LENGTH = 200
    const val MAX_AMOUNT = 999999.99
    const val MIN_AMOUNT = 0.01
}
