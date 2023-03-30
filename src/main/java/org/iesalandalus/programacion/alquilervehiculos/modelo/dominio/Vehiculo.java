package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.util.Objects;

public abstract class Vehiculo {

	private static final String ER_MARCA = "([A-Z][a-z]+([ -]?[A-Z][a-z]+)?)|[A-Z]+";
	private static final String ER_MATRICULA = "\\d{4}[B-DF-HJ-NP-TV-Z]{3}";
	private String marca;
	private String modelo;
	private String matricula;

	protected Vehiculo(String marca, String modelo, String matricula) {

		setMarca(marca);
		setMatricula(matricula);
		setModelo(modelo);

	}

	protected Vehiculo(Vehiculo vehiculo) {

		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No es posible copiar un vehículo nulo.");
		}

		marca = vehiculo.getMarca();
		modelo = vehiculo.getModelo();
		matricula = vehiculo.getMatricula();

	}

	public static Vehiculo getVehiculoConMatricula(String matricula) {

		return new Turismo("Toyota", "supra", 40, matricula);

	}

	public abstract int getFactorPrecio();

	public String getMarca() {
		return marca;
	}

	private void setMarca(String marca) {

		if (marca == null) {
			throw new NullPointerException("ERROR: La marca no puede ser nula.");
		}
		if (!marca.matches(ER_MARCA)) {
			throw new IllegalArgumentException("ERROR: La marca no tiene un formato válido.");
		}

		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	private void setModelo(String modelo) {

		if (modelo == null) {
			throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
		}
		if (modelo.isBlank()) { // isBlank elimina los espacios.
			throw new IllegalArgumentException("ERROR: El modelo no puede estar en blanco.");
		}

		this.modelo = modelo;
	}

	public String getMatricula() {
		return matricula;
	}

	private void setMatricula(String matricula) {

		if (matricula == null) {
			throw new NullPointerException("ERROR: La matrícula no puede ser nula.");
		}
		if (!matricula.matches(ER_MATRICULA)) {
			throw new IllegalArgumentException("ERROR: La matrícula no tiene un formato válido.");
		}
		this.matricula = matricula;
	}

	public static Vehiculo copiar(Vehiculo vehiculo) {

		Vehiculo vehiculoCopiado = null;
		if (vehiculo instanceof Turismo turismo) { // verifica si la variable vehiculo es una instancia de la clase
													// Turismo y, si es cierto, crea una nueva variable llamada turismo
													// que es del tipo Turismo
			vehiculoCopiado = new Turismo(turismo);// creo un nuevo Turismo
		}
		if (vehiculo instanceof Autobus autobus) {
			vehiculoCopiado = new Autobus(autobus);
		}
		if (vehiculo instanceof Furgoneta furgoneta) {
			vehiculoCopiado = new Furgoneta(furgoneta);
		}

		return vehiculoCopiado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(matricula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Vehiculo))
			return false;
		Vehiculo other = (Vehiculo) obj;
		return Objects.equals(matricula, other.matricula);
	}

}