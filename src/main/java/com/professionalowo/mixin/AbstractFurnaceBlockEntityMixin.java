package com.professionalowo.mixin;

import com.professionalowo.IAbstractFurnaceBlockEntityAccessor;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import org.spongepowered.asm.mixin.*;

@Mixin(AbstractFurnaceBlockEntity.class)
@Implements(@Interface(iface = IAbstractFurnaceBlockEntityAccessor.class, prefix = "professionaltombstones$"))
public class AbstractFurnaceBlockEntityMixin {
    @Mutable
    @Shadow @Final private RecipeManager.MatchGetter<SingleStackRecipeInput, ? extends AbstractCookingRecipe> matchGetter;

    public void professionaltombstones$setMatchGetter(RecipeManager.MatchGetter<SingleStackRecipeInput, ? extends AbstractCookingRecipe> matchGetter){
        this.matchGetter = matchGetter;
    }

    public RecipeManager.MatchGetter<SingleStackRecipeInput, ? extends AbstractCookingRecipe>  professionaltombstones$getMatchGetter(){
        return this.matchGetter;
    }
}
