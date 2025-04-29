package com.example.model1
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class broad: AppCompatActivity() {

    private lateinit var airplaneModeReceiver: BroadcastReceiver
    private lateinit var statusTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broad)

        // Initialize the TextView
        statusTextView = findViewById(R.id.statusTextView)

        // Create the receiver to listen for airplane mode changes
        airplaneModeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // Check if the airplane mode status has changed
                if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    val isAirplaneModeOn = intent.getBooleanExtra("state", false)

                    // Update the TextView to reflect the airplane mode status
                    val status = if (isAirplaneModeOn) {
                        "Airplane Mode: ON"
                    } else {
                        "Airplane Mode: OFF"
                    }

                    // Update UI (TextView)
                    statusTextView.text = status

                    // Show a Toast message as well
                    Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Set up the filter to listen for airplane mode changes
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneModeReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister the receiver to avoid memory leaks
        try {
            unregisterReceiver(airplaneModeReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }
}