package com.aman.ottdisplay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        Toast.makeText(this, intent.getStringExtra("genre"),Toast.LENGTH_SHORT).show()
    }
}