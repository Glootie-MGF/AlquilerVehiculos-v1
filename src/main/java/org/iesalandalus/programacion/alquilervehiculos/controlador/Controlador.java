package org.iesalandalus.programacion.alquilervehiculos.controlador;

import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class Controlador {

	/*
	 * Crea la clase Controlador que será la encargada de hacer de intermediario
	 * entre la vista y el modelo. Crea los atributos adecuados. Crea el constructor
	 * con parámetros que comprobará que no son nulos y los asignará a los
	 * atributos. Además debe llamar al método setVista de la vista con una
	 * instancia suya. Crea los métodos comenzar y terminar, que llamarán a los
	 * correspondientes métodos en el modelo y en la vista. Crea los demás métodos
	 * que simplemente harán una llamada al correspondiente método del modelo.
	 */
	private Modelo modelo;
	private Vista vista;

	public Controlador(Modelo modelo, Vista vista) {
		if (modelo == null) {
			throw new NullPointerException("ERROR: El modelo NO puede ser nulo.");
		}

		if (vista == null) {
			throw new NullPointerException("ERROR: La vista NO puede ser nula.");
		}
		this.modelo = modelo;
		this.vista = vista;
		vista.setControlador(this);
	}
	
	public void comenzar() {
		modelo.comenzar();
		vista.comenzar();
	}

	public void terminar() {
		modelo.terminar();
		vista.terminar();
		System.out.printf("%n¡Chao Bacalao!");
	}
}
