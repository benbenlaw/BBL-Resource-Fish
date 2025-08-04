package com.benbenlaw.resourcefish.recipe;

import com.benbenlaw.resourcefish.block.entity.TankControllerBlockEntity;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.item.ResourceFishDataComponents;
import com.benbenlaw.resourcefish.screen.TankControllerScreen;
import com.benbenlaw.resourcefish.util.ResourceType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record FishBreedingRecipe(ResourceLocation parentIngredientA,
                                 ResourceLocation parentIngredientB,
                                 SizedIngredient breedingIngredient,
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

            boolean fishTypeA = fishTypes.contains(parentIngredientA);
            boolean fishTypeB = fishTypes.contains(parentIngredientB);
            boolean hasBreedingItem = breedingIngredient.test(container.getItem(TankControllerBlockEntity.RECIPE_SLOT_1));

            return fishTypeA && fishTypeB && hasBreedingItem;
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
        return FishBreedingRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return FishBreedingRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<FishBreedingRecipe> {
        private Type() {

        }

        public static final FishBreedingRecipe.Type INSTANCE = new FishBreedingRecipe.Type();
    }

    public static class Serializer implements RecipeSerializer<FishBreedingRecipe> {
        public static final FishBreedingRecipe.Serializer INSTANCE = new Serializer();

        public final MapCodec<FishBreedingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                        ResourceLocation.CODEC.fieldOf("parent_a").forGetter(FishBreedingRecipe::parentIngredientA),
                        ResourceLocation.CODEC.fieldOf("parent_b").forGetter(FishBreedingRecipe::parentIngredientB),
                        SizedIngredient.FLAT_CODEC.fieldOf("breeding_ingredient").forGetter(FishBreedingRecipe::breedingIngredient),
                        Codec.INT.fieldOf("duration").forGetter(FishBreedingRecipe::duration),
                        Codec.DOUBLE.fieldOf("chance").forGetter(FishBreedingRecipe::chance),
                        ResourceLocation.CODEC.fieldOf("created_fish").forGetter(FishBreedingRecipe::createdFish)
                ).apply(instance, FishBreedingRecipe::new)
        );

        private final StreamCodec<RegistryFriendlyByteBuf, FishBreedingRecipe> STREAM_CODEC = StreamCodec.of(
                FishBreedingRecipe.Serializer::write, FishBreedingRecipe.Serializer::read);

        @Override
        public MapCodec<FishBreedingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FishBreedingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static FishBreedingRecipe read(RegistryFriendlyByteBuf buffer) {
            ResourceLocation parentA = ResourceLocation.STREAM_CODEC.decode(buffer);
            ResourceLocation parentB = ResourceLocation.STREAM_CODEC.decode(buffer);
            SizedIngredient breedingItem = SizedIngredient.STREAM_CODEC.decode(buffer);
            int duration = buffer.readInt();
            double chance = buffer.readDouble();
            ResourceLocation createdFish = ResourceLocation.STREAM_CODEC.decode(buffer);

            return new FishBreedingRecipe(parentA, parentB, breedingItem, duration, chance, createdFish);
        }

        private static FishBreedingRecipe write(RegistryFriendlyByteBuf buffer, FishBreedingRecipe recipe) {
            ResourceLocation.STREAM_CODEC.encode(buffer, recipe.parentIngredientA);
            ResourceLocation.STREAM_CODEC.encode(buffer, recipe.parentIngredientB);
            SizedIngredient.STREAM_CODEC.encode(buffer, recipe.breedingIngredient);
            buffer.writeInt(recipe.duration);
            buffer.writeDouble(recipe.chance);
            ResourceLocation.STREAM_CODEC.encode(buffer, recipe.createdFish);

            return recipe;
        }
    }
}
