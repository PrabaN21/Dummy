package com.example.model1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ShareActivity : AppCompatActivity() {

    private lateinit var phoneEditText: EditText
    private lateinit var messageEditText: EditText
    private lateinit var whatsappBtn: Button
    private lateinit var smsBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        phoneEditText = findViewById(R.id.editPhone)
        messageEditText = findViewById(R.id.editMessage)
        whatsappBtn = findViewById(R.id.btnWhatsApp)
        smsBtn = findViewById(R.id.btnSMS)

        whatsappBtn.setOnClickListener {
            val phone = phoneEditText.text.toString().trim()
            val message = messageEditText.text.toString()

            if (phone.isNotEmpty() && message.isNotEmpty()) {
                shareViaWhatsApp(phone, message)
            } else {
                Toast.makeText(this, "Enter both phone and message", Toast.LENGTH_SHORT).show()
            }
        }

        smsBtn.setOnClickListener {
            val phone = phoneEditText.text.toString().trim()
            val message = messageEditText.text.toString()

            if (phone.isNotEmpty() && message.isNotEmpty()) {
                shareViaSMS(phone, message)
            } else {
                Toast.makeText(this, "Enter both phone and message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun shareViaWhatsApp(phone: String, message: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = Uri.parse("https://wa.me/$phone?text=" + Uri.encode(message))
            intent.data = uri
            intent.setPackage("com.whatsapp")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "WhatsApp not installed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareViaSMS(phone: String, message: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:$phone")
        intent.putExtra("sms_body", message)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to send SMS", Toast.LENGTH_SHORT).show()
        }
    }
}
