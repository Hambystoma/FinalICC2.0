import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;

public class TorresDeHanoi {

    private static Scanner scanner = new Scanner(System.in);
    private static HashMap<String, Jugador> jugadores = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Bienvenido al juego de Torres de Hanoi.");
        System.out.println("El objetivo es mover los discos de la torre A (izquierda) a la torre C (derecha).");
        System.out.println("Reglas:");
        System.out.println("- Puedes mover solo un disco a la vez.");
        System.out.println("- Un disco más grande no puede colocarse sobre uno más pequeño.");
        System.out.println("- El número mínimo de movimientos es 63 si haces las jugadas correctas.");

        System.out.print("Ingresa tu nombre: ");
        String nombreJugador = scanner.nextLine();

        if (!jugadores.containsKey(nombreJugador)) {
            System.out.println("Jugador no registrado. Primero debes registrarte.");
            return;
        }

        Jugador jugador = jugadores.get(nombreJugador);
        int movimientosRealizados = 0;

        Stack<Integer> torreA = new Stack<>();
        Stack<Integer> torreB = new Stack<>();
        Stack<Integer> torreC = new Stack<>();

        // Inicializar la torre A con discos
        for (int i = 6; i > 0; i--) {
            torreA.push(i);
        }

        // Ciclo principal del juego
        while (torreC.size() < 6) {
            mostrarEstado(torreA, torreB, torreC);

            if (moverDisco(torreA, torreB, torreC)) {
                movimientosRealizados++;
            }

            if (torreC.size() == 6) {
                System.out.println("¡Felicidades, has completado el juego de Torres de Hanoi!");
                break;
            }
        }

        int puntos = calcularPuntos(movimientosRealizados);
        jugador.agregarPuntos(puntos);
        System.out.println("¡Juego completado! Tu puntuación es: " + puntos);
    }

    private static boolean moverDisco(Stack<Integer> torreA, Stack<Integer> torreB, Stack<Integer> torreC) {
        System.out.print("Selecciona el poste de origen (A, B, C): ");
        String origen = scanner.nextLine().toUpperCase();
        System.out.print("Selecciona el poste de destino (A, B, C): ");
        String destino = scanner.nextLine().toUpperCase();

        Stack<Integer> torreOrigen = obtenerTorre(origen, torreA, torreB, torreC);
        Stack<Integer> torreDestino = obtenerTorre(destino, torreA, torreB, torreC);

        if (torreOrigen == null || torreDestino == null) {
            System.out.println("Entrada inválida. Intenta nuevamente.");
            return false;
        }

        if (torreOrigen.isEmpty()) {
            System.out.println("La torre de origen está vacía. Elige otra torre.");
            return false;
        }

        if (!torreDestino.isEmpty() && torreDestino.peek() < torreOrigen.peek()) {
            System.out.println("No puedes colocar un disco más grande sobre uno más pequeño.");
            return false;
        }

        torreDestino.push(torreOrigen.pop());
        System.out.println("Movimiento exitoso: Disco movido de " + origen + " a " + destino);
        return true;
    }

    private static Stack<Integer> obtenerTorre(String nombreTorre, Stack<Integer> torreA, Stack<Integer> torreB, Stack<Integer> torreC) {
        switch (nombreTorre) {
            case "A":
                return torreA;
            case "B":
                return torreB;
            case "C":
                return torreC;
            default:
                return null;
        }
    }

    private static void mostrarEstado(Stack<Integer> torreA, Stack<Integer> torreB, Stack<Integer> torreC) {
        System.out.println("Estado actual:");
        System.out.println("Torre A: " + torreA);
        System.out.println("Torre B: " + torreB);
        System.out.println("Torre C: " + torreC);
        System.out.println();
    }

    private static int calcularPuntos(int movimientosRealizados) {
        int movimientosOptimos = (int) Math.pow(2, 6) - 1; // 63 para 6 discos
        return Math.max(0, (movimientosOptimos * 10) - (movimientosRealizados - movimientosOptimos) * 5);
    }
}
