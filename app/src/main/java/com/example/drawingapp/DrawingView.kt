package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.* //faz com que possamos utilizar todos os imports de android.graphics
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet.Motion
import androidx.core.graphics.toColorInt

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs){

    private var mDrawPath: CustomPath? = null
    private var mCanvasBitmap: Bitmap? = null
    private var mDrawPaint: Paint? = null
    private var mCanvasPaint: Paint? = null
    private var mBrushSize: Float = 0f
    private var mColor = Color.BLACK
    private var mCanvas: Canvas? = null
    private var mPaths = ArrayList<CustomPath>() //cria um array que irá guardar os dados ddas linhas desenhadas
    private var mUndoPaths = ArrayList<CustomPath>()

    init{
        setUpDrawing()
    }

    fun onClickUndo(){
        if(mPaths.size > 0){
            mUndoPaths.add(mPaths.removeAt(mPaths.size - 1)) //recebe o ultimo traço antes do atual
            invalidate()
        }
    }

    fun onClickRedo(){
        if(mUndoPaths.size > 0){
            mPaths.add(mUndoPaths.removeAt(mUndoPaths.size - 1)) //processo contrário
            invalidate()
        }
    }

    private fun setUpDrawing(){
        mDrawPaint = Paint()
        mDrawPath = CustomPath(mColor, mBrushSize)
        mDrawPaint!!.color = mColor
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawPaint!!.strokeCap = Paint.Cap.ROUND
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mCanvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)

        for(path in mPaths){ //fixa os traços no quadro
            mDrawPaint!!.strokeWidth = path.brushTam
            mDrawPaint!!.color = path.color
            canvas.drawPath(path, mDrawPaint!!)

        }

        if(!mDrawPath!!.isEmpty){
            mDrawPaint!!.strokeWidth = mDrawPath!!.brushTam
            mDrawPaint!!.color = mDrawPath!!.color
            canvas.drawPath(mDrawPath!!, mDrawPaint!!)

        }

    }



    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                mDrawPath!!.color = mColor
                mDrawPath!!.brushTam = mBrushSize

                mDrawPath!!.reset()
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.moveTo(touchX, touchY)
                    }
                }

            }
            MotionEvent.ACTION_MOVE ->{
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.lineTo(touchX, touchY)
                    }
                }
            }
            MotionEvent.ACTION_UP ->{
                mPaths.add(mDrawPath!!) //adiciona o traço criado mPaths, ou seja, em um array
                mDrawPath = CustomPath(mColor, mBrushSize)
            }
            else -> return false
        }
        invalidate()

        return true
    }

    fun setBrushSize(newSize: Float){ //faz com que a mudança de tamanho do pincel seja mais fácil de alterar, ao chamar a função na "MainActivity"
        mBrushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, resources.displayMetrics)
        mDrawPaint!!.strokeWidth = mBrushSize
    }

    fun setBrushColor(newColor: Int){
        mColor = newColor //recebe o valor da cor e armazena
        mDrawPaint!!.color = mColor //recebe o valor armazenado
    }


    internal inner class CustomPath(var color: Int, var brushTam: Float): Path(){

    } //fim do CustomPath //

}