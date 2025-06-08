package pro.mikey.justhammers.fabric.data;

import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import pro.mikey.justhammers.HammerItems;
import pro.mikey.justhammers.HammerTags;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class DataGenerators implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var output = generator.createPack();

        output.addProvider(ItemModelGen::new);
        output.addProvider(LangGen::new);
        output.addProvider(RecipeGen::new);
        output.addProvider(ItemTagsGen::new);
    }

    public static class ItemTagsGen extends FabricTagProvider.ItemTagProvider {
        public ItemTagsGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            for (var hammer : HammerItems.HAMMERS) {
                tag(HammerTags.HAMMERS).add(hammer.getKey());
                tag(ItemTags.PICKAXES).add(hammer.getKey());
            }
        }
    }

    public static class RecipeGen extends FabricRecipeProvider {
        public RecipeGen(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void buildRecipes(RecipeOutput consumer) {
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
            core(HammerItems.REINFORCED_CORE, Items.REDSTONE_BLOCK, HammerItems.IMPACT_CORE.get(), Items.GOLD_BLOCK, Items.GOLD_BLOCK).save(consumer);
            core(HammerItems.REINFORCED_IMPACT_CORE, Items.REDSTONE_BLOCK, HammerItems.REINFORCED_CORE.get(), Items.DIAMOND_BLOCK, Items.GOLD_BLOCK).save(consumer);
            core(HammerItems.DESTRUCTOR_CORE, Items.REDSTONE_BLOCK, HammerItems.REINFORCED_IMPACT_CORE.get(), Items.DIAMOND_BLOCK, Items.DIAMOND_BLOCK).save(consumer);
        }

        private RecipeBuilder standardHammer(Supplier<Item> hammer, ItemLike material) {
            return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hammer.get())
                    .define('a', material)
                    .define('b', Items.STICK)
                    .pattern("aba")
                    .pattern(" ba")
                    .pattern(" b ")
                    .unlockedBy("has_material", has(material));
        }

        private RecipeBuilder coreHammer(Supplier<Item> hammer, Supplier<Item> core, ItemLike material) {
            return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hammer.get())
                    .define('a', material)
                    .define('b', Items.STICK)
                    .define('c', core.get())
                    .pattern("aca")
                    .pattern(" ba")
                    .pattern(" b ")
                    .unlockedBy("has_material", has(material));
        }

        private RecipeBuilder core(Supplier<Item> result, Item outside, Item inside, Item left, Item right) {
            return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get())
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

    public static class LangGen extends FabricLanguageProvider {
        protected LangGen(FabricDataOutput dataOutput) {
            super(dataOutput, "en_us");
        }

        @Override
        public void generateTranslations(TranslationBuilder builder) {
            builder.add(HammerItems.STONE_HAMMER.get(), "Stone Hammer");
            builder.add(HammerItems.IRON_HAMMER.get(), "Iron Hammer");
            builder.add(HammerItems.GOLD_HAMMER.get(), "Gold Hammer");
            builder.add(HammerItems.DIAMOND_HAMMER.get(), "Diamond Hammer");
            builder.add(HammerItems.NETHERITE_HAMMER.get(), "Netherite Hammer");
            builder.add(HammerItems.STONE_IMPACT_HAMMER.get(), "Stone Impact Hammer");
            builder.add(HammerItems.IRON_IMPACT_HAMMER.get(), "Iron Impact Hammer");
            builder.add(HammerItems.GOLD_IMPACT_HAMMER.get(), "Gold Impact Hammer");
            builder.add(HammerItems.DIAMOND_IMPACT_HAMMER.get(), "Diamond Impact Hammer");
            builder.add(HammerItems.NETHERITE_IMPACT_HAMMER.get(), "Netherite Impact Hammer");
            builder.add(HammerItems.STONE_FIVE_HAMMER.get(), "Stone Reinforced Hammer");
            builder.add(HammerItems.IRON_FIVE_HAMMER.get(), "Iron Reinforced Hammer");
            builder.add(HammerItems.GOLD_FIVE_HAMMER.get(), "Gold Reinforced Hammer");
            builder.add(HammerItems.DIAMOND_FIVE_HAMMER.get(), "Diamond Reinforced Hammer");
            builder.add(HammerItems.NETHERITE_FIVE_HAMMER.get(), "Netherite Reinforced Hammer");
            builder.add(HammerItems.STONE_FIVE_IMPACT_HAMMER.get(), "Stone Reinforced Impact Hammer");
            builder.add(HammerItems.IRON_FIVE_IMPACT_HAMMER.get(), "Iron Reinforced Impact Hammer");
            builder.add(HammerItems.GOLD_FIVE_IMPACT_HAMMER.get(), "Gold Reinforced Impact Hammer");
            builder.add(HammerItems.DIAMOND_FIVE_IMPACT_HAMMER.get(), "Diamond Reinforced Impact Hammer");
            builder.add(HammerItems.NETHERITE_FIVE_IMPACT_HAMMER.get(), "Netherite Reinforced Impact Hammer");
            builder.add(HammerItems.STONE_FIVE_DESTROY_HAMMER.get(), "Stone Destructor Hammer");
            builder.add(HammerItems.IRON_FIVE_DESTROY_HAMMER.get(), "Iron Destructor Hammer");
            builder.add(HammerItems.GOLD_FIVE_DESTROY_HAMMER.get(), "Gold Destructor Hammer");
            builder.add(HammerItems.DIAMOND_FIVE_DESTROY_HAMMER.get(), "Diamond Destructor Hammer");
            builder.add(HammerItems.NETHERITE_FIVE_DESTROY_HAMMER.get(), "Netherite Destructor Hammer");

            builder.add(HammerItems.IMPACT_CORE.get(), "Impact Core");
            builder.add(HammerItems.REINFORCED_CORE.get(), "Reinforced Core");
            builder.add(HammerItems.REINFORCED_IMPACT_CORE.get(), "Reinforced Impact Core");
            builder.add(HammerItems.DESTRUCTOR_CORE.get(), "Destruction Core");

            builder.add("justhammers.tooltip.durability_warning", "Hammer durability nearing 0%!");
            builder.add("justhammers.tooltip.size", "Mines a %sx%sx%s area");

            builder.add("itemGroup.justhammers.justhammers_tab", "Just Hammers");
        }
    }

    public static class ItemModelGen extends FabricModelProvider {
        public ItemModelGen(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerator) {
            HammerItems.HAMMERS.forEach(e -> handHeldItemHandheld(itemModelGenerator, e));

            handHeldItem(itemModelGenerator, HammerItems.IMPACT_CORE);
            handHeldItem(itemModelGenerator, HammerItems.REINFORCED_CORE);
            handHeldItem(itemModelGenerator, HammerItems.REINFORCED_IMPACT_CORE);
            handHeldItem(itemModelGenerator, HammerItems.DESTRUCTOR_CORE);
        }

        private void handHeldItemHandheld(ItemModelGenerators itemModelGenerator, RegistrySupplier<Item> item) {
            itemModelGenerator.generateFlatItem(item.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        }

        private void handHeldItem(ItemModelGenerators itemModelGenerator, RegistrySupplier<Item> item) {
            itemModelGenerator.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
        }
    }
}
