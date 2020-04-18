package cf.lucasmellof.st.speedy.core.commands

enum class Category(val lower: String, val emoji: String) {
        FUN("➥Diversão", ":video_game:"),
        INFO("➥Informações", ":scroll:"),
        MODERATION("➥Moderação", ":cop:"),
        OWNER("➥Administração", ":lock:"),
    }