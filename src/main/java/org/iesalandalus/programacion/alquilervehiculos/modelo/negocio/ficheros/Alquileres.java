package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;

// version 2

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres {

	private static final File FICHEROS_ALQUILERES = new File(
			String.format("%s%s%s", "datos", File.separator, "alquileres.xml"));
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String RAIZ = "alquileres";
	private static final String ALQUILER = "alquiler";
	private static final String VEHICULO = "vehiculo";
	private static final String CLIENTE = "cliente";
	private static final String FECHA_ALQUILER = "fechaAlquiler";
	private static final String FECHA_DEVOLUCION = "fechaDevolucion";

	private static Alquileres instancia;

	private List<Alquiler> coleccionAlquileres;

	public Alquileres() {

		coleccionAlquileres = new ArrayList<>(); // creando la lista

	}

	static Alquileres getInstancia() {

		if (instancia == null) {

			instancia = new Alquileres();
		}

		return instancia;
	}

	@Override
	public void comenzar() {

		Document documento = UtilidadesXml.leerXmlDeFichero(FICHEROS_ALQUILERES);

		if (documento != null) { /* Si el fichero es distinto de null */

			System.out.println("El fichero XML de alquileres se ha leido correctamente");
			leerDom(documento);
		} else {
			System.out.printf("No se puede leer el fichero: %s. %n", FICHEROS_ALQUILERES);
		}

	}

	private void leerDom(Document documentoXml) {

		NodeList alquileres = documentoXml.getElementsByTagName(ALQUILER);
		for (int i = 0; i < alquileres.getLength(); i++) {
			Node alquiler = alquileres.item(i);
			if (alquiler.getNodeType() == Node.ELEMENT_NODE) {

				try {
					insertar(getAlquiler((Element) alquiler)); // le hacemos casting a cliente de tipo node para que sea
																// un elemento
				} catch (OperationNotSupportedException | NullPointerException e) {

					System.out.println(e.getMessage());
					System.out.println(i);

				}

			}
		}

	}

	private Alquiler getAlquiler(Element elemento) throws OperationNotSupportedException {
		String cliente = elemento.getAttribute(CLIENTE);
		Cliente buscarCliente = Clientes.getInstancia().buscar(Cliente.getClienteConDni(cliente));
		String fechaAlquiler = elemento.getAttribute(FECHA_ALQUILER);
		LocalDate localDate = LocalDate.parse(fechaAlquiler, FORMATO_FECHA);
		String vehiculo = elemento.getAttribute(VEHICULO);
		Vehiculo buscarVehiculo = Vehiculos.getInstancia().buscar(Vehiculo.getVehiculoConMatricula(vehiculo));

		if (buscarVehiculo == null) {
			throw new NullPointerException("El vehiculo buscado no puede ser nulo");
		}

		if (buscarCliente == null) {
			throw new NullPointerException("El cliente buscado no puede ser nulo");
		}

		Alquiler alquilerDevolucion = new Alquiler(buscarCliente, buscarVehiculo, localDate);

		if (elemento.hasAttribute(FECHA_DEVOLUCION)) {
			String fechaDevolucion = elemento.getAttribute(FECHA_DEVOLUCION);
			LocalDate formatoFechaDevolucion = LocalDate.parse(fechaDevolucion, FORMATO_FECHA);

			alquilerDevolucion.devolver(formatoFechaDevolucion);

		}

		return alquilerDevolucion;
	}

	@Override
	public void terminar() {

		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHEROS_ALQUILERES);

	}

	private Document crearDom() {

		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Alquiler alquiler : coleccionAlquileres) {
				Element elementoAlquiler = getElemento(documentoXml, alquiler);
				documentoXml.getDocumentElement().appendChild(elementoAlquiler);
			}
		}
		return documentoXml;

	}

	private Element getElemento(Document documentoXml, Alquiler alquiler) {

		Element elementoAlquiler = documentoXml.createElement(ALQUILER);
		elementoAlquiler.setAttribute(CLIENTE, alquiler.getCliente().getDni());
		elementoAlquiler.setAttribute(FECHA_ALQUILER, alquiler.getFechaAlquiler().format(FORMATO_FECHA));
		elementoAlquiler.setAttribute(FECHA_DEVOLUCION, alquiler.getFechaDevolucion().format(FORMATO_FECHA));
		elementoAlquiler.setAttribute(VEHICULO, alquiler.getVehiculo().getMatricula());
		return elementoAlquiler;

	}

	@Override
	public List<Alquiler> get() {
		return new ArrayList<>(coleccionAlquileres);
	}

	@Override
	public List<Alquiler> get(Cliente cliente) {

		List<Alquiler> listaNuevaCliente = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				listaNuevaCliente.add(alquiler);
			}
		}
		return listaNuevaCliente;

	}

	@Override
	public List<Alquiler> get(Vehiculo vehiculo) {

		List<Alquiler> listaNuevaTurismo = new ArrayList<>(); // creo una nueva list

		for (Alquiler alquiler : coleccionAlquileres) { // recorro la lista
			if (alquiler.getVehiculo().equals(vehiculo)) {
				// Si el turismo del alquiler es el mismo turismo que pasamos por parametro lo
				// almacenamos?
				listaNuevaTurismo.add(alquiler);

			}

		}
		return listaNuevaTurismo; // devolvemos la lista. NOSE SI ESTA BIEN TODO LO QUE HE HECHO.

	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {
		for (Alquiler alquiler : coleccionAlquileres) {
			// con equal compruebo si el el objeto cliente y turismo pasados son los mismos
			// que los objetors y turismos de alquiler.
			if (alquiler.getFechaDevolucion() == null) {

				if (alquiler.getCliente().equals(cliente)) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
				}
				if (alquiler.getVehiculo().equals(vehiculo)) {

					throw new OperationNotSupportedException("ERROR: El vehículo está actualmente alquilado.");
				}

			} else {
				// alquileres que son devueltos
				if (alquiler.getCliente().equals(cliente) && (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
						|| alquiler.getFechaDevolucion().isEqual(fechaAlquiler))) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
				}
				if (alquiler.getVehiculo().equals(vehiculo) && (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
						|| alquiler.getFechaDevolucion().isEqual(fechaAlquiler))) {
					throw new OperationNotSupportedException("ERROR: El vehículo tiene un alquiler posterior.");
				}
			}
		}
	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {

		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}

		if (!coleccionAlquileres.contains(alquiler)) {
			comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
			coleccionAlquileres.add(alquiler);
		}

	}

