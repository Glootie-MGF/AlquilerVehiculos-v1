package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public class Alquileres {

	private List<Alquiler> coleccionAlquileres;

	// Constructor por defecto
	public Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}

	// Métodos
	public List<Alquiler> get() {
		return new ArrayList<>(coleccionAlquileres);
	}

	public List<Alquiler> get(Cliente cliente) {
		List<Alquiler> listaAuxCliente = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				listaAuxCliente.add(alquiler);
			}
		}
		return listaAuxCliente;
	}

	public List<Alquiler> get(Vehiculo turismo) {
		List<Alquiler> listaAuxTurismo = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getTurismo().equals(turismo)) {
				listaAuxTurismo.add(alquiler);
			}
		}
		return listaAuxTurismo;
	}

	public int getCantidad() {
		return coleccionAlquileres.size();
	}

	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		comprobarAlquiler(alquiler.getCliente(),alquiler.getTurismo(),alquiler.getFechaAlquiler());	
		coleccionAlquileres.add(alquiler);
			// Cuando tengamos que crear nuevas instancias entonces:
			// coleccionClientes.add(new Cliente(cliente));
	}

	private void comprobarAlquiler (Cliente cliente, Vehiculo turismo, LocalDate fechaAlquiler) throws OperationNotSupportedException {
		//comprobará que en la lista no existe ningún alquiler sin devolver 
		//ni para el cliente ni para el turismo y que tampoco hay un alquiler devuelto, 
		//del cliente o del turismo, con fecha de devolución posterior(after) o igual  
		//a la fecha en la que se pretende alquilar.
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getFechaDevolucion() == null) {
				//alquileres sin devolver
				if (cliente.equals(alquiler.getCliente())) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
				}
				if (turismo.equals(alquiler.getTurismo())) {
					throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
				}
			}else {
				//Alquileres devueltos
				if (cliente.equals(alquiler.getCliente()) && (alquiler.getFechaDevolucion().isAfter(fechaAlquiler) || alquiler.getFechaDevolucion().isEqual(fechaAlquiler))) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
				}
				if (turismo.equals(alquiler.getTurismo()) && (alquiler.getFechaDevolucion().isAfter(fechaAlquiler) || alquiler.getFechaDevolucion().isEqual(fechaAlquiler))) {
					throw new OperationNotSupportedException("ERROR: El turismo tiene un alquiler posterior.");
				}
			}
		}
	}
	public void devolver (Alquiler alquiler, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		//Devolverá (asignará la fecha de devolución) si éste existe en 
		//la lista o lanzará la excepción en caso contrario.
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
		}
		if (buscar(alquiler) != null) {
			alquiler.devolver(fechaDevolucion);
		}else {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
	}

	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		if (!coleccionAlquileres.contains(alquiler)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		coleccionAlquileres.remove(alquiler);
	}

	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}

		int indice = coleccionAlquileres.indexOf(alquiler);
		// Alquiler auxiliar
		Alquiler aux = null;

		if (indice != -1) {
			aux = coleccionAlquileres.get(indice);
			// aux = new Cliente(coleccionClientes.get(indice));
		}
		return aux;
	}

}
