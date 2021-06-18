package com.example.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mynotes.model.ListNote;
import com.example.mynotes.model.Note;

import static com.example.mynotes.service.Common.formatStringToDate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataPickerFragment extends Fragment {



    public static final String _ID = "id";
    public static final String FILED_NAME = "filed_name";


    private Integer id;
    private String fieldName;

    DatePicker mDatePicker;

    public static final String ID_NOTE = "idNote";
    private Integer idNote;

    public DataPickerFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataPickerFragment.
     */

    public static DataPickerFragment newInstance(Integer param1, String param2) {
        DataPickerFragment fragment = new DataPickerFragment();
        Bundle args = new Bundle();
        args.putInt(_ID, param1);
        args.putString(FILED_NAME, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(_ID);
            fieldName = getArguments().getString(FILED_NAME);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatePicker = view.findViewById(R.id.datePicker);
        Button btnDataPicker = view.findViewById(R.id.btnDataPickerDone);
        btnDataPicker.setOnClickListener((View v) -> {
            Note note = ListNote.getInstance().getNote(id);
            if (fieldName.equals("mDataAlarm")) {
                note.setDateAlarm(formatStringToDate(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth()));
            }

            NoteDetailsFragment details = new NoteDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(ID_NOTE, id);
            details.setArguments(args);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nodeDetailsContainer, details)
                    .commit();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_data_picker, container, false);
    }
}
