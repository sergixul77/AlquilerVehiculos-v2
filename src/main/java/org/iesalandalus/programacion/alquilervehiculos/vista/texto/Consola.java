package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

// version 1

import java.time.LocalDate;

/*Version 1*/

import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private static final String PATRON_FECHA = "dd/MM/yyyy";

	private static final String PATRON_MES = "MM/yyyy";

	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);

	private Consola() {

	}

	public static void mostrarCabecera(String mensaje) {

		System.out.println(mensaje);
		System.out.println("-".repeat(mensaje.length())); // imprime una linea de guiones igual que la longitud del
															// mensaje
	}

	public static void mostrarMenuAcciones() {

		mostrarCabecera("Alquiler de vehículos Sergio");

		for (Accion menu : Accion.values()) {
			System.out.println(menu.toString());

		}

	}

	private static String leerCadena(String mensaje) {

		System.out.print(mensaje); // devuelvo el mensaje pasado por parametro

		return Entrada.cadena(); // Almaceno en la variable cadena lo que me escribe el usuario; //
	}

	private static int leerEntero(String mensaje) {

		System.out.print(mensaje); // devuelvo el mensaje pasado por parametro

		return Entrada.entero(); // Almaceno en la variable entero lo que me escribe el usuario; //
	}

	private static LocalDate leerFecha(String mensaje, String patron) {

		/*
		 * recive un patron, una vez que leo la cadena con la fecha tengo que ver que el
		 * patron sea igual que la fecha que le paso y tengo que poner un 01/
		 */

		LocalDate fecha;
		
		String fechaFormato = leerCadena(mensaje);
		
		if (patron.equals(PATRON_MES)) {
			fechaFormato = "01/"+fechaFormato;
		}

		try {
			fecha = LocalDate.parse(fechaFormato, FORMATO_FECHA);

		} catch (Exception e) {
			fecha = null;
		}

		return fecha;
	}

	public static Accion elegirAccion() {

		Accion accion = null;

		do {

			try {

				accion = Accion.get(leerEntero("Introduce una opción: "));

			} catch (Exception e) {

				System.out.println("Error: el ordinal no es valido" + e.getMessage());
			}

		} while (accion == null && accion != Accion.SALIR); // si la opcion es = null es porque la opcion que le has
															// pasado por parametro no
		// esta definida

		return accion;

	}

	private static TipoVehiculo elegirTipoVehiculo() {
		
		mostrarMenuTiposVehiculos();

		TipoVehiculo opcion = null;

		do {

			try {

				opcion = TipoVehiculo.get(leerEntero("Introduce una opción: "));

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} while (opcion == null);

		return opcion;

	}

	public static Cliente leerCliente() {

		return new Cliente(leerCadena("Introduce tu nombre: "), (leerCadena("Introduce tu dni: ")),
				(leerCadena("Introduce tu número de teléfono: ")));

	}

	public static Cliente leerClienteDni() {
		return Cliente.getClienteConDni(leerCadena("Introduce el dni del cliente: ")); // crea un nuevo cliente con dni
																						// segun lo que el usuario mete
																						// por teclado

	}

	public static String leerNombre() {

		return leerCadena("Introduce tu nombre:  ");

	}

	public static String leerTelefono() {

		return leerCadena("Introduce tu teléfono: ");

	}

	public static Vehiculo leerVehiculo() {
	
		return Vehiculo.copiar(leerVehiculo(elegirTipoVehiculo()));

	}

	public static Vehiculo leerVehiculoMatricula() {

		return Vehiculo.getVehiculoConMatricula(leerCadena("Introduce la matrícula del vehículo: "));
	}

	public static Alquiler leerAlquiler() {

		return new Alquiler(leerClienteDni(), leerVehiculoMatricula(), leerFecha("Introduzca la fecha de alquiler: ", PATRON_FECHA));

	}

	public static LocalDate leerMes() {

		return leerFecha("Introduzca un mes en formato MM/yyyy", PATRON_MES);

	}

	public static LocalDate leerFechaDevolucion() {

		return leerFecha("Introduceme la fecha de devolución: " , PATRON_FECHA);
	}

	private static void mostrarMenuTiposVehiculos() {

		mostrarCabecera("Tipos de vehiculos");
		for (TipoVehiculo tipoDeVehiculo : TipoVehiculo.values()) {
			System.out.printf("%s. %s", tipoDeVehiculo.ordinal(), tipoDeVehiculo.toString());
		}

	}

	private static Vehiculo leerVehiculo(TipoVehiculo tipoVehiculo) {

		Vehiculo vehiculo = null;

		String marca = leerCadena("Introduce la marca: ");
		String modelo = leerCadena("Introduce el modelo: ");
		String matricula = leerCadena("Introduce una matrícula: ");

		switch (tipoVehiculo) {
		case AUTOBUS:

			vehiculo = new Autobus(marca, modelo, leerEntero("Introduce el número de plazas: "), matricula);

			break;

		case FURGONETA:

			vehiculo = new Furgoneta(marca, modelo, leerEntero("Introduce el peso maximo permitido: "),
					leerEntero("Introduce el número de plazas"), matricula);

			break;
		case TURISMO:

			vehiculo = new Turismo(marca, modelo, leerEntero("Introduce la cilindrada: "), matricula);
			break;

		default:
			break;
		}
		return vehiculo;
	}

}
