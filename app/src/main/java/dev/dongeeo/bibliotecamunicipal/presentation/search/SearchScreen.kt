package dev.dongeeo.bibliotecamunicipal.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dongeeo.bibliotecamunicipal.presentation.components.BookListItem
import dev.dongeeo.bibliotecamunicipal.presentation.components.EmptyState
import dev.dongeeo.bibliotecamunicipal.presentation.components.InitialState
import dev.dongeeo.bibliotecamunicipal.presentation.components.LoadingIndicator
import dev.dongeeo.bibliotecamunicipal.presentation.components.SearchBar
import dev.dongeeo.bibliotecamunicipal.ui.theme.BibliotecaMunicipalTheme

/**
 * Pantalla principal de búsqueda de libros
 * 
 * CARACTERÍSTICAS:
 * - SearchBar para ingresar términos de búsqueda
 * - Gestión de estados (Loading, Success, Error, Empty, Idle)
 * - Lista de libros con scroll infinito
 * - Manejo de errores con Snackbar
 * - Diseño siguiendo Material 3
 * 
 * THREADING Y COROUTINES:
 * - La UI se ejecuta en Main Thread
 * - Los cambios de estado se propagan automáticamente desde ViewModel
 * - El ViewModel maneja las corrutinas en background
 * - La recomposición es automática cuando cambia el estado
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    // Estado del ViewModel
    val state by viewModel.state.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchType by viewModel.searchType.collectAsState()
    
    // Snackbar para mostrar errores
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Manejar errores con Snackbar
    LaunchedEffect(state) {
        if (state is SearchState.Error) {
            val errorState = state
            snackbarHostState.showSnackbar(errorState.message)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Biblioteca Municipal",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Barra de búsqueda
                SearchBar(
                    query = searchQuery,
                    searchType = searchType,
                    onQueryChange = viewModel::updateSearchQuery,
                    onSearchTypeChange = viewModel::updateSearchType,
                    modifier = Modifier.padding(16.dp)
                )
                
                // Contenido principal basado en el estado
                when (state) {
                    is SearchState.Idle -> {
                        InitialState(
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    is SearchState.Loading -> {
                        LoadingIndicator(
                            message = "Buscando libros...",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    is SearchState.Success -> {
                        val successState = state
                        if (successState.books.isEmpty()) {
                            EmptyState(
                                title = "No se encontraron libros",
                                message = "No hay libros que coincidan con '${successState.query}'",
                                buttonText = "Limpiar búsqueda",
                                onButtonClick = { viewModel.clearSearch() },
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            // Lista de libros encontrados
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(vertical = 8.dp)
                            ) {
                                // Header con información de resultados
                                item {
                                    Text(
                                        text = "${successState.books.size} libro(s) encontrado(s)",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                    )
                                }
                                
                                // Lista de libros
                                items(successState.books) { book ->
                                    BookListItem(
                                        book = book,
                                        onClick = {
                                            // TODO: Navegar a detalles del libro
                                        }
                                    )
                                }
                            }
                        }
                    }
                    
                    is SearchState.Error -> {
                        val errorState = state
                        EmptyState(
                            title = "Error en la búsqueda",
                            message = errorState.message,
                            buttonText = "Reintentar",
                            onButtonClick = { viewModel.refreshSearch() },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    is SearchState.Empty -> {
                        val emptyState = state
                        EmptyState(
                            title = "No se encontraron libros",
                            message = emptyState.message,
                            buttonText = "Limpiar búsqueda",
                            onButtonClick = { viewModel.clearSearch() },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Preview de la SearchScreen en estado inicial
 */
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    BibliotecaMunicipalTheme {
        SearchScreen()
    }
}