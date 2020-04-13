package com.example.contactosapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*

class NuevoContacto : AppCompatActivity() {

    var fotoIndex:Int = 0 //para mapear el builder set adapter
    val fotos = arrayOf(R.drawable.foto_01, R.drawable.foto_02, R.drawable.foto_03, R.drawable.foto_04, R.drawable.foto_05,R.drawable.foto_06)
    var foto:ImageView? = null
    var index:Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_contacto)

        setSupportActionBar(findViewById(R.id.toolbar))//esto me coloca la barrita

        //para colocar la flechita para atras
        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //para seleccionar la imagen de contacto
        foto = findViewById<ImageView>(R.id.iv_detalle)
        foto?.setOnClickListener{
            seleccionarFoto()
        }
        //reconocer accion de nuevo vs editar
        if(intent.hasExtra("ID")){//SI TIENE EL ID, entonces quiere editar
            index = intent.getStringExtra("ID").toInt()
            rellenarDatos(index)

        }
    }
    //esto coloca los elementos de la barra (toolbar), los infla
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_nuevo_contacto, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.item_guardarContacto -> {


               //GUARDAR EL CONTACTO  NUEVO(CREAR NUEVO ELEMENTO DE TIPO CONTACTO)

                val nombre = findViewById<EditText>(R.id.et_nombre_detalle)
                val apellido = findViewById<EditText>(R.id.et_apellido_detalle)
                val empresa = findViewById<EditText>(R.id.et_empresa_detalle)
                val edad = findViewById<EditText>(R.id.et_edad_detalle)
                val peso = findViewById<EditText>(R.id.et_peso_detalle)
                val telefono = findViewById<EditText>(R.id.et_telefono_detalle)
                val email = findViewById<EditText>(R.id.et_email_detalle)
                val direccion = findViewById<EditText>(R.id.et_direccion_detalle)

                //validar campos

                var campos = ArrayList<String>()
                campos.add(nombre.text.toString())//0
                campos.add(apellido.text.toString())//1
                campos.add(empresa.text.toString())//2
                campos.add(edad.text.toString())//3
                campos.add(peso.text.toString())//4
                campos.add(direccion.text.toString())//5
                campos.add(telefono.text.toString())//6
                campos.add(email.text.toString())//7

                //validacion
                var flag = 0
                for(campo in campos){
                    if(campo.isNullOrEmpty()){//si algun campo está vacío
                        flag++
                    }
                }
                if(flag>0){
                    Toast.makeText(this, "Debes llenar todos los campos!", Toast.LENGTH_SHORT).show()
                }else{
                    if(index > -1){
                        MainActivity.actualizarContacto( index, Contactos(campos.get(0),campos.get(1), campos.get(2),
                            campos.get(3).toInt(), campos.get(4).toFloat(), campos.get(5), campos.get(6),
                            campos.get(7), obtenerFoto(fotoIndex)) )
                    }else{
                        MainActivity.agregarContacto(Contactos(campos.get(0),campos.get(1), campos.get(2),
                            campos.get(3).toInt(), campos.get(4).toFloat(), campos.get(5), campos.get(6),
                            campos.get(7), obtenerFoto(fotoIndex)))
                    }

                    finish()
                    Log.d("No Elementos", MainActivity.contactos?.count().toString())
                }




                return true

            }else -> {return super.onOptionsItemSelected(item)}
        }
    }
    fun seleccionarFoto(){

        //TRABAJANDO CON ALERT DIALOG
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccionar imagen de Perfil")

        val adaptadorDialogo = ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
        adaptadorDialogo.add("Foto 01")
        adaptadorDialogo.add("Foto 02")
        adaptadorDialogo.add("Foto 03")
        adaptadorDialogo.add("Foto 04")
        adaptadorDialogo.add("Foto 05")
        adaptadorDialogo.add("Foto 06")


        builder.setAdapter(adaptadorDialogo){
            dialog, which ->
            fotoIndex = which
            //PARA VER LA IMAGEN AL SELECCIONARLA
            foto?.setImageResource(obtenerFoto(fotoIndex))

        }
        //boton de cancelar del dialog
        builder.setNegativeButton("Cancelar"){
            dialog, which ->  dialog.dismiss()
        }

        builder.show()
    }

    fun obtenerFoto(index:Int):Int {
        return fotos.get(index)
    }
    fun rellenarDatos(index:Int){
        val contacto = MainActivity.obtenerContacto(index)

        val tvNombre = findViewById<EditText>(R.id.et_nombre_detalle)
        val tvApellido = findViewById<EditText>(R.id.et_apellido_detalle)
        val tvEmpresa = findViewById<EditText>(R.id.et_empresa_detalle)
        val tvEdad = findViewById<EditText>(R.id.et_edad_detalle)
        val tvPeso = findViewById<EditText>(R.id.et_peso_detalle)
        val tvTelefono = findViewById<EditText>(R.id.et_telefono_detalle)
        val tvEmail = findViewById<EditText>(R.id.et_email_detalle)
        val tvDireccion = findViewById<EditText>(R.id.et_direccion_detalle)
        val iv_imagen = findViewById<ImageView>(R.id.iv_detalle)

        tvNombre.setText(contacto.nombre, TextView.BufferType.EDITABLE)
        tvApellido.setText(contacto.apellido, TextView.BufferType.EDITABLE)
        tvEmpresa.setText(contacto.empresa, TextView.BufferType.EDITABLE)
        tvEdad.setText(contacto.edad.toString(), TextView.BufferType.EDITABLE)
        tvPeso.setText(contacto.peso.toString(), TextView.BufferType.EDITABLE)
        tvTelefono.setText(contacto.telefono, TextView.BufferType.EDITABLE)
        tvEmail.setText(contacto.email, TextView.BufferType.EDITABLE)
        tvDireccion.setText(contacto.direccion, TextView.BufferType.EDITABLE)
        iv_imagen.setImageResource(contacto.imagen)

        //PARA MAPEAR LAS FOTOS
        var posicion:Int = 0
        for(foto in fotos){
            if(contacto.imagen == foto){
                fotoIndex = posicion
            }
            posicion++
        }
    }
}
