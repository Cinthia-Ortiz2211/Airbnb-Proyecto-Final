package util;

import java.io.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public class JsonUtil {

    public static void grabar(JSONArray array, String rutaArchivo) {
        try (FileWriter file = new FileWriter(rutaArchivo)) {
            file.write(array.toString(2));
        } catch (IOException | JSONException e) {
            System.err.println("Error al grabar JSON en " + rutaArchivo + ": " + e.getMessage());
        }
    }

    public static JSONArray leer(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            return new JSONArray(new JSONTokener(reader));
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado, creando uno nuevo: " + rutaArchivo);
            grabar(new JSONArray(), rutaArchivo);
            return new JSONArray();
        } catch (IOException | JSONException e) {
            System.err.println("Error al leer JSON: " + e.getMessage());
            return new JSONArray();
        }
    }

}
