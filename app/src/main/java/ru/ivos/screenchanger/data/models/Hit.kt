package ru.ivos.screenchanger.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Hit(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("webformatURL")
    @Expose
    val previewURL: String,
    @SerializedName("largeImageURL")
    @Expose
    val largeImageURL: String,
    @SerializedName("tags")
    @Expose
    val tags: String,
    @SerializedName("previewWidth")
    @Expose
    val previewWidth: String,
    @SerializedName("previewHeight")
    @Expose
    val previewHeight: String,
    @SerializedName("fullHDURL")
    @Expose
    val fullHDURL: String,
)