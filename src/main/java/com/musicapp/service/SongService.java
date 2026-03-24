package com.musicapp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.musicapp.document.Song;
import com.musicapp.dto.SongListResponse;
import com.musicapp.dto.SongRequest;
import com.musicapp.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongService {

    private final SongRepository songRepository;
    private final Cloudinary cloudinary;

    public Song addSong(SongRequest request) throws IOException {
        Map<String, Object> audioUploadFile = cloudinary.uploader().upload(request.getAudioFile().getBytes(), ObjectUtils.asMap("resource_type","video"));
        Map<String, Object> audioImageFile = cloudinary.uploader().upload(request.getImageFile().getBytes(), ObjectUtils.asMap("resource_type","image"));
        log.info("Secure URL: {}", audioUploadFile.get("secure_url"));
        log.info("Audio file uploaded to Cloudinary: {}", audioUploadFile);

        Double durationSeconds = (Double) audioUploadFile.get("duration");
        String duration = formatDuration(durationSeconds);

        Song song = Song.builder()
                .name(request.getName())
                .desc(request.getDesc())
                .album(request.getAlbum())
                .image((String) audioImageFile.get("secure_url"))
                .file((String) audioUploadFile.get("secure_url"))
                .duration(duration)
                .build();

        return songRepository.save(song);
    }
    private String formatDuration(Double durationSeconds) {
        if (durationSeconds == null) return "00:00";
        int minutes = (int) (durationSeconds / 60);
        int seconds = (int) (durationSeconds % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public SongListResponse getAllSongs() {
        return new SongListResponse(true, songRepository.findAll());
    }

    public Boolean removeSong(String id) {
        songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song not found with id: " + id));
        songRepository.deleteById(id);
        return true;
    }
}
