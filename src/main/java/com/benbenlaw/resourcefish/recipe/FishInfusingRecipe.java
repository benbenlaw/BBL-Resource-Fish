package com.benbenlaw.resourcefish.recipe;

import com.benbenlaw.resourcefish.block.entity.TankControllerBlockEntity;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.item.ResourceFishItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record FishInfusingRecipe(ResourceLocation fish,
                                 NonNullList<SizedIngredient> inputs,
                                 int duration,
                                 double chance,
                                 ResourceLocation createdFish) implements Recipe<RecipeInput> {

    @Override
    public boolean matches(RecipeInput container, Level level) {

        boolean hasInfusingUpgrade =
                container.getItem(TankControllerBlockEntity.UPGRADE_SLOT_1)
                        .is(ResourceFishItems.INFUSING_UPGRADE)
                        || container.getItem(TankControllerBlockEntity.UPGRADE_SLOT_2)
                        .is(ResourceFishItems.INFUSING_UPGRADE)
                        || container.getItem(TankControllerBlockEntity.UPGRADE_SLOT_3)
                        .is(ResourceFishItems.INFUSING_UPGRADE)
                        || container.getItem(TankControllerBlockEntity.UPGRADE_SLOT_4)
                        .is(ResourceFishItems.INFUSING_UPGRADE);

        if (!hasInfusingUpgrade) return false;

        if (!(container instanceof TankRecipeInput tankInput)) return false;

        TankControllerBlockEntity entity =
                (TankControllerBlockEntity) level.getBlockEntity(tankInput.getPos());

        if (entity == null || entity.fishPool == null || entity.fishPool.isEmpty()) {
            return false;
        }

        boolean fishTypeMatches = false;
        for (ResourceFishEntity fishEntity : entity.fishPool) {
            ResourceLocation typeId = fishEntity.getResourceType().getId();
            if (typeId != null && typeId.equals(this.fish)) {
                fishTypeMatches = true;
                break;
            }
        }

        if (!fishTypeMatches) return false;

        boolean[] usedSlots = new boolean[3];

        for (SizedIngredient ingredient : inputs) {

            if (ingredient.ingredient() == Ingredient.EMPTY) {
                continue;
            }

            boolean matched = false;

            for (int slot = 0; slot < 3; slot++) {
                if (usedSlots[slot]) continue;

                ItemStack stack = container.getItem(
                        TankControllerBlockEntity.RECIPE_SLOT_1 + slot
                );

                if (ingredient.test(stack) && stack.getCount() >= ingredient.count()) {
                    usedSlots[slot] = true;
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                return false;
            }
        }

        return true;
    }


    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider p_336125_) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FishInfusingRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return FishInfusingRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<FishInfusingRecipe> {
        private Type() {

        }

        public static final FishInfusingRecipe.Type INSTANCE = new FishInfusingRecipe.Type();
    }

    public static class Serializer implements RecipeSerializer<FishInfusingRecipe> {
        public static final FishInfusingRecipe.Serializer INSTANCE = new Serializer();

        public final MapCodec<FishInfusingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                        ResourceLocation.CODEC.fieldOf("fish").forGetter(FishInfusingRecipe::fish),

                        Codec.list(SizedIngredient.FLAT_CODEC).fieldOf("inputs").flatXmap(inputs -> {
                            if (inputs.size() > 3) {
                                    return DataResult.error(
                                            () -> "Too many inputs for infusing recipe! The maximum quantity of unique results is "
                                                    + 3);
                            }
                            NonNullList<SizedIngredient> nonNullList = NonNullList.create();
                            nonNullList.addAll(inputs);
                            return DataResult.success(nonNullList);
                        }, DataResult::success).forGetter(FishInfusingRecipe::inputs),
                        Codec.INT.fieldOf("duration").forGetter(FishInfusingRecipe::duration),
                        Codec.DOUBLE.fieldOf("chance").forGetter(FishInfusingRecipe::chance),
                        ResourceLocation.CODEC.fieldOf("created_fish").forGetter(FishInfusingRecipe::createdFish)
                ).apply(instance, FishInfusingRecipe::new )
        );

        private final StreamCodec<RegistryFriendlyByteBuf, FishInfusingRecipe> STREAM_CODEC = StreamCodec.of(
                FishInfusingRecipe.Serializer::write, FishInfusingRecipe.Serializer::read);

        @Override
        public MapCodec<FishInfusingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FishInfusingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static FishInfusingRecipe read(RegistryFriendlyByteBuf buffer) {
            ResourceLocation fish = ResourceLocation.STREAM_CODEC.decode(buffer);

            int inputCount = buffer.readInt();
            NonNullList<SizedIngredient> inputs = NonNullList.create();
            for (int i = 0; i < inputCount; i++) {
                inputs.add(SizedIngredient.STREAM_CODEC.decode(buffer));
            }


            int duration = buffer.readInt();
            double chance = buffer.readDouble();
            ResourceLocation createdFish = ResourceLocation.STREAM_CODEC.decode(buffer);

            return new FishInfusingRecipe(fish, inputs, duration, chance, createdFish);
        }

        private static FishInfusingRecipe write(RegistryFriendlyByteBuf buffer, FishInfusingRecipe recipe) {
            ResourceLocation.STREAM_CODEC.encode(buffer, recipe.fish);

            buffer.writeInt(recipe.inputs.size());
            for (SizedIngredient input : recipe.inputs) {
                SizedIngredient.STREAM_CODEC.encode(buffer, input);
            }

            buffer.writeInt(recipe.duration);
            buffer.writeDouble(recipe.chance);
            ResourceLocation.STREAM_CODEC.encode(buffer, recipe.createdFish);

            return recipe;
        }
    }
}
