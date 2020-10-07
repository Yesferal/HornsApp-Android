package com.yesferal.hornsapp.app.presentation.concert.detail

import android.net.Uri
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.entity.Venue
import com.yesferal.hornsapp.domain.usecase.GetConcertUseCase
import com.yesferal.hornsapp.domain.usecase.UpdateFavoriteConcertUseCase
import java.net.URI
import java.util.*

class ConcertPresenter(
    private val getConcertUseCase: GetConcertUseCase,
    private val adManager: AdManager,
    private val updateFavoriteConcertUseCase: UpdateFavoriteConcertUseCase
) : BasePresenter<ConcertFragment, ConcertViewData>() {

    fun onViewCreated(id: String) {
        getConcertUseCase(
            id,
            onSuccess = {
                val viewData = ConcertViewData(
                    concert = it,
                    adView = adManager.concertDetailAdView()
                )
                render(viewData)
            },
            onError = {
                render(ConcertViewData(errorMessageId = R.string.error_default))
            }
        )
    }

    override fun render(
        viewData: ConcertViewData
    ) {
        viewData.concert?.let {
            view?.show(concert = it)
        }

        viewData.adView?.let {
            view?.showAd(it)
        }

        viewData.errorMessageId?.let {
            view?.showError(messageId =  it)
        }

        if (viewData.isLoading) {
            view?.showProgress()
        } else {
            view?.hideProgress()
        }
    }

    fun onFavoriteImageViewClick(
        concert: Concert,
        isChecked: Boolean
    ) {
        concert.isFavorite = isChecked
        updateFavoriteConcertUseCase(
            concert,
            onInsert = {
                view?.showToast(R.string.add_to_favorite)
            },
            onRemove = {}
        )
    }

    fun onFacebookClick(facebookUrl: URI?) {
        facebookUrl?.let {
            val event = facebookUrl.path.replace("/events", "event")
            val facebookAppUri = URI("fb://$event")

            view?.openFacebook(facebookUrl, facebookAppUri)
        }
    }

    fun onDateClick(concert: Concert) {
        concert.date?.let { date ->
            val calendar = Calendar.getInstance()
            calendar.time = date
            view?.openCalendar(concert, calendar)
        }
    }

    fun onVenueClick(venue: Venue?) {
        venue?.let {
            val latitude = it.latitude
            val longitude = it.longitude
            val uri = URI("geo:${latitude},${longitude}?q=${Uri.encode(it.name)}")

            view?.openGoogleMaps(uri)
        }
    }
}