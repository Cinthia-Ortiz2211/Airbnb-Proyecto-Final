package model.alojamiento;

import contract.Identificable;
import model.usuario.Anfitrion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un alojamiento en el sistema.
 * Contiene información sobre la dirección, tipo, nivel, descripción,
 * precio por noche, fechas disponibles y el anfitrión asociado.
 */
public class Alojamiento implements Identificable {

    private static int contador = 0;

    private int id;
    private String direccion;
    private TipoAlojamiento tipo;
    private String nivel;
    private String descripcion;
    private double precioPorNoche;
    private List<LocalDate> fechasDisponibles;
    private Anfitrion anfitrion;

    /**
     * Constructor por defecto que inicializa un alojamiento con un ID único
     * y una lista vacía de fechas disponibles.
     */
    public Alojamiento() {
        this.id = ++contador;
        this.fechasDisponibles = new ArrayList<>();
    }

    /**
     * Constructor completo para inicializar un alojamiento con todos sus atributos.
     *
     * @param id                Identificador único del alojamiento.
     * @param direccion         Dirección del alojamiento.
     * @param tipo              Tipo de alojamiento (CASA, DEPARTAMENTO, HABITACION).
     * @param nivel             Nivel o categoría del alojamiento.
     * @param descripcion       Descripción detallada del alojamiento.
     * @param precioPorNoche    Precio por noche del alojamiento.
     * @param fechasDisponibles Lista de fechas disponibles para reserva.
     * @param anfitrion         Anfitrión asociado al alojamiento.
     */
    public Alojamiento(int id, String direccion, TipoAlojamiento tipo, String nivel, String descripcion, double precioPorNoche, List<LocalDate> fechasDisponibles, Anfitrion anfitrion) {
        this.id = id;
        if (id > contador) contador = id;
        this.direccion = direccion;
        this.tipo = (tipo != null) ? tipo : TipoAlojamiento.CASA;
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.precioPorNoche = precioPorNoche;
        this.fechasDisponibles = (fechasDisponibles != null) ? fechasDisponibles : new ArrayList<>();
        this.anfitrion = anfitrion;
    }

    /**
     * Constructor sin ID que inicializa un alojamiento con un ID único generado automáticamente.
     *
     * @param direccion         Dirección del alojamiento.
     * @param tipo              Tipo de alojamiento (CASA, DEPARTAMENTO, HABITACION).
     * @param nivel             Nivel o categoría del alojamiento.
     * @param descripcion       Descripción detallada del alojamiento.
     * @param precioPorNoche    Precio por noche del alojamiento.
     * @param fechasDisponibles Lista de fechas disponibles para reserva.
     * @param anfitrion         Anfitrión asociado al alojamiento.
     */
    public Alojamiento(String direccion, TipoAlojamiento tipo, String nivel, String descripcion, double precioPorNoche, List<LocalDate> fechasDisponibles, Anfitrion anfitrion) {
        this.id = ++contador;
        this.direccion = direccion;
        this.tipo = tipo;
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.precioPorNoche = precioPorNoche;
        this.fechasDisponibles = (fechasDisponibles != null) ? fechasDisponibles : new ArrayList<>();
        this.anfitrion = anfitrion;
    }

    /**
     * Obtiene el identificador único del alojamiento.
     *
     * @return El identificador del alojamiento.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del alojamiento.
     *
     * @param id El nuevo identificador del alojamiento.
     */
    public void setId(int id) {
        this.id = id;
        if (id > contador) contador = id;
    }

    /**
     * Obtiene la dirección del alojamiento.
     *
     * @return La dirección del alojamiento.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del alojamiento.
     *
     * @param direccion La nueva dirección del alojamiento.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el tipo de alojamiento.
     *
     * @return El tipo de alojamiento.
     */
    public TipoAlojamiento getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de alojamiento.
     *
     * @param tipo El nuevo tipo de alojamiento.
     */
    public void setTipo(TipoAlojamiento tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el nivel del alojamiento.
     *
     * @return El nivel del alojamiento.
     */
    public String getNivel() {
        return nivel;
    }

    /**
     * Establece el nivel del alojamiento.
     *
     * @param nivel El nuevo nivel del alojamiento.
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     * Obtiene la descripción del alojamiento.
     *
     * @return La descripción del alojamiento.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del alojamiento.
     *
     * @param descripcion La nueva descripción del alojamiento.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el precio por noche del alojamiento.
     *
     * @return El precio por noche del alojamiento.
     */
    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    /**
     * Establece el precio por noche del alojamiento.
     *
     * @param precioPorNoche El nuevo precio por noche del alojamiento.
     */
    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    /**
     * Obtiene la lista de fechas disponibles para reserva.
     *
     * @return La lista de fechas disponibles.
     */
    public List<LocalDate> getFechasDisponibles() {
        return fechasDisponibles;
    }

    /**
     * Establece la lista de fechas disponibles para reserva.
     *
     * @param fechasDisponibles La nueva lista de fechas disponibles.
     */
    public void setFechasDisponibles(List<LocalDate> fechasDisponibles) {
        this.fechasDisponibles = fechasDisponibles;
    }

    /**
     * Obtiene el anfitrión asociado al alojamiento.
     *
     * @return El anfitrión del alojamiento.
     */
    public Anfitrion getAnfitrion() {
        return anfitrion;
    }

    /**
     * Establece el anfitrión asociado al alojamiento.
     *
     * @param anfitrion El nuevo anfitrión del alojamiento.
     */
    public void setAnfitrion(Anfitrion anfitrion) {
        this.anfitrion = anfitrion;
    }

    /**
     * Verifica si el alojamiento está disponible en un rango de fechas dado.
     *
     * @param inicio Fecha de inicio del rango.
     * @param fin    Fecha de fin del rango.
     * @return true si el alojamiento está disponible en alguna fecha dentro del rango, false en caso contrario.
     */
    public boolean esDisponible(LocalDate inicio, LocalDate fin) {
        if (fechasDisponibles == null || fechasDisponibles.isEmpty()) return false;
        for (int i = 0; i < fechasDisponibles.size(); i++) {
            LocalDate fecha = fechasDisponibles.get(i);
            if (!fecha.isBefore(inicio) && !fecha.isAfter(fin)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza el contador estático de IDs si el último ID proporcionado es mayor.
     *
     * @param ultimoId El último ID utilizado.
     */
    public static void actualizarContador(int ultimoId) {
        if (ultimoId > contador) contador = ultimoId;
    }

    /**
     * Obtiene el siguiente ID disponible para un nuevo alojamiento.
     *
     * @return El siguiente ID como un entero.
     */
    public static int getSiguienteId() {
        return ++contador;
    }

    /**
     * Representación en cadena del alojamiento.
     *
     * @return Una cadena que describe el alojamiento.
     */
    @Override
    public String toString() {
        return "Alojamiento{" + "id=" + id + ", direccion='" + direccion + '\'' + ", tipo=" + tipo + ", nivel='" + nivel + '\'' + ", descripcion='" + descripcion + '\'' + ", precioPorNoche=" + precioPorNoche + ", anfitrion=" + (anfitrion != null ? anfitrion.getEmail() : "sin anfitrión") + '}';
    }
}