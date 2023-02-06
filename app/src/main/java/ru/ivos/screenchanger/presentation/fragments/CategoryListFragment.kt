package ru.ivos.screenchanger.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivos.screenchanger.R
import ru.ivos.screenchanger.databinding.FragmentCategoryListBinding
import ru.ivos.screenchanger.presentation.MainActivity
import ru.ivos.screenchanger.presentation.MainViewModel
import ru.ivos.screenchanger.presentation.adapters.CategoryListAdapter

@AndroidEntryPoint
class CategoryListFragment : Fragment() {

    private var _binding: FragmentCategoryListBinding? = null
    private val binding: FragmentCategoryListBinding
        get() = _binding ?: throw java.lang.RuntimeException("Binding is empty")

    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var adapter: CategoryListAdapter

    private var category: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString("category")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)

        if ((activity as MainActivity).fragment is HomeFragment) {
            viewModel.getApiAnswer(category = category.toString())
            lifecycleScope.launch {
                binding.rvCategoryList.visibility = View.GONE
                binding.pbCategoryList.visibility = View.VISIBLE
                delay(3000)
                binding.rvCategoryList.visibility = View.VISIBLE
                binding.pbCategoryList.visibility = View.GONE
            }
        }
        (activity as MainActivity).fragment = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CategoryListAdapter()
        binding.rvCategoryList.adapter = adapter
        binding.rvCategoryList.setHasFixedSize(true)
        adapter.setOnItemClickListener(object : CategoryListAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                val string =
                    adapter.differ.currentList[position].largeImageURL + "," + adapter.differ.currentList[position].previewURL
                bundle.putString("largeImageURL", string)
                findNavController().navigate(
                    R.id.action_categoryListFragment_to_imageFragment,
                    bundle
                )
            }
        })
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.homeResponse.observe(viewLifecycleOwner) {
            adapter.differ.submitList(it.hits)
        }
    }
}