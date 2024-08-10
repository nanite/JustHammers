# Just Hammers Changelog

## [21.0.3]

### Changed

- Added the hammers to the #minecraft:pickaxes tag
- Hammer repair durability is now handled by a percentage based system instead of a flat amount

### Fixed 

- Fixed a crashing issue on fabric due to poorly registered handlers [#38](https://github.com/nanite/JustHammers/issues/38)
- The config system will now auto repair itself if it is missing a value
- Actually call the blockdrops event on NeoForge

## [21.0.2]

### Added

- Added a config option to disable the hammers durability from being shown in the tooltip [#22](https://github.com/nanite/JustHammers/issues/22)
- Added a custom recipe type to allow for repairing the item in your crafting grid with the same material as the hammer

### Changed

- You can now enchant hammers with all the same enchantments as pickaxes [#29](https://github.com/nanite/JustHammers/issues/29)

### Fixed

- Hammer cores acting as hammers [#37](https://github.com/nanite/JustHammers/issues/37)
- Hammers would apply one extra damage on break due to the hammer trying to break the same block twice [#36](https://github.com/nanite/JustHammers/issues/36)
- Hammers will no longer void blocks like dirt and sand but will break slower like in vanilla [#35](https://github.com/nanite/JustHammers/issues/35)
- Hammers now show in the hand correctly instead of looking like a normal item [#30](https://github.com/nanite/JustHammers/issues/30)
- Hammers will no longer crash the client if durability goes negative (somehow) [#34](https://github.com/nanite/JustHammers/issues/34)

## [21.0.1]

### Fixed

- Unable to craft hammers due to missing recipes

## [21.0.0]

### Changed

- Ported to 1.21

### Fixed

- The hammers block outline would select fluids...

## [20.6.0]

### Changed

- Ported to 1.20.6
