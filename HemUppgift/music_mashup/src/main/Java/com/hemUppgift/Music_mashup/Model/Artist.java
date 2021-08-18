package com.hemUppgift.Music_mashup.Model;


import lombok.Data;


import java.util.List;

@Data
/**
 *
 * @author Talal Attar
 * @since 2021-06-04
 */
public class Artist {

    private String mbId;
    private String name;
    private String description;
    private String country;
    private List<Album> albums;

    public Artist(String mbId, String name, String description, String country, List<Album> albums) {
        this.mbId = mbId;
        this.name = name;
        this.description = description;
        this.country = country;
        this.albums = albums;
    }

    public Object getMbId() {
        return mbId;
    }
}
