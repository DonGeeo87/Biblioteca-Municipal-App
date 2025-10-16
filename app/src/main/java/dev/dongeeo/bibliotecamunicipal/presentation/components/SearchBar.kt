package dev.dongeeo.bibliotecamunicipal.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dongeeo.bibliotecamunicipal.presentation.search.SearchType
import dev.dongeeo.bibliotecamunicipal.ui.theme.BibliotecaMunicipalTheme

/**
 * Componente de barra de búsqueda con selector de tipo
 * 
 * CARACTERÍSTICAS:
 * - Campo de texto para ingresar término de búsqueda
 * - Radio buttons para seleccionar tipo de búsqueda (Título/Autor/Ambos)
 * - Diseño siguiendo Material 3
 * - Animaciones sutiles con Card elevation
 * 
 * COMPORTAMIENTO:
 * - onQueryChange se llama en cada cambio de texto (para debounce en ViewModel)
 * - onSearchTypeChange se llama cuando cambia el tipo de búsqueda
 * - KeyboardActions maneja la acción de búsqueda al presionar Enter
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    searchType: SearchType,
    onQueryChange: (String) -> Unit,
    onSearchTypeChange: (SearchType) -> Unit,
    modifier: Modifier = Modifier
) {
    // FocusManager para ocultar el teclado
    val focusManager = LocalFocusManager.current
    
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Campo de búsqueda
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Buscar libros...") },
                placeholder = { Text("Ingresa título, autor o ISBN") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Ocultar el teclado al buscar
                        focusManager.clearFocus()
                    }
                )
            )
            
            // Selector de tipo de búsqueda
            Text(
                text = "Buscar por:",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Radio button para búsqueda general
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RadioButton(
                        selected = searchType == SearchType.ALL,
                        onClick = { onSearchTypeChange(SearchType.ALL) }
                    )
                    Text(
                        text = "Ambos",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                // Radio button para búsqueda por título
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RadioButton(
                        selected = searchType == SearchType.TITLE,
                        onClick = { onSearchTypeChange(SearchType.TITLE) }
                    )
                    Text(
                        text = "Título",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                // Radio button para búsqueda por autor
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RadioButton(
                        selected = searchType == SearchType.AUTHOR,
                        onClick = { onSearchTypeChange(SearchType.AUTHOR) }
                    )
                    Text(
                        text = "Autor",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

/**
 * Preview del componente SearchBar
 */
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    BibliotecaMunicipalTheme {
        SearchBar(
            query = "Harry Potter",
            searchType = SearchType.ALL,
            onQueryChange = {},
            onSearchTypeChange = {}
        )
    }
}

/**
 * Preview del componente SearchBar con tipo de búsqueda específico
 */
@Preview(showBackground = true)
@Composable
fun SearchBarWithTitleSearchPreview() {
    BibliotecaMunicipalTheme {
        SearchBar(
            query = "J.K. Rowling",
            searchType = SearchType.AUTHOR,
            onQueryChange = {},
            onSearchTypeChange = {}
        )
    }
}
