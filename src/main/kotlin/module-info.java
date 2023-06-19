module dev.medzik.librepass.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires dev.medzik.librepass.client;
    requires kotlin.stdlib;
    requires dev.medzik.librepass.types;
    requires dev.medzik.libcrypto;

    exports dev.medzik.librepass.desktop;
    exports dev.medzik.librepass.desktop.gui;

    opens dev.medzik.librepass.desktop to javafx.fxml;
    opens dev.medzik.librepass.desktop.gui to javafx.fxml;
    opens dev.medzik.librepass.desktop.state to javafx.fxml;
}