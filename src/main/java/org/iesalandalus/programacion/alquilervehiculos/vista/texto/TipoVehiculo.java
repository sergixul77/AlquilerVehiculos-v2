package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public enum TipoVehiculo {

	TURISMO("Turismo"), AUTOBUS("Autobús"), FURGONETA("Furgoneta");

	private String nombre;

	private TipoVehiculo(String nombre) {

		this.nombre = nombre;

	}

	private static boolean esOrdinalValido(int ordinal) {
		return ordinal >= 0 && ordinal < values().length;

	}

	public static TipoVehiculo get(int ordinal) {

		if (!esOrdinalValido(ordinal)) {
			throw new IllegalArgumentException("El ordinal proporcionado no es válido.");
		}

		return values()[ordinal];
	}

	public static TipoVehiculo get(Vehiculo vehiculo) {

		TipoVehiculo tipoVehiculo = null;

		if (vehiculo == null) {
			throw new IllegalArgumentException("El vehículo no puede ser nulo");
		}

		if (vehiculo instanceof Turismo) {
			tipoVehiculo = TipoVehiculo.TURISMO;
		} else if (vehiculo instanceof Autobus) {
			tipoVehiculo = TipoVehiculo.AUTOBUS;
		} else if (vehiculo instanceof Furgoneta) {
			tipoVehiculo = TipoVehiculo.FURGONETA;
		}

		return tipoVehiculo;

	}

	@Override
	public String toString() {

		return String.format("%s ", nombre);
	}

}
