package com.flood_android.ui.bookmarkedit.post

data class PostFlipRequest(
    var categoryObejct: CategoryObejct
)

data class CategoryObejct(
    var add: List<String>,
    var delete: List<String>,
    var update: List<List<String>>
)