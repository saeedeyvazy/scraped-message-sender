package com.content.provider.schedule;

import com.content.provider.model.ScrapedNews;
import com.content.provider.service.EittaMessageSenderService;
import com.content.provider.service.ScrapedNewsService;
import com.content.provider.service.TelegramMsgSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EittaScheduleTask {
    private ScrapedNewsService scrapedNewsService;
    private EittaMessageSenderService eittaMessageSenderService;
    private final TelegramMsgSenderService telegramMsgSenderService;

    private final Logger LOGGER = LoggerFactory.getLogger(EittaScheduleTask.class);
    @Autowired
    public EittaScheduleTask(ScrapedNewsService scrapedNewsService, EittaMessageSenderService eittaMessageSenderService, TelegramMsgSenderService telegramMsgSenderService) {
        this.scrapedNewsService = scrapedNewsService;
        this.eittaMessageSenderService = eittaMessageSenderService;
        this.telegramMsgSenderService = telegramMsgSenderService;
    }

    @Scheduled(cron = "${eitta.cron.job}")
    public void sendPhotoWithCaptionAndContentToEittaChannel() {
        List<ScrapedNews> unsentNews = scrapedNewsService.fetchEittaNotSentNews();
        LOGGER.info("Eitta Job Starting...");
        unsentNews
                .stream()
                .forEach(item -> {
                            eittaMessageSenderService.sendPhotoWithCaptionAndContentToChannel(item);
                        }
                );
    }

    @Scheduled(cron = "${telegram.cron.job}")
    public void sendPhotoWithCaptionAndContentToTelegramChannel() {
        List<ScrapedNews> unsentNews = scrapedNewsService.fetchTelegramNotSentNews();
        LOGGER.info("Telegram Schedule Starting...");
        unsentNews
                .stream()
                .forEach(item -> {
                            telegramMsgSenderService.sendMsg(item);
                        }
                );
    }
}
