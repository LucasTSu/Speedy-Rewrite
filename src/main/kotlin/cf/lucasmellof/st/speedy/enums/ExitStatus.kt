package cf.lucasmellof.st.speedy.enums

enum class ExitStatus(val code: Int) {
    // Error
    INVALID_TOKEN(20),
    CONFIG_MISSING(21),
    DUPLICATE_COMMAND_NAME(22)
}