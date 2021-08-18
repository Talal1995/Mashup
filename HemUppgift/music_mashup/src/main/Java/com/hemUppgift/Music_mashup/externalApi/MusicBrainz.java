package com.hemUppgift.Music_mashup.externalApi;

import com.hemUppgift.Music_mashup.Model.Album;
import com.hemUppgift.Music_mashup.Model.Artist;
import com.hemUppgift.Music_mashup.thread.CoverArtRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author Talal Attar
 * @since 2021-06-05
 */
@Service
public class MusicBrainz {

    @Autowired // tells the application context to inject an instance of WikidataService here
    private Wikidata wikidataService;

    @Autowired
    private Wikipedia wikipediaService;


    private static final RestTemplate restTemplate = new RestTemplate();


    public Artist GetArtistByID(String id) {
        System.out.println(id);
        try {
            String MbApiJson = "http://musicbrainz.org/ws/2/artist/" + id + "?&fmt=json&inc=url-rels+release-groups";

            //This adding the header of User-Agent in compliance with the documentation
            // For more information please open this link https://musicbrainz.org/doc/MusicBrainz_API/Rate_Limiting#Provide_meaningful_User-Agent_strings
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "music_mashup/0.0.1 (talal.attar95@gmail.com)");
            HttpEntity entity = new HttpEntity(headers);

            var artistMap = restTemplate.getForEntity(MbApiJson, Map.class);

            Map body = artistMap.getBody();
            System.out.println(body);
            String mbId = (String) body.get("id");
            String name = (String) body.get("name");
            String country = (String) body.get("country");
            var relations = (List<Map<String, Object>>) body.get("relations");

            String description = getDescription(relations, name);
            var releaseGroups = (List<Map<String, Object>>) body.get("release-groups");
            var albums = getAlbums(releaseGroups);

            Artist artist = new Artist(mbId, name, description, country, albums);
            return artist;

        } catch (Exception e) {
            e.printStackTrace();
            // it catch the exception if string is not artist's mbid
            Artist artist = new Artist("", "", "", "", null);
            return artist;
        }
    }

    //This will get the description from Wikidata or Wikipedia
    private String getDescription(List<Map<String, Object>> relations, String name) {
        //String description;
        var wikipedia = relations.stream()
                .filter(o -> o.get("type").equals("wikipedia")).findFirst();

        if (wikipedia.isPresent()) {

            return wikipediaService.getDescription(name);
        } else {
            var wikidata = relations.stream()
                    .filter(o -> o.get("type").equals("wikidata")).findFirst();
            if (wikidata.isPresent()) {
                var wiki = wikidata.get();
                var qId = ((Map<String, String>) wiki.get("url")).get("resource").split("/")[4];
                return wikidataService.getDescription(qId);
            }
        }
        return null;
    }


    private List<Album> getAlbums(List<Map<String, Object>> releaseGroups) {
        List<Album> albums = new ArrayList<>();
        List<Thread> threadList = new ArrayList<>();

        for (Map<String, Object> a : releaseGroups) {
            String title = (String) a.get("title");
            String id = (String) a.get("id");

            // Using thread for every call to coverArtArchive api to get image for each album
            // because otherwise it would take very long time to get the URLs for all albums.
            // E.g. observed response time up to 40s during testing without threads.
            CoverArtRunnable runnable = new CoverArtRunnable(title, id, albums);
            Thread thread = new Thread(runnable);
            threadList.add(thread);
            thread.start();
        }
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        });
        Collections.reverse(albums);

        return albums;
    }
}
