package org.iesalandalus.programacion.alquilervehiculos.vista;

import org.iesalandalus.programacion.alquilervehiculos.vista.texto.VistaTexto;

public enum FactoriaVista {

	TEXTO {
		@Override
		public VistaTexto crear() {
			return new VistaTexto();
		}
	};

	public abstract VistaTexto crear();

}
