package com.cqse.model;

import java.util.List;
import java.util.Objects;

public final class FileCoverage {
    private final String fileName;
    private final List<LineRange> coveredLines;

    public FileCoverage(String fileName, List<LineRange> coveredLines) {
        this.fileName = fileName;
        this.coveredLines = coveredLines;
    }

    public String fileName() {
        return fileName;
    }

    public List<LineRange> coveredLines() {
        return coveredLines;
    }

    public String coveredLinesString() {
        StringBuilder result = new StringBuilder();
        for (LineRange range : coveredLines) {
            if (!result.isEmpty()) {
                result.append(',');
            }
            if (range.start() == range.end()) {
                result.append(range.start());
                continue;
            }
            result.append(range.start()).append('-').append(range.end());
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FileCoverage) obj;
        return Objects.equals(this.fileName, that.fileName) &&
                Objects.equals(this.coveredLines, that.coveredLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, coveredLines);
    }

    @Override
    public String toString() {
        return "FileCoverage[" +
                "fileName=" + fileName + ", " +
                "coveredLines=" + coveredLines + ']';
    }
}
