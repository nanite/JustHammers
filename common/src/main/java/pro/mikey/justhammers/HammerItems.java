package pro.mikey.justhammers;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public interface HammerItems {
    Supplier<Item.Properties> DEFAULT_PROPERTIES = () -> new Item.Properties().arch$tab(Hammers.TAB);
    DeferredRegister<Item> ITEMS = DeferredRegister.create(Hammers.MOD_ID, Registries.ITEM);

    List<RegistrySupplier<Item>> HAMMERS = new ArrayList<>();

    RegistrySupplier<Item> STONE_HAMMER = register("stone", ToolMaterial.STONE, SizeOption.THREE_THREE, 1);
    RegistrySupplier<Item> IRON_HAMMER = register("iron", ToolMaterial.IRON, SizeOption.THREE_THREE, 1);
    RegistrySupplier<Item> GOLD_HAMMER = register("gold", ToolMaterial.GOLD, SizeOption.THREE_THREE, 1);
    RegistrySupplier<Item> DIAMOND_HAMMER = register("diamond", ToolMaterial.DIAMOND, SizeOption.THREE_THREE, 1);
    RegistrySupplier<Item> NETHERITE_HAMMER = register("netherite", ToolMaterial.NETHERITE, SizeOption.THREE_THREE, 1);

    RegistrySupplier<Item> STONE_IMPACT_HAMMER = register("stone_impact", ToolMaterial.STONE, SizeOption.THREE_THREE_THREE, 2);
    RegistrySupplier<Item> IRON_IMPACT_HAMMER = register("iron_impact", ToolMaterial.IRON, SizeOption.THREE_THREE_THREE, 2);
    RegistrySupplier<Item> GOLD_IMPACT_HAMMER = register("gold_impact", ToolMaterial.GOLD, SizeOption.THREE_THREE_THREE, 2);
    RegistrySupplier<Item> DIAMOND_IMPACT_HAMMER = register("diamond_impact", ToolMaterial.DIAMOND, SizeOption.THREE_THREE_THREE, 2);
    RegistrySupplier<Item> NETHERITE_IMPACT_HAMMER = register("netherite_impact", ToolMaterial.NETHERITE, SizeOption.THREE_THREE_THREE, 2);

    RegistrySupplier<Item> STONE_FIVE_HAMMER = register("stone_reinforced", ToolMaterial.STONE, SizeOption.FIVE_FIVE, 3);
    RegistrySupplier<Item> IRON_FIVE_HAMMER = register("iron_reinforced", ToolMaterial.IRON, SizeOption.FIVE_FIVE, 3);
    RegistrySupplier<Item> GOLD_FIVE_HAMMER = register("gold_reinforced", ToolMaterial.GOLD, SizeOption.FIVE_FIVE, 3);
    RegistrySupplier<Item> DIAMOND_FIVE_HAMMER = register("diamond_reinforced", ToolMaterial.DIAMOND, SizeOption.FIVE_FIVE, 3);
    RegistrySupplier<Item> NETHERITE_FIVE_HAMMER = register("netherite_reinforced", ToolMaterial.NETHERITE, SizeOption.FIVE_FIVE, 3);

    RegistrySupplier<Item> STONE_FIVE_IMPACT_HAMMER = register("stone_reinforced_impact", ToolMaterial.STONE, SizeOption.FIVE_FIVE_THREE, 4);
    RegistrySupplier<Item> IRON_FIVE_IMPACT_HAMMER = register("iron_reinforced_impact", ToolMaterial.IRON, SizeOption.FIVE_FIVE_THREE, 4);
    RegistrySupplier<Item> GOLD_FIVE_IMPACT_HAMMER = register("gold_reinforced_impact", ToolMaterial.GOLD, SizeOption.FIVE_FIVE_THREE, 4);
    RegistrySupplier<Item> DIAMOND_FIVE_IMPACT_HAMMER = register("diamond_reinforced_impact", ToolMaterial.DIAMOND, SizeOption.FIVE_FIVE_THREE, 4);
    RegistrySupplier<Item> NETHERITE_FIVE_IMPACT_HAMMER = register("netherite_reinforced_impact", ToolMaterial.NETHERITE, SizeOption.FIVE_FIVE_THREE, 4);

    RegistrySupplier<Item> STONE_FIVE_DESTROY_HAMMER = register("stone_destructor", ToolMaterial.STONE, SizeOption.FIVE_FIVE_FIVE, 5);
    RegistrySupplier<Item> IRON_FIVE_DESTROY_HAMMER = register("iron_destructor", ToolMaterial.IRON, SizeOption.FIVE_FIVE_FIVE, 5);
    RegistrySupplier<Item> GOLD_FIVE_DESTROY_HAMMER = register("gold_destructor", ToolMaterial.GOLD, SizeOption.FIVE_FIVE_FIVE, 5);
    RegistrySupplier<Item> DIAMOND_FIVE_DESTROY_HAMMER = register("diamond_destructor", ToolMaterial.DIAMOND, SizeOption.FIVE_FIVE_FIVE, 5);
    RegistrySupplier<Item> NETHERITE_FIVE_DESTROY_HAMMER = register("netherite_destructor", ToolMaterial.NETHERITE, SizeOption.FIVE_FIVE_FIVE, 5);

    RegistrySupplier<Item> IMPACT_CORE = ITEMS.register("impact_core", () -> new Item(DEFAULT_PROPERTIES.get().setId(ResourceKey.create(Registries.ITEM, Hammers.id("impact_core")))));
    RegistrySupplier<Item> REINFORCED_CORE = ITEMS.register("reinforced_core", () -> new Item(DEFAULT_PROPERTIES.get().setId(ResourceKey.create(Registries.ITEM, Hammers.id("reinforced_core")))));
    RegistrySupplier<Item> REINFORCED_IMPACT_CORE = ITEMS.register("reinforced_impact_core", () -> new Item(DEFAULT_PROPERTIES.get().setId(ResourceKey.create(Registries.ITEM, Hammers.id("reinforced_impact_core")))));
    RegistrySupplier<Item> DESTRUCTOR_CORE = ITEMS.register("destructor_core", () -> new Item(DEFAULT_PROPERTIES.get().setId(ResourceKey.create(Registries.ITEM, Hammers.id("destructor_core")))));

    private static RegistrySupplier<Item> register(String name, ToolMaterial tier, SizeOption size, int level) {
        RegistrySupplier<Item> register = ITEMS.register(name + "_hammer", () -> new HammerItem(DEFAULT_PROPERTIES.get().setId(ResourceKey.create(Registries.ITEM, Hammers.id(name + "_hammer"))),
                tier, size.radius, size.depth, level));
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
