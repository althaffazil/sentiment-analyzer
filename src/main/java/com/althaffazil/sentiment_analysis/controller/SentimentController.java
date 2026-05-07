package com.althaffazil.sentiment_analysis.controller;

import com.althaffazil.sentiment_analysis.model.TextRequest;
import com.althaffazil.sentiment_analysis.model.SentimentResponse;
import com.althaffazil.sentiment_analysis.service.SentimentService;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SentimentController {

    private final SentimentService sentimentService;

    public SentimentController(
            SentimentService sentimentService
    ) {
        this.sentimentService = sentimentService;
    }

    @PostMapping("/analyze")
    public Object analyze(
            @RequestBody TextRequest request
    ) {

        if (
                request.getText() == null ||
                request.getText().trim().isEmpty()
        ) {

            return Map.of(
                    "error",
                    "Text cannot be empty"
            );
        }

        String sentiment =
                sentimentService.analyzeSentiment(
                        request.getText()
                );

        double score =
                sentimentService.getSentimentScore(
                        sentiment
                );

        return new SentimentResponse(
                sentiment,
                score
        );
    }
}