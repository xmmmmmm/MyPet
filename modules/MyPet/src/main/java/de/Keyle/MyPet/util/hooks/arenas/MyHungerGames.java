/*
 * This file is part of MyPet
 *
 * Copyright © 2011-2016 Keyle
 * MyPet is licensed under the GNU Lesser General Public License.
 *
 * MyPet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyPet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.Keyle.MyPet.util.hooks.arenas;

import de.Keyle.MyPet.MyPetApi;
import de.Keyle.MyPet.api.Configuration;
import de.Keyle.MyPet.api.entity.MyPet;
import de.Keyle.MyPet.api.event.MyPetCallEvent;
import de.Keyle.MyPet.api.player.MyPetPlayer;
import de.Keyle.MyPet.api.util.hooks.PluginHookManager;
import de.Keyle.MyPet.api.util.locale.Translation;
import me.kitskub.hungergames.HungerGames;
import me.kitskub.hungergames.api.GameManager;
import me.kitskub.hungergames.api.event.PlayerJoinGameEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MyHungerGames implements Listener {

    private static boolean active = false;
    private static GameManager gameManager;

    public static void findPlugin() {
        if (PluginHookManager.isPluginUsable("MyHungerGames", "me.kitskub.hungergames.HungerGames")) {
            Bukkit.getPluginManager().registerEvents(new MyHungerGames(), MyPetApi.getPlugin());
            gameManager = HungerGames.getInstance().getGameManager();
            MyPetApi.getLogger().warning("MyHungerGames hook activated.");
            active = true;
        }
    }

    public static boolean isInHungerGames(MyPetPlayer owner) {
        if (active) {
            try {
                return gameManager.getSpectating(owner.getPlayer()) != null || HungerGames.getInstance().getGameManager().getRawPlayingSession(owner.getPlayer()) != null;
            } catch (Exception e) {
                active = false;
            }
        }
        return false;
    }

    @EventHandler
    public void onJoinPvPArena(PlayerJoinGameEvent event) {
        if (active && Configuration.Hooks.DISABLE_PETS_IN_HUNGER_GAMES && MyPetApi.getPlayerManager().isMyPetPlayer(event.getPlayer())) {
            MyPetPlayer player = MyPetApi.getPlayerManager().getMyPetPlayer(event.getPlayer());
            if (player.hasMyPet() && player.getMyPet().getStatus() == MyPet.PetState.Here) {
                player.getMyPet().removePet(true);
                player.getPlayer().sendMessage(Translation.getString("Message.No.AllowedHere", player.getPlayer()));
            }
        }
    }

    @EventHandler
    public void onMyPetCall(MyPetCallEvent event) {
        if (active && Configuration.Hooks.DISABLE_PETS_IN_HUNGER_GAMES) {
            if (isInHungerGames(event.getOwner())) {
                event.setCancelled(true);
            }
        }
    }
}