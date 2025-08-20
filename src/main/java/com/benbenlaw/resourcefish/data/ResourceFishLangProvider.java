package com.benbenlaw.resourcefish.data;

import com.benbenlaw.resourcefish.ResourceFish;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ResourceFishLangProvider extends LanguageProvider {

    public ResourceFishLangProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ResourceFish.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        //Creative Tab
        add("itemGroup.resourcefish", "Resource Fish");

        //JEI
        addJEITranslation("spawn_biome", "Spawn Biome(s):");
        addJEITranslation("chance", "Chance: ");
        addJEITranslation("fish_in_tank_area", "Fish must be in tank area!");
        addJEITranslation("resource_fish", "Resource Fish");
        addJEITranslation("duration", "Duration: %s");

        //Items
        addItemTranslation("caviar", "Caviar");
        addItemTranslation("resource_fish_spawn_egg", "%s Resource Fish Spawn Egg");
        addItemTranslation("resource_fish_bucket", "%s Resource Fish Bucket");
        addItemTranslation("depth_upgrade_1", "Depth Upgrade 1");
        addItemTranslation("depth_upgrade_2", "Depth Upgrade 2");
        addItemTranslation("depth_upgrade_3", "Depth Upgrade 3");
        addItemTranslation("width_upgrade_1", "Width Upgrade 1");
        addItemTranslation("width_upgrade_2", "Width Upgrade 2");
        addItemTranslation("width_upgrade_3", "Width Upgrade 3");
        addItemTranslation("speed_upgrade_1", "Speed Upgrade 1");
        addItemTranslation("speed_upgrade_2", "Speed Upgrade 2");
        addItemTranslation("speed_upgrade_3", "Speed Upgrade 3");
        addItemTranslation("speed_upgrade_4", "Speed Upgrade 4");
        addItemTranslation("speed_upgrade_5", "Speed Upgrade 5");
        addItemTranslation("tank_upgrade", "Tank Upgrade");


        //Blocks
        addBlockTranslation("tank_controller", "Resource Fish Tank");
        addBlockTranslation("caviar_processor", "Caviar Processor");

        //Recipes
        addRecipeTranslation("caviar_processor", "Caviar Processor");
        addRecipeTranslation("fish_breeding", "Fish Breeding");
        addRecipeTranslation("fish_infusing", "Fish Infusing");
        addRecipeTranslation("fish_drops", "Fish Drops");


    }

    private void addItemTranslation(String name, String translation) {
        add("item." + ResourceFish.MOD_ID + "." + name, translation);
    }
    private void addBlockTranslation(String name, String translation) {
        add("block." + ResourceFish.MOD_ID + "." + name, translation);
    }
    private void addJEITranslation(String name, String translation) {
        add("jei." + ResourceFish.MOD_ID + "." + name, translation);
    }
    private void addRecipeTranslation(String name, String translation) {
        add("recipe." + ResourceFish.MOD_ID + "." + name, translation);
    }


}