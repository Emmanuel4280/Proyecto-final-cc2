/* ProcessScheduler.java */

/**
 * Hecho por: Wiliam Alejando Ruiz Zelada
 * Carnet: 21004892
 * Seccion: A
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

import scheduler.Procesador;
import scheduler.GeneradorProcesos;
import scheduler.scheduling.policies.Policy;
import scheduler.scheduling.policies.PoliticaFCFS;
import scheduler.scheduling.policies.PoliticaLCFS;
import scheduler.scheduling.policies.PoliticaRoundRobin;
import scheduler.scheduling.policies.PoliticaPrioridad;

/**
 * Clase principal del simulador de calendarizacion de procesos.
 * Lee los argumentos de la linea de comandos, crea la politica,
 * el generador de procesos y el procesador y controla la finalizacion
 * al presionar la tecla q.
 */
public class ProcessScheduler {

    /**
     * Metodo principal de la aplicacion.
     *
     * @param args argumentos de linea de comandos segun el enunciado.
     */
    public static void main(String[] args) {
        if (args.length < 6) {
            imprimirUso();
            return;
        }

        String banderaPolitica = args[0];

        boolean esRoundRobin = "-rr".equals(banderaPolitica);
        if ((esRoundRobin && args.length != 7)
                || (!esRoundRobin && !"-fcfs".equals(banderaPolitica)
                && !"-lcfs".equals(banderaPolitica)
                && !"-pp".equals(banderaPolitica))) {
            imprimirUso();
            return;
        }

        if (!esRoundRobin && args.length != 6) {
            imprimirUso();
            return;
        }

        try {
            // Rango de tiempo de ingreso: "min-max"
            String textoRango = args[1];
            int posicionGuion = textoRango.indexOf('-');
            if (posicionGuion == -1) {
                System.out.println("Formato de rango invalido. Debe ser min-max");
                return;
            }
            String textoMin = textoRango.substring(0, posicionGuion);
            String textoMax = textoRango.substring(posicionGuion + 1);

            double tiempoMinIngreso = Double.parseDouble(textoMin);
            double tiempoMaxIngreso = Double.parseDouble(textoMax);

            double tiempoAritmetico = Double.parseDouble(args[2]);
            double tiempoIO = Double.parseDouble(args[3]);
            double tiempoCondicional = Double.parseDouble(args[4]);
            double tiempoIterativo = Double.parseDouble(args[5]);

            double quantum = 0.0;
            if (esRoundRobin) {
                quantum = Double.parseDouble(args[6]);
            }

            Policy politica = null;
            String nombrePolitica = "";

            if ("-fcfs".equals(banderaPolitica)) {
                politica = new PoliticaFCFS();
                nombrePolitica = "First Come First Served (FCFS)";
            } else if ("-lcfs".equals(banderaPolitica)) {
                politica = new PoliticaLCFS();
                nombrePolitica = "Last Come First Served (LCFS)";
            } else if ("-rr".equals(banderaPolitica)) {
                politica = new PoliticaRoundRobin();
                nombrePolitica = "Round Robin (RR)";
            } else if ("-pp".equals(banderaPolitica)) {
                politica = new PoliticaPrioridad();
                nombrePolitica = "Priority Policy (PP)";
            }

            // Procesador y generador
            Procesador procesador = new Procesador(politica, nombrePolitica, esRoundRobin, quantum);
            GeneradorProcesos generador = new GeneradorProcesos(
                    politica,
                    procesador,
                    nombrePolitica,
                    tiempoMinIngreso,
                    tiempoMaxIngreso,
                    tiempoAritmetico,
                    tiempoIO,
                    tiempoCondicional,
                    tiempoIterativo,
                    esRoundRobin,
                    "-pp".equals(banderaPolitica));

            Thread hiloProcesador = new Thread(procesador);
            Thread hiloGenerador = new Thread(generador);

            hiloProcesador.start();
            hiloGenerador.start();

            System.out.println("Simulacion iniciada con politica: " + nombrePolitica);
            System.out.println("Presione 'q' y ENTER para detener la simulacion.");

            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
            boolean continuarLeyendo = true;
            while (continuarLeyendo) {
                String linea = lector.readLine();
                if (linea != null && linea.trim().equalsIgnoreCase("q")) {
                    continuarLeyendo = false;
                }
            }

            // Detener hilos
            generador.detener();
            procesador.detener();

            hiloGenerador.join();
            hiloProcesador.join();

            // Resumen final
            System.out.println();
            System.out.println("===== RESUMEN DE LA SIMULACION =====");
            System.out.println("Politica utilizada: " + nombrePolitica);
            System.out.println("Procesos atendidos completamente: " + procesador.obtenerProcesosAtendidos());
            System.out.println("Procesos que quedaron en cola: " + politica.size());
            double promedio = procesador.obtenerPromedioTiempoAtencion();
            System.out.println("Tiempo promedio de atencion por proceso: " + promedio + " segundos");
            System.out.println("====================================");

        } catch (Exception e) {
            System.out.println("Error en la ejecucion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Imprime la forma correcta de usar el programa.
     */
    private static void imprimirUso() {
        System.out.println("Uso:");
        System.out.println("  java ProcessScheduler -fcfs rango_tiempo_ingreso arith io cond loop");
        System.out.println("  java ProcessScheduler -lcfs rango_tiempo_ingreso arith io cond loop");
        System.out.println("  java ProcessScheduler -rr   rango_tiempo_ingreso arith io cond loop quantum");
        System.out.println("  java ProcessScheduler -pp   rango_tiempo_ingreso arith io cond loop");
        System.out.println("Ejemplo:");
        System.out.println("  java ProcessScheduler -fcfs 1.5-3 2 1 2.5 3");
        System.out.println("  java ProcessScheduler -rr 1-2.5 1.5 1 3 4 0.5");
    }
}
