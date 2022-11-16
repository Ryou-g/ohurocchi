package com.example.ohurocchi

import com.google.firebase.firestore.DocumentId
import java.util.*

data class User(
    @DocumentId
    val id: String = "",
    val title: String = "",
    var createdAt: Date = Date(System.currentTimeMillis()),
)