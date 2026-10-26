# BoardLog

BoardLog es una aplicación Android para registrar partidas de juegos de mesa. Permite consultar juegos y jugadores, crear partidas, llevar puntuaciones, ver resultados, historial y estadísticas. En esta entrega se utilizan datos de ejemplo, navegación y arquitectura MVI.

## Servicios

- Room (base de datos local): guarda jugadores, partidas y puntuaciones. Incluye datos de ejemplo.
- API de Wikidata (internet): consulta información adicional de los juegos.
- Catálogo local: contiene los juegos de ejemplo para utilizar la aplicación sin conexión.

## Librerías

- Jetpack Compose y Material 3: crean las pantallas.
- Navigation Compose y Kotlin Serialization: permiten navegar con destinos @Serializable.
- ViewModel, StateFlow y Coroutines: manejan el estado y la lógica con MVI.
- Room y KSP: permiten almacenar datos localmente.
- Material Icons Extended: proporciona íconos para la interfaz.

