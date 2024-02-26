package sk.wpbo_assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sk.wpbo_assignment.client.FollowedUsersEntity
import sk.wpbo_assignment.client.FollowedUsersRepository
import sk.wpbo_assignment.network.userRepo.UserRepository
import sk.wpbo_assignment.network.userRepo.dtos.RegisterRequest
import sk.wpbo_assignment.network.userRepo.dtos.RegisterResponse
import sk.wpbo_assignment.network.userRepo.dtos.UserDto
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val followedUsersRepository: FollowedUsersRepository
) : ViewModel() {
    init {
        loadFollowed()
    }

    private val _userListSuccess = MutableLiveData<ArrayList<UserDto>>()
    val userListSuccess: LiveData<ArrayList<UserDto>>
        get() = _userListSuccess

    private val _userListError = MutableLiveData<String?>()
    val userListError: LiveData<String?>
        get() = _userListError

    private val _userListLoading = MutableLiveData<Boolean?>()
    val userListLoading: LiveData<Boolean?>
        get() = _userListLoading


    var currentPage = 1
    val perPage = 5
    var total: Int? = null
    var totalPages: Int? = null
    var isLoading: Boolean = false

    fun loadUsers(){
        viewModelScope.launch {
            _userListLoading.value = true
            val response = userRepository.getUsers(currentPage,perPage)
            _userListLoading.value = false
            if (response.isSuccess()){
                if (_userListSuccess.value != null){
                    val userlist: ArrayList<UserDto> = _userListSuccess.value!!
                    response.data?.users?.let { userlist.addAll(it) }
                    _userListSuccess.value = userlist
                }else{
                    total = response.data?.total
                    totalPages = response.data?.totalPages
                    _userListSuccess.value = response.data?.users as ArrayList<UserDto>?
                }

            }else{
                _userListError.value = response.message
            }
        }
    }

    private val _registerSuccess = MutableLiveData<RegisterResponse?>()
    val registerSuccess: LiveData<RegisterResponse?>
        get() = _registerSuccess

    private val _registerError = MutableLiveData<String?>()
    val registerError: LiveData<String?>
        get() = _registerError

    private val _registerLoading = MutableLiveData<Boolean?>()
    val registerLoading: LiveData<Boolean?>
        get() = _registerLoading

    fun register(password: String, username: String, email: String){
        viewModelScope.launch {
            _registerLoading.value = true
            val response = userRepository.register(RegisterRequest(password,username,email))
            _registerLoading.value = false
            if (response.isSuccess()){
                _registerSuccess.value = response.data
            }else{
                println("${response.message}111111")
                _registerError.value = response.message
            }
        }
    }

    private val _followedUsers = MutableLiveData<ArrayList<Int>>()
    val followedUsers: LiveData<ArrayList<Int>>
        get() = _followedUsers
    private fun loadFollowed(){
        viewModelScope.launch {
            val response = followedUsersRepository.getAll()
            _followedUsers.value = response as ArrayList<Int>
        }
    }

    fun addFollow(id:Int){
        viewModelScope.launch {
            followedUsersRepository.insert(FollowedUsersEntity(id))
            _followedUsers.value?.add(id)
        }
    }

    fun removeFollow(id:Int){
        viewModelScope.launch {
            followedUsersRepository.delete(id)
            _followedUsers.value?.remove(id)
        }
    }
}