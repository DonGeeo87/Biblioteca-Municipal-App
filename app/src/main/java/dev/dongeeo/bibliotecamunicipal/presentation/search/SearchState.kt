package dev.dongeeo.bibliotecamunicipal.presentation.search

import dev.dongeeo.bibliotecamunicipal.domain.model.Book

/**
 * Estados de la pantalla de búsqueda de libros
 * 
 * Este sealed class representa todos los posibles estados de la UI
 * durante el proceso de búsqueda de libros, permitiendo un manejo
 * declarativo y type-safe del estado.
 */
sealed class SearchState {
    
    /**
     * Estado inicial - Sin búsqueda realizada
     * La pantalla muestra el estado inicial con campo de búsqueda vacío
     */
    object Idle : SearchState()
    
    /**
     * Estado de carga - Búsqueda en progreso
     * Muestra indicador de progreso mientras se ejecuta la búsqueda
     */
    object Loading : SearchState()
    
    /**
     * Estado de éxito - Búsqueda completada exitosamente
     * 
     * @param books Lista de libros encontrados
     * @param query Término de búsqueda utilizado
     * @param searchType Tipo de búsqueda realizado
     */
    data class Success(
        val books: List<Book>,
        val query: String,
        val searchType: SearchType
    ) : SearchState()
    
    /**
     * Estado de error - Búsqueda falló
     * 
     * @param message Mensaje de error para mostrar al usuario
     * @param throwable Excepción original (opcional, para debugging)
     */
    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : SearchState()
    
    /**
     * Estado vacío - Búsqueda completada pero sin resultados
     * 
     * @param query Término de búsqueda que no produjo resultados
     * @param message Mensaje explicativo para el usuario
     */
    data class Empty(
        val query: String,
        val message: String = "No se encontraron libros para: $query"
    ) : SearchState()
}

/**
 * Enum para tipos de búsqueda disponibles en la UI
 */
enum class SearchType {
    ALL,      // Búsqueda general (título + autor)
    TITLE,    // Solo por título
    AUTHOR    // Solo por autor
}

