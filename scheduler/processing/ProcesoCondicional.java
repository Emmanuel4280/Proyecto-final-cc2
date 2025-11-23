/* ProcesoCondicional.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

package scheduler.processing;

/**
 * Proceso de tipo condicional.
 */
public class ProcesoCondicional extends ProcesoBase {

    /**
     * Construye un ProcesoCondicional con id y tiempo de servicio.
     *
     * @param id             identificador del proceso.
     * @param tiempoServicio tiempo de servicio en segundos.
     */
    public ProcesoCondicional(int id, double tiempoServicio) {
        super(id, tiempoServicio);
    }

    /**
     * Devuelve la abreviatura del tipo de proceso.
     *
     * @return "C" para condicional.
     */
    public String getTipoCorto() {
        return "C";
    }
}