//	public void devolver(Alquiler alquiler , LocalDate fechaDevolucion) throws OperationNotSupportedException {
//
//		if (alquiler == null) {
//			throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
//		}
//
//		if (coleccionAlquileres.contains(alquiler)) {
//			
//			alquiler.devolver(fechaDevolucion);
//			
//		}else {
//			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
//		}
//
//	}

	public Alquiler getAlquilerAbiertoCliente(Cliente cliente) {

		Alquiler devuelvoAlquilerAbiertoCliente = null;

		for (Iterator<Alquiler> iterator = coleccionAlquileres.iterator(); iterator.hasNext()
				&& devuelvoAlquilerAbiertoCliente == null;) {
			Alquiler alquiler = iterator.next();
			if (alquiler.getCliente().equals(cliente) && alquiler.getFechaDevolucion() == null) { // defino si el
																									// aquiler esta
																									// abierto y ademas
																									// si es del cliente
																									// que nos ha pasado
				devuelvoAlquilerAbiertoCliente = alquiler;
			}

		}

		return devuelvoAlquilerAbiertoCliente;

	}

	public Alquiler getAlquilerAbiertoVehiculo(Vehiculo vehiculo) {

		Alquiler devuelvoAlquilerAbiertoVehiculo = null;

		for (Iterator<Alquiler> iterator = coleccionAlquileres.iterator(); devuelvoAlquilerAbiertoVehiculo == null
				&& iterator.hasNext();) {
			Alquiler alquiler = (Alquiler) iterator.next();

			if (alquiler.getVehiculo().equals(vehiculo) && alquiler.getFechaDevolucion() == null) { // con
																									// get.fechadevolucion
																									// indico que
																									// todavia el
																									// alquiler esta
																									// abierto.
				devuelvoAlquilerAbiertoVehiculo = alquiler;
			}
		}
		return devuelvoAlquilerAbiertoVehiculo;

	}

	@Override

	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException { // metodo
																												// devolver
																												// para
																												// el
																												// cliente

		if (cliente == null) { // cambiar mensaje
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}

		Alquiler alquiler = getAlquilerAbiertoCliente(cliente);

		if (alquiler != null) { // si es diferente de null es porque esta abierto y lo devuelvo

			alquiler.devolver(fechaDevolucion);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		}

	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {

		if (vehiculo == null) { // cambiar mensaje
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}

		Alquiler alquiler = getAlquilerAbiertoVehiculo(vehiculo);

		if (alquiler != null) { // si es diferente de null es porque esta abierto y lo devuelvo
			alquiler.devolver(fechaDevolucion);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}

	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {

		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}

		int alquilerIndice = coleccionAlquileres.indexOf(alquiler);

		if (alquilerIndice == -1) { // El -1 en un numerico es como si fuera null, por lo tanto si es diferente de
									// null
			alquiler = null; // Cogemos el indice del alquiler
		} else {
			alquiler = coleccionAlquileres.get(alquilerIndice);

		}

		return alquiler; // devolvemos el valor que hay dentro del indice de la lista.

	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {

		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}

		if (coleccionAlquileres.contains(alquiler)) { // si existe en la lista
			coleccionAlquileres.remove(alquiler);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}

	}

}
