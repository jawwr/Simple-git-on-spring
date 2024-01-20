package com.example.mygit.executors;

import com.example.mygit.models.VersionDirectoryInfo;
import com.example.mygit.repository.FileRepository;
import com.example.mygit.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SaveVersionExecutor implements CommandExecutor {
    @Value("${app.version-directory}")
    private String versionDirectoryPath;
    @Value("${app.version-prefix}")
    private String versionPrefix;

    private final FileRepository fileRepository;
    private final VersionRepository versionRepository;

    @Override
    public void execute(String... params) {
        if (params.length == 1) {
            System.out.println("Недостаточно параметров: не указана версия");
            return;
        }
        try {
            String path = versionDirectoryPath + "/" + versionPrefix + params[1];
            VersionDirectoryInfo version = params.length == 3
                    ? new VersionDirectoryInfo(Double.parseDouble(params[1]), params[2], LocalDateTime.now(), path)
                    : new VersionDirectoryInfo(Double.parseDouble(params[1]), "version - " + params[1], LocalDateTime.now(), path);
            fileRepository.saveVersion(version);
            versionRepository.saveVersion(version);
        } catch (NumberFormatException e) {
            System.out.println("Версия указана в неправильном формате, требуется число");
        } catch (IllegalArgumentException e) {

        } catch (Exception ignored) {
        }
    }

    @Override
    public String getCommandToExecute() {
        return "commit";
    }
}
