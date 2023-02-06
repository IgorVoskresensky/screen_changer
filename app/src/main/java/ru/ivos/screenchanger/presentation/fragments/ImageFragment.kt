package ru.ivos.screenchanger.presentation.fragments

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivos.screenchanger.data.models.Favorite
import ru.ivos.screenchanger.databinding.FragmentImageBinding
import ru.ivos.screenchanger.presentation.MainActivity
import ru.ivos.screenchanger.presentation.MainViewModel
import java.io.IOException

class ImageFragment : Fragment() {

    private var _binding: FragmentImageBinding? = null
    private val binding: FragmentImageBinding
        get() = _binding ?: throw java.lang.RuntimeException("Binding is empty")

    private val viewModel by activityViewModels<MainViewModel>()

    private var imageURL: String? = "string"
    private var previewURL: String? = "string"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val urls = it.getString("largeImageURL")?.split(",")
            imageURL = urls!![0].trim()
            previewURL = urls[1].trim()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as MainActivity).fragment = this

        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(3000)
            binding.pbCategoryList.visibility = View.GONE
        }

        lifecycleScope.launch(Dispatchers.Main) {
            Picasso.get().load(imageURL).into(binding.ivPicture)
        }

        binding.cbFavorite.setOnCheckedChangeListener { _, isChecked ->
            val favorite = Favorite(
                previewURL = previewURL!!,
                largeImageURL = imageURL!!
            )
            if (isChecked) {
                viewModel.insertFavorite(favorite)
            } else {
                viewModel.deleteFavorite(favorite.id)
            }
        }

        val wpManager = WallpaperManager.getInstance(requireContext().applicationContext)

        var bit: Bitmap? = null

        binding.btnSetWallpaper.setOnClickListener {
            Picasso.get().load(imageURL).into(object : com.squareup.picasso.Target{
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    bit = bitmap
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                }
            })

//            val bit: Bitmap = binding.ivPicture.drawToBitmap()
            try {
                wpManager.setBitmap(bit)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}