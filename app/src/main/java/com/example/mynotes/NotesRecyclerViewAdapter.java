package com.example.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.model.ListNote;
import com.example.mynotes.model.Note;

import java.util.List;

import static com.example.mynotes.service.Common.formatDateTimeToString;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder> {

    private final List<Note> mValues;
    private ViewGroup parent;
    private Fragment parentFragment;
    private boolean isLandscape;
    private int currentIdNote = -1;

    public NotesRecyclerViewAdapter(List<Note> items, Fragment parentFragment) {
        mValues = items;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node, parent, false);

        isLandscape = parent.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mTheme.setText(mValues.get(position).getTheme());
        holder.mDataCreate.setText(formatDateTimeToString(mValues.get(position).getDateCreate()));
        holder.mDataChange.setText(formatDateTimeToString(mValues.get(position).getDateChange()));

        final Integer idNote = mValues.get(position).getId();

        holder.mView.setOnClickListener(v -> showNoteDetails(idNote));

        holder.mView.setOnLongClickListener(v -> {
            openPopupMenu(v, idNote);
            return true;
        });
    }

    private void showNoteDetails(Integer idNote) {
        if (isLandscape) {
            showLandNoteDetails(idNote);
        } else {
            showPortNoteDetails(idNote);
        }
    }

    private void showPortNoteDetails(Integer idNote) {

        Intent intent = new Intent();
        intent.setClass(parent.getContext(), NoteDetailsActivity.class);

        intent.putExtra(NoteDetailsFragment.ID_NOTE, idNote);
        parentFragment.startActivity(intent);
    }

    private void showLandNoteDetails(Integer idNote) {

        Intent intent = new Intent();
        intent.setClass(parent.getContext(), NoteDetailsActivity.class);

        intent.putExtra(NoteDetailsFragment.ID_NOTE, idNote);
        parentFragment.startActivity(intent);

        NoteDetailsFragment detail = NoteDetailsFragment.newInstance(idNote);

        FragmentManager fragmentManager = parentFragment.requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nodeDetailsContainer, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTheme;
        public final TextView mDataCreate;
        public final TextView mDataChange;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTheme = (TextView) view.findViewById(R.id.theme);
            mDataCreate = (TextView) view.findViewById(R.id.dateCreate);
            mDataChange = (TextView) view.findViewById(R.id.dateChange);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mTheme.getText() + "'";
        }
    }

    private void openPopupMenu(View view, Integer idNote) {
        currentIdNote = idNote;
        TextView text = view.findViewById(R.id.theme);

        Activity activity = parentFragment.getActivity();
        PopupMenu popupMenu = new PopupMenu(activity, view);
        activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
        Menu menu = popupMenu.getMenu();
        menu.add(0, 1, 10, R.string.add);
        menu.add(0, 2, 12, R.string.edit);
        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case 1:
                    Toast.makeText(parentFragment.getContext(), "Добавить", Toast.LENGTH_SHORT).show();
                    showNoteDetails(ListNote.getInstance().addNote("",""));
                    return true;
                case 2:
                    Toast.makeText(parentFragment.getContext(), "Изменить", Toast.LENGTH_SHORT).show();
                    showNoteDetails(currentIdNote);
                    return true;
            }
            return true;
        });
        popupMenu.show();
    }

}
