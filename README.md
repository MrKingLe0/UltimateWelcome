<img width="1774" height="887" alt="UltimateWelcome" src="https://github.com/user-attachments/assets/aefacb66-d0b9-438c-b98e-300d315e32a6" />

A powerful, lightweight and fully configurable Minecraft Paper plugin that enhances your server's join/leave experience with ranks, messages, commands execution, sounds, titles, fireworks, and a full welcome bounty system.

Supports 1.21+

---

## 🚀 Features

✔ Rank-based welcome messages  
✔ Join & leave broadcasts  
✔ Custom join titles  
✔ Sounds & fireworks on join  
✔ Console & player join commands  
✔ Rank-based join commands  
✔ Welcome bounty system (reward players for welcoming others)  
✔ Fully customizable config  
✔ Placeholder support like `%player%` and `%target%`  

---

## 📦 Installation

1. Download `UltimateWelcome.jar`
2. Place it into your `plugins/` folder
3. Restart your server
4. Edit `config.yml`
5. Enjoy!

---

## ⚙ Commands

| Command | Description |
|--------|-------------|
| `/wb <player>` | Welcome another player |
| `/uwreload` | Reload configuration |
| `/uw forcewelcome <player>` | Force uses wb command, bypassing any restriction(for testing) |
| `/uw checkcooldown <player>` | Checks the coodown for using /wb commnad on a certain player(coming soon) |
| `/uw welcomeself` | Allows players to welcome them selfs(for testing) |

---

## 🎯 Permissions

| Permission | Description |
|------------|-------------|
| `uw.reload` | Allows plugin reload command |
| `uw.admin` | Allows using /uw command |

---

## 💡 Placeholders

You can use these in certain messages or commands on config.yml file:

- `%player%` → Player name  
- `%target%` → Target player (welcome bounty)  
- `%amount%` → Reward amount  
- `%currency%` → Currency name  

---

## 🎉 Features Overview

### 👋 Join System
- Rank-based join messages
- Titles on join
- Sounds and fireworks
- Console/player commands

### 🚪 Leave System
- Custom leave broadcasts per rank
- Optional leave commands

### 💰 Welcome Bounty System
Encourages player interaction:

- Reward players for welcoming others
- Cooldowns to prevent abuse
- Rank-based rewards
- Broadcast + personal messages
- Sound effects

---
<div>
<details>
<summary align="center">[ 📄 Click to view config.yml ]</summary>

```yaml

#=====================================================================
#  __  ______  _            __        _      __    __                  
# / / / / / /_(_)_ _  ___ _/ /____   | | /| / /__ / /______  __ _  ___ 
#/ /_/ / / __/ /  ' \/ _ `/ __/ -_)  | |/ |/ / -_) / __/ _ \/  ' \/ -_)
#\____/_/\__/_/_/_/_/\_,_/\__/\__/   |__/|__/\__/_/\__/\___/_/_/_/\__/ 
#                                                                      
#======================================================================
# Thanks for downloading UltimateWelcome V1.1!
# If you have any questions or need help, feel free to join the support discord server: https://discord.gg/6gCFHFHsbD

# Enable or disable the entire welcome plugin
enabled: true

# Rank priority order (highest will be used if a player has multiple ranks).
# Make sure to include all ranks you want to use in the welcome messages.
rank-order:
  - owner
  - admin
  - moderator
  - mvp
  - vip
  - default


#===================================================================
#                        JOIN SYSTEM
#===================================================================

# ------------ COLOR FORMATING ---------------

# You can use the following color and formatting codes in messages:
# Legacy Colors:  &0-&f  (e.g., &cRed, &aGreen)
# Formatting:     &l Bold, &o Italic, &n Underline, &m Strikethrough, &r Reset
# Legacy Hex:     &x&R&R&G&G&B&B  (e.g., &x&F&F&0&0&0&0 = #FF0000)
# Modern Hex:     &#RRGGBB  (e.g., &#FF0000Red)
# MiniMessage:    <red>text, <gradient:red:blue>text</gradient>, <rainbow>text</rainbow>


