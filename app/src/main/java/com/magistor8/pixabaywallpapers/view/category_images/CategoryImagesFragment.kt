package com.magistor8.pixabaywallpapers.view.category_images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magistor8.pixabaywallpapers.App.Companion.BUNDLE_KEY
import com.magistor8.pixabaywallpapers.R
import com.magistor8.pixabaywallpapers.data.retrofit.entires.Image
import com.magistor8.pixabaywallpapers.data.retrofit.entires.ImagesData
import com.magistor8.pixabaywallpapers.databinding.FragmentImagesBinding
import com.magistor8.pixabaywallpapers.domain.contracts.CategoryImagesFragmentContract
import com.magistor8.pixabaywallpapers.view.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope
import org.koin.core.component.inject
import org.koin.core.scope.Scope


class CategoryImagesFragment : BaseFragment(), KoinScopeComponent {

    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!!

    override val scope: Scope by getOrCreateScope()
    private val adapter: CategoryImagesAdapter by inject()
    private val viewModel : CategoryImagesFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        val imagesData = (arguments?.get(BUNDLE_KEY)) as ImagesData
        viewModel.category = imagesData.category ?: ""
        showFirstResult(imagesData)

        initScrollListener()
        adapterClickListener()
        backPressed()

        viewModel.viewState.observe(viewLifecycleOwner) { state -> renderData(state) }
    }

    private fun backPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val navController = findNavController()
                navController.navigate(R.id.action_categoryImagesFragment_to_categoryFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
    }

    private fun adapterClickListener() {
        adapter.setItemClickListener(object : CategoryImagesAdapter.OnListItemClickListener {
            override fun onItemClick(image: Image) {
                val navController = findNavController()
                navController.navigate(R.id.action_categoryImagesFragment_to_imageFragment, bundleOf(Pair(BUNDLE_KEY, image)))
            }
        })
    }

    private fun showFirstResult(data: ImagesData?) {
        data?.let {
            adapter.submitList(data)
        }
    }

    private fun initScrollListener() {
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null
                    && linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1
                    && adapter.itemCount > 1
                ) {
                    loadMore()
                }
            }
        })
    }

    private fun loadMore() {
        viewModel.onEvent(CategoryImagesFragmentContract.Events.LoadMore)
    }


    //Рендер лайвдаты
    private fun renderData(state: CategoryImagesFragmentContract.ViewState) {
        when(state) {
            is CategoryImagesFragmentContract.ViewState.Error -> super.renderError(state.throwable)
            is CategoryImagesFragmentContract.ViewState.Success -> success(state.data)
        }
    }

    private fun success(data: ImagesData) {
        adapter.submitList(data)
    }

    override fun onDetach() {
        scope.close()
        super.onDetach()
    }
}