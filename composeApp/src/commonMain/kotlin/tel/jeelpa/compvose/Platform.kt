package tel.jeelpa.compvose

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform