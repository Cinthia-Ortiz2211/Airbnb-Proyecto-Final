

```mermaid
classDiagram
    direction TB

%% ========= INTERFACES =========
    class Persistible {
        <<interface>>
        +guardarEnArchivo(): void
        +cargarDesdeArchivo(): void
    }

    class IGestor~E~ {
        <<interface>>
        +agregar(E elemento): void
        +listar(): List~E~
        +buscarPorId(id: Number): E
        +eliminar(id: Number): Boolean
    }

    class Autenticable {
        <<interface>>
        +iniciarSesion(email: Text, contrasena: Text): Boolean
        +cerrarSesion(): void
    }

    class Identificable {
        <<interface>>
        +getId(): Number
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
        - Number id
        - Text nombre
        - Text email
        - Text contrasena
        - Text numeroTelefonico
        - DateTime fechaRegistro
        - TipoUsuario tipoUsuario
        +getId(): Number
        +getNombre(): Text
        +getEmail(): Text
        +getTipoUsuario(): TipoUsuario
        +actualizarPerfil(nombre: Text, email: Text, contrasena: Text, telefono: Text): void
    }

    Identificable <|.. Usuario
    Autenticable <|.. Usuario

%% ========= SUBCLASES DE USUARIO =========
    class Administrador {
        +Administrador(nombre: Text, email: Text, contrasena: Text)
    }

    class Anfitrion {
        +Anfitrion(nombre: Text, email: Text, contrasena: Text)
    }

    class Viajero {
        +Viajero(nombre: Text, email: Text, contrasena: Text)
    }

    Usuario <|-- Administrador
    Usuario <|-- Anfitrion
    Usuario <|-- Viajero

%% ========= CLASES DE DOMINIO =========
    class Alojamiento {
        - Number id
        - Text direccion
        - TipoAlojamiento tipo
        - Text nivel
        - Text descripcion
        - Decimal precioPorNoche
        - List~Date~ fechasDisponibles
        - Anfitrion anfitrion
        +esDisponible(inicio: Date, fin: Date): Boolean
    }

    class Reserva {
        - Number id
        - Alojamiento alojamiento
        - Viajero viajero
        - Date fechaInicio
        - Date fechaFin
        - Decimal costoTotal
        - Boolean pendiente
        +calcularCosto(): Decimal
        +verDetalle(): Text
        +cancelar(): void
    }

    class Pago {
        - Number id
        - Reserva reserva
        - Decimal monto
        - EstadoPago estado
        +procesar(): void
    }

    class CodigoDescuento {
        - Number id
        - Text codigo
        - TipoCodigoDescuento tipo
        - Decimal monto
        - DateTime fechaExpiracion
        +esValido(fechaActual: DateTime): Boolean
    }

    class Resenia {
        - Number id
        - Number puntaje
        - Text comentario
        - Viajero autor
        - DateTime fecha
        - Alojamiento alojamiento
        - Anfitrion anfitrion
    }

    Identificable <|.. Alojamiento
    Identificable <|.. Reserva
    Identificable <|.. Pago
    Identificable <|.. CodigoDescuento
    Identificable <|.. Resenia

%% ========= RELACIONES ENTRE ENTIDADES =========
    Anfitrion "1" --> "many" Alojamiento : publica
    Alojamiento "1" --> "many" Resenia : tiene
    Viajero "1" --> "many" Reserva : realiza
    Viajero "1" --> "many" Resenia : escribe
    Reserva "1" --> "1" Alojamiento
    Reserva "1" --> "0..1" CodigoDescuento
    Reserva "1" --> "1" Pago

%% ========= CLASE GENÉRICA BASE =========
    class Gestor~E extends Identificable~ {
        <<generic>>
        - List~E~ elementos
        +agregar(elemento: E): void
        +listar(): List~E~
        +buscarPorId(id: Number): E
        +eliminar(id: Number): Boolean
    }

    IGestor <|.. Gestor
    Persistible <|.. Gestor

%% ========= GESTORES ESPECIALIZADOS =========
    class GestorUsuario
    class GestorAlojamiento
    class GestorReserva
    class GestorPago
    class GestorCodigoDescuento
    class GestorResenia

    Gestor <|-- GestorUsuario
    Gestor <|-- GestorAlojamiento
    Gestor <|-- GestorReserva
    Gestor <|-- GestorPago
    Gestor <|-- GestorCodigoDescuento
    Gestor <|-- GestorResenia

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
`IGestor<E>` define la estructura genérica de los gestores mediante operaciones CRUD;
`Persistible` establece el contrato para la persistencia de datos;
y `Autenticable` define las operaciones básicas de autenticación que comparten los diferentes tipos de usuario.

Estas interfaces promueven el bajo acoplamiento y la alta cohesión, asegurando que cada clase tenga una única responsabilidad.

La clase genérica `Gestor<E>` introduce el uso de parámetros de tipo (T) para abstraer las operaciones comunes de gestión sobre diferentes entidades del sistema.
Gracias a la genericidad, el diseño evita la duplicación de código y permite reutilizar la misma estructura base para administrar distintos tipos de objetos (`Usuario`, `Alojamiento`, `Reserva`, `Pago`, `CodigoDescuento`, `Resenia`) sin necesidad de implementar versiones específicas de métodos CRUD para cada uno.

De esta forma, `Gestor<E>` actúa como una superclase genérica que define comportamientos compartidos, mientras que las subclases (`GestorUsuario`, `GestorAlojamiento`, etc.) especializan la lógica de negocio según el dominio correspondiente.
Esto refuerza los principios de abstracción y polimorfismo paramétrico, promoviendo un bajo acoplamiento entre los tipos concretos y las operaciones que los manipulan.

Además, la combinación entre `IGestor<E>` (interfaz genérica) y `Gestor<E>` (implementación genérica) permite que el sistema sea fácilmente extensible:
para incorporar una nueva entidad (por ejemplo, “Notificación” o “Factura”), basta con crear una nueva clase `GestorNotificacion` que herede de `Gestor<Notificacion>`, sin alterar el resto del sistema.