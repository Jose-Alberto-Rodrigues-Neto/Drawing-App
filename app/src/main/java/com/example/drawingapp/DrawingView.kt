package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.* //faz com que possamos utilizar todos os imports de android.graphics
import android.util.AttributeSet
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs){

    private var mDrawPath: CustomPath? = null
    private var mCanvasBitmap: Bitmap? = null
    private var mDrawPaint: Paint? = null
    private var mCanvasPaint: Paint? = null
    private var mBrushSize: Float = 0f
    private var mColor = Color.BLACK
    private var mCanvas: Canvas? = null


    private fun setUpDrawing(){
        mDrawPaint = Paint()
        mDrawPath = CustomPath(mColor, mBrushSize)
        mDrawPaint!!.color = mColor
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawPaint!!.strokeCap = Paint.Cap.ROUND
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        mBrushSize = 20f
    }

    init{
        setUpDrawing()
    }

    internal inner class CustomPath(var color: Int, var brushTam: Float): Path(){

    } //fim do CustomPath //

}