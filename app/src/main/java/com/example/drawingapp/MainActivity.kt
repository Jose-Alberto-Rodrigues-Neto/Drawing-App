package com.example.drawingapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import yuku.ambilwarna.AmbilWarnaDialog


class MainActivity : AppCompatActivity() {

    private var canvasName: Button? = null
    private var drawingView: DrawingView? = null
    private var textBrushSize: TextView? = null
    var startSize = 7
    var endSize = 0
    private var defaultColor = 0
    private var btnColorPicker: ImageButton? = null
    private var btnBrushPicker: ImageButton? = null
    private var btnBrushPathUndo: ImageButton? = null
    private var btnBrushPathRedo: ImageButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setBrushSize(startSize.toFloat())

        //setando um brush/pincel
        btnBrushPicker= findViewById(R.id.btn_brushSizePicker)
        btnBrushPicker?.setOnClickListener{
            displayDialogBrushSizePicker()

        }

        //setando color picker
        btnColorPicker = findViewById(R.id.btn_brushColorPicker)
        btnColorPicker?.setOnClickListener{
            displayDialogBrushColorPicker()

        }

        //setando o botão de desfazer
        btnBrushPathUndo = findViewById(R.id.btn_undo)
        btnBrushPathUndo?.setOnClickListener {
            drawingView?.onClickUndo()
        }

        //setando o botão refazer
        btnBrushPathRedo = findViewById(R.id.btn_redo)
        btnBrushPathRedo?.setOnClickListener {
            drawingView?.onClickRedo()
        }

        //fazendo o nome do Canvas mudar
        canvasName = findViewById(R.id.tv_canvasName)
        canvasName?.setOnClickListener{
            displayDialogChangeCanvasName()
        }

    }


    private fun displayDialogBrushSizePicker(){
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

    private fun displayDialogBrushColorPicker(){
        //criando um objeto com a implementação AmbilWaenaDialog, para setar o brushPicker
        val colorPickerDialogue = AmbilWarnaDialog(this, defaultColor,
            object : AmbilWarnaDialog.OnAmbilWarnaListener {
                override fun onCancel(dialog: AmbilWarnaDialog?) {
                    //não precisa colocar nada aqui
                }

                override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                    defaultColor = color //valor da cor escolhida

                    btnColorPicker?.setBackgroundColor(defaultColor) //fazendo com que o valor da cor escolhida seja utilizada no background do botão

                    drawingView?.setBrushColor(defaultColor)
                }
            })
        colorPickerDialogue.show()
    }

    private fun displayDialogChangeCanvasName() {
        val nameDialog = Dialog(this)
        nameDialog.setContentView(R.layout.dialog_canvas_name)
        nameDialog.window?.setLayout(600, 560) //setando o tamanho da tela do dialog

        val changingCanvasName: EditText = nameDialog.findViewById(R.id.tv_changeCanvaName)
        changingCanvasName.setText(canvasName?.text) //fazendo com que o texto no EditText mude conforme o nome do Canvas
        val btnEnterChangingName: Button = nameDialog.findViewById(R.id.btn_canvasNameEnter)
        btnEnterChangingName.setOnClickListener{
            canvasName?.text = changingCanvasName.text
            nameDialog.dismiss()
        }


        nameDialog.show()
    }
}