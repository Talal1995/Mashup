package com.hemUppgift.Music_mashup.Model;

import lombok.Data;

/**
 * @author Talal Attar
 * @since 2021-06-04
 */
@Data
public class Album {
    private String title;
    private String id;
    private String image;


    public Album(String title, String id, String image) {
        this.title = title;
        this.id = id;
        this.image = image;
    }
}
