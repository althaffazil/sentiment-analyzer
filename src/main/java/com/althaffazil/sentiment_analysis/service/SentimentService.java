package com.althaffazil.sentiment_analysis.service;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class SentimentService {

    private final StanfordCoreNLP pipeline;

    public SentimentService() {

        Properties props = new Properties();

        props.setProperty(
                "annotators",
                "tokenize,ssplit,pos,lemma,parse,sentiment"
        );

        pipeline = new StanfordCoreNLP(props);
    }

    public String analyzeSentiment(String text) {

        CoreDocument document = new CoreDocument(text);

        pipeline.annotate(document);

        String sentiment = "Neutral";

        for (CoreSentence sentence : document.sentences()) {
            sentiment = sentence.sentiment();
        }

        return sentiment;
    }

    public double getSentimentScore(String sentiment) {

        return switch (sentiment) {

            case "Very positive" -> 0.95;

            case "Positive" -> 0.75;

            case "Neutral" -> 0.50;

            case "Negative" -> 0.25;

            case "Very negative" -> 0.05;

            default -> 0.50;
        };
    }
}