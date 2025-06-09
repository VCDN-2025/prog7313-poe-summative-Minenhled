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
//🔗 Android Developer Portal  
//   https://developer.android.com  
//   (Official documentation, API guides, and Jetpack libraries)
//
//🔗 Android Studio User Guide  
//   https://developer.android.com/studio/intro  
//   (Installation, setup, Gradle, and emulator instructions)
//
//🔗 Android Codelabs  
//   https://developer.android.com/codelabs  
//   (Hands-on tutorials for Room, ViewModel, Compose, etc.)
//
//🔗 Android API Reference  
//   https://developer.android.com/reference  
//   (Full SDK API list for Java & Kotlin)
//
//🔗 Stack Overflow - Android  
//   https://stackoverflow.com/questions/tagged/android  
//   (Community Q&A, error troubleshooting)
//
//🔗 Android GitHub Samples  
//   https://github.com/android  
//   (Official sample projects for Android features)
//
//🔗 Kotlin Playground  
//   https://play.kotlinlang.org  
//   (Practice Kotlin syntax online)
//
//🔗 JetBrains Academy  
//   https://www.jetbrains.com/academy/  
//   (Courses for Kotlin, Java, Android development)


