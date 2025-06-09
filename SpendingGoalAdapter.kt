package vcmsa.projects.expensetracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vcmsa.projects.expensetracker.data.models.SpendingGoal
import vcmsa.projects.expensetracker.databinding.ItemSpendingGoalBinding

class SpendingGoalAdapter(
    private val onClick: ((SpendingGoal) -> Unit)? = null
) : ListAdapter<SpendingGoal, SpendingGoalAdapter.GoalViewHolder>(SpendingGoalDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val binding = ItemSpendingGoalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GoalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GoalViewHolder(private val binding: ItemSpendingGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClick?.invoke(getItem(position))
                }
            }
        }

        fun bind(goal: SpendingGoal) = with(binding) {
            textViewCategory.text = goal.category
            textViewMinGoal.text = goal.min.toString()
            textViewMaxGoal.text = goal.max.toString()
            // Optionally highlight if current spending out of bounds
        }
    }

    private class SpendingGoalDiffCallback : DiffUtil.ItemCallback<SpendingGoal>() {
        override fun areItemsTheSame(oldItem: SpendingGoal, newItem: SpendingGoal): Boolean {
            // Assuming SpendingGoal has an id field or use category as unique identifier
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(oldItem: SpendingGoal, newItem: SpendingGoal): Boolean {
            return oldItem == newItem
        }
    }
}

class ItemSpendingGoalBinding {

}
