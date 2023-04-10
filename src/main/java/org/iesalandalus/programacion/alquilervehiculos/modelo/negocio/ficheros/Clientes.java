package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// version 1

public class Clientes implements IClientes {

	private static Clientes instancia;
	
	 static final File FICHEROS_CLIENTES = new File(
			String.format("%s%s%s", "datos", File.separator, "clientes.xml"));
	private static final String RAIZ = "clientes";
	private static final String CLIENTE = "cliente";
	private static final String NOMBRE = "nombre";
	private static final String DNI = "dni";
	private static final String TELEFONO = "telefono";

	private List<Cliente> coleccionClientes;

	private Clientes() {
		coleccionClientes = new ArrayList<>(); // creando la lista
	}

	static Clientes getInstancia() {

		if (instancia == null) {

			instancia = new Clientes();
		}

		return instancia;
	}

//	para que al comenzar lea el fichero XML de clientes, lo almacene en un una lista

	@Override
	public void comenzar() {

		Document documento = UtilidadesXml.leerXmlDeFichero(FICHEROS_CLIENTES);

		if (documento != null) { /* Si el fichero es distinto de null */

			System.out.println("El fichero XML se ha leido correctamente");
			leerDom(documento);
		} else {
			System.out.printf("No se puede leer el fichero: %s. %n", FICHEROS_CLIENTES);
		}

	}

	private void leerDom(Document documentoXml) {

		NodeList clientes = documentoXml.getElementsByTagName(CLIENTE);
		for (int i = 0; i < clientes.getLength(); i++) {
			Node cliente = clientes.item(i);
			if (cliente.getNodeType() == Node.ELEMENT_NODE) {

				try {
					insertar(getCliente((Element) cliente)); // le hacemos casting a cliente de tipo node para que sea
																// un elemento
				} catch (OperationNotSupportedException | NullPointerException e) {

					System.out.println(e.getMessage());

				}

			}
		}

	}

	private Cliente getCliente(Element elemento) {

		String dni = elemento.getAttribute(DNI);
		String nombre = elemento.getAttribute(NOMBRE);
		String telefono = elemento.getAttribute(TELEFONO);

		return new Cliente(nombre, dni, telefono);
	}

	@Override
	public void terminar() {

		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHEROS_CLIENTES);

	}

	private Document crearDom() {

		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Cliente cliente : coleccionClientes) {
				Element elementoCliente = getElemento(documentoXml, cliente);
				documentoXml.getDocumentElement().appendChild(elementoCliente);
			}
		}
		return documentoXml;

	}

	private Element getElemento(Document documentoXml, Cliente cliente) {

		Element elementoCliente = documentoXml.createElement(CLIENTE);
		elementoCliente.setAttribute(NOMBRE, cliente.getNombre());
		elementoCliente.setAttribute(DNI, cliente.getDni());
		elementoCliente.setAttribute(TELEFONO, cliente.getTelefono());
		return elementoCliente;

	}

	@Override
	public List<Cliente> get() {

		return new ArrayList<>(coleccionClientes);
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
		if (!coleccionClientes.contains(cliente)) {
			coleccionClientes.add(cliente);
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
		}
	}

	@Override
	public Cliente buscar(Cliente cliente) {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}

		int indiceCliente = coleccionClientes.indexOf(cliente);

		Cliente clienteList = null;

		if (indiceCliente != -1) { // El -1 en un numerico es como si fuera null

			clienteList = coleccionClientes.get(indiceCliente);
		}

		return clienteList;

	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}

		if (coleccionClientes.contains(cliente)) { // si existe el cliente.

			coleccionClientes.remove(cliente);

		} else { // si el cliente no existe
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}

	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}

		Cliente clienteEncontrado = buscar(cliente); // esto busca al cliente.

		if (clienteEncontrado == null) { // si el cliente que buscamos no esta en la lista salta la excepcion
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}

		if (telefono != null && !telefono.isBlank()) { // si el telefono no es nulo ni tampoco tiene espacios en blanco

			clienteEncontrado.setTelefono(telefono);

		}
		if (nombre != null && !nombre.isBlank()) { // si el nombre es diferente a nulo y no tiene espacios ya que lo
													// quito con el isBlank

			clienteEncontrado.setNombre(nombre);
		}

	}

}