# Player Will see this message on that after they join. There's a 1 second delay before the message is sent to the player. You can use %player% as a placeholder for the player's name.
# If not set, it will use default messages in the welcome-messages.
welcome-messages:
  enabled: true
  messages:
    owner:
      - "========================================="
      - "                <b><gradient:#FF0000:#9400FF>sᴇʀᴠᴇʀ_ɴᴀᴍᴇ</gradient></b>"
      - "            &4&lᴡᴇʟᴄᴏᴍᴇ &f%player% &4&l!"
      - " &cʜᴏᴘᴇ ʏᴏᴜ ʜᴀᴠᴇ ᴀɴ &6ᴀᴍᴀᴢɪɴɢ &cʀᴇsᴛ ᴏғ ʏᴏᴜʀ sᴛᴀʏ!"
      - ""
      - "          &bᴡᴇʙsɪᴛᴇ: &fwww.example.com"
      - "         &9ᴅɪsᴄᴏʀᴅ: &fdiscord.gg/example"
      - " <gradient:#FF5555:#FFAA00:#FFFF55>✨ ʏᴏᴜ ᴀʀᴇ ᴜsɪɴɢ ᴜʟᴛɪᴍᴀᴛᴇᴡᴇʟᴄᴏᴍᴇ V1.1 ✨</gradient> <gray>by <gradient:#55FF55:#00AA00>KingLe0</gradient></gray>"
      - "========================================="
    vip:
      - "========================================="
      - "                <b><gradient:#FF0000:#9400FF>sᴇʀᴠᴇʀ_ɴᴀᴍᴇ</gradient></b>"
      - ""
      - "          <b><gradient:#FF0000:#0300FF>ᴡᴇʟᴄᴏᴍᴇ <color:#FFFF00>%player%</color> <gradient:#FF0000:#0300FF>!</gradient></b>"
      - "    <color:#999999>ᴡᴇ ʜᴀᴠᴇ ʙᴇᴇᴅ ᴡᴀɪᴛɪɴɢ ᴊᴜsᴛ ꜰᴏʀ ʏᴏᴜ!</color>"
      - ""
      - "          &bᴡᴇʙsɪᴛᴇ: &fwww.example.com"
      - "        &9ᴅɪsᴄᴏʀᴅ: &fdiscord.gg/example"
      - "========================================="
    default:
      - "========================================="
      - "                <b><gradient:#FF0000:#9400FF>sᴇʀᴠᴇʀ_ɴᴀᴍᴇ</gradient></b>"
      - "           &d&lᴡᴇʟᴄᴏᴍᴇ &f%player% &d&l!"
      - "       &9ʜᴏᴘᴇ ʏᴏᴜ ʜᴀᴠᴇ ᴀɴ &aᴀᴍᴀᴢɪɴɴɢ &9sᴛᴀʏ!"
      - ""
      - "          &bᴡᴇʙsɪᴛᴇ: &fwww.example.com"
      - "         &9ᴅɪsᴄᴏʀᴅ: &fdiscord.gg/example"
      - "========================================="

# Broadcast messages to all players when someone joins based on their rank. You can use %player% as a placeholder for the player's name.
welcome-broadcasts:
  enabled: true
  messages:
    owner:
      - "<b><gradient:#FF0000:#0300FF>ᴛʜᴇ ᴏᴡɴᴇʀ %player% ᴊᴏɪɴᴇᴅ ᴛʜᴇ sᴇʀᴠᴇʀ</gradient></b>"
    vip:
      - "&a&lVIP &e%player% &aᴊᴏɪɴᴇᴅ ᴛʜᴇ sᴇʀᴠᴇʀ!"
      - "&f[&a!&f] &eᴛʏᴘᴇ &f/wb <ᴘʟᴀʏᴇʀɴᴀᴍᴇ> &eᴛᴏ ᴡᴇʟᴄᴏᴍᴇ ᴛʜᴇ ᴘʟᴀʏᴇʀ ᴀɴᴅ ʀᴇᴄɪᴠᴇ &6EXTRA &eʀᴇᴡᴀʀᴅs!"
    default:
      - "&f[&a+&f] &d&l&f%player% &dᴊᴏɪɴᴇᴅ ᴛʜᴇ sᴇʀᴠᴇʀ!"
      - "&eᴛʏᴘᴇ &f/wb <ᴘʟᴀʏᴇʀɴᴀᴍᴇ> &eᴛᴏ ᴡᴇʟᴄᴏᴍᴇ ᴛʜᴇ ᴘʟᴀʏᴇʀ ᴀɴᴅ ʀᴇᴄɪᴠᴇ ʀᴇᴡᴀʀᴅs!"


# These commands will be executed when a player joins. You can use %player% as a placeholder for the player's name.
# The command will be executed by the console if it starts with [CONSOLE],
# or by the player if it starts with [PLAYER]. If no prefix is provided, it will default to console execution.
on-join-commands:
  - "[CONSOLE] say ᴡᴇʟᴄᴏᴍᴇ %player%!"

rank-join-commands:
  enabled: true
  commands:
    owner:
      - "[CONSOLE] say OWNER %player% JOINED!"
      - "[PLAYER] warp ownerroom"

    vip:
      - "[CONSOLE] eco give %player% 100"

    default:
      - "[PLAYER] spawn"

# Title on join settings
title:
  enabled: true
  main: "&5&lᴡᴇʟᴄᴏᴍᴇ %player%!"
  sub: "&9&lᴇɴᴊᴏʏ ʏᴏᴜʀ sᴛᴀʏ!"
  # Durations are in seconds, Defaults: stay=3, fade-in=1, fade-out=2
  stay: 3
  fade-in: 1
  fade-out: 2

# Sound on join settings
sound:
  enabled: true
  type: "entity.player.levelup"
  # Some example sound types(capitalization doesn't matter):
  # ENTITY_PLAYER_LEVELUP
  # BLOCK_NOTE_BLOCK_PLING
  # ITEM_FIREWORK_ROCKET_LAUNCH
  # ENTITY_FIREWORK_ROCKET_BLAST
  volume: 1.0
  pitch: 1.0

# Firework on join settings
firework:
  enabled: true
  delay: 20 # 1 second(20 ticks)

  power: 1

  colors:
    - "PURPLE"
    - "BLUE"

  fade-colors:
    - "CYAN"

  type: "BALL"

# JOIN BOSSBAR

bossbar:
  enabled: true
  # How long the bossbar stays visible (in seconds)
  duration: 20
  # Bossbar text (supports %player% placeholder and all color formats)
  text: "<gradient:#FF0000:#FFAA00>✨ ᴡᴇʟᴄᴏᴍᴇ %player%! ✨</gradient>"
  # Bossbar color (PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE)
  color: "PURPLE"
  # Bossbar style (SOLID, SEGMENTED_6, SEGMENTED_10, SEGMENTED_12, SEGMENTED_20)
  style: "SOLID"
  
  # Loop color settings
  loop-color:
    enabled: true
    # How often the color changes (in seconds)
    interval: 1
    # Colors to cycle through
    colors:
      - "PURPLE"
      - "BLUE"
      - "GREEN"
      - "YELLOW"
      - "RED"

#===================================================================
#                        LEAVE SYSTEM
#===================================================================

# Broadcast messages to all players when someone leaves based on their rank.
# If rank-specific messages are not set, it will use the default messages.
leave-broadcasts:
  enabled: true
  messages:
    owner:
      - "&f[&4-&f] &4&lOWNER %player% &7ʟᴇꜰᴛ ᴛʜᴇ sᴇʀᴠᴇʀ!"
    vip:
      - "&f[&4-&f] &d&lVIP %player% &7ʟᴇꜰᴛ ᴛʜᴇ sᴇʀᴠᴇʀ!"
    default:
      - "&f[&c-&f] &7%player% &7ʟᴇꜰᴛ ᴛʜᴇ sᴇʀᴠᴇʀ!"

# Commands executed when player leaves (all ranks).
on-leave-commands:
#  - "[CONSOLE] say %player% ʟᴇꜰᴛ ᴛʜᴇ sᴇʀᴠᴇʀ"

leave-rank-commands:
  enabled: false
  commands:
    owner:
#      - "[CONSOLE] say OWNER %player% LEFT"
    vip:
#      - "[CONSOLE] eco take %player% 50"
    default:
#      - "[PLAYER] spawn"


#===================================================================
#                        Welcome bounty
#===================================================================

