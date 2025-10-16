package dev.dongeeo.bibliotecamunicipal.domain.model

/**
 * Modelo de dominio para un libro
 * 
 * Este modelo representa la entidad de negocio "Libro" y contiene
 * solo los datos necesarios para la lógica de la aplicación,
 * independientemente de la fuente de datos (API, base de datos local, etc.).
 */
data class Book(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val authors: List<String> = emptyList(),
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val categories: List<String> = emptyList(),
    val language: String? = null,
    val thumbnailUrl: String? = null,
    val smallThumbnailUrl: String? = null,
    val previewLink: String? = null,
    val infoLink: String? = null,
    val isAvailable: Boolean = true, // Mock de disponibilidad
    val availabilityStatus: String = "Disponible" // Estado mock
) {
    /**
     * Obtiene el autor principal (primer autor de la lista)
     */
    val primaryAuthor: String
        get() = authors.firstOrNull() ?: "Autor desconocido"
    
    /**
     * Obtiene todos los autores como string separado por comas
     */
    val authorsString: String
        get() = authors.joinToString(", ")
    
    /**
     * Obtiene la URL de imagen preferida (thumbnail o smallThumbnail)
     */
    val imageUrl: String?
        get() = thumbnailUrl ?: smallThumbnailUrl
    
    /**
     * Verifica si el libro tiene imagen
     */
    val hasImage: Boolean
        get() = !imageUrl.isNullOrBlank()
    
    /**
     * Obtiene el año de publicación como entero
     */
    val publishedYear: Int?
        get() = publishedDate?.let { date ->
            try {
                // Intenta extraer el año de diferentes formatos de fecha
                when {
                    date.length >= 4 -> date.substring(0, 4).toInt()
                    else -> null
                }
            } catch (e: NumberFormatException) {
                null
            }
        }
}
