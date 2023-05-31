package com.example.drawingapp

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private var drawingView: DrawingView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setBrushSize(7f)

        val brushPicker: ImageButton = findViewById(R.id.btn_brushSizePicker)
        brushPicker.setOnClickListener{
            displayDialog()
        }
    }

    fun displayDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        //todo: tentar utilizar "when", para ver se fica mais "bonito"
        val smallbtn = brushDialog.findViewById<ImageButton>(R.id.ib_small_brush)
        smallbtn.setOnClickListener{
            drawingView?.setBrushSize(3f)
            brushDialog.dismiss()
        }
        val mediumbtn = brushDialog.findViewById<ImageButton>(R.id.medium_brush)
        mediumbtn.setOnClickListener{
            drawingView?.setBrushSize(15f)
            brushDialog.dismiss()
        }
        val extrabtn = brushDialog.findViewById<ImageButton>(R.id.extra_brush)
        extrabtn.setOnClickListener{
            drawingView?.setBrushSize(30f)
            brushDialog.dismiss()
        }
        brushDialog.show()
    }

}