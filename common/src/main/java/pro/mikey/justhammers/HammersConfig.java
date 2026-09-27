package pro.mikey.justhammers;

import dev.nanite.library.core.config.Config;
import dev.nanite.library.core.config.values.BooleanConfigValue;
import dev.nanite.library.core.config.values.DoubleConfigValue;

public interface HammersConfig {
    Config CLIENT_CONFIG = Config.clientConfig(Hammers.MOD_ID);
    Config COMMON_CONFIG = Config.commonConfig(Hammers.MOD_ID);

    BooleanConfigValue DISABLED_DURABILITY_TOOLTIP = CLIENT_CONFIG.booleanValue("disabledDurabilityTooltip", false)
            .comments("Set to true to disable the durability tooltip on hammers");

    DoubleConfigValue DURABILITY_REPAIR_PERCENTAGE = COMMON_CONFIG.doubleValue(
            "durabilityRepairPercentage",
            33.33D
    ).comments("The percentage of durability restored per repair item");

    DoubleConfigValue DURABILITY_REPAIR_PERCENTAGE_NETHERITE = COMMON_CONFIG.doubleValue(
            "durabilityRepairPercentageNetherite",
            100D
    ).comments("The percentage of durability restored per repair item for netherite hammers");

    BooleanConfigValue ALLOW_HAMMER_BREAKING = COMMON_CONFIG.booleanValue(
            "allowHammerBreaking",
            false
    ).comments("Set to true to allow hammers to break when running out of durability");
}
