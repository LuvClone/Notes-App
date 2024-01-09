package com.example.finalnotes;

public class Note {
    public String getNoteTitle() {
        return NoteTitle;
    }

    public String getNoteContent() {
        return NoteContent;
    }

    public int getIdNote() {
        return IdNote;
    }

    public String getEmail() {
        return email;
    }

    public void setNoteTitle(String noteTitle) {
        NoteTitle = noteTitle;
    }

    public void setNoteContent(String noteContent) {
        NoteContent = noteContent;
    }

    public void setIdNote(int idNote) {
        IdNote = idNote;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String NoteTitle;
    String NoteContent;
    int IdNote;

    String email;


    public Note(){

    }


}
