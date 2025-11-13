import model.pago.Pago;
import model.reserva.EstadoReserva;
import model.usuario.*;
import model.alojamiento.*;
import model.reserva.Reserva;
import model.resenia.Resenia;
import model.gestor.*;
import model.descuento.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.IOException;
import java.nio.file.*;

public class Main {

    private static GestorUsuario gestorUsuario;
    private static GestorAlojamiento gestorAlojamiento;
    private static GestorCodigoDescuento gestorCodigoDescuento;
    private static GestorPago gestorPago;
    private static GestorReserva gestorReserva;
    private static GestorResenia gestorResenia;

    private static Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        inicializarBaseDeDatos();
        inicializarSistema();
        flujoPrincipal();
        System.out.println("\n Gracias por usar el sistema de gestión de alojamientos. ¡Hasta pronto!");
    }


    private static void inicializarSistema() {
        System.out.println("=== Iniciando Sistema de Gestión de Alojamientos ===");
        gestorUsuario = new GestorUsuario();
        gestorAlojamiento = new GestorAlojamiento(gestorUsuario);
        gestorCodigoDescuento = new GestorCodigoDescuento();
        gestorReserva = new GestorReserva(gestorAlojamiento, gestorUsuario, gestorCodigoDescuento, null);
        gestorPago = new GestorPago(gestorReserva);
        gestorReserva.setGestorPago(gestorPago);
        gestorResenia = new GestorResenia(gestorUsuario, gestorAlojamiento);
        System.out.println("Sistema inicializado correctamente.\n");
    }


    private static void flujoPrincipal() {
        boolean salir = false;
        while (!salir) {
            if (usuarioActual == null) {
                System.out.println("\n=== Menú Principal ===");
                System.out.println("1. Registrarse");
                System.out.println("2. Iniciar sesión");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                String opcion = scanner.nextLine();

                switch (opcion) {
                    case "1":
                        registrarUsuario();
                        break;
                    case "2":
                        iniciarSesion();
                        break;
                    case "0":
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } else {
                mostrarMenuPorRol(usuarioActual);
            }
        }
    }


    private static void registrarUsuario() {
        System.out.println("\n=== Registro de usuario ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.println("Tipo de usuario (1=Admin, 2=Anfitrión, 3=Viajero): ");
        int tipo = Integer.parseInt(scanner.nextLine());
        TipoUsuario tipoUsuario = tipo == 1 ? TipoUsuario.ADMINISTRADOR :
                tipo == 2 ? TipoUsuario.ANFITRION :
                        TipoUsuario.VIAJERO;

        gestorUsuario.registrar(nombre, email, contrasena, telefono, tipoUsuario);
        System.out.println(" Usuario registrado correctamente.");
    }

    private static void iniciarSesion() {
        System.out.println("\n=== Inicio de sesión ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        try {
            usuarioActual = gestorUsuario.iniciarSesion(email, contrasena);
            System.out.println(" Sesión iniciada correctamente. Bienvenido, " + usuarioActual.getNombre() + " (" + usuarioActual.getTipoUsuario() + ")");
        } catch (Exception e) {
            System.err.println(" " + e.getMessage());
        }
    }

    private static void cerrarSesion() {
        if (usuarioActual != null) {
            gestorUsuario.cerrarSesion(usuarioActual);
            System.out.println(" Sesión cerrada de " + usuarioActual.getNombre());
            usuarioActual = null;
        }
    }


    private static void mostrarMenuPorRol(Usuario usuario) {
        switch (usuario.getTipoUsuario()) {
            case ADMINISTRADOR:
                menuAdministrador((Administrador) usuario);
                break;
            case ANFITRION:
                menuAnfitrion((Anfitrion) usuario);
                break;
            case VIAJERO:
                menuViajero((Viajero) usuario);
                break;
        }
    }


    private static void menuAdministrador(Administrador admin) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Administrador ===");
            System.out.println("1. Crear código de descuento");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Ver pagos");
            System.out.println("4. Actualizar mi perfil");
            System.out.println("5. Administrar pagos (aprobar / rechazar)");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1": crearCodigoDescuento(admin); break;
                case "2": listarUsuarios(admin); break;
                case "3": verPagos(); break;
                case "4": actualizarPerfilUsuario(); break;
                case "5": administrarPagos(); break;
                case "0": cerrarSesion(); salir = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }


    private static void menuAnfitrion(Anfitrion anfitrion) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Anfitrión ===");
            System.out.println("1. Crear alojamiento");
            System.out.println("2. Listar mis alojamientos");
            System.out.println("3. Actualizar mi perfil");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1": crearAlojamiento(anfitrion); break;
                case "2": listarAlojamientos(); break;
                case "3": actualizarPerfilUsuario(); break;
                case "0": cerrarSesion(); salir = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }


    private static void menuViajero(Viajero viajero) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Viajero ===");
            System.out.println("1. Buscar alojamientos");
            System.out.println("2. Crear reserva");
            System.out.println("3. Cancelar reserva");
            System.out.println("4. Enviar reseña");
            System.out.println("5. Ver reseñas");
            System.out.println("6. Actualizar mi perfil");
            System.out.println("7. Ver detalle de una reserva");
            System.out.println("8. Cambiar destino de reserva");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1": buscarAlojamientos(); break;
                case "2": crearReserva(viajero); break;
                case "3": cancelarReserva(viajero); break;
                case "4": crearResenia(viajero); break;
                case "5": verResenias(); break;
                case "6": actualizarPerfilUsuario(); break;
                case "7": verDetalleReserva(); break;
                case "8": cambiarDestinoReserva(viajero); break;
                case "0": cerrarSesion(); salir = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void actualizarPerfilUsuario() {
        System.out.println("\n=== Actualizar perfil ===");

        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = scanner.nextLine();
        if (nombre.isBlank()) nombre = usuarioActual.getNombre();

        System.out.print("Nuevo email (Enter para mantener): ");
        String email = scanner.nextLine();
        if (email.isBlank()) email = usuarioActual.getEmail();

        System.out.print("Nueva contraseña (Enter para mantener): ");
        String contrasena = scanner.nextLine();
        if (contrasena.isBlank()) contrasena = usuarioActual.getContrasena();

        System.out.print("Nuevo teléfono (Enter para mantener): ");
        String telefono = scanner.nextLine();
        if (telefono.isBlank()) telefono = usuarioActual.getTelefono();

        usuarioActual.actualizarPerfil(nombre, email, contrasena, telefono);
        gestorUsuario.guardarEnArchivo();

        System.out.println(" Perfil actualizado correctamente.");
    }

    private static void crearCodigoDescuento(Administrador admin) {
        System.out.println("\n=== Crear código de descuento ===");
        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        System.out.print("Tipo (1=Fijo, 2=Porcentaje): ");
        int tipoNum = Integer.parseInt(scanner.nextLine());
        TipoCodigoDescuento tipo = tipoNum == 1 ? TipoCodigoDescuento.FIJO : TipoCodigoDescuento.PORCENTAJE;
        System.out.print("Monto: ");
        double monto = Double.parseDouble(scanner.nextLine());

        gestorCodigoDescuento.crearCodigo(admin, codigo, tipo, monto, LocalDateTime.now().plusDays(30));
        System.out.println(" Código creado con éxito.");
    }

    private static void listarUsuarios(Administrador admin) {
        System.out.println("\n=== Lista de usuarios ===");
        List<Usuario> lista = gestorUsuario.listarUsuarios(admin);
        for (int i = 0; i < lista.size(); i++) {
            Usuario u = lista.get(i);
            System.out.println("- " + u.getNombre() + " (" + u.getTipoUsuario() + ")");
        }
    }

    private static void crearAlojamiento(Anfitrion anfitrion) {
        System.out.println("\n=== Crear alojamiento ===");
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Tipo (1=Casa, 2=Departamento, 3=Habitación): ");
        int tipoNum = Integer.parseInt(scanner.nextLine());
        TipoAlojamiento tipo = tipoNum == 1 ? TipoAlojamiento.CASA :
                tipoNum == 2 ? TipoAlojamiento.DEPARTAMENTO :
                        TipoAlojamiento.HABITACION;
        System.out.print("Nivel: ");
        String nivel = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Precio por noche: ");
        double precio = Double.parseDouble(scanner.nextLine());

        System.out.println("Ingrese fechas disponibles (AAAA-MM-DD), una por línea. Escriba FIN para terminar:");
        List<LocalDate> disponibles = new ArrayList<>();
        while (true) {
            String linea = scanner.nextLine();
            if (linea.equalsIgnoreCase("FIN")) break;
            disponibles.add(LocalDate.parse(linea));
        }

        Alojamiento alojamiento = new Alojamiento(
                gestorAlojamiento.listar().size() + 1,
                direccion,
                tipo,
                nivel,
                descripcion,
                precio,
                disponibles,
                anfitrion
        );

        gestorAlojamiento.agregarAlojamiento(anfitrion, alojamiento);
        System.out.println(" Alojamiento registrado correctamente.");
    }

    private static void listarAlojamientos() {
        System.out.println("\n=== Lista de alojamientos ===");
        List<Alojamiento> lista = gestorAlojamiento.listar();
        if (lista.isEmpty()) System.out.println("No hay alojamientos.");
        else for (int i = 0; i < lista.size(); i++) {
            Alojamiento a = lista.get(i);
            System.out.println((i+1) + ". " + a.getDireccion() + " - " + a.getTipo() + " - $" + a.getPrecioPorNoche());
        }
    }

    private static void crearReserva(Viajero viajero) {
        System.out.println("\n=== Crear reserva ===");
        listarAlojamientos();

        System.out.print("Seleccione número de alojamiento: ");
        int num = Integer.parseInt(scanner.nextLine());
        List<Alojamiento> alojamientos = gestorAlojamiento.listar();
        if (num < 1 || num > alojamientos.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Alojamiento alojamiento = alojamientos.get(num - 1);
        System.out.print("Código de descuento (Enter si no aplica): ");
        String codigo = scanner.nextLine();

        Reserva reserva = gestorReserva.crearReserva(viajero, alojamiento, LocalDate.now(), LocalDate.now().plusDays(3), codigo);
        System.out.println(" Reserva creada. Total: $" + reserva.getCostoTotal());
    }

    private static void cancelarReserva(Viajero viajero) {
        System.out.println("\n=== Cancelar reserva ===");
        List<Reserva> reservas = gestorReserva.listar();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        for (int i = 0; i < reservas.size(); i++) {
            System.out.println((i+1) + ". " + reservas.get(i).verDetalle());
        }

        System.out.print("Seleccione reserva a cancelar: ");
        int num = Integer.parseInt(scanner.nextLine());
        if (num < 1 || num > reservas.size()) {
            System.out.println("Opción inválida.");
            return;
        }
        gestorReserva.cancelarReserva(viajero, reservas.get(num - 1));
        System.out.println("Reserva cancelada.");
    }

    private static void crearResenia(Viajero viajero) {
        System.out.println("\n=== Crear reseña ===");
        listarAlojamientos();
        System.out.print("Seleccione alojamiento: ");
        int num = Integer.parseInt(scanner.nextLine());
        List<Alojamiento> alojamientos = gestorAlojamiento.listar();
        if (num < 1 || num > alojamientos.size()) return;

        Alojamiento alojamiento = alojamientos.get(num - 1);
        System.out.print("Comentario: ");
        String comentario = scanner.nextLine();
        System.out.print("Puntaje (1-5): ");
        int puntaje = Integer.parseInt(scanner.nextLine());

        gestorResenia.crearReseniaAlojamiento(viajero, alojamiento, puntaje, comentario);
        System.out.println(" Reseña enviada.");
    }

    private static void verPagos() {
        System.out.println("\n=== Pagos registrados ===");
        List pagos = gestorPago.listar();
        if (pagos.isEmpty()) System.out.println("No hay pagos.");
        else for (int i = 0; i < pagos.size(); i++) System.out.println(pagos.get(i));
    }

    private static void verResenias() {
        System.out.println("\n=== Reseñas ===");
        List<Resenia> todas = gestorResenia.listar();
        if (todas.isEmpty()) System.out.println("No hay reseñas.");
        else for (int i = 0; i < todas.size(); i++) {
            System.out.println(todas.get(i).verDetalle());
            System.out.println("----------------------------------");
        }
    }

    private static void inicializarBaseDeDatos() {
        System.out.println("=== Inicializando base de datos desde /sample_data ===");

        Path sampleDataDir = Paths.get("sample_data");
        Path dbDir = Paths.get("db");

        try {
            if (!Files.exists(dbDir)) {
                Files.createDirectory(dbDir);
                System.out.println(" Carpeta /db creada.");
            }
        } catch (IOException e) {
            System.err.println(" Error al crear carpeta /db: " + e.getMessage());
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sampleDataDir, "*.json")) {
            for (Path archivo : stream) {
                Path destino = dbDir.resolve(archivo.getFileName());
                if (!Files.exists(destino)) {
                    Files.copy(archivo, destino, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println(" Copiado: " + archivo.getFileName());
                } else {
                    System.out.println(" Ya existe: " + archivo.getFileName() + " (no se sobrescribe)");
                }
            }
        } catch (IOException e) {
            System.err.println(" Error al copiar archivos desde sample_data: " + e.getMessage());
        }

        System.out.println("Base de datos inicializada.\n");
    }

    private static void buscarAlojamientos() {
        System.out.println("\n=== Búsqueda de alojamientos ===");
        System.out.println("1. Filtrar por tipo");
        System.out.println("2. Filtrar por precio máximo");
        System.out.println("3. Filtrar por fecha disponible");
        System.out.print("Opción: ");
        String opcion = scanner.nextLine();

        List<Alojamiento> filtrados = null;

        switch (opcion) {
            case "1":
                System.out.print("Tipo (1=Casa, 2=Departamento, 3=Habitación): ");
                int t = Integer.parseInt(scanner.nextLine());
                TipoAlojamiento tipo = t == 1 ? TipoAlojamiento.CASA :
                        t == 2 ? TipoAlojamiento.DEPARTAMENTO :
                                TipoAlojamiento.HABITACION;
                filtrados = gestorAlojamiento.filtrarPorTipo(tipo);
                break;
            case "2":
                System.out.print("Precio máximo: ");
                double precio = Double.parseDouble(scanner.nextLine());
                filtrados = gestorAlojamiento.filtrarPorPrecio(precio);
                break;
            case "3":
                System.out.print("Fecha (AAAA-MM-DD): ");
                LocalDate fecha = LocalDate.parse(scanner.nextLine());
                filtrados = gestorAlojamiento.filtrarPorDisponibilidad(fecha);
                break;
        }

        if (filtrados == null || filtrados.isEmpty()) {
            System.out.println("No se encontraron resultados.");
        } else {
            for (int i = 0; i < filtrados.size(); i++) {
                System.out.println(filtrados.get(i));
            }
        }
    }

    private static void verDetalleReserva() {
        List<Reserva> reservas = gestorReserva.listar();

        for (int i = 0; i < reservas.size(); i++)
            System.out.println((i+1) + ". " + reservas.get(i).getId());

        System.out.print("Seleccione: ");
        int n = Integer.parseInt(scanner.nextLine());

        System.out.println(reservas.get(n-1).verDetalle());
    }

    private static void cambiarDestinoReserva(Viajero viajero) {
        System.out.println("\n=== Cambiar destino de reserva ===");

        List<Reserva> reservas = gestorReserva.listar();

        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getViajero().getId() == viajero.getId() &&
                    r.getEstado() == EstadoReserva.PENDIENTE) {
                System.out.println(r);
            }
        }

        System.out.print("Seleccione ID de reserva: ");
        int id = Integer.parseInt(scanner.nextLine());
        Reserva r = gestorReserva.buscarPorId(id);

        if (r == null || r.getEstado() != EstadoReserva.PENDIENTE) {
            System.out.println("No puede modificarse.");
            return;
        }

        listarAlojamientos();

        System.out.print("Nuevo alojamiento (número): ");
        int num = Integer.parseInt(scanner.nextLine());
        List<Alojamiento> alojamientos = gestorAlojamiento.listar();

        if (num < 1 || num > alojamientos.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Alojamiento nuevo = alojamientos.get(num - 1);

        r.setAlojamiento(nuevo);
        gestorReserva.guardarEnArchivo();
        System.out.println("Destino actualizado.");
    }

    private static void administrarPagos() {
        List<Pago> pagos = gestorPago.listar();

        for (int i = 0; i < pagos.size(); i++)
            System.out.println((i+1) + ". " + pagos.get(i));

        System.out.print("Seleccione pago: ");
        int n = Integer.parseInt(scanner.nextLine());
        Pago p = pagos.get(n - 1);

        System.out.println("1. Aprobar\n2. Rechazar");
        String op = scanner.nextLine();

        if (op.equals("1")) gestorPago.aprobar(p);
        else gestorPago.rechazar(p);

        gestorPago.guardarEnArchivo();
        System.out.println("Pago actualizado.");
    }

}