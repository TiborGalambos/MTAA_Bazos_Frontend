package com.example.myapplication.activityLogic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_added_item.*

class added_item : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_added_item)
        supportActionBar?.hide()


        back_to_main.setOnClickListener{

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
    }
}