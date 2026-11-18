package com.smartfan.mobility.intelligence.api;

import java.util.List;

public record AnalyzeResponse(String model, String summary, String confidence, List<String> recommendations) {
}
