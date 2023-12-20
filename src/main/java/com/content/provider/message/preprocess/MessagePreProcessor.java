package com.content.provider.message.preprocess;

public interface MessagePreProcessor {
    String preprocessMessageContent(String messageContent);
    String preprocessMessageTitle(String title);
}
