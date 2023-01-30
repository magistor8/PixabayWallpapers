package com.magistor8.pixabaywallpapers.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import com.magistor8.pixabaywallpapers.App.Companion.BUNDLE_KEY
import com.magistor8.pixabaywallpapers.R
import com.magistor8.pixabaywallpapers.data.retrofit.entires.ImagesData
import com.magistor8.pixabaywallpapers.databinding.FragmentCategoryBinding
import com.magistor8.pixabaywallpapers.domain.contracts.CategoryFragmentContract
import com.magistor8.pixabaywallpapers.view.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope
import org.koin.core.scope.Scope


class CategoryFragment : BaseFragment(), KoinScopeComponent {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override val scope: Scope by getOrCreateScope()
    private val viewModel : CategoryFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCategoryButtons()
        setLoadingLayout()
        viewModel.viewState.observe(viewLifecycleOwner) { state -> renderData(state) }
    }

    private fun setLoadingLayout() {
        val animationView = binding.loading
        animationView.playAnimation()
    }

    private fun setCategoryButtons() {
        val uniqueValues = resources.getStringArray(R.array.category_array).toSet().shuffled().take(6)
        val buttons = binding.root.children.filter { it is AppCompatButton }
        buttons.forEachIndexed { index, button ->
            (button as AppCompatButton).text = uniqueValues[index]
            setCategoryClickListener(button)
        }
    }

    private fun setCategoryClickListener(button: AppCompatButton) {
        button.setOnClickListener {
            if (!viewModel.isInternetAvailable()) {
                renderError(IllegalAccessException())
                return@setOnClickListener
            }
            val category = button.text.toString()
            viewModel.onEvent(CategoryFragmentContract.Events.OpenCategory(category))
        }
    }
    
    //Рендер лайвдаты
    private fun renderData(state: CategoryFragmentContract.ViewState) {
        when(state) {
            is CategoryFragmentContract.ViewState.Error -> renderError(state.throwable)
            is CategoryFragmentContract.ViewState.Loading -> loadingLayout(View.VISIBLE)
            is CategoryFragmentContract.ViewState.Success -> success(state.data)
        }
    }

    private fun success(data: ImagesData) {
        loadingLayout(View.GONE)
        val navController = findNavController()
        navController.navigate(R.id.action_categoryFragment_to_categoryImagesFragment, bundleOf(Pair(BUNDLE_KEY, data)))
    }

    override fun showAlert(title: String, alert: String) {
        loadingLayout(View.GONE)
        super.showAlert(title, alert)
    }

    private fun loadingLayout(state: Int) {
        binding.loading.visibility = state
    }

    override fun onDetach() {
        scope.close()
        super.onDetach()
    }
}