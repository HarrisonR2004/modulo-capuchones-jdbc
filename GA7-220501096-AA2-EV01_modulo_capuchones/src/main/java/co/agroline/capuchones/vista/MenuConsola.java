package co.agroline.capuchones.vista;

import co.agroline.capuchones.datos.CapuchonDAO;
import co.agroline.capuchones.modelo.Capuchon;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Menu por consola para administrar el catalogo de capuchones.
 * Autor: Harrison Rodriguez Galindo - Ficha 3336080
 */
public class MenuConsola {

    private final Scanner teclado = new Scanner(System.in);
    private final CapuchonDAO dao = new CapuchonDAO();

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println();
            System.out.println("=== AGRO LINE - CATALOGO DE CAPUCHONES ===");
            System.out.println("1. Registrar capuchon");
            System.out.println("2. Listar capuchones");
            System.out.println("3. Buscar capuchon");
            System.out.println("4. Actualizar capuchon");
            System.out.println("5. Eliminar capuchon");
            System.out.println("0. Salir");
            opcion = leerEntero("Opcion: ");

            try {
                switch (opcion) {
                    case 1 -> registrar();
                    case 2 -> listar();
                    case 3 -> buscar();
                    case 4 -> actualizar();
                    case 5 -> eliminar();
                    case 0 -> System.out.println("Hasta luego");
                    default -> System.out.println("Opcion no valida");
                }
            } catch (SQLException e) {
                System.out.println("Error de base de datos: " + e.getMessage());
            }
        }
    }

    private void registrar() throws SQLException {
        Capuchon c = new Capuchon();
        c.setValorBd(leerTexto("Valor en BD (ej. TRANSPARENTE): ").toUpperCase());
        if (dao.buscarPorValor(c.getValorBd()) != null) {
            System.out.println("Ya existe un capuchon con ese valor");
            return;
        }
        c.setNombreVisible(leerTexto("Nombre visible: "));
        c.setColorFondo(leerColor());
        c.setActivo(true);
        if (dao.insertar(c)) {
            System.out.println("Capuchon registrado");
        }
    }

    private void listar() throws SQLException {
        List<Capuchon> lista = dao.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay capuchones registrados");
            return;
        }
        System.out.println(String.format("%-4s %-15s %-25s %-9s %s", "ID", "VALOR BD", "NOMBRE", "COLOR", "ESTADO"));
        for (Capuchon c : lista) {
            System.out.println(c);
        }
        System.out.println("Total: " + lista.size());
    }

    private void buscar() throws SQLException {
        Capuchon c = dao.buscarPorValor(leerTexto("Valor en BD a buscar: ").toUpperCase());
        System.out.println(c == null ? "No se encontro el capuchon" : c.toString());
    }

    private void actualizar() throws SQLException {
        Capuchon c = dao.buscarPorValor(leerTexto("Valor en BD del capuchon a actualizar: ").toUpperCase());
        if (c == null) {
            System.out.println("No se encontro el capuchon");
            return;
        }
        System.out.println("Datos actuales: " + c);
        String nombre = leerTextoOpcional("Nuevo nombre visible (Enter para dejar igual): ");
        if (!nombre.isEmpty()) {
            c.setNombreVisible(nombre);
        }
        c.setActivo(leerTexto("Activo? (s/n): ").equalsIgnoreCase("s"));
        if (dao.actualizar(c)) {
            System.out.println("Capuchon actualizado");
        }
    }

    private void eliminar() throws SQLException {
        Capuchon c = dao.buscarPorValor(leerTexto("Valor en BD del capuchon a eliminar: ").toUpperCase());
        if (c == null) {
            System.out.println("No se encontro el capuchon");
            return;
        }
        if (leerTexto("Seguro que desea eliminar " + c.getNombreVisible() + "? (s/n): ").equalsIgnoreCase("s")) {
            dao.eliminar(c.getId());
            System.out.println("Capuchon eliminado");
        }
    }

    // ---- lectura de datos ----

    private String leerTexto(String mensaje) {
        String texto = "";
        while (texto.isEmpty()) {
            System.out.print(mensaje);
            if (!teclado.hasNextLine()) {
                return "";
            }
            texto = teclado.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El dato es obligatorio");
            }
        }
        return texto;
    }

    private String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return teclado.hasNextLine() ? teclado.nextLine().trim() : "";
    }

    private String leerColor() {
        while (true) {
            String color = leerTextoOpcional("Color de fondo #RRGGBB (Enter para ninguno): ");
            if (color.isEmpty()) {
                return null;
            }
            if (color.matches("^#[0-9A-Fa-f]{6}$")) {
                return color.toUpperCase();
            }
            System.out.println("Formato no valido, ejemplo: #D9D9D9");
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!teclado.hasNextLine()) {
                return 0;
            }
            try {
                return Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite un numero");
            }
        }
    }
}
