[README_en.txt](https://github.com/user-attachments/files/32910100/README_en.txt)
Map View Range (Ver.1.2.3)
============================

Overview
--------
Expands the area revealed on your map as you walk around while holding it.

Vanilla decides "how far around the player to reveal per update" inside
MapItem#update(...) with:
    int radius = 128 / scale;
The larger the map's scale (the more zoomed out it is), the smaller this
radius becomes — which is why zoomed-out maps feel like they barely fill
in no matter how much you walk.

This mod multiplies just the "128" in that formula by a configurable
factor, expanding the radius itself. The underlying mechanism (revealing
a little more each game tick) is unchanged, so there's no heavy one-shot
fill like a full-map instant reveal would cause.

Changing the multiplier
------------------------
0. Mod Menu (added in Ver.1.2.3)
   With Mod Menu (21.0.0) installed, pick "Map View Range" in the mod
   list and press the config button to adjust the multiplier with a
   slider (0.1 steps, with a "Reset to default" button; saved to the
   config when you close the screen).
   Mod Menu is optional - the mod works normally without it.

1. Config file
   Edit "radiusMultiplier" in config/mapviewrange.json (range: 1.0-4.0,
   loaded on game start).

   Example:
   {
     "radiusMultiplier": 3.0
   }

2. In-game command (no permission required)
   - Check current value:  /mapviewrange radius
   - Change it (applies immediately and saves to config):
     /mapviewrange radius 3.0
   Only values between 1.0 and 4.0 are accepted.

Guidance
--------
- 1.0x: same as vanilla
- 2.0x: tested and confirmed to work well; a bit slower to load but
  still reasonable on most setups
- 3.0x-4.0x: worth trying on a higher-end PC. You can lower it again
  instantly with the command if it feels heavy

Target versions
----------------
- Minecraft: 26.3 (unobfuscated; 26.3 only as of Ver.1.2.3)
- Fabric Loader: 0.19.5
- Fabric API: 0.161.0+26.3
- Mod Menu: 21.0.0 (optional)
- Fabric Loom: 1.17-SNAPSHOT (plugin id net.fabricmc.fabric-loom)
- Java: 25
Note: for 26.1 / 26.2, use the builds up to Ver.1.0.3.

Installation
------------
1. Install Fabric Loader 0.19.5 for MC 26.3
2. Put Fabric API in your mods folder
3. (Optional) Put Mod Menu 21.0.0 in your mods folder
4. Put this mod's jar in your mods folder
   (Safe to use alongside mapinstafill - they hook different methods)

Building
--------
Run from the project folder:
  ./gradlew build
Output jar: build/libs/mapviewrange-26.3-<mod_version>.jar
(<mod_version> comes from gradle.properties, e.g. 1.2.3)
To test in-game: ./gradlew runClient (launches with Mod Menu too)

Known limitations
------------------
- If the actual layout/order of the "128" constants inside MapItem#update
  differs from what was confirmed during development, the mixin could
  end up targeting the wrong constant.
- The command has no permission requirement (anyone can run it).
