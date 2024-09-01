package com.andreich.moviesearcher.ui.screen

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.FragmentActorDetailBinding
import com.andreich.moviesearcher.presentation.actor_detail.ActorDetailEvent
import com.andreich.moviesearcher.presentation.actor_detail.ActorDetailNews
import com.andreich.moviesearcher.presentation.actor_detail.ActorDetailStore
import com.andreich.moviesearcher.presentation.actor_detail.ActorDetailUiStateMapper
import com.andreich.moviesearcher.ui.ActorDetailItem
import com.andreich.moviesearcher.ui.adapter.ActorDetailAdapter
import com.andreich.moviesearcher.ui.state.ActorDetailUiState
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tinkoff.kotea.android.lifecycle.collectOnCreate
import ru.tinkoff.kotea.android.storeViaViewModel
import javax.inject.Inject

class ActorDetailFragment : Fragment() {

    companion object {

        private const val ID_KEY = "id_key"

        fun getInstance(id: Int): ActorDetailFragment {
            return ActorDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_KEY, id)
                }
            }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MainActivity", throwable.message, throwable)
    }

    private val component by lazy { (activity?.application as MovieApp).component }

    @Inject
    lateinit var actorDetailStore: ActorDetailStore

    @Inject
    lateinit var mapper: ActorDetailUiStateMapper

    private val store by storeViaViewModel(Dispatchers.Default + coroutineExceptionHandler) {
        actorDetailStore
    }

    private var _binding: FragmentActorDetailBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("Binding is null!")

    private val adapter: ActorDetailAdapter by lazy(LazyThreadSafetyMode.NONE) { ActorDetailAdapter() }


    private val actorId by lazy { arguments?.getInt(ID_KEY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        store.collectOnCreate(
            fragment = this,
            uiStateMapper = mapper,
            stateCollector = ::collectState,
            newsCollector = ::handleNews
        )
        store.dispatch(ActorDetailEvent.ActorDetailUiEvent.LoadActor(lifecycleScope, actorId ?: 0))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleNews(news: ActorDetailNews) {
        when (news) {
            ActorDetailNews.BackPressed -> {

            }
            is ActorDetailNews.NavigateTo -> {
                navigateTo(news.fragment)
            }
            is ActorDetailNews.ShowError -> {
                Toast.makeText(requireContext(), news.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun collectState(state: ActorDetailUiState) {

        state.actorDetailItem?.let {
            initScreen(it)
        }
    }

    private fun initViews() {
        with(binding) {
            recyclerActorDetailMovies.layoutManager = LinearLayoutManager(context)
            recyclerActorDetailMovies.adapter = adapter
            adapter.onCLick = {
                Log.d("ACTORS_MOVIE_CLICK", it.id.toString())
                store.dispatch(ActorDetailEvent.ActorDetailUiEvent.MovieItemClicked(it.id))
            }
        }
    }

    private fun initScreen(actor: ActorDetailItem) {
        with(binding) {
            Glide.with(this@ActorDetailFragment).load(actor.photo)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        actorImage.setImageDrawable(resource)
                        shimmerActor.visibility = GONE
                        actorImage.visibility = VISIBLE
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        actorImage.setImageDrawable(placeholder)
                        shimmerActor.visibility = GONE
                        actorImage.visibility = VISIBLE
                    }

                })
            textViewActorName.text = actor.name
            textViewActorEnName.text = actor.enName
            textViewGrowth.text = actor.growth
            textViewBirthday.text = actor.birthday
            textViewAge.text = actor.age
            textViewBirthplace.text = actor.birthPlace
            textViewCareer.text = actor.profession
            textViewGenres.text = actor.profession
            textViewSpouseTag.text = if (actor.sex.equals("Мужской")) "Супруга" else "Супруг"
            adapter.submitList(actor.movies)
            actor.spouses.let {
                if (actor.spouses != "") {
                    textViewSpouse.visibility = VISIBLE
                    textViewSpouseTag.visibility = VISIBLE
                    textViewSpouse.text = actor.spouses
                }
            }
            actor.death?.let {
                textViewDeathDay.visibility = VISIBLE
                textViewDeathDayTag.visibility = VISIBLE
                textViewDeathDay.text = actor.death
            }
        }
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}