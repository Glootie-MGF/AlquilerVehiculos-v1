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

	//Métodos
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
		} else {
			coleccionClientes.add(cliente);
			//Cuando tengamos que crear nuevas instancias entonces:
			//coleccionClientes.add(new Cliente(cliente));
		}
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}
		
		int indice = coleccionClientes.indexOf(cliente);
		//Cliente auxiliar
		Cliente aux = null;
		
		if (indice != -1) {
			aux = coleccionClientes.get(indice);
			//aux = new Cliente(coleccionClientes.get(indice));
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
		coleccionClientes.remove(cliente);
	}
	
	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		// Crea el método modificar que permitirá cambiar el nombre o el teléfono
		// (si estos parámetros no son nulos ni blancos) de un cliente existente y
		// si no lanzará la correspondiente excepción
		
		//		if (buscar(cliente) != null) {
		//			if (nombre != null || !nombre.isBlank()) {
		//				cliente.setNombre(nombre);	
		//			}
		//			if (telefono != null || !telefono.isBlank()) {
		//				cliente.setTelefono(telefono);	
		//			}
		//		}else {
		//			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		//		}
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}
		if (coleccionClientes.contains(cliente)) {
			if (nombre != null && !nombre.isBlank()) {
				buscar(cliente).setNombre(nombre);
			}
			if (telefono != null && !telefono.isBlank()) {
				buscar(cliente).setTelefono(telefono);
			}
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}
	}
	
}
