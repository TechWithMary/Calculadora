package com.mary.calculadora;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Calculadora de consola de Mary Cardona.
 * Muestra un menú, pide los números, valida lo que escribe el usuario
 * y guarda un historial de las operaciones realizadas.
 */
public class App {

    // Secuencias de escape que cambian el color del texto en la consola
    static final String RESET = "\u001B[0m";
    static final String MORADO = "\u001B[35m";
    static final String VERDE = "\u001B[32m";
    static final String ROJO = "\u001B[31m";
    static final String AZUL = "\u001B[34m";

    // Objeto que lee lo que la usuaria escribe con el teclado
    static Scanner teclado = new Scanner(System.in);
    // Lista dinámica donde se guarda cada operación con su resultado
    static ArrayList<String> historial = new ArrayList<>();

    public static void main(String[] args) {
        // Se crea el objeto que tiene los métodos matemáticos
        Operaciones op = new Operaciones();
        int opcion;

        System.out.println(MORADO + "Bienvenida a la calculadora de Mary" + RESET);

        // Ciclo do-while: el menú se repite hasta que se elija 0 (Salir)
        do {
            mostrarMenu();
            opcion = leerEntero("Escribe el número de la opción: ");

            // Según la opción elegida se piden los datos y se llama a calcular()
            switch (opcion) {
                case 1 -> calcular(op, "+", leerNumero("Primer número: "), leerNumero("Segundo número: "));
                case 2 -> calcular(op, "-", leerNumero("Primer número: "), leerNumero("Segundo número: "));
                case 3 -> calcular(op, "x", leerNumero("Primer número: "), leerNumero("Segundo número: "));
                case 4 -> calcular(op, "/", leerNumero("Dividendo: "), leerNumero("Divisor: "));
                case 5 -> calcular(op, "^", leerNumero("Base: "), leerNumero("Exponente: "));
                case 6 -> calcular(op, "%", leerNumero("Número: "), leerNumero("Divisor: "));
                case 7 -> calcular(op, "porc", leerNumero("Valor: "), leerNumero("Porcentaje: "));
                case 8 -> calcular(op, "raiz", leerNumero("Número: "), 0);
                case 9 -> mostrarHistorial();
                case 0 -> System.out.println(VERDE + "Hasta luego. Operaciones hechas: " + historial.size() + RESET);
                default -> System.out.println(ROJO + "Esa opción no existe, elige entre 0 y 9." + RESET);
            }
        } while (opcion != 0);

        teclado.close();
    }

    // Imprime las opciones disponibles
    static void mostrarMenu() {
        System.out.println();
        System.out.println(AZUL + "=========== MENÚ ===========" + RESET);
        System.out.println(" 1. Suma");
        System.out.println(" 2. Resta");
        System.out.println(" 3. Multiplicación");
        System.out.println(" 4. División");
        System.out.println(" 5. Potencia");
        System.out.println(" 6. Residuo (módulo)");
        System.out.println(" 7. Porcentaje");
        System.out.println(" 8. Raíz cuadrada");
        System.out.println(" 9. Ver historial");
        System.out.println(" 0. Salir");
        System.out.println(AZUL + "============================" + RESET);
    }

    // Ejecuta la operación indicada por "tipo", arma el texto y lo guarda en el historial
    static void calcular(Operaciones op, String tipo, double a, double b) {
        // try-catch atrapa los casos no permitidos (dividir por cero, raíz de negativo)
        try {
            double r;
            String texto;
            switch (tipo) {
                case "+" -> { r = op.suma(a, b); texto = a + " + " + b; }
                case "-" -> { r = op.resta(a, b); texto = a + " - " + b; }
                case "x" -> { r = op.multiplicacion(a, b); texto = a + " x " + b; }
                case "/" -> { r = op.division(a, b); texto = a + " / " + b; }
                case "^" -> { r = op.potencia(a, b); texto = a + " ^ " + b; }
                case "%" -> { r = op.residuo(a, b); texto = a + " % " + b; }
                case "porc" -> { r = op.porcentaje(a, b); texto = b + "% de " + a; }
                default -> { r = op.raizCuadrada(a); texto = "raíz de " + a; }
            }
            String linea = texto + " = " + r;
            historial.add(linea);
            System.out.println(VERDE + "Resultado: " + linea + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(ROJO + "Atención: " + e.getMessage() + RESET);
        }
    }

    // Recorre la lista con un for y muestra las operaciones numeradas
    static void mostrarHistorial() {
        if (historial.isEmpty()) {
            System.out.println("Todavía no has hecho operaciones.");
            return;
        }
        System.out.println(MORADO + "Historial de operaciones:" + RESET);
        for (int i = 0; i < historial.size(); i++) {
            System.out.println((i + 1) + ") " + historial.get(i));
        }
    }

    // Lee un número decimal; si la usuaria escribe letras, descarta la entrada y pregunta otra vez
    static double leerNumero(String mensaje) {
        System.out.print(mensaje);
        while (!teclado.hasNextDouble()) {
            System.out.print(ROJO + "Eso no es un número, intenta de nuevo: " + RESET);
            teclado.next();
        }
        return teclado.nextDouble();
    }

    // Lee un número entero para escoger la opción del menú, con la misma validación
    static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!teclado.hasNextInt()) {
            System.out.print(ROJO + "Escribe solo el número de la opción: " + RESET);
            teclado.next();
        }
        return teclado.nextInt();
    }
}
