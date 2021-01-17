package tv.comnata.websocketserver.entities;

public class ChatNotification {
    private String id;
    private String senderId;
    private String senderName;

    public ChatNotification(String id, String senderId, String senderName) {
        this.id = id;
        this.senderId = senderId;
        this.senderName = senderName;
    }

    public String getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }
}
