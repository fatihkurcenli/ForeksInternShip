package com.autumnsun.foreksinternship.db

import java.io.Serializable

data class FavoriteModel(
    var id: Int = 0,
    var favorite: String
) : Serializable