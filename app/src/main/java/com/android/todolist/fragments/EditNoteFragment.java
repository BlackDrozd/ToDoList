package com.android.todolist.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.todolist.NewNoteActivity;
import com.android.todolist.R;
import com.android.todolist.common.Note;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.models.NoteModel;
import com.android.todolist.presenters.SingleOpenedViewPresenter;

public class EditNoteFragment extends Fragment {

    private static final String ARG_TITLE = "noteTitle";
    private static final String ARG_TEXT = "noteText";
    private static final String ARG_ID = "noteId";

    private DbHelper dbHelper;
    private NoteModel noteModel;
    private SingleOpenedViewPresenter mSingleOpenedViewPresenter;
    private Note mNote;

    private static final String TAG = "EditNoteFragment";

    public static EditNoteFragment newInstance(Note mNote) {
        
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, mNote.getTitle());
        args.putString(ARG_TEXT, mNote.getText());
        args.putString(ARG_ID, Long.toString(mNote.getId()));
        EditNoteFragment fragment = new EditNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       LinearLayout root = (LinearLayout) inflater.inflate(R.layout.edit_note_fragment, container, false);
        ((TextView)root.findViewById(R.id.note_title)).setText(getArguments().getString(ARG_TITLE));
        ((TextView)root.findViewById(R.id.note_text)).setText(getArguments().getString(ARG_TEXT));
        ((TextView)root.findViewById(R.id.note_id)).setText(getArguments().getString(ARG_ID));
       return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Context context = view.getContext();
        NewNoteActivity activity = (NewNoteActivity) getActivity();


        dbHelper = new DbHelper(context);
        noteModel = new NoteModel(dbHelper);
        mSingleOpenedViewPresenter = new SingleOpenedViewPresenter(noteModel);
        mSingleOpenedViewPresenter.attachView(activity);
        final Long noteId = Long.valueOf(getArguments().getString(ARG_ID));

        view.findViewById(R.id.edit_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleOpenedViewPresenter.editNote(context);
            }
        });

        view.findViewById(R.id.delete_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleOpenedViewPresenter.deleteNote(context, noteId);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

}
