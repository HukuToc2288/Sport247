package ru.hukutoc2288.sport247

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SportSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sport_select)
    }

    fun onFootballCardClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Sports.EXTRA_NAME,Sports.FOOTBALL)
        startActivity(intent)
    }

    fun onBasketballCardClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Sports.EXTRA_NAME,Sports.BASKETBALL)
        startActivity(intent)
    }
}