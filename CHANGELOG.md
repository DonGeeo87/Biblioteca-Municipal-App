# 📋 Changelog

Todos los cambios notables de este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.1] - 2025-01-16

### ✨ Mejorado
- **Portadas rectangulares**: Cambiado de tamaño cuadrado (100dp) a rectangular (70dp × 100dp)
- **Proporción de libros**: ContentScale.Fit para mantener la proporción original sin distorsión
- **Representación visual**: Las portadas ahora se ven como libros reales

### 🔧 Técnico
- Modificado `BookListItem.kt` para usar `width()` y `height()` en lugar de `size()`
- Cambiado `ContentScale.Crop` a `ContentScale.Fit`
- Actualizado placeholders para mantener consistencia visual

## [1.1.0] - 2025-01-16

### ✨ Nuevo
- **Carga de imágenes**: Las portadas de Google Books ahora se cargan correctamente
- **Normalización HTTPS**: URLs HTTP convertidas automáticamente a HTTPS
- **LoadingIndicator mejorado**: Mensajes más informativos y diseño atractivo
- **Teclado automático**: Se oculta automáticamente al presionar buscar
- **Portadas más grandes**: Aumentadas de 80dp a 100dp para mejor visibilidad

### 🔧 Técnico
- Implementada función `normalizeUrl()` en `BookRepositoryImpl`
- Mejorado `LoadingIndicator` con mensajes secundarios
- Agregado `LocalFocusManager` en `SearchBar` para ocultar teclado
- Headers HTTP personalizados en `ImageModule` para mejor compatibilidad

### 🐛 Corregido
- **Imágenes no cargaban**: Problema resuelto con normalización HTTPS
- **UX durante carga**: Mejor feedback visual con indicadores informativos
- **Teclado obstruía resultados**: Ahora se oculta automáticamente

## [1.0.0] - 2025-01-16

### ✨ Lanzamiento inicial
- **Búsqueda de libros**: Funcionalidad completa con Google Books API
- **Tipos de búsqueda**: Por título, autor o ambos
- **Arquitectura Clean**: Separación clara de capas (Data, Domain, Presentation)
- **MVVM**: ViewModel con StateFlow para gestión de estado
- **Hilt**: Inyección de dependencias configurada
- **Material 3**: Diseño moderno con Jetpack Compose
- **Componentes reutilizables**: BookListItem, SearchBar, LoadingIndicator, EmptyState

### 🔧 Técnico
- Configuración inicial de dependencias
- Estructura de proyecto con Clean Architecture
- Integración con Google Books API
- Sistema de temas y estilos Material 3
- Manejo de estados (Loading, Success, Error, Empty)

---

## 📝 Notas de Desarrollo

### Arquitectura
- **Clean Architecture** con separación clara de responsabilidades
- **MVVM** para gestión de estado reactiva
- **Repository Pattern** para abstracción de datos
- **Use Cases** para lógica de negocio

### UX/UI
- **Material 3** como sistema de diseño
- **Jetpack Compose** para UI declarativa
- **Animaciones suaves** y transiciones
- **Feedback visual** en todas las interacciones

### Rendimiento
- **Debounce** en búsquedas para optimizar llamadas API
- **Caché de imágenes** con Coil
- **LazyColumn** para listas eficientes
- **Normalización HTTPS** para mejor compatibilidad

---

**Desarrollador**: Giorgio Interdonato Palacios  
**GitHub**: [@DonGeeo87](https://github.com/DonGeeo87)
