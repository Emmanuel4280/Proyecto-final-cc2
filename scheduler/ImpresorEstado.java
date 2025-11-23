/* ImpresorEstado.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

package scheduler;

import scheduler.processing.ProcesoBase;
import scheduler.scheduling.policies.Policy;

/**
 * Clase auxiliar para imprimir el estado actual de la simulacion
 * de forma sincronizada.
 */
public class ImpresorEstado {

    /**
     * Imprime en pantalla la informacion principal de la simulacion:
     * evento, politica, proceso actual, numero de procesos atendidos
     * y el contenido de la cola.
     *
     * @param evento            descripcion del evento que ocurrio.
     * @param politica          politica de calendarizacion utilizada.
     * @param procesoActual     proceso que se esta atendiendo actualmente.
     * @param procesosAtendidos numero de procesos atendidos hasta el momento.
     * @param nombrePolitica    nombre legible de la politica.
     */
    public static synchronized void imprimirEstado(
            String evento,
            Policy politica,
            ProcesoBase procesoActual,
            int procesosAtendidos,
            String nombrePolitica) {

        System.out.println("--------------------------------------------------");
        System.out.println("Evento: " + evento);
        System.out.println("Politica: " + nombrePolitica);
        System.out.println("Procesos atendidos: " + procesosAtendidos);
        if (procesoActual != null) {
            System.out.println("Proceso actual: " + procesoActual.resumenCorto());
        } else {
            System.out.println("Proceso actual: Ninguno");
        }
        System.out.println("Cola de procesos:");
        System.out.print(politica.toString());
        System.out.println("--------------------------------------------------");
    }
}
