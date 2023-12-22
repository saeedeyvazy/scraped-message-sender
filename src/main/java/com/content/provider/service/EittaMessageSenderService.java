package com.content.provider.service;

import com.content.provider.api.eitta.dto.EittaRequestDto;
import com.content.provider.api.eitta.service.IEittaService;
import com.content.provider.message.preprocess.MessagePreProcessor;
import com.content.provider.model.ScrapedNews;
import com.content.provider.repository.ScrapedNewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EittaMessageSenderService {
    private final ScrapedNewRepository scrapedNewRepository;
    private final IEittaService eittaService;
    private final MessagePreProcessor messagePreProcessor;
    private final Logger LOGGER = LoggerFactory.getLogger(EittaMessageSenderService.class);
    @Value("${eitta.chat.id}")
    private String chatId;

    @Autowired
    public EittaMessageSenderService(ScrapedNewRepository scrapedNewRepository, IEittaService eittaService, MessagePreProcessor messagePreProcessor) {
        this.scrapedNewRepository = scrapedNewRepository;
        this.eittaService = eittaService;
        this.messagePreProcessor = messagePreProcessor;
    }

    public void sendPhotoWithCaptionAndContentToChannel(ScrapedNews scrapedNews) {
        String preparedTitle = messagePreProcessor.preprocessMessageTitle(scrapedNews.getTitle());
        String preparedContent = messagePreProcessor.preprocessMessageContent(scrapedNews.getText());

        EittaRequestDto eittaRequestDto = new EittaRequestDto();
        eittaRequestDto.setCaption(preparedTitle.concat(preparedContent));
        eittaRequestDto.setFile(scrapedNews.getImg());
        eittaRequestDto.setChatId(chatId);

        try {
            eittaService
                    .sendMessage(eittaRequestDto.getChatId(),
                            eittaRequestDto.getParseMode(),
                            eittaRequestDto.getCaption(),
                            eittaRequestDto.getFile(),
                            eittaRequestDto.getPin(), eittaRequestDto.getDate());
        } catch (Exception e) {
            LOGGER.error("Sending message t eitta with message id ".concat(scrapedNews.getId().toString()).concat(" Failed"));
            e.printStackTrace();
        } finally {
            scrapedNews.setEittaStatus("S");
            scrapedNewRepository.save(scrapedNews);
        }


    }

}
