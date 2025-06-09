package vcmsa.projects.expensetracker.data.models


data class SpendingGoal(
    val categoryId: String = "",
    val categoryName: String = "",
    val minAmount: Double = 0.0,
    val maxAmount: Double = 0.0,
    val currentAmount: Double = 0.0,
    val id: Long,
    val syncedToCloud: Boolean,
    val isCompleted: Boolean
) {
    fun isWithinGoal(): Boolean {
        return currentAmount in minAmount..maxAmount
    }

    fun getProgressPercentage(): Float {
        return if (maxAmount > 0) {
            ((currentAmount / maxAmount) * 100).toFloat()
        } else 0f
    }
}

