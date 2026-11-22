package com.iticbcn.kollect.data

data class CollectionItem(
    val id: String,
    val name: String,
    val description: String = "",
    val category: String = "Carta"
)

data class ObjectItem(
    val id: String,
    val collectionId: String,
    val name: String,
    val details: String = "",
    val condition: String = "Molt bo",
    val releaseYear: String = "",
    val acquisitionYear: String = "",
    val price: String = ""
)

val collectionCategories = listOf(
    "Carta", "Còmic", "Moneda", "Segell", "Figura", "Barbie",
    "Videojoc", "Disc", "Vinil", "Llibre", "Rellotge", "Joia",
    "Pòster", "Autògraf", "Fotografia", "Maqueta", "Altres"
)

val objectConditions = listOf("Mal estat", "Aceptable", "Bo", "Molt bo")
