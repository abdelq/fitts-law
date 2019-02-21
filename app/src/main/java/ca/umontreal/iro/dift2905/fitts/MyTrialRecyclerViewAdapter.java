package ca.umontreal.iro.dift2905.fitts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ca.umontreal.iro.dift2905.fitts.trial.TrialContent.TrialItem;

public class MyTrialRecyclerViewAdapter extends RecyclerView.Adapter<MyTrialRecyclerViewAdapter.ViewHolder> {

    private final List<TrialItem> mValues;

    /* TODO */
    public MyTrialRecyclerViewAdapter(List<TrialItem> items) {
        mValues = items;
    }

    @Override
    /* TODO */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_trial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    /* TODO */
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.format("%d", position));
        holder.mContentView.setText(mValues.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /* TODO */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public TrialItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
