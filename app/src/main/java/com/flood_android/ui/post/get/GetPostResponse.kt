package com.flood_android.ui.post.get

data class GetPostResponse(
    val `data`: PostCategoryData,
    val message: String
)

data class PostCategoryData(
    val category: List<String>
)