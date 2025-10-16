package dev.dongeeo.bibliotecamunicipal.data.repository

import dev.dongeeo.bibliotecamunicipal.data.remote.GoogleBooksApi
import dev.dongeeo.bibliotecamunicipal.data.remote.dto.BookItemDto
import dev.dongeeo.bibliotecamunicipal.domain.model.Book
import dev.dongeeo.bibliotecamunicipal.domain.repository.BookRepository
import dev.dongeeo.bibliotecamunicipal.domain.repository.DomainSearchType
import javax.inject.Inject
import javax.inject.Named

/**
 * Implementación del repositorio de libros usando Google Books API
 * 
 * COROUTINE DETAILS:
 * - Todas las funciones son suspend para operaciones asíncronas
 * - Las llamadas a la API se ejecutan en thread de background
 * - Los errores de red se propagan como excepciones
 * - No se bloquea el Main Thread durante las operaciones de red
 * 
 * THREADING:
 * - Las llamadas suspend se ejecutan en el thread pool de Retrofit
 * - El mapeo de DTOs a modelos de dominio se hace en el mismo thread
 * - Los errores se manejan apropiadamente y se propagan al caller
 */
class BookRepositoryImpl @Inject constructor(
    private val googleBooksApi: GoogleBooksApi,
    @Named("google_books_api_key") private val apiKey: String
) : BookRepository {
    
    /**
     * Busca libros usando un término de búsqueda general
     * 
     * THREADING:
     * - La función suspend se ejecuta en thread de background
     * - Retrofit maneja automáticamente el thread pool de I/O
     * - El mapeo se realiza en el mismo thread de background
     */
    override suspend fun searchBooks(query: String): List<Book> {
        return try {
            val response = googleBooksApi.searchBooks(
                query = query,
                apiKey = apiKey
            )
            response.items?.map { it.toDomainModel() } ?: emptyList()
        } catch (e: Exception) {
            throw Exception("Error al buscar libros: ${e.message}", e)
        }
    }
    
    /**
     * Busca libros por título específico
     * 
     * THREADING:
     * - Función suspend ejecutada en thread de background
     * - No bloquea la UI durante la operación de red
     */
    override suspend fun searchBooksByTitle(title: String): List<Book> {
        return try {
            val query = "intitle:$title"
            val response = googleBooksApi.searchBooksByTitle(
                title = query,
                apiKey = apiKey
            )
            response.items?.map { it.toDomainModel() } ?: emptyList()
        } catch (e: Exception) {
            throw Exception("Error al buscar libros por título: ${e.message}", e)
        }
    }
    
    /**
     * Busca libros por autor específico
     * 
     * THREADING:
     * - Función suspend ejecutada en thread de background
     * - No bloquea la UI durante la operación de red
     */
    override suspend fun searchBooksByAuthor(author: String): List<Book> {
        return try {
            val query = "inauthor:$author"
            val response = googleBooksApi.searchBooksByAuthor(
                author = query,
                apiKey = apiKey
            )
            response.items?.map { it.toDomainModel() } ?: emptyList()
        } catch (e: Exception) {
            throw Exception("Error al buscar libros por autor: ${e.message}", e)
        }
    }
    
    /**
     * Busca libros usando una estrategia específica
     * 
     * THREADING:
     * - Función suspend ejecutada en thread de background
     * - Delegación a métodos específicos según el tipo de búsqueda
     */
    override suspend fun searchBooks(query: String, searchType: DomainSearchType): List<Book> {
        return when (searchType) {
            DomainSearchType.ALL -> searchBooks(query)
            DomainSearchType.TITLE -> searchBooksByTitle(query)
            DomainSearchType.AUTHOR -> searchBooksByAuthor(query)
        }
    }
    
    /**
     * Mapea un DTO de la API a un modelo de dominio
     * 
     * THREADING:
     * - Función de mapeo ejecutada en el mismo thread que la llamada suspend
     * - Operación síncrona de transformación de datos
     */
    private fun BookItemDto.toDomainModel(): Book {
        return Book(
            id = this.id,
            title = this.volumeInfo.title,
            subtitle = this.volumeInfo.subtitle,
            authors = this.volumeInfo.authors ?: emptyList(),
            publisher = this.volumeInfo.publisher,
            publishedDate = this.volumeInfo.publishedDate,
            description = this.volumeInfo.description,
            pageCount = this.volumeInfo.pageCount,
            categories = this.volumeInfo.categories ?: emptyList(),
            language = this.volumeInfo.language,
            thumbnailUrl = this.volumeInfo.imageLinks?.thumbnail,
            smallThumbnailUrl = this.volumeInfo.imageLinks?.smallThumbnail,
            previewLink = this.volumeInfo.previewLink,
            infoLink = this.volumeInfo.infoLink,
            isAvailable = determineAvailability(this.saleInfo),
            availabilityStatus = getAvailabilityStatus(this.saleInfo)
        )
    }
    
    /**
     * Determina la disponibilidad del libro basado en la información de venta
     * 
     * LÓGICA MOCK:
     * - Por simplicidad, consideramos que todos los libros están disponibles
     * - En una implementación real, esto dependería de la disponibilidad física
     */
    private fun determineAvailability(saleInfo: dev.dongeeo.bibliotecamunicipal.data.remote.dto.SaleInfoDto?): Boolean {
        // Mock: todos los libros están disponibles
        return true
    }
    
    /**
     * Obtiene el estado de disponibilidad del libro
     * 
     * LÓGICA MOCK:
     * - Retorna un estado genérico de disponibilidad
     * - En implementación real, consultaría sistema de inventario
     */
    private fun getAvailabilityStatus(saleInfo: dev.dongeeo.bibliotecamunicipal.data.remote.dto.SaleInfoDto?): String {
        // Mock: todos los libros están disponibles
        return "Disponible"
    }
}
