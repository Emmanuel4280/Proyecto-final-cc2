/* GeneradorProcesos.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

package scheduler;

import java.util.Random;

import scheduler.processing.ProcesoBase;
import scheduler.processing.ProcesoAritmetico;
import scheduler.processing.ProcesoIO;
import scheduler.processing.ProcesoCondicional;
import scheduler.processing.ProcesoIterativo;
import scheduler.scheduling.policies.Policy;

/**
 * Clase que genera procesos de forma aleatoria y los ingresa
 * a la cola de la politica seleccionada.
 */
public class GeneradorProcesos implements Runnable {

    private Policy politica;
    private Procesador procesador;
    private String nombrePolitica;

    private double tiempoMinIngreso;
    private double tiempoMaxIngreso;

    private double tiempoAritmetico;
    private double tiempoIO;
    private double tiempoCondicional;
    private double tiempoIterativo;

    private boolean usarRoundRobin;
    private boolean usarPrioridades;

    private volatile boolean seguir;
    private int siguienteId;
    private Random aleatorio;

    /**
     * Constructor del generador de procesos.
     *
     * @param politica         politica de calendarizacion a utilizar.
     * @param procesador       referencia al procesador para imprimir estado.
     * @param nombrePolitica   nombre legible de la politica.
     * @param tiempoMinIngreso tiempo minimo de ingreso en segundos.
     * @param tiempoMaxIngreso tiempo maximo de ingreso en segundos.
     * @param tiempoAritmetico tiempo de procesos aritmeticos.
     * @param tiempoIO         tiempo de procesos de IO.
     * @param tiempoCondicional tiempo de procesos condicionales.
     * @param tiempoIterativo  tiempo de procesos iterativos.
     * @param usarRoundRobin   indica si la politica es Round Robin.
     * @param usarPrioridades  indica si la politica es de prioridad.
     */
    public GeneradorProcesos(
            Policy politica,
            Procesador procesador,
            String nombrePolitica,
            double tiempoMinIngreso,
            double tiempoMaxIngreso,
            double tiempoAritmetico,
            double tiempoIO,
            double tiempoCondicional,
            double tiempoIterativo,
            boolean usarRoundRobin,
            boolean usarPrioridades) {

        this.politica = politica;
        this.procesador = procesador;
        this.nombrePolitica = nombrePolitica;
        this.tiempoMinIngreso = tiempoMinIngreso;
        this.tiempoMaxIngreso = tiempoMaxIngreso;
        this.tiempoAritmetico = tiempoAritmetico;
        this.tiempoIO = tiempoIO;
        this.tiempoCondicional = tiempoCondicional;
        this.tiempoIterativo = tiempoIterativo;
        this.usarRoundRobin = usarRoundRobin;
        this.usarPrioridades = usarPrioridades;

        this.seguir = true;
        this.siguienteId = 1;
        this.aleatorio = new Random();
    }

    /**
     * Metodo run del hilo generador.
     * Genera procesos hasta que seguir es false.
     */
    public void run() {
        while (seguir) {
            dormirIntervaloAleatorio();

            if (!seguir) {
                break;
            }

            ProcesoBase nuevo = crearProcesoAleatorio();
            if (usarPrioridades) {
                asignarPrioridadAleatoria(nuevo);
            }

            synchronized (politica) {
                politica.add(nuevo);
            }

            ImpresorEstado.imprimirEstado(
                    "Nuevo proceso ingresado a la cola",
                    politica,
                    procesador.obtenerProcesoActual(),
                    procesador.obtenerProcesosAtendidos(),
                    nombrePolitica);
        }
    }

    /**
     * Detiene la generacion de nuevos procesos.
     */
    public void detener() {
        this.seguir = false;
    }

    /**
     * Hace dormir al hilo un intervalo aleatorio entre los limites dados.
     */
    private void dormirIntervaloAleatorio() {
        try {
            double diferencia = tiempoMaxIngreso - tiempoMinIngreso;
            double numeroAleatorio = aleatorio.nextDouble();
            double intervalo = tiempoMinIngreso + numeroAleatorio * diferencia;
            long milisegundos = (long) (intervalo * 1000.0);
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            // Ignorar
        }
    }

    /**
     * Crea un proceso de tipo aleatorio con el siguiente id correlativo.
     *
     * @return proceso creado.
     */
    private ProcesoBase crearProcesoAleatorio() {
        int numero = aleatorio.nextInt(4);
        ProcesoBase proceso;

        int id = siguienteId;
        siguienteId = siguienteId + 1;

        if (numero == 0) {
            proceso = new ProcesoAritmetico(id, tiempoAritmetico);
        } else if (numero == 1) {
            proceso = new ProcesoIO(id, tiempoIO);
        } else if (numero == 2) {
            proceso = new ProcesoCondicional(id, tiempoCondicional);
        } else {
            proceso = new ProcesoIterativo(id, tiempoIterativo);
        }

        if (usarRoundRobin) {
            proceso.setTiempoRestante(proceso.getTiempoServicio());
        }

        return proceso;
    }

    /**
     * Asigna una prioridad aleatoria de 1 a 3 al proceso.
     *
     * @param proceso proceso al que se le asignara prioridad.
     */
    private void asignarPrioridadAleatoria(ProcesoBase proceso) {
        int prioridad = aleatorio.nextInt(3) + 1;
        proceso.setPrioridad(prioridad);
    }
}
