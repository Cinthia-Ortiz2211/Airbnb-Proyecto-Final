

```mermaid
classDiagram
    direction TB

%% ========= INTERFACES =========
    class Persistible {
        <<interface>>
        +guardarEnArchivo(): void
        +cargarDesdeArchivo(): void
    }

    class IGestor~T~ {
        <<interface>>
        +agregar(T elemento): void
        +listar(): java.util.List~T~
        +buscarPorId(int id): T
        +eliminar(int id): void
    }

    class Autenticable {
        <<interface>>
        +iniciarSesion(email:String, contrasena:String): boolean
        +cerrarSesion(): void
    }

%% ========= ENUMS =========
    class TipoUsuario {
        <<enumeration>>
        ADMINISTRADOR
        ANFITRION
        VIAJERO
    }

    class TipoAlojamiento {
        <<enumeration>>
        CASA
        DEPARTAMENTO
        HABITACION
    }

    class EstadoPago {
        <<enumeration>>
        PENDIENTE
        APROBADO
        RECHAZADO
    }

    class TipoCodigoDescuento {
        <<enumeration>>
        FIJO
        PORCENTAJE
    }

%% ========= CLASE ABSTRACTA USUARIO =========
    class Usuario {
        <<abstract>>
        - int id
        - String nombre
        - String email
        - String contrasena
        - String numeroTelefonico
        - java.time.LocalDateTime fechaDeRegistro
        - TipoUsuario tipoUsuario
        +getId(): int
        +getNombre(): String
        +getEmail(): String
        +getTipoUsuario(): TipoUsuario
        +actualizarPerfil(nombre:String, email:String, contrasena:String, telefono:String): void
    }

%% ========= SUBCLASES DE USUARIO =========
    class Administrador {
        +Administrador(nombre:String, email:String, contrasena:String)
    }

    class Anfitrion {
        +Anfitrion(nombre:String, email:String, contrasena:String)
    }

    class Viajero {
        +Viajero(nombre:String, email:String, contrasena:String)
    }

    Autenticable <|.. Usuario
    Usuario <|-- Administrador
    Usuario <|-- Anfitrion
    Usuario <|-- Viajero

%% ========= CLASES DE DOMINIO =========
    class Alojamiento {
        - int id
        - String direccion
        - TipoAlojamiento tipo
        - String nivel
        - String descripcion
        - double precioPorNoche
        - java.util.List~java.time.LocalDate~ fechasDisponibles
        - Anfitrion anfitrion
        +esDisponible(inicio:LocalDate, fin:LocalDate): boolean
    }

    class Reserva {
        - int id
        - Alojamiento alojamiento
        - Viajero viajero
        - java.time.LocalDate fechaInicio
        - java.time.LocalDate fechaFin
        - double costoTotal
        - boolean pendiente
        +calcularCosto(): double
        +verDetalle(): String
        +cambiarDestino(nuevo:Alojamiento): void
        +cancelar(): void
    }

    class Pago {
        - int id
        - Reserva reserva
        - double monto
        - EstadoPago estado
        +procesar(): void
    }

    class CodigoDescuento {
        - String codigo
        - TipoCodigoDescuento tipoCodigoDescuento
        - double monto
        - java.time.LocalDateTime fechaExpiracion
        +esValido(fechaActual:LocalDateTime): boolean
    }

    class Resenia {
        - int id
        - int puntaje
        - String comentario
        - Viajero autor
        - java.time.LocalDateTime fecha
        - Alojamiento alojamiento  %% opcional: resenia de alojamiento
        - Anfitrion anfitrion      %% opcional: resenia de anfitrión
    }

    Anfitrion "1" --> "many" Alojamiento : publica
    Alojamiento "1" --> "many" Resenia : tiene
    Viajero "1" --> "many" Reserva : realiza
    Viajero "1" --> "many" Resenia : escribe
    Reserva "1" --> "1" Alojamiento
    Reserva "1" --> "0..1" CodigoDescuento
    Reserva "1" --> "1" Pago

%% ========= CLASE GENÉRICA BASE =========
    class Gestor~T~ {
        <<generic>>
        - java.util.List~T~ elementos
        +agregar(T elemento): void
        +listar(): java.util.List~T~
        +buscarPorId(int id): T
        +eliminar(int id): void
    }

%% ========= GESTORES ESPECIALIZADOS =========
    class GestorUsuario {
        - java.util.List~Usuario~ usuarios
        +registrar(nombre:String, email:String, contrasena:String, telefono:String, tipo:TipoUsuario): Usuario
        +iniciarSesion(email:String, contrasena:String): Usuario
        +cerrarSesion(Usuario usuario): void
        +listarUsuarios(solicitante:Usuario): java.util.List~Usuario~
        +actualizarPerfil(Usuario usuario, ...): void
    }

    class GestorAlojamiento {
        - java.util.List~Alojamiento~ alojamientos
        +agregarAlojamiento(Anfitrion anfitrion, Alojamiento alojamiento): void
        +listarAlojamientos(): java.util.List~Alojamiento~
        +buscarPorCriterios(tipo:TipoAlojamiento, precioMax:double, disponible:boolean): java.util.List~Alojamiento~
    }

    class GestorReserva {
        - java.util.List~Reserva~ reservas
        +crearReserva(viajero:Viajero, alojamiento:Alojamiento, inicio:LocalDate, fin:LocalDate, codigo:CodigoDescuento): Reserva
        +cancelarReserva(viajero:Viajero, reserva:Reserva): void
        +verDetalleReserva(id:int, solicitante:Usuario): Reserva
    }

    class GestorPago {
        - java.util.List~Pago~ pagos
        +procesarPago(reserva:Reserva): Pago
        +listarPagos(solicitante:Usuario): java.util.List~Pago~
    }

    class GestorCodigoDescuento {
        - java.util.List~CodigoDescuento~ codigos
        +crearCodigo(admin:Administrador, codigo:String, tipoCodigoDescuento:TipoCodigoDescuento, monto:double, expira:LocalDateTime): void
        +buscarCodigo(codigo:String): CodigoDescuento
        +aplicarCodigo(reserva:Reserva, codigo:String): void
    }

    class GestorResenia {
        - java.util.List~Resenia~ resenias
        +crearReseniaAlojamiento(v:Viajero, a:Alojamiento, puntaje:int, comentario:String): Resenia
        +crearReseniaAnfitrion(v:Viajero, h:Anfitrion, puntaje:int, comentario:String): Resenia
        +listarPorAlojamiento(a:Alojamiento): java.util.List~Resenia~
        +listarPorUsuario(u:Usuario): java.util.List~Resenia~
        +eliminarResenia(solicitante:Usuario, resenia:Resenia): void
    }

%% ========= RELACIONES DE HERENCIA E IMPLEMENTACIÓN =========
    IGestor <|.. Gestor
    Persistible <|.. Gestor
    Autenticable <|.. Usuario

    Gestor <|-- GestorUsuario
    Gestor <|-- GestorAlojamiento
    Gestor <|-- GestorReserva
    Gestor <|-- GestorPago
    Gestor <|-- GestorCodigoDescuento
    Gestor <|-- GestorResenia

%% ========= IMPLEMENTACIÓN DE PERSISTENCIA =========
    GestorUsuario ..|> Persistible
    GestorAlojamiento ..|> Persistible
    GestorReserva ..|> Persistible
    GestorPago ..|> Persistible
    GestorCodigoDescuento ..|> Persistible
    GestorResenia ..|> Persistible

%% ========= EXCEPCIONES =========
    class UsuarioNoEncontradoException {
        <<exception>>
    }

    class ReservaInvalidaException {
        <<exception>>
    }

    class CodigoDescuentoInvalidoException {
        <<exception>>
    }

````

En este modelo, los tipos de datos se expresan de forma agnóstica al lenguaje de programación, empleando convenciones estándar del UML:
`Number`, `Text`, `Decimal`, `Boolean`, `Date` y `DateTime`.

De esta forma, el UML representa un modelo conceptual del sistema de gestión de alojamientos, y no una descripción específica de código Java.

Las clases `Administrador`, `Anfitrión` y `Viajero` heredan de la clase abstracta `Usuario`, pero no implementan métodos propios.
Su función principal es actuar como roles del sistema, permitiendo que los gestores apliquen validaciones de permiso mediante polimorfismo (`instanceof`) o el atributo `TipoUsuario`.

De este modo, se respeta el principio de responsabilidad única (SRP), ya que las entidades modelan solo datos y los gestores encapsulan la lógica de negocio y las reglas del sistema.

Esta separación refuerza el encapsulamiento y simplifica el mantenimiento, donde los gestores funcionan como controladores de casos de uso y las entidades representan el estado del sistema.

También se implementa tres interfaces principales:
`IGestor<T>` define la estructura genérica de los gestores mediante operaciones CRUD;
`Persistible` establece el contrato para la persistencia de datos;
y `Autenticable` define las operaciones básicas de autenticación que comparten los diferentes tipos de usuario.

Estas interfaces promueven el bajo acoplamiento y la alta cohesión, asegurando que cada clase tenga una única responsabilidad.

La clase genérica `Gestor<T>` introduce el uso de parámetros de tipo (T) para abstraer las operaciones comunes de gestión sobre diferentes entidades del sistema.
Gracias a la genericidad, el diseño evita la duplicación de código y permite reutilizar la misma estructura base para administrar distintos tipos de objetos (`Usuario`, `Alojamiento`, `Reserva`, `Pago`, `CodigoDescuento`, `Resenia`) sin necesidad de implementar versiones específicas de métodos CRUD para cada uno.

De esta forma, `Gestor<T>` actúa como una superclase genérica que define comportamientos compartidos, mientras que las subclases (`GestorUsuario`, `GestorAlojamiento`, etc.) especializan la lógica de negocio según el dominio correspondiente.
Esto refuerza los principios de abstracción y polimorfismo paramétrico, promoviendo un bajo acoplamiento entre los tipos concretos y las operaciones que los manipulan.

Además, la combinación entre `IGestor<T>` (interfaz genérica) y `Gestor<T>` (implementación genérica) permite que el sistema sea fácilmente extensible:
para incorporar una nueva entidad (por ejemplo, “Notificación” o “Factura”), basta con crear una nueva clase `GestorNotificacion` que herede de `Gestor<Notificacion>`, sin alterar el resto del sistema.