package com.cqse.model;

import java.util.List;
import java.util.Objects;

public final class Test {
    private final ETestExecutionResult result;
    private final String sourcePath;
    private final String uniformPath;
    private final double duration;
    private final List<PathCoverage> paths;

    public Test(ETestExecutionResult result, String sourcePath, String uniformPath, double duration, List<PathCoverage> paths) {
        this.result = result;
        this.sourcePath = sourcePath;
        this.uniformPath = uniformPath;
        this.duration = duration;
        this.paths = paths;
    }

    public ETestExecutionResult result() {
        return result;
    }

    public String sourcePath() {
        return sourcePath;
    }

    public String uniformPath() {
        return uniformPath;
    }

    public double duration() {
        return duration;
    }

    public List<PathCoverage> paths() {
        return paths;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Test) obj;
        return Objects.equals(this.result, that.result) &&
                Objects.equals(this.sourcePath, that.sourcePath) &&
                Objects.equals(this.uniformPath, that.uniformPath) &&
                Double.doubleToLongBits(this.duration) == Double.doubleToLongBits(that.duration) &&
                Objects.equals(this.paths, that.paths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, sourcePath, uniformPath, duration, paths);
    }

    @Override
    public String toString() {
        return "Test[" +
                "result=" + result + ", " +
                "sourcePath=" + sourcePath + ", " +
                "uniformPath=" + uniformPath + ", " +
                "duration=" + duration + ", " +
                "paths=" + paths + ']';
    }
}
