package vcmsa.projects.expensetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.expensetracker.databinding.ActivityGraphBinding

class GraphActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGraphBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Graphs & Analytics"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
