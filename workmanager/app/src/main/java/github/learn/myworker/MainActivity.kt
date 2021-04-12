package github.learn.myworker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import github.learn.myworker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDownload.setOnClickListener {
            startWorker()
        }
    }

    private fun startWorker() {
        val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .build()

        val workRequest = OneTimeWorkRequestBuilder<MainActivityWorker>()
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(this).enqueue(workRequest)

        Toast.makeText(this, "WorkManager Request Made", Toast.LENGTH_SHORT).show()
    }
}