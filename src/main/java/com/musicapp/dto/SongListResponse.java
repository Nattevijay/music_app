package com.musicapp.dto;

import com.musicapp.document.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SongListResponse {

    private Boolean success;
    private List<Song> songs;

}
