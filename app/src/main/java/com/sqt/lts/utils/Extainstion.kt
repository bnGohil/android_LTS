package com.example.lts.utils





fun Boolean.toggle(): Boolean {
    return when(this) {
        true -> {
            false
        }

        false -> {
            true
        }


    }
}

