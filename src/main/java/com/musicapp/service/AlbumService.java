package com.musicapp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.musicapp.document.Album;
import com.musicapp.dto.AlbumListResponse;
import com.musicapp.dto.AlbumRequest;
import com.musicapp.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final Cloudinary cloudinary;

    public Album addAlbum(AlbumRequest request) throws IOException {
        Map<String, Object> imageFileUpload = cloudinary.uploader().upload(request.getImageFile().getBytes(), ObjectUtils.asMap("resource_type","image"));
        log.info("Album image uploaded to Cloudinary: {}", imageFileUpload);
        Album newAlbum = Album.builder()
                .name(request.getName())
                .desc(request.getDesc())
                .bgColor(request.getBgColor())
                .imageUrl(imageFileUpload.get("secure_url").toString())
                .build();
        log.info("New album added: {}", newAlbum);
        return albumRepository.save(newAlbum);
    }

    public AlbumListResponse getAllAlbums() {
        log.info("Fetching all albums");
        return new AlbumListResponse(true, albumRepository.findAll());
    }

    public Boolean removeAlbum(String id) {
        log.info("Removing album with id: {}", id);
        if (albumRepository.existsById(id)) {
            albumRepository.deleteById(id);
            log.info("Album removed successfully");
            return true;
        } else {
            log.warn("Album with id {} not found", id);
            return false;
        }

    }
}
