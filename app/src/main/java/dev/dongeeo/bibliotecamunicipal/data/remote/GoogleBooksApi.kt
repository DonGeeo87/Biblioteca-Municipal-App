package dev.dongeeo.bibliotecamunicipal.data.remote

import dev.dongeeo.bibliotecamunicipal.data.remote.dto.GoogleBooksResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz Retrofit para Google Books API
 * 
 * COROUTINE DETAILS:
 * - Todas las funciones son suspend functions para operaciones asíncronas
 * - Retrofit maneja automáticamente el thread pool de background
 * - Las llamadas se ejecutan en un thread de I/O, no en Main Thread
 * - El suspend permite que la UI no se bloquee durante las operaciones de red
 * 
 * API ENDPOINTS:
 * - Base URL: https://www.googleapis.com/books/v1/
 * - Endpoint principal: volumes?q={query}&key={API_KEY}
 * - Parámetros: query (búsqueda), key (API key), maxResults (límite), startIndex (paginación)
 */
interface GoogleBooksApi {
    
    /**
     * Busca libros en Google Books API
     * 
     * @param query Término de búsqueda (título, autor, ISBN, etc.)
     * @param apiKey Clave de API de Google Books
     * @param maxResults Máximo número de resultados (default: 40, max: 40)
     * @param startIndex Índice de inicio para paginación (default: 0)
     * @return GoogleBooksResponseDto con los libros encontrados
     * 
     * THREADING:
     * - Esta función suspend se ejecuta en un thread de background
     * - Retrofit maneja automáticamente el thread pool de I/O
     * - La respuesta se serializa automáticamente desde JSON
     */
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 40,
        @Query("startIndex") startIndex: Int = 0
    ): GoogleBooksResponseDto
    
    /**
     * Busca libros por título específico
     * 
     * @param title Título exacto del libro
     * @param apiKey Clave de API de Google Books
     * @return GoogleBooksResponseDto con libros que coinciden con el título
     */
    @GET("volumes")
    suspend fun searchBooksByTitle(
        @Query("q") title: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 40
    ): GoogleBooksResponseDto
    
    /**
     * Busca libros por autor específico
     * 
     * @param author Nombre del autor
     * @param apiKey Clave de API de Google Books
     * @return GoogleBooksResponseDto con libros del autor
     */
    @GET("volumes")
    suspend fun searchBooksByAuthor(
        @Query("q") author: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 40
    ): GoogleBooksResponseDto
}
