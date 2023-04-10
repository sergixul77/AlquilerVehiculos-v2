package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

public class FuenteDatosFicheros implements IFuenteDatos {
	@Override
	public IClientes crearClientes() { // Creo una interfaz de la clase clientes que devuelve un nuevo cliente.

		return Clientes.getInstancia();
	}

	@Override
	public IVehiculos crearVehiculos() {

		return Vehiculos.getInstancia();

	}

	@Override
	public IAlquileres crearAlquileres() {

		return Alquileres.getInstancia();

	}

}
