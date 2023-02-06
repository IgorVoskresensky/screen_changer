package ru.ivos.screenchanger.data.api

import javax.inject.Inject

class ApiRepo @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getApiAnswer(id: String = "", q: String = "", category: String = "", page: Int = 1) =
        apiService.getApiAnswer(id, q, category, page)
}