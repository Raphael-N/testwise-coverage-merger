package com.cqse;

import com.cqse.json.FileCoverageDeserializer;
import com.cqse.json.FileCoverageSerializer;
import com.cqse.model.ETestExecutionResult;
import com.cqse.model.FileCoverage;
import com.cqse.model.LineRange;
import com.cqse.model.PathCoverage;
import com.cqse.model.Test;
import com.cqse.model.TestwiseCoverage;
import com.cqse.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Merger {
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(FileCoverage.class, new FileCoverageDeserializer())
            .registerTypeAdapter(FileCoverage.class, new FileCoverageSerializer()).create();


    public static void main(String[] args) throws IOException {
        List<String> coveragePaths = FileUtils.getJsonFilesForPaths(args);
        List<TestwiseCoverage> coverages = new ArrayList<>();
        for (String coveragePath : coveragePaths) {
            System.out.println("Loading Coverage from " + coveragePath);
            coverages.add(loadCoverage(coveragePath));
        }
        TestwiseCoverage resultCov = merge(coverages);
        FileWriter outputWriter = new FileWriter("out.json");
        gson.toJson(resultCov, outputWriter);
        outputWriter.flush();
        outputWriter.close();
    }

    private static TestwiseCoverage loadCoverage(String coveragePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(coveragePath))) {
            return gson.fromJson(reader, TestwiseCoverage.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static TestwiseCoverage merge(List<TestwiseCoverage> coverageList) {
        Map<String, Test> finalTestMap = new HashMap<>();
        for (TestwiseCoverage coverage : coverageList) {
            for (Test test : coverage.tests()) {
                System.out.println("Merging Test " + test.uniformPath());
                if (finalTestMap.containsKey(test.uniformPath())) {
                    finalTestMap.put(test.uniformPath(), mergeTestCoverage(finalTestMap.get(test.uniformPath()), test));
                } else {
                    finalTestMap.put(test.uniformPath(), test);
                }
            }
        }
        return new TestwiseCoverage(new HashSet<>(finalTestMap.values()));
    }

    private static Test mergeTestCoverage(Test test1, Test test2) {
        Map<String, PathCoverage> resultPaths = new HashMap<>();
        List<PathCoverage> allPaths = new ArrayList<>(test1.paths());
        allPaths.addAll(test2.paths());
        for (PathCoverage path : allPaths) {
            if (resultPaths.containsKey(path.path())) {
                resultPaths.put(path.path(), mergePathCoverage(path, resultPaths.get(path.path())));
            } else {
                resultPaths.put(path.path(), path);
            }
        }
        return new Test(ETestExecutionResult.combine(test1.result(), test2.result()), test1.sourcePath(), test1.uniformPath(), test1.duration(), new ArrayList<>(resultPaths.values()));
    }

    private static PathCoverage mergePathCoverage(PathCoverage path1, PathCoverage path2) {
        Map<String, FileCoverage> resultFileCov = new HashMap<>();
        List<FileCoverage> allFiles = new ArrayList<>(path1.files());
        allFiles.addAll(path2.files());
        for (FileCoverage fileCov : allFiles) {
            if (resultFileCov.containsKey(fileCov.fileName())) {
                resultFileCov.put(fileCov.fileName(), mergeFileCoverage(fileCov, resultFileCov.get(fileCov.fileName())));
            } else {
                resultFileCov.put(fileCov.fileName(), fileCov);
            }
        }
        return new PathCoverage(new ArrayList<>(resultFileCov.values()), path1.path());
    }

    private static FileCoverage mergeFileCoverage(FileCoverage cov1, FileCoverage cov2) {
        List<LineRange> ranges = new ArrayList<>(cov1.coveredLines());
        ranges.addAll(cov2.coveredLines());
        List<LineRange> resultRanges = compactifyRanges(ranges);
        return new FileCoverage(cov1.fileName(), resultRanges);

    }

    private static List<LineRange> compactifyRanges(List<LineRange> ranges) {
        ranges.sort(Comparator.comparingInt(LineRange::start).thenComparingInt(LineRange::negativeEnd));
        List<LineRange> result = new ArrayList<>();
        LineRange activeRange = null;
        for (LineRange range : ranges) {
            if (activeRange == null) {
                activeRange = range;
                continue;
            }

            // Since they are sorted by start ascending and then end descending, we have the largest chunk of a function first
            if (range.start() == activeRange.start() || range.end() <= activeRange.end()) {
                continue;
            }

            if (range.start() <= activeRange.end()) {
                activeRange = new LineRange(activeRange.start(), range.end());
                continue;
            }

            if (range.start() > activeRange.start()) {
                result.add(activeRange);
                activeRange = range;
            }
        }
        result.add(activeRange);

        return result;
    }


}