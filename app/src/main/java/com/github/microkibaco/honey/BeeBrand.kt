package com.github.microkibaco.honey
/**
 * @see: S1032118
 * @author: YangZhengYou
 */
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
