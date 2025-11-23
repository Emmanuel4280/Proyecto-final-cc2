/* Procesador.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

package scheduler;

import scheduler.processing.ProcesoBase;
import scheduler.scheduling.policies.Policy;

/**
 * Clase que representa al procesador que atiende los procesos
 * de la cola segun la politica seleccionada.
 */
public class Procesador implements Runnable {

    private Policy politica;
    private String nombrePolitica;
    private boolean esRoundRobin;
    private double quantumSegundos;

    private volatile boolean seguir;
    private int procesosAtendidos;
    private double tiempoTotalAtencion;
    private ProcesoBase procesoActual;

    /**
     * Constructor del procesador.
     *
     * @param politica        instancia de la politica de calendarizacion.
     * @param nombrePolitica  nombre legible de la politica.
     * @param esRoundRobin    indica si la politica es Round Robin.
     * @param quantumSegundos quantum en segundos (solo RR).
     */
    public Procesador(Policy politica, String nombrePolitica, boolean esRoundRobin, double quantumSegundos) {
        this.politica = politica;
        this.nombrePolitica = nombrePolitica;
        this.esRoundRobin = esRoundRobin;
        this.quantumSegundos = quantumSegundos;
        this.seguir = true;
        this.procesosAtendidos = 0;
        this.tiempoTotalAtencion = 0.0;
        this.procesoActual = null;
    }

    /**
     * Metodo run del hilo de procesador.
     * Atiende los procesos mientras seguir sea true.
     */
    public void run() {
        while (seguir) {
            ProcesoBase proceso = obtenerSiguienteProceso();
            if (proceso != null) {
                if (esRoundRobin) {
                    atenderRoundRobin(proceso);
                } else {
                    atenderNormal(proceso);
                }
            } else {
                dormirPequenoTiempo();
            }
        }
    }

    /**
     * Obtiene el siguiente proceso a atender desde la politica.
     *
     * @return ProcesoBase o null si no hay procesos.
     */
    private ProcesoBase obtenerSiguienteProceso() {
        synchronized (politica) {
            if (politica.size() > 0) {
                try {
                    return (ProcesoBase) politica.next();
                } catch (ClassCastException e) {
                    return null;
                }
            }
            return null;
        }
    }

    /**
     * Atiende un proceso segun una politica que no es Round Robin.
     *
     * @param proceso proceso a atender.
     */
    private void atenderNormal(ProcesoBase proceso) {
        this.procesoActual = proceso;
        ImpresorEstado.imprimirEstado("Inicio de atencion de proceso",
                politica, procesoActual, procesosAtendidos, nombrePolitica);

        dormirSegundos(proceso.getTiempoServicio());

        synchronized (politica) {
            politica.remove();
        }

        procesosAtendidos++;
        tiempoTotalAtencion += proceso.getTiempoServicio();
        this.procesoActual = null;

        ImpresorEstado.imprimirEstado("Fin de atencion de proceso",
                politica, procesoActual, procesosAtendidos, nombrePolitica);
    }

    /**
     * Atiende un proceso segun la politica Round Robin.
     *
     * @param proceso proceso a atender.
     */
    private void atenderRoundRobin(ProcesoBase proceso) {
        this.procesoActual = proceso;

        double restante = proceso.getTiempoRestante();
        double tiempoTrabajo = quantumSegundos;
        if (restante < quantumSegundos) {
            tiempoTrabajo = restante;
        }

        ImpresorEstado.imprimirEstado("Inicio de atencion (RR)",
                politica, procesoActual, procesosAtendidos, nombrePolitica);

        dormirSegundos(tiempoTrabajo);

        double nuevoRestante = restante - tiempoTrabajo;
        proceso.setTiempoRestante(nuevoRestante);

        synchronized (politica) {
            politica.remove();
            if (nuevoRestante <= 0.000001) {
                procesosAtendidos++;
                tiempoTotalAtencion += proceso.getTiempoServicio();
                this.procesoActual = null;
                ImpresorEstado.imprimirEstado("Proceso terminado (RR)",
                        politica, procesoActual, procesosAtendidos, nombrePolitica);
            } else {
                politica.add(proceso);
                this.procesoActual = null;
                ImpresorEstado.imprimirEstado("Quantum terminado, proceso vuelve a la cola (RR)",
                        politica, procesoActual, procesosAtendidos, nombrePolitica);
            }
        }
    }

    /**
     * Detiene el ciclo principal del procesador.
     */
    public void detener() {
        this.seguir = false;
    }

    /**
     * Devuelve el proceso que esta siendo atendido actualmente.
     *
     * @return proceso actual o null si no hay.
     */
    public ProcesoBase obtenerProcesoActual() {
        return procesoActual;
    }

    /**
     * Devuelve el numero de procesos atendidos completamente.
     *
     * @return numero de procesos atendidos.
     */
    public int obtenerProcesosAtendidos() {
        return procesosAtendidos;
    }

    /**
     * Devuelve el tiempo promedio de atencion de los procesos atendidos.
     *
     * @return promedio en segundos, o 0 si no se atendio ninguno.
     */
    public double obtenerPromedioTiempoAtencion() {
        if (procesosAtendidos == 0) {
            return 0.0;
        }
        return tiempoTotalAtencion / procesosAtendidos;
    }

    /**
     * Hace dormir el hilo una pequeña cantidad de tiempo cuando no hay procesos.
     */
    private void dormirPequenoTiempo() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // Ignorar
        }
    }

    /**
     * Hace dormir el hilo el numero de segundos indicado.
     *
     * @param segundos cantidad de segundos a dormir.
     */
    private void dormirSegundos(double segundos) {
        try {
            long milisegundos = (long) (segundos * 1000.0);
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            // Ignorar
        }
    }
}
