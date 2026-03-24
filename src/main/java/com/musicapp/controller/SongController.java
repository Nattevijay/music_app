package com.musicapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.dto.SongListResponse;
import com.musicapp.dto.SongRequest;
import com.musicapp.service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> addSong(@RequestPart(value = "request",  required = false) String request,
                                     @RequestPart(value = "audio",  required = false) MultipartFile audioFile,
                                     @RequestPart(value = "image",  required = false) MultipartFile imageFile
    ) {

        System.out.println("Request: " + request);
        System.out.println("Audio: " + (audioFile != null ? audioFile.getOriginalFilename() : "null"));
        System.out.println("Image: " + (imageFile != null ? imageFile.getOriginalFilename() : "null"));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SongRequest songRequest = objectMapper.readValue(request, SongRequest.class);
            songRequest.setImageFile(imageFile);
            songRequest.setAudioFile(audioFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(songService.addSong(songRequest));
        } catch (Exception e) {
            log.error("Error adding song: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add song: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllSongs() {
        try {
            return ResponseEntity.ok(songService.getAllSongs());

        } catch (Exception e) {
            log.error("Error fetching songs: ", e);
            return ResponseEntity.ok(new SongListResponse(false, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeSongById(@PathVariable String id) {
        try {
            songService.removeSong(id);
            return ResponseEntity.ok("Song deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting song: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete song: " + e.getMessage());
        }
    }
}
