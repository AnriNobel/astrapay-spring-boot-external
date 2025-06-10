package com.astrapay.notes.controller;

import com.astrapay.notes.dto.NoteDto;
import com.astrapay.notes.dto.NoteRequestDto;
import com.astrapay.notes.service.NoteService;
import com.astrapay.notes.service.model.NoteModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteDto>> getAllNotes() {
        List<NoteDto> notes = noteService.getAllNotes().stream()
                .map(note -> new NoteDto(note.getId(), note.getContent()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(notes);
    }

    @PostMapping
public ResponseEntity<?> addNote(@RequestBody NoteRequestDto request) {
    if (request.getContent() == null || request.getContent().trim().isEmpty()) {
        return ResponseEntity.badRequest().body("Content cannot be empty.");
    }
    NoteModel note = noteService.addNote(request.getContent());
    return ResponseEntity.ok(new NoteDto(note.getId(), note.getContent()));
}

@DeleteMapping("/{id}")
public ResponseEntity<?> deleteNote(@PathVariable String id) {
    boolean deleted = noteService.deleteNote(id);
    if (!deleted) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().build();
}
}
