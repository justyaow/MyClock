package com.example.myclock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*
import java.text.SimpleDateFormat

class clockPage : Fragment() {
    private lateinit var timelabel: TextView
    private lateinit var timeThread: Thread
    private var ok: Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.clockpage, container, false)
        timelabel = view.findViewById(R.id.timeLabel)
        initThread()

        return view
    }
    private fun initThread(): Unit {
        timeThread = Thread {
            try {
                while (true) {
                    updateTime()
                    Thread.sleep(1000)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        timeThread.start()
    }

    private fun updateTime(): Unit {
        activity?.runOnUiThread {
            getTime()
        }
    }

    private fun getTime(): Unit {
        val currentTime = Calendar.getInstance().time
        val timeResult = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        timelabel.text = timeResult.format(currentTime)
    }

    override fun onDestroy() {
        super.onDestroy()
        ok = false
        timeThread.interrupt()
    }
}