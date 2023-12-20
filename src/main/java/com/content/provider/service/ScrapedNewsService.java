package com.content.provider.service;

import com.content.provider.model.ScrapedNews;
import com.content.provider.repository.ScrapedNewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapedNewsService {
    private ScrapedNewRepository scrapedNewRepository;

    @Autowired
    public ScrapedNewsService(ScrapedNewRepository scrapedNewRepository) {
        this.scrapedNewRepository = scrapedNewRepository;
    }

    public List<ScrapedNews> fetchEittaNotSentNews() {
        return scrapedNewRepository.findAllByEittaStatusIsNullOrderByIdDesc();
    }

    public List<ScrapedNews> fetchTelegramNotSentNews() {
        return scrapedNewRepository.findAllByTelegramStatusIsNullOrderByIdDesc();
    }
}
