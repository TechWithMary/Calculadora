package com.mary.calculadora;

/**
 * Contiene la lógica matemática de la calculadora.
 * Cada método recibe los números y devuelve el resultado.
 */
public class Operaciones {

    // Suma dos números
    public double suma(double x, double y) {
        return x + y;
    }

    // Resta el segundo número al primero
    public double resta(double x, double y) {
        return x - y;
    }

    // Multiplica dos números
    public double multiplicacion(double x, double y) {
        return x * y;
    }

    // Divide; si el divisor es cero lanza una excepción para avisar del error
    public double division(double x, double y) {
        if (y == 0) {
            throw new IllegalArgumentException("No se puede dividir por cero.");
        }
        return x / y;
    }

    // Eleva la base al exponente usando Math.pow
    public double potencia(double base, double exponente) {
        return Math.pow(base, exponente);
    }

    // Devuelve lo que sobra de la división (operador %)
    public double residuo(double x, double y) {
        if (y == 0) {
            throw new IllegalArgumentException("No se puede sacar residuo con divisor cero.");
        }
        return x % y;
    }

    // Calcula cuánto es cierto porcentaje de un valor
    public double porcentaje(double valor, double porciento) {
        return valor * porciento / 100;
    }

    // Saca la raíz cuadrada con Math.sqrt; no acepta negativos
    public double raizCuadrada(double x) {
        if (x < 0) {
            throw new IllegalArgumentException("No existe raíz cuadrada real de un número negativo.");
        }
        return Math.sqrt(x);
    }
}
