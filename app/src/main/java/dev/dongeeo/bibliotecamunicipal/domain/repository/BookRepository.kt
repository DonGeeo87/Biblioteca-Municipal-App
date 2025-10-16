package dev.dongeeo.bibliotecamunicipal.domain.repository

import dev.dongeeo.bibliotecamunicipal.domain.model.Book

/**
 * Contrato del repositorio para operaciones con libros
 * 
 * Este interface define las operaciones que puede realizar el repositorio,
 * independientemente de la implementación (API, base de datos local, etc.).
 * 
 * PATRÓN REPOSITORY:
 * - Abstrae la fuente de datos
 * - Permite cambiar implementación sin afectar la lógica de negocio
 * - Facilita testing con implementaciones mock
 */
interface BookRepository {
    
    /**
     * Busca libros usando un término de búsqueda general
     * 
     * @param query Término de búsqueda (puede ser título, autor, ISBN, etc.)
     * @return Lista de libros que coinciden con la búsqueda
     * @throws Exception si ocurre un error en la búsqueda
     */
    suspend fun searchBooks(query: String): List<Book>
    
    /**
     * Busca libros por título específico
     * 
     * @param title Título del libro
     * @return Lista de libros que coinciden con el título
     * @throws Exception si ocurre un error en la búsqueda
     */
    suspend fun searchBooksByTitle(title: String): List<Book>
    
    /**
     * Busca libros por autor específico
     * 
     * @param author Nombre del autor
     * @return Lista de libros del autor
     * @throws Exception si ocurre un error en la búsqueda
     */
    suspend fun searchBooksByAuthor(author: String): List<Book>
    
    /**
     * Busca libros usando una estrategia específica
     * 
     * @param query Término de búsqueda
     * @param searchType Tipo de búsqueda (ALL, TITLE, AUTHOR)
     * @return Lista de libros que coinciden con la búsqueda
     * @throws Exception si ocurre un error en la búsqueda
     */
    suspend fun searchBooks(query: String, searchType: DomainSearchType): List<Book>
}

/**
 * Enum para tipos de búsqueda
 */
enum class DomainSearchType {
    ALL,      // Búsqueda general (título + autor)
    TITLE,    // Solo por título
    AUTHOR    // Solo por autor
}
