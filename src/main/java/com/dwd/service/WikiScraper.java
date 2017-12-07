package com.dwd.service;

import com.dwd.model.Slot;
import com.dwd.model.Item;
import com.dwd.persistence.ItemRepository;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WikiScraper {

    private static final Logger logger = Logger.getLogger(WikiScraper.class);

    @Autowired
    private ItemRepository itemRepository;

    public void start() {
        logger.info("Wiki scraper starting");
        int scrapeCount = 0;
        for (Slot slot : Slot.values()) {
            List<String> scrapedItemNames = this.scrape(slot);

            logger.info(String.format("Loading items matching scraped items for %s slot", slot.name()));
            List<Item> itemsFromDb = itemRepository.findByNameIn(scrapedItemNames);
            logger.info(String.format("%s: %d scraped, %d retrived from db", slot.name(), scrapedItemNames.size(), itemsFromDb.size()));

            logger.info(String.format("Assigning %s slots to db items and saving down", slot.name()));
            itemsFromDb.forEach(item -> item.setSlot(slot));
            itemRepository.save(itemsFromDb);
            scrapeCount += scrapedItemNames.size();
        }
        logger.info("Total number of items scraped: " + scrapeCount);
        logger.info("Wiki scraper finished");
    }

    private List<String> scrape(Slot slot) {
        logger.info(String.format("Scraping items for %s slot", slot.name()));
        List<String> itemNames = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(slot.wikiUrl).get();

            Elements rows = doc.getElementsByTag("tr");

            //skip the header row, start at 1
            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                String itemName = row.getElementsByTag("a").get(0).html();
                itemNames.add(itemName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemNames;
    }

//    @Bean
//    WikiScraper wikiScraper() {
//        return new WikiScraper();
//    }
}
