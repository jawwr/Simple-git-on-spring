package com.example.mygit.repository;

import com.example.mygit.models.VersionDirectoryInfo;

import java.util.Collection;

public interface VersionRepository {
    void saveVersion(VersionDirectoryInfo version);

    Collection<VersionDirectoryInfo> getVersions();

    VersionDirectoryInfo getVersion(Double version);
}
