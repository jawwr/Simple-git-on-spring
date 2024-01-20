package com.example.mygit.repository;

import com.example.mygit.models.VersionDirectoryInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class VersionRepositoryImpl implements VersionRepository {
    @Value("${app.metadata-file}")
    private String metadataFilePath;

    private final ObjectMapper mapper;
    private Collection<VersionDirectoryInfo> versions;

    @Override
    public void saveVersion(VersionDirectoryInfo version) {
        versions.add(version);
        writeVersions();
    }

    @Override
    public Collection<VersionDirectoryInfo> getVersions() {
        return versions;
    }

    @Override
    public VersionDirectoryInfo getVersion(Double version) {
        return versions.stream().filter(v -> version.equals(v.version())).findFirst().get();
    }

    @EventListener(ApplicationStartedEvent.class)
    public void readVersions() {
        try {
            File metadataFile = new File(metadataFilePath);
            String data = FileUtils.readFileToString(metadataFile, StandardCharsets.UTF_8);
            versions = mapper.readValue(data, new TypeReference<Set<VersionDirectoryInfo>>() {
            });
        } catch (Exception e) {
            versions = new HashSet<>();
        }
    }

    @EventListener(ContextClosedEvent.class)
    public void writeVersions() {
        try {
            String data = mapper.writeValueAsString(versions);
            File metadataFile = new File(metadataFilePath);
            FileUtils.write(metadataFile, data, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Произошла ошибка при сохранении версий");
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void configMapper() {
        mapper.findAndRegisterModules();
    }
}
