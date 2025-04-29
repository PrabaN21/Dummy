package com.example.model1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.model1.MapActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val button=findViewById<Button>(R.id.ebutton)
        button.setOnClickListener{
            val explicit= Intent(this,MainActivity2::class.java)
            startActivity(explicit)
        }

        val button2=findViewById<Button>(R.id.implicitbutton)
        button2.setOnClickListener{
            val button2=Intent(this,notifyact::class.java)
            startActivity(button2)
        }
        val button3=findViewById<Button>(R.id.button3)
        button3.setOnClickListener{
            val intent= Intent(this,MainActivity3::class.java)
            startActivity(intent)
        }
        val button4=findViewById<Button>(R.id.quizbutton)
        button4.setOnClickListener{
            val button4=Intent(this,QuizActivity::class.java)
            startActivity(button4)
        }
        val hardware=findViewById<Button>(R.id.hardware)
        hardware.setOnClickListener{
            val hardware=Intent(this,Hardware::class.java)
            startActivity(hardware)
        }
        val multimedia=findViewById<Button>(R.id.multimedia)
        multimedia.setOnClickListener{
            val multimedia=Intent(this,Multimedia::class.java)
            startActivity(multimedia)
        }
        val broadd=findViewById<Button>(R.id.broadd)
        broadd.setOnClickListener{
            val broad=Intent(this,broad::class.java)
            startActivity(broad)
        }

        val sqlbutton=findViewById<Button>(R.id.sqlbutton)
        sqlbutton.setOnClickListener{
            val sqlbutton=Intent(this,sql::class.java)
            startActivity(sqlbutton)
        }

        val openMapButton = findViewById<Button>(R.id.openMap)
        openMapButton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        val shareBtn = findViewById<Button>(R.id.btnShare)
        shareBtn.setOnClickListener {
            val intent = Intent(this, ShareActivity::class.java)
            startActivity(intent)
        }




    }
}