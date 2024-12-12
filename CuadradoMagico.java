import java.util.*;

public class CuadradoMagico {
    static final int N = 4;
    static int puntuacionJugador = 0;
    static List<Jugador> jugadores = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¡Bienvenido al juego del Cuadrado Mágico!");
        System.out.print("Ingresa tu nombre: ");
        String nombreJugador = scanner.nextLine();

        int[][] tablero = inicializarTablero();
        mostrarTablero(tablero);

        System.out.println("Completa el tablero para que sea un cuadrado mágico.");
        int numerosUsados = 0;
        while (numerosUsados < 16) {
            System.out.print("Ingresa la fila (0-3): ");
            int fila = scanner.nextInt();

            System.out.print("Ingresa la columna (0-3): ");
            int columna = scanner.nextInt();

            if (tablero[fila][columna] != 0) {
                System.out.println("Esta celda ya tiene un valor fijo o ya has colocado un número aquí.");
                continue;
            }

            System.out.print("Ingresa el número que deseas colocar (1-16): ");
            int numero = scanner.nextInt();

            if (numero < 1 || numero > 16 || contiene(tablero, numero)) {
                System.out.println("Número inválido o ya utilizado. Intenta de nuevo.");
                continue;
            }

            tablero[fila][columna] = numero;
            numerosUsados++;
            mostrarTablero(tablero);

            if (!esPosibleCuadradoMagico(tablero)) {
                System.out.println("No es posible completar el cuadrado mágico. ¡Has perdido!");
                return;
            }
        }

        if (esCuadradoMagico(tablero)) {
            System.out.println("¡Felicitaciones! Has completado el cuadrado mágico.");
            puntuacionJugador += 10;
        } else {
            System.out.println("El tablero no es un cuadrado mágico. Mejor suerte la próxima vez.");
        }

        jugadores.add(new Jugador(nombreJugador, puntuacionJugador));
        mostrarPosiciones();

        scanner.close();
    }

    private static int[][] inicializarTablero() {
        int[][] tablero = new int[N][N];
        tablero[0][0] = 1;
        tablero[1][1] = 6;
        tablero[2][2] = 11;
        tablero[3][3] = 16;
        return tablero;
    }

    private static void mostrarTablero(int[][] tablero) {
        for (int[] fila : tablero) {
            for (int celda : fila) {
                System.out.print((celda == 0 ? "_" : celda) + "\t");
            }
            System.out.println();
        }
    }

    private static boolean contiene(int[][] tablero, int numero) {
        for (int[] fila : tablero) {
            for (int celda : fila) {
                if (celda == numero) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean esPosibleCuadradoMagico(int[][] tablero) {
        int sumaMagica = N * (N * N + 1) / 2;
        int[] filas = new int[N];
        int[] columnas = new int[N];
        int diagonal1 = 0, diagonal2 = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tablero[i][j] != 0) {
                    filas[i] += tablero[i][j];
                    columnas[j] += tablero[i][j];
                    if (i == j) diagonal1 += tablero[i][j];
                    if (i + j == N - 1) diagonal2 += tablero[i][j];
                }
            }
        }

        for (int k = 0; k < N; k++) {
            if (filas[k] > sumaMagica || columnas[k] > sumaMagica) {
                return false;
            }
        }
        return diagonal1 <= sumaMagica && diagonal2 <= sumaMagica;
    }

    private static boolean esCuadradoMagico(int[][] tablero) {
        int sumaMagica = N * (N * N + 1) / 2;
        for (int i = 0; i < N; i++) {
            if (Arrays.stream(tablero[i]).sum() != sumaMagica) {
                return false;
            }
        }

        for (int j = 0; j < N; j++) {
            int sumaColumna = 0;
            for (int i = 0; i < N; i++) {
                sumaColumna += tablero[i][j];
            }
            if (sumaColumna != sumaMagica) {
                return false;
            }
        }

        int diagonal1 = 0, diagonal2 = 0;
        for (int i = 0; i < N; i++) {
            diagonal1 += tablero[i][i];
            diagonal2 += tablero[i][N - 1 - i];
        }
        return diagonal1 == sumaMagica && diagonal2 == sumaMagica;
    }

    private static void mostrarPosiciones() {
        System.out.println("\nPosiciones de los jugadores:");
        jugadores.sort(Comparator.comparingInt(j -> -j.puntuacion));
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.println((i + 1) + ". " + jugadores.get(i).nombre + " - " + jugadores.get(i).puntuacion + " puntos");
        }

        System.out.println("\nTop 3 jugadores:");
        for (int i = 0; i < Math.min(3, jugadores.size()); i++) {
            System.out.println((i + 1) + ". " + jugadores.get(i).nombre + " - " + jugadores.get(i).puntuacion + " puntos");
        }
    }

    static class Jugador {
        String nombre;
        int puntuacion;

        Jugador(String nombre, int puntuacion) {
            this.nombre = nombre;
            this.puntuacion = puntuacion;
        }
    }
}
