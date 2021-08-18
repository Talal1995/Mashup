package com.hemUppgift.Music_mashup.externalApi;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Talal Attar
 * @since 2021-06-05
 */
@Service
public class Wikipedia {
    private static final RestTemplate wikipediaRestTemplate = new RestTemplate();


    public String getDescription(@PathVariable String title) {
        String wikipediaApiJson =
                "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles=" + title;
        try {
            var wikipediaMap = wikipediaRestTemplate.getForObject(wikipediaApiJson, Map.class);

            var queryMap = (Map<String, Object>) wikipediaMap.get("query");
            var pageMap = ((Map<String, Object>) queryMap.get("pages"))
                    .entrySet()
                    .stream().map(o -> o.getValue())
                    .findFirst().get();
            String description = (String) ((Map<String, Object>) pageMap).get("extract");
            return description;
        } catch (RestClientException e) {
            e.printStackTrace();
            return "";
        }
    }
}