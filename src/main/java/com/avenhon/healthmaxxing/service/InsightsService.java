package com.avenhon.healthmaxxing.service;

import com.avenhon.healthmaxxing.dto.InsightsResponse;
import com.avenhon.healthmaxxing.dto.MetricInsightDTO;
import com.avenhon.healthmaxxing.entity.Metrics;
import com.avenhon.healthmaxxing.enums.InsightMetricsStatuses;
import com.avenhon.healthmaxxing.enums.InsightMetricsTypes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsightsService {
    private final MetricsService metricsService;

    public InsightsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    public InsightsResponse prepareTodayInsight(Long userId) {
        return createInsights(metricsService.getTodayMetricsByUserId(userId));
    }

    private static final double K = 8;

    private int calculateSleepScore(Metrics metrics) {
        float sleepHours = metrics.getSleepHours();
        double sleepCycles = (metrics.getSleepHours() * 60f) / 90f;

        // K here it's penalty coefficient: how strictly deviation from 5 cycles is punished
        return (int) Math.max(0, Math.min(100, (Math.round(100 - K * Math.pow((sleepCycles - 5), 2)))));
    }

    private static final double NORMAL_MIN = 18.5;
    private static final double NORMAL_MAX = 24.9;
    private static final double A = 8;
    private static final double B = 1;

    private int calculateBMIScore(Metrics metrics) {
        double BMI = metrics.getBmi();

        if (NORMAL_MIN <= BMI && BMI <= NORMAL_MAX) return 100;

        double d = BMI > NORMAL_MAX
                ? BMI - NORMAL_MAX
                : NORMAL_MIN - BMI;

        return (int) Math.max(0, Math.min(100, Math.round(100 - A * d - B * d * d)));
    }

    private int calculateStepsScore(Metrics metrics) {
        double steps = metrics.getSteps();

        return (int) Math.max(0, Math.min(100, Math.round(100 * steps / 7000)));
    }

    private InsightMetricsStatuses determineStatus(int score) {
        if (score >= 85) return InsightMetricsStatuses.EXCELLENT;
        if (score >= 70) return InsightMetricsStatuses.GOOD;
        if (score >= 50) return InsightMetricsStatuses.AVERAGE;
        if (score >= 30) return InsightMetricsStatuses.POOR;
        return InsightMetricsStatuses.CRITICAL;
    }

    private InsightsResponse createInsights(Metrics metrics) {
        int sleepScore = calculateSleepScore(metrics);
        int bmiScore = calculateBMIScore(metrics);
        int stepsScore = calculateStepsScore(metrics);

        MetricInsightDTO sleepMetric = new MetricInsightDTO(
                InsightMetricsTypes.SLEEP,
                sleepScore,
                determineStatus(sleepScore)
        );

        MetricInsightDTO bmiMetric = new MetricInsightDTO(
                InsightMetricsTypes.BMI,
                bmiScore,
                determineStatus(bmiScore)
        );

        MetricInsightDTO stepsMetric = new MetricInsightDTO(
                InsightMetricsTypes.STEPS,
                stepsScore,
                determineStatus(stepsScore)
        );

        List<MetricInsightDTO> insightsMetrics = List.of(sleepMetric, bmiMetric, stepsMetric);

        int summaryScore = (int) (sleepScore * 0.5 + bmiScore * 0.2 + stepsScore * 0.3);
        String summaryText = "";

        if (sleepMetric.status() == InsightMetricsStatuses.POOR ||
                sleepMetric.status() == InsightMetricsStatuses.CRITICAL) {
            summaryText += "It's highly recommended to resting and sleep more\n";
        }

        if (bmiMetric.status() == InsightMetricsStatuses.POOR ||
                bmiMetric.status() == InsightMetricsStatuses.CRITICAL) {
            summaryText += "Take care of your diet, problems with weight may cause heart diseases\n";
        }

        if (stepsMetric.status() == InsightMetricsStatuses.POOR ||
                stepsMetric.status() == InsightMetricsStatuses.CRITICAL) {
            summaryText += "Give attention to your daily steps count. Highly recommended to have more than 5000 steps per day.";
        }

        if (summaryText.isEmpty()) {
            summaryText += "Everything is good! Great job!";
        }

        return new InsightsResponse(summaryScore, determineStatus(summaryScore), insightsMetrics, summaryText);
    }
}