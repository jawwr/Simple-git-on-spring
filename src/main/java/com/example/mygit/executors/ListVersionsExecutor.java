package com.example.mygit.executors;

import com.example.mygit.models.VersionDirectoryInfo;
import com.example.mygit.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListVersionsExecutor implements CommandExecutor {
    private final VersionRepository repository;

    @Override
    public void execute(String... params) {
        var versions = repository.getVersions();
        System.out.println("Список доступных версий:");
        for (VersionDirectoryInfo version : versions) {
            String dateCreate = version.dateTime().toLocalDate() + " " + version.dateTime().toLocalTime();
            System.out.println("Версия: '" + version.version() + "'\n"
                    + "Описание: " + version.description() + "\n"
                    + "Дата создания: " + dateCreate + "\n");
        }
    }

    @Override
    public String getCommandToExecute() {
        return "list";
    }
}
