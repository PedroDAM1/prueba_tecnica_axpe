package com.pedropelayo.prueba_tecnica.domain.model

import androidx.annotation.StringRes
import com.pedropelayo.prueba_tecnica.R

sealed class GenderTypes(
    @StringRes val stringResource : Int
)  {
    data object Male : GenderTypes(R.string.gender_male)

    data object Female : GenderTypes(R.string.gender_female)

    data object NotDefined : GenderTypes(R.string.not_defined)
}