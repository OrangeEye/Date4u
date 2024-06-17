package com.materna.date4u.interfaces;

import com.materna.date4u.core.FileSystem;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class FsCommands {

    private final FileSystem fs;

    public FsCommands(FileSystem fs) {
        this.fs = fs;
    }

    @ShellMethod("Zeigt den freien Speicherplatz")
    String freeDiskSpace() {
        return fs.getFreeDiskSpace() + " Byte";
    }
}
