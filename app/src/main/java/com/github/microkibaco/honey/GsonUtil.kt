package com.github.microkibaco.honey

import android.content.Context
import com.google.gson.Gson
import java.io.InputStreamReader

fun parseBrands(context: Context): BeeBrands {
    val inputStream = context.resources.openRawResource(R.raw.brand)
    val reader = InputStreamReader(inputStream)
    val gson = Gson()
    val brands = gson.fromJson(reader, BeeBrands::class.java)
    reader.close()
    return brands
}
