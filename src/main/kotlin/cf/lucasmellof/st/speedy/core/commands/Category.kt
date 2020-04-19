package cf.lucasmellof.st.speedy.core.commands

enum class Category(val cName: String, val emoji: String) {
        FUN("➥Diversão", ":video_game:"),
        INFO("➥Informações", ":scroll:"),
        MODERATION("➥Moderação", ":cop:"),
        OWNER("➥Administração", ":closed_lock_with_key:"),
        MISC("➥Diversos", ":gem:"),
    }