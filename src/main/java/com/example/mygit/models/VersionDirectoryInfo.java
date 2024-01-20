package com.example.mygit.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record VersionDirectoryInfo(
        double version,
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTime,
        String path
) {
}
