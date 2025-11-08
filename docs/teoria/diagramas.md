## Justificación del uso de Mermaid para el diagrama de clases

### ¿Qué es Mermaid?

**Mermaid** es un lenguaje de marcado basado en texto que permite crear diagramas de manera rápida, sencilla y legible.  
Está diseñado para integrarse fácilmente en entornos de documentación como **Markdown**, **GitHub**, **Notion**, **Obsidian** o **VS Code**, entre otros.  
Permite generar diagramas como **diagramas de clases UML**, **diagramas de flujo**, **diagramas de secuencia**, **gantt**, entre otros.

---

### Razones para utilizar Mermaid

1. **Simplicidad y legibilidad**  
   Mermaid permite crear diagramas utilizando texto plano, sin necesidad de herramientas gráficas complejas.  
   Esto facilita mantener y modificar el diagrama directamente dentro del código o la documentación del proyecto.

2. **Integración con herramientas modernas**  
   Al ser compatible con Markdown, los diagramas pueden visualizarse directamente en plataformas como **GitHub**, **IntelliJ** o **Visual Studio Code**, sin necesidad de software adicional.

3. **Control de versiones**  
   Al estar escrito en texto, el diagrama puede guardarse en un repositorio de control de versiones (**Git**), lo que permite rastrear cambios y colaborar fácilmente con otros desarrolladores.

4. **Automatización y documentación técnica**  
   Mermaid ayuda a mantener la documentación técnica actualizada dentro del mismo entorno de desarrollo, haciendo más eficiente el proceso de documentación del sistema.

5. **Representación clara de relaciones UML**  
   Permite expresar conceptos fundamentales del diseño orientado a objetos (herencia, asociación, agregación, composición) de manera visual y estandarizada, ideal para representar estructuras como las del **Sistema de Reservas (Airbnb)**.

---

### Aplicación al proyecto

En este proyecto, se utiliza Mermaid para representar el **Diagrama de Clases** del sistema, mostrando:

- Las clases principales del sistema (**Usuario**, **Alojamiento**, **Reserva**, **Pago**, etc.).
- Las relaciones entre ellas (herencia, asociaciones y multiplicidades).
- Los atributos y métodos que definen el comportamiento de cada entidad.

Esto facilita comprender la estructura del sistema y sirve como guía para la posterior implementación en código.

---