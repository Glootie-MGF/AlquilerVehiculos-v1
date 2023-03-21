package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;

public class Alquileres implements IAlquileres {

	private List<Alquiler> coleccionAlquileres;

	// Constructor por defecto
	public Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}

	// Métodos
	@Override
	public List<Alquiler> get() {

		return new ArrayList<>(coleccionAlquileres);
	}

	@Override
	public List<Alquiler> get(Cliente cliente) {

		List<Alquiler> listaAuxCliente = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {

				listaAuxCliente.add(alquiler);
			}
		}
		return listaAuxCliente;
	}

	@Override
	public List<Alquiler> get(Vehiculo vehiculo) {

		List<Alquiler> listaAuxVehiculo = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getVehiculo().equals(vehiculo)) {

				listaAuxVehiculo.add(alquiler);
			}
		}
		return listaAuxVehiculo;
	}

	@Override
	public int getCantidad() {
		return coleccionAlquileres.size();
	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {

		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());

		coleccionAlquileres.add(alquiler);
	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {
		/*
		 * Comprobará que en la lista no existe ningún alquiler sin devolver ni para el
		 * cliente ni para el turismo y que tampoco hay un alquiler devuelto, del
		 * cliente o del turismo, con fecha de devolución posterior o igual a la fecha
		 * en la que se pretende alquilar.
		 */
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getFechaDevolucion() == null) {
				// Alquileres sin devolver
				if (cliente.equals(alquiler.getCliente())) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
				}
				if (vehiculo.equals(alquiler.getVehiculo())) {
					throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
				}
				// Alquileres devueltos
			} else if (cliente.equals(alquiler.getCliente()) && alquiler.getFechaDevolucion().compareTo(fechaAlquiler) >= 0) {
				throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
			}
			if (vehiculo.equals(alquiler.getVehiculo()) && alquiler.getFechaDevolucion().compareTo(fechaAlquiler) >= 0) {
				throw new OperationNotSupportedException("ERROR: El turismo tiene un alquiler posterior.");
			}
		}
	}

	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}
		
		Alquiler alquilerBuscado = getAlquilerAbierto(cliente);
		
		if (alquilerBuscado == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		}
		coleccionAlquileres.get(coleccionAlquileres.indexOf(alquilerBuscado)).devolver(fechaDevolucion);
	}

	private Alquiler getAlquilerAbierto(Cliente cliente) {
		
		Alquiler alquilerAbierto = null;

		for (Iterator<Alquiler> iterador = coleccionAlquileres.iterator(); iterador.hasNext();) {
			
			Alquiler alquiler = iterador.next();
			
			if (alquiler.getCliente().equals(cliente) && alquiler.getFechaDevolucion() == null) {
				alquilerAbierto = alquiler;
			}
		}
		return alquilerAbierto;
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		
		Alquiler alquilerBuscado = getAlquilerAbierto(vehiculo);
		
		if (alquilerBuscado == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		coleccionAlquileres.get(coleccionAlquileres.indexOf(alquilerBuscado)).devolver(fechaDevolucion);
	}

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		
		Alquiler alquilerAbierto = null;

		for (Iterator<Alquiler> iterador = coleccionAlquileres.iterator(); iterador.hasNext();) {
			
			Alquiler alquiler = iterador.next();
			
			if (alquiler.getVehiculo().equals(vehiculo) && alquiler.getFechaDevolucion() == null) {
				alquilerAbierto = alquiler;
			}
		}
		return alquilerAbierto;
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		int indice = coleccionAlquileres.indexOf(alquiler);
		
		Alquiler aux = null; // Alquiler auxiliar

		if (indice != -1) {
			aux = coleccionAlquileres.get(indice);
		}
		return aux;
	}
	
	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		if (!coleccionAlquileres.contains(alquiler)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		coleccionAlquileres.remove(buscar(alquiler));
	}

}
