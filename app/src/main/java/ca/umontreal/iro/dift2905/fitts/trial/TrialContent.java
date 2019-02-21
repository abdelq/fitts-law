package ca.umontreal.iro.dift2905.fitts.trial;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.log;

public class TrialContent {

    public static final List<TrialItem> ITEMS = new ArrayList<>();

    public static class TrialItem {
        public final double difficulty; // TODO
        public final long duration; // TODO

        public TrialItem(long duration, double distance, int dimension) {
            this.duration = duration;
            this.difficulty = log(distance/dimension + 1) / log(2); // TODO
        }

        @Override
        public String toString() {
            return String.format("Difficulty: %f. Duration: %d", difficulty, duration); // TODO
        }
    }
}
