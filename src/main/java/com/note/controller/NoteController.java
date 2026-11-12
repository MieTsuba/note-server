package com.note.controller;

import com.note.entity.Note;
import com.note.mapper.NoteMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteController {

    private final NoteMapper noteMapper;

    public NoteController(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @GetMapping("/note/list")
    public List<Note> list() {
        return noteMapper.selectList(null);
    }
}
