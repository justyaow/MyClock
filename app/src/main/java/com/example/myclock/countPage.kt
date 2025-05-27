package com.example.myclock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.os.SystemClock

class countPage : Fragment() {
    private lateinit var timeTextView: TextView
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var ok: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.countpage, container, false)

        timeTextView = view.findViewById(R.id.timeTextView)
        startButton = view.findViewById(R.id.startButton)
        resetButton = view.findViewById(R.id.resetButton)

        startButton.setOnClickListener { onCount() }
        resetButton.setOnClickListener { onReset() }
        return view
    }
    private fun onCount() {
        if (!ok) {
            startTime = SystemClock.elapsedRealtime() - elapsedTime
            ok = true
            startButton.text = "暂停"
            startChronometer()
        } else {
            elapsedTime = SystemClock.elapsedRealtime() - startTime
            ok = false
            startButton.text = "继续"
        }
    }

    private fun onReset() {
        elapsedTime = 0
        ok = false
        startButton.text = "开始"
        timeTextView.text = "00:00:00"
    }

    private fun startChronometer() {
        val chronometerThread = Thread {
            while (ok) {
                try {
                    Thread.sleep(100)
                    elapsedTime = SystemClock.elapsedRealtime() - startTime
                    val formattedTime = formatTime(elapsedTime)
                    activity?.runOnUiThread { timeTextView.text = formattedTime }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        chronometerThread.start()
    }

    private fun formatTime(millis: Long): String {
        val hours = millis / (1000 * 60 * 60)
        val minutes = (millis / (1000 * 60)) % 60
        val seconds = (millis / 1000) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}