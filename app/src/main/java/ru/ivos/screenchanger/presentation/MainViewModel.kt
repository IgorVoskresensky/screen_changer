package ru.ivos.screenchanger.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ivos.screenchanger.data.api.ApiRepo
import ru.ivos.screenchanger.data.database.DataRepo
import ru.ivos.screenchanger.data.models.Favorite
import ru.ivos.screenchanger.data.models.HomeResponse
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepo: ApiRepo,
    private val dataRepo: DataRepo,
) : ViewModel() {

    private var _homeResponse = MutableLiveData<HomeResponse>()
    val homeResponse: LiveData<HomeResponse> get() = _homeResponse

    private var _favorites = MutableLiveData<List<Favorite>>()
    val favorites: LiveData<List<Favorite>> get() = _favorites

    fun getFavorites() = viewModelScope.launch(Dispatchers.IO) {
        val favoritesLIst = dataRepo.getFavorites()
        _favorites.postValue(favoritesLIst)
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        dataRepo.insertFavorite(favorite)
    }

    fun deleteFavorite(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        dataRepo.deleteFavorite(id)
    }

    fun getApiAnswer(id: String = "", q: String = "", category: String = "", page: Int = 1)
    = viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                apiRepo.getApiAnswer(id, q, category)
            }.onSuccess {
                _homeResponse.postValue(it.body())
            }.onFailure {

            }
    }
}