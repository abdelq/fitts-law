package ca.umontreal.iro.dift2905.fitts.trial;

import android.annotation.SuppressLint;

import ca.umontreal.iro.dift2905.fitts.trial.TrialContent.TrialItem;

import static ca.umontreal.iro.dift2905.fitts.trial.TrialContent.ITEMS;
import static ca.umontreal.iro.dift2905.fitts.trial.TrialContent.getAverageDifficulty;
import static ca.umontreal.iro.dift2905.fitts.trial.TrialContent.getAverageDuration;

public class LinearRegression {

    private double intercept, slope, coefficient;

    /**
     * Calcule la pente, l'ordonnée à l'origine et le coefficient de régression
     * pour un modèle de régression linéaire, utilisant la méthode des moindres
     * carrés comme méthode d'estimation.
     *
     * Source : en.wikipedia.org/wiki/Regression_analysis#Linear_regression
     */
    public LinearRegression() {
        double xMean = getAverageDifficulty();
        double yMean = getAverageDuration();

        double xxMean = 0, yyMean = 0, xyMean = 0;
        for (TrialItem trial : ITEMS) {
            xxMean += (trial.difficulty - xMean) * (trial.difficulty - xMean);
            yyMean += (trial.duration - yMean) * (trial.duration - yMean);
            xyMean += (trial.difficulty - xMean) * (trial.duration - yMean);
        }

        slope = xyMean / xxMean;
        intercept = yMean - slope * xMean;

        double ssr = 0; // Somme des résidus au carré
        for (TrialItem trial : ITEMS) {
            double y = intercept + slope * trial.difficulty;
            ssr += (y - yMean) * (y - yMean);
        }

        coefficient = ssr / yyMean;
    }

    @SuppressLint("DefaultLocale")
    public String getIntercept() {
        return String.format("a = %f", intercept);
    }
    public double getInterceptValue() {return intercept;}

    @SuppressLint("DefaultLocale")
    public String getSlope() {
        return String.format("b = %f", slope);
    }
    public double getSlopeValue() {return slope;}

    @SuppressLint("DefaultLocale")
    public String getCoefficient() {
        return String.format("r² = %f", coefficient);
    }
}
