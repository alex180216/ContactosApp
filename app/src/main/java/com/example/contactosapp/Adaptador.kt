package com.example.contactosapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class Adaptador(var contexto: Context, items:ArrayList<Contactos>):BaseAdapter() {

    //Almacenar los elementos que se van a mostrar en el listView
    var items: ArrayList<Contactos>? = null
    var copiaItems:ArrayList<Contactos>? = null //para no modificar el items original porque si no me modifica to-do el codigo

    //INICIALIZAMOS
    init {
        this.items = ArrayList(items)//Con esto hago una copia nueva
        this.copiaItems = items
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* Creamos una clase, ViewHolder mas abajo para hacer el modelo de vista que se muestra,
        luego configuramos el viewHolder con los valores que voy recibiendo con cada vista
        */
        var viewHolder:ViewHolder? = null
        var vista:View? = convertView

        if(vista == null){
            //si estamos usando una nueva vista
            vista = LayoutInflater.from(contexto).inflate(R.layout.template_contacto, null)
            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder

        }else{
            //si la estamos reciclando
            viewHolder = vista.tag as? ViewHolder
        }


        val item = getItem(position) as Contactos

        //asignacion de valores a elementos graficos
        viewHolder?.nombre?.text = item.nombre +" "+ item.apellido
        viewHolder?.empresa?.text = item.empresa
        viewHolder?.imagen?.setImageResource(item.imagen)

        return vista!!

    }

    override fun getItem(position: Int): Any {
        return this.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        //regresar el numero de elementos de mi lista
        return this.items?.count()!!
    }

    fun filtrar(str:String){
        items?.clear()
        if (str.isEmpty()){
            //el usuario no está buscando nada
            items = copiaItems
            notifyDataSetChanged()
            return
        }
        var busqueda = str
        busqueda = busqueda.toLowerCase()

        for(item in copiaItems!!){
            val nombre = item.nombre.toLowerCase()

            if(nombre.contains(busqueda)){//si el nombre contiene a la busqueda
                items?.add(item)

            }
        }

        notifyDataSetChanged()
    }

    private class ViewHolder(vista:View){
        var nombre:TextView? = null
        var empresa:TextView? = null
        var imagen:ImageView? = null


        init {
            nombre = vista.findViewById(R.id.tv_nombre_contacto)
            empresa = vista.findViewById(R.id.tv_lugar_trabajo)
            imagen = vista.findViewById(R.id.image_foto)
        }

    }
}