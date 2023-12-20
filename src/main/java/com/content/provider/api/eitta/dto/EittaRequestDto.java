package com.content.provider.api.eitta.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EittaRequestDto {
    private String caption;
    private String date;
    private String chatId;
    private String pin;
    private String parseMode;
    private String file;

    public EittaRequestDto() {
        this.date = "0";
        this.pin = "off";
        this.parseMode = "html";
    }
}
