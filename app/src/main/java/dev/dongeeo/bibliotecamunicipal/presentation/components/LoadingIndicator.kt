package dev.dongeeo.bibliotecamunicipal.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dongeeo.bibliotecamunicipal.ui.theme.BibliotecaMunicipalTheme

/**
 * Componente de indicador de carga
 * 
 * CARACTERÍSTICAS:
 * - CircularProgressIndicator centrado
 * - Mensaje de carga personalizable
 * - Diseño siguiendo Material 3
 * - Animación suave del progreso
 * 
 * USO:
 * - Se muestra durante operaciones asíncronas
 * - Indica al usuario que la aplicación está procesando
 * - Reemplaza el contenido principal temporalmente
 */
@Composable
fun LoadingIndicator(
    message: String = "Buscando libros...",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Indicador circular de progreso
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Mensaje de carga
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * LoadingIndicator compacto para usar dentro de listas
 * 
 * CARACTERÍSTICAS:
 * - Tamaño más pequeño que el indicador principal
 * - Sin mensaje de texto
 * - Ideal para mostrar al final de listas durante carga de más elementos
 */
@Composable
fun CompactLoadingIndicator(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 2.dp
        )
    }
}

/**
 * Preview del LoadingIndicator principal
 */
@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    BibliotecaMunicipalTheme {
        LoadingIndicator(
            message = "Buscando libros en la biblioteca..."
        )
    }
}

/**
 * Preview del LoadingIndicator compacto
 */
@Preview(showBackground = true)
@Composable
fun CompactLoadingIndicatorPreview() {
    BibliotecaMunicipalTheme {
        CompactLoadingIndicator()
    }
}
