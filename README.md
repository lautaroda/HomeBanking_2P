# Sistema de Gestión Bancaria

Este proyecto es un sistema de gestión bancaria que permite a los usuarios administrar cuentas bancarias, tarjetas y realizar transacciones financieras.

## Características

- Gestión de cuentas de usuarios.
- Operaciones CRUD para cuentas bancarias y tarjetas de crédito.
- Realización de transferencias entre cuentas.
- Panel de administración para la gestión de usuarios y auditoría de transacciones.

## Tecnologías Utilizadas

- Java: Lenguaje de programación principal.
- Swing: Para la interfaz gráfica de usuario.
- JDBC: Para la conexión y operaciones con la base de datos.

## Instalación y Ejecución

Para ejecutar este proyecto, necesitarás Java instalado en tu sistema. Sigue estos pasos:

1. Clona el repositorio a tu máquina local.
2. Abre una terminal y navega hasta la carpeta del proyecto.
3. Ejecuta `javac Main.java` para compilar el proyecto.
4. Ejecuta `java Main` para iniciar la aplicación.

## Estructura del Proyecto

El proyecto sigue la siguiente estructura de paquetes:

- `main`: Contiene la clase principal para ejecutar la aplicación.
- `main.views`: Contiene todas las interfaces gráficas de usuario.
- `main.modelo`: Define las entidades y la lógica de negocio.
- `main.dao`: Capa de acceso a datos para interactuar con la base de datos.
- `main.service`: Capa de servicios para manejar la lógica de aplicación.

## Contribuciones

Las contribuciones son bienvenidas. Para contribuir al proyecto, por favor asegúrate de leer el archivo `CONTRIBUTING.md` donde encontrarás detalles sobre el código de conducta y los procesos para enviar pull requests y reportar issues.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Esto significa que tienes amplia libertad para distribuir, modificar, incluir en proyectos privados o comerciales, siempre que incluyas el aviso de licencia original y el aviso de derechos de autor con cualquier copia sustancial del software. No se otorga garantía alguna con respecto al software, que se proporciona "tal cual". Para más información, consulta el archivo `LICENSE.md` incluido con el código fuente.
