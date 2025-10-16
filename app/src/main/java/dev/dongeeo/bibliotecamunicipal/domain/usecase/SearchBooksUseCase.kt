package dev.dongeeo.bibliotecamunicipal.domain.usecase

import dev.dongeeo.bibliotecamunicipal.domain.model.Book
import dev.dongeeo.bibliotecamunicipal.domain.repository.BookRepository
import dev.dongeeo.bibliotecamunicipal.domain.repository.DomainSearchType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Caso de uso para buscar libros
 * 
 * COROUTINE DETAILS:
 * - Usa withContext(Dispatchers.IO) para cambiar el contexto de ejecución
 * - Mueve la operación de red del Main Thread a un thread de background
 * - Permite que la UI permanezca responsiva durante la búsqueda
 * 
 * THREADING EXPLANATION:
 * - withContext(Dispatchers.IO): cambia temporalmente al thread pool de I/O
 * - Dispatchers.IO: optimizado para operaciones de red y archivos
 * - Al finalizar, automáticamente regresa al contexto original (Main Thread)
 * - Esto evita bloquear la UI durante operaciones de red
 */
class SearchBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    
    /**
     * Ejecuta la búsqueda de libros con un término específico
     * 
     * COROUTINE DETAILS:
     * - withContext(Dispatchers.IO): mueve la ejecución a thread de background
     * - El repositorio se ejecuta en el thread pool de I/O
     * - La UI permanece responsiva durante la búsqueda
     * - Si hay error, se propaga como excepción
     * 
     * @param query Término de búsqueda
     * @param searchType Tipo de búsqueda (ALL, TITLE, AUTHOR)
     * @return Lista de libros encontrados
     * @throws Exception si ocurre un error en la búsqueda
     */
    suspend operator fun invoke(
        query: String,
        searchType: DomainSearchType = DomainSearchType.ALL
    ): List<Book> {
        return withContext(Dispatchers.IO) {
            try {
                // Validar query
                if (query.isBlank()) {
                    throw IllegalArgumentException("El término de búsqueda no puede estar vacío")
                }
                
                // Ejecutar búsqueda en thread de background
                bookRepository.searchBooks(query.trim(), searchType)
            } catch (e: Exception) {
                throw Exception("Error al buscar libros: ${e.message}", e)
            }
        }
    }
    
    /**
     * Búsqueda simple sin especificar tipo
     * 
     * THREADING:
     * - Delegación al método principal con tipo ALL
     * - Mismo comportamiento de threading (withContext(Dispatchers.IO))
     */
    suspend fun searchBooks(query: String): List<Book> {
        return invoke(query, DomainSearchType.ALL)
    }
    
    /**
     * Búsqueda por título específico
     * 
     * THREADING:
     * - Delegación al método principal con tipo TITLE
     * - Mismo comportamiento de threading
     */
    suspend fun searchBooksByTitle(title: String): List<Book> {
        return invoke(title, DomainSearchType.TITLE)
    }
    
    /**
     * Búsqueda por autor específico
     * 
     * THREADING:
     * - Delegación al método principal con tipo AUTHOR
     * - Mismo comportamiento de threading
     */
    suspend fun searchBooksByAuthor(author: String): List<Book> {
        return invoke(author, DomainSearchType.AUTHOR)
    }
}
