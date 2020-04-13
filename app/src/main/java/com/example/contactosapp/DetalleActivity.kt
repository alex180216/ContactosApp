package com.example.contactosapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

class DetalleActivity : AppCompatActivity() {

    var index:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        setSupportActionBar(findViewById(R.id.toolbar))//esto me coloca la barrita

        //para colocar la flechita para atras
        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        index = intent.getStringExtra("ID").toInt()
        //Log.d("INDEXX", index.toString)

        mapearDatos()
    }

    fun mapearDatos(){
        val contacto = MainActivity.obtenerContacto(index)

        val tvNombre = findViewById<TextView>(R.id.et_nombre_detalle)//nombre completo
        val tvEmpresa = findViewById<TextView>(R.id.et_empresa_detalle)
        val tvEdad = findViewById<TextView>(R.id.et_edad_detalle)
        val tvPeso = findViewById<TextView>(R.id.et_peso_detalle)
        val tvTelefono = findViewById<TextView>(R.id.et_telefono_detalle)
        val tvEmail = findViewById<TextView>(R.id.et_email_detalle)
        val tvDireccion = findViewById<TextView>(R.id.et_direccion_detalle)
        val iv_imagen = findViewById<ImageView>(R.id.iv_detalle)

        tvNombre.text = contacto.nombre +" "+contacto.apellido
        tvEmpresa.text = contacto.empresa
        tvEdad.text = contacto.edad.toString() +" años"
        tvPeso.text = contacto.peso.toString() +" kg"
        tvTelefono.text = contacto.telefono
        tvEmail.text = contacto.email
        tvDireccion.text = contacto.direccion
        iv_imagen.setImageResource(contacto.imagen)

    }
    //esto coloca los elementos de la barra (toolbar), los infla
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_detalle, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.bt_eliminar -> {
                MainActivity.eliminarContacto(index )
                finish()
                return true
            }
            R.id.bt_editar_contacto -> {
                val intent = Intent(this, NuevoContacto::class.java)
                intent.putExtra("ID", index.toString())
                startActivity(intent)
                return true
            }
            else -> { return super.onOptionsItemSelected(item)}
        }


    }

    override fun onResume() {
        super.onResume()
        mapearDatos()
    }
}
