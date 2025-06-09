package vcmsa.projects.expensetracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vcmsa.projects.expensetracker.data.models.Expense
import vcmsa.projects.expensetracker.databinding.ItemExpenseBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseAdapter(
    private val onExpenseClick: (Expense) -> Unit
) : ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ExpenseViewHolder(
        private val binding: ItemExpenseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: Expense) {
            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

            binding.apply {
                textViewAmount.text = "$${String.format("%.2f", expense.amount)}"
                textViewCategory.text = expense.category
                textViewDescription.text = expense.description
                textViewDate.text = dateFormat.format(expense.getDateAsDate())

                root.setOnLongClickListener {
                    onExpenseClick(expense)
                    true
                }
            }
        }
    }

    private class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
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
