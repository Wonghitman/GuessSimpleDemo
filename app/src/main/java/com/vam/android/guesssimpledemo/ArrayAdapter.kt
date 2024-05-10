package com.vam.android.guesssimpledemo

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class ColorAdapter(context: Context, private val colors: IntArray) : ArrayAdapter<Int>(context, 0, colors.toList()) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //dialog 中颜色形状
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.color_item, parent, false)
        val oval = ShapeDrawable(OvalShape())
        oval.paint.color = colors[position]
        view.background = oval

        return view
    }
}