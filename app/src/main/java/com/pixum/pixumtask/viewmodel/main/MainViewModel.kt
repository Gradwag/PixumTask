package com.pixum.pixumtask.viewmodel.main

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pixum.pixumtask.db.ComicDatabase
import com.pixum.pixumtask.db.ComicRepository
import com.pixum.pixumtask.networking.comics.api.RetrofitInstance
import com.pixum.pixumtask.networking.comics.model.ComicResponse
import com.pixum.pixumtask.networking.comics.model.Data
import com.pixum.pixumtask.view.description.DescriptionActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {
    private var comicsLiveData = MutableLiveData<Data>()
    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Empty)
    val uiState: StateFlow<MainUiState> = _uiState
    private val allComics : LiveData<List<com.pixum.pixumtask.db.entity.Comic>>
    private val comicRepository : ComicRepository

    init {
        val dao = ComicDatabase.getDatabase(application).getNotesDao()
        comicRepository = ComicRepository(dao)
        allComics = comicRepository.allComics
    }

    fun addComicToDatabase(
        comic: com.pixum.pixumtask.db.entity.Comic
    ) = viewModelScope.launch(Dispatchers.IO) {
        comicRepository.insert(comic)
    }

    fun getComics() {
        _uiState.value = MainUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                RetrofitInstance.api.getComics(
                    ts = TS,
                    apikey = API_KEY,
                    hash = HASH
                ).enqueue(object : Callback<ComicResponse> {
                    override fun onResponse(call: Call<ComicResponse>, response: Response<ComicResponse>) {
                        _uiState.value = MainUiState.Loaded(
                            MainUIModel(
                                copyright = response.body()!!.copyright,
                                data = response.body()?.data
                            )
                        )

                        if (response.body() != null) {
                            comicsLiveData.value = response.body()?.data!!

                            for (i in 0 until response.body()?.data!!.results!!.size) {
                                addComicToDatabase(
                                    com.pixum.pixumtask.db.entity.Comic(
                                        title = response.body()?.data!!.results!![i].title.toString(),
                                        description = response.body()?.data!!.results!![i].description.toString(),
                                        thumbnailPath = response.body()?.data!!.results!![i].thumbnail.toString()
                                    )
                                )
                            }
                        } else {
                            return
                        }
                    }
                    override fun onFailure(call: Call<ComicResponse>, t: Throwable) {
                        onErrorOccurred()
                    }
                })
            } catch (ex: Exception) {
                if (ex is HttpException && ex.code() == 429) {
                    onQueryLimitReached()
                } else {
                    onErrorOccurred()
                }
            }
        }
    }

    private fun onQueryLimitReached() {
        _uiState.value = MainUiState.Error(
            "query_limit_reached"   // TODO: Add in strings.
        )
    }

    private fun onErrorOccurred() {
        _uiState.value = MainUiState.Error(
            "something_went_wrong"  // TODO: Add in strings.
        )
    }

    fun onComicClicked(
        context: Context,
        description: String
    ) {
        context.startActivity(
            Intent(context, DescriptionActivity::class.java)
                .putExtra("description", description)
        )
    }

    companion object {
        const val TS = 1
        const val API_KEY = "63dedebb703655f52a1b47a9eb665974"
        const val HASH = "684390565c45415107a20ed7088e2b47"
    }
}