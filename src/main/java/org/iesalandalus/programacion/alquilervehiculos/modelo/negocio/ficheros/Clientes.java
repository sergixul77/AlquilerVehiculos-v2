package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.naming.OperationNotSupportedException;
import javax.print.DocFlavor.STRING;
import javax.swing.text.Document;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;

// version 1

public class Clientes implements IClientes {

	private static Clientes instancia;

	private static File FICHEROS_CLIENTES = new File(String.format("%s%s%s", "datos", File.separator,"clientes.xmls"));
	private static String RAIZ = "Raiz";
	private static String CLIENTE = "Cliente";
	private static String NOMBRE = "Nombre";
	private static String DNI = "DNI";
	private static String TELEFONO = "Télefono";

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
	
	public void comenzar() {
		
		Document documento = UtilidadesXml.leerXmlDeFichero(FICHEROS_CLIENTES);
		
		if (documento != null) { /*Si el fichero es distinto de null*/
		
			System.out.println("El fichero XML se ha leido correctamente");
			leerDom(documento);
		} else {
			System.out.printf("No se puede leer el fichero: %s. %s",FICHEROS_CLIENTES);
		}

		
		
		
	}

	private void leerDom(Document documentoXml) {
		
		File comienzo = new File((String.format);

	}

	private Cliente getCliente(Element elemento) {

	}

	public void terminar() {

	}

	private Document crearDom() {

	}

	private Element getElemento(Document documentoXml, Cliente cliente) {

	}

	@Override
	public List<Cliente> get() {

		return new ArrayList<>(coleccionClientes);
	}

	@Override
	public int getCantidad() { // tengo que recorrer la lista y incrementar en cada paso

		return coleccionClientes.size(); // devuelvo el tamaño de la coleccion
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
