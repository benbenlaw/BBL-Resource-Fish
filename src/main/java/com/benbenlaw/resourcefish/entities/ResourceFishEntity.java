package com.benbenlaw.resourcefish.entities;

import com.benbenlaw.resourcefish.item.ResourceFishItems;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ResourceFishEntity extends AbstractSchoolingFish {
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(ResourceFishEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> DATA_RESOURCE_TYPE = SynchedEntityData.defineId(ResourceFishEntity.class, EntityDataSerializers.STRING);

    private int dropTimer = 0;
    private final Level level;

    public ResourceFishEntity(EntityType<? extends AbstractSchoolingFish> type, Level level) {
        super(type, level);
        this.level = level;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide) {
            dropTimer++;

            ResourceType resourceType = getResourceType();
            int ticksPerDrop = resourceType.getDropIntervalTicks();

            if (dropTimer >= ticksPerDrop) {
                dropTimer = 0;

                ItemStack dropStack = ResourceType.getDropForResourceType(resourceType);

                if (!dropStack.isEmpty()) {
                    this.spawnAtLocation(dropStack);
                }
            }
        }
    }

    @Override
    protected @NotNull SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(ResourceFishItems.RESOURCE_FISH_BUCKET.get());
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0D);
    }

    public record Variant(Pattern pattern, DyeColor baseColor, DyeColor patternColor) {
        public int getPackedId() {
            return (pattern.getPackedId() & 0xFFFF)
                    | (baseColor.getId() << 16)
                    | (patternColor.getId() << 24);
        }

        public static Variant fromPackedId(int packedId) {
            Pattern pattern = Pattern.byId(packedId & 0xFFFF);
            DyeColor base = DyeColor.byId((packedId >> 16) & 0xFF);
            DyeColor overlay = DyeColor.byId((packedId >> 24) & 0xFF);
            return new Variant(pattern, base, overlay);
        }
    }

    public enum Pattern {
        SMALL_STRIPE(0, Base.SMALL),
        LARGE_DOT(1, Base.LARGE);

        public enum Base { SMALL, LARGE }

        private final int id;
        private final Base size;

        Pattern(int id, Base size) {
            this.id = id;
            this.size = size;
        }

        public int getPackedId() {
            return (size == Base.LARGE ? 1 : 0) | (id << 8);
        }

        public static Pattern byId(int id) {
            for (Pattern p : values()) {
                if (p.getPackedId() == id) return p;
            }
            return SMALL_STRIPE;
        }

        public Base getBase() {
            return size;
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT, 0);
        builder.define(DATA_RESOURCE_TYPE, ResourceType.NONE.toString());
    }

    public Variant getVariant() {
        return Variant.fromPackedId(this.entityData.get(DATA_VARIANT));
    }

    public void setVariant(Variant variant) {
        this.entityData.set(DATA_VARIANT, variant.getPackedId());
    }

    public ResourceType getResourceType() {
        String idString = this.entityData.get(DATA_RESOURCE_TYPE);
        if (idString == null || idString.isEmpty()) {
            idString = ResourceType.NONE.getId().toString();
        }
        ResourceLocation id = ResourceLocation.parse(idString);
        ResourceType resourceType = ResourceType.REGISTRY.get(id);
        if (resourceType == null) {
            resourceType = ResourceType.NONE;
        }
        return resourceType;
    }

    public void setResourceType(ResourceType type) {
        this.entityData.set(DATA_RESOURCE_TYPE, type.getId().toString());
    }

    public int getResourceColor() {
        return getResourceType().getColor();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.entityData.get(DATA_VARIANT));
        tag.putString("ResourceType", this.entityData.get(DATA_RESOURCE_TYPE));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_VARIANT, tag.getInt("Variant"));
        if (tag.contains("ResourceType", Tag.TAG_STRING)) {
            this.entityData.set(DATA_RESOURCE_TYPE, tag.getString("ResourceType"));
        } else {
            // fallback if old int is still used or missing
            this.entityData.set(DATA_RESOURCE_TYPE, ResourceType.NONE.toString());
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, groupData);
        this.setVariant(generateRandomVariant(level.getRandom()));
        this.setResourceType(generateRandomResource(level.getRandom()));
        return data;
    }

    private Variant generateRandomVariant(RandomSource random) {
        Pattern pattern = Pattern.values()[random.nextInt(Pattern.values().length)];
        DyeColor base = DyeColor.values()[random.nextInt(DyeColor.values().length)];
        DyeColor overlay = DyeColor.values()[random.nextInt(DyeColor.values().length)];
        return new Variant(pattern, base, overlay);
    }

    private ResourceType generateRandomResource(RandomSource random) {
        List<ResourceType> values = new ArrayList<>(ResourceType.REGISTRY.values());
        if (values.isEmpty()) {
            return ResourceType.NONE; // fallback default
        }
        return values.get(random.nextInt(values.size()));
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        super.loadFromBucketTag(tag);

        if (tag.contains("BucketVariant", Tag.TAG_INT)) {
            setVariant(ResourceFishEntity.Variant.fromPackedId(tag.getInt("BucketVariant")));
        }

        if (tag.contains("BucketResourceType", Tag.TAG_STRING)) {
            setResourceType(ResourceType.REGISTRY.getOrDefault(ResourceLocation.parse(tag.getString("BucketResourceType")), ResourceType.NONE));
        }
    }


    @Override
    public void saveToBucketTag(ItemStack bucket) {
        super.saveToBucketTag(bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, data -> {
            data.putInt("BucketVariant", this.getVariant().getPackedId());
            data.putString("BucketResourceType", this.getResourceType().getId().toString());
        });
    }
}
