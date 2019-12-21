package com.android.todolist.presenters;

import com.android.todolist.MainActivity;
import com.android.todolist.models.NoteModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NotesPresenterTest {

    @Mock
    private MainActivity view;

    @Mock
    private NoteModel noteModel;

    private NotesPresenter presenter;

    @Before
    public void setUp() throws Exception {
        noteModel = Mockito.mock(NoteModel.class);
        view  = Mockito.mock(MainActivity.class);
        presenter = new NotesPresenter(noteModel);
        presenter.attachView(view);

    }

    @Test
    public void viewIsReady() {
        presenter.viewIsReady();
    }

    @Test
    public void detachView(){
        presenter.detachView();
    }
}