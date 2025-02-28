/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5_file;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class GUIConsola extends JFrame {
    private JTextArea areaTexto;
    private Archivo gestor;
    private Comandos procesador;
    private int posicionPrompt;
    private FiltroDocumento filtro;

    
    private boolean modoEscritura = false;
    private String archivoEscritura = "";

    public GUIConsola() {
        setTitle("Administrator: Command Prompt");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        areaTexto = new JTextArea();
        areaTexto.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaTexto.setBackground(Color.BLACK);
        areaTexto.setForeground(Color.WHITE);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setEditable(true);

        filtro = new FiltroDocumento();
        ((AbstractDocument) areaTexto.getDocument()).setDocumentFilter(filtro);

        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);

        
        gestor = new Archivo();
        procesador = new Comandos(gestor, this);

        
        areaTexto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); 
                    procesarEntrada();
                }
            }
        });

        
        areaTexto.setText("Microsoft Windows [Version 10.0.22621.521]\n"
                          + "(c) Microsoft Windows. Todos los derechos reservados.\n\n");
        mostrarPrompt();

        setVisible(true);
    }

    
    private void mostrarPrompt() {
        String prompt = gestor.obtenerRutaActual() + "> ";
        areaTexto.append(prompt);
        posicionPrompt = areaTexto.getDocument().getLength();
        filtro.setPosicionPrompt(posicionPrompt);
        areaTexto.setCaretPosition(posicionPrompt);
    }

    
    private void procesarEntrada() {
        String textoCompleto = areaTexto.getText();
        String entrada = "";
        if (textoCompleto.length() >= posicionPrompt) {
            entrada = textoCompleto.substring(posicionPrompt).trim();
        }
        areaTexto.append("\n");
        String resultado = "";
        if (modoEscritura) {
            
            try {
                resultado = gestor.escribirArchivo(archivoEscritura, entrada);
            } catch(Exception ex) {
                resultado = "Error: " + ex.getMessage();
            }
            modoEscritura = false;
            archivoEscritura = "";
        } else {
            
            resultado = procesador.procesarComando(entrada);
        }
        if (!resultado.isEmpty()) {
            areaTexto.append(resultado + "\n");
        }
        mostrarPrompt();
    }

    
    public void iniciarModoEscritura(String archivo) {
        this.modoEscritura = true;
        this.archivoEscritura = archivo;
    }

    
    public JFrame obtenerVentana() {
        return this;
    }

    
    class FiltroDocumento extends DocumentFilter {
        private int posPrompt = 0;

        public void setPosicionPrompt(int pos) {
            posPrompt = pos;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (offset < posPrompt) {
                offset = posPrompt;
            }
            super.insertString(fb, offset, string, attr);
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            if (offset < posPrompt) {
                return;
            }
            super.remove(fb, offset, length);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (offset < posPrompt) {
                offset = posPrompt;
            }
            super.replace(fb, offset, length, text, attrs);
        }
    }
}