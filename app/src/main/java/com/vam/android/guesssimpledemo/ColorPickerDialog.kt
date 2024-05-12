package com.vam.android.guesssimpledemo

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridView


// 弹窗
class ColorPickerDialog(
    context: Context,
    private val button: Button,
    private val colors: IntArray,
    private val x: Float,
    private val y: Float
) : Dialog(context) {
    private lateinit var gridView: GridView
    private var selectedColor: Int = colors[0]
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_color_picker)
        window?.apply {
            setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP) // 设置 Dialog 在屏幕X方向中间
            attributes = attributes.apply {
                y = this@ColorPickerDialog.y.toInt()
            }
            //Dialog 美化，配合corner round_shape.xml实现 圆角矩形弹窗，更符合现代设计美观
            //Dialog background 设置为round_shape
            //黑暗模式如何适配？
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }

        gridView = findViewById(R.id.gridView)
        gridView.adapter = ColorAdapter(context, colors)
        gridView.setOnTouchListener { _, event ->
            val position = gridView.pointToPosition(event.x.toInt(), event.y.toInt())
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    return@setOnTouchListener false
                }

                MotionEvent.ACTION_MOVE -> {
                    Log.d("ColorPickerDialog", "ACTION_MOVE triggered, position: $position")
                    if (position != GridView.INVALID_POSITION) {
                        selectedColor = getColorAtPosition(event.x.toInt(), event.y.toInt())
                        button.setBackgroundColor(selectedColor)
                        button.invalidate()
                        Log.d(
                            "ColorPickerDialog",
                            "ACTION_MOVE valid, position: $position, selectedColor: $selectedColor"
                        )
                    }
                    return@setOnTouchListener false
                }

                MotionEvent.ACTION_UP -> {
                    Log.d("ColorPickerDialog", "ACTION_UP triggered, position: $position")
                    if (position != GridView.INVALID_POSITION && event.x >= 0 && event.y >= 0 && event.x < gridView.width && event.y < gridView.height) {
                        selectedColor = getColorAtPosition(event.x.toInt(), event.y.toInt())
                        Log.d(
                            "ColorPickerDialog",
                            "ACTION_UP valid, position: $position, selectedColor: $selectedColor"
                        )
                    } else {
                        Log.d("ColorPickerDialog", "ACTION_UP invalid, position: $position")
                    }
                    dismiss()
                    return@setOnTouchListener false
                }
            }
            false
        }
    }

    fun getColorAtPosition(x: Int, y: Int): Int {
        val position = gridView.pointToPosition(x, y)
        if (position != GridView.INVALID_POSITION && position < colors.size) {
            return colors[position]
        }
        return Color.TRANSPARENT
    }

    fun getColors(): IntArray {
        return colors
    }

    fun getGridView(): GridView {
        return gridView
    }
}