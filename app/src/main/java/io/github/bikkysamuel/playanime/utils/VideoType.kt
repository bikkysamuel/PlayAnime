package io.github.bikkysamuel.playanime.utils

enum class VideoType {
    RAW, SUB, DUB;

    companion object {
        fun parseVideoType(videoTypeAsString: String): VideoType {
            return when (videoTypeAsString) {
                "SUB" -> SUB
                "DUB" -> DUB
                "RAW" -> RAW
                else -> RAW
            }
        }
    }
}