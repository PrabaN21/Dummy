package com.example.model1

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class notifyact : AppCompatActivity() {

    private var notificationShown = false // Track if notification has been shown
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your app.
                showFeatureNotification()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their decision.
                // You might show a dialog or a snackbar explaining the situation.
                // For this example, we'll just log a message.
                println("Notification permission denied. Feature notification not shown.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notify)

        val showNotificationButton: Button = findViewById(R.id.showNotificationButton)
        showNotificationButton.setOnClickListener {
            if (!notificationShown) {
                checkAndShowNotification()
                notificationShown = true
            }
        }
    }

    private fun checkAndShowNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is already granted
                showFeatureNotification()
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // Provide an additional rationale to the user if the permission was
                // not granted and the user would benefit from additional context for the
                // use of the permission. For example, if the user has turned down the
                // permission request previously.
                // In this example, we'll just request the permission again.  In a real app,
                // you'd show a dialog or a snackbar explaining why the permission is needed.
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            // Permissions are granted at install time on older Android versions.
            showFeatureNotification()
        }
    }

    private fun showFeatureNotification() {
        // Create a notification channel (required for Android 8.0 and higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "feature_notifications"
            val channelName = "Feature Notifications"
            val channelDescription = "Notifications for new app features"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val builder = NotificationCompat.Builder(this, "feature_notifications")
            .setSmallIcon(R.drawable.ic_notifications) // Replace with your icon.  Ensure it exists in res/drawable!
            .setContentTitle("New Feature: Exciting Update!")
            .setContentText("We've added a fantastic new feature to enhance your experience. Tap to learn more!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Display the notification
        try {
            with(NotificationManagerCompat.from(this)) {
                notify(1, builder.build()) // Use a unique ID for each notification
            }
        } catch (e: SecurityException) {
            // Handle the case where the permission was revoked after the check.
            println("SecurityException: Notification permission might have been revoked.  Notification not shown.")
        }
    }
}