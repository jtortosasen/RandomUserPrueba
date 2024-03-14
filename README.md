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
- Se ha utilizado Ktor como librería para req


uests HTTP
- La paginación se resuelve de forma manual, sin necesidad de usar Paging3
- Se ha simplificado la navegación usado el bundle por ahorrar tiempo
- Se utiliza como ejemplo la deserialización manual y automática
- Se ha utilizado un fake map en vez de utilizar la librería compose oficial de Maps.

## Mejoras Pendientes por falta de tiempo
- Utilización de base de datos local como origen único de la información para simplificar el flujo y la navegación y añadir persistencia, a utilizar SQLDelight
- Implementación de tests unitarios para data y test e2e visuales (UI Tests) con Maestro
- Simplificación de varios composables, componente de búsqueda extraido junto con un viewmodel exclusivo para la búsqueda

https://github.com/jtortosasen/RandomUserPrueba/assets/26440994/37cf0e3f-ae54-4fa8-a6c9-3881fac04a50

