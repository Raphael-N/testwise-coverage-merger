package com.cqse.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class FileUtils {

    public static List<String> getJsonFilesForPaths(String[] paths) throws IOException {
        Set<String> result = new HashSet<>();
        for (String path : paths) {
            Path currentPath = Path.of(path);
            if (path.endsWith(".json") && Files.exists(currentPath)) {
                result.add(path);
                continue;
            }
            if (Files.isDirectory(currentPath)) {
                try (Stream<Path> stream = Files.walk(currentPath)) {
                    result.addAll(stream.map(Path::toString).filter(filePath -> filePath.endsWith(".json")).toList());
                }
            }
        }
        return result.stream().toList();
    }
}
