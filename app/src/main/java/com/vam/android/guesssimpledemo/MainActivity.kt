package com.vam.android.guesssimpledemo

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.GridView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vam.android.guesssimpledemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var ISREPEAT:Boolean = false

    private lateinit var activityMainBinding:ActivityMainBinding
    private lateinit var guessViewModel: GuessViewModel
    private lateinit var dialog: ColorPickerDialog
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }

        guessViewModel = ViewModelProvider(this)[GuessViewModel::class.java]
        val colorList = guessViewModel.colorList
        var guessList = guessViewModel.resetNotRepeat(ISREPEAT)



        button1 = activityMainBinding.button1
        button2 = activityMainBinding.button2
        button3 = activityMainBinding.button3
        button4 = activityMainBinding.button4

        setupButton(button1,0)
        setupButton(button2,1)
        setupButton(button3,2)
        setupButton(button4,3)


        with(activityMainBinding) {
            switch1.setOnCheckedChangeListener { _, isChecked ->
                ISREPEAT = isChecked
            }
            buttonReset.setOnClickListener {
                guessList = guessViewModel.resetNotRepeat(ISREPEAT)
                cheat.text = guessViewModel.cheat(guessList).toString()+ guessList
            }
            buttonSubmit.setOnClickListener {
                val (fullpass,halfpass) = guessViewModel.direct2(guessList,colorList)

                textView.text = "$fullpass 个全对,+$halfpass 个半对"
                debug.text = guessViewModel.cheat(colorList).toString()+colorList
                if(fullpass<4){ }else{
                    AlertDialog.Builder(this@MainActivity).apply {
                        setTitle("恭喜你通关")
                        setMessage("全部颜色已经猜中，请重新开始")
                        setCancelable(true)
                        setPositiveButton("重新开始") { _, _ ->
                            guessList =guessViewModel.resetNotRepeat(ISREPEAT)
                            cheat.text = guessViewModel.cheat(guessList).toString()+ guessList
                        }
                        show()
                    }
                }
            }
        }
    }

    /**
     * 初始化Button函数，复用
     * @param button Button
     * @param index Int
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun setupButton(button: Button, index: Int) {
        button.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 显示 ColorPickerDialog
                    dialog = ColorPickerDialog(this, button, guessViewModel.colorMapping, event.rawX, event.rawY)
                    dialog.show()
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val location = IntArray(2)
                    dialog.getGridView().getLocationOnScreen(location)
                    val x = event.rawX.toInt() - location[0]
                    val y = event.rawY.toInt() - location[1]
                    val position = dialog.getGridView().pointToPosition(x, y)
                    if (position != GridView.INVALID_POSITION) {
                        val selectedColor = dialog.getColors()[position]
                        button.setBackgroundColor(selectedColor)
                        button.invalidate()
                        guessViewModel.colorList[index] = position + 1 // 更新选择颜色list
                    }
                    if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                        dialog.dismiss()
                    }
                    return@setOnTouchListener true
                }
            }
            false
        }
    }


}