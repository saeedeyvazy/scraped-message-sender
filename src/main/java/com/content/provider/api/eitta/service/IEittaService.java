package com.content.provider.api.eitta.service;

import com.content.provider.api.eitta.dto.EittaResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "eitta", url = "${eitta.url}/${eitta.api.token}")
public interface IEittaService {
    @PostMapping("/sendFile")
    EittaResponseDto
    sendMessage(@RequestParam(value = "chat_id") String chatId,
                @RequestParam(value = "parse_mode") String parseMode,
                @RequestParam String caption,
                @RequestParam String file,
                @RequestParam String pin,
                @RequestParam String date
    );
}