# give players rewards for welcoming others back to the server.
# This encourages players to welcome back returning players and creates a more friendly community.
# You can customize the rewards, cooldowns, and messages for this feature.
welcome-bounty:
  # enable or disable the welcome bounty system
  enabled: true
  # how long after a player joins that they can be welcomed by others.
  expiry-seconds: 60
  # how long a player must wait before they can welcome the same player again and receive rewards again.
  reward-cooldown-seconds: 1800
  # The name of the currency used for rewards. You can use this in messages with the %currency% placeholder below.
  currency-name: "ᴍᴏɴᴇʏ"
  # Main reward command with placeholders
  reward-command: "[CONSOLE] eco give %player% %amount%"

  # Rank-specific rewards (optional)
  rewards:
    owner:
      amount: 500  # money amount, can be used in the reward-command with %amount% placeholder
      wb-commands: # additional commands to run when welcoming someone of this rank (optional)
        - "[CONSOLE] eco give %target% 500"
        - "[CONSOLE] give %target% diamond 3"
    vip:
      amount: 150
      reward-command: "[CONSOLE] eco give %player% %amount%" # You can override the main reward command for specific ranks.
      wb-commands:
        - "[CONSOLE] give %target% diamond 1"
    mvp:
      amount: 200
      wb-commands:
        - "[CONSOLE] eco give %target% 200"
        - "[CONSOLE] give %target% diamond 2"
    default:
      amount: 50

  # General welcome back commands (optional)
  wb-commands:
  #  - "[CONSOLE] say %player% ᴊᴜsᴛ ᴡᴇʟᴄᴏᴍᴇᴅ %target%!"

  broadcast-message:
    - "&f%player% &dᴡᴇʟᴄᴏᴍᴇᴅ &b%target% &dᴛᴏ ᴛʜᴇ sᴇʀᴠᴇʀ ᴀɴᴅ &9ʀᴇᴄɪᴠᴇᴅ ʀᴇᴡᴀʀᴅs!"

  # This will be sent to the player after they welcome someone.
  welcomer-message:
    - "&b&l                    ᴀᴡsᴏᴍᴇ!"
    - "&bʏᴏᴜ ᴡᴇʟᴄᴏᴍᴇᴅ &9%target% &bᴀɴᴅ ʀᴇᴄᴇɪᴠᴇᴅ &d%amount% %currency%&a!"

  # This will be sent to the welcomed player after they are welcomed by someone.
  welcomed-message:
    - "&e&l                    sᴇʀᴠᴇʀ"
    - "%player% &dᴡᴇʟᴄᴏᴍᴇᴅ ʏᴏᴜ ᴀɴᴅ ʀᴇᴄᴇɪᴠᴇᴅ ᴀ ʀᴇᴡᴀʀᴅ!"

  # This will be heard by the player after they welcome someone.
  sound:
    enabled: true
    type: "ENTITY_PLAYER_LEVELUP"
    volume: 1.0
    pitch: 1.0

```
</details>
</div>  

<div>
<details>
<summary align="center">[ 📄 Click to view settings-messages.yml ]</summary>

```yaml

# This is the language file for some setting messages.
# You can customize the messages for different languages here.
# The keys in this file correspond to the keys used in the config.yml file for settings messages.
# You can use color codes and placeholders in these messages as well.

plugin-reload:
  success: "<gradient:#55FF55:#00AA00>✅ ᴘʟᴜɢɪɴ ʀᴇʟᴏᴀᴅᴇᴅ sᴜᴄᴄᴇssꜰᴜʟʟʏ!</gradient>"
  error: "<gradient:#FF5555:#AA0000>❌ ᴀɴ ᴇʀʀᴏʀ ᴏᴄᴄᴜʀʀᴇᴅ ᴡʜɪʟᴇ ʀᴇʟᴏᴀᴅɪɴɢ! ᴄʜᴇᴄᴋ ᴄᴏɴsᴏʟᴇ ꜰᴏʀ ᴅᴇᴛᴀɪʟs.</gradient>"


uw-help:
  header: "<gradient:#FFAA00:#FF5500>━━━━━━━━ <bold>ᴡᴇʟᴄᴏᴍᴇ ᴄᴏᴍᴍᴀɴᴅs</bold> ━━━━━━━━</gradient>"
  wb: "<yellow>/ᴡʙ <ᴘʟᴀʏᴇʀ></yellow> <gray>- ᴡᴇʟᴄᴏᴍᴇ ᴀ ᴘʟᴀʏᴇʀ ᴀɴᴅ ᴇᴀʀɴ ʀᴇᴡᴀʀᴅs!</gray>"
  uw-forcewelcome: "<red>/ᴜᴡ ꜰᴏʀᴄᴇᴡᴇʟᴄᴏᴍᴇ <ᴘʟᴀʏᴇʀ></red> <gray>- ꜰᴏʀᴄᴇ ᴡᴇʟᴄᴏᴍᴇ ᴀ ᴘʟᴀʏᴇʀ</gray>"
  uw-resetcooldown: "<red>/ᴜᴡ ʀᴇsᴇᴛᴄᴏᴏʟᴅᴏᴡɴ <ᴘʟᴀʏᴇʀ></red> <gray>- ʀᴇsᴇᴛ ᴄᴏᴏʟᴅᴏᴡɴ</gray>"
  uw-welcomeself: "<red>/ᴜᴡ ᴡᴇʟᴄᴏᴍᴇsᴇʟꜰ</red> <gray>- ᴛᴏɢɢʟᴇ sᴇʟꜰ-ᴡᴇʟᴄᴏᴍᴇ</gray>"
  uw-reload: "<red>/ᴜᴡʀᴇʟᴏᴀᴅ</red> <gray>- ʀᴇʟᴏᴀᴅ ᴄᴏɴꜰɪɢ</gray>"
  footer: "<gradient:#FFAA00:#FF5500>━━━━━━━━━━━━━━━━━━━━━━━━━</gradient>"

uw-forcewelcome:
  success: "<gradient:#55FF55:#00FF00>✅ ᴡᴇʟᴄᴏᴍᴇ ᴍᴇssᴀɢᴇ sᴇɴᴛ ᴛᴏ <yellow>%target%</yellow>!</gradient>"
  error: "<gradient:#FF5555:#FF0000>❌ ꜰᴀɪʟᴇᴅ ᴛᴏ sᴇɴᴅ ᴡᴇʟᴄᴏᴍᴇ ᴍᴇssᴀɢᴇ! ᴄʜᴇᴄᴋ ᴄᴏɴsᴏʟᴇ.</gradient>"
uw-selfwelcome:
  enabled: "<gradient:#55FF55:#00FF00>✅ sᴇʟꜰ-ᴡᴇʟᴄᴏᴍᴇ ɪs ɴᴏᴡ <bold>ᴇɴᴀʙʟᴇᴅ</bold>! ᴘʟᴀʏᴇʀs ᴄᴀɴ ᴡᴇʟᴄᴏᴍᴇ ᴛʜᴇᴍsᴇʟᴠᴇs.</gradient>"
  disabled: "<gradient:#FF5555:#FF0000>🔒 sᴇʟꜰ-ᴡᴇʟᴄᴏᴍᴇ ɪs ɴᴏᴡ <bold>ᴅɪsᴀʙʟᴇᴅ</bold>! ᴘʟᴀʏᴇʀs ᴄᴀɴɴᴏᴛ ᴡᴇʟᴄᴏᴍᴇ ᴛʜᴇᴍsᴇʟᴠᴇs.</gradient>"
uw-resetcooldown:
  success: "<gradient:#55FF55:#00AA00>🔄 ᴄᴏᴏʟᴅᴏᴡɴ ꜰᴏʀ <yellow>%target%</yellow> ʜᴀs ʙᴇᴇɴ ʀᴇsᴇᴛ!</gradient>"
  error: "<gradient:#FF5555:#AA0000>❌ ꜰᴀɪʟᴇᴅ ᴛᴏ ʀᴇsᴇᴛ ᴄᴏᴏʟᴅᴏᴡɴ! ᴄʜᴇᴄᴋ ᴄᴏɴsᴏʟᴇ.</gradient>"

uw-unknownsubcommand: "<gradient:#FFAA00:#FF5500>⚠️ ᴜɴᴋɴᴏᴡɴ sᴜʙᴄᴏᴍᴍᴀɴᴅ! ᴜsᴇ <yellow>/ᴜᴡ ʜᴇʟᴘ</yellow> ꜰᴏʀ ᴀ ʟɪsᴛ.</gradient>"
uw-only-players: "<gradient:#FF5555:#FF0000>🚫 ᴏɴʟʏ ᴘʟᴀʏᴇʀs ᴄᴀɴ ᴜsᴇ ᴛʜɪs ᴄᴏᴍᴍᴀɴᴅ!</gradient>"
uw-no-permission: "<gradient:#FF5555:#FF0000>🔒 ʏᴏᴜ ᴅᴏɴ'ᴛ ʜᴀᴠᴇ ᴘᴇʀᴍɪssɪᴏɴ ᴛᴏ ᴜsᴇ ᴛʜɪs ᴄᴏᴍᴍᴀɴᴅ!</gradient>"


