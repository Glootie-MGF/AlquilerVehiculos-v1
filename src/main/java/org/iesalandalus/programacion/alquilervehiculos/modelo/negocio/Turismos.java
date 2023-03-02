package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public class Turismos {

	private List<Turismo> coleccionTurismos; 

	// Constructor por defecto
	public Turismos() {
		coleccionTurismos = new ArrayList<>();
	}

	public List<Turismo> get() {
		return new ArrayList<>(coleccionTurismos);
	}

	public int getCantidad() {
		return coleccionTurismos.size();
	}

	public void insertar(Turismo turismo) throws OperationNotSupportedException {
		if (turismo == null) {
			throw new NullPointerException("ERROR: No se puede insertar un turismo nulo.");
		}
		if (coleccionTurismos.contains(turismo)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un turismo con esa matrícula.");
		} else {
			coleccionTurismos.add(turismo);
			// Cuando tengamos que crear nuevas instancias entonces:
			// coleccionClientes.add(new Cliente(cliente));
		}
	}

	public Turismo buscar(Vehiculo turismo) {
		if (turismo == null) {
			throw new NullPointerException("ERROR: No se puede buscar un turismo nulo.");
		}

		int indice = coleccionTurismos.indexOf(turismo);
		// Turismo auxiliar
		Turismo aux = null;

		if (indice != -1) {
			aux = coleccionTurismos.get(indice);
			// aux = new Cliente(coleccionClientes.get(indice));
		}
		return aux;
	}

	public void borrar(Vehiculo turismo) throws OperationNotSupportedException {
		if (turismo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un turismo nulo.");
		}
		if (!coleccionTurismos.contains(turismo)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún turismo con esa matrícula.");
		}
		coleccionTurismos.remove(turismo);
	}

}
