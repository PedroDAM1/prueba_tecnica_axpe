package com.pedropelayo.prueba_tecnica.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.pedropelayo.prueba_tecnica.R

sealed class GenderTypes(
    @StringRes val stringResource : Int,
    @DrawableRes val iconResource : Int
)  {
    data object Male : GenderTypes(R.string.gender_male, R.drawable.male_icon)

    data object Female : GenderTypes(R.string.gender_female, R.drawable.female_icon)

    data object NotDefined : GenderTypes(R.string.not_defined, R.drawable.genre_default_icon)
}