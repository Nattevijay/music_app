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
public class AlbumRequest {

    private String id;
    private String name;
    private String desc;
    private String bgColor;
    private MultipartFile imageFile;
}
