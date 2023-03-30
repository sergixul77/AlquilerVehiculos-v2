package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;

public class ModeloCascada extends Modelo {

	public ModeloCascada(IFuenteDatos fuenteDatos) {
		setFuenteDatos(fuenteDatos);
		comenzar();
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		getClientes().insertar(new Cliente(cliente));
	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		getVehiculos().insertar(Vehiculo.copiar(vehiculo));
	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede realizar un alquiler nulo.");
		}
		Cliente clienteEncontrado = getClientes().buscar(alquiler.getCliente());
		if (clienteEncontrado == null) {
			throw new OperationNotSupportedException("ERROR: No existe el cliente del alquiler.");
		}
		Vehiculo vehiculoEncontrado = getVehiculos().buscar(alquiler.getVehiculo());
		if (vehiculoEncontrado == null) {
			throw new OperationNotSupportedException("ERROR: No existe el turismo del alquiler.");
		}
		getAlquileres().insertar(new Alquiler(clienteEncontrado, vehiculoEncontrado, alquiler.getFechaAlquiler()));
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		return new Cliente(getClientes().buscar(cliente));
	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		return Vehiculo.copiar(getVehiculos().buscar(vehiculo));
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		return new Alquiler(getAlquileres().buscar(alquiler));
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		getClientes().modificar(cliente, nombre, telefono);
	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		getAlquileres().devolver(cliente, fechaDevolucion);
	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		getAlquileres().devolver(vehiculo, fechaDevolucion);
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		for (Alquiler alquiler : getAlquileres().get(cliente)) {
			borrar(alquiler);
		}
		getClientes().borrar(cliente);
	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		for (Alquiler alquiler : getAlquileres().get(vehiculo)) {
			borrar(alquiler);
		}
		getVehiculos().borrar(vehiculo);
	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		getAlquileres().borrar(alquiler);

	}

	@Override
	public List<Cliente> getListaClientes() {
		List<Cliente> listaDeClientes = new ArrayList<>();
		for (Cliente unCliente : getClientes().get()) {
			Cliente cliente = new Cliente(unCliente);
			listaDeClientes.add(cliente);
		}
		return listaDeClientes;
	}

	@Override
	public List<Vehiculo> getListaVehiculos() {
		List<Vehiculo> listaDeVehiculos = new ArrayList<>();
		for (Vehiculo unVehiculo : getVehiculos().get()) {
			Vehiculo vehiculo = Vehiculo.copiar(unVehiculo);
			listaDeVehiculos.add(vehiculo);
		}

		return listaDeVehiculos;
	}

	@Override
	public List<Alquiler> getListaAlquileres() {
		List<Alquiler> listaDeAlquileres = new ArrayList<>();
		for (Alquiler unAlquiler : getAlquileres().get()) {
			Alquiler alquiler = new Alquiler(unAlquiler);
			listaDeAlquileres.add(alquiler);
		}
		return listaDeAlquileres;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Cliente cliente) {
		List<Alquiler> listaDeAlquiler = new ArrayList<>();
		for (Alquiler unAlquiler : getAlquileres().get(cliente)) {
			Alquiler alquiler = new Alquiler(unAlquiler);
			listaDeAlquiler.add(alquiler);
		}
		return listaDeAlquiler;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Vehiculo vehiculo) {
		List<Alquiler> listaDeAlquiler = new ArrayList<>();
		for (Alquiler unAlquiler : getAlquileres().get(vehiculo)) {
			Alquiler alquiler = new Alquiler(unAlquiler);
			listaDeAlquiler.add(alquiler);
		}
		return listaDeAlquiler;
	}

}