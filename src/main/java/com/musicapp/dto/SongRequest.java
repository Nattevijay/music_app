package com.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SongRequest {

    private String id;
    private String name;
    private String desc;
    private String album;
    private MultipartFile audioFile;
    private MultipartFile imageFile; // Duration in seconds
}
