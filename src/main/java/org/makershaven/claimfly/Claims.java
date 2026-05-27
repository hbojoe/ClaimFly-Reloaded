package org.makershaven.claimfly;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.entity.Player;

class Claims {

    Claim getClaim(Player player) {
        return getClaim(player.getLocation());
    }

    private Claim getClaim(Location location){
        return GriefPrevention.instance.dataStore.getClaimAt(location, true, null);
    }

    boolean hasAccessTrust(Player player, Claim claim) {
        return claim != null && claim.checkPermission(player, ClaimPermission.Access, null) == null;
    }

    boolean isClaimOwner(Player player) {
        return isClaimOwner(player, getClaim(player));
    }

    boolean isClaimOwner(Player player, Claim claim) {
        return claim != null && player.getUniqueId().equals(claim.getOwnerID());
    }

    boolean isClaimOwner(Player player,Location location){
        return isClaimOwner(player, getClaim(location));
    }

    boolean isInClaim(Player player) {

        return getClaim(player) != null;
    }

    boolean isClaimed(Location location){

        return getClaim(location) != null;
    }

    boolean isInAdminClaim(Player player) {
        return isAdminClaim(getClaim(player));

    }

    boolean isAdminClaim(Claim claim) {
        return claim != null && claim.isAdminClaim();
    }

    boolean isAnAdminClaim(Location location){
        return isAdminClaim(getClaim(location));
    }

    String getOwnerName(Claim claim) {
        return claim != null ? claim.getOwnerName() : "Unknown";
    }

}

