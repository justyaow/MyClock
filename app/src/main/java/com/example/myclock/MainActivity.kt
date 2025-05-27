package com.example.myclock

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val bottomTabView = findViewById<BottomNavigationView>(R.id.bottomTab)

        loadView(clockPage())
        bottomTabView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.clockPage -> loadView(clockPage())
                R.id.alertPage -> loadView(alertPage())
                R.id.countPage -> loadView(countPage())
                else -> false
            }
        }
    }

    private fun loadView(view: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.contentFrame, view).commit()
        return true
    }
}