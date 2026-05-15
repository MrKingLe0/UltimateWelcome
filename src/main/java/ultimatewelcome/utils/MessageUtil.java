package ultimatewelcome.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    // matches &#RRGGBB
    private static final Pattern HEX_PATTERN = 
            Pattern.compile("&#([A-Fa-f0-9]{6})");

    // matches legacy hex &x&R&R&G&G&B&B
    private static final Pattern LEGACY_HEX_PATTERN = 
            Pattern.compile("&x(&[A-Fa-f0-9]){6}");

    public static Component parse(String input) {
        if (input == null) return Component.empty();

        // 1. Convert legacy hex (&x&R&R...) to &#RRGGBB
        input = legacyHexToNormalHex(input);

        // 2. Convert &#RRGGBB to <#RRGGBB>
        input = convertHexColors(input);

        // 3. Convert & formatting codes to MiniMessage
        input = convertLegacyFormatting(input);

        // 4. Parse MiniMessage
        return mm.deserialize(input);
    }

    // Converts &x&R&R&G&G&B&B to &#RRGGBB
    private static String legacyHexToNormalHex(String msg) {
        Matcher matcher = LEGACY_HEX_PATTERN.matcher(msg);
        StringBuffer sb = new StringBuffer();
        
        while (matcher.find()) {
            String match = matcher.group();
            StringBuilder hex = new StringBuilder();
            for (int i = 2; i < match.length(); i += 2) {
                if (i + 1 < match.length()) {
                    hex.append(match.charAt(i + 1));
                }
            }
            matcher.appendReplacement(sb, Matcher.quoteReplacement("&#" + hex.toString()));
        }
        matcher.appendTail(sb);
        
        return sb.toString();
    }

    // Converts &#RRGGBB into <#RRGGBB>
    private static String convertHexColors(String msg) {
        return HEX_PATTERN.matcher(msg).replaceAll("<#$1>");
    }

    // Converts & color codes and formatting to MiniMessage
    private static String convertLegacyFormatting(String msg) {
        StringBuilder out = new StringBuilder();
        boolean hasOpenColor = false;
        int lastColorPosition = -1;

        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);

            if (c == '&' && i + 1 < msg.length()) {
                char code = msg.charAt(i + 1);

                // Handle format codes
                if (code == 'l' || code == 'L') {
                    out.append("<bold>");
                    i++;
                    continue;
                }
                if (code == 'o' || code == 'O') {
                    out.append("<italic>");
                    i++;
                    continue;
                }
                if (code == 'n' || code == 'N') {
                    out.append("<underlined>");
                    i++;
                    continue;
                }
                if (code == 'm' || code == 'M') {
                    out.append("<strikethrough>");
                    i++;
                    continue;
                }
                if (code == 'k' || code == 'K') {
                    out.append("<obfuscated>");
                    i++;
                    continue;
                }

                // Handle reset
                if (code == 'r' || code == 'R') {
                    out.append("<reset>");
                    hasOpenColor = false;
                    lastColorPosition = -1;
                    i++;
                    continue;
                }

                // Handle color codes (0-9, a-f)
                String miniColor = legacyColorToMini(code);
                if (miniColor != null) {
                    out.append(miniColor);
                    hasOpenColor = true;
                    lastColorPosition = out.length(); // Track where the color tag was added
                    i++;
                    continue;
                }
            }

            out.append(c);
        }

        // Don't add closing tag if the last thing was text after a color
        // The color should remain active for the rest of the text
        return out.toString();
    }

    private static String legacyColorToMini(char code) {
        // Convert to lowercase for matching
        char lowerCode = Character.toLowerCase(code);
        return switch (lowerCode) {
            case '0' -> "<black>";
            case '1' -> "<dark_blue>";
            case '2' -> "<dark_green>";
            case '3' -> "<dark_aqua>";
            case '4' -> "<dark_red>";
            case '5' -> "<dark_purple>";
            case '6' -> "<gold>";
            case '7' -> "<gray>";
            case '8' -> "<dark_gray>";
            case '9' -> "<blue>";
            case 'a' -> "<green>";
            case 'b' -> "<aqua>";
            case 'c' -> "<red>";
            case 'd' -> "<light_purple>";
            case 'e' -> "<yellow>";
            case 'f' -> "<white>";
            default -> null;
        };
    }
}