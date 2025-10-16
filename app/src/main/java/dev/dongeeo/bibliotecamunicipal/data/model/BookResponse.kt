package dev.dongeeo.bibliotecamunicipal.data.model

/**
 * Modelo de respuesta para operaciones de búsqueda de libros
 * 
 * Este wrapper permite manejar diferentes tipos de respuesta
 * y proporcionar información adicional sobre el estado de la operación.
 */
sealed class BookResponse {
    /**
     * Respuesta exitosa con lista de libros
     */
    data class Success(
        val books: List<dev.dongeeo.bibliotecamunicipal.domain.model.Book>,
        val totalItems: Int,
        val query: String
    ) : BookResponse()
    
    /**
     * Respuesta de error con mensaje descriptivo
     */
    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : BookResponse()
    
    /**
     * Estado de carga (loading)
     */
    object Loading : BookResponse()
    
    /**
     * Estado inicial (sin búsqueda realizada)
     */
    object Idle : BookResponse()
    
    /**
     * Estado cuando no se encontraron resultados
     */
    data class Empty(
        val query: String,
        val message: String = "No se encontraron libros para la búsqueda: $query"
    ) : BookResponse()
}
