package com.content.provider.api.telegram;

import com.content.provider.api.eitta.dto.TelegramResponseDto;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "telegramSendMsg", url = "${telegram.api}")
public interface TelegramFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "${telegram.send.photo}")
    TelegramResponseDto sendPhotoWithCaption(@RequestParam(value = "chat_id") String chatId
            , @RequestParam(value = "caption") String caption
            , @RequestParam(value = "photo") String photo
            , @RequestParam(value = "parse_mode", defaultValue = "MARKDOWN", required = false) String parseMode);

    @RequestMapping(method = RequestMethod.POST, value = "sendVideo")
    TelegramResponseDto sendVideoWithCaption(@RequestParam(value = "chat_id") String chatId
            , @RequestParam(value = "caption") String caption
            , @RequestParam(value = "video") String video
            , @RequestParam(value = "parse_mode", defaultValue = "MARKDOWN", required = false) String parseMode);
}
