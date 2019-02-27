package ca.umontreal.iro.dift2905.fitts.trial;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.log;
import static java.lang.Math.sqrt;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

/**
 * La classe TrialContent fournit des méthodes qui initialisent les informations
 * relatives aux essais.
 */
public class TrialContent {

    public static final List<TrialItem> ITEMS = new ArrayList<>();

    /*
     * Ajoute un essai à la liste
     *
     * @param duration temps écoulé pour l'essai
     * @param distX distance entre la position x précédente et courante
     * @param distY distance entre la position y précédente et courante 
     * @param dimension du bouton
     */
    public static void add(long duration, float distX, float distY, int dimension) {
        ITEMS.add(new TrialItem(duration, sqrt(distX * distX + distY * distY), dimension));
    }

    /*
     * Efface les informations relatives aux essais
     */
    public static void clear() {
        ITEMS.clear();
    }

    /*
     * @return la moyenne des indices de difficulté des essais
     */
    static double getAverageDifficulty() {
        return ITEMS.stream().mapToDouble(trial -> trial.difficulty).average().orElse(0);
    }

    /*
     * @return la moyenne des temps écoulés des essais
     */
    static double getAverageDuration() {
        return ITEMS.stream().mapToLong(trial -> trial.duration).average().orElse(0);
    }

    /*
     * @return une chaine de caractères contenant les informations
     * de tous les essais
     */
    public static String toCSV() {
        return ITEMS.stream().map(trial ->
                trial.difficulty + "," + trial.duration
        ).collect(joining(lineSeparator()));
    }

    /*
     * Classe qui crée un essai avec les informations sur 
     * le temps écoulé et la difficulté de celui-ci
     */
    public static class TrialItem {
        public final double difficulty;
        public final long duration;

        private TrialItem(long duration, double distance, int dimension) {
            this.duration = duration;
            this.difficulty = log(distance / dimension + 1) / log(2);
        }
    }
}
