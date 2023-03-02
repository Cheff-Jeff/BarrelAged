package com.example.barrelaged.modals.beer

data class beer_ingredients(
    val malt: List<beer_malt>,
    val hops: List<beer_hops>,
    val yeast: String,
)