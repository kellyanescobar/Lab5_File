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
    
        directorioActual = new File("C:\\Windows\\System32");
    }

    public String obtenerRutaActual() {
        return directorioActual.getAbsolutePath();
    }

    public String crearCarpeta(String nombreCarpeta) throws Exception {
        File nuevaCarpeta = new File(directorioActual, nombreCarpeta);
        if(nuevaCarpeta.exists()) {
            throw new Exception("La carpeta ya existe.");
        }
        if(nuevaCarpeta.mkdir()){
            return "Carpeta '" + nombreCarpeta + "' creada.";
        } else {
            throw new Exception("No se pudo crear la carpeta.");
        }
    }

    public String crearArchivo(String nombreArchivo) throws Exception {
        File nuevoArchivo = new File(directorioActual, nombreArchivo);
        if(nuevoArchivo.exists()){
            throw new Exception("El archivo ya existe.");
        }
        if(nuevoArchivo.createNewFile()){
            return "Archivo '" + nombreArchivo + "' creado.";
        } else {
            throw new Exception("No se pudo crear el archivo.");
        }
    }

    public String eliminar(String nombre) throws Exception {
        File archivoOCarpeta = new File(directorioActual, nombre);
        if(!archivoOCarpeta.exists()){
            return "El elemento no se encontro.";
        }
        
        if(archivoOCarpeta.isDirectory()){
            String[] contenido = archivoOCarpeta.list();
            if(contenido != null && contenido.length > 0) {
                throw new Exception("La carpeta no está vacía.");
            }
        }
        if(archivoOCarpeta.delete()){
            if(archivoOCarpeta.isDirectory()){
                return "Carpeta '" + nombre + "' eliminada.";
            } else {
                return "Archivo '" + nombre + "' eliminado.";
            }
        } else {
            throw new Exception("No se pudo eliminar el elemento.");
        }
    }

    public String cambiarDirectorio(String ruta) throws Exception {
        File nuevoDirectorio;
        
       
        if(ruta.equals("..")) {
            nuevoDirectorio = directorioActual.getParentFile();
            if(nuevoDirectorio == null){
                return "No se puede regresar más.";
            }
        } else {
            
            
            File pruebaAbsoluta = new File(ruta);
            if(pruebaAbsoluta.isAbsolute()) {
                nuevoDirectorio = pruebaAbsoluta;
            } else {
                nuevoDirectorio = new File(directorioActual, ruta);
            }
        }
        
       
        if(nuevoDirectorio.exists() && nuevoDirectorio.isDirectory()){
            directorioActual = nuevoDirectorio.getAbsoluteFile();
            return "Cambiado a: " + directorioActual.getAbsolutePath();
        } else {
            throw new Exception("Carpeta no encontrada.");
        }
    }

    public String listar() {
        String[] elementos = directorioActual.list();
        if(elementos == null || elementos.length == 0){
            return "Carpeta vacía.";
        }
        StringBuilder sb = new StringBuilder();
        for(String elem : elementos){
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
        if(!archivo.exists()){
            throw new Exception("Archivo no encontrado.");
        }
        try(FileWriter escritor = new FileWriter(archivo)) {
            escritor.write(texto);
        }
        return "Texto escrito en '" + nombreArchivo + "'.";
    }

    public String leerArchivo(String nombreArchivo) throws Exception {
        File archivo = new File(directorioActual, nombreArchivo);
        if(!archivo.exists()){
            throw new Exception("Archivo no encontrado.");
        }
        StringBuilder contenido = new StringBuilder();
        try(BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while((linea = lector.readLine()) != null){
                contenido.append(linea).append("\n");
            }
        }
        return contenido.toString();
    }





}
