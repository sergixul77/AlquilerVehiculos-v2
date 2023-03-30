package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

// version 1

public class VistaTexto extends Vista {

	public VistaTexto() {
		super();
		Accion.setVista(this);

	}

	public void comenzar() {
		Accion eligeOpcion;

		do {
			Consola.mostrarMenuAcciones();
			eligeOpcion = Consola.elegirAccion();// este metodo ya lee la opcion y la ejecutara
			Consola.mostrarCabecera(eligeOpcion.toString());
			eligeOpcion.ejecutar();
		} while (eligeOpcion != Accion.SALIR); // si la opcion es diferente de la opcion salir va a seguir mostrando el
												// menu, para poder seguir eligiendo opciones

	}

	public void terminar() {
		System.out.println("Me despido de ti, desde alquiler Sergio");
	}

	public void insertarCliente() {
		try {
			getControlador().insertar(Consola.leerCliente());
			System.out.println("El cliente se ha insertado de forma  correcta.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertarVehiculo() {
		try {
			getControlador().insertar(Consola.leerVehiculo());
			System.out.println("El turismo se ha insertado correctamente.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertarAlquiler() {
		try {
			getControlador().insertar(Consola.leerAlquiler());
			System.out.println("El alquiler se ha insertado de forma correcta.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarCliente() {
		try {

			System.out.println(getControlador().buscar(Consola.leerClienteDni()));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarVehicuo() {
		try {
			System.out.println(getControlador().buscar(Consola.leerVehiculoMatricula()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarAlquiler() {
		try {
			System.out.println(getControlador().buscar(Consola.leerAlquiler()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void modificarCliente() {
		try {
			getControlador().modificar(Consola.leerClienteDni(), Consola.leerNombre(), Consola.leerTelefono());
			System.out.println("El cliente se ha  modificado  correctamente.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverAlquilerCliente() {
		try {
			getControlador().devolver(Consola.leerClienteDni(), Consola.leerFechaDevolucion());
			System.out.println("El alquiler se ha devuelto de forma correcta.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverAlquilerVehiculo() {
		try {
			getControlador().devolver(Consola.leerVehiculoMatricula(), Consola.leerFechaDevolucion());
			System.out.println("El alquiler se ha devuelto de forma correcta.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarCliente() {
		try {
			getControlador().borrar(Consola.leerClienteDni());
			System.out.println("El cliente se ha podido borrar correctamente.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarVehiculo() {
		try {
			getControlador().borrar(Consola.leerVehiculoMatricula());
			System.out.println("El turismo  se ha podido borrar correctamente.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarAlquiler() {
		try {
			getControlador().borrar(Consola.leerAlquiler());
			System.out.println("El alquiler se ha podido borrar correctamente.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarClientes() {
		try {
			List<Cliente> listaDeClientesOrdenada = getControlador().getClientes();
			listaDeClientesOrdenada.sort(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni));
			for (Cliente cliente : listaDeClientesOrdenada) {
				System.out.println(cliente.toString());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarVehiculos() {
		try {
			List<Vehiculo> listaDeVehiculosOrdenada = getControlador().getVehiculos();
			listaDeVehiculosOrdenada.sort(Comparator.comparing(Vehiculo::getMarca).thenComparing(Vehiculo::getModelo));
			for (Vehiculo vehiculo : listaDeVehiculosOrdenada) {
				System.out.println(vehiculo.toString());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileres() {
		try {
			List<Alquiler> listaDeAlquileresOrdenada = getControlador().getAlquileres();
			listaDeAlquileresOrdenada
					.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getFechaDevolucion));
			for (Alquiler alquiler : listaDeAlquileresOrdenada) {
				System.out.println(alquiler.toString());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileresClientes() {
		try {

			List<Alquiler> listaDeAlquileresCliente = getControlador().getAlquileres(Consola.leerClienteDni());
			for (Alquiler alquiler : listaDeAlquileresCliente) {
				System.out.println(alquiler.toString());

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileresVehiculo() {
		try {

			List<Alquiler> listaDeAlquileresVehiculo = getControlador().getAlquileres(Consola.leerVehiculoMatricula());
			for (Alquiler alquiler : listaDeAlquileresVehiculo) {
				System.out.println(alquiler.toString());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void mostrarEstadisticasMensualesTipoVehiculo() {

		LocalDate mesInicio = Consola.leerMes(); /*Leemos el mes*/
		Map<TipoVehiculo, Integer> mapa = inicializarEstadisticas(); /*Utilizamos el metodo inicializar estadisticas que inicializa a 0 los vehiculos*/
		for (Alquiler alquiler : getControlador().getAlquileres()) { // recorro la lista de alquileres
//			if (alquiler.getFechaAlquiler().isAfter(mesInicio.minusDays(1)) && alquiler.getFechaAlquiler().isBefore(mesFin)) { /*Si la fecha de alquiler es despues */ 
//			
//				
//				
//			}
			
			if (alquiler.getFechaAlquiler().getMonthValue() == mesInicio.getMonthValue() && alquiler.getFechaAlquiler().getYear() == mesInicio.getYear())  { // esto comprueba si el mes del alquier que leo por la lista es igual al mes de inicio.
				
				mapa.put(TipoVehiculo.get(alquiler.getVehiculo()), mapa.getOrDefault(TipoVehiculo.get(alquiler.getVehiculo()),0)+1); // coge el tipo del vehiculo y mira la cantidad que tiene, de forma predeterminada estara a 0 , entonces cuando sea de un turismo hara 0 + 1 = 1
				
			}
			
		}

	}

	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {

		Map<TipoVehiculo, Integer> mapaVehiculos = new TreeMap<TipoVehiculo, Integer>();

		for (TipoVehiculo vehiculo : TipoVehiculo.values()) {
			mapaVehiculos.put(vehiculo, 0); // cuandro creo un mapa se inicializa todos los tipos de vehiculos a 0.
		}
		return mapaVehiculos;
	}

}
