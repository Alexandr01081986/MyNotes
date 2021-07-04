package com.example.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mynotes.model.ListNote;

import static com.example.mynotes.DataPickerFragment.FILED_NAME;
import static com.example.mynotes.DataPickerFragment._ID;
import static com.example.mynotes.service.Common.formatDateTimeToString;


public class NoteDetailsFragment<Note> extends Fragment {

    public static final String ID_NOTE = "idNote";
    private Integer idNote;
    private Note note;

    private TextView mTheme;
    private TextView mDataCreate;
    private TextView mDataChange;
    private TextView mDataAlarm;

    public NoteDetailsFragment() {
    }

    public static NoteDetailsFragment newInstance(Integer param1) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ID_NOTE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idNote = getArguments().getInt(ID_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTheme = (TextView) view.findViewById(R.id.theme);
        mDataCreate = (TextView) view.findViewById(R.id.dateCreate);
        mDataChange = (TextView) view.findViewById(R.id.dateChange);
        mDataAlarm = (TextView) view.findViewById(R.id.dateAlarm);

        mDataAlarm.setOnClickListener((View v) -> {

            DataPickerFragment dataPickerFragment = new DataPickerFragment();
            Bundle args = new Bundle();
            args.putInt(_ID, idNote);
            args.putString(FILED_NAME, "mDataAlarm");
            dataPickerFragment.setArguments(args);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nodeDetailsContainer, dataPickerFragment)
                    .commit();
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        ListNote notes = ListNote.getInstance();
        note = (Note) notes.getNote(idNote);

        mTheme.setText(((com.example.mynotes.model.Note) note).getTheme());
        mDataCreate.setText(formatDateTimeToString(((com.example.mynotes.model.Note) note).getDateCreate()));
        mDataChange.setText(formatDateTimeToString(((com.example.mynotes.model.Note) note).getDateChange()));
        mDataAlarm.setText(formatDateTimeToString(((com.example.mynotes.model.Note) note).getDateAlarm()));
    }
}