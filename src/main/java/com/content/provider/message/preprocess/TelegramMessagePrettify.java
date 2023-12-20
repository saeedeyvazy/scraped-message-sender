package com.content.provider.message.preprocess;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("TelegramMessagePrettify")
public class TelegramMessagePrettify implements MessagePrettier {

    @Override
    public String prettifyMessage(String msgText, String title) {
        List<String> startpList = new ArrayList<>(Arrays.asList(msgText.split("STARTP")));

        startpList.removeIf(String::isEmpty);
        if( startpList.size() > 2 ) {
            startpList = new ArrayList<>(startpList.subList(0,2));
//            msgText = "STARTP".concat(startpList.get(0)).concat("STARTP").concat(startpList.get(1));
        }

        msgText = String.join("\n\uD83D\uDD39", startpList);
        return "\n*" + title + "*\n\n \uD83D\uDD39 " + msgText + "\n @havades\\_ruz\\_iran";
    }
}
