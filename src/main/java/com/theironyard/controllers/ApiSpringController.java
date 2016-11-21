package com.theironyard.controllers;

import com.theironyard.entities.Beer;
import com.theironyard.services.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;

/**
 * Created by stevenburris on 11/21/16.
 */
@RestController
public class ApiSpringController {

    @Autowired
    BeerRepository beers;

    static final String QUOTE_URL = "http://gturnquist-quoters.cfapps.io/api/random";
    static final String REDDIT_URL = "https://www.reddit.com/user/%s/comments/.json";
    static final String BREWERY_URL = "http://api.brewerydb.com/v2/beer/random";
    static final String BREWERY_KEY = "042c563dcfe9e0ddb28e04511c2ee8d7";

    @RequestMapping(path = "/quote", method = RequestMethod.GET)
    public HashMap getQuote() {
        RestTemplate query = new RestTemplate();
        HashMap result = query.getForObject(QUOTE_URL, HashMap.class);
        String type = (String) result.get("type");
        if (type.equals("success")) {
            return (HashMap) result.get("value");
        }
        return null;
    }

    @RequestMapping(path = "/comments/{user}", method = RequestMethod.GET)
    public HashMap getComments(@PathVariable("user") String user) {
        RestTemplate query = new RestTemplate();
        String url = String.format(REDDIT_URL, user);
        HashMap result = query.getForObject(url, HashMap.class);
        return result;
    }

    @RequestMapping(path = "/beer", method = RequestMethod.GET)
    public HashMap getBeer() {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(BREWERY_URL)
                .queryParam("key", BREWERY_KEY);

        RestTemplate query = new RestTemplate();
        HashMap result = query.getForObject(builder.build().encode().toUri(), HashMap.class);
        String status = (String) result.get("status");
        if (status.equals("success")) {
            HashMap data = (HashMap) result.get("data");
            Beer beer = new Beer((String) data.get("id"), (String) data.get("name"), (String) data.get("description"));
            beers.save(beer);
            return data;
        }
        return null;
    }

    @RequestMapping(path = "/beers", method = RequestMethod.GET)
    public Iterable<Beer> getTheBeers() {
        return beers.findAll();
    }
}
