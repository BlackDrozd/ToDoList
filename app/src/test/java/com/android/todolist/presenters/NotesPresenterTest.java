package com.android.todolist.presenters;

import com.android.todolist.MainActivity;
import com.android.todolist.models.NoteModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class NotesPresenterTest {

    @Mock
    private MainActivity view;

    @Mock
    private NoteModel noteModel;

    private NotesPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new NotesPresenter(noteModel);
        assertNotNull(presenter);
    }

    @Test
    public void attachView() {
        presenter.attachView(view);
    }

    @Test
    public void detachView() {
        presenter.detachView();
    }

    @Test
    public void viewIsReady() {
    }

    @Test
    public void deleteNote() {
    }

    @Test
    public void openNote() {
    }
}