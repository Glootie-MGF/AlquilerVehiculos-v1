package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import javax.naming.OperationNotSupportedException;

public class Alquiler {

	static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final int PRECIO_DIA = 20;

	private LocalDate fechaAlquiler;
	private LocalDate fechaDevolucion;
	private Vehiculo vehiculo;
	private Cliente cliente;

	// Constructor con parámetros
	public Alquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler) {
		setFechaAlquiler(fechaAlquiler);
		setVehiculo(vehiculo);
		setCliente(cliente);
	}

	// Constructor copia
	public Alquiler(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alquiler nulo.");
		}
		fechaAlquiler = (alquiler.getFechaAlquiler());
		fechaDevolucion = (alquiler.getFechaDevolucion());
		vehiculo = (Vehiculo.copiar(alquiler.getVehiculo()));
		cliente = (new Cliente(alquiler.getCliente()));
	}

	// Métodos
	public void devolver(LocalDate fechaDevolucion) throws OperationNotSupportedException {
		// se encargará de asignar la fecha de devoluión si ésta es correcta
		if (this.fechaDevolucion != null) {
			throw new OperationNotSupportedException("ERROR: La devolución ya estaba registrada.");
		}
		setFechaDevolucion(fechaDevolucion);
	}

	public int getPrecio() {
		// devolverá el precio del alquiler conforme a la fórmula establecida por
		// nuestro cliente
		// (precioDia + factorCilindrada) * numDias
		// factorCilindrada = cilindrada del turismo / 10
		// numDias = fechaDevolucion-fechaAlquiler
		int precio = -1;

		if (fechaDevolucion == null) {
			precio = 0;
		} else {
			Duration numDias = Duration.between(fechaAlquiler.atStartOfDay(), fechaDevolucion.atStartOfDay());
			precio = (int) ((PRECIO_DIA + vehiculo.getFactorPrecio()) * numDias.toDays());
		}
		return precio;
	}

	// Métodos get y set
	public LocalDate getFechaAlquiler() {
		return fechaAlquiler;
	}

	private void setFechaAlquiler(LocalDate fechaAlquiler) {
		if (fechaAlquiler == null) {
			throw new NullPointerException("ERROR: La fecha de alquiler no puede ser nula.");
		}
		if (fechaAlquiler.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de alquiler no puede ser futura.");
		}
		this.fechaAlquiler = fechaAlquiler;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	private void setFechaDevolucion(LocalDate fechaDevolucion) {
		if (fechaDevolucion == null) {
			throw new NullPointerException("ERROR: La fecha de devolución no puede ser nula.");
		}
		// La fecha de devolución no puede ser igual o anterior a la fecha de alquiler
		// y tampoco puede ser posterior a hoy
		if (fechaDevolucion.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de devolución no puede ser futura.");
		}
		if (fechaDevolucion.isEqual(fechaAlquiler) || fechaDevolucion.isBefore(fechaAlquiler)) {
			throw new IllegalArgumentException(
					"ERROR: La fecha de devolución debe ser posterior a la fecha de alquiler.");
		}
		this.fechaDevolucion = fechaDevolucion;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	private void setVehiculo(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: El vehículo no puede ser nulo.");
		}
		this.vehiculo = vehiculo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	private void setCliente(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: El cliente no puede ser nulo.");
		}
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, fechaAlquiler, fechaDevolucion, vehiculo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Alquiler))
			return false;
		Alquiler other = (Alquiler) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(fechaAlquiler, other.fechaAlquiler)
				&& Objects.equals(fechaDevolucion, other.fechaDevolucion) && Objects.equals(vehiculo, other.vehiculo);
	}

	// @Override
	// public int hashCode() {
	// return Objects.hash(cliente, fechaAlquiler, fechaDevolucion, vehiculo);
	// }
	// @Override
	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	// if (getClass() != obj.getClass())
	// return false;
	// Alquiler other = (Alquiler) obj;
	// return Objects.equals(cliente, other.cliente) &&
	// Objects.equals(fechaAlquiler, other.fechaAlquiler)
	// && Objects.equals(fechaDevolucion, other.fechaDevolucion) &&
	// Objects.equals(vehiculo, other.vehiculo);
	// }
	@Override
	public String toString() {
		if (fechaDevolucion == null) {
			return String.format("%s <---> %s, %s - %s (%d€)", cliente, vehiculo, fechaAlquiler.format(FORMATO_FECHA),
					"Aún no devuelto", 0);
		}
		return String.format("%s <---> %s, %s - %s (%d€)", cliente, vehiculo, fechaAlquiler.format(FORMATO_FECHA),
				fechaDevolucion.format(FORMATO_FECHA), getPrecio());
	}

}
