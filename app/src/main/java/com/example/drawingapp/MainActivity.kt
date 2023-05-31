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
    private var textBrushSize: TextView? = null
    var startSize = 7
    var endSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setBrushSize(startSize.toFloat())

        val brushPicker: ImageButton = findViewById(R.id.btn_brushSizePicker)
        brushPicker.setOnClickListener{
            displayDialog()

        }


    }


    fun displayDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.window?.setLayout(600, 560) //setando o tamanho da tela do dialog

        //setando o texto que informa o tamanho do brush
        textBrushSize =  brushDialog.findViewById(R.id.sizeBrush)
        textBrushSize?.text = startSize.toString() //será o texto do textview toda vez que o usuário clicar no display, mostrando o valor deixado no textview quando selecionado anteriormente


        val sbBrushPicker: SeekBar = brushDialog.findViewById(R.id.sb_brush_size_picker) //começa o objeto seekbar
        sbBrushPicker.progress = startSize //será o posicionamento do seekbar toda vez que o usuário clicar no display, mostrando o valor deixado na seekbar quando selecionado anteriormente
        sbBrushPicker.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                drawingView?.setBrushSize(progress.toFloat()) //muda o tamanho do brush
                textBrushSize?.text = progress.toString() //muda o valor do textview em cima do seekbar

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    startSize = seekBar.progress //declara o valor do startSize como o valor que é disposto ao iniciar a ação do dialog
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    endSize = seekBar.progress
                    startSize = endSize //declara o valor que é recebido pelo progresso do sbBrushPicker seja salvo e setado para que na próxima vez ele continue de onde parou
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