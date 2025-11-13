package util;

import java.io.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public class JsonUtil {

    /**
     * Graba un JSONArray en un archivo especificado.
     * @param array
     * @param rutaArchivo
     */
    public static void grabar(JSONArray array, String rutaArchivo) {
        try (FileWriter file = new FileWriter(rutaArchivo)) {
            file.write(array.toString(2));
        } catch (IOException | JSONException e) {
            System.err.println("Error al grabar JSON en " + rutaArchivo + ": " + e.getMessage());
        }
    }

    /**
     * Lee un JSONArray desde un archivo especificado.
     * Si el archivo no existe, crea uno nuevo con un JSONArray vacío.
     * @param rutaArchivo
     * @return JSONArray leído del archivo o un JSONArray vacío si el archivo no existe.
     */
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
