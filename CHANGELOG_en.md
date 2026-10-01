Changelog
=========

1.2.3
-----
- Bundled the changelog (CHANGELOG.md / CHANGELOG_en.md)

1.2.2
-----
- Changed the author name shown in Mod Menu to "CCCHJP"

1.2.1
-----
- Fixed build errors in the config screen (title rendering and screen
  switching now use the Minecraft 26.3 API)

1.2.0
-----
- Added Mod Menu (21.0.0) support
  - Open the config screen from the mod list and adjust the multiplier
    with a slider (0.1 steps)
  - Added a "Reset to default" button to the config screen
- Fixed incorrect translation keys for the Mod Menu summary/description
- Now Minecraft 26.3 only (removed the 26.1 / 26.2 build switching)
- Updated Fabric API to 0.161.0+26.3
- Removed README_versions.txt / README_versions_en.txt and merged the
  build instructions into the README

1.1.1
-----
- Support for the official Minecraft 26.3 release

1.0.3
-----
- Added per-version build guides (README_versions.txt / README_versions_en.txt)

1.0.2
-----
- Fixed the Minecraft version requirement in fabric.mod.json
  (removed the ">=26.2" restriction so it works across all supported versions)

1.0.1
-----
- Added a build-log message showing which Minecraft version is actually
  being targeted

1.0.0
-----
- Initial release
- Reveal-range multiplier from 1.0x to 4.0x
- Adjustable via config/mapviewrange.json or in-game command
