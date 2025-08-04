package com.benbenlaw.resourcefish.recipe;

import com.benbenlaw.resourcefish.block.entity.TankControllerBlockEntity;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record FishInfusingRecipe(ResourceLocation fish,
                                 SizedIngredient input1,
                                 SizedIngredient input2,
                                 SizedIngredient input3,
                                 int duration,
                                 double chance,
                                 ResourceLocation createdFish) implements Recipe<RecipeInput> {

    @Override
    public boolean matches(RecipeInput container, Level level) {


        if (TankControllerBlockEntity.fishPool != null) {

            List<ResourceLocation> fishTypes = new ArrayList<>();

            for (ResourceFishEntity fish  : TankControllerBlockEntity.fishPool) {
                if (fish.getResourceType().getId() != null) {
                    fishTypes.add(fish.getResourceType().getId());
                }
            }

            if (fishTypes.isEmpty()) return false;

            boolean fishType = fishTypes.contains(fish);

            boolean isInput1 = input1.test(container.getItem(TankControllerBlockEntity.RECIPE_SLOT_1));

            boolean isInput2 = Arrays.stream(input2.getItems()).toList().isEmpty() || input2.test(container.getItem(TankControllerBlockEntity.RECIPE_SLOT_2));
            boolean isInput3 = Arrays.stream(input3.getItems()).toList().isEmpty() || input3.test(container.getItem(TankControllerBlockEntity.RECIPE_SLOT_3));


            return fishType && isInput1 && isInput2 && isInput3;
        }


        return false;
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
                        SizedIngredient.FLAT_CODEC.fieldOf("input_1").forGetter(FishInfusingRecipe::input1),
                        SizedIngredient.FLAT_CODEC.optionalFieldOf("input_2", new SizedIngredient(Ingredient.EMPTY, 1)).forGetter(FishInfusingRecipe::input2),
                        SizedIngredient.FLAT_CODEC.optionalFieldOf("input_3", new SizedIngredient(Ingredient.EMPTY, 1)).forGetter(FishInfusingRecipe::input3),
                        Codec.INT.fieldOf("duration").forGetter(FishInfusingRecipe::duration),
                        Codec.DOUBLE.fieldOf("chance").forGetter(FishInfusingRecipe::chance),
                        ResourceLocation.CODEC.fieldOf("created_fish").forGetter(FishInfusingRecipe::createdFish)
                ).apply(instance, FishInfusingRecipe::new)
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
            SizedIngredient input1 = SizedIngredient.STREAM_CODEC.decode(buffer);
            SizedIngredient input2 = SizedIngredient.STREAM_CODEC.decode(buffer);
            SizedIngredient input3 = SizedIngredient.STREAM_CODEC.decode(buffer);
            int duration = buffer.readInt();
            double chance = buffer.readDouble();
            ResourceLocation createdFish = ResourceLocation.STREAM_CODEC.decode(buffer);

            return new FishInfusingRecipe(fish, input1, input2, input3, duration, chance, createdFish);
        }

        private static FishInfusingRecipe write(RegistryFriendlyByteBuf buffer, FishInfusingRecipe recipe) {
            ResourceLocation.STREAM_CODEC.encode(buffer, recipe.fish);
            SizedIngredient.STREAM_CODEC.encode(buffer, recipe.input1);
            SizedIngredient.STREAM_CODEC.encode(buffer, recipe.input2);
            SizedIngredient.STREAM_CODEC.encode(buffer, recipe.input3);
            buffer.writeInt(recipe.duration);
            buffer.writeDouble(recipe.chance);
            ResourceLocation.STREAM_CODEC.encode(buffer, recipe.createdFish);

            return recipe;
        }
    }
}
