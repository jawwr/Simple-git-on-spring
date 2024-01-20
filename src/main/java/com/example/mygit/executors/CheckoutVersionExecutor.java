package com.example.mygit.executors;

import com.example.mygit.models.VersionDirectoryInfo;
import com.example.mygit.repository.FileRepository;
import com.example.mygit.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckoutVersionExecutor implements CommandExecutor {
    private final FileRepository fileRepository;
    private final VersionRepository versionRepository;

    @Override
    public void execute(String... params) {
        if (params.length == 1) {
            System.out.println("Недостаточно параметров: необходимо указать версию");
            return;
        }
        try {
            VersionDirectoryInfo version = versionRepository.getVersion(Double.parseDouble(params[1]));
            fileRepository.updateRootDirectory(version);
        } catch (NumberFormatException e) {
            System.out.println("Версия указана не правильно");
        } catch (Exception e) {
            System.out.println("Произошла ошибка во время выполнения команды");
        }
    }

    @Override
    public String getCommandToExecute() {
        return "checkout";
    }
}
