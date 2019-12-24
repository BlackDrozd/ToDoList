package com.android.todolist.presenters;

import android.content.ContentValues;

import com.android.todolist.NewNoteActivity;
import com.android.todolist.R;
import com.android.todolist.common.Note;
import com.android.todolist.models.NoteModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SingleOpenedViewPresenterTest {

    @Mock
    NoteModel noteModel;

    @Mock
    NewNoteActivity view;

    @Mock
    Note mNote;

    SingleOpenedViewPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new SingleOpenedViewPresenter(noteModel);
        view  = Mockito.mock(NewNoteActivity.class);
        presenter.attachView(view);
        noteModel = Mockito.mock(NoteModel.class);
        assertNotNull(presenter);
    }

    @Test
    public void shouldShowErrorWhenTitleIsEmpty(){
        when(view.getNoteTitle()).thenReturn("");
        when(view.getNoteText()).thenReturn("text");
        presenter.onAddNoteButtonClicked();
        verify(view).showCreationError(R.string.error_note_is_empty);
    }

    @Test
    public void shouldShowToastWhenNoteUpdated(){
        presenter.attachView(view);
        when(view.getNoteTitle()).thenReturn("title");
        when(view.getNoteText()).thenReturn("text");
        presenter.onAddNoteButtonClicked();
        ContentValues cv = new ContentValues();
        verify(noteModel).addNote(cv, any(NoteModel.CompleteCallback.class));
    }

    @Test
    public void editNote() {
        when(view.getEditedNote()).thenReturn(mNote);
        presenter.editNote();
        verify(view).getEditedNote();
        verify(view).showToast(R.string.note_updated_toast);
        verify(view).startMainActivity();
    }

    @Test
    public void deleteNote() {
        Long someId = Long.valueOf(124);
        presenter.deleteNote(someId);
    }

    @Test
    public void updateNote() {
        noteModel.updateNote(mNote);
    }

    @Test
    public void detachView() {
        presenter.detachView();
    }

}