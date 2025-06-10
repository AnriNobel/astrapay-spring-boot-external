package com.astrapay.notes.service;

import com.astrapay.notes.service.model.NoteModel;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteService {
    private final Map<String, NoteModel> notes = new HashMap<>();

    public List<NoteModel> getAllNotes() {
        return new ArrayList<>(notes.values());
    }

    public NoteModel addNote(String content) {
        String id = UUID.randomUUID().toString();
        NoteModel note = new NoteModel(id, content);
        notes.put(id, note);
        return note;
    }

    public boolean deleteNote(String id) {
        return notes.remove(id) != null;
    }

    public void clearAll() {
        notes.clear();
    }
}
