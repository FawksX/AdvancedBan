package me.leoko.advancedban.velocity.payload;

import co.schemati.trevor.api.network.event.EventProcessor;
import co.schemati.trevor.api.network.event.NetworkEvent;
import co.schemati.trevor.api.network.payload.NetworkPayload;

public class ConnectionPayload extends NetworkPayload<String> {

    private final String player;
    private final String address;
    private final boolean join;

    public ConnectionPayload(String player, String address, boolean join) {
        this.player = player;
        this.address = address;
        this.join = join;
    }

    @Override
    public EventProcessor.EventAction<? extends NetworkEvent> process(EventProcessor eventProcessor) {
        return eventProcessor.onMessage(this);
    }

    public String getPlayer() {
        return this.player;
    }

    public String getAddress() {
        return this.address;
    }

    public boolean hasJoined() {
        return this.join;
    }
}
