/* PoliticaPrioridad.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

package scheduler.scheduling.policies;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Iterator;

import scheduler.processing.SimpleProcess;
import scheduler.processing.ProcesoBase;

/**
 * Politica de prioridad.
 * Atiende primero a los procesos con prioridad 1, luego 2, luego 3.
 * Dentro de la misma prioridad se atiende al que llego primero (id menor).
 */
public class PoliticaPrioridad extends Policy {

    private PriorityQueue<SimpleProcess> cola;

    /**
     * Constructor de la politica de prioridad.
     */
    public PoliticaPrioridad() {
        super();
        cola = new PriorityQueue<SimpleProcess>(11, new ComparadorPrioridad());
    }

    /**
     * Agrega un proceso a la cola de prioridad.
     *
     * @param p proceso a agregar.
     */
    public synchronized void add(SimpleProcess p) {
        cola.add(p);
        size = size + 1;
        totalProcesses = totalProcesses + 1;
    }

    /**
     * Remueve el siguiente proceso a ser atendido.
     */
    public synchronized void remove() {
        SimpleProcess p = cola.poll();
        if (p != null) {
            size = size - 1;
        }
    }

    /**
     * Devuelve el siguiente proceso a ser atendido sin removerlo.
     *
     * @return proceso con mayor prioridad o null si no hay.
     */
    public synchronized SimpleProcess next() {
        return cola.peek();
    }

    /**
     * Devuelve una representacion en cadena de la cola de procesos.
     *
     * @return cadena con todos los procesos.
     */
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<SimpleProcess> it = cola.iterator();
        while (it.hasNext()) {
            SimpleProcess proceso = it.next();
            if (proceso instanceof ProcesoBase) {
                ProcesoBase base = (ProcesoBase) proceso;
                sb.append(base.resumenCorto());
            } else {
                sb.append(proceso.toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Comparador interno para ordenar procesos por prioridad y luego por id.
     */
    private static class ComparadorPrioridad implements Comparator<SimpleProcess> {
        /**
         * Compara dos procesos segun prioridad e id.
         *
         * @param p1 primer proceso.
         * @param p2 segundo proceso.
         * @return numero negativo si p1 < p2, positivo si p1 > p2, 0 si iguales.
         */
        public int compare(SimpleProcess p1, SimpleProcess p2) {
            if (p1 instanceof ProcesoBase && p2 instanceof ProcesoBase) {
                ProcesoBase b1 = (ProcesoBase) p1;
                ProcesoBase b2 = (ProcesoBase) p2;

                int diferenciaPrioridad = b1.getPrioridad() - b2.getPrioridad();
                if (diferenciaPrioridad != 0) {
                    return diferenciaPrioridad;
                }
                return b1.getId() - b2.getId();
            }
            return 0;
        }
    }
}
