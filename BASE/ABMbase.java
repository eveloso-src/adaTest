package BASE;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class ABMbase {
	public static void main(String[] args) {

		Connection conexion = null;
		try {
			conexion = ConecctionABM.obtenerConexion();
			Scanner sc = new Scanner(System.in);

			int opcion = mostrarMenu(sc);
			while (opcion != 0) {

				switch (opcion) {
				case 1:
					alta(conexion, sc);
					break;
				case 2:
					modificacion(conexion, sc);
					break;
				case 3:
					baja(conexion, sc);
					break;
				case 4:
					listado(conexion);
					break;
				case 5:
					buscarRegistro(conexion, sc);
					break;
				case 0:

					break;

				default:
					System.out.println("ERROR");
					System.out.println("=====");
					System.out.println("Ingrese numero valido");
					System.out.println("");
					break;
				}
				opcion = mostrarMenu(sc);
			}

			conexion.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR");
			System.out.println("=====");
			System.out.println("Error de conexión");
			System.out.println("");
		}
	}

	private static void buscarRegistro(Connection conexion, Scanner sc) {
		System.out.println();
		System.out.println("BUSQUEDA DE REGISTRO");
		System.out.println("====================");
		System.out.println("MENU OPCIONES: ");
		System.out.println("");
		System.out.println("1: BUSCAR POR NOMBRE ");
		System.out.println("2: BUSCAR POR EDAD ");
		int opcionDos = sc.nextInt();

		Statement stmt;
		try {

			switch (opcionDos) {
			case 1:
				System.out.println("INGRESA LA LETRA O EL NOMBRE DEL USUARIO QUE QUIERE BUSCAR: ");
				String nomCli = sc.next();
				stmt = conexion.createStatement();
				System.out.println("");
				System.out.println("RESULTADOS------------");
				ResultSet rs = stmt.executeQuery("SELECT * FROM cliente WHERE `NOMBRE` LIKE'" + nomCli + "%';");
				while (rs.next()) {
					Date fNac = rs.getDate(4);
					System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + fNac);
				}
				System.out.println("FIN------------");
				System.out.println();

				break;
			case 2:
				System.out.println("INGRESA EL PRIMER RANGO DE EDAD QUE QUIERE BUSCAR: ");
				int edadUno = sc.nextInt();
				System.out.println("INGRESA EL SEGUNDO RANGO DE EDAD QUE QUIERE BUSCAR: ");
				int edadDos = sc.nextInt();
				stmt = conexion.createStatement();

				System.out.println("");
				System.out.println("RESULTADOS------------");
				rs = stmt.executeQuery(
						"SELECT * FROM cliente WHERE `EDAD` BETWEEN " + edadUno + " AND " + edadDos + ";");
				while (rs.next()) {
					Date fNac = rs.getDate(4);
					System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + fNac);
				}
				System.out.println("FIN LISTADO------------");
				System.out.println();

				break;
			default:
				System.out.println("ERROR");
				System.out.println("=====");
				System.out.println("Ingresar una opcion correcta");
				System.out.println("");
				break;
			}
		} catch (SQLException e) {
			System.out.println("ERROR");
			System.out.println("=====");
			System.out.println("No se ha podido buscar correctamente");
			System.out.println("");
		}

	}

	private static void listado(Connection conexion) {
		System.out.println();
		System.out.println("LISTADO--------------------");
		Statement stmt;
		try {

			stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM cliente");
			while (rs.next()) {
				Date fNac = rs.getDate(4);
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + fNac);
			}
			System.out.println("FIN LISTADO------------");
			System.out.println();
		} catch (SQLException e) {
		}

	}

	private static void baja(Connection conexion, Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("BAJA DE PERSONA");
		System.out.println("===============");

		listado(conexion);

		System.out.println("INGRESA EL ID DEL USUARIO QUE QUIERE ELIMINAR: ");
		int numCli = sc.nextInt();

		Statement stmt;
		try {
			stmt = conexion.createStatement();
			System.out.println("ESTA SEGURO QUE QUIERE ELIMINAR EL SIGUENTE USUARIO");
			ResultSet rs = stmt.executeQuery("SELECT * FROM `ada`.`cliente` WHERE ID=" + numCli + ";");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4));
			}
			System.out.println("----------------------");
			System.out.println("1 ELIMAR 2 NO ELIMINAR");
			int eleccion = sc.nextInt();
			if (eleccion == 1) {
				String eliminaUsuario = "DELETE FROM `ada`.`cliente` " + " WHERE `ID`='" + numCli + "';";
				stmt.execute(eliminaUsuario);
				System.out.println("");
				System.out.println("EL USUARIO SE HA ELIMINADO CORRECTAMENTE");
			} else {
				System.out.println("EL USUARIO NO FUE ELIMINADO");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR");
			System.out.println("=====");
			System.out.println("Error en BAJA DE USUARIO");
			System.out.println("");
		}

	}

	private static void modificacion(Connection conexion, Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("MODIFICACION DE PERSONA");
		System.out.println("=======================");
		System.out.println("MENU OPCIONES: ");
		System.out.println("");
		System.out.println("1: MODIFICAR NOMBRE ");
		System.out.println("2: MODIFICAR FECHA NACIMIENTO ");
		int opcionDos = sc.nextInt();

		String nombreModificado = null;
		String fechaModificado = null;
		int cliModificado = 0;
		int anios = 0;
		listado(conexion);

		Statement stmt;
		try {

			stmt = conexion.createStatement();

			switch (opcionDos) {
			case 1:
				System.out.println("INGRESA EL ID DEL USUARIO QUE QUIERE MODIFICAR: ");
				int numCli = sc.nextInt();
				cliModificado = numCli;
				System.out.println("Ingrese nombre:");
				String nombre = sc.next();
				nombreModificado = nombre;
				break;
			case 2:
				System.out.println("INGRESA EL ID DEL USUARIO QUE QUIERE MODIFICAR: ");
				numCli = sc.nextInt();
				cliModificado = numCli;
				System.out.println("Ingrese fecha nacimiento (yyyy-MM-dd):");
				String fNac = sc.next();
				fechaModificado = fNac;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date fechaNac = sdf.parse(fechaModificado);
				int edad = calcularEdad(fechaNac);
				anios = edad;
				break;
			default:
				System.out.println("ERROR");
				System.out.println("=====");
				System.out.println("Ingresar una opcion correcta");
				System.out.println("");
				break;
			}

			System.out.println("ESTA SEGURO QUE QUIERE MODIFICAR EL SIGUENTE USUARIO");
			ResultSet rs = stmt.executeQuery("SELECT * FROM `ada`.`cliente` WHERE ID=" + cliModificado + ";");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4));
			}
			System.out.println("----------------------");
			System.out.println("1 MODIFICAR 2 NO MODIFICAR");
			int eleccion = sc.nextInt();
			switch (eleccion) {
				case 1:
					if(opcionDos == 1) {
						String modifNombre = " UPDATE `ada`.`cliente`" + " SET  `NOMBRE`='" + nombreModificado
							+ "' WHERE `ID`='" + cliModificado + "';";
					stmt.executeUpdate(modifNombre);
					System.out.println("EL USUARIO ID = " + cliModificado + " HA CAMBIADO SU NOMBRE");
					rs = stmt.executeQuery("SELECT * FROM `ada`.`cliente` WHERE ID=" + cliModificado + ";");
					while (rs.next()) {
						System.out.println("ID = " + rs.getInt(1) + ", NOMBRE = " + rs.getString(2));
					}
					} else {
						if (opcionDos == 2) {
							String modifEdad = " UPDATE `ada`.`cliente`" + " SET  `EDAD`='" + anios + "', `FECHA_NACIMIENTO`='"
									+ fechaModificado + "' WHERE `ID`='" + cliModificado + "';";
							stmt.executeUpdate(modifEdad);
							System.out.println("EL USUARIO ID = " + cliModificado + " HA CAMBIADO SU EDAD Y FECHA DE NACIMIENTO");
							rs = stmt.executeQuery("SELECT * FROM `ada`.`cliente` WHERE ID=" + cliModificado + ";");
							while (rs.next()) {
								System.out.println("ID = " + rs.getInt(1) + ", NOMBRE = " + rs.getString(2) + " EDAD = "
										+ rs.getInt(3) + " FECHA NACIMIENTO = " + rs.getString(4));
							}
				}
					}
			break;
				case 2:
					break;
				default:
					System.out.println("ERROR");
					System.out.println("=====");
					System.out.println("Ingresar una opcion correcta");
					System.out.println("");
					break;
			
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR");
			System.out.println("=====");
			System.out.println("Error en MODIFICACIÓN DE USUARIO");
			System.out.println("");
		} catch (ParseException e) {
			System.out.println("ERROR");
			System.out.println("=====");
			System.out.println("La fecha ingresada es incorrecta");
			System.out.println("");
		}
	}

	private static void alta(Connection conexion, Scanner sc) {
		System.out.println();
		System.out.println("ALTA DE PERSONA");
		System.out.println("===============");
		System.out.println("Ingrese nombre:");
		String nombre = sc.next();
		int anios = 0;
		System.out.println("Ingrese fecha nacimiento (yyyy-MM-dd):");
		String fNac = sc.next();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date fechaNac = sdf.parse(fNac);
			int edad = calcularEdad(fechaNac);
			anios = edad;
		} catch (ParseException e1) {
			System.out.println("ERROR");
			System.out.println("=====");
			System.out.println("La fecha ingresada es incorrecta");
			System.out.println("");
		}

		Statement stmt;
		try {
			stmt = conexion.createStatement();
			String insert = "INSERT INTO ada.cliente (NOMBRE, EDAD, FECHA_NACIMIENTO)\n" + "  VALUES ('" + nombre + "',"
					+ anios + ", '" + fNac + "');";
			stmt.executeUpdate(insert);
			System.out.println("El usuario se ha dado ALTA correctamente");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR");
			System.out.println("=====");
			System.out.println("Error en ALTA DE USUARIO");
			System.out.println("");

		}

	}

	private static int calcularEdad(Date fechaNac) {
		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar hoy = new GregorianCalendar();
		gc.setTime(fechaNac);
		int anioActual = hoy.get(Calendar.YEAR);
		int anioNacim = gc.get(Calendar.YEAR);

		int mesActual = hoy.get(Calendar.MONTH);
		int mesNacim = gc.get(Calendar.MONTH);

		int diaActual = hoy.get(Calendar.DATE);
		int diaNacim = gc.get(Calendar.DATE);

		int dif = anioActual - anioNacim;

		if (mesActual < mesNacim) {
			dif = dif - 1;
		} else {
			if (mesActual == mesNacim && diaActual < diaNacim) {
				dif = dif - 1;
			}
		}

		return dif;
	}

	private static int mostrarMenu(Scanner sc) {

		System.out.println("");
		System.out.println("SISTEMA DE PERSONAS (ABM)");
		System.out.println("=========================");

		System.out.println("");
		System.out.println("MENU OPCIONES: ");
		System.out.println("");
		System.out.println("1: ALTA ");
		System.out.println("2: MODIFICACION ");
		System.out.println("3: BAJA");
		System.out.println("4: LISTADO");
		System.out.println("5: BUSQUEDA");
		System.out.println("0: SALIR");
		int opcion = 0;
		opcion = sc.nextInt();
		return opcion;
	}
}
