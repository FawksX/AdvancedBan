package me.leoko.advancedban.velocity.listener;

import co.schemati.trevor.api.TrevorAPI;
import co.schemati.trevor.api.TrevorService;
import co.schemati.trevor.velocity.platform.event.VelocityNetworkMessageEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.proxy.Player;
import me.leoko.advancedban.Universal;
import me.leoko.advancedban.velocity.VelocityMain;
import me.leoko.advancedban.velocity.cache.UsernameCache;
import me.leoko.advancedban.velocity.payload.ConnectionPayload;
import me.leoko.advancedban.velocity.payload.KickPayload;
import me.leoko.advancedban.velocity.payload.NotificationPayload;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.Optional;

public class PubSubMessageListener {

    private TrevorAPI trevorAPI = TrevorService.getAPI();

    @Subscribe
    public void onMessageReceive(VelocityNetworkMessageEvent event) {

        if(event.payload() instanceof KickPayload) {
            KickPayload payload = (KickPayload) event.payload();

            Universal.get().log("KICK PAYLOAD - " + payload.getPlayer() + " - " + payload.getReason());
            Optional<Player> player = VelocityMain.get().getProxy().getPlayer(payload.getPlayer());
            if(player.isPresent()) {
                player.get().disconnect(LegacyComponentSerializer.legacyAmpersand().deserialize(payload.getReason()));
            }
        }
        else if(event.payload() instanceof NotificationPayload) {
            NotificationPayload payload = (NotificationPayload) event.payload();
            for(Player player : VelocityMain.get().getProxy().getAllPlayers()) {
                if(Universal.get().getMethods().hasPerms(player, payload.getNotification())) {
                    Universal.get().getMethods().sendMessage(player, payload.getNotification());
                }
            }
        }
        else if(event.payload() instanceof ConnectionPayload) {
            ConnectionPayload payload = (ConnectionPayload) event.payload();
            if(payload.hasJoined()) {
                UsernameCache.add(payload.getPlayer());
            } else {
                UsernameCache.remove(payload.getPlayer());
            }
            Universal.get().getIps().remove(payload.getPlayer().toLowerCase());
            Universal.get().getIps().put(payload.getPlayer().toLowerCase(), payload.getAddress());
        }

    }

}
