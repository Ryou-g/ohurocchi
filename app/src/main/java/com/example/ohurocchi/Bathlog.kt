package com.example.ohurocchi

import com.google.firebase.firestore.DocumentId
import java.util.*

data class Bathlog(
    @DocumentId
    val id: String = "お風呂に入りました",
    val title: String = "",
    //var createdAt: Date = Date(System.currentTimeMillis()),
    val createdAt: String = ""
)