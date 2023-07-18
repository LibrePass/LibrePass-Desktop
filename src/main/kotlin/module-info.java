module dev.medzik.librepass.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires dev.medzik.librepass.client;
    requires kotlin.stdlib;
    requires dev.medzik.librepass.shared;
    requires dev.medzik.libcrypto;
    requires org.controlsfx.controls;

    //fix ssl handshake error
    requires jdk.crypto.ec;
    requires jdk.crypto.cryptoki;

    exports dev.medzik.librepass.desktop;
    exports dev.medzik.librepass.desktop.gui;
    exports dev.medzik.librepass.desktop.gui.auth;
    exports dev.medzik.librepass.desktop.gui.dashboard;
    exports dev.medzik.librepass.desktop.gui.components;

    opens dev.medzik.librepass.desktop to javafx.fxml;
    opens dev.medzik.librepass.desktop.gui to javafx.fxml;
    opens dev.medzik.librepass.desktop.gui.auth to javafx.fxml;
    opens dev.medzik.librepass.desktop.gui.dashboard to javafx.fxml;
    opens dev.medzik.librepass.desktop.gui.components to javafx.fxml;
    opens dev.medzik.librepass.desktop.state to javafx.fxml;
}