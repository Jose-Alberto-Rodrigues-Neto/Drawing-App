package com.example.drawingapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton

class FirstScreenViewActivity : AppCompatActivity() {
    private var changeCanvasOptions: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen_view)

        changeCanvasOptions = findViewById(R.id.changeOptions)
        changeCanvasOptions?.setOnClickListener{
            displayWindowSize()

        }
    }

    private fun displayWindowSize(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_canva_size)
        dialog.window?.setLayout(1000, 1100) //setando o tamanho da tela do dialog

        val canvasHeight: EditText = dialog.findViewById(R.id.canvasHeight) //pegando valor da altura do canvas
        val canvasWidth: EditText = dialog.findViewById(R.id.canvasWidth) //pegando valor da largura do canvas

        val enter: Button = dialog.findViewById(R.id.enter_canvasProperties)
        enter.setOnClickListener{
            val intent = Intent(this, MainActivity :: class.java)
            intent.putExtra(CanvasProperties.canvasH, canvasHeight.text.toString())
            intent.putExtra(CanvasProperties.canvasW, canvasWidth.text.toString())
            startActivity(intent)
            dialog.dismiss()
            finish()
        }
        val cancel: Button = dialog.findViewById(R.id.cancel_canvasProperties)
        cancel.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()

    }

}