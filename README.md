# 📚 Biblioteca Municipal

Una aplicación Android moderna para buscar y explorar libros usando la API de Google Books.

## ✨ Características

- **🔍 Búsqueda avanzada**: Busca por título, autor o ambos
- **📱 Diseño moderno**: Interfaz Material 3 con Jetpack Compose
- **🖼️ Portadas de libros**: Imágenes de alta calidad con proporción rectangular
- **⚡ Carga rápida**: Indicadores de progreso y experiencia optimizada
- **🎨 UX pulida**: Teclado automático, animaciones suaves y feedback visual

## 🏗️ Arquitectura

- **Clean Architecture** con separación de capas
- **MVVM** con ViewModel y StateFlow
- **Hilt** para inyección de dependencias
- **Jetpack Compose** para UI moderna
- **Retrofit** para comunicación con APIs
- **Coil** para carga de imágenes

## 📦 Stack Tecnológico

- **Kotlin** - Lenguaje principal
- **Jetpack Compose** - UI declarativa
- **Hilt** - Inyección de dependencias
- **Retrofit + OkHttp** - Cliente HTTP
- **Coil** - Carga de imágenes
- **Room** - Base de datos local (preparado)
- **Navigation Compose** - Navegación
- **Material 3** - Sistema de diseño

## 🚀 Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/DonGeeo87/BibliotecaMunicipal.git
cd BibliotecaMunicipal
```

2. Abre el proyecto en Android Studio

3. Configura tu API Key de Google Books:
   - Obtén una API Key en [Google Cloud Console](https://console.cloud.google.com/)
   - Agrega la key en `local.properties`:
   ```properties
   GOOGLE_BOOKS_API_KEY=tu_api_key_aqui
   ```

4. Compila e instala:
```bash
./gradlew installDebug
```

## 📱 Capturas de Pantalla

### Pantalla Principal
- Barra de búsqueda con selector de tipo
- Lista de resultados con portadas rectangulares
- Indicadores de disponibilidad

### Características de UX
- LoadingIndicator mejorado con mensajes informativos
- Teclado se oculta automáticamente al buscar
- Animaciones suaves y transiciones

## 🔧 Configuración

### API Key de Google Books
1. Ve a [Google Cloud Console](https://console.cloud.google.com/)
2. Crea un nuevo proyecto o selecciona uno existente
3. Habilita la Google Books API
4. Crea credenciales (API Key)
5. Agrega la key en `local.properties`

### Permisos
La aplicación requiere:
- `INTERNET` - Para acceder a la API de Google Books

## 📁 Estructura del Proyecto

```
app/src/main/java/dev/dongeeo/bibliotecamunicipal/
├── data/                    # Capa de datos
│   ├── remote/             # API y DTOs
│   ├── repository/         # Implementación de repositorios
│   └── local/              # Base de datos local (futuro)
├── domain/                 # Capa de dominio
│   ├── model/             # Modelos de negocio
│   ├── repository/        # Interfaces de repositorios
│   └── usecase/           # Casos de uso
├── presentation/          # Capa de presentación
│   ├── components/        # Componentes reutilizables
│   ├── search/            # Pantalla de búsqueda
│   └── theme/             # Temas y estilos
└── di/                    # Módulos de Hilt
```

## 🎯 Funcionalidades Implementadas

### ✅ Completadas
- [x] Búsqueda de libros por título, autor o ambos
- [x] Carga de portadas con proporción rectangular
- [x] Normalización HTTPS para compatibilidad Android
- [x] LoadingIndicator mejorado
- [x] Ocultar teclado automáticamente
- [x] Diseño Material 3
- [x] Arquitectura Clean + MVVM
- [x] Inyección de dependencias con Hilt

### 🔄 En Desarrollo
- [ ] Pantalla de detalles del libro
- [ ] Favoritos y lista de lectura
- [ ] Base de datos local con Room
- [ ] Modo offline
- [ ] Filtros avanzados

## 🐛 Solución de Problemas

### Las imágenes no se cargan
- Verifica que tengas conexión a internet
- Confirma que tu API Key sea válida
- Las URLs se normalizan automáticamente a HTTPS

### La búsqueda es lenta
- Es normal en la primera carga
- Los resultados se cachean para búsquedas posteriores
- El LoadingIndicator muestra el progreso

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver `LICENSE` para más detalles.

## 👨‍💻 Desarrollador

**Giorgio Interdonato Palacios**  
GitHub: [@DonGeeo87](https://github.com/DonGeeo87)

---

## 📋 Changelog

### v1.1.1 (2025-10-16)
- ✅ Portadas con proporción rectangular (70dp × 100dp)
- ✅ ContentScale.Fit para mantener proporción original
- ✅ Mejor representación visual de libros

### v1.1.0 (2025-10-16)
- ✅ Imágenes de Google Books funcionando
- ✅ URLs normalizadas a HTTPS
- ✅ LoadingIndicator mejorado
- ✅ Teclado automático al buscar
- ✅ UX optimizada

### v1.0.0 (2025-10-16)
- 🎉 Release inicial
- ✅ Búsqueda básica de libros
- ✅ Arquitectura Clean + MVVM
- ✅ Material 3 Design
