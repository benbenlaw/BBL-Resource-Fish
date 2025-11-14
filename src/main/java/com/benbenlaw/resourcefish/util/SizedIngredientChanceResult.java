package com.benbenlaw.resourcefish.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public record SizedIngredientChanceResult(SizedIngredient output, float chance) {
    public static final SizedIngredientChanceResult EMPTY;
    public static final Codec<SizedIngredientChanceResult> CODEC;

    public ItemStack rollOutput(RandomSource rand) {
        int outputAmount = this.output.count();

        for(int roll = 0; roll < this.output.count(); ++roll) {
            if (rand.nextFloat() > this.chance) {
                --outputAmount;
            }
        }

        if (outputAmount == 0) {
            return ItemStack.EMPTY;
        } else {
            ItemStack out = this.output.getItems()[0].copy();
            out.setCount(outputAmount);
            return out;
        }
    }

    public void write(RegistryFriendlyByteBuf buffer) {
        SizedIngredient.STREAM_CODEC.encode(buffer, this.output);
        buffer.writeFloat(this.chance());
    }

    public static SizedIngredientChanceResult read(RegistryFriendlyByteBuf buffer) {
        return new SizedIngredientChanceResult(SizedIngredient.STREAM_CODEC.decode(buffer), buffer.readFloat());
    }

    static {
        EMPTY = new SizedIngredientChanceResult(new SizedIngredient(Ingredient.EMPTY, 1), 1.0F);
        CODEC = RecordCodecBuilder.create((inst) ->
                inst.group(SizedIngredient.FLAT_CODEC.fieldOf("ingredient").forGetter(SizedIngredientChanceResult::output),
                        Codec.FLOAT.optionalFieldOf("chance", 1.0F).forGetter(SizedIngredientChanceResult::chance))
                        .apply(inst, SizedIngredientChanceResult::new));
    }
}