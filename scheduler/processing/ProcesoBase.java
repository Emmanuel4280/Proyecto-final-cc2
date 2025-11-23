/* ProcesoBase.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

package scheduler.processing;

/**
 * Clase abstracta que representa un proceso generico del sistema.
 * Hereda de SimpleProcess y agrega tiempo de servicio, tiempo restante
 * (para Round Robin) y prioridad (para Priority Policy).
 */
public abstract class ProcesoBase extends SimpleProcess {

    protected double tiempoServicio;
    protected double tiempoRestante;
    protected int prioridad;

    /**
     * Construye un ProcesoBase con id y tiempo de servicio.
     *
     * @param id             identificador unico del proceso.
     * @param tiempoServicio tiempo total de servicio en segundos.
     */
    public ProcesoBase(int id, double tiempoServicio) {
        super(id);
        this.tiempoServicio = tiempoServicio;
        this.tiempoRestante = tiempoServicio;
        this.prioridad = 0;
    }

    /**
     * Devuelve el tiempo total de servicio del proceso.
     *
     * @return tiempo de servicio en segundos.
     */
    public double getTiempoServicio() {
        return tiempoServicio;
    }

    /**
     * Devuelve el tiempo restante de servicio del proceso.
     *
     * @return tiempo restante en segundos.
     */
    public double getTiempoRestante() {
        return tiempoRestante;
    }

    /**
     * Actualiza el tiempo restante de servicio del proceso.
     *
     * @param tiempoRestante nuevo tiempo restante en segundos.
     */
    public void setTiempoRestante(double tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

    /**
     * Devuelve la prioridad del proceso (1 a 3).
     *
     * @return prioridad del proceso.
     */
    public int getPrioridad() {
        return prioridad;
    }

    /**
     * Asigna una prioridad al proceso.
     *
     * @param prioridad prioridad entre 1 y 3.
     */
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * Devuelve una abreviatura del tipo de proceso.
     *
     * @return "A", "IO", "C" o "L".
     */
    public abstract String getTipoCorto();

    /**
     * Devuelve una representacion corta del proceso para imprimir
     * en la cola.
     *
     * @return cadena con id, tipo, tiempo y prioridad.
     */
    public String resumenCorto() {
        return "[id:" + getId() +
                ", tipo:" + getTipoCorto() +
                ", servicio:" + tiempoServicio +
                "s, restante:" + tiempoRestante +
                "s, prioridad:" + prioridad + "]";
    }

    /**
     * Devuelve la representacion en cadena del proceso.
     *
     * @return resumen corto.
     */
    public String toString() {
        return resumenCorto();
    }
}
