package me.leoko.advancedban.velocity.listener;

import co.schemati.trevor.api.TrevorService;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import me.leoko.advancedban.Universal;
import me.leoko.advancedban.manager.UUIDManager;
import me.leoko.advancedban.velocity.cache.UsernameCache;
import me.leoko.advancedban.velocity.payload.ConnectionPayload;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ConnectionListenerVelocity {

  @Subscribe
  public void onLogin(LoginEvent event) {

    UsernameCache.add(event.getPlayer().getUsername());

    Player player = event.getPlayer();
    UUIDManager.get().supplyInternUUID(player.getUsername(), player.getUniqueId());
    String result = Universal.get().callConnection(player.getUsername(), player.getRemoteAddress().getAddress().getHostAddress());
    if (result != null) {
      event.setResult(ResultedEvent.ComponentResult.denied(LegacyComponentSerializer.legacyAmpersand().deserialize(result)));
    }

    if(Universal.isRedis()) {
      TrevorService.getAPI().getDatabaseProxy().post("advancedban:connection", new ConnectionPayload(event.getPlayer().getUsername(), event.getPlayer().getRemoteAddress().getHostName(), true));
    }
  }

  @Subscribe
  public void onLeave(DisconnectEvent event) {
    UsernameCache.remove(event.getPlayer().getUsername());
    TrevorService.getAPI().getDatabaseProxy().post("advancedban:connection", new ConnectionPayload(event.getPlayer().getUsername(), event.getPlayer().getRemoteAddress().getHostName(), false));
  }

}
