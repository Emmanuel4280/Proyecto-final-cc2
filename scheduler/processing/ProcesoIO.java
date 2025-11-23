/* ProcesoIO.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

package scheduler.processing;

/**
 * Proceso de tipo Input/Output.
 */
public class ProcesoIO extends ProcesoBase {

    /**
     * Construye un ProcesoIO con id y tiempo de servicio.
     *
     * @param id             identificador del proceso.
     * @param tiempoServicio tiempo de servicio en segundos.
     */
    public ProcesoIO(int id, double tiempoServicio) {
        super(id, tiempoServicio);
    }

    /**
     * Devuelve la abreviatura del tipo de proceso.
     *
     * @return "IO" para input/output.
     */
    public String getTipoCorto() {
        return "IO";
    }
}
