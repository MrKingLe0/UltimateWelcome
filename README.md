<img width="1774" height="887" alt="UltimateWelcome" src="https://github.com/user-attachments/assets/aefacb66-d0b9-438c-b98e-300d315e32a6" />


A powerful and highly configurable Minecraft Paper plugin that enhances your server's join/leave experience with ranks, messages, sounds, titles, fireworks, and a full welcome bounty system.

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

You can use these in certain messages or commands:

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
<div align="center">
<details>
<summary>[ 📄 Click to view config.yml ]</summary>

```yaml
#=====================================================================
#  __  ______  _            __        _      __    __                  
# / / / / / /_(_)_ _  ___ _/ /____   | | /| / /__ / /______  __ _  ___ 
#/ /_/ / / __/ /  ' \/ _ `/ __/ -_)  | |/ |/ / -_) / __/ _ \/  ' \/ -_)
#\____/_/\__/_/_/_/_/\_,_/\__/\__/   |__/|__/\__/_/\__/\___/_/_/_/\__/ 
#                                                                      
#======================================================================
#Thanks for downloading UltimateWelcome V1.0!
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

# Player Will see this message on that after they join. There's a 1 second delay before the message is sent to the player. You can use %player% as a placeholder for the player's name.
# If not set, it will use default messages in the welcome-messages.
welcome-messages:
  enabled: true
  messages:
    owner:
      - "========================================="
      - "                &5&lsᴇʀᴠᴇʀ_ɴᴀᴍᴇ"
      - "            &4&lᴡᴇʟᴄᴏᴍᴇ &f%player% &4&l!"
      - " &cʜᴏᴘᴇ ʏᴏᴜ ʜᴀᴠᴇ ᴀɴ &6ᴀᴍᴀᴢɪɴɢ &cʀᴇsᴛ ᴏғ ʏᴏᴜʀ sᴛᴀʏ!"
      - ""
      - "          &bᴡᴇʙsɪᴛᴇ: &fwww.example.com"
      - "         &9ᴅɪsᴄᴏʀᴅ: &fdiscord.gg/example"
      - "========================================="
    vip:
      - "========================================="
      - "                &5&lsᴇʀᴠᴇʀ_ɴᴀᴍᴇ"
      - ""
      - "          &d&lᴡᴇʟᴄᴏᴍᴇ &a&lVIP &f%player% &d&l!"
      - "    &9ᴡᴇ ʜᴀᴠᴇ ʙᴇᴇᴅɴ ᴡᴀɪᴛɪɴɢ ᴊᴜsᴛ ꜰᴏʀ ʏᴏᴜ!"
      - ""
      - "          &bᴡᴇʙsɪᴛᴇ: &fwww.example.com"
      - "        &9ᴅɪsᴄᴏʀᴅ: &fdiscord.gg/example"
      - "========================================="
    default:
      - "========================================="
      - "                &5&lsᴇʀᴠᴇʀ_ɴᴀᴍᴇ"
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
      - "&6ᴛʜᴇ ᴏᴡɴᴇʀ &4&l%player% &6ᴊᴏɪɴᴇᴅ ᴛʜᴇ sᴇʀᴠᴇʀ!"
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
    - "%player% &dᴡᴇʟᴄᴏᴍᴇᴅ ʏᴏᴜ ᴀɴᴅ ʏᴏᴜ ʀᴇᴄᴇɪᴠᴇᴅ ᴀ ʀᴇᴡᴀʀᴅ!"

  # This will be heard by the player after they welcome someone.
  sound:
    enabled: true
    type: "ENTITY_PLAYER_LEVELUP"
    volume: 1.0
    pitch: 1.0
```
</details>
</div>  

## ⚡ Support

Join Discord Server for help or updates:
[click here](https://discord.gg/6gCFHFHsbD)

---

## 📌 Version

###`1.0 - Initial Release`
---

## 🔥 Example Preview
<img width="772" height="235" alt="image" src="https://github.com/user-attachments/assets/ff978626-3e23-43c0-8d90-37979f440c6d" />
<img width="952" height="58" alt="image" src="https://github.com/user-attachments/assets/3db07e2a-dc5d-499d-a3e6-ad594344c30e" />
<img width="837" height="57" alt="image" src="https://github.com/user-attachments/assets/9ba09601-61c2-43a6-8254-5af678c18572" />
<img width="865" height="137" alt="image" src="https://github.com/user-attachments/assets/6808daf4-a515-4d88-93b0-c527043f3742" />

