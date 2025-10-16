package dev.dongeeo.bibliotecamunicipal.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dongeeo.bibliotecamunicipal.ui.theme.BibliotecaMunicipalTheme

/**
 * Componente para mostrar estado vacío cuando no hay resultados
 * 
 * CARACTERÍSTICAS:
 * - Ícono representativo del estado vacío
 * - Título y mensaje explicativo
 * - Botón de acción opcional
 * - Diseño siguiendo Material 3
 * - Animaciones sutiles
 * 
 * USO:
 * - Se muestra cuando una búsqueda no produce resultados
 * - Indica al usuario que puede intentar con otros términos
 * - Proporciona acciones para mejorar la experiencia
 */
@Composable
fun EmptyState(
    title: String = "No se encontraron libros",
    message: String = "Intenta con otros términos de búsqueda o verifica la ortografía",
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Ícono de estado vacío
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Sin resultados",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Título
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Mensaje explicativo
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        // Botón de acción opcional
        if (buttonText != null && onButtonClick != null) {
            Spacer(modifier = Modifier.height(24.dp))
            TextButton(
                onClick = onButtonClick
            ) {
                Text(buttonText)
            }
        }
    }
}

/**
 * Componente para mostrar estado inicial (antes de realizar búsqueda)
 * 
 * CARACTERÍSTICAS:
 * - Ícono de biblioteca
 * - Mensaje de bienvenida
 * - Instrucciones para el usuario
 * - Diseño acogedor
 */
@Composable
fun InitialState(
    title: String = "¡Bienvenido a la Biblioteca Municipal!",
    message: String = "Busca libros por título, autor o ISBN usando el campo de búsqueda",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Ícono de biblioteca
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Biblioteca",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Título de bienvenida
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Mensaje instructivo
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Preview del EmptyState
 */
@Preview(showBackground = true)
@Composable
fun EmptyStatePreview() {
    BibliotecaMunicipalTheme {
        EmptyState(
            title = "No se encontraron libros",
            message = "No hay libros que coincidan con 'xyz123'. Intenta con otros términos de búsqueda.",
            buttonText = "Limpiar búsqueda",
            onButtonClick = {}
        )
    }
}

/**
 * Preview del InitialState
 */
@Preview(showBackground = true)
@Composable
fun InitialStatePreview() {
    BibliotecaMunicipalTheme {
        InitialState()
    }
}
