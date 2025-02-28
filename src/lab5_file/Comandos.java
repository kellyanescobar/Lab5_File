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
    
private Archivo Archivo;
private ArchivoGUI consolaGUI;

    public Comandos(Archivo GestorArchivo, ArchivoGUI consolaGUI){
        this.Archivo=GestorArchivo;
        this.consolaGUI=consolaGUI;
    }

    public String ProcesarComando(String comandoCompleto){
        if(comandoCompleto.isEmpty()){
            return "";
        }

        String[] partes=comandoCompleto.split("\\s+");
        String comando=partes[0].toLowerCase();

        try {
            switch(comando){
                case "mkdir":
                    if(partes.length >= 2){
                        return Archivo.crearCarpeta(partes[1]);
                    }else{
                        return "Uso: mkdir <nombre>";
                    }

                case "mfile":
                    if(partes.length >= 2){
                        return Archivo.crearArchivo(partes[1]);
                    }else{
                        return "Uso: mfile <nombre.ext>";
                    }

                case "rm":
                    if(partes.length >= 2){
                        return Archivo.eliminar(partes[1]);
                    } else {
                        return "Uso: rm <nombre>";
                    }

                case "cd":
                    
                    String ruta = comandoCompleto.substring(2).trim();
                    if(ruta.isEmpty()){
                        return "Uso: cd <ruta>";
                    }
                    return Archivo.cambiarDirectorio(ruta);

                case "dir":
                    return Archivo.listar();

                case "date":
                    return Archivo.obtenerFecha();

                case "time":
                    return Archivo.obtenerHora();

                case "wr":
                    if(partes.length >= 2){
                        String nombreArchivo = partes[1];
                        
                        String textoAEscribir = JOptionPane.showInputDialog(
                                consolaGUI,
                                "Ingrese el texto a escribir:",
                                "Escribir en " + nombreArchivo,
                                JOptionPane.PLAIN_MESSAGE
                        );
                        if(textoAEscribir != null){
                            return Archivo.escribirArchivo(nombreArchivo, textoAEscribir);
                        } else {
                            return "Operacion cancelada";
                        }
                    } else {
                        return "Uso: wr <archivo>";
                    }

                case "rd":
                    if(partes.length >= 2) {
                        return Archivo.leerArchivo(partes[1]);
                    } else {
                        return "Uso: rd <archivo>";
                    }

                default:
                    return "Comando no reconocido";
            }
        } catch(Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}

