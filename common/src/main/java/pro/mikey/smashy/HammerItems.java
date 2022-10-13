package pro.mikey.smashy;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import pro.mikey.smashy.Hammers;
import pro.mikey.smashy.hammer.HammerItem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface HammerItems {
    Item.Properties DEFAULT_PROPERTIES = new Item.Properties().tab(Hammers.CREATIVE_TAB);
    DeferredRegister<Item> ITEMS = DeferredRegister.create(Hammers.MOD_ID, Registry.ITEM_REGISTRY);

    List<RegistrySupplier<Item>> HAMMERS = new ArrayList<>();

    RegistrySupplier<Item> STONE_HAMMER = register("stone", Tiers.STONE, SizeOption.THREE_THREE, 1);
    RegistrySupplier<Item> IRON_HAMMER = register("iron", Tiers.IRON, SizeOption.THREE_THREE, 1);
    RegistrySupplier<Item> GOLD_HAMMER = register("gold", Tiers.GOLD, SizeOption.THREE_THREE, 1);
    RegistrySupplier<Item> DIAMOND_HAMMER = register("diamond", Tiers.DIAMOND, SizeOption.THREE_THREE, 1);
    RegistrySupplier<Item> NETHERITE_HAMMER = register("netherite", Tiers.NETHERITE, SizeOption.THREE_THREE, 1);

    RegistrySupplier<Item> STONE_IMPACT_HAMMER = register("stone_impact", Tiers.STONE, SizeOption.THREE_THREE_THREE, 2);
    RegistrySupplier<Item> IRON_IMPACT_HAMMER = register("iron_impact", Tiers.IRON, SizeOption.THREE_THREE_THREE, 2);
    RegistrySupplier<Item> GOLD_IMPACT_HAMMER = register("gold_impact", Tiers.GOLD, SizeOption.THREE_THREE_THREE, 2);
    RegistrySupplier<Item> DIAMOND_IMPACT_HAMMER = register("diamond_impact", Tiers.DIAMOND, SizeOption.THREE_THREE_THREE, 2);
    RegistrySupplier<Item> NETHERITE_IMPACT_HAMMER = register("netherite_impact", Tiers.NETHERITE, SizeOption.THREE_THREE_THREE, 2);

    RegistrySupplier<Item> STONE_FIVE_HAMMER = register("stone_reinforced", Tiers.STONE, SizeOption.FIVE_FIVE, 3);
    RegistrySupplier<Item> IRON_FIVE_HAMMER = register("iron_reinforced", Tiers.IRON, SizeOption.FIVE_FIVE, 3);
    RegistrySupplier<Item> GOLD_FIVE_HAMMER = register("gold_reinforced", Tiers.GOLD, SizeOption.FIVE_FIVE, 3);
    RegistrySupplier<Item> DIAMOND_FIVE_HAMMER = register("diamond_reinforced", Tiers.DIAMOND, SizeOption.FIVE_FIVE, 3);
    RegistrySupplier<Item> NETHERITE_FIVE_HAMMER = register("netherite_reinforced", Tiers.NETHERITE, SizeOption.FIVE_FIVE, 3);

    RegistrySupplier<Item> STONE_FIVE_IMPACT_HAMMER = register("stone_reinforced_impact", Tiers.STONE, SizeOption.FIVE_FIVE_THREE, 4);
    RegistrySupplier<Item> IRON_FIVE_IMPACT_HAMMER = register("iron_reinforced_impact", Tiers.IRON, SizeOption.FIVE_FIVE_THREE, 4);
    RegistrySupplier<Item> GOLD_FIVE_IMPACT_HAMMER = register("gold_reinforced_impact", Tiers.GOLD, SizeOption.FIVE_FIVE_THREE, 4);
    RegistrySupplier<Item> DIAMOND_FIVE_IMPACT_HAMMER = register("diamond_reinforced_impact", Tiers.DIAMOND, SizeOption.FIVE_FIVE_THREE, 4);
    RegistrySupplier<Item> NETHERITE_FIVE_IMPACT_HAMMER = register("netherite_reinforced_impact", Tiers.NETHERITE, SizeOption.FIVE_FIVE_THREE, 4);

    RegistrySupplier<Item> STONE_FIVE_DESTROY_HAMMER = register("stone_destructor", Tiers.STONE, SizeOption.FIVE_FIVE_FIVE, 5);
    RegistrySupplier<Item> IRON_FIVE_DESTROY_HAMMER = register("iron_destructor", Tiers.IRON, SizeOption.FIVE_FIVE_FIVE, 5);
    RegistrySupplier<Item> GOLD_FIVE_DESTROY_HAMMER = register("gold_destructor", Tiers.GOLD, SizeOption.FIVE_FIVE_FIVE, 5);
    RegistrySupplier<Item> DIAMOND_FIVE_DESTROY_HAMMER = register("diamond_destructor", Tiers.DIAMOND, SizeOption.FIVE_FIVE_FIVE, 5);
    RegistrySupplier<Item> NETHERITE_FIVE_DESTROY_HAMMER = register("netherite_destructor", Tiers.NETHERITE, SizeOption.FIVE_FIVE_FIVE, 5);

    RegistrySupplier<Item> IMPACT_CORE = ITEMS.register("impact_core", () -> new Item(DEFAULT_PROPERTIES));
    RegistrySupplier<Item> REINFORCED_CORE = ITEMS.register("reinforced_core", () -> new Item(DEFAULT_PROPERTIES));
    RegistrySupplier<Item> REINFORCED_IMPACT_CORE = ITEMS.register("reinforced_impact_core", () -> new Item(DEFAULT_PROPERTIES));
    RegistrySupplier<Item> DESTRUCTOR_CORE = ITEMS.register("destructor_core", () -> new Item(DEFAULT_PROPERTIES));

    private static RegistrySupplier<Item> register(String name, Tier tier, SizeOption size, int level) {
        RegistrySupplier<Item> register = ITEMS.register(name + "_hammer", () -> new HammerItem(tier, size.radius, size.depth, level));
        HAMMERS.add(register);
        return register;
    }

    static void init() {}

    enum SizeOption {
        THREE_THREE(3, 1),
        FIVE_FIVE(5, 1),
        THREE_THREE_THREE(3, 3),
        FIVE_FIVE_THREE(5, 3),
        FIVE_FIVE_FIVE(5, 5);

        final int radius;
        final int depth;

        SizeOption(int radius, int depth) {
            this.radius = radius;
            this.depth = depth;
        }
    }
}
