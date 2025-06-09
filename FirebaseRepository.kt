package vcmsa.projects.expensetracker.data.repository



import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.Timestamp
import vcmsa.projects.expensetracker.data.models.Category
import vcmsa.projects.expensetracker.data.models.Expense
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

class FirebaseRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: ""
    }

    // EXPENSE OPERATIONS
    suspend fun addExpense(expense: Expense): Result<String> {
        return try {
            val expenseWithUserId = expense.copy(userId = getCurrentUserId())
            val docRef = db.collection("expenses").add(expenseWithUserId).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getExpenses(): Result<List<Expense>> {
        return try {
            val snapshot = db.collection("expenses")
                .whereEqualTo("userId", getCurrentUserId())
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()

            val expenses = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Expense::class.java)?.copy(id = doc.id)
            }
            Result.success(expenses)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getExpensesByDateRange(startDate: Date, endDate: Date): Result<List<Expense>> {
        return try {
            val startTimestamp = Timestamp(startDate)
            val endTimestamp = Timestamp(endDate)

            val snapshot = db.collection("expenses")
                .whereEqualTo("userId", getCurrentUserId())
                .whereGreaterThanOrEqualTo("date", startTimestamp)
                .whereLessThanOrEqualTo("date", endTimestamp)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()

            val expenses = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Expense::class.java)?.copy(id = doc.id)
            }
            Result.success(expenses)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteExpense(expenseId: String): Result<Unit> {
        return try {
            db.collection("expenses").document(expenseId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // CATEGORY OPERATIONS
    suspend fun addCategory(category: Category): Result<String> {
        return try {
            val categoryWithUserId = category.copy(userId = getCurrentUserId())
            val docRef = db.collection("categories").add(categoryWithUserId).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCategories(): Result<List<Category>> {
        return try {
            val snapshot = db.collection("categories")
                .whereEqualTo("userId", getCurrentUserId())
                .get()
                .await()

            val categories = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Category::class.java)?.copy(id = doc.id)
            }
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ANALYTICS
    suspend fun getSpendingByCategory(startDate: Date, endDate: Date): Result<Map<String, Double>> {
        return try {
            val expensesResult = getExpensesByDateRange(startDate, endDate)
            if (expensesResult.isSuccess) {
                val expenses = expensesResult.getOrNull() ?: emptyList()
                val spendingByCategory = expenses.groupBy { it.category }
                    .mapValues { (_, expenses) -> expenses.sumOf { it.amount } }
                Result.success(spendingByCategory)
            } else {
                Result.failure(expensesResult.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLastMonthSpending(): Result<Map<String, Double>> {
        val calendar = Calendar.getInstance()
        val endDate = calendar.time
        calendar.add(Calendar.MONTH, -1)
        val startDate = calendar.time

        return getSpendingByCategory(startDate, endDate)
    }
}