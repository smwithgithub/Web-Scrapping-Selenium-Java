package com.codewithsm.webscrapingusingrpa.controller;

import com.codewithsm.webscrapingusingrpa.service.ScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ScrapperController {

    @Autowired
    private ScrapperService scraperService;

    @PostMapping("/scrapandsave")
    public String scrapeAndSaveData() {
        return scraperService.scrapeAndSaveData();
    }

}
