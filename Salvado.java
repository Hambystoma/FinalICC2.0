import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Salvado {
    static int puntuacionJugador = 0;
    static List<Jugador> jugadores = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("\u00a1Bienvenido al juego del Salvado!");
        System.out.print("Ingresa tu nombre: ");
        String nombreJugador = scanner.nextLine();

        // Elegir un número aleatorio entre 1 y 100
        int i = random.nextInt(100) + 1;
        System.out.println("El valor aleatorio i es: " + i);

        // Pedir al usuario que adivine la última silla
        System.out.print("Adivina cuál será la última silla ocupada (1-100): ");
        int adivinanza = scanner.nextInt();

        // Calcular la última silla ocupada
        int sillaSalvada = calcularSillaSalvada(100, i);
        System.out.println("La última silla ocupada es: " + sillaSalvada);

        // Verificar la adivinanza del jugador
        if (adivinanza == sillaSalvada) {
            puntuacionJugador += 12;
            System.out.println("\u00a1Correcto! Has ganado 12 puntos.");
        } else {
            puntuacionJugador += 2;
            System.out.println("Incorrecto. Has ganado 2 puntos.");
        }

        // Agregar jugador a la lista de jugadores
        jugadores.add(new Jugador(nombreJugador, puntuacionJugador));

        // Mostrar posiciones de jugadores
        mostrarPosiciones();

        scanner.close();
    }

    // Método para calcular la última silla ocupada
    public static int calcularSillaSalvada(int n, int i) {
        int pos = 0;
        for (int j = 2; j <= n; j++) {
            pos = (pos + i) % j;
        }
        return pos + 1;
    }

    // Método para mostrar las posiciones de los jugadores
    public static void mostrarPosiciones() {
        System.out.println("\nPosiciones de los jugadores:");
        Collections.sort(jugadores);
        for (int k = 0; k < jugadores.size(); k++) {
            Jugador jugador = jugadores.get(k);
            System.out.println((k + 1) + ". " + jugador.nombre + " - " + jugador.puntuacion + " puntos");
        }

        // Mostrar los tres primeros concursantes en alcanzar la victoria
        System.out.println("\nTop 3 jugadores:");
        for (int k = 0; k < Math.min(3, jugadores.size()); k++) {
            Jugador jugador = jugadores.get(k);
            System.out.println((k + 1) + ". " + jugador.nombre + " - " + jugador.puntuacion + " puntos");
        }
    }

    // Clase Jugador para almacenar el nombre y la puntuación de cada jugador
    static class Jugador implements Comparable<Jugador> {
        String nombre;
        int puntuacion;

        Jugador(String nombre, int puntuacion) {
            this.nombre = nombre;
            this.puntuacion = puntuacion;
        }

        @Override
        public int compareTo(Jugador otro) {
            return Integer.compare(otro.puntuacion, this.puntuacion);
        }
    }
}
