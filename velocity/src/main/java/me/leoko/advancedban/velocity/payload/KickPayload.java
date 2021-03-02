package me.leoko.advancedban.velocity.payload;

import co.schemati.trevor.api.network.event.EventProcessor;
import co.schemati.trevor.api.network.event.NetworkEvent;
import co.schemati.trevor.api.network.payload.NetworkPayload;

public class KickPayload extends NetworkPayload<String> {

    private final String player;
    private final String reason;

    public KickPayload(String player, String reason) {
        this.player = player;
        this.reason = reason;
    }

    public String getPlayer() {
        return player;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public EventProcessor.EventAction<? extends NetworkEvent> process(EventProcessor eventProcessor) {
        return eventProcessor.onMessage(this);
    }
}
