package sk.doxxbet.doxxbetui.wpbo_assignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import sk.doxxbet.doxxbetui.wpbo_assignment.MainViewModel
import sk.doxxbet.doxxbetui.wpbo_assignment.R
import sk.doxxbet.doxxbetui.wpbo_assignment.client.MySharedPreferences
import sk.doxxbet.doxxbetui.wpbo_assignment.databinding.FragmentRegistrationBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val model: MainViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //checkForRegistration()

        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mySharedPreferences = MySharedPreferences(requireContext())
        model.registerSuccess.observe(viewLifecycleOwner){
            mySharedPreferences.setRegistered(true)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.registerButton.setOnClickListener {
            model.register(
                binding.registrationPassword.text.toString(),
                binding.registrationUsername.text.toString(),
                binding.registrationEmail.text.toString()
                )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkForRegistration() {
        val mySharedPreferences = MySharedPreferences(requireContext())
        if (mySharedPreferences.getRegistered()) {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}