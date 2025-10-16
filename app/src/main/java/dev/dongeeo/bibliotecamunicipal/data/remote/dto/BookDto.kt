package dev.dongeeo.bibliotecamunicipal.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTOs para la respuesta de Google Books API
 * Estos modelos representan la estructura exacta de la respuesta JSON
 * de la API externa, sin lógica de negocio.
 */

@Serializable
data class GoogleBooksResponseDto(
    val kind: String,
    val totalItems: Int,
    val items: List<BookItemDto>? = null
)

@Serializable
data class BookItemDto(
    val kind: String,
    val id: String,
    val etag: String,
    val selfLink: String,
    val volumeInfo: VolumeInfoDto,
    val saleInfo: SaleInfoDto? = null,
    val accessInfo: AccessInfoDto? = null,
    val searchInfo: SearchInfoDto? = null
)

@Serializable
data class VolumeInfoDto(
    val title: String,
    val subtitle: String? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val industryIdentifiers: List<IndustryIdentifierDto>? = null,
    val pageCount: Int? = null,
    val printType: String? = null,
    val categories: List<String>? = null,
    val maturityRating: String? = null,
    val allowAnonLogging: Boolean? = null,
    val contentVersion: String? = null,
    val panelizationSummary: PanelizationSummaryDto? = null,
    val imageLinks: ImageLinksDto? = null,
    val language: String? = null,
    val previewLink: String? = null,
    val infoLink: String? = null,
    val canonicalVolumeLink: String? = null
)

@Serializable
data class SaleInfoDto(
    val country: String? = null,
    val saleability: String? = null,
    val isEbook: Boolean? = null,
    val listPrice: PriceDto? = null,
    val retailPrice: PriceDto? = null,
    val buyLink: String? = null,
    val offers: List<OfferDto>? = null
)

@Serializable
data class PriceDto(
    val amount: Double? = null,
    val currencyCode: String? = null
)

@Serializable
data class OfferDto(
    val finskyOfferType: Int? = null,
    val listPrice: PriceDto? = null,
    val retailPrice: PriceDto? = null
)

@Serializable
data class AccessInfoDto(
    val country: String? = null,
    val viewability: String? = null,
    val embeddable: Boolean? = null,
    val publicDomain: Boolean? = null,
    val textToSpeechPermission: String? = null,
    val epub: FormatDto? = null,
    val pdf: FormatDto? = null,
    val webReaderLink: String? = null,
    val accessViewStatus: String? = null,
    val quoteSharingAllowed: Boolean? = null
)

@Serializable
data class FormatDto(
    val isAvailable: Boolean? = null,
    val acsTokenLink: String? = null
)

@Serializable
data class SearchInfoDto(
    val textSnippet: String? = null
)

@Serializable
data class IndustryIdentifierDto(
    val type: String,
    val identifier: String
)

@Serializable
data class PanelizationSummaryDto(
    val containsEpubBubbles: Boolean? = null,
    val containsImageBubbles: Boolean? = null
)

@Serializable
data class ImageLinksDto(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null,
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null,
    val extraLarge: String? = null
)
