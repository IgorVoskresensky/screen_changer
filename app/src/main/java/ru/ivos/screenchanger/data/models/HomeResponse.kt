package ru.ivos.screenchanger.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("total")
    @Expose
    val total: Int,
    @SerializedName("totalHits")
    @Expose
    val totalHits: Int,
    @SerializedName("hits")
    @Expose
    val hits: List<Hit>
)