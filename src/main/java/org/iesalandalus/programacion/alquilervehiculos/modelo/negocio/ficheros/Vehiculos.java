package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;


import java.util.ArrayList;

// version 1
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

public class Vehiculos implements IVehiculos {
	
	private static Vehiculos instancia;
	
	 private List<Vehiculo> coleccionVehiculos; 
	
	public Vehiculos () {
		
		coleccionVehiculos = new ArrayList<>(); // creando la lista
		
	}
	
	@Override
	public List<Vehiculo> get() {

		return new ArrayList<>(coleccionVehiculos) ;
	}

	@Override
	public int getCantidad () {
	
		return coleccionVehiculos.size(); // devuelvo el numero de elementos que contiene la lista
	}
	
	public void insertar(Vehiculo vehiculos) throws OperationNotSupportedException {
	
		if (vehiculos == null) {
			throw new NullPointerException("ERROR: No se puede insertar un vehículo nulo.");
		}
		
		if (!coleccionVehiculos.contains(vehiculos)) {
			coleccionVehiculos.add(vehiculos);
		}else {
			throw new OperationNotSupportedException("ERROR: Ya existe un vehículo con esa matrícula.");
		}
	}
		
	@Override
	public Vehiculo buscar(Vehiculo vehiculos) {

		if (vehiculos == null) {
			throw new NullPointerException("ERROR: No se puede buscar un vehículo nulo.");
		}

		int indiceTurismo = coleccionVehiculos.indexOf(vehiculos); //Almacenamos los indices de la lista de turismos 

		Vehiculo vehiculoEncontrado = null; // lo iniciamos a null

		if (indiceTurismo != -1) { // El -1 en un numerico es como si fuera null, por lo tanto si es diferente de null

			vehiculoEncontrado = coleccionVehiculos.get(indiceTurismo); // Cogemos el indice del turismo que se ha encontrado 
		}

		return vehiculoEncontrado; // y lo mostramos 		

	}
	
	@Override
	public void borrar(Vehiculo turismo) throws OperationNotSupportedException {

		if (turismo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un vehículo nulo.");
		}

		if (coleccionVehiculos.contains(turismo)) { // si existe el turismo.

			coleccionVehiculos.remove(turismo);

		} else { // si el turismo no existe
			throw new OperationNotSupportedException("ERROR: No existe ningún vehículo con esa matrícula.");
		}

	}

	

	
	
			
	}

		
	
	
	
