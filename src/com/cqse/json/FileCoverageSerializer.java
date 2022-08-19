package com.cqse.json;

import com.cqse.model.FileCoverage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class FileCoverageSerializer implements JsonSerializer<FileCoverage> {

    @Override
    public JsonElement serialize(FileCoverage fileCoverage, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("fileName", fileCoverage.fileName());
        resultObject.addProperty("coveredLines", fileCoverage.coveredLinesString());
        return resultObject;
    }
}
