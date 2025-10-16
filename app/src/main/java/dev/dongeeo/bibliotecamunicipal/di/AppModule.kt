package dev.dongeeo.bibliotecamunicipal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dongeeo.bibliotecamunicipal.data.repository.BookRepositoryImpl
import dev.dongeeo.bibliotecamunicipal.domain.repository.BookRepository
import javax.inject.Singleton

/**
 * Módulo de Hilt para configurar las dependencias de la aplicación
 * 
 * Este módulo provee las dependencias principales de la aplicación:
 * - Repositorios: implementaciones concretas de los contratos de dominio
 * - Casos de uso: lógica de negocio de la aplicación
 * - Otras dependencias de aplicación
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    /**
     * Provee la implementación del repositorio de libros
     * 
     * INYECCIÓN DE DEPENDENCIAS:
     * - BookRepositoryImpl recibe GoogleBooksApi y API key como dependencias
     * - Hilt resuelve automáticamente estas dependencias desde NetworkModule
     * - Se marca como Singleton para reutilizar la misma instancia
     * 
     * @param bookRepositoryImpl Implementación concreta del repositorio
     * @return BookRepository como contrato del dominio
     */
    @Provides
    @Singleton
    fun provideBookRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): BookRepository = bookRepositoryImpl
    
}
