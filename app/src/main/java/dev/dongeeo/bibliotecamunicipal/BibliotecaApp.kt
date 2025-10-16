package dev.dongeeo.bibliotecamunicipal

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase Application principal de la app Biblioteca Municipal
 * 
 * HILT CONFIGURATION:
 * - @HiltAndroidApp inicializa Hilt para toda la aplicación
 * - Genera automáticamente el componente de aplicación
 * - Permite inyección de dependencias en Activities, Fragments, Services, etc.
 * - Configura el grafo de dependencias de la aplicación
 * 
 * LIFECYCLE:
 * - Se inicializa cuando la aplicación arranca
 * - Configura Hilt antes de que cualquier componente necesite dependencias
 * - Permite acceso a dependencias en toda la aplicación
 */
@HiltAndroidApp
class BibliotecaApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // La inicialización de Hilt se hace automáticamente por @HiltAndroidApp
        // Aquí se pueden agregar otras configuraciones globales si es necesario
    }
}
