package com.hemUppgift.Music_mashup.thread;

import com.hemUppgift.Music_mashup.externalApi.CoverArtArchive;
import com.hemUppgift.Music_mashup.Model.Album;

import java.util.List;


/**
 * @author Talal Attar
 * @since 2021-06-05
 */

public class CoverArtRunnable implements Runnable {
    private String title;
    private String id;
    private List<Album> albums;

    public CoverArtRunnable(String title, String id, List<Album> albums) {
        this.title = title;
        this.id = id;
        this.albums = albums;
    }
    @Override
    public void run() {

        String image = CoverArtArchive.getImage(id);
        Album album = new Album(title, id, image);
        albums.add(album);
    }
}
