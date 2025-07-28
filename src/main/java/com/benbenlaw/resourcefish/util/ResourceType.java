package com.benbenlaw.resourcefish.util;

public enum ResourceType {
    NONE(0, 0xFFFFFF),       // white/no color
    IRON(1, 0xAAAAAA),       // gray-ish for iron
    GOLD(2, 0xFFD700),       // gold color
    DIAMOND(3, 0x00FFFF);    // cyan-ish for diamond

    private final int id;
    private final int color; // RGB hex color

    ResourceType(int id, int color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public static ResourceType byId(int id) {
        for (ResourceType type : values()) {
            if (type.id == id) return type;
        }
        return NONE; // default
    }
}