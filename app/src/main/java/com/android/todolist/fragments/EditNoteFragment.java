package com.android.todolist.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.todolist.NewNoteActivity;
import com.android.todolist.R;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.models.NoteModel;
import com.android.todolist.presenters.SingleOpenedViewPresenter;

public class EditNoteFragment extends Fragment {

    private DbHelper dbHelper;
    private NoteModel noteModel;
    private SingleOpenedViewPresenter mSingleOpenedViewPresenter;

    private static final String TAG = "EditNoteFragment";

    public static EditNoteFragment newInstance() {
        
        Bundle args = new Bundle();
        
        EditNoteFragment fragment = new EditNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_note_fragment, container, false);
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

        view.findViewById(R.id.edit_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleOpenedViewPresenter.editNote(context);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

}
