package com.yesferal.hornsapp.app.presentation.categories

import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.domain.entity.Category
import com.yesferal.hornsapp.domain.entity.CategoryKey

class CategoriesPresenter
    : BasePresenter<CategoriesFragment, CategoriesViewData>(){

    fun onViewCreated() {
        // TODO ("Create GetCategoryUseCase(...)")
        val categories = listOf(
            Category(CategoryKey.FAVORITE.toString(), "Favoritos", "https://c4.wallpaperflare.com/wallpaper/850/581/197/hands-people-heavy-metal-concerts-wallpaper-preview.jpg"),
            Category(CategoryKey.LIVE.toString(), "En Vivo", "https://media.altpress.com/uploads/2020/03/concert-crowd.jpeg"),
            Category(CategoryKey.VIRTUAL.toString(), "Virtuales", "https://www.fmpalihue.com/inicio/wp-content/uploads/2020/06/conciertos-streaming.jpg"),
            Category(CategoryKey.METAL.toString(), "Metal", "https://i.pinimg.com/originals/53/4e/2d/534e2df6ce8166eee010ef7282b4491b.jpg"),
            Category(CategoryKey.ROCK.toString(), "Rock", "https://pbs.twimg.com/profile_images/761544198420127744/9T__51m4_400x400.jpg")
        )
        val viewData = CategoriesViewData(categories)
        val success = ViewState.Success(viewData)
        render(state = success)
    }

    override fun render(state: ViewState<CategoriesViewData>) {
        when(state) {
            is ViewState.Success -> {
                view?.show(categories = state.viewData.categories)
            }
        }
    }
}