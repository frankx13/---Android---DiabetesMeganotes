package com.studio.neopanda.diabetesmeganotes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class AverageGlycemyUtils {

    public static double calculateAverageGlycemyAllResults(List<Double> entriesList) {
        double counterSum = 0;
        double counterTimes = 0;

        for (int i = 0; i < entriesList.size(); i++) {
            counterTimes += 1;
            counterSum = counterSum + entriesList.get(i);
        }

        double result = (counterSum / counterTimes);
        result = round(result, 2);

        return result;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
