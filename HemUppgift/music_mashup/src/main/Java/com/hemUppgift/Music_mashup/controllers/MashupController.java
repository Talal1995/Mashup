package com.hemUppgift.Music_mashup.controllers;

import com.hemUppgift.Music_mashup.externalApi.Wikipedia;
import com.hemUppgift.Music_mashup.externalApi.MusicBrainz;
import com.hemUppgift.Music_mashup.externalApi.Wikidata;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author  Talal Attar
 * @version 1.0
 * @since   2021-06-05
 */

@RestController // it taking care of mapping request data to the defined request handler
@RequestMapping("/restApi/showArtists")
public class MashupController {

    @Autowired
    private MusicBrainz musicBrainzService;
    @Autowired
    private Wikidata wikidataService;
    @Autowired
    private Wikipedia wikipediaService;

    @GetMapping("/{id}")

/**
     Added rate limit, one request per second because MusicBrainz API
     supports only one request per second.
     see this: https://musicbrainz.org/doc/MusicBrainz_API#Application_rate_limiting_and_identification */

    @RateLimiter(name = "artistRateLimit", fallbackMethod = "artistFallBack")
    public ResponseEntity getArtist(@PathVariable String id) {    //5b11f4ce-a62d-471e-81fc-a69a8278c7da
        var artist = musicBrainzService.GetArtistByID(id);
        if (artist == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Write a valid id please!");
        }
        if (artist.getMbId().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The input is not an artist's id");
        }
        return ResponseEntity.status(HttpStatus.OK).body(artist);
    }

    public ResponseEntity artistFallBack(String name, io.github.resilience4j.ratelimiter.RequestNotPermitted ex) {
        System.out.println("No more calls are accepted, @RateLimit applied ");

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Retry-After", "1"); //Try one second later

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .headers(responseHeaders) //send retry header
                .body("No more calls are accepted, because many request");
    }
}
