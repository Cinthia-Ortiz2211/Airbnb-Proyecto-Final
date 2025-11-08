# Programación II

## Trabajo Práctico Final

------------------------------------------------------------------------

## Opción 3: Tema libre

El tema elegido es un sistema de gestion de alojamientos (Airbnb).

Por lo cual sera necesario cumplir con los siguientes requermientos:


---

### Gestión de usuarios

- [ ] El sistema debe permitir **registrar** nuevos usuarios, solicitando los datos básicos (nombre, mail, contraseña, tipo de usuario).
- [ ] El sistema debe permitir **iniciar sesión** y **cerrar sesión**.
- [ ] El usuario podrá **actualizar su perfil** (nombre, mail, contraseña o teléfono).
- [ ] Según el tipo de usuario:
    - [ ] **Administrador**: podrá generar códigos de descuento, listar usuarios y ver pagos.
    - [ ] **Anfitrión**: podrá gestionar sus alojamientos y ver las reservas asociadas.
    - [ ] **Viajero**: podrá buscar, filtrar y reservar alojamientos, además de enviar reseñas.

---

### Gestión de alojamientos

- [ ] Un **anfitrión** debe poder **agregar nuevos alojamientos**, definiendo dirección, tipo, nivel, descripción, precio por noche y fechas disponibles.
- [ ] El sistema debe permitir **listar los alojamientos** existentes.
- [ ] Los **viajeros** deben poder **buscar y filtrar alojamientos** según ciertos criterios (tipo, precio, disponibilidad, etc.).
- [ ] Los usuarios podrán **ver las reseñas** asociadas a cada alojamiento.

---

### Gestión de reservas

- [ ] Un **viajero** podrá **crear una reserva**, seleccionando un alojamiento, las fechas de inicio y fin, y confirmando el pago.
- [ ] El sistema debe calcular el **costo total** según el precio por noche y los días reservados.
- [ ] El viajero podrá **cancelar una reserva existente**.
- [ ] El sistema debe permitir **ver el detalle de una reserva** y **cambiar el destino** si aún está pendiente.

---

### Gestión de pagos

- [ ] El sistema debe permitir **procesar un pago** al confirmar una reserva.
- [ ] El pago podrá tener diferentes **estados**: *Pendiente*, *Aprobado* o *Rechazado*.
- [ ] El administrador podrá **listar todos los pagos realizados** en el sistema.

---

### Códigos de descuento

- [ ] El **administrador** podrá **crear nuevos códigos de descuento**, indicando monto, porcentaje y fecha de expiración.
- [ ] Los **viajeros** podrán **aplicar un código de descuento** al momento de realizar una reserva, siempre que esté disponible y no vencido.

---

### Reseñas

- [ ] Los **viajeros** podrán **enviar reseñas** sobre un alojamiento o anfitrión luego de una reserva.
- [ ] Cada reseña debe contener un puntaje, comentario, autor y fecha.
- [ ] Los usuarios podrán **ver o eliminar** reseñas asociadas a un alojamiento o a su perfil.

---

### Flujo básico de ejecución del Main

El método `main()` deberá simular un flujo típico de uso del sistema:

- [ ] **Registro e inicio de sesión de usuarios** (Administrador, Anfitrión y Viajero).
- [ ] **Creación de alojamientos** por parte del anfitrión.
- [ ] **Búsqueda de alojamientos** y **creación de una reserva** por parte del viajero.
- [ ] **Aplicación de un código de descuento** y **procesamiento del pago**.
- [ ] **Confirmación o cancelación de la reserva**.
- [ ] **Envío de una reseña** sobre el alojamiento.
- [ ] **Listados y consultas** de datos generales (usuarios, pagos, reservas, reseñas).

---

------------------------------------------------------------------------

### Requisitos y conceptos obligatorios / opcionales

Durante el desarrollo del trabajo deberán implementarse los conceptos
aprendidos durante el cuatrimestre.

#### **Obligatorios**

- [ ]   **Diagrama UML** del sistema.
- [ ]   **Uso de los 4 pilares de la POO:**
    - [ ]  Herencia
    - [ ]   Polimorfismo
    - [ ]   Abstracción
    - [ ]   Encapsulamiento
- [ ]   **Mínimo 5 clases** (sin contar la clase `Main`, interfaces ni
    excepciones).
- [ ]   **1 clase abstracta** y **1 interfaz**.
- [ ]   **Uso de Listas / Arreglos / Colecciones.**
- [ ]   **Manejo de errores** con al menos **2 clases de excepción
    personalizadas.**
- [ ]   **Clase genérica.**
- [ ]   **Persistencia de datos** mediante archivos (texto, binarios o
    JSON).

#### **Deseable**

- [ ]  Uso de **GIT y GITHUB** para el versionado y trabajo colaborativo.

#### **Opcional**

- [ ]   **Interfaz gráfica (GUI)** con **JavaFX.**

------------------------------------------------------------------------

### Trabajo en equipo

- [ ]  Todos los integrantes del grupo deben **commitear sus cambios** a
    medida que suben funcionalidades o corrigen código.
- [ ]  No se aceptará un único commit con todo el código o que solo una
    persona haya subido el trabajo completo.
- [ ]  **Todo el código fuente debe estar documentado** y comentado
    adecuadamente.

------------------------------------------------------------------------

