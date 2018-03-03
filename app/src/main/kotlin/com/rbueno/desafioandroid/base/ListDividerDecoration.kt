package com.rbueno.desafioandroid.base

import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.applyDimension
import com.rbueno.desafioandroid.R

class ListDividerDecoration(val context: Context) : RecyclerView.ItemDecoration() {

    private val divider = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        context.resources.getDrawable(R.drawable.line_divider, null)
    else
        context.resources.getDrawable(R.drawable.line_divider)

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {

        if (parent != null) {
            val left = applyDimension(COMPLEX_UNIT_DIP, 20F, context.resources.displayMetrics)
            val right = parent.width - parent.paddingRight
            val childCount = parent.childCount

            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin
                val bottom = top + divider.intrinsicHeight

                divider.setBounds(left.toInt(), top, right, bottom)
                divider.draw(c)
            }
        }

    }
}