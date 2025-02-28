/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5_file;

/**
 *
 * @author Usuario
 */


import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Archivo {
    private File directorioActual;

    public Archivo() {
       
        directorioActual = new File("C:\\Users\\Usuario\\OneDrive\\captura\\NetBeansProjects");
    }

    public String obtenerRutaActual() {
        return directorioActual.getAbsolutePath();
    }

    public String crearCarpeta(String nombreCarpeta) throws Exception {
        if (nombreCarpeta == null || nombreCarpeta.trim().isEmpty()) {
            throw new Exception("Debe proporcionar un nombre válido para la carpeta.");
        }
        File nuevaCarpeta = new File(directorioActual, nombreCarpeta);
        if (nuevaCarpeta.exists()) {
            throw new Exception("La carpeta ya existe.");
        }
        if (!nuevaCarpeta.mkdir()) {
            throw new Exception("No se pudo crear la carpeta. Verifique permisos o nombre.");
        }
        return "Carpeta '" + nombreCarpeta + "' creada.";
    }

    public String crearArchivo(String nombreArchivo) throws Exception {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            throw new Exception("Debe proporcionar un nombre válido para el archivo.");
        }
        File nuevoArchivo = new File(directorioActual, nombreArchivo);
        if (nuevoArchivo.exists()) {
            throw new Exception("El archivo ya existe.");
        }
        if (!nuevoArchivo.createNewFile()) {
            throw new Exception("No se pudo crear el archivo.");
        }
        return "Archivo '" + nombreArchivo + "' creado.";
    }

    public String eliminar(String nombre) throws Exception {
        File elem = new File(directorioActual, nombre);
        if (!elem.exists()) {
            return "Elemento no encontrado.";
        }
        
        if (elem.isDirectory() && elem.list().length > 0) {
            throw new Exception("La carpeta no está vacía.");
        }
        if (!elem.delete()) {
            throw new Exception("No se pudo eliminar el elemento.");
        }
        return "Elemento '" + nombre + "' eliminado.";
    }

    public String cambiarDirectorio(String ruta) throws Exception {
        File nuevoDir;
        if (ruta.equals("..")) {
            nuevoDir = directorioActual.getParentFile();
            if (nuevoDir == null) {
                return "No se puede regresar más.";
            }
        } else {
            File pruebaAbsoluta = new File(ruta);
            nuevoDir = pruebaAbsoluta.isAbsolute() ? pruebaAbsoluta : new File(directorioActual, ruta);
        }
        if (nuevoDir.exists() && nuevoDir.isDirectory()) {
            directorioActual = nuevoDir.getAbsoluteFile();
            return "Cambiado a: " + directorioActual.getAbsolutePath();
        } else {
            throw new Exception("Carpeta no encontrada.");
        }
    }

    public String listarElementos() {
        String[] elementos = directorioActual.list();
        if (elementos == null || elementos.length == 0) {
            return "Carpeta vacía.";
        }
        StringBuilder sb = new StringBuilder();
        for (String elem : elementos) {
            sb.append(elem).append("\n");
        }
        return sb.toString();
    }

    public String obtenerFecha() {
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return hoy.format(formato);
    }

    public String obtenerHora() {
        LocalTime ahora = LocalTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
        return ahora.format(formato);
    }

    public String escribirArchivo(String nombreArchivo, String texto) throws Exception {
        File archivo = new File(directorioActual, nombreArchivo);
        if (!archivo.exists()) {
            throw new Exception("Archivo no encontrado.");
        }
        try (FileWriter escritor = new FileWriter(archivo)) {
            escritor.write(texto);
        }
        return "Texto escrito en '" + nombreArchivo + "'.";
    }

    public String leerArchivo(String nombreArchivo) throws Exception {
        File archivo = new File(directorioActual, nombreArchivo);
        if (!archivo.exists()) {
            throw new Exception("Archivo no encontrado.");
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                sb.append(linea).append("\n");
            }
        }
        return sb.toString();
    }
}
