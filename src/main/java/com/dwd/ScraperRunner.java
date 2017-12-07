package com.dwd;

import com.dwd.service.WikiScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScraperRunner implements CommandLineRunner {

    @Autowired
    WikiScraper wikiScraper;

    public static void main(String[] args) {
        SpringApplication.run(ScraperRunner.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        wikiScraper.start();
    }
}
