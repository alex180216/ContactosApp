package com.example.contactosapp

import android.telecom.TelecomManager

class Contactos(nombre:String, apellido:String, empresa:String, edad:Int, peso:Float, direccion:String, Telefono:String, email:String, imagen:Int) {
    var nombre:String = ""
    var apellido:String = ""
    var empresa:String = ""
    var edad:Int = 0
    var peso:Float = 0.0F
    var direccion:String = ""
    var telefono:String = ""
    var email:String = ""
    var imagen:Int = 0

    init {
        this.nombre = nombre
        this.apellido = apellido
        this.empresa = empresa
        this.edad = edad
        this.peso = peso
        this.direccion = direccion
        this.telefono = telefono
        this.email =email
        this.imagen = imagen
    }

}