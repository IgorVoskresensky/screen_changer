package ru.ivos.screenchanger.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.ivos.screenchanger.data.models.HomeResponse

interface ApiService {

    @GET("?key=33106230-b104905cd7ff74ed17e2229af")
    suspend fun getApiAnswer(
        @Query("id") id: String = "",
        @Query("q") q: String = "",
        @Query("category") category: String = "",
        @Query("page") page: Int = 1
    ): Response<HomeResponse>
}