package com.example.drawingapp

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import yuku.ambilwarna.AmbilWarnaDialog


class MainActivity : AppCompatActivity() {

    private var drawingView: DrawingView? = null
    private var textBrushSize: TextView? = null
    var startSize = 7
    var endSize = 0
    private var defaultColor = 0
    private var btnColorPicker: ImageButton? = null
    private var btnBrushPicker: ImageButton? = null

    //criando um display que pede permissão para acessar as imagens do celular
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setBrushSize(startSize.toFloat())

        btnBrushPicker= findViewById(R.id.btn_brushSizePicker)
        btnBrushPicker?.setOnClickListener{
            displayDialogBrushSizePicker()

        }

        //setando color picker
        btnColorPicker = findViewById(R.id.btn_brushColorPicker)
        btnColorPicker?.setOnClickListener{
            displayDialogBrushColorPicker()

        }

        //setando a mudança de background
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
                permissions ->
            permissions.entries.forEach{
                val permissionName = it.key
                val isGranted = it.value

                if(isGranted){
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                }else{
                    if(permissionName == Manifest.permission.READ_EXTERNAL_STORAGE){
                        Toast.makeText(this@MainActivity, "Permission denied", Toast.LENGTH_SHORT).show()
                        showRationaleDialog("Drawing App", "Drawing app needs to acess your external storage")

                    }
                }

            }
        }

        val btnBgChanger: ImageButton = findViewById(R.id.btn_bgChanger)
        btnBgChanger.setOnClickListener {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                showRationaleDialog("Drawing App", "Drawing app needs to acess your external storage")
            }else{
                permissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }
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

    //criando função para permissão de galeria
    private fun showRationaleDialog(title: String, message: String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel"){
                dialog, _-> dialog.dismiss()
            }
        builder.create().show()
    }
}