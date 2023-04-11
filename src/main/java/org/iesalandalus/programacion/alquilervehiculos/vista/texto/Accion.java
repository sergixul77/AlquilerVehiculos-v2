package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

// version 1

public enum Accion {

	SALIR("Salir") {
		@Override
		public void ejecutar() {
			vista.terminar();

		}
	},
	INSERTAR_CLIENTE("Insertar cliente") {
		@Override
		public void ejecutar() {
			vista.insertarCliente();

		}
	},
	INSERTAR_VEHICULO("Insertar vehículos") {
		@Override
		public void ejecutar() {
			vista.insertarVehiculo();

		}
	},
	INSERTAR_ALQUILER("Insertar alquiler") {
		@Override
		public void ejecutar() {
			vista.insertarAlquiler();

		}
	},
	BUSCAR_CLIENTE("Buscar cliente") {
		@Override
		public void ejecutar() {
			vista.buscarCliente();

		}
	},
	BUSCAR_VEHICULO("Buscar vehículo") {
		@Override
		public void ejecutar() {
			vista.buscarVehicuo();

		}
	},
	BUSCAR_ALQUILER("Buscar alquiler") {
		@Override
		public void ejecutar() {
			vista.buscarAlquiler();

		}
	},
	MODIFICAR_CLIENTE("Modificar cliente") {
		@Override
		public void ejecutar() {
			vista.modificarCliente();

		}
	},
	DEVOLVER_ALQUILER_CLIENTE("Devolver alquiler de un cliente") {
		@Override
		public void ejecutar() {
			vista.devolverAlquilerCliente();

		}
	},
	DEVOLVER_ALQUILER_VEHICULO("Devolver alquiler de un Vehículo") {
		@Override
		public void ejecutar() {
			vista.devolverAlquilerVehiculo();

		}
	},
	BORRAR_CLIENTE("Borrar cliente") {
		@Override
		public void ejecutar() {
			vista.borrarCliente();

		}
	},
	BORRAR_VEHICULO("Borrar vehículo") {
		@Override
		public void ejecutar() {
			vista.borrarVehiculo();

		}
	},
	BORRAR_ALQUILER("Borrar alquiler") {
		@Override
		public void ejecutar() {
			vista.borrarAlquiler();

		}
	},
	LISTAR_CLIENTES("Listar clientes") {
		@Override
		public void ejecutar() {
			vista.listarClientes();

		}
	},
	LISTAR_VEHICULOS("Listar vehículos") {
		@Override
		public void ejecutar() {
			vista.listarVehiculos();

		}
	},
	LISTAR_ALQUILERES("Listar alquileres") {
		@Override
		public void ejecutar() {
			vista.listarAlquileres();

		}
	},
	LISTAR_ALQUILERES_CLIENTE("Listar alquiler de un cliente") {
		@Override
		public void ejecutar() {
			vista.listarAlquileresClientes();

		}
	},
	LISTAR_ALQUILERES_VEHICULO("Lista alquiler de un vehículo") {
		@Override
		public void ejecutar() {
			vista.listarAlquileresVehiculo();

		}
	},
	MOSTRAR_ESTADISTICAS_MENSUALES("Mostrar estadisticas mensuales") {
		@Override
		public void ejecutar() {
			vista.mostrarEstadisticasMensualesTipoVehiculo();

		}
	};

	private String texto;

	private static VistaTexto vista;

	private Accion(String texto) {

		this.texto = texto;

	}

	static void setVista(VistaTexto vista) {

		Accion.vista = vista;

	}

	public abstract void ejecutar();

	private static boolean esOrdinalValido(int ordinal) {

		return ordinal >= 0 && ordinal < Accion.values().length;

	}

	public static Accion get(int ordinal) {

		if (!esOrdinalValido(ordinal)) { // si el ordinal pasado no es correcto pues me salta un mensaje de error
			throw new IllegalArgumentException("El ordinal que has pasado no es valido.");
		}

		return Accion.values()[ordinal];
	}

	@Override
	public String toString() {
		return String.format("%s. %s", ordinal(), texto);

	}

}
