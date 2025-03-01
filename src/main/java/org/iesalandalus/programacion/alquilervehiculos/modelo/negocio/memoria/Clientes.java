package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;

public class Clientes implements IClientes {

	private List<Cliente> coleccionClientes;

	// Constructor por defecto
	public Clientes() {
		coleccionClientes = new ArrayList<>();
	}

	// Métodos
	@Override
	public List<Cliente> get() {
		return new ArrayList<>(coleccionClientes);
	}

	@Override
	public int getCantidad() {
		return coleccionClientes.size();
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
		if (coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
		}
		coleccionClientes.add(cliente);
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		/*
		 * Crea el método modificar que permitirá cambiar el nombre o el teléfono (si
		 * estos parámetros no son nulos ni blancos) de un cliente existente y si no
		 * lanzará la correspondiente excepción
		 */

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}

		Cliente clienteAux = buscar(cliente);

		if (clienteAux == null) {

			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}

		if (nombre != null && !nombre.isBlank()) {
			clienteAux.setNombre(nombre);
		}
		if (telefono != null && !telefono.isBlank()) {
			clienteAux.setTelefono(telefono);
		}

	}

	@Override
	public Cliente buscar(Cliente cliente) {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}

		int indice = coleccionClientes.indexOf(cliente);

		Cliente aux = null; // Cliente auxiliar

		if (indice != -1) {
			aux = coleccionClientes.get(indice);
		}
		return aux;
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}
		if (!coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}
		coleccionClientes.remove(buscar(cliente));
	}

}
