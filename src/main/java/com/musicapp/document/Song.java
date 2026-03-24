package com.musicapp.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "songs")
public class Song {

    @Id
    @JsonProperty("_id")
    private String id;
    private String name;
    private String desc;
    private String album;
    private String image;
    private String file;
    private String duration;
}
