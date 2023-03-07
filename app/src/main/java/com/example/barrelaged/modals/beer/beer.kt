package com.example.barrelaged.modals.beer

data class beer(
    val id: Int,
    val name: String,
    val tagline: String,
    val first_brewed: String,
    val description: String,
    val image_url: String?,
    val abv: Double,
    val ibu: Double,
    val target_fg: Double,
    val target_og:Double,
    val ebc: Double,
    val srm: Double,
    val ph: Double,
    val attenuation_level: Double,
    val volume: beer_volume,
    val boil_volume: beer_boilvolume,
    val method: beer_method,
    val ingredients: beer_ingredients,
    val food_pairing: List<String>,
    val brewers_tips: String,
    val contributed_by: String,
)