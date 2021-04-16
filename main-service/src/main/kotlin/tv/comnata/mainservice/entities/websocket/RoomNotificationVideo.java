package tv.comnata.mainservice.entities.websocket;

import java.util.Date;

public class RoomNotificationVideo implements RoomNotification {
    private RoomNotificationType roomNotificationType;

    private RoomMessageVideo.VideoActionType videoActionType;
    private Date dateOfVideoAction;

    public RoomNotificationVideo(RoomMessageVideo.VideoActionType videoActionType, Date dateOfVideoAction) {
        this.roomNotificationType = RoomNotificationType.VIDEO;
        this.videoActionType = videoActionType;
        this.dateOfVideoAction = dateOfVideoAction;
    }
}
