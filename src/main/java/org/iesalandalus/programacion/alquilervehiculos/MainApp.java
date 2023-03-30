package org.iesalandalus.programacion.alquilervehiculos;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;

//version 1

import org.iesalandalus.programacion.alquilervehiculos.modelo.ModeloCascada;
import org.iesalandalus.programacion.alquilervehiculos.vista.FactoriaVista;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.VistaTexto;

public class MainApp {

	public static void main(String[] args) {

		VistaTexto vistaTexto = FactoriaVista.TEXTO.crear(); // Esto crea una nueva vista 

		Modelo modeloCascada = new ModeloCascada(FactoriaFuenteDatos.MEMORIA.crear());

		Controlador controlador = new Controlador(modeloCascada, vistaTexto);

		controlador.comenzar();

	}

}

