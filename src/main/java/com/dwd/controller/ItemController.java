package com.dwd.controller;

import com.dwd.AppConstants;
import com.dwd.model.ApiResponse;
import com.dwd.model.Item;
import com.dwd.persistence.ItemRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/getItems")
public class ItemController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ItemRepository itemRepository;

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Get one page worth of items beginning with a certain letter
     *
     * @param alpha the letter the items start with
     * @param page
     * @return a set of the retrieved items
     */
    @RequestMapping(params = {"alpha", "page"})
    public Set<Item> get(
            @RequestParam(value = "alpha", defaultValue = "a") String alpha,
            @RequestParam(value = "page", defaultValue = "1") String page) {
        Set<Item> items = request(alpha, page);
        if (items.isEmpty()) {
            System.out.println(String.format("API returned 0 items for alpha %s and page %s", alpha, page));
        } else {
            itemRepository.save(items);
        }
        return items;
    }

    /**
     * Get every item from the api and save to the database
     *
     * @return a set containing all the retrieved items
     */
    @RequestMapping("/all")
    public Set<Item> getAll() {
        //note category is always 1 for osrs, returns all items
        Set<Item> items = new HashSet<>();
        for (char alpha = 'a'; alpha <= 'z'; alpha++) {
            Set<Item> temp = recursiveFetch(new HashSet<>(), alpha, 1);
            items.addAll(temp);

            System.out.println(String.format("Saving alpha %s to db", alpha));
            itemRepository.save(temp);
        }
        return items;
    }

    /**
     * Get every data on every item beginning with a specified letter and
     * save to the database
     *
     * @param alpha the first letter of the items to save
     * @return a set of the retrieved items
     */
    @RequestMapping(params = "alpha")
    public Set<Item> get(@RequestParam("alpha") char alpha) {

        //note category is always 1 for osrs, returns all items
        Set<Item> items = new HashSet<>();
        items.addAll(recursiveFetch(new HashSet<>(), alpha, 1));

        System.out.println(String.format("Saving alpha %s to db", alpha));
        itemRepository.save(items);

        return items;
    }

    /**
     * Recursively call the API for a certain letter to get all of the items
     * beginning with that letter
     *
     * @param items a cumulative set of items beginning with that letter
     * @param alpha the first letter of the desired items
     * @param page
     * @return a set of retrieved items
     */
    private Set<Item> recursiveFetch(Set<Item> items, char alpha, int page) {

        Set<Item> temp = request(Character.toString(alpha), Integer.toString(page));

        if (temp.isEmpty()) {
            //have reached the last page so return the items fetched
            System.out.println(String.format("API returned 0 items for alpha %c and page %d", alpha, page));
            return items;
        } else {
            //add the retrieved items then fetch the next page
            items.addAll(temp);
            System.out.println(String.format("Items for alpha %c and page %d retrieved", alpha, page));
            return recursiveFetch(items, alpha, ++page);
        }
    }

    /**
     * Makes an API call to retrive items and parses the response into a list of Item objects
     *
     * @param alpha the first letter of the requested item names
     * @param page
     * @return a set of items received from the API
     * @throws HttpClientErrorException
     */
    private Set<Item> request(String alpha, String page) {
        String url = AppConstants.ITEM_API + "?category=1&alpha=" + alpha + "&page=" + page;
        System.out.println("Consuming " + url);
        try {
            //make the API call and retrive the item json string
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

            //API returns null body often but with a 200 response code
            //think it's probably rate limited, but check here and retry after timeout
            while (responseEntity.getBody() == null) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("alpha %s and page %s returned null, retrying", alpha, page));
                responseEntity = restTemplate.getForEntity(url, String.class);
            }

            //parse the response string to an object
            //have to do it the long way as the API response type is text/html not application/json
            ApiResponse parsedResponse = mapper.readValue(responseEntity.getBody(), ApiResponse.class);
            return parsedResponse.getItems();

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //return an empty set if something breaks
        return new HashSet<>();
    }
}
