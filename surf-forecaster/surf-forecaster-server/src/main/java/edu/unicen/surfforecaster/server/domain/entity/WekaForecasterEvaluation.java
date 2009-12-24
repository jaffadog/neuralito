package edu.unicen.surfforecaster.server.domain.entity;

import java.util.Map;

public class WekaForecasterEvaluation {
private Map<String, String> evaluations;

public WekaForecasterEvaluation(Map<String,String> evaluations) {
this.evaluations= evaluations;
}
}
