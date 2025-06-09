package vcmsa.projects.expensetracker.data.models



import com.google.firebase.Timestamp
import java.util.Date

data class Expense(
    val id: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val description: String = "",
    val date: Timestamp = Timestamp.now(),
    val userId: String = "",
    val title: String,
    val createdAt: Timestamp
) {
    // Convert Timestamp to Date for easier handling
    fun getDateAsDate(): Date = date.toDate()
}