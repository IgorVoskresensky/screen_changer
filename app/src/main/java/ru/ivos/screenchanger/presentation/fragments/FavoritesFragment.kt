package ru.ivos.screenchanger.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.ivos.screenchanger.R
import ru.ivos.screenchanger.data.models.Favorite
import ru.ivos.screenchanger.databinding.FragmentFavoritesBinding
import ru.ivos.screenchanger.presentation.MainActivity
import ru.ivos.screenchanger.presentation.MainViewModel
import ru.ivos.screenchanger.presentation.adapters.FavoritesAdapter
import ru.ivos.screenchanger.utils.Constants


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = _binding ?: throw java.lang.RuntimeException("Binding is empty")

    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var adapter: FavoritesAdapter
    private var favorites: List<Favorite> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.getFavorites()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        (activity as MainActivity).fragment = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.favorites.observe(viewLifecycleOwner) {
            adapter.differ.submitList(it)
            Log.d(Constants.LOG_TAG, "${favorites}")
        }

        adapter = FavoritesAdapter()
        Log.d(Constants.LOG_TAG, "${favorites}")
        binding.rvFavorites.adapter = adapter

        adapter.setOnItemClickListener(object : FavoritesAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                val string = adapter.differ.currentList[position].largeImageURL + "," + adapter.differ.currentList[position].previewURL
                bundle.putString("largeImageURL", string)
                findNavController().navigate(R.id.action_favoritesFragment_to_imageFragment, bundle)
            }
        })
        setupSwipeListener(binding.rvFavorites)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.differ.currentList[viewHolder.adapterPosition]
                viewModel.deleteFavorite(item.id)
//                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                adapter.notifyDataSetChanged()
                lifecycleScope.launch {
                    viewModel.getFavorites()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}