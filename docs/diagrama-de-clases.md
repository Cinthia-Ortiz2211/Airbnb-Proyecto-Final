```mermaid
classDiagram
class Usuario {
+int id
+string nombre
+string mail
+string contrasena
+string numeroTelefonico
+int tipoDeUsuario
+datetime fechaDeRegistro
+float valoracion
+registrar(nombreUsuario, contrasena, mail)
+ingresar(nombreUsuario, contrasena)
+cerrarSesion(idUsuario)
+actualizaPerfil(idUsuario, nombre, mail, contrasena, numeroTelefonico)
+verResenas(idUsuario)
}

    class Admin {
        +generarCodigoDescuento(idCodigo)
        +listarUsuarios()
        +listarPagos()
    }

    class Anfitrion {
        +verReservas(idUsuario)
        +listarAlojamientos(idUsuario)
    }

    class Viajero {
        +buscarAlojamiento()
        +filtrarAlojamientos()
        +verHistorialDeReservas(idUsuario)
        +enviarResena(idResena)
    }

    class Alojamiento {
        +int idAlojamiento
        +string direccion
        +string tipo
        +string nivel
        +string descripcion
        +float precioPorNoche
        +date diasDisponibles[]
        +agregarAlojamiento(...)
        +verResenas(id_alojamiento)
    }

    class Reserva {
        +int idReserva
        +datetime fechaInicio
        +datetime fechaFin
        +float costoTotal
        +string estadoDeReserva  // Pendiente, Confirmada, Cancelada
        +string descripcion
        +string destino
        +crearReserva(...)
        +cancelarReserva(idReserva)
        +verDetalleDeReserva(idReserva)
        +cambiarDestino(idReserva, destino)
    }

    class Pago {
        +int idPago
        +float monto
        +datetime fechaDePago
        +string estadoDePago  // Aprobado, Rechazado, Pendiente
        +procesarPago(pagoID)
        +confirmar(pagoID)
    }

    class CodigoDescuento {
        +int idCodigoDeDescuento
        +string codigo
        +string descripcion
        +float montoDeDescuento
        +float porcentajeDeDescuento
        +datetime fechaDeExpiracion
        +boolean estaDisponible
        +aplicarCodigoDeDescuento(idCodigoDeDescuento)
    }

    class Resena {
        +int idResena
        +int puntaje
        +string comentario
        +Date fecha
        +int autorID
        +int targetID  // puede ser Host o Alojamiento
        +subirResena(id_resena)
        +eliminarResena(id_resena)
    }

    %% Herencia
    Admin --|> Usuario
    Anfitrion --|> Usuario
    Viajero --|> Usuario

    %% Relaciones
    Anfitrion "1" --> "0..*" Alojamiento : gestiona
    Viajero "1" --> "0..*" Reserva : realiza
    Reserva "1" --> "1" Pago : tiene
    Reserva "0..*" --> "0..1" CodigoDescuento : aplica
    Alojamiento "0..*" --> "0..*" Resena : tiene
    Viajero "1" --> "0..*" Resena : escribe/recibe
````