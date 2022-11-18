package com.example.ohurocchi

import java.util.*

data class Bathlog(
    @com.google.firebase.firestore.DocumentId
    val id: String = "お風呂に入りました",
    val title: String = "",
    var createdAt: Date = Date(System.currentTimeMillis()),
)