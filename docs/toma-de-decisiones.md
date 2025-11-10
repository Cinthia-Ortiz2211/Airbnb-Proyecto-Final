# Toma de decisiones

## Creación automática de archivos JSON inexistentes

Durante el desarrollo del sistema de gestión de alojamientos (tipo Airbnb) en Java, se utiliza la clase `JsonUtil`
como componente responsable de la **persistencia de datos** en formato JSON.
Esta clase centraliza las operaciones de lectura y escritura de información de usuarios, alojamientos,
reservas, pagos, reseñas y códigos de descuento.

En su método `leer(String rutaArchivo)`, la clase se enfrenta a un caso particular:
cuando el archivo especificado **no existe** en el sistema de archivos.

El equipo debía decidir si, ante esta situación, el método debía:
1. Lanzar una excepción y delegar la decisión al gestor, o
2. Crear automáticamente un archivo JSON vacío y continuar el flujo del programa.

---

### Alternativas consideradas

#### 1. **Lanzar una excepción (`FileNotFoundException`)**
- **Ventajas:**
    - Obliga a los gestores a manejar explícitamente la creación del archivo.
    - Evita la generación accidental de archivos en rutas erróneas.
    - Mayor control sobre la capa de persistencia.
- **Desventajas:**
    - Rompe el flujo normal de ejecución al iniciar el sistema por primera vez.
    - Requiere inicialización manual de los archivos antes de ejecutar el programa.
    - Aumenta la complejidad del código en las capas superiores (gestores).

---

#### 2. **Crear automáticamente un archivo vacío (`[]`) ✅**
- **Ventajas:**
    - Permite que el sistema funcione sin necesidad de datos iniciales.
    - Mejora la **robustez** y la **tolerancia a fallos**.
    - Facilita la primera ejecución del sistema y la experiencia del usuario.
    - Cumple con el requerimiento de “persistencia de datos” del trabajo práctico, sin configuraciones previas.
- **Desventajas:**
    - Puede crear archivos vacíos en rutas mal configuradas si no se valida la entrada.
    - Oculta posibles errores de configuración de ruta, si no se informa adecuadamente.

---

## Decisión adoptada

Se decidió **crear automáticamente un archivo JSON vacío** (`[]`) cuando no se encuentra el archivo especificado.

Esto se implementó en el método:

```java
catch (FileNotFoundException e) {
    System.out.println("Archivo no encontrado, creando uno nuevo: " + rutaArchivo);
    grabar(new JSONArray(), rutaArchivo);
    return new JSONArray();
}