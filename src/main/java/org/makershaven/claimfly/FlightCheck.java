package org.makershaven.claimfly;

import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

class FlightCheck {

    private Claims claims = new Claims();
    private FlightBoundary flightBoundary;
    private Plugin plugin;
    private final String FLIGHT_ALLOWED = "allow";
    private String noFlyOnServer;
    private String noFlyThisClaim;
    private String noFlyOutsideClaims;


    FlightCheck(ClaimFly plugin) {
        this.plugin = plugin;
        this.flightBoundary = new FlightBoundary(plugin);
        retrieveMessages();
    }
    String check(Player player) {
        if (hasNaturalFlight(player)) {
            return FLIGHT_ALLOWED;
        }

        Claim claim = claims.getClaim(player);

        if (!player.hasPermission("claimfly.use")) {
            return noFlyOnServer;
        }

        if (claim == null) {
            return noFlyOutsideClaims;
        }

        if (claims.isAdminClaim(claim)) {
            if (player.hasPermission("claimfly.claims.admin")) {
                flightBoundary.showFlightBoundaries(player);
                return FLIGHT_ALLOWED;
            }

            return noFlyThisClaim.replace("%ClaimOwner%", claims.getOwnerName(claim));
        }

        if (claims.isClaimOwner(player, claim)
                || (player.hasPermission("claimfly.claims.others") && claims.hasAccessTrust(player, claim))) {
            flightBoundary.showFlightBoundaries(player);
            return FLIGHT_ALLOWED;
        }

        return noFlyThisClaim.replace("%ClaimOwner%", claims.getOwnerName(claim));
    }

    private void retrieveMessages() {
        noFlyOnServer = plugin.getConfig().getString("message.cannot-fly-on-server");
        noFlyThisClaim = plugin.getConfig().getString("message.cannot-fly-this-claim");
        noFlyOutsideClaims = plugin.getConfig().getString("message.cannot-fly-outside-claims");

    }

    String getFLIGHT_ALLOWED() {
        return FLIGHT_ALLOWED;
    }

    static void disableManagedFlight(Player player) {
        if (player.isFlying()) {
            player.setFlying(false);
        }

        if (!hasNaturalFlight(player)) {
            player.setAllowFlight(false);
        }
    }

    static boolean hasNaturalFlight(Player player) {
        return player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR;
    }


}
