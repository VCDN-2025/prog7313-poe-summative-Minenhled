package vcmsa.projects.expensetracker.utils

import android.view.View
import android.widget.ProgressBar

object LoadingUtils {

    fun showLoading(progressBar: ProgressBar, vararg viewsToHide: View) {
        progressBar.visibility = View.VISIBLE
        viewsToHide.forEach { it.visibility = View.GONE }
    }

    fun hideLoading(progressBar: ProgressBar, vararg viewsToShow: View) {
        progressBar.visibility = View.GONE
        viewsToShow.forEach { it.visibility = View.VISIBLE }
    }
}