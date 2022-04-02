package de.gibmirrechte.tablist.listener;

import de.gibmirrechte.tablist.main.Main;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.Field;

public class JoinListener implements Listener {

    private static boolean isUpdating = false;

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent){
        if(!isUpdating){
            isUpdating = true;
            new Thread(() ->{
                while(true){
                    for(Player all : Bukkit.getOnlinePlayers()){
                        setTablist(all);

                        try {
                            Thread.sleep(Main.interval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
    
    public void setTablist(Player player){
        String formatedHeader = Main.stringHeader
                .replace("%maxplayers%", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                .replace("%onlineplayers%" , String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                .replace("%player%", player.getName())
                .replace("%world%", player.getWorld().getName())
                .replace("&", "§");

        String formatedFooter = Main.stringFooter
                .replace("%maxplayers%", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                .replace("%onlineplayers%" , String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                .replace("%player%", player.getName())
                .replace("%world%", player.getWorld().getName())
                .replace("&", "§");

        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" +
                formatedHeader +
                "\"}");
        IChatBaseComponent tabSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + formatedFooter + "\"}");

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

        try {
            Field field = packet.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packet, tabSubTitle);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
