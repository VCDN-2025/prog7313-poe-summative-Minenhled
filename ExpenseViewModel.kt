package vcmsa.projects.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import vcmsa.projects.expensetracker.data.models.Category
import vcmsa.projects.expensetracker.data.models.Expense
import vcmsa.projects.expensetracker.data.models.SpendingGoal
import vcmsa.projects.expensetracker.data.repository.FirebaseRepository
import kotlinx.coroutines.launch
import java.util.Date

class ExpenseViewModel : ViewModel() {

    private val repository = FirebaseRepository()

    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> = _expenses

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _spendingByCategory = MutableLiveData<Map<String, Double>>()
    val spendingByCategory: LiveData<Map<String, Double>> = _spendingByCategory

    private val _spendingGoals = MutableLiveData<List<SpendingGoal>>()
    val spendingGoals: LiveData<List<SpendingGoal>> = _spendingGoals

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadExpenses() {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.getExpenses()
            if (result.isSuccess) {
                _expenses.value = result.getOrNull() ?: emptyList()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Unknown error loading expenses"
            }
            _loading.value = false
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.addExpense(expense)
            if (result.isSuccess) {
                loadExpenses() // Refresh after adding
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Unknown error adding expense"
            }
            _loading.value = false
        }
    }

    fun deleteExpense(expenseId: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.deleteExpense(expenseId)
            if (result.isSuccess) {
                loadExpenses() // Refresh after deleting
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Unknown error deleting expense"
            }
            _loading.value = false
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.getCategories()
            if (result.isSuccess) {
                _categories.value = result.getOrNull() ?: emptyList()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Unknown error loading categories"
            }
            _loading.value = false
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.addCategory(category)
            if (result.isSuccess) {
                loadCategories() // Refresh after adding
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Unknown error adding category"
            }
            _loading.value = false
        }
    }

    fun loadSpendingByCategory(startDate: Date, endDate: Date) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.getSpendingByCategory(startDate, endDate)
            if (result.isSuccess) {
                _spendingByCategory.value = result.getOrNull() ?: emptyMap()
                calculateSpendingGoals()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Unknown error loading spending by category"
            }
            _loading.value = false
        }
    }

    fun loadLastMonthSpending() {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.getLastMonthSpending()
            if (result.isSuccess) {
                _spendingByCategory.value = result.getOrNull() ?: emptyMap()
                calculateSpendingGoals()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Unknown error loading last month spending"
            }
            _loading.value = false
        }
    }

    private fun calculateSpendingGoals() {
        val categories = _categories.value ?: return
        val spending = _spendingByCategory.value ?: return

        val goals = categories.map { category ->
            SpendingGoal(
                categoryId = category.id,
                categoryName = category.name,
                minAmount = category.minGoal,
                maxAmount = category.maxGoal,
                currentAmount = spending[category.name] ?: 0.0
            )
        }
        _spendingGoals.value = goals
    }
}
