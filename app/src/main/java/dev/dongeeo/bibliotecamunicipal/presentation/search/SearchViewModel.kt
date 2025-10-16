package dev.dongeeo.bibliotecamunicipal.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dongeeo.bibliotecamunicipal.domain.model.Book
import dev.dongeeo.bibliotecamunicipal.domain.repository.DomainSearchType
import dev.dongeeo.bibliotecamunicipal.domain.usecase.SearchBooksUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla de búsqueda de libros
 * 
 * COROUTINE DETAILS:
 * - Usa viewModelScope para el ciclo de vida de corrutinas
 * - viewModelScope se cancela automáticamente cuando el ViewModel es destruido
 * - Las corrutinas se ejecutan en el contexto del ViewModel
 * - Manejo de Job para control granular de cancelación
 * 
 * THREADING:
 * - viewModelScope.launch inicia corrutinas en el contexto del ViewModel
 * - Las operaciones suspend se ejecutan en threads de background
 * - El UseCase usa withContext(Dispatchers.IO) internamente
 * - Los cambios de estado se propagan al Main Thread automáticamente
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase
) : ViewModel() {
    
    // Estado privado mutable
    private val _state = MutableStateFlow<SearchState>(SearchState.Idle)
    val state: StateFlow<SearchState> = _state.asStateFlow()
    
    // Estado privado para el texto de búsqueda
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    // Estado privado para el tipo de búsqueda
    private val _searchType = MutableStateFlow(SearchType.ALL)
    val searchType: StateFlow<SearchType> = _searchType.asStateFlow()
    
    // Job para controlar la búsqueda actual
    private var searchJob: Job? = null
    
    /**
     * Actualiza el texto de búsqueda y ejecuta búsqueda con debounce
     * 
     * COROUTINE DETAILS:
     * - Cancela la búsqueda anterior si existe (searchJob?.cancel())
     * - Inicia nueva corrutina con viewModelScope.launch
     * - Debounce de 500ms para evitar búsquedas excesivas
     * - Si el usuario cambia el texto rápidamente, solo la última búsqueda se ejecuta
     * 
     * LIFECYCLE:
     * - Si el usuario sale de la pantalla, la corrutina se cancela automáticamente
     * - Esto previene búsquedas innecesarias y memory leaks
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        
        // Cancelar búsqueda anterior si existe
        searchJob?.cancel()
        
        // Solo buscar si el query no está vacío
        if (query.isNotBlank()) {
            searchJob = viewModelScope.launch {
                // Debounce: esperar 500ms antes de ejecutar la búsqueda
                delay(500)
                
                // Verificar si el job no fue cancelado durante el delay
                if (!searchJob?.isCancelled!!) {
                    searchBooks(query, _searchType.value)
                }
            }
        } else {
            // Si el query está vacío, volver al estado inicial
            _state.value = SearchState.Idle
        }
    }
    
    /**
     * Actualiza el tipo de búsqueda y ejecuta nueva búsqueda si hay query
     * 
     * THREADING:
     * - Función suspend ejecutada en viewModelScope
     * - Cancela búsqueda anterior antes de iniciar nueva
     */
    fun updateSearchType(searchType: SearchType) {
        _searchType.value = searchType
        
        // Si hay un query válido, ejecutar nueva búsqueda
        if (_searchQuery.value.isNotBlank()) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                searchBooks(_searchQuery.value, searchType)
            }
        }
    }
    
    /**
     * Ejecuta la búsqueda de libros
     * 
     * COROUTINE DETAILS:
     * - Función suspend ejecutada en viewModelScope
     * - Usa launch para iniciar corrutina sin retorno (fire-and-forget)
     * - El UseCase internamente usa withContext(Dispatchers.IO)
     * - Manejo de errores con try-catch
     * 
     * THREADING EXPLANATION:
     * - viewModelScope.launch: ejecuta en el contexto del ViewModel
     * - searchBooksUseCase(query, searchType): internamente usa withContext(Dispatchers.IO)
     * - withContext(Dispatchers.IO): mueve la ejecución a thread de background
     * - Los cambios de estado se propagan automáticamente al Main Thread
     * 
     * LIFECYCLE:
     * - Si el ViewModel es destruido, la corrutina se cancela automáticamente
     * - Esto previene operaciones innecesarias y memory leaks
     */
    fun searchBooks(query: String, searchType: SearchType) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            try {
                _state.value = SearchState.Loading
                
                // Ejecutar búsqueda en thread de background
                // El UseCase internamente usa withContext(Dispatchers.IO)
                val domainSearchType = searchType.toDomainSearchType()
                val books = searchBooksUseCase(query, domainSearchType)
                
                // Actualizar estado basado en resultados
                _state.value = if (books.isEmpty()) {
                    SearchState.Empty(query)
                } else {
                    SearchState.Success(books, query, searchType)
                }
                
            } catch (e: Exception) {
                _state.value = SearchState.Error(
                    message = e.message ?: "Error desconocido al buscar libros",
                    throwable = e
                )
            }
        }
    }
    
    /**
     * Refresca la búsqueda actual
     * 
     * THREADING:
     * - Reutiliza la búsqueda actual con los mismos parámetros
     * - Ejecuta nueva corrutina con viewModelScope.launch
     */
    fun refreshSearch() {
        if (_searchQuery.value.isNotBlank()) {
            searchBooks(_searchQuery.value, _searchType.value)
        }
    }
    
    /**
     * Limpia el estado de búsqueda
     * 
     * LIFECYCLE:
     * - Cancela cualquier búsqueda en progreso
     * - Resetea el estado a Idle
     * - Limpia el texto de búsqueda
     */
    fun clearSearch() {
        searchJob?.cancel()
        _searchQuery.value = ""
        _state.value = SearchState.Idle
    }
    
    /**
     * Convierte SearchType de UI a SearchType de dominio
     */
    private fun SearchType.toDomainSearchType(): DomainSearchType {
        return when (this) {
            SearchType.ALL -> DomainSearchType.ALL
            SearchType.TITLE -> DomainSearchType.TITLE
            SearchType.AUTHOR -> DomainSearchType.AUTHOR
        }
    }
    
    /**
     * Convierte SearchType de dominio a SearchType de UI
     */
    private fun DomainSearchType.toPresentationSearchType(): SearchType {
        return when (this) {
            DomainSearchType.ALL -> SearchType.ALL
            DomainSearchType.TITLE -> SearchType.TITLE
            DomainSearchType.AUTHOR -> SearchType.AUTHOR
        }
    }
    
    /**
     * Se llama cuando el ViewModel es destruido
     * 
     * LIFECYCLE:
     * - Cancela todas las corrutinas en progreso
     * - Previene memory leaks
     * - Se ejecuta automáticamente cuando la Activity/Fragment es destruida
     */
    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}
