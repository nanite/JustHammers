package pro.mikey.justhammers.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import pro.mikey.justhammers.HammerItems;
import pro.mikey.justhammers.Hammers;
import net.fabricmc.api.ModInitializer;
import pro.mikey.justhammers.recipe.HammerRecipes;

public class HammersFabric implements ModInitializer {
    public static final ResourceKey<CreativeModeTab> CREATIVE_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Hammers.id("item_group"));

    public static final CreativeModeTab CREATIVE_TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(HammerItems.NETHERITE_HAMMER.get()))
            .title(Component.translatable("itemGroup.justhammers.justhammers_tab"))
            .build();

    @Override
    public void onInitialize() {
        Hammers.init();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CREATIVE_TAB_KEY, CREATIVE_TAB);

        for (var item : HammerItems.ITEMS) {
            var key = item.createKey(Registries.ITEM);
            Registry.register(BuiltInRegistries.ITEM, key, item.get());
        }

        Registry.register(BuiltInRegistries.RECIPE_TYPE, HammerRecipes.REPAIR_RECIPE.createKey(Registries.RECIPE_TYPE), HammerRecipes.REPAIR_RECIPE.get());
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, HammerRecipes.REPAIR_RECIPE_SERIALIZER.createKey(Registries.RECIPE_SERIALIZER), HammerRecipes.REPAIR_RECIPE_SERIALIZER.get());

        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TAB_KEY).register(itemGroup -> {
            for (var item : HammerItems.ITEMS) {
                itemGroup.accept(item.get());
            }
        });
    }
}
