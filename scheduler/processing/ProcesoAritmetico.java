/* ProcesoAritmetico.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

package scheduler.processing;

/**
 * Proceso de tipo aritmetico.
 */
public class ProcesoAritmetico extends ProcesoBase {

    /**
     * Construye un ProcesoAritmetico con id y tiempo de servicio.
     *
     * @param id             identificador del proceso.
     * @param tiempoServicio tiempo de servicio en segundos.
     */
    public ProcesoAritmetico(int id, double tiempoServicio) {
        super(id, tiempoServicio);
    }

    /**
     * Devuelve la abreviatura del tipo de proceso.
     *
     * @return "A" para aritmetico.
     */
    public String getTipoCorto() {
        return "A";
    }
}
