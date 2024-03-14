# UserApp - Prueba Técnica

## Arquitectura

Módulos:
- **Domain:**
- **Data:**
- **App:**

## Puntos a tener en cuenta

- Se ha ahorrado el uso de UseCases para simplificar la cantidad de componentes innecesarios
- No se usa un módulo específico para BuildSrc con conventions como plugins
- Se ha utilizado Koin como DI
- Se ha utilizado Ktor como librería para requests HTTP
- La paginación se resuelve de forma manual, sin necesidad de usar Paging3
- Se ha simplificado la navegación usado el bundle por ahorrar tiempo
- Se utiliza como ejemplo la deserialización manual y automática
- Se ha utilizado un fake map en vez de utilizar la librería compose oficial de Maps.

## Mejoras Pendientes y Posibles

Aquí puedes listar las cosas que faltan o que podrían mejorarse en tu proyecto. Por ejemplo:

- Utilización de base de datos local como origen único de la información para simplificar el flujo y la navegación y añadir persistencia, a utilizar SQLDelight
- Implementación de tests unitarios para data y test e2e visuales (UI Tests) con Maestro
- Simplificación de varios composables, componente de búsqueda extraido junto con un viewmodel exclusivo para la búsqueda