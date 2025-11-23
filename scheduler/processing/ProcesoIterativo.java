/* ProcesoIterativo.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

package scheduler.processing;

/**
 * Proceso de tipo iterativo (loop).
 */
public class ProcesoIterativo extends ProcesoBase {

    /**
     * Construye un ProcesoIterativo con id y tiempo de servicio.
     *
     * @param id             identificador del proceso.
     * @param tiempoServicio tiempo de servicio en segundos.
     */
    public ProcesoIterativo(int id, double tiempoServicio) {
        super(id, tiempoServicio);
    }

    /**
     * Devuelve la abreviatura del tipo de proceso.
     *
     * @return "L" para iterativo (loop).
     */
    public String getTipoCorto() {
        return "L";
    }
}
