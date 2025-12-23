package com.example.randomuserapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuserapp.domain.models.User
import com.example.randomuserapp.domain.repository.RandomUserRepository
import com.example.randomuserapp.utils.Constant
import com.example.randomuserapp.utils.UIResources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class RandomUserUiEvent {
    data object CreateUser: RandomUserUiEvent()
    data class OnGenderChanged(val gender: String): RandomUserUiEvent()
    data class OnNationalityChanged(val nationality: String): RandomUserUiEvent()
    data object CreateUserSetIdleState: RandomUserUiEvent()
    data class DeleteUser(val user: User): RandomUserUiEvent()
}

data class UserState(
    val gender: String = "",
    val nationality: String = ""
)

sealed interface CreateUserState{
    object Idle: CreateUserState
    object Loading: CreateUserState
    object Success: CreateUserState
    data class Error(val error: String): CreateUserState
}

data class CreateUserUIState(
    val contentState: CreateUserState = CreateUserState.Idle
)

@HiltViewModel
class RandomUserViewModel  @Inject constructor(
    private val randomUserRepository: RandomUserRepository,
): ViewModel() {

    private val _userState: MutableStateFlow<UserState> = MutableStateFlow(
        UserState()
    )
    val userState: StateFlow<UserState>
        get() = _userState

    val users: Flow<List<User>> = randomUserRepository.getUsersFromDB()

    private val _stateCreateUser = MutableStateFlow<CreateUserUIState>(CreateUserUIState())
    val stateCreateUser: StateFlow<CreateUserUIState> = _stateCreateUser.asStateFlow()


    fun handleEvent(event: RandomUserUiEvent) = viewModelScope.launch {
        when (event) {
            is RandomUserUiEvent.CreateUser -> {
                createUser()
            }
            is RandomUserUiEvent.OnGenderChanged -> {
                setGender(event.gender)
            }
            is RandomUserUiEvent.OnNationalityChanged -> {
                setNationality(event.nationality)
            }
            is RandomUserUiEvent.CreateUserSetIdleState -> {
                setIdleState()
            }
            is RandomUserUiEvent.DeleteUser -> {
                deleteUser(event.user)
            }

        }
    }

    fun getUserById(id: String): Flow<User> {
        return randomUserRepository.getUserById(id)
    }

    private suspend fun deleteUser(user: User) {
        randomUserRepository.deleteUser(user)
    }

    private fun setIdleState() {
        _stateCreateUser.update { it.copy(contentState = CreateUserState.Idle) }
    }

    private suspend fun createUser() {
        _stateCreateUser.update { it.copy(contentState = CreateUserState.Idle) }
        randomUserRepository.createUser(_userState.value.gender,_userState.value.nationality).flowOn(Dispatchers.IO)
            .collectLatest { resources ->
                when (resources) {
                    is UIResources.Error -> withContext(Dispatchers.Main) {
                        _stateCreateUser.update {
                            it.copy(
                                contentState = CreateUserState.Error(
                                    resources.message
                                )
                            )
                        }
                    }

                    is UIResources.Loading -> withContext(Dispatchers.Main) {
                        _stateCreateUser.update { it.copy(contentState = CreateUserState.Loading) }
                    }

                    is UIResources.Success -> withContext(Dispatchers.Main) {
                        _stateCreateUser.update {
                            it.copy(contentState = CreateUserState.Success)
                        }
                    }
                }
            }
    }

    private fun setGender(gender: String) {
        _userState.update {
            it.copy(gender = gender)
        }
    }

    private fun setNationality(nationality: String) {
        _userState.update {
            it.copy(nationality = nationality)
        }
    }

    init {
        _userState.value = UserState(gender = Constant.GENDER_DEFAULT, nationality = Constant.NATIONALITY_DEFAULT)
    }

}