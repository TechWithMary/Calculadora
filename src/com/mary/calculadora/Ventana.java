package com.mary.calculadora;

import javax.swing.*;
import java.awt.*;

/**
 * Versión con ventana de la calculadora de Mary.
 * Usa la misma clase Operaciones que la versión de consola (App),
 * solo cambia la forma de pedir los datos y mostrar el resultado.
 */
public class Ventana extends JFrame {

    // Objeto con los métodos matemáticos (la misma base de la versión de consola)
    private final Operaciones op = new Operaciones();

    // Campos donde se escriben los dos números
    private final JTextField campoA = new JTextField(10);
    private final JTextField campoB = new JTextField(10);

    // Etiqueta donde se muestra el resultado o el aviso de error
    private final JLabel lblResultado = new JLabel("Escribe los números y elige una operación", SwingConstants.CENTER);

    // Área de texto que funciona como historial
    private final JTextArea areaHistorial = new JTextArea(8, 30);

    // Colores de la ventana
    // Colores del diseño oscuro moderno
    private static final Color AZUL = new Color(59, 130, 246);
    private static final Color FONDO = new Color(24, 24, 32);
    private static final Color TARJETA = new Color(38, 38, 52);
    private static final Color TEXTO = new Color(235, 235, 245);

    public Ventana() {
        super("Calculadora de Mary");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(FONDO);

        // Parte de arriba: título y los dos campos de entrada
        JPanel panelEntrada = new JPanel(new GridLayout(3, 2, 8, 8));
        panelEntrada.setBackground(FONDO);
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        JLabel titulo = new JLabel("Calculadora de Mary");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(AZUL);
        panelEntrada.add(titulo);
        panelEntrada.add(new JLabel(""));
        panelEntrada.add(etiqueta("Primer número"));
        panelEntrada.add(estilizarCampo(campoA));
        panelEntrada.add(etiqueta("Segundo número"));
        panelEntrada.add(estilizarCampo(campoB));
        add(panelEntrada, BorderLayout.NORTH);

        // Centro: un botón por cada operación (las mismas 8 de la consola)
        JPanel panelBotones = new JPanel(new GridLayout(2, 4, 8, 8));
        panelBotones.setBackground(FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        String[] operaciones = {"+", "-", "x", "/", "^", "%", "porc", "raiz"};
        String[] nombres = {"+", "−", "×", "÷", "xʸ", "mod", "%", "√"};
        for (int i = 0; i < operaciones.length; i++) {
            String tipo = operaciones[i];
            JButton boton = new JButton(nombres[i]);
            estilizarBoton(boton, i < 4 ? AZUL : TARJETA, 24);
            // Cada botón llama a calcular() con su tipo de operación
            boton.addActionListener(e -> calcular(tipo));
            panelBotones.add(boton);
        }

        // Abajo: resultado, historial y botón para limpiar
        JPanel panelAbajo = new JPanel(new BorderLayout(8, 8));
        panelAbajo.setBackground(FONDO);
        panelAbajo.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        lblResultado.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblResultado.setForeground(TEXTO);
        lblResultado.setBorder(BorderFactory.createEmptyBorder(12, 0, 4, 0));
        panelAbajo.add(lblResultado, BorderLayout.NORTH);
        areaHistorial.setEditable(false);
        areaHistorial.setBackground(TARJETA);
        areaHistorial.setForeground(new Color(180, 180, 200));
        areaHistorial.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaHistorial.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JScrollPane scroll = new JScrollPane(areaHistorial);
        scroll.setBorder(BorderFactory.createLineBorder(TARJETA));
        panelAbajo.add(scroll, BorderLayout.CENTER);
        JButton limpiar = new JButton("C  Limpiar");
        estilizarBoton(limpiar, new Color(220, 70, 90), 15);
        limpiar.addActionListener(e -> {
            campoA.setText("");
            campoB.setText("");
            lblResultado.setText("Escribe los números y elige una operación");
        });
        panelAbajo.add(limpiar, BorderLayout.SOUTH);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBackground(FONDO);
        centro.add(panelBotones, BorderLayout.NORTH);
        centro.add(panelAbajo, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // centra la ventana en la pantalla
    }

    // Lee los campos, valida que sean números y ejecuta la operación elegida
    private void calcular(String tipo) {
        try {
            double a = Double.parseDouble(campoA.getText().trim());
            // La raíz solo necesita el primer número
            double b = tipo.equals("raiz") ? 0 : Double.parseDouble(campoB.getText().trim());
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
            lblResultado.setForeground(new Color(74, 222, 128));
            lblResultado.setText("Resultado: " + linea);
            areaHistorial.append(linea + "\n");
        } catch (NumberFormatException e) {
            // Entra aquí si el campo está vacío o tiene letras
            mostrarError("Escribe números válidos en los campos.");
        } catch (IllegalArgumentException e) {
            // Entra aquí con división por cero o raíz de un negativo
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        lblResultado.setForeground(new Color(248, 113, 113));
        lblResultado.setText("Atención: " + mensaje);
    }

    // Da a un botón el estilo plano: color de fondo, texto blanco, letra grande y cursor de mano
    private void estilizarBoton(JButton boton, Color fondo, int tamano) {
        boton.setBackground(fondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, tamano));
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(80, 60));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Da a un campo de texto el estilo oscuro con letra grande
    private JTextField estilizarCampo(JTextField campo) {
        campo.setBackground(TARJETA);
        campo.setForeground(TEXTO);
        campo.setCaretColor(TEXTO);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 20));
        campo.setHorizontalAlignment(JTextField.RIGHT);
        campo.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        return campo;
    }

    // Crea una etiqueta con el color de texto claro
    private JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(new Color(170, 170, 190));
        l.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return l;
    }

    public static void main(String[] args) {
        // Usa el aspecto multiplataforma para que se respeten los colores en Mac
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception ignored) { }
        // Swing recomienda crear la ventana en su propio hilo de eventos
        SwingUtilities.invokeLater(() -> new Ventana().setVisible(true));
    }
}
