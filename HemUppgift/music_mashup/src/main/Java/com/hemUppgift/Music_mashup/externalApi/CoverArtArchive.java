package com.hemUppgift.Music_mashup.externalApi;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Talal Attar
 * @since 2021-06-05
 */
@Service
public class CoverArtArchive {
    private static final RestTemplate coverArtTemplate = new RestTemplate();


    public static String getImage(String id) {
        String coverArtArchiveUrl = "http://coverartarchive.org/release-group/" + id;

        try {
            var coverArtMap = coverArtTemplate.getForObject(coverArtArchiveUrl, Map.class);
            var images = (List<Map<String, Object>>) coverArtMap.get("images");
            if (images.isEmpty()) {
                return "";
            }
            return (String) images.get(0).get("image");


        } catch (RestClientException e) {

            return "";
        }
    }
}