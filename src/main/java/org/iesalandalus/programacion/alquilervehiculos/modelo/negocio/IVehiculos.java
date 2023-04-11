package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public interface IVehiculos {

	List<Vehiculo> get();


	void insertar(Vehiculo turismo) throws OperationNotSupportedException;

	Vehiculo buscar(Vehiculo turismo);

	void borrar(Vehiculo vehiculo) throws OperationNotSupportedException;


	void comenzar();


	void terminar();

}