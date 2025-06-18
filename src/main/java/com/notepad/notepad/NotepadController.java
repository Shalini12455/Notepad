package com.notepad.notepad;
import org.springframework.web.bind.annotation.*;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@RestController
public class NotepadController {

    private static final String FILE_PATH = "output.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/write")
    public String writeNoteToJsonArray(@RequestBody Note newNote) {
        List<Note> notes = new ArrayList<>();

        try {
            // Step 1: Read existing data
            File file = new File(FILE_PATH);
            if (file.exists() && file.length() > 0) {
                notes = objectMapper.readValue(file, new TypeReference<List<Note>>() {});
            }

            // Step 2: Append new note
            notes.add(newNote);

            // Step 3: Write updated list back to file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, notes);

            return "Note saved successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/read")
    public List<Note> readNotes() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists() && file.length() > 0) {
                return objectMapper.readValue(file, new TypeReference<List<Note>>() {});
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
