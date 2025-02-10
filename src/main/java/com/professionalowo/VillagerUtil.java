package com.professionalowo;

import com.professionalowo.items.ModItems;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class VillagerUtil {
    public static final Item TEMPT_ITEM = ModItems.INSTANCE.getVILLAGER_CHARM().asItem();

    public static Predicate<ItemStack> isTemptItem() {
        return stack -> stack.isOf(TEMPT_ITEM);
    }

    public static TemptGoal getTemptGoal(VillagerEntity villager) {
        return new TemptGoal(villager, 1, VillagerUtil.isTemptItem(), false);
    }
}
