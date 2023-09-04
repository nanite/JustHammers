package pro.mikey.justhammers;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public interface HammerTags {
    TagKey<Block> HAMMER_NO_SMASHY = TagKey.create(Registries.BLOCK, new ResourceLocation(Hammers.MOD_ID, "hammer_no_smashy"));
}
