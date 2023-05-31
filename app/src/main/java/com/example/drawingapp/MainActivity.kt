package com.example.drawingapp

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView

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
        //todo:
        // 1 - salvar informação do seekbar
        // 2 - atualizar valor do tamanho do brush no textview

        brushDialog.window?.setLayout(600, 560)


        val sbBrushPicker: SeekBar = brushDialog.findViewById(R.id.sb_brush_size_picker)
        sbBrushPicker.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var startSize = 0
            var endSize = 0
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                drawingView?.setBrushSize(progress.toFloat())

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    startSize = seekBar.progress
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    endSize = seekBar.progress
                }
            }

        })

        val btnChoose = brushDialog.findViewById<Button>(R.id.enter)
        btnChoose.setOnClickListener{
            brushDialog.dismiss()
        }

        brushDialog.show()
    }

}