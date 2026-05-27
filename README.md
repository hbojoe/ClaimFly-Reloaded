# ClaimFly Reloaded

ClaimFly Reloaded lets players fly inside GriefPrevention claims while keeping flight restricted everywhere else. It is built for servers that want claim-based utility flight without allowing global creative-style flight.

## Features

- Claim-based flight for GriefPrevention claims
- Flight in owned claims
- Optional flight in other players' claims when the player has both ClaimFly permission and GriefPrevention access trust
- Optional flight in admin claims for staff
- Automatic flight removal when a player leaves an allowed claim
- Claim boundary particles while flying
- Configurable messages, check interval, boundary distance, and storage backend
- YAML or SQLite player data storage
- Optional bStats metrics

## Requirements

- Minecraft/Paper `1.21.11`
- Java `21`
- GriefPrevention
- Vault

Install GriefPrevention and Vault before installing ClaimFly. ClaimFly declares both as required dependencies.

## Player Guide

Use:

```text
/claimfly
/cfly
```

Running the command toggles ClaimFly flight on or off. Flight only stays active while you are in a claim where you are allowed to fly.

### Where Flight Works

| Location | Allowed? |
| --- | --- |
| Your own claim | Yes, with `claimfly.use` |
| Another player's claim | Yes, only with `claimfly.claims.others` and GriefPrevention access trust |
| Another player's claim without access trust | No |
| Unclaimed land | No |
| Admin claim | Yes, only with `claimfly.claims.admin` |

For another player to fly in your claim, give them GriefPrevention access trust and make sure your server owner has granted them `claimfly.claims.others`.

## Commands

| Command | Permission | Description |
| --- | --- | --- |
| `/claimfly` | `claimfly.command` | Toggle claim flight on or off |
| `/cfly` | `claimfly.command` | Alias for `/claimfly` |
| `/claimfly boundary` | `claimfly.commands.boundary.toggle` | Toggle claim boundary particles |
| `/claimfly boundary <distance>` | `claimfly.commands.boundary.set` | Set your boundary particle view distance |
| `/claimfly reload` | `claimfly.commands.admin` | Reload the plugin config |
| `/claimfly checkinterval <ticks>` | `claimfly.commands.admin` | Change how often flight permissions are checked |
| `/claimfly version` | `claimfly.commands.admin` | Show the installed ClaimFly version |

## Permissions

| Permission | Default | Description |
| --- | --- | --- |
| `claimfly.*` | OP | Grants all ClaimFly permissions |
| `claimfly.use` | false | Allows the player to use claim-based flight |
| `claimfly.command` | false | Allows `/claimfly` and `/cfly` |
| `claimfly.commands.admin` | false | Allows admin commands |
| `claimfly.commands.boundary.toggle` | false | Allows toggling boundary particles |
| `claimfly.commands.boundary.set` | false | Allows changing boundary particle distance |
| `claimfly.claims.others` | false | Allows flight in other players' claims, but only when the player also has GriefPrevention access trust |
| `claimfly.claims.admin` | false | Allows flight in GriefPrevention admin claims |

## Server Owner Setup

1. Install GriefPrevention.
2. Install Vault.
3. Place the ClaimFly jar in your server's `plugins` folder.
4. Start or restart the server.
5. Edit `plugins/ClaimFly/config.yml` if needed.
6. Grant permissions with your permissions plugin.

Basic player permissions:

```text
claimfly.use
claimfly.command
```

To allow a player to fly in trusted claims owned by other players:

```text
claimfly.claims.others
```

The claim owner must also give that player GriefPrevention access trust. The ClaimFly permission alone is not enough.

Staff/admin claim permission:

```text
claimfly.claims.admin
```

## Configuration

Default config:

```yaml
check-interval: 5
flight-toggle-cooldown: 10
storage: "sqlite"
enable-metrics: true

boundary:
  show-distance: 10
  show-by-default: true

message:
  cannot-fly-on-server: "&cYou do not have permission to fly on this server!"
  cannot-fly-this-claim: "&cYou do not have permission to fly in this claim owned by &6%ClaimOwner%&c."
  cannot-fly-outside-claims: "&cYou cannot fly outside of claims!"
  claimfly-cmd-deny: "&cYou do not have permission to turn on flight!"
  flight-toggle-on: "&aFlight enabled."
  flight-toggle-off: "&cFlight disabled."
  dont-spam-space: "&cDo not spam space to glide. Flight disabled."
```

### Config Options

| Option | Description |
| --- | --- |
| `check-interval` | How often, in ticks, ClaimFly checks whether flying players are still allowed to fly |
| `flight-toggle-cooldown` | Minimum time between denied flight toggle attempts |
| `storage` | Player data storage type: `sqlite` or `yaml` |
| `enable-metrics` | Enables or disables bStats metrics |
| `boundary.show-distance` | Default distance for showing claim boundary particles |
| `boundary.show-by-default` | Whether boundary particles are enabled by default |

## Important Behavior

ClaimFly removes managed flight when a player leaves an allowed claim. This prevents players from keeping `allowFlight` outside claims and keeps normal survival fall damage behavior intact.

ClaimFly does not grant flight in unclaimed land.

GriefPrevention access trust by itself does not grant flight. For other-player claims, the player needs both:

```text
claimfly.claims.others
GriefPrevention access trust
```

## Building From Source

```text
mvn package
```

The compiled jar will be created in:

```text
target/
```

## License

This project is licensed under the GPL-3.0 license. See `LICENSE.txt` for details.
