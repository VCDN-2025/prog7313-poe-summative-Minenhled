package vcmsa.projects.expensetracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import vcmsa.projects.expensetracker.data.models.Expense
import vcmsa.projects.expensetracker.databinding.ActivityAddExpenseBinding
import vcmsa.projects.expensetracker.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Suppress("DEPRECATION")
class AddExpenseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddExpenseBinding
    private val viewModel: ExpenseViewModel by viewModels()
    private var selectedDate: Date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupDatePicker()
        setupCategorySpinner()
        setupObservers()
        setupClickListeners()

        viewModel.loadCategories()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_expense)
    }

    private fun setupDatePicker() {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        binding.textViewSelectedDate.text = dateFormat.format(selectedDate)

        binding.btnSelectDate.setOnClickListener {
            val calendar = Calendar.getInstance().apply { time = selectedDate }

            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    selectedDate = calendar.time
                    binding.textViewSelectedDate.text = dateFormat.format(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupCategorySpinner() {
        val categories = listOf(
            getString(R.string.category_food_dining),
            getString(R.string.category_transportation),
            getString(R.string.category_shopping),
            getString(R.string.category_entertainment),
            getString(R.string.category_bills_utilities),
            getString(R.string.category_healthcare),
            getString(R.string.category_education),
            getString(R.string.category_travel),
            getString(R.string.category_other)
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter
    }

    private fun setupObservers() {
        // Observe loading state and error message from the ViewModel
        viewModel.loading.observe(this) { isLoading ->
            binding.btnSaveExpense.isEnabled = !isLoading
            binding.btnSaveExpense.text = if (isLoading) getString(R.string.saving) else getString(R.string.save_expense)
        }

        viewModel.error.observe(this) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnSaveExpense.setOnClickListener {
            saveExpense()
        }
    }

    private fun saveExpense() {
        val title = binding.editTextTitle.text.toString().trim()
        val amountText = binding.editTextAmount.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val category = binding.spinnerCategory.selectedItem?.toString() ?: ""

        if (title.isEmpty()) {
            binding.editTextTitle.error = getString(R.string.error_title_required)
            binding.editTextTitle.requestFocus()
            return
        }

        if (amountText.isEmpty()) {
            binding.editTextAmount.error = getString(R.string.error_amount_required)
            binding.editTextAmount.requestFocus()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null) {
            binding.editTextAmount.error = getString(R.string.error_invalid_amount)
            binding.editTextAmount.requestFocus()
            return
        }

        if (amount <= 0) {
            binding.editTextAmount.error = getString(R.string.error_amount_positive)
            binding.editTextAmount.requestFocus()
            return
        }

        val expense = Expense(
            id = "", // Backend generates ID
            title = title,
            amount = amount,
            category = category,
            description = description,
            date = Timestamp(selectedDate),
            createdAt = Timestamp.now()
        )

        viewModel.addExpense(expense)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
