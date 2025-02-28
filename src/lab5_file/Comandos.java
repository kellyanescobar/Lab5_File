/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5_file;
import javax.swing.JOptionPane;

/**
 *
 * @author laraj
 */

public class Comandos {
 private Archivo gestor;
 private GUIConsola consola;

    public Comandos(Archivo gestor, GUIConsola consola) {
        this.gestor = gestor;
        this.consola = consola;
    }

    public String procesarComando(String comando) {
        if (comando.trim().isEmpty()) {
            return "";
        }
        String[] partes = comando.split("\\s+");
        String instruccion = partes[0].toLowerCase();

        try {
            switch (instruccion) {
                case "mkdir":
                    return partes.length >= 2 ? gestor.crearCarpeta(partes[1]) : "Uso: mkdir <nombre>";
                case "mfile":
                    return partes.length >= 2 ? gestor.crearArchivo(partes[1]) : "Uso: mfile <nombre.ext>";
                case "rm":
                    return partes.length >= 2 ? gestor.eliminar(partes[1]) : "Uso: rm <nombre>";
                case "cd":
                    return partes.length >= 2 ? gestor.cambiarDirectorio(partes[1]) : "Uso: cd <nombre carpeta>";
                case "dir":
                    return gestor.listarElementos();
                case "date":
                    return gestor.obtenerFecha();
                case "time":
                    return gestor.obtenerHora();
                case "wr":
                    if (partes.length >= 2) {
                        consola.iniciarModoEscritura(partes[1]);
                        return "Escriba el texto a escribir:";
                    } else {
                        return "Uso: wr <archivo.ext>";
                    }
                case "rd":
                    return partes.length >= 2 ? gestor.leerArchivo(partes[1]) : "Uso: rd <archivo.ext>";
                default:
                    return "Comando no reconocido.";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}