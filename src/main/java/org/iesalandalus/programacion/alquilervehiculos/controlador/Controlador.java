package org.iesalandalus.programacion.alquilervehiculos.controlador;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class Controlador {

	private Modelo modelo;

	private Vista vista;

	public Controlador(Modelo modelo, Vista vista) {
		if (modelo == null) {
			throw new NullPointerException("ERROR: no puedes crear un modelo nulo");
		}
		if (vista == null) {
			throw new NullPointerException("ERROR: no puedes crear una vista  nula");
		}

		this.modelo = modelo;
		this.vista = vista;
		vista.setControlador(this); // para que vista pueda mandar a ejecutar opciones al controlador y pueda
									// manejarla correctamente. Con el this te estas refiriendo a una instancia de
									// la clase?

	}

	public void comenzar() {
		modelo.comenzar(); // tengo que poner primero modelo,
		vista.comenzar();

	}

	public void terminar() {
		modelo.terminar();
		vista.terminar();

	}

	/*------       Inserta un cliente o un turismo o un alquiler           --------*/

	public void insertar(Cliente cliente) throws OperationNotSupportedException {

		modelo.insertar(cliente);
	}

	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {

		modelo.insertar(vehiculo);

	}

	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		modelo.insertar(alquiler);
	}

	/*------       Busca un cliente o un turismo o un alquiler           --------*/

	public Cliente buscar(Cliente cliente) {

		return modelo.buscar(cliente);

	}

	public Vehiculo buscar(Vehiculo vehiculo) {

		return modelo.buscar(vehiculo);

	}

	public Alquiler buscar(Alquiler alquiler) {

		return modelo.buscar(alquiler);

	}

	/* Modifica un clinente el nombre */

	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {

		modelo.modificar(cliente, nombre, telefono);

	}

	/* Devuelve el alquiler con la fecha de devolucion */

	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {

		modelo.devolver(cliente, fechaDevolucion);

	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {

		modelo.devolver(vehiculo, fechaDevolucion);

	}

	/* Borrar un cliente, un turismo y un alquiler */

	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		modelo.borrar(vehiculo);

	}

	public void borrar(Cliente cliente) throws OperationNotSupportedException {

		modelo.borrar(cliente);

	}

	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {

		modelo.borrar(alquiler);

	}

	public List<Cliente> getClientes() {
		return modelo.getListaClientes();
	}

	public List<Vehiculo> getVehiculos() {
		return modelo.getListaVehiculos();
	}

	public List<Alquiler> getAlquileres() {

		return modelo.getListaAlquileres();
	}

	public List<Alquiler> getAlquileres(Cliente cliente) {

		return modelo.getListaAlquileres(cliente);

	}

	public List<Alquiler> getAlquileres(Vehiculo vehiculo) {
		return modelo.getListaAlquileres(vehiculo);

	}

}