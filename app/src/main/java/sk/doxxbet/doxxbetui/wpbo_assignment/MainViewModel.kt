package sk.doxxbet.doxxbetui.wpbo_assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sk.doxxbet.doxxbetui.wpbo_assignment.network.userRepo.UserRepository
import sk.doxxbet.doxxbetui.wpbo_assignment.network.userRepo.dtos.UserDto
import sk.doxxbet.doxxbetui.wpbo_assignment.ui.UserModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userListSuccess = MutableLiveData<ArrayList<UserDto>>()
    val userListSuccess: LiveData<ArrayList<UserDto>>
        get() = _userListSuccess

    private val _userListError = MutableLiveData<String?>()
    val userListError: LiveData<String?>
        get() = _userListError

    var currentPage = 1
    val perPage = 5
    var total: Int? = null
    var totalPages: Int? = null
    var isLoading: Boolean = false

    fun loadUsers(){
        viewModelScope.launch {
            val response = userRepository.getUsers(currentPage,perPage)
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
}