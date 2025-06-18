package com.notepad.notepad;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotepadController.class)
class NotepadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String FILE_PATH = "output.txt";

    @BeforeEach
    void setup() throws Exception {
        // Clear or create the file before each test
        Files.write(new File(FILE_PATH).toPath(), "[]".getBytes());
    }

    @AfterEach
    void cleanup() {
        // Clean up the test file after tests
        new File(FILE_PATH).delete();
    }

    @Test
    void shouldWriteNoteToFile() throws Exception {
        Note note = new Note(1, "Test Topic", "Test Content");

        mockMvc.perform(post("/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Note saved successfully.")));
    }

    @Test
    void shouldReadNotesFromFile() throws Exception {
        Note note = new Note(1, "Topic", "Content");

        // First write a note
        mockMvc.perform(post("/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk());

        // Then read it back
        mockMvc.perform(get("/read"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].topic").value("Topic"))
                .andExpect(jsonPath("$[0].content").value("Content"));
    }
}
