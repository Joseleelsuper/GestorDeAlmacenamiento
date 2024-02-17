# Instalación del proyecto de Android Studio desde GitHub

## Clonar el repositorio
- Abre Android Studio.
- Haz clic en "Obtener de Control de Versiones" o ve a "File" > "New" > "Poyect from Version Control".
- Ingresa la URL del repositorio de GitHub ó inicia sesión con GitHub.
- Elige un directorio para guardar el proyecto (A ser posible, guárdalo en una carpeta vacía llamada `GestorDeAlmacenamiento`).
- Haz clic en "Clone" o "Aceptar".

## Importar el proyecto
- Una vez que se complete el proceso de clonación, Android Studio te pedirá que abras el proyecto.
- Haz clic en "Abrir" o ve a "File" > "Open".
- Navega hasta el directorio donde clonaste el repositorio y selecciona la carpeta del proyecto.
- Haz clic en "Aceptar" para importar el proyecto.

## Configurar dependencias y SDK
- Android Studio sincronizará automáticamente el proyecto y descargará las dependencias necesarias.
- Asegúrate de tener instaladas las versiones requeridas del SDK. Si no, Android Studio te pedirá que las descargues.

## Construir y ejecutar el proyecto
- Después de que se importe y sincronice el proyecto, puedes construirlo y ejecutarlo haciendo clic en el botón "Run 'app'" o presionando Ctrl+F5.

## Realizar un commit y push a GitHub (Sincronizar los cambios en Github)
- Haz cambios en tu proyecto de Android Studio.
- Una vez que estés listo para guardar tus cambios, ve a "Git" > "Commit".
- En la ventana de commit, selecciona los archivos que deseas incluir en el commit.
- Escribe un mensaje de commit que describa los cambios que has realizado.
- Haz clic en "Commit" o "Commit and Push" si deseas subir los cambios a GitHub inmediatamente.
- Si solo hiciste un commit, puedes hacer push de tus cambios a GitHub yendo a "Git" > "Push".
- En la ventana de push, selecciona las ramas que deseas empujar y haz clic en "Push".

## Otros datos importantes
- A la hora de descargar los [iconos](https://fonts.google.com/icons?icon.platform=android) que utilizaremos para terminar nuestra aplicación,
deben ser guardados en el directorio `.\app\src\main\res\drawable`. Para descargar un icono, debes dar click sobre él, ir al apartado **Web**
y por último descargar como **PNG**. **NO** utilizaremos el apartado **Android** porque no nos merece la pena.