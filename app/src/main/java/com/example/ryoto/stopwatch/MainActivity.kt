package com.example.ryoto.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.*
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // 1度だけ代入するものはvalを使う
    val handler = Handler()
    // 繰り返し代入するためvarを使う
    var timeValue = 0
    var running = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // View要素を変数に代入
        val timeText = findViewById(R.id.timeText) as TextView
        val startButton = findViewById(R.id.start) as Button
        val stopButton = findViewById(R.id.stop) as Button
        val resetButton = findViewById(R.id.reset) as Button

        val runnable = object : Runnable {
            override fun run() {
                timeValue++
                // TextViewを更新
                // ?.letを用いて、nullではない場合のみ更新
                timeToText(timeValue)?.let {
                    // timeToText(timeValue)の値がlet内ではitとして使える
                    timeText.text = it
                }
                handler.postDelayed(this, 1000)
            }
        }


        // start
        startButton.setOnClickListener {
            if( !running ) {
                handler.post(runnable)
                running = true
            }
        }

        // stop
        stopButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            running = false
        }


        // reset
        resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 0
            // timeToTextの引数はデフォルト値が設定されているので、引数省略できる
            timeToText()?.let {
                timeText.text = it
            }
            running = false
        }
    }
    private fun timeToText(time: Int = 0): String? {
        // if式は値を返すため、そのままreturnできる
        return if (time < 0) {
            null
        } else if (time == 0) {
            "00:00:00"
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }
}
