package com.github.microkibaco.honey

data class Brand(
    val Brand: String,
    val Country: String,
    val Description: String,
    val Price: String,
    val ImageURL: String
)

data class BeeBrands(
    val BeeBrands: List<Brand>
)
