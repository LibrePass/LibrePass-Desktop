package dev.medzik.librepass.desktop.utils
enum class OS {
    WINDOWS, LINUX, MACOS, OTHER;
    companion object {
        fun get(): OS {
            val operSys = System.getProperty("os.name").lowercase()
            return if (operSys.contains("win"))
                WINDOWS
            else if (operSys.contains("nix") || operSys.contains("nux") ||
                operSys.contains("aix")
            )
                LINUX
            else if (operSys.contains("mac"))
                MACOS
            else OTHER
        }
    }
}
