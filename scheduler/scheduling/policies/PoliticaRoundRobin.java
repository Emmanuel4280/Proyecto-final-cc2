/* PoliticaRoundRobin.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

package scheduler.scheduling.policies;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Iterator;

import scheduler.processing.SimpleProcess;
import scheduler.processing.ProcesoBase;

/**
 * Politica Round Robin.
 * Utiliza una ConcurrentLinkedQueue como cola de procesos.
 */
public class PoliticaRoundRobin extends Policy {

    private ConcurrentLinkedQueue<SimpleProcess> cola;

    /**
     * Constructor de la politica Round Robin.
     */
    public PoliticaRoundRobin() {
        super();
        cola = new ConcurrentLinkedQueue<SimpleProcess>();
    }

    /**
     * Agrega un proceso al final de la cola.
     *
     * @param p proceso a agregar.
     */
    public synchronized void add(SimpleProcess p) {
        cola.add(p);
        size = size + 1;
        totalProcesses = totalProcesses + 1;
    }

    /**
     * Remueve el proceso al frente de la cola.
     */
    public synchronized void remove() {
        SimpleProcess p = cola.poll();
        if (p != null) {
            size = size - 1;
        }
    }

    /**
     * Devuelve el proceso siguiente a ser atendido sin removerlo.
     *
     * @return proceso al frente o null si no hay.
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
}
