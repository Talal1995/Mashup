package com.hemUppgift.Music_mashup.externalApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Talal Attar
 * @since 2021-06-05
 */
@Service
public class Wikidata {
    private static final RestTemplate wikidataRestTemplate = new RestTemplate();

    @Autowired
    private Wikipedia wikipediaService;


    public String getDescription( @PathVariable  String qId) {
        String wikidataApiJson = "https://www.wikidata.org/w/api.php?action=wbgetentities&ids="
                + qId + "&format=json&props=sitelinks";

        try {
            var wikidataMap = wikidataRestTemplate.getForObject(wikidataApiJson, Map.class);
            var entities = (Map<String, Object>) wikidataMap.get("entities");
            var siteLinks = (Map<String, Object>) ((Map<String, Object>) entities.get(qId)).get("sitelinks");
            var enWiki = (Map<String, Object>) siteLinks.get("enwiki");
            String title = (String) enWiki.get("title");
            String description = wikipediaService.getDescription(title);
            return description;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}