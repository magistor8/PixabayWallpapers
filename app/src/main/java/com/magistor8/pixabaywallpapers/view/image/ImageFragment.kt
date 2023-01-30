package com.magistor8.pixabaywallpapers.view.image

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.magistor8.pixabaywallpapers.App.Companion.BUNDLE_KEY
import com.magistor8.pixabaywallpapers.R
import com.magistor8.pixabaywallpapers.data.retrofit.entires.Image
import com.magistor8.pixabaywallpapers.databinding.FragmentImageBinding
import com.magistor8.pixabaywallpapers.view.BaseFragment
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope
import org.koin.core.scope.Scope


class ImageFragment : BaseFragment(), KoinScopeComponent {

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!
    private lateinit var image : Image

    override val scope: Scope by getOrCreateScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image = (arguments?.get(BUNDLE_KEY) as Image)
        binding.loadingLayout.visibility = VISIBLE
        loadImage()
        setToDesktop()
    }

    private fun loadImage() {
        Glide.with(binding.root)
            .load(image.largeImageURL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.loadingLayout.visibility = GONE
                    return false
                }
            })
            .into(binding.image)
    }

    private fun setToDesktop() {
        binding.select.setOnClickListener {
            val wallpaperManager = WallpaperManager.getInstance(context)
            Glide.with(this)
                .asBitmap()
                .load(image.largeImageURL)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        wallpaperManager.setBitmap(resource)
                        showAlert(getString(R.string.DialogAlertTitleSuccess), getString(R.string.SetToDesk))
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }
    }
}