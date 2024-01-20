package com.example.mygit.utils;

import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@Getter
public class MyGitignoreHandler {
    private Collection<String> ignoreFilter;

    @EventListener(ApplicationStartedEvent.class)
    public void readGitIgnore() {
        try (FileInputStream fis = new FileInputStream(".mygitignore")) {
            String data = IOUtils.toString(fis, StandardCharsets.UTF_8);
            ignoreFilter = Arrays.stream(data.split("\n|\r\n"))
                    .collect(Collectors.toSet());
        } catch (Exception ignore) {
            ignoreFilter = new HashSet<>();
        }
        ignoreFilter.add(".mygit");
    }
}
