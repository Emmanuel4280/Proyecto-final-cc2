/* PoliticaFCFS.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

package scheduler.scheduling.policies;

import java.util.LinkedList;
import java.util.Iterator;

import scheduler.processing.SimpleProcess;
import scheduler.processing.ProcesoBase;

/**
 * Politica First Come First Served.
 * Utiliza una LinkedList como cola (unica politica que usa LinkedList).
 */
public class PoliticaFCFS extends Policy {

    private LinkedList<SimpleProcess> cola;

    /**
     * Constructor de la politica FCFS.
     */
    public PoliticaFCFS() {
        super();
        cola = new LinkedList<SimpleProcess>();
    }

    /**
     * Agrega un proceso al final de la cola.
     *
     * @param p proceso a agregar.
     */
    public synchronized void add(SimpleProcess p) {
        cola.addLast(p);
        size = size + 1;
        totalProcesses = totalProcesses + 1;
    }

    /**
     * Remueve el proceso al frente de la cola.
     */
    public synchronized void remove() {
        if (!cola.isEmpty()) {
            cola.removeFirst();
            size = size - 1;
        }
    }

    /**
     * Devuelve el proceso siguiente a ser atendido sin removerlo.
     *
     * @return proceso al frente o null si no hay.
     */
    public synchronized SimpleProcess next() {
        if (cola.isEmpty()) {
            return null;
        }
        return cola.getFirst();
    }

    /**
     * Devuelve una representacion en cadena de la cola de procesos.
     *
     * @return cadena con todos los procesos en la cola.
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
