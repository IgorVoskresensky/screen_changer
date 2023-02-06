package ru.ivos.screenchanger.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ivos.screenchanger.R
import ru.ivos.screenchanger.databinding.FragmentHomeBinding
import ru.ivos.screenchanger.presentation.MainActivity
import ru.ivos.screenchanger.presentation.MainViewModel
import ru.ivos.screenchanger.presentation.adapters.CategoryListAdapter
import ru.ivos.screenchanger.utils.Constants

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw java.lang.RuntimeException("Binding is empty")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        (activity as MainActivity).fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoryClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initCategoryClickListeners() {
        val bundle = Bundle()

        binding.btnBackgrounds.setOnClickListener {
            bundle.putString("category", "backgrounds")
            findNavController().navigate(R.id.action_homeFragment_to_categoryListFragment, bundle)
        }
        binding.btnFashion.setOnClickListener {
            bundle.putString("category", "fashion")
            findNavController().navigate(R.id.action_homeFragment_to_categoryListFragment, bundle)
        }
        binding.btnNature.setOnClickListener {
            bundle.putString("category", "nature")
            findNavController().navigate(R.id.action_homeFragment_to_categoryListFragment, bundle)
        }
        binding.btnScience.setOnClickListener {
            bundle.putString("category", "science")
            findNavController().navigate(R.id.action_homeFragment_to_categoryListFragment, bundle)
        }
        binding.btnEducation.setOnClickListener {
            bundle.putString("category", "education")
            findNavController().navigate(R.id.action_homeFragment_to_categoryListFragment, bundle)
        }
        binding.btnFeelings.setOnClickListener {
            bundle.putString("category", "feelings")
            findNavController().navigate(R.id.action_homeFragment_to_categoryListFragment, bundle)
        }
        binding.btnHealth.setOnClickListener {
            bundle.putString("category", "health")
            findNavController().navigate(R.id.action_homeFragment_to_categoryListFragment, bundle)
        }
        binding.btnPeople.setOnClickListener {
            bundle.putString("category", "people")
            findNavController().navigate(R.id.action_homeFragment_to_categoryListFragment, bundle)
        }
    }

}