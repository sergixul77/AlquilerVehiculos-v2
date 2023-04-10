package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;


import java.io.File;
import java.util.ArrayList;
// version 1
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.TipoVehiculo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Vehiculos implements IVehiculos {
	
	private static final File FICHERO_VEHICULOS = new File(String.format("%s%s%s%s%s", "datos", File.separator, "vehiculos.xml"));
    private static final String RAIZ = "vehiculos";
    private static final String VEHICULO = "vehiculo";
    private static final String MARCA="marca";
    private static final String MODELO="modelo";
    private static final String MATRICULA="matricula";
    private static final String CILINDRADA="cilindrada";
    private static final String PLAZAS="plazas";
    private static final String PMA="pma";
    private static final String TIPO="tipo";
    private static final String TURISMO="turismo";
    private static final String AUTOBUS="autobus";
    private static final String FURGONETA="furgoneta";
	private static Vehiculos instancia;
	
	
	static Vehiculos getInstancia() {

		if (instancia == null) {

			instancia = new Vehiculos();
		}

		return instancia;
	}
	
	@Override
	public void comenzar() {

		Document documento = UtilidadesXml.leerXmlDeFichero(FICHERO_VEHICULOS);

		if (documento != null) { /* Si el fichero es distinto de null */

			System.out.println("El fichero XML se ha leido correctamente");
			leerDom(documento);
		} else {
			System.out.printf("No se puede leer el fichero: %s. %n", FICHERO_VEHICULOS);
		}

	}
	
	
	
	private void leerDom(Document documentoXml) {

		NodeList vehiculos = documentoXml.getElementsByTagName(VEHICULO);
		for (int i = 0; i < vehiculos.getLength(); i++) {
			Node vehiculo = vehiculos.item(i);
			if (vehiculo.getNodeType() == Node.ELEMENT_NODE) {

				try {
					insertar(getVehiculo((Element) vehiculo)); // le hacemos casting a cliente de tipo node para que sea
																// un elemento
				} catch (OperationNotSupportedException | NullPointerException e) {

					System.out.println(e.getMessage());

				}

			}
		}

	}
	
	
	
	private Vehiculo getVehiculo(Element elemento) { 
		
		Vehiculo vehiculo = (Vehiculo)elemento;
		String marca = elemento.getAttribute(MARCA);
        String modelo = elemento.getAttribute(MODELO);
        String matricula = elemento.getAttribute(MATRICULA);
		if (vehiculo instanceof Turismo) {
			int cilindrada = Integer.parseInt(elemento.getAttribute(CILINDRADA));
			vehiculo = new Turismo(marca, modelo, cilindrada, matricula);
		} else if (vehiculo instanceof Autobus) {
			int plazas = Integer.parseInt(elemento.getAttribute(PLAZAS));
			vehiculo = new Autobus(marca, modelo, plazas, matricula);
		} else if (vehiculo instanceof Furgoneta) {
			int plazas = Integer.parseInt(elemento.getAttribute(PLAZAS));
			int pma = Integer.parseInt(elemento.getAttribute(PMA));
			vehiculo = new Furgoneta(marca, modelo, pma, plazas, matricula);
		}
		
		return vehiculo;
	}
	
	
	@Override
	public void terminar() {

		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_VEHICULOS);

	}
	
	private Document crearDom() {

		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Vehiculo vehiculo : coleccionVehiculos) {
				Element elementoVehiculo = getElemento(documentoXml, vehiculo);
				documentoXml.getDocumentElement().appendChild(elementoVehiculo);
			}
		}
		return documentoXml;

	}
	
	
	private Element getElemento(Document documentoXml, Vehiculo vehiculo) {

		Element elementoVehiculo = documentoXml.createElement(VEHICULO);
		elementoVehiculo.setAttribute(MARCA, vehiculo.getMarca());
		elementoVehiculo.setAttribute(MODELO, vehiculo.getModelo());
		elementoVehiculo.setAttribute(MATRICULA, vehiculo.getMatricula());
		return elementoVehiculo;

	}
	
	 private List<Vehiculo> coleccionVehiculos; 
	
	public Vehiculos () {
		
		coleccionVehiculos = new ArrayList<>(); // creando la lista
		
	}
	
	@Override
	public List<Vehiculo> get() {

		return new ArrayList<>(coleccionVehiculos) ;
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

		
	
	
	
