package com.example.mygit.repository;

import com.example.mygit.models.VersionDirectoryInfo;
import com.example.mygit.utils.MyGitignoreHandler;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepository {
    private final MyGitignoreHandler gitignoreHandler;

    public void saveVersion(VersionDirectoryInfo directory) {
        File workDirectoryFile = new File(".");
        File versionDirectoryFile = new File(directory.path());
        rewriteFiles(workDirectoryFile, versionDirectoryFile, directory.version());
    }

    public void updateRootDirectory(VersionDirectoryInfo directory) {
        File workDirectoryFile = new File(".");
        File versionDirectoryFile = new File(directory.path());
        rewriteFiles(versionDirectoryFile, workDirectoryFile, directory.version());
    }

    private void rewriteFiles(File src, File dest, Double version) {
        try {
            List<File> files = FileUtils.listFilesAndDirs(src, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)
                    .stream()
                    .toList();
            for (File file : files) {
                if (isIgnore(file)) {
                    continue;
                }
                File targetFile = new File(dest, src.toURI().relativize(file.toURI()).getPath());
                if (file.isDirectory()) {
                    targetFile.mkdirs();
                } else {
                    FileUtils.copyFile(file, targetFile);
                }
            }
        } catch (FileAlreadyExistsException e) {
            System.out.println("Файл уже существует");
        } catch (Exception e) {
            System.out.println("Произошла ошибка при создании версии '" + version + "'");
            try {
                FileUtils.deleteDirectory(dest);
            } catch (Exception ignored) {
            }
            e.printStackTrace();
        }
    }

    private boolean isIgnore(File file) {
        boolean isIgnore = false;
        for (String filter : gitignoreHandler.getIgnoreFilter()) {
            if (file.getPath().equals(filter) || file.getPath().contains(filter)) {
                isIgnore = true;
                break;
            }
        }
        return isIgnore;
    }
}
