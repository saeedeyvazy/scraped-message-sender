package com.content.provider.message.preprocess;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class EittaMessagePreProcessor implements MessagePreProcessor {
    @Value("${eitta.content.emoji}")
    private String contentEmoji;

    @Value("${eitta.title.emoji}")
    private String titleEmoji;
    @Value("${eitta.chat.id}")
    private String chatId;

    @Override
    public String preprocessMessageContent(String messageContent) {
        List<String> paragraphList = new ArrayList<String>(Arrays.asList(messageContent.split("STARTP")));
        paragraphList.removeIf(StringUtils::isEmpty);

        String[] preparedParagraphList = paragraphList.toArray(String[]::new);
        if (preparedParagraphList.length >= 2)
            preparedParagraphList = new String[]{contentEmoji.concat(preparedParagraphList[0]), preparedParagraphList[1]};
        return String.join(contentEmoji, preparedParagraphList).concat("@").concat(chatId);
    }

    @Override
    public String preprocessMessageTitle(String title) {
        return titleEmoji.concat(title).concat("\n\n");
    }
}
