package com.dwd.controller;

import com.dwd.AppConstants;
import com.dwd.model.osrsapi.Item;
import com.dwd.persistence.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("getIcons")
public class IconController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ItemRepository itemRepository;

    /**
     * Get a single image and save it
     * @param itemId the id of the image to be retrieved and saved
     */
    @RequestMapping(params = {"itemId"})
    public void get(@RequestParam("itemId") int itemId) {
        String urlString = AppConstants.ICON_API + "?id=" + itemId;
        try {
            URL url = new URL(urlString);
            BufferedImage image = ImageIO.read(url);
            File output = new File(AppConstants.ICON_PATH + itemId + ".gif");
            if (!output.exists()) {
                output.getParentFile().mkdirs();
                output.createNewFile();
            }
            ImageIO.write(image, "gif", output);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get a list of items from the database
     * Grab the icons for every image then save
     */
    @RequestMapping("/all")
    public void get() {
        List<Item> items = itemRepository.findAll();
        System.out.println(items.size());
        File dir = new File(AppConstants.ICON_PATH);
        
        if(!dir.exists()){
            dir.mkdirs();
        }

        for(Item item : items){
            try {
                BufferedImage img = ImageIO.read(item.getIcon());
                File file = new File(AppConstants.ICON_PATH + "/" + item.getId() + ".gif");

                if(!file.exists()){
                    file.createNewFile();
                }

                ImageIO.write(img, "gif", file);
                System.out.println(String.format("Item %s saved successfully", item.getName()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
