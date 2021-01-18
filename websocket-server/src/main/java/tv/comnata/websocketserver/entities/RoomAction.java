package tv.comnata.websocketserver.entities;

public class RoomAction {
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

    public Action getAction() {
        try {
            return Action.valueOf(action.toLowerCase());
        } catch (IllegalArgumentException exception) {
            return Action.UNIDENTIFIED;
        }
    }

    public String getData() {
        return data;
    }

    enum Action {
        SEEK,
        PAUSE,
        RESUME,
        UNIDENTIFIED
    }
}
