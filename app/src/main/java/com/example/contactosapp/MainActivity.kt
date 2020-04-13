package com.example.contactosapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toolbar

class MainActivity : AppCompatActivity() {

    var lista:ListView?=null
    var adaptador:Adaptador? = null

    companion object{
        var contactos:ArrayList<Contactos>? = null

        fun agregarContacto(contacto:Contactos){
            contactos?.add(contacto)
        }
        fun obtenerContacto(index:Int):Contactos{
            return contactos?.get(index)!!
        }
        fun eliminarContacto(index: Int){
            contactos?.removeAt(index)
        }
        fun actualizarContacto(index:Int, nuevoContacto:Contactos){
            contactos?.set(index, nuevoContacto)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //CONEXION ELEMENTOS DE VISTA
        setSupportActionBar(findViewById(R.id.toolbar))//esto me coloca la barrita

        contactos = ArrayList()
        contactos?.add(Contactos("Marcos", "Rivas", "Underc0de", 24, 69.0f, "Peru", "1234567","marcos123@gmail.com", R.drawable.foto_01))
        contactos?.add(Contactos("Miriam", "Mendez", "Underc0de", 34, 69.0f, "Venezuela", "43567456","mm1234123@gmail.com", R.drawable.foto_02))
        contactos?.add(Contactos("Romina", "Alces", "Flavicon", 37, 69.0f, "Colombia", "46865w3","rivss@gmail.com", R.drawable.foto_03))
        contactos?.add(Contactos("Julian", "Zapata", "Evenbrite", 24, 69.0f, "Argentina", "23444356","njoljh@gmail.com", R.drawable.foto_04))

        lista = findViewById<ListView>(R.id.lista_princpal)
        adaptador = Adaptador(this, contactos!!)

        lista?.adapter = adaptador
        lista?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetalleActivity::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }

    }


    //esto coloca los elementos de la barra (toolbar), los infla
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        //BUSCADOR EN EL MAIN PRINCIPAL
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.search_View)
        val searchView = itemBusqueda?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Nombre del Contacto"

        //METODO CUANDO OBTIENE EL FOCO
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            //preparar datos
        }

        //METODO PARA HACER BUSQUEDA
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //filtrar
                adaptador?.filtrar(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //filtrar
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.bt_main_nuevoContacto -> {
                val intent = Intent(this, NuevoContacto::class.java)
                startActivity(intent)
                return true

            }else -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        adaptador?.notifyDataSetChanged()
    }
}
