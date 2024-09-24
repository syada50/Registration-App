package com.example.userregistrationnsdajob2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserProfileRepository

    // LiveData to hold the result of the insert operation
    private val _insertResult = MutableLiveData<Boolean>()
    val insertResult: LiveData<Boolean> get() = _insertResult

    init {
        val userProfileDao = UserDatabase.getDatabase(application).userProfileDao()
        repository = UserProfileRepository(userProfileDao)
    }

    fun getUserProfiles(): LiveData<List<UserProfile>> {
        return repository.getUserProfiles()
    }

    fun insertUserProfile(userProfile: UserProfile) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insert(userProfile)
                // Post success to LiveData
                _insertResult.postValue(true)
            } catch (e: Exception) {
                // Post failure to LiveData
                _insertResult.postValue(false)
            }
        }
    }

    fun updateUserProfile(userProfile: UserProfile) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(userProfile)
        }
    }

    fun deleteUserProfile(userProfile: UserProfile) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(userProfile)
        }
    }
}
