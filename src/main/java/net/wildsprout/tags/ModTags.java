package net.wildsprout.tags;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.wildsprout.WildSproutPlains;


public class ModTags {

    public static class Blocks {

        public static final TagKey<Block> CAN_BE_REPLACED =
                createTag("can_be_replaced");


        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, WildSproutPlains.identifier( name));
        }
    }

    public static class Items {


        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, WildSproutPlains.identifier( name));
        }
    }

    public static class Fluids {

        private static TagKey<Fluid> of(String name) {
            return TagKey.of(RegistryKeys.FLUID, WildSproutPlains.identifier( name));
        }
    }

}
