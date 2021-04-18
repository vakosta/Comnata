package tv.comnata.mainservice.entities.websocket;

public class RoomMessageVideo {
    private String roomId;
    private String senderId;
    private String action;
    private String data;

    public String getRoomId() {
        return roomId;
    }

    public String getSenderId() {
        return senderId;
    }

    public VideoActionType getAction() {
        try {
            return VideoActionType.valueOf(action.toLowerCase());
        } catch (IllegalArgumentException exception) {
            return VideoActionType.UNIDENTIFIED;
        }
    }

    public String getData() {
        return data;
    }

    enum VideoActionType {
        SEEK,
        PAUSE,
        RESUME,
        UNIDENTIFIED
    }
}
