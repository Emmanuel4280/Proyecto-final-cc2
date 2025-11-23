/* PoliticaLCFS.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

package scheduler.scheduling.policies;

import java.util.Stack;
import java.util.Iterator;

import scheduler.processing.SimpleProcess;
import scheduler.processing.ProcesoBase;

/**
 * Politica Last Come First Served.
 * Utiliza una pila (Stack) para atender al ultimo proceso que llego.
 */
public class PoliticaLCFS extends Policy {

    private Stack<SimpleProcess> pila;

    /**
     * Constructor de la politica LCFS.
     */
    public PoliticaLCFS() {
        super();
        pila = new Stack<SimpleProcess>();
    }

    /**
     * Agrega un proceso en la pila.
     *
     * @param p proceso a agregar.
     */
    public synchronized void add(SimpleProcess p) {
        pila.push(p);
        size = size + 1;
        totalProcesses = totalProcesses + 1;
    }

    /**
     * Remueve el proceso en la cima de la pila.
     */
    public synchronized void remove() {
        if (!pila.isEmpty()) {
            pila.pop();
            size = size - 1;
        }
    }

    /**
     * Devuelve el proceso siguiente a ser atendido sin removerlo.
     *
     * @return proceso en la cima o null si no hay.
     */
    public synchronized SimpleProcess next() {
        if (pila.isEmpty()) {
            return null;
        }
        return pila.peek();
    }

    /**
     * Devuelve una representacion en cadena de la pila de procesos.
     *
     * @return cadena con todos los procesos.
     */
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<SimpleProcess> it = pila.iterator();
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
