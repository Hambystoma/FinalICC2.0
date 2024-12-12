import java.util.Scanner;

public class Conecta4 {
    int[][] tablero = new int[6][7];  // Tablero del juego
    int jugadorActual = 1;           // Jugador 1 comienza
    boolean juegoTerminado = false; // Control del estado del juego
    Scanner scanner = new Scanner(System.in); // Para entrada del usuario

    public static void main(String[] args) {
        new Conecta4().jugar(); // Inicia el juego
    }

    public void jugar() {
        while (!juegoTerminado) {
            mostrarTableroConecta4();
            System.out.print("Jugador " + jugadorActual + ", selecciona una columna (0-6): ");
            int columna = scanner.nextInt();

            // Validación de la columna
            if (columna < 0 || columna >= 7 || tablero[0][columna] != 0) {
                System.out.println("Columna inválida o llena. Intenta de nuevo.");
                continue;
            }

            int fila = obtenerFilaDisponible(columna);
            tablero[fila][columna] = jugadorActual;

            // Verificar victoria
            if (verificarVictoria(fila, columna, jugadorActual)) {
                mostrarTableroConecta4();
                System.out.println("¡Jugador " + jugadorActual + " gana!");
                juegoTerminado = true;
            } else {
                // Cambiar al otro jugador
                jugadorActual = (jugadorActual == 1) ? 2 : 1;
            }
        }
    }

    private void mostrarTableroConecta4() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print((tablero[i][j] == 0 ? "." : (tablero[i][j] == 1 ? "X" : "O")) + " ");
            }
            System.out.println();
        }
    }

    private int obtenerFilaDisponible(int columna) {
        for (int i = 5; i >= 0; i--) {
            if (tablero[i][columna] == 0) {
                return i;
            }
        }
        return -1; // Esto nunca debería ocurrir porque ya verificamos antes
    }

    private boolean verificarVictoria(int fila, int columna, int jugador) {
        return verificarDireccion(fila, columna, jugador, 1, 0) ||  // Horizontal
               verificarDireccion(fila, columna, jugador, 0, 1) ||  // Vertical
               verificarDireccion(fila, columna, jugador, 1, 1) ||  // Diagonal principal
               verificarDireccion(fila, columna, jugador, 1, -1);   // Diagonal inversa
    }

    private boolean verificarDireccion(int fila, int columna, int jugador, int dirX, int dirY) {
        int count = 1;

        // Contar en la dirección positiva
        for (int i = 1; i < 4; i++) {
            int x = fila + i * dirX;
            int y = columna + i * dirY;
            if (x < 0 || x >= 6 || y < 0 || y >= 7 || tablero[x][y] != jugador) {
                break;
            }
            count++;
        }

        // Contar en la dirección negativa
        for (int i = 1; i < 4; i++) {
            int x = fila - i * dirX;
            int y = columna - i * dirY;
            if (x < 0 || x >= 6 || y < 0 || y >= 7 || tablero[x][y] != jugador) {
                break;
            }
            count++;
        }

        return count >= 4; // Hay al menos 4 en línea
    }
}