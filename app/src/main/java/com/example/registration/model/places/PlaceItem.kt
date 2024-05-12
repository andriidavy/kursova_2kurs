package com.example.registration.model.places

import java.time.LocalDateTime

data class PlaceItem (
    val description: String,
    val created: LocalDateTime,
    val tags: String,
    val imageUrl: String
    )