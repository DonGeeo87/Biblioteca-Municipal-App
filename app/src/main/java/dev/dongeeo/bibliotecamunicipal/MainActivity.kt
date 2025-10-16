package dev.dongeeo.bibliotecamunicipal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import dev.dongeeo.bibliotecamunicipal.presentation.search.SearchScreen
import dev.dongeeo.bibliotecamunicipal.ui.theme.BibliotecaMunicipalTheme

/**
 * MainActivity principal de la aplicación Biblioteca Municipal
 * 
 * CONFIGURACIÓN:
 * - @AndroidEntryPoint habilita la inyección de dependencias con Hilt
 * - enableEdgeToEdge() permite que el contenido se extienda hasta los bordes
 * - SearchScreen es la pantalla principal de la aplicación
 * 
 * ARQUITECTURA:
 * - Activity mínima que solo contiene la configuración básica
 * - Toda la lógica de UI está en SearchScreen
 * - ViewModel se inyecta automáticamente con Hilt
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BibliotecaMunicipalTheme {
                SearchScreen()
            }
        }
    }
}