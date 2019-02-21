package ca.umontreal.iro.dift2905.fitts.trial;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.log;
import static java.lang.Math.sqrt;

public class TrialContent {

    public static final List<TrialItem> ITEMS = new ArrayList<>();

    public static void add(long duration, float distX, float distY, int dimension) {
        ITEMS.add(new TrialItem(duration, sqrt(distX * distX + distY * distY), dimension));
    }

    public static void clear() {
        ITEMS.clear();
    }

    public static class TrialItem {
        public final double difficulty;
        public final long duration;

        private TrialItem(long duration, double distance, int dimension) {
            this.duration = duration;
            this.difficulty = log(distance/dimension + 1) / log(2);
        }
    }
}
