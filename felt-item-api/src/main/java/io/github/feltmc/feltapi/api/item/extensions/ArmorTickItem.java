package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ArmorTickItem {
    /**
     * Called to tick armor in the armor slot. Override to do something
     */
    default void onArmorTick(ItemStack stack, World level, PlayerEntity player) {
    }
}