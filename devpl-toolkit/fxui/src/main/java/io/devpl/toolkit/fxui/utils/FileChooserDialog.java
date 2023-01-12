package io.devpl.toolkit.fxui.utils;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class FileChooserDialog {

    DirectoryChooser directoryChooser = new DirectoryChooser();
    FileChooser fileChooser = new FileChooser();

    static FileChooserDialog instance = new FileChooserDialog();

    public static File showFileOpenDialog(Window ownerWindow) {
        return instance.fileChooser.showOpenDialog(ownerWindow);
    }

    public static File showFileOpenDialog(File initialDirectory, Window ownerWindow) {
        instance.fileChooser.setInitialDirectory(initialDirectory);
        return instance.fileChooser.showOpenDialog(ownerWindow);
    }

    public static File showFileOpenDialog(String title, File initialDirectory, Window ownerWindow) {
        instance.fileChooser.setTitle(title);
        instance.fileChooser.setInitialDirectory(initialDirectory);
        return instance.fileChooser.showOpenDialog(ownerWindow);
    }

    public static File showFileSaveDialog(Window ownerWindow) {
        return instance.fileChooser.showSaveDialog(ownerWindow);
    }

    public static List<File> showFileOpenMultipleDialog(Window ownerWindow) {
        return instance.fileChooser.showOpenMultipleDialog(ownerWindow);
    }

    public static Optional<File> showDirectoryDialog(Window ownerWindow) {
        return Optional.ofNullable(instance.directoryChooser.showDialog(ownerWindow));
    }

    public static File showDirectoryDialog(File initialDirectory, Window ownerWindow) {
        instance.directoryChooser.setInitialDirectory(initialDirectory);
        return instance.directoryChooser.showDialog(ownerWindow);
    }

    public static File showDirectoryDialog(String title, File initialDirectory, Window ownerWindow) {
        instance.directoryChooser.setTitle(title);
        instance.directoryChooser.setInitialDirectory(initialDirectory);
        return instance.directoryChooser.showDialog(ownerWindow);
    }
}