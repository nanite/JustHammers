package pro.mikey.justhammers;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import pro.mikey.justhammers.utils.DeferredResource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public interface HammerItems {
    Item.Properties DEFAULT_PROPERTIES = new Item.Properties();

    List<DeferredResource<Item, ? extends Item>> ITEMS = new ArrayList<>();
    List<DeferredResource<Item, HammerItem>> HAMMERS = new ArrayList<>();

    DeferredResource<Item, HammerItem> STONE_HAMMER = registerHammer("stone", Tiers.STONE, SizeOption.THREE_THREE, 1);
    DeferredResource<Item, HammerItem> IRON_HAMMER = registerHammer("iron", Tiers.IRON, SizeOption.THREE_THREE, 1);
    DeferredResource<Item, HammerItem> GOLD_HAMMER = registerHammer("gold", Tiers.GOLD, SizeOption.THREE_THREE, 1);
    DeferredResource<Item, HammerItem> DIAMOND_HAMMER = registerHammer("diamond", Tiers.DIAMOND, SizeOption.THREE_THREE, 1);
    DeferredResource<Item, HammerItem> NETHERITE_HAMMER = registerHammer("netherite", Tiers.NETHERITE, SizeOption.THREE_THREE, 1);

    DeferredResource<Item, HammerItem> STONE_IMPACT_HAMMER = registerHammer("stone_impact", Tiers.STONE, SizeOption.THREE_THREE_THREE, 2);
    DeferredResource<Item, HammerItem> IRON_IMPACT_HAMMER = registerHammer("iron_impact", Tiers.IRON, SizeOption.THREE_THREE_THREE, 2);
    DeferredResource<Item, HammerItem> GOLD_IMPACT_HAMMER = registerHammer("gold_impact", Tiers.GOLD, SizeOption.THREE_THREE_THREE, 2);
    DeferredResource<Item, HammerItem> DIAMOND_IMPACT_HAMMER = registerHammer("diamond_impact", Tiers.DIAMOND, SizeOption.THREE_THREE_THREE, 2);
    DeferredResource<Item, HammerItem> NETHERITE_IMPACT_HAMMER = registerHammer("netherite_impact", Tiers.NETHERITE, SizeOption.THREE_THREE_THREE, 2);

    DeferredResource<Item, HammerItem> STONE_FIVE_HAMMER = registerHammer("stone_reinforced", Tiers.STONE, SizeOption.FIVE_FIVE, 3);
    DeferredResource<Item, HammerItem> IRON_FIVE_HAMMER = registerHammer("iron_reinforced", Tiers.IRON, SizeOption.FIVE_FIVE, 3);
    DeferredResource<Item, HammerItem> GOLD_FIVE_HAMMER = registerHammer("gold_reinforced", Tiers.GOLD, SizeOption.FIVE_FIVE, 3);
    DeferredResource<Item, HammerItem> DIAMOND_FIVE_HAMMER = registerHammer("diamond_reinforced", Tiers.DIAMOND, SizeOption.FIVE_FIVE, 3);
    DeferredResource<Item, HammerItem> NETHERITE_FIVE_HAMMER = registerHammer("netherite_reinforced", Tiers.NETHERITE, SizeOption.FIVE_FIVE, 3);

    DeferredResource<Item, HammerItem> STONE_FIVE_IMPACT_HAMMER = registerHammer("stone_reinforced_impact", Tiers.STONE, SizeOption.FIVE_FIVE_THREE, 4);
    DeferredResource<Item, HammerItem> IRON_FIVE_IMPACT_HAMMER = registerHammer("iron_reinforced_impact", Tiers.IRON, SizeOption.FIVE_FIVE_THREE, 4);
    DeferredResource<Item, HammerItem> GOLD_FIVE_IMPACT_HAMMER = registerHammer("gold_reinforced_impact", Tiers.GOLD, SizeOption.FIVE_FIVE_THREE, 4);
    DeferredResource<Item, HammerItem> DIAMOND_FIVE_IMPACT_HAMMER = registerHammer("diamond_reinforced_impact", Tiers.DIAMOND, SizeOption.FIVE_FIVE_THREE, 4);
    DeferredResource<Item, HammerItem> NETHERITE_FIVE_IMPACT_HAMMER = registerHammer("netherite_reinforced_impact", Tiers.NETHERITE, SizeOption.FIVE_FIVE_THREE, 4);

    DeferredResource<Item, HammerItem> STONE_FIVE_DESTROY_HAMMER = registerHammer("stone_destructor", Tiers.STONE, SizeOption.FIVE_FIVE_FIVE, 5);
    DeferredResource<Item, HammerItem> IRON_FIVE_DESTROY_HAMMER = registerHammer("iron_destructor", Tiers.IRON, SizeOption.FIVE_FIVE_FIVE, 5);
    DeferredResource<Item, HammerItem> GOLD_FIVE_DESTROY_HAMMER = registerHammer("gold_destructor", Tiers.GOLD, SizeOption.FIVE_FIVE_FIVE, 5);
    DeferredResource<Item, HammerItem> DIAMOND_FIVE_DESTROY_HAMMER = registerHammer("diamond_destructor", Tiers.DIAMOND, SizeOption.FIVE_FIVE_FIVE, 5);
    DeferredResource<Item, HammerItem> NETHERITE_FIVE_DESTROY_HAMMER = registerHammer("netherite_destructor", Tiers.NETHERITE, SizeOption.FIVE_FIVE_FIVE, 5);

    DeferredResource<Item, Item> IMPACT_CORE = register("impact_core", () -> new Item(DEFAULT_PROPERTIES));
    DeferredResource<Item, Item> REINFORCED_CORE = register("reinforced_core", () -> new Item(DEFAULT_PROPERTIES));
    DeferredResource<Item, Item> REINFORCED_IMPACT_CORE = register("reinforced_impact_core", () -> new Item(DEFAULT_PROPERTIES));
    DeferredResource<Item, Item> DESTRUCTOR_CORE = register("destructor_core", () -> new Item(DEFAULT_PROPERTIES));

    private static DeferredResource<Item, HammerItem> registerHammer(String name, Tier tier, SizeOption size, int level) {
        DeferredResource<Item, HammerItem> register = register(name + "_hammer", () -> new HammerItem(tier, size.radius, size.depth, level));
        HAMMERS.add(register);
        return register;
    }

    private static <T extends Item> DeferredResource<Item, T> register(String name, Supplier<T> supplier) {
        var entry = new DeferredResource<Item, T>(name, supplier);
        ITEMS.add(entry);
        return entry;
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
