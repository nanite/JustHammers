package pro.mikey.justhammers.fabric;

import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import pro.mikey.justhammers.HammerItems;
import pro.mikey.justhammers.HammerTags;
import pro.mikey.justhammers.recipe.RepairRecipe;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class DataGenerators implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator event) {
        var pack = event.createPack();

        pack.addProvider(RecipeGen::new);
        pack.addProvider(LangGen::new);
        pack.addProvider(ItemModelGen::new);
        pack.addProvider(ItemTagsGen::new);
    }

    public static class ItemTagsGen extends FabricTagProvider.ItemTagProvider {
        public ItemTagsGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            List<ResourceKey<Item>> hammers = HammerItems.HAMMERS.stream().map(e -> e.unwrapKey().get())
                    .toList();

            tag(ItemTags.DURABILITY_ENCHANTABLE).addAll(hammers);
            tag(ItemTags.MINING_LOOT_ENCHANTABLE).addAll(hammers);
            tag(ItemTags.VANISHING_ENCHANTABLE).addAll(hammers);
            tag(ItemTags.MINING_ENCHANTABLE).addAll(hammers);

            tag(HammerTags.HAMMERS).addAll(hammers);
            tag(ItemTags.PICKAXES).addAll(hammers);
        }
    }

    public static class RecipeGen extends FabricRecipeProvider {
        public RecipeGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public String getName() {
            return "Just Hammer Recipes";
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new RecipeProvider(provider, recipeOutput) {
                @Override
                public void buildRecipes() {
                    SpecialRecipeBuilder.special(RepairRecipe::new)
                            .save(this.output, "justhammers:repair");

                    standardHammer(HammerItems.STONE_HAMMER, Items.STONE);
                    standardHammer(HammerItems.IRON_HAMMER, Items.IRON_INGOT);
                    standardHammer(HammerItems.GOLD_HAMMER, Items.GOLD_INGOT);
                    standardHammer(HammerItems.DIAMOND_HAMMER, Items.DIAMOND);
                    standardHammer(HammerItems.NETHERITE_HAMMER, Items.NETHERITE_INGOT);

                    coreHammer(HammerItems.STONE_IMPACT_HAMMER, HammerItems.IMPACT_CORE, Items.STONE);
                    coreHammer(HammerItems.IRON_IMPACT_HAMMER, HammerItems.IMPACT_CORE, Items.IRON_BLOCK);
                    coreHammer(HammerItems.GOLD_IMPACT_HAMMER, HammerItems.IMPACT_CORE, Items.GOLD_BLOCK);
                    coreHammer(HammerItems.DIAMOND_IMPACT_HAMMER, HammerItems.IMPACT_CORE, Items.DIAMOND_BLOCK);
                    coreHammer(HammerItems.NETHERITE_IMPACT_HAMMER, HammerItems.IMPACT_CORE, Items.NETHERITE_BLOCK);

                    coreHammer(HammerItems.STONE_FIVE_HAMMER, HammerItems.REINFORCED_CORE, Items.STONE);
                    coreHammer(HammerItems.IRON_FIVE_HAMMER, HammerItems.REINFORCED_CORE, Items.IRON_BLOCK);
                    coreHammer(HammerItems.GOLD_FIVE_HAMMER, HammerItems.REINFORCED_CORE, Items.GOLD_BLOCK);
                    coreHammer(HammerItems.DIAMOND_FIVE_HAMMER, HammerItems.REINFORCED_CORE, Items.DIAMOND_BLOCK);
                    coreHammer(HammerItems.NETHERITE_FIVE_HAMMER, HammerItems.REINFORCED_CORE, Items.NETHERITE_BLOCK);

                    coreHammer(HammerItems.STONE_FIVE_IMPACT_HAMMER, HammerItems.REINFORCED_IMPACT_CORE, Items.STONE);
                    coreHammer(HammerItems.IRON_FIVE_IMPACT_HAMMER, HammerItems.REINFORCED_IMPACT_CORE, Items.IRON_BLOCK);
                    coreHammer(HammerItems.GOLD_FIVE_IMPACT_HAMMER, HammerItems.REINFORCED_IMPACT_CORE, Items.GOLD_BLOCK);
                    coreHammer(HammerItems.DIAMOND_FIVE_IMPACT_HAMMER, HammerItems.REINFORCED_IMPACT_CORE, Items.DIAMOND_BLOCK);
                    coreHammer(HammerItems.NETHERITE_FIVE_IMPACT_HAMMER, HammerItems.REINFORCED_IMPACT_CORE, Items.NETHERITE_BLOCK);

                    coreHammer(HammerItems.STONE_FIVE_DESTROY_HAMMER, HammerItems.DESTRUCTOR_CORE, Items.STONE);
                    coreHammer(HammerItems.IRON_FIVE_DESTROY_HAMMER, HammerItems.DESTRUCTOR_CORE, Items.IRON_BLOCK);
                    coreHammer(HammerItems.GOLD_FIVE_DESTROY_HAMMER, HammerItems.DESTRUCTOR_CORE, Items.GOLD_BLOCK);
                    coreHammer(HammerItems.DIAMOND_FIVE_DESTROY_HAMMER, HammerItems.DESTRUCTOR_CORE, Items.DIAMOND_BLOCK);
                    coreHammer(HammerItems.NETHERITE_FIVE_DESTROY_HAMMER, HammerItems.DESTRUCTOR_CORE, Items.NETHERITE_BLOCK);

                    // These kinda suck
                    core(HammerItems.IMPACT_CORE, Items.REDSTONE, HammerItems.NETHERITE_HAMMER.get(), Items.IRON_BLOCK, Items.GOLD_BLOCK);
                    core(HammerItems.REINFORCED_CORE, Items.REDSTONE_BLOCK, HammerItems.IMPACT_CORE.get(), Items.GOLD_BLOCK, Items.GOLD_BLOCK);
                    core(HammerItems.REINFORCED_IMPACT_CORE, Items.REDSTONE_BLOCK, HammerItems.REINFORCED_CORE.get(), Items.DIAMOND_BLOCK, Items.GOLD_BLOCK);
                    core(HammerItems.DESTRUCTOR_CORE, Items.REDSTONE_BLOCK, HammerItems.REINFORCED_IMPACT_CORE.get(), Items.DIAMOND_BLOCK, Items.DIAMOND_BLOCK);
                }

                private void standardHammer(Supplier<Item> hammer, ItemLike material) {
                    this.shaped(RecipeCategory.TOOLS, hammer.get())
                            .define('a', material)
                            .define('b', Items.STICK)
                            .pattern("aba")
                            .pattern(" ba")
                            .pattern(" b ")
                            .unlockedBy("has_material", has(material))
                            .save(this.output);
                }

                private void coreHammer(Supplier<Item> hammer, Supplier<Item> core, ItemLike material) {
                    this.shaped(RecipeCategory.TOOLS, hammer.get())
                            .define('a', material)
                            .define('b', Items.STICK)
                            .define('c', core.get())
                            .pattern("aca")
                            .pattern(" ba")
                            .pattern(" b ")
                            .unlockedBy("has_material", has(material))
                            .save(this.output);
                }

                private void core(Supplier<Item> result, Item outside, Item inside, Item left, Item right) {
                    this.shaped(RecipeCategory.TOOLS, result.get())
                            .define('a', outside)
                            .define('b', inside)
                            .define('c', left)
                            .define('d', right)
                            .pattern("aaa")
                            .pattern("cbd")
                            .pattern("aaa")
                            .unlockedBy("has_material", has(HammerItems.STONE_HAMMER.get()))
                            .save(this.output);
                }
            };
        }
    }

    public static class LangGen extends FabricLanguageProvider {
        protected LangGen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, "en_us", registryLookup);
        }

        @Override
        public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
            translationBuilder.add(HammerItems.STONE_HAMMER.get(), "Stone Hammer");
            translationBuilder.add(HammerItems.IRON_HAMMER.get(), "Iron Hammer");
            translationBuilder.add(HammerItems.GOLD_HAMMER.get(), "Gold Hammer");
            translationBuilder.add(HammerItems.DIAMOND_HAMMER.get(), "Diamond Hammer");
            translationBuilder.add(HammerItems.NETHERITE_HAMMER.get(), "Netherite Hammer");
            translationBuilder.add(HammerItems.STONE_IMPACT_HAMMER.get(), "Stone Impact Hammer");
            translationBuilder.add(HammerItems.IRON_IMPACT_HAMMER.get(), "Iron Impact Hammer");
            translationBuilder.add(HammerItems.GOLD_IMPACT_HAMMER.get(), "Gold Impact Hammer");
            translationBuilder.add(HammerItems.DIAMOND_IMPACT_HAMMER.get(), "Diamond Impact Hammer");
            translationBuilder.add(HammerItems.NETHERITE_IMPACT_HAMMER.get(), "Netherite Impact Hammer");
            translationBuilder.add(HammerItems.STONE_FIVE_HAMMER.get(), "Stone Reinforced Hammer");
            translationBuilder.add(HammerItems.IRON_FIVE_HAMMER.get(), "Iron Reinforced Hammer");
            translationBuilder.add(HammerItems.GOLD_FIVE_HAMMER.get(), "Gold Reinforced Hammer");
            translationBuilder.add(HammerItems.DIAMOND_FIVE_HAMMER.get(), "Diamond Reinforced Hammer");
            translationBuilder.add(HammerItems.NETHERITE_FIVE_HAMMER.get(), "Netherite Reinforced Hammer");
            translationBuilder.add(HammerItems.STONE_FIVE_IMPACT_HAMMER.get(), "Stone Reinforced Impact Hammer");
            translationBuilder.add(HammerItems.IRON_FIVE_IMPACT_HAMMER.get(), "Iron Reinforced Impact Hammer");
            translationBuilder.add(HammerItems.GOLD_FIVE_IMPACT_HAMMER.get(), "Gold Reinforced Impact Hammer");
            translationBuilder.add(HammerItems.DIAMOND_FIVE_IMPACT_HAMMER.get(), "Diamond Reinforced Impact Hammer");
            translationBuilder.add(HammerItems.NETHERITE_FIVE_IMPACT_HAMMER.get(), "Netherite Reinforced Impact Hammer");
            translationBuilder.add(HammerItems.STONE_FIVE_DESTROY_HAMMER.get(), "Stone Destructor Hammer");
            translationBuilder.add(HammerItems.IRON_FIVE_DESTROY_HAMMER.get(), "Iron Destructor Hammer");
            translationBuilder.add(HammerItems.GOLD_FIVE_DESTROY_HAMMER.get(), "Gold Destructor Hammer");
            translationBuilder.add(HammerItems.DIAMOND_FIVE_DESTROY_HAMMER.get(), "Diamond Destructor Hammer");
            translationBuilder.add(HammerItems.NETHERITE_FIVE_DESTROY_HAMMER.get(), "Netherite Destructor Hammer");

            translationBuilder.add(HammerItems.IMPACT_CORE.get(), "Impact Core");
            translationBuilder.add(HammerItems.REINFORCED_CORE.get(), "Reinforced Core");
            translationBuilder.add(HammerItems.REINFORCED_IMPACT_CORE.get(), "Reinforced Impact Core");
            translationBuilder.add(HammerItems.DESTRUCTOR_CORE.get(), "Destruction Core");

            translationBuilder.add("justhammers.tooltip.durability_warning", "Hammer durability nearing 0%!");
            translationBuilder.add("justhammers.tooltip.size", "Mines a %sx%sx%s area");

            translationBuilder.add("itemGroup.justhammers.justhammers_tab", "Just Hammers");
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
