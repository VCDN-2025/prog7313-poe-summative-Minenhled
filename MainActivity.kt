package vcmsa.projects.expensetracker

import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import vcmsa.projects.expensetracker.adapters.ExpenseAdapter
import vcmsa.projects.expensetracker.databinding.ActivityMainBinding
import vcmsa.projects.expensetracker.viewmodel.ExpenseViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ExpenseViewModel by viewModels()
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Check if user is logged in, otherwise sign in anonymously
        if (auth.currentUser == null) {
            signInAnonymously()
        } else {
            loadInitialData()
        }

        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun signInAnonymously() {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show()
                loadInitialData()
            } else {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadInitialData() {
        viewModel.loadExpenses()
        viewModel.loadCategories()
    }

    private fun setupRecyclerView() {
        expenseAdapter = ExpenseAdapter { expense ->
            // Handle expense click (delete)
            viewModel.deleteExpense(expense.id)
        }

        binding.recyclerViewExpenses.apply {
            adapter = expenseAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupObservers() {
        viewModel.expenses.observe(this) { expenses ->
            expenseAdapter.submitList(expenses)
            updateTotalSpending(expenses)
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.fabAddExpense.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        binding.btnViewGraphs.setOnClickListener {
            startActivity(Intent(this, GraphActivity::class.java))
        }

        binding.btnManageCategories.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        binding.btnSpendingGoals.setOnClickListener {
            startActivity(Intent(this, SpendingGoalsActivity::class.java))
        }
    }


    // Alternative with additional safety checks:
    private fun updateTotalSpendingSafe(expenses: List<vcmsa.projects.expensetracker.data.models.Expense>) {
        val total = expenses.sumOf { expense ->
            expense.amount.takeIf { it >= 0 } ?: 0.0
        }
        binding.textViewTotalSpending.text = NumberFormat.getCurrencyInstance().format(total)
    }


    override fun onResume() {
        super.onResume()
        viewModel.loadExpenses()
    }

}

