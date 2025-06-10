package com.astrapay.notes.controller;

import com.astrapay.notes.dto.NoteRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String noteId;

    @BeforeEach
    void setup() throws Exception {
        // Tambah 1 note sebelum setiap test
        NoteRequestDto request = new NoteRequestDto("Test note");
        String response = mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        noteId = objectMapper.readTree(response).get("id").asText();
    }

    @Test
    void testGetAllNotes() throws Exception {
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    void testAddNoteSuccess() throws Exception {
        NoteRequestDto request = new NoteRequestDto("Note dari unit test");
        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Note dari unit test"));
    }

    @Test
    void testAddNoteValidationFail() throws Exception {
        NoteRequestDto request = new NoteRequestDto("   "); // kosong
        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Content cannot be empty."));
    }

    @Test
    void testDeleteNote() throws Exception {
        mockMvc.perform(delete("/api/notes/" + noteId))
                .andExpect(status().isOk());
    }
}
