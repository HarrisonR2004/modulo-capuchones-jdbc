package co.agroline.capuchones.modelo;

/**
 * Representa un capuchon del catalogo (tabla capuchon).
 * Autor: Harrison Rodriguez Galindo - Ficha 3336080
 */
public class Capuchon {

    private int id;
    private String valorBd;
    private String nombreVisible;
    private String colorFondo;
    private boolean activo;

    public Capuchon() {
    }

    public Capuchon(int id, String valorBd, String nombreVisible, String colorFondo, boolean activo) {
        this.id = id;
        this.valorBd = valorBd;
        this.nombreVisible = nombreVisible;
        this.colorFondo = colorFondo;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValorBd() {
        return valorBd;
    }

    public void setValorBd(String valorBd) {
        this.valorBd = valorBd;
    }

    public String getNombreVisible() {
        return nombreVisible;
    }

    public void setNombreVisible(String nombreVisible) {
        this.nombreVisible = nombreVisible;
    }

    public String getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(String colorFondo) {
        this.colorFondo = colorFondo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return String.format("%-4d %-15s %-25s %-9s %s", id, valorBd, nombreVisible,
                colorFondo == null ? "-" : colorFondo, activo ? "Activo" : "Inactivo");
    }
}