wb-player-not-found: "<gradient:#FF5555:#FF0000>🔍 ᴘʟᴀʏᴇʀ <yellow>%target%</yellow> ɴᴏᴛ ꜰᴏᴜɴᴅ ᴏʀ ɴᴏᴛ ᴏɴʟɪɴᴇ!</gradient>"
wb-cooldown: "<gradient:#FFAA00:#FF5500>⏳ ʏᴏᴜ ᴍᴜsᴛ ᴡᴀɪᴛ <yellow>%time%</yellow> ʙᴇꜰᴏʀᴇ ᴡᴇʟᴄᴏᴍɪɴɢ <yellow>%target%</yellow> ᴀɢᴀɪɴ!</gradient>"
wb-expired: "<gradient:#FF5555:#FF0000>⏰ <yellow>%target%</yellow> ᴄᴀɴ ɴᴏ ʟᴏɴɢᴇʀ ʙᴇ ᴡᴇʟᴄᴏᴍᴇᴅ! ᴛʜᴇʏ ᴊᴏɪɴᴇᴅ ᴏᴠᴇʀ <yellow>%time%</yellow> ᴀɢᴏ.</gradient>"
wb-no-reward: "<gradient:#FFAA00:#FF5500>🎁 ɴᴏ ʀᴇᴡᴀʀᴅ ᴀᴠᴀɪʟᴀʙʟᴇ ꜰᴏʀ ᴡᴇʟᴄᴏᴍɪɴɢ <yellow>%target%</yellow>! ɴᴏ ʀᴇᴡᴀʀᴅ ᴄᴏɴꜰɪɢᴜʀᴇᴅ ꜰᴏʀ ᴛʜᴇɪʀ ʀᴀɴᴋ.</gradient>"
wb-no-selfwelcome: "<gradient:#FF5555:#FF0000>🚫 ʏᴏᴜ ᴄᴀɴɴᴏᴛ ᴡᴇʟᴄᴏᴍᴇ ʏᴏᴜʀsᴇʟꜰ!</gradient>"

```
</details>
</div>  

## ⚡ Support

Join Discord Server for help or updates:
[<img width="2172" height="724" alt="discordhelpbanner" src="https://github.com/user-attachments/assets/c4279560-b178-416c-9084-9390f2bc4195" />
](https://discord.gg/6gCFHFHsbD)

---
<div align="center">

# Example Preview

### 📋 Welcome Bounty
<img width="837" src="https://github.com/user-attachments/assets/9ba09601-61c2-43a6-8254-5af678c18572" alt="Join Broadcast 2" />
<img width="952" src="https://github.com/user-attachments/assets/3db07e2a-dc5d-499d-a3e6-ad594344c30e" alt="Join Broadcast" />

### 💬 Join Broadcast Messages
<img width="798" height="33" alt="image" src="https://github.com/user-attachments/assets/69d7f70f-0f4b-48ed-94f3-630622c3208c" />
<img width="692" height="35" alt="image" src="https://github.com/user-attachments/assets/f62d5dce-8e66-42d8-8afa-4e3570adf334" />


### 🎨 Welcome Messages with Gradients
<img width="830" src="https://github.com/user-attachments/assets/d230320e-8639-43ef-babc-053a6f5051fb" alt="BossBar Display" />
<img width="772" src="https://github.com/user-attachments/assets/ff978626-3e23-43c0-8d90-37979f440c6d" alt="Welcome Bounty Command" />

### 🎮 BossBar Display
<img width="746" src="https://github.com/user-attachments/assets/ae9cacf3-d219-4fd9-a216-9868d3aa07cf" alt="Reward Notification" />

### ✅ Reward Notification
<img width="865" src="https://github.com/user-attachments/assets/6808daf4-a515-4d88-93b0-c527043f3742" alt="Welcome Messages" />

</div>



