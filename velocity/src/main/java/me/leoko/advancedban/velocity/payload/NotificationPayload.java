package me.leoko.advancedban.velocity.payload;

import co.schemati.trevor.api.network.event.EventProcessor;
import co.schemati.trevor.api.network.event.NetworkEvent;
import co.schemati.trevor.api.network.payload.NetworkPayload;

public class NotificationPayload extends NetworkPayload<String> {

    private final String perm;
    private final String str;

    public NotificationPayload(String perm, String str) {
        this.perm = perm;
        this.str = str;
    }

    public String getPerm() {
        return this.perm;
    }

    public String getNotification() {
        return this.str;
    }

    @Override
    public EventProcessor.EventAction<? extends NetworkEvent> process(EventProcessor eventProcessor) {
        return eventProcessor.onMessage(this);
    }
}
