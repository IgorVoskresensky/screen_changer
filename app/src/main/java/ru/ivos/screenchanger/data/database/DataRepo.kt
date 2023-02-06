package ru.ivos.screenchanger.data.database

import ru.ivos.screenchanger.data.models.Favorite
import javax.inject.Inject

class DataRepo @Inject constructor(
    private val appDao: AppDao
) {

    suspend fun getFavorites(): List<Favorite> {
        return appDao.getFavorites()
    }

    suspend fun insertFavorite(favorite: Favorite) {
        appDao.insertFavorite(favorite)
    }

    suspend fun deleteFavorite(id: Int) {
        appDao.deleteFavorite(id)
    }
}