package com.android.todolist.common;

import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class NoteTest {

    @Test
    public void isReturnSameHash_identicalProperties() {
        Note note1 = new Note("Note 1", "some text for note 1");
        note1.setId(1);
        Note note2 = new Note("Note 1", "some text for note 1");
        note2.setId(1);
        assertEquals(note1.hashCode(), note2.hashCode());
    }

    @Test
    public void isNotesEqual_identicalProperties_returnTrue() throws Exception {
        Note note1 = new Note("Note 1", "This is note 1");
        note1.setId(1);
        Note note2 = new Note("Note 1", "This is note 1");
        note2.setId(1);
        assertEquals(note1, note2);
    }

    @Test
    public void isMethodToStringReturnStringInstanceProperly(){
        Note note1 = new Note("Note 1", "some description");
        note1.setId(1);
        assertEquals("{ Note: id = 1, title = Note 1 , text = some description }", note1.toString());
        assertNotNull(note1.toString());
        assertThat(note1.toString(), containsString("title"));
    }

}