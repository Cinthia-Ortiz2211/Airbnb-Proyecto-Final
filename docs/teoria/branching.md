# Estrategia de Branching: Trunk-Based Development

## Descripción general
En este proyecto se adopta **Trunk-Based Development (TBD)** como estrategia de branching.  
Esto significa que todo el equipo trabaja principalmente sobre una única rama estable (`master`), creando ramas cortas y de vida breve para cada cambio o mejora específica.

## Motivación
Elegimos este enfoque porque:
- **Facilita la integración continua:** los cambios se integran de forma frecuente, reduciendo conflictos y evitando divergencias largas.
- **Acelera el ciclo de desarrollo:** cada feature o fix se desarrolla en una rama corta (1–2 días) y se mergea rápidamente.
- **Mantiene la rama principal siempre estable:** gracias a las revisiones rápidas, `master` está lista para desplegar en cualquier momento.
- **Evita la sobrecarga de ramas largas o complejas:** no se utilizan ramas como `develop`, `release` o `hotfix` permanentes; todo fluye hacia `master`.

## Convenciones de ramas
Cada rama temporal debe usar un prefijo descriptivo según el tipo de cambio:

| Tipo de cambio | Prefijo de rama | Ejemplo                             |
|----------------|-----------------|-------------------------------------|
| Nueva funcionalidad | `feature/` | `feature/agregar-modelo-de-usuario` |
| Corrección de error | `fix/` | `fix/validar-inicio-de-sesion`      |
| Documentación | `docs/` | `docs/agregar-diagrama-uml`         |
| Tareas de mantenimiento | `chore/` | `chore/actualizar-dependencias`     |

## Flujo de trabajo resumido
1. Crear rama desde `master`:
   ```bash
   git checkout -b feature/add-login-endpoint
   ```
2. Implementar y commitear cambios.
3. Rebase con main antes de subir:
    ```bash
    git fetch origin main
    git rebase origin/main
    ```
3. Subir la rama y abrir un PR pequeño.
4. Revisar, mergear y eliminar la rama.