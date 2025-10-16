package dev.dongeeo.bibliotecamunicipal.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * API de Open Library para obtener imágenes de libros
 * 
 * Open Library es más permisivo que Google Books para el acceso a imágenes
 * y proporciona URLs directas sin restricciones de referer.
 */
interface OpenLibraryApi {
    
    /**
     * Busca un libro por ISBN para obtener su imagen
     * 
     * @param isbn ISBN del libro
     * @return Información del libro incluyendo URL de imagen
     */
    suspend fun getBookByIsbn(isbn: String): OpenLibraryBook?
    
    /**
     * Busca libros por título para obtener imágenes
     * 
     * @param title Título del libro
     * @return Lista de libros con imágenes
     */
    suspend fun searchBooksByTitle(title: String): OpenLibrarySearchResponse?
}

/**
 * Respuesta de búsqueda de Open Library
 */
@Serializable
data class OpenLibrarySearchResponse(
    @SerialName("docs") val docs: List<OpenLibraryBook>
)

/**
 * Libro de Open Library
 */
@Serializable
data class OpenLibraryBook(
    @SerialName("key") val key: String,
    @SerialName("title") val title: String,
    @SerialName("author_name") val authors: List<String>? = null,
    @SerialName("isbn") val isbn: List<String>? = null,
    @SerialName("cover_i") val coverId: Int? = null,
    @SerialName("first_publish_year") val publishYear: Int? = null
) {
    /**
     * Obtiene la URL de la imagen de portada
     */
    val coverImageUrl: String?
        get() = coverId?.let { 
            "https://covers.openlibrary.org/b/id/$it-M.jpg"
        }
}
