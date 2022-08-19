package com.cqse.json;

import com.cqse.model.FileCoverage;
import com.cqse.model.LineRange;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileCoverageDeserializer implements JsonDeserializer<FileCoverage> {
    @Override
    public FileCoverage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String coveredLines = jsonObject.get("coveredLines").getAsString();
        return new FileCoverage(jsonObject.get("fileName").getAsString(), parseLineRanges(coveredLines));
    }

    List<LineRange> parseLineRanges(String ranges) {
        List<LineRange> result = new ArrayList<>();

        String[] split = ranges.split(",");
        for (String range: split) {
            String[] startAndEnd = range.split("-");
            if (startAndEnd.length == 1) {
                int singleLine = Integer.parseInt(startAndEnd[0]);

                result.add(new LineRange(singleLine, singleLine));
            } else {
                int start = Integer.parseInt(startAndEnd[0]);
                int end = Integer.parseInt(startAndEnd[1]);

                result.add(new LineRange(start, end));
            }
        }
        return result;
    }
}
