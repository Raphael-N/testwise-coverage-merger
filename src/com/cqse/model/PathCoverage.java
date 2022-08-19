package com.cqse.model;

import java.util.List;
import java.util.Objects;

public final class PathCoverage {
    private final List<FileCoverage> files;
    private final String path;

    public PathCoverage(List<FileCoverage> files, String path) {
        this.files = files;
        this.path = path;
    }

    public List<FileCoverage> files() {
        return files;
    }

    public String path() {
        return path;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PathCoverage) obj;
        return Objects.equals(this.files, that.files) &&
                Objects.equals(this.path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(files, path);
    }

    @Override
    public String toString() {
        return "PathCoverage[" +
                "files=" + files + ", " +
                "path=" + path + ']';
    }
}
