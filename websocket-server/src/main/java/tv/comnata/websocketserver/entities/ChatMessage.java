package tv.comnata.websocketserver.entities;

public class ChatMessage {
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String senderName;
    private String recipientName;
    private String content;

    public String getId() {
        return id;
    }

    public String getChatId() {
        return chatId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getContent() {
        return content;
    }
}
