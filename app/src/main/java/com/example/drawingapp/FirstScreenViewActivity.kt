package com.example.drawingapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FirstScreenViewActivity : AppCompatActivity() {
    private var startDrawing: Button? = null
    var canvasH: String = "584"
    var canvasW: String = "333"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen_view)

        startDrawing = findViewById(R.id.startApp)
        startDrawing?.setOnClickListener{
            displayWindowSize()
        }
    }

    private fun displayWindowSize(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_canva_size)
        dialog.window?.setLayout(600, 560) //setando o tamanho da tela do dialog
        val canvasHeight: EditText = findViewById(R.id.canvasHeight)
        val canvasWidth: EditText = findViewById(R.id.canvasWidth)
        val enter: Button = findViewById(R.id.enter_canvasProperties)
        enter.setOnClickListener{
            canvasH = canvasHeight.text.toString()
            canvasW = canvasWidth.text.toString()
            dialog.dismiss()

        }
        val cancel: Button = findViewById(R.id.cancel_canvasProperties)
        cancel.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()

    }
}