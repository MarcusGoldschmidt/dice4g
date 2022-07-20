package com.example.dice4g.ui.history

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.*
import com.example.dice4g.app.InstanceManager
import com.example.dice4g.infra.models.Profile
import com.example.dice4g.infra.models.Result
import kotlinx.coroutines.launch

class HistoryViewModel(app: Application) : AndroidViewModel(app) {

    val manager: InstanceManager = InstanceManager.getInstance(app.applicationContext)

    private var _results =
        Pager(
            PagingConfig(
                pageSize = 20,
                initialLoadSize = 20
            ),
        ) {
            ResultPagingSource(
                manager.getCurrentProfile(),
                manager.db.resultDao()
            )
        }.flow.asLiveData()
    val results: LiveData<PagingData<Result>> = _results


    private var _profile = MutableLiveData<Profile>().apply {
        this.value = manager.getCurrentProfile()
    }
    val profile: LiveData<Profile> = _profile
}