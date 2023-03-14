package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String PATRON_FECHA = "dd/MM/yyyy";

	// Constructor por defecto
	private Consola() {

	}

	// Métodos
	public static void mostrarCabecera(String mensaje) {
		// mostrará por pantalla el mensaje pasado por parámetro y
		// luego mostrará un subrayado compuesto de guiones con su misma longitud.
		System.out.printf("%n%s%n", mensaje);
		String formatoStr = "%0" + mensaje.length() + "d%n";
		System.out.println(String.format(formatoStr, 0).replace("0", "-"));
	}

	public static void mostrarMenu() {
		// mostrará una cabecera informando del cometido de la aplicación y
		// mostrará las diferentes opciones del menú.
		mostrarCabecera("BIENVENIDO, ¿QUÉ DESEA HACER?");
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
	}

	private static String leerCadena(String mensaje) {
		// mostrará el mensaje pasado por parámetro y devolverá la cadena que lea por
		// consola.
		System.out.println(mensaje);
		String cadena = Entrada.cadena();
		return cadena;
	}

	private static Integer leerEntero(String mensaje) {
		System.out.println(mensaje);
		int entero = Entrada.entero();
		return entero;
	}

	private static LocalDate leerFecha(String mensaje) {
		System.out.print(mensaje);
		LocalDate fecha = null;
		try {
			fecha = LocalDate.parse(Entrada.cadena(), FORMATO_FECHA);
		} catch (DateTimeParseException e) {
			System.out.printf("%s", e.getMessage());
		}
		return fecha;
	}

	public static Opcion elegirOpcion() {
		// leerá un entero (utilizando el método anteriormente creado)
		// asociado a la opción y devolverá la opción correspondiente.
		// Si el entero introducido no se corresponde con ninguna opción
		// deberá volver a leerlo hasta que éste sea válido.
		int eleccion = 0;
		Opcion opcionElegida = null;
		do {
			try {
				eleccion = leerEntero("Introduzca una opción, por favor: ");
				opcionElegida = Opcion.get(eleccion);
			} catch (IllegalArgumentException e) {
				System.out.printf("%s",e.getMessage());
			}
		} while (opcionElegida == null);
		return opcionElegida;
	}

	public static Cliente leerCliente() {
		return new Cliente(leerNombre(), leerCadena("Introduzca DNI: "), leerTelefono());
	}

	public static Cliente leerClienteDni() {
		return Cliente.getClienteConDni(leerCadena("Introduzca DNI: "));
	}

	public static String leerNombre() {
		return leerCadena("Introduzca el nombre: ");
	}

	public static String leerTelefono() {
		return leerCadena("Introduzca el teléfono de contacto: ");
	}

	private static Vehiculo leerVehiculo() {
		return new Turismo(leerCadena("Introduzca la marca del turismo: "),
				leerCadena("Introduzca el modelo del turismo: "), leerEntero("Introduzca la cilindrada del turismo: "),
				leerCadena("Introduzca la matrícula del turismo: "));
	}

	public static Vehiculo leerVehiculoMatricula() {
		return Vehiculo.getVehiculoConMatricula(leerCadena("Introduzca matrícula: "));
	}

	public static Alquiler leerAlquiler() {
		return new Alquiler(leerClienteDni(), leerVehiculoMatricula(),
				leerFecha("Introduzca la fecha para el alquiler: "));
	}

	public static LocalDate leerFechaDevolucion() {
		return leerFecha("Introduzca la fecha de devolución del alquiler: ");
	}

}
