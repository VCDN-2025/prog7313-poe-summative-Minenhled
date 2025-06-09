package vcmsa.projects.expensetracker.utils

object ValidationUtils {

    fun isValidTitle(title: String): Boolean {
        return title.trim().isNotEmpty() && title.length <= Constants.MAX_TITLE_LENGTH
    }

    fun isValidAmount(amount: String): Boolean {
        return try {
            val value = amount.toDouble()
            value >= Constants.MIN_AMOUNT && value <= Constants.MAX_AMOUNT
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun isValidDescription(description: String): Boolean {
        return description.length <= Constants.MAX_DESCRIPTION_LENGTH
    }

    fun getTitleError(title: String): String? {
        return when {
            title.trim().isEmpty() -> "Title is required"
            title.length > Constants.MAX_TITLE_LENGTH -> "Title is too long (max ${Constants.MAX_TITLE_LENGTH} characters)"
            else -> null
        }
    }

    fun getAmountError(amount: String): String? {
        return try {
            val value = amount.toDouble()
            when {
                value < Constants.MIN_AMOUNT -> "Amount must be at least $${Constants.MIN_AMOUNT}"
                value > Constants.MAX_AMOUNT -> "Amount cannot exceed $${Constants.MAX_AMOUNT}"
                else -> null
            }
        } catch (e: NumberFormatException) {
            "Invalid amount format"
        }
    }

    fun getDescriptionError(description: String): String? {
        return if (description.length > Constants.MAX_DESCRIPTION_LENGTH) {
            "Description is too long (max ${Constants.MAX_DESCRIPTION_LENGTH} characters)"
        } else null
    }
}
