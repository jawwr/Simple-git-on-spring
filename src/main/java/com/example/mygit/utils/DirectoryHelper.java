package com.example.mygit.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class DirectoryHelper {
    @Value("${app.mygit-directory}")
    private String workDirectory;

    @Value("${app.version-directory}")
    private String versionDirectory;

    @Value("${app.metadata-file}")
    private String metadataFilePath;

    @EventListener(ApplicationStartedEvent.class)
    public void createWorkDirectories() throws IOException {
        File workDirectoryFile = new File(workDirectory);
        if (!workDirectoryFile.exists()) {
            workDirectoryFile.mkdirs();
        }
        File versionDirectoryFile = new File(versionDirectory);
        if (!versionDirectoryFile.exists()) {
            versionDirectoryFile.mkdirs();
        }
        File metadataFile = new File(metadataFilePath);
        if (!metadataFile.exists()) {
            metadataFile.createNewFile();
        }
    }
}
