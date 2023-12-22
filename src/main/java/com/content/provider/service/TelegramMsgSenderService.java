package com.content.provider.service;

import com.content.provider.api.eitta.dto.ResponseMessage;
import com.content.provider.api.eitta.dto.TelegramResponseDto;
import com.content.provider.api.telegram.TelegramFeignClient;
import com.content.provider.message.preprocess.MessagePrettier;
import com.content.provider.model.ScrapedNews;
import com.content.provider.repository.ScrapedNewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TelegramMsgSenderService {

    private final String chatId;
    private final TelegramFeignClient telegramFeignClient;
    private final MessagePrettier messagePrettier;
    private final ScrapedNewRepository scrapedNewRepository;

    private  final Logger LOGGER = LoggerFactory.getLogger(TelegramMsgSenderService.class);

    public TelegramMsgSenderService(TelegramFeignClient telegramFeignClient,
                                    @Value("${telegram.chat.id}") String chatId, @Qualifier("TelegramMessagePrettify") MessagePrettier messagePrettier, ScrapedNewRepository scrapedNewRepository) {

        this.telegramFeignClient = telegramFeignClient;
        this.chatId = chatId;
        this.messagePrettier = messagePrettier;
        this.scrapedNewRepository = scrapedNewRepository;

    }

    public void sendMsg(ScrapedNews scrapedNews) {
        String msgText = scrapedNews.getText();
        String title = scrapedNews.getTitle();
        String file = scrapedNews.getImg();

        ResponseMessage response;
        try {
            if (file.contains(".mov") || file.contains(".mp4")) {
                response = telegramFeignClient.sendVideoWithCaption(chatId, messagePrettier.prettifyMessage(msgText, title), file, "MARKDOWN");
            } else
                response = telegramFeignClient.sendPhotoWithCaption(chatId, messagePrettier.prettifyMessage(msgText, title), file, "MARKDOWN");
            scrapedNews.setTelegramStatus(response.isOk() ? "S" : null);
        } catch (Exception e) {
            LOGGER.error("Sending message to telegram with message id ".concat(scrapedNews.getId().toString()).concat(" Failed"));
            e.printStackTrace();
        } finally {
            scrapedNews.setTelegramStatus("S");
            scrapedNewRepository.save(scrapedNews);
        }


    }

}
