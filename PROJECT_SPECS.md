# 📋 Especificaciones del Proyecto - Biblioteca Municipal

## 🎯 Objetivo
Desarrollar una aplicación Android moderna para buscar y explorar libros usando la API de Google Books, con una experiencia de usuario pulida y arquitectura escalable.

## 📱 Plataforma
- **Sistema**: Android (API 24+)
- **Lenguaje**: Kotlin
- **UI**: Jetpack Compose
- **Arquitectura**: Clean Architecture + MVVM

## 🏗️ Arquitectura Técnica

### Capas
1. **Presentation Layer**
   - UI Components (Jetpack Compose)
   - ViewModels (MVVM)
   - Navigation

2. **Domain Layer**
   - Business Models
   - Use Cases
   - Repository Interfaces

3. **Data Layer**
   - Repository Implementations
   - Remote Data Sources (API)
   - Local Data Sources (Room - futuro)

### Patrones Implementados
- **Repository Pattern**: Abstracción de fuentes de datos
- **Dependency Injection**: Hilt para gestión de dependencias
- **Observer Pattern**: StateFlow para reactividad
- **Factory Pattern**: Para creación de objetos complejos

## 🎨 Diseño UI/UX

### Principios
- **Material 3**: Sistema de diseño moderno
- **Consistencia**: Colores, tipografía y espaciado uniformes
- **Accesibilidad**: Contraste adecuado y navegación por teclado
- **Responsividad**: Adaptable a diferentes tamaños de pantalla

### Componentes Clave
- **BookListItem**: Tarjeta de libro con portada rectangular (70dp × 100dp)
- **SearchBar**: Búsqueda con selector de tipo
- **LoadingIndicator**: Feedback visual durante carga
- **EmptyState**: Estados vacíos informativos

## 🔧 Configuración Técnica

### Dependencias Principales
```kotlin
// UI
implementation("androidx.compose.ui:ui:$compose_version")
implementation("androidx.compose.material3:material3:$material3_version")

// Arquitectura
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
implementation("androidx.hilt:hilt-navigation-compose:$hilt_navigation_version")

// Red
implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
implementation("com.squareup.okhttp3:okhttp:$okhttp_version")

// Imágenes
implementation("io.coil-kt:coil-compose:$coil_version")

// DI
implementation("com.google.dagger:hilt-android:$hilt_version")
```

### Configuración de Red
- **Base URL**: `https://www.googleapis.com/books/v1/`
- **Timeout**: 30 segundos
- **Headers**: User-Agent y Referer para Google Books
- **HTTPS**: Normalización automática de URLs HTTP

## 📊 Métricas de Calidad

### Código
- **Linting**: ktlint + detekt
- **Testing**: JUnit + MockK (preparado)
- **Coverage**: Objetivo 80%+ en capa Domain

### Performance
- **Tiempo de carga**: < 3 segundos para búsquedas
- **Memoria**: Optimización con LazyColumn
- **Red**: Debounce de 500ms en búsquedas

## 🚀 Roadmap

### Fase Actual (v1.1.x)
- ✅ Búsqueda básica funcional
- ✅ Portadas con proporción correcta
- ✅ UX optimizada

### Fase 2 (v1.2.x)
- [ ] Pantalla de detalles del libro
- [ ] Favoritos y lista de lectura
- [ ] Mejoras en filtros

### Fase 3 (v2.0.x)
- [ ] Base de datos local (Room)
- [ ] Modo offline
- [ ] Sincronización de datos

## 🔒 Seguridad

### API Keys
- Almacenadas en `local.properties` (no versionadas)
- Validación de formato en tiempo de compilación
- Rotación automática recomendada

### Red
- Solo HTTPS permitido
- Validación de certificados SSL
- Headers de seguridad configurados

## 📈 Monitoreo

### Logs
- **Debug**: Información detallada de carga de imágenes
- **Error**: Errores de red y API
- **Performance**: Tiempos de respuesta

### Métricas
- Tiempo de carga de imágenes
- Tasa de éxito de búsquedas
- Errores de red

---

**Desarrollador**: Giorgio Interdonato Palacios  
**GitHub**: [@DonGeeo87](https://github.com/DonGeeo87)  
**Fecha**: Enero 2025
