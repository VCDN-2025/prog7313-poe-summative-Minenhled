package vcmsa.projects.expensetracker.utils

import androidx.annotation.DrawableRes
import vcmsa.projects.expensetracker.R

object CategoryIcons {

    private val categoryIconMap = mapOf(
        "Food & Dining" to R.drawable.ic_restaurant,
        "Transportation" to R.drawable.ic_directions_car,
        "Shopping" to R.drawable.ic_shopping_cart,
        "Entertainment" to R.drawable.ic_movie,
        "Bills & Utilities" to R.drawable.ic_receipt,
        "Healthcare" to R.drawable.ic_local_hospital,
        "Education" to R.drawable.ic_school,
        "Travel" to R.drawable.ic_flight,
        "Groceries" to R.drawable.ic_local_grocery_store,
        "Gas & Fuel" to R.drawable.ic_local_gas_station,
        "Insurance" to R.drawable.ic_security,
        "Rent/Mortgage" to R.drawable.ic_home,
        "Personal Care" to R.drawable.ic_face,
        "Gifts & Donations" to R.drawable.ic_card_giftcard,
        "Other" to R.drawable.ic_more_horiz
    )

    @DrawableRes
    fun getIconForCategory(category: String): Int {
        return categoryIconMap[category] ?: R.drawable.ic_more_horiz
    }

    fun getAllCategories(): List<String> {
        return categoryIconMap.keys.toList()
    }
}