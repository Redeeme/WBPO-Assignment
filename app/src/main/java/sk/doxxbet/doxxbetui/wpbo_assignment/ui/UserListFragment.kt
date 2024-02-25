package sk.doxxbet.doxxbetui.wpbo_assignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import sk.doxxbet.doxxbetui.wpbo_assignment.MainViewModel
import sk.doxxbet.doxxbetui.wpbo_assignment.databinding.FragmentUserListBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class UserListFragment : Fragment(), UserAdapter.UserClickDelegate {

    private var _binding: FragmentUserListBinding? = null
    private val model: MainViewModel by activityViewModels()

    private lateinit var adapter: UserAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserAdapter(requireContext(), this)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        binding.recyclerView.adapter = adapter

        model.userListSuccess.observe(viewLifecycleOwner){
            adapter.users = it.map { userDto ->
                var followed = false
//                if (model.followedUsers.value?.contains(userDto.id) == true){
//                    followed = true
//                }
                userDto.toUserModel(followed = followed) // You can set followed based on your logic
            }
        }
        binding.recyclerView.addOnScrollListener(object :
            UserPaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                model.isLoading = true
                model.currentPage++
                model.loadUsers()
                model.isLoading = false
            }

            override fun isLastPage(): Boolean {
                return model.currentPage == model.totalPages
            }

            override fun isLoading(): Boolean {
                return model.isLoading
            }
        })
        model.loadUsers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCheck(id: Int) {
        model.addFollow(id)
    }

    override fun onUncheck(id: Int) {
        model.removeFollow(id)
    }
}