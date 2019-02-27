package ca.umontreal.iro.dift2905.fitts;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ca.umontreal.iro.dift2905.fitts.trial.TrialContent.TrialItem;

import static android.view.LayoutInflater.from;

/**
 * La classe MyTrialRecyclerViewAdapter s'occupe de l'affichage
 * de l'indice de difficulté et du temps de réaction pour chaque essai.
 */
public class MyTrialRecyclerViewAdapter extends RecyclerView.Adapter<MyTrialRecyclerViewAdapter.ViewHolder> {

    private final List<TrialItem> mValues;

    MyTrialRecyclerViewAdapter(List<TrialItem> items) {
        mValues = items;
    }

    @Override
    public @NonNull ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(from(parent.getContext()).inflate(R.layout.fragment_trial, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNumberView.setText(holder.mRes.getString(R.string.trial_num, position + 1));
        holder.mDifficultyView.setText(holder.mRes.getString(R.string.difficulty_val, holder.mItem.difficulty));
        holder.mDurationView.setText(holder.mRes.getString(R.string.duration_val, holder.mItem.duration));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final Resources mRes;
        private final TextView mNumberView;
        private final TextView mDifficultyView;
        private final TextView mDurationView;
        private TrialItem mItem;

        private ViewHolder(View view) {
            super(view);
            mRes = view.getResources();
            mNumberView = view.findViewById(R.id.number);
            mDifficultyView = view.findViewById(R.id.difficulty);
            mDurationView = view.findViewById(R.id.duration);
        }
    }
}
