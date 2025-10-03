package com.benbenlaw.resourcefish.recipe;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.resourcefish.block.CaviarProcessorBlock;
import com.benbenlaw.resourcefish.block.entity.CaviarProcessorBlockEntity;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record CaviarProcessorRecipe(ItemStack caviar,
                                    NonNullList<ChanceResult> results, FluidStack fluidStack) implements Recipe<RecipeInput> {

    @Override
    public boolean matches(RecipeInput container, Level level) {
        int[] inputs = CaviarProcessorBlockEntity.INPUT_SLOTS;
        int[] upgradeSlots = CaviarProcessorBlockEntity.UPGRADE_SLOTS;

        for (int input : inputs) {
            ItemStack stack = container.getItem(input);
            if (stack.isEmpty() || caviar.isEmpty()) continue;
            if (!ItemStack.isSameItemSameComponents(caviar, stack)) continue;

            if (fluidStack.isEmpty()) {
                return true;
            }

            if (container instanceof CaviarProcessorRecipeInput caviarProcessorRecipeInput) {
                for (int upgradeSlot : upgradeSlots) {
                    ItemStack upgradeStack = caviarProcessorRecipeInput.getItemHandler().getStackInSlot(upgradeSlot);
                    if (upgradeStack.isEmpty()) continue;
                    if (upgradeStack.is(ResourceFishItems.TANK_UPGRADE.get())) {

                        IFluidHandler tank = caviarProcessorRecipeInput.getFluidHandler();
                        FluidStack current = tank.getFluidInTank(0);

                        // ✅ Only allow if tank is empty OR contains the same fluid
                        if (current.isEmpty() || current.is(fluidStack.getFluid())) {
                            int canFill = tank.fill(fluidStack.copy(), IFluidHandler.FluidAction.SIMULATE);

                            // must be able to fit the full recipe output
                            if (canFill >= fluidStack.getAmount()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }


    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        if (!results.isEmpty()) {
            return results.getFirst().stack();
        } else {
            return ItemStack.EMPTY;
        }
    }

    public List<ItemStack> getResults() {
        return getRollResults().stream()
                .map(ChanceResult::stack)
                .collect(Collectors.toList());
    }

    public NonNullList<ChanceResult> getRollResults() {
        return this.results;
    }

    public List<ItemStack> rollResults(RandomSource rand) {
        List<ItemStack> results = new ArrayList<>();
        List<ChanceResult> rollResults = getRollResults();
        for (ChanceResult output : rollResults) {
            ItemStack stack = output.rollOutput(rand);
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider provider) {
        if (results.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return results.getFirst().stack().copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CaviarProcessorRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return CaviarProcessorRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<CaviarProcessorRecipe> {
        private Type() {

        }

        public static final CaviarProcessorRecipe.Type INSTANCE = new CaviarProcessorRecipe.Type();
    }

    public static class Serializer implements RecipeSerializer<CaviarProcessorRecipe> {
        public static final CaviarProcessorRecipe.Serializer INSTANCE = new Serializer();

        public final MapCodec<CaviarProcessorRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                        ItemStack.CODEC.fieldOf("caviar").forGetter(CaviarProcessorRecipe::caviar),
                        Codec.list(ChanceResult.CODEC).fieldOf("results").flatXmap(chanceResults -> {
                            NonNullList<ChanceResult> nonNullList = NonNullList.create();
                            nonNullList.addAll(chanceResults);
                            return DataResult.success(nonNullList);
                        }, DataResult::success).forGetter(CaviarProcessorRecipe::getRollResults),
                        FluidStack.OPTIONAL_CODEC.fieldOf("output_fluid").forGetter(CaviarProcessorRecipe::fluidStack)
                ).apply(instance, CaviarProcessorRecipe::new)
        );

        private final StreamCodec<RegistryFriendlyByteBuf, CaviarProcessorRecipe> STREAM_CODEC = StreamCodec.of(
                CaviarProcessorRecipe.Serializer::write, CaviarProcessorRecipe.Serializer::read);

        @Override
        public MapCodec<CaviarProcessorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CaviarProcessorRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static CaviarProcessorRecipe read(RegistryFriendlyByteBuf buffer) {
            ItemStack caviar = ItemStack.STREAM_CODEC.decode(buffer);
            int size = buffer.readVarInt();
            NonNullList<ChanceResult> outputs = NonNullList.withSize(size, ChanceResult.EMPTY);
            outputs.replaceAll(ignored -> ChanceResult.read(buffer));
            FluidStack fluidStack = FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new CaviarProcessorRecipe(caviar, outputs, fluidStack);
        }

        private static CaviarProcessorRecipe write(RegistryFriendlyByteBuf buffer, CaviarProcessorRecipe recipe) {
            ItemStack.STREAM_CODEC.encode(buffer, recipe.caviar);
            buffer.writeVarInt(recipe.results.size());
            for (ChanceResult output : recipe.results) {
                output.write(buffer);
            }
            FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.fluidStack);

            return recipe;
        }
    }
}
