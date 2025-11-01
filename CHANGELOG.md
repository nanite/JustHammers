# Just Hammers Changelog

## [21.1.2]

### Changed

- The mod no longer requires architectury as a dependency.
- Minor updates to the toolchain to allow for this move.

### Fixed

- We now fire platform specific events for block break to allow block protection mods to function correctly
- Fluid / Waterlogged blocks will not be broken when within the hammer's area of effect

## [21.1.1]

### Added

- `zh-cn` translations thanks to [shenyx110](https://github.com/shenyx110) [#65](https://github.com/nanite/JustHammers/issues/65)
- `ja_jp` translations thanks to [PExPE3](https://github.com/PExPE3) [#72](https://github.com/nanite/JustHammers/pull/72)

### Fixed

- Blocks not being counted towards the blocks mined statistic [#67](https://github.com/nanite/JustHammers/issues/67)
- Animation resetting on fabric with mending causing the hammer to be delayed in breaking blocks [#64](https://github.com/nanite/JustHammers/issues/64)
- Server issue with BreakBlockEvent [#75](https://github.com/nanite/JustHammers/issues/75)

## [21.1.0]

### Added

- Added a config to disable the no-breaking checks. This means, when enabled, hammers can break in the players inventory when their damage is depleted.
- Added a config option to disable the "fancy" durability in the tooltip as some other mods will already provide something similar.

### Fixed

- XP not dropping for ores and other blocks that drop XP
- Sound being played for all breaking blocks which caused sound engine issues
- Make netherite hammers fire resistant
