package pro.mikey.justhammers.forge.data;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import pro.mikey.justhammers.HammerItems;
import pro.mikey.justhammers.Hammers;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void onDataGen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new ItemModelGen(generator, existingFileHelper));
            generator.addProvider(new LangGen(generator));
        }

        if (event.includeServer()) {
            generator.addProvider(new RecipeGen(generator));
        }
    }

    public static class RecipeGen extends RecipeProvider {
        public RecipeGen(DataGenerator arg) {
            super(arg);
        }

        @Override
        protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
            standardHammer(HammerItems.STONE_HAMMER, Items.STONE).save(consumer);
            standardHammer(HammerItems.IRON_HAMMER, Items.IRON_INGOT).save(consumer);
            standardHammer(HammerItems.GOLD_HAMMER, Items.GOLD_INGOT).save(consumer);
            standardHammer(HammerItems.DIAMOND_HAMMER, Items.DIAMOND).save(consumer);
            standardHammer(HammerItems.NETHERITE_HAMMER, Items.NETHERITE_INGOT).save(consumer);

            coreHammer(HammerItems.STONE_IMPACT_HAMMER, HammerItems.IMPACT_CORE, Items.STONE).save(consumer);
            coreHammer(HammerItems.IRON_IMPACT_HAMMER, HammerItems.IMPACT_CORE, Items.IRON_BLOCK).save(consumer);
            coreHammer(HammerItems.GOLD_IMPACT_HAMMER, HammerItems.IMPACT_CORE, Items.GOLD_BLOCK).save(consumer);
            coreHammer(HammerItems.DIAMOND_IMPACT_HAMMER, HammerItems.IMPACT_CORE, Items.DIAMOND_BLOCK).save(consumer);
            coreHammer(HammerItems.NETHERITE_IMPACT_HAMMER, HammerItems.IMPACT_CORE, Items.NETHERITE_BLOCK).save(consumer);

            coreHammer(HammerItems.STONE_FIVE_HAMMER, HammerItems.REINFORCED_CORE, Items.STONE).save(consumer);
            coreHammer(HammerItems.IRON_FIVE_HAMMER, HammerItems.REINFORCED_CORE, Items.IRON_BLOCK).save(consumer);
            coreHammer(HammerItems.GOLD_FIVE_HAMMER, HammerItems.REINFORCED_CORE, Items.GOLD_BLOCK).save(consumer);
            coreHammer(HammerItems.DIAMOND_FIVE_HAMMER, HammerItems.REINFORCED_CORE, Items.DIAMOND_BLOCK).save(consumer);
            coreHammer(HammerItems.NETHERITE_FIVE_HAMMER, HammerItems.REINFORCED_CORE, Items.NETHERITE_BLOCK).save(consumer);

            coreHammer(HammerItems.STONE_FIVE_IMPACT_HAMMER, HammerItems.REINFORCED_IMPACT_CORE, Items.STONE).save(consumer);
            coreHammer(HammerItems.IRON_FIVE_IMPACT_HAMMER, HammerItems.REINFORCED_IMPACT_CORE, Items.IRON_BLOCK).save(consumer);
            coreHammer(HammerItems.GOLD_FIVE_IMPACT_HAMMER, HammerItems.REINFORCED_IMPACT_CORE, Items.GOLD_BLOCK).save(consumer);
            coreHammer(HammerItems.DIAMOND_FIVE_IMPACT_HAMMER, HammerItems.REINFORCED_IMPACT_CORE, Items.DIAMOND_BLOCK).save(consumer);
            coreHammer(HammerItems.NETHERITE_FIVE_IMPACT_HAMMER, HammerItems.REINFORCED_IMPACT_CORE, Items.NETHERITE_BLOCK).save(consumer);

            coreHammer(HammerItems.STONE_FIVE_DESTROY_HAMMER, HammerItems.DESTRUCTOR_CORE, Items.STONE).save(consumer);
            coreHammer(HammerItems.IRON_FIVE_DESTROY_HAMMER, HammerItems.DESTRUCTOR_CORE, Items.IRON_BLOCK).save(consumer);
            coreHammer(HammerItems.GOLD_FIVE_DESTROY_HAMMER, HammerItems.DESTRUCTOR_CORE, Items.GOLD_BLOCK).save(consumer);
            coreHammer(HammerItems.DIAMOND_FIVE_DESTROY_HAMMER, HammerItems.DESTRUCTOR_CORE, Items.DIAMOND_BLOCK).save(consumer);
            coreHammer(HammerItems.NETHERITE_FIVE_DESTROY_HAMMER, HammerItems.DESTRUCTOR_CORE, Items.NETHERITE_BLOCK).save(consumer);

            // These kinda suck
            core(HammerItems.IMPACT_CORE, Items.REDSTONE, HammerItems.NETHERITE_HAMMER.get(), Items.IRON_BLOCK, Items.GOLD_BLOCK).save(consumer);
            core(HammerItems.REINFORCED_CORE, Items.REDSTONE_BLOCK, HammerItems.NETHERITE_HAMMER.get(), Items.GOLD_BLOCK, Items.GOLD_BLOCK).save(consumer);
            core(HammerItems.REINFORCED_IMPACT_CORE, Items.REDSTONE_BLOCK, HammerItems.NETHERITE_HAMMER.get(), Items.DIAMOND_BLOCK, Items.GOLD_BLOCK).save(consumer);
            core(HammerItems.DESTRUCTOR_CORE, Items.REDSTONE_BLOCK, HammerItems.NETHERITE_HAMMER.get(), Items.DIAMOND_BLOCK, Items.DIAMOND_BLOCK).save(consumer);
        }

        private RecipeBuilder standardHammer(Supplier<Item> hammer, ItemLike material) {
            return ShapedRecipeBuilder.shaped(hammer.get())
                    .define('a', material)
                    .define('b', Items.STICK)
                    .pattern("aba")
                    .pattern(" ba")
                    .pattern(" b ")
                    .unlockedBy("has_material", has(material));
        }

        private RecipeBuilder coreHammer(Supplier<Item> hammer, Supplier<Item> core, ItemLike material) {
            return ShapedRecipeBuilder.shaped(hammer.get())
                    .define('a', material)
                    .define('b', Items.STICK)
                    .define('c', core.get())
                    .pattern("aca")
                    .pattern(" ba")
                    .pattern(" b ")
                    .unlockedBy("has_material", has(material));
        }

        private RecipeBuilder core(Supplier<Item> result, Item outside, Item inside, Item left, Item right) {
            return ShapedRecipeBuilder.shaped(result.get())
                    .define('a', outside)
                    .define('b', inside)
                    .define('c', left)
                    .define('d', right)
                    .pattern("aaa")
                    .pattern("cbd")
                    .pattern("aaa")
                    .unlockedBy("has_material", has(HammerItems.STONE_HAMMER.get()));
        }
    }

    public static class LangGen extends LanguageProvider {
        public LangGen(DataGenerator gen) {
            super(gen, Hammers.MOD_ID, "en_us");
        }

        @Override
        protected void addTranslations() {
            addItem(HammerItems.STONE_HAMMER, "Stone Hammer");
            addItem(HammerItems.IRON_HAMMER, "Iron Hammer");
            addItem(HammerItems.GOLD_HAMMER, "Gold Hammer");
            addItem(HammerItems.DIAMOND_HAMMER, "Diamond Hammer");
            addItem(HammerItems.NETHERITE_HAMMER, "Netherite Hammer");
            addItem(HammerItems.STONE_IMPACT_HAMMER, "Stone Impact Hammer");
            addItem(HammerItems.IRON_IMPACT_HAMMER, "Iron Impact Hammer");
            addItem(HammerItems.GOLD_IMPACT_HAMMER, "Gold Impact Hammer");
            addItem(HammerItems.DIAMOND_IMPACT_HAMMER, "Diamond Impact Hammer");
            addItem(HammerItems.NETHERITE_IMPACT_HAMMER, "Netherite Impact Hammer");
            addItem(HammerItems.STONE_FIVE_HAMMER, "Stone Reinforced Hammer");
            addItem(HammerItems.IRON_FIVE_HAMMER, "Iron Reinforced Hammer");
            addItem(HammerItems.GOLD_FIVE_HAMMER, "Gold Reinforced Hammer");
            addItem(HammerItems.DIAMOND_FIVE_HAMMER, "Diamond Reinforced Hammer");
            addItem(HammerItems.NETHERITE_FIVE_HAMMER, "Netherite Reinforced Hammer");
            addItem(HammerItems.STONE_FIVE_IMPACT_HAMMER, "Stone Reinforced Impact Hammer");
            addItem(HammerItems.IRON_FIVE_IMPACT_HAMMER, "Iron Reinforced Impact Hammer");
            addItem(HammerItems.GOLD_FIVE_IMPACT_HAMMER, "Gold Reinforced Impact Hammer");
            addItem(HammerItems.DIAMOND_FIVE_IMPACT_HAMMER, "Diamond Reinforced Impact Hammer");
            addItem(HammerItems.NETHERITE_FIVE_IMPACT_HAMMER, "Netherite Reinforced Impact Hammer");
            addItem(HammerItems.STONE_FIVE_DESTROY_HAMMER, "Stone Destructor Hammer");
            addItem(HammerItems.IRON_FIVE_DESTROY_HAMMER, "Iron Destructor Hammer");
            addItem(HammerItems.GOLD_FIVE_DESTROY_HAMMER, "Gold Destructor Hammer");
            addItem(HammerItems.DIAMOND_FIVE_DESTROY_HAMMER, "Diamond Destructor Hammer");
            addItem(HammerItems.NETHERITE_FIVE_DESTROY_HAMMER, "Netherite Destructor Hammer");

            addItem(HammerItems.IMPACT_CORE, "Impact Core");
            addItem(HammerItems.REINFORCED_CORE, "Reinforced Core");
            addItem(HammerItems.REINFORCED_IMPACT_CORE, "Reinforced Impact Core");
            addItem(HammerItems.DESTRUCTOR_CORE, "Destruction Core");

            add("itemGroup.justhammers.justhammers_tab", "Just Hammers");
        }
    }

    public static class ItemModelGen extends ItemModelProvider {
        public ItemModelGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Hammers.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            HammerItems.HAMMERS.forEach(this::handHeldItem);

            handHeldItem(HammerItems.IMPACT_CORE);
            handHeldItem(HammerItems.REINFORCED_CORE);
            handHeldItem(HammerItems.REINFORCED_IMPACT_CORE);
            handHeldItem(HammerItems.DESTRUCTOR_CORE);
        }

        private void handHeldItem(RegistrySupplier<Item> item) {
            var path = item.getId().getPath();
            singleTexture(path, mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
        }
    }
}
