package com.brskng.optimized.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import java.util.List;

public final class BrskngsOptimizedClientConfig {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue ENABLE_SUPERB_WARFARE_PATCHES, SUPERB_WARFARE_FORCE_DISTANCE_CHECK_IN_RENDER, SUPERB_WARFARE_USE_CUTOUT_RENDER_TYPE;
    public static final ModConfigSpec.DoubleValue SUPERB_WARFARE_VEHICLE_RENDER_DISTANCE, SUPERB_WARFARE_CUTOUT_RENDER_TYPE_DISTANCE, SUPERB_WARFARE_PRISM_LASER_RENDER_DISTANCE, SUPERB_WARFARE_PRISM_LIGHT_RENDER_DISTANCE;

    public static final ModConfigSpec.BooleanValue ENABLE_IRONS_LIB_PATCHES, IRONS_LIB_SKIP_TRANSMOG_FOR_NON_PLAYERS;
    public static final ModConfigSpec.DoubleValue IRONS_LIB_TRANSMOG_RENDER_DISTANCE;

    public static final ModConfigSpec.BooleanValue ENABLE_TOUHOU_LITTLE_MAID_PATCHES, TLM_LOD_DISABLE_HELD_ITEM_LAYER, TLM_LOD_DISABLE_BACK_ITEM_LAYER, TLM_LOD_DISABLE_BACKPACK_LAYER, TLM_LOD_DISABLE_BANNER_LAYER, TLM_LOD_DISABLE_HEAD_LAYER;
    public static final ModConfigSpec.DoubleValue TLM_LOD_DISTANCE, TLM_CULLING_DISTANCE;
    public static final ModConfigSpec.EnumValue<TouhouLittleMaidMode> TLM_MODE;

    public static final ModConfigSpec.BooleanValue CREATE_ENABLED, CREATE_VIEW_PRIORITY, CREATE_AFFECT_SHAFTS, CREATE_AFFECT_COGWHEELS, CREATE_AFFECT_GEARBOXES, CREATE_AFFECT_CHAINS;
    public static final ModConfigSpec.DoubleValue CREATE_LOD_DISTANCE, CREATE_CULLING_DISTANCE, CREATE_LOD_SPEED_MULTIPLIER, CREATE_VIEW_PRIORITY_DOT;
    public static final ModConfigSpec.IntValue CREATE_UPDATE_INTERVAL, CREATE_MAX_INSTANCES;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CREATE_WHITELIST;

    public static final ModConfigSpec.BooleanValue FPS_HUD_ENABLED, FPS_HUD_BACKGROUND;
    public static final ModConfigSpec.IntValue FPS_HUD_X, FPS_HUD_Y, FPS_HUD_TEXT_COLOR;

    static {
        ModConfigSpec.Builder b = new ModConfigSpec.Builder();

        b.translation("brskngs_optimized.configuration.superb_warfare").push("superb_warfare");
        ENABLE_SUPERB_WARFARE_PATCHES = b.translation("brskngs_optimized.configuration.superb_warfare.enabled").comment("Enable Superb Warfare render optimizations.").define("enabled", true);
        SUPERB_WARFARE_VEHICLE_RENDER_DISTANCE = b.translation("brskngs_optimized.configuration.superb_warfare.vehicle_render_distance").comment("Superb Warfare vehicles farther than this distance will not be rendered.").defineInRange("vehicle_render_distance", 48.0, 8.0, 256.0);
        SUPERB_WARFARE_FORCE_DISTANCE_CHECK_IN_RENDER = b.translation("brskngs_optimized.configuration.superb_warfare.force_distance_check_in_render").comment("Also check distance inside VehicleRenderer.render as a safety net.").define("force_distance_check_in_render", true);
        SUPERB_WARFARE_USE_CUTOUT_RENDER_TYPE = b.translation("brskngs_optimized.configuration.superb_warfare.use_cutout_render_type_for_far_vehicles").comment("Use entityCutoutNoCull instead of entityTranslucent for far Superb Warfare vehicles.").define("use_cutout_render_type_for_far_vehicles", false);
        SUPERB_WARFARE_CUTOUT_RENDER_TYPE_DISTANCE = b.translation("brskngs_optimized.configuration.superb_warfare.cutout_render_type_distance").comment("Distance beyond which Superb Warfare vehicles use cutout render type if enabled.").defineInRange("cutout_render_type_distance", 24.0, 0.0, 256.0);
        SUPERB_WARFARE_PRISM_LASER_RENDER_DISTANCE = b.translation("brskngs_optimized.configuration.superb_warfare.prism_tank_laser_layer_render_distance").comment("Prism Tank laser layer render distance. 0 disables the layer entirely.").defineInRange("prism_tank_laser_layer_render_distance", 24.0, 0.0, 256.0);
        SUPERB_WARFARE_PRISM_LIGHT_RENDER_DISTANCE = b.translation("brskngs_optimized.configuration.superb_warfare.prism_tank_light_layer_render_distance").comment("Prism Tank light layer render distance. 0 disables the layer entirely.").defineInRange("prism_tank_light_layer_render_distance", 24.0, 0.0, 256.0);
        b.pop();

        b.translation("brskngs_optimized.configuration.irons_lib").push("irons_lib");
        ENABLE_IRONS_LIB_PATCHES = b.translation("brskngs_optimized.configuration.irons_lib.enabled").comment("Enable Iron's Lib / Iron's Spells transmog render optimizations.").define("enabled", true);
        IRONS_LIB_TRANSMOG_RENDER_DISTANCE = b.translation("brskngs_optimized.configuration.irons_lib.transmog_render_distance").comment("Living entities farther than this distance render without Iron's Lib transmog state.").defineInRange("transmog_render_distance", 24.0, 4.0, 256.0);
        IRONS_LIB_SKIP_TRANSMOG_FOR_NON_PLAYERS = b.translation("brskngs_optimized.configuration.irons_lib.skip_transmog_for_non_players").comment("If true, non-player living entities render normally without transmog.").define("skip_transmog_for_non_players", false);
        b.pop();

        b.translation("brskngs_optimized.configuration.touhou_little_maid").push("touhou_little_maid");
        ENABLE_TOUHOU_LITTLE_MAID_PATCHES = b.translation("brskngs_optimized.configuration.touhou_little_maid.enabled").comment("Enable Touhou Little Maid LOD/culling patches.").define("enabled", true);
        TLM_MODE = b.translation("brskngs_optimized.configuration.touhou_little_maid.mode").comment("OFF, CULLING, LOD, HYBRID.").defineEnum("mode", TouhouLittleMaidMode.HYBRID);
        TLM_LOD_DISTANCE = b.translation("brskngs_optimized.configuration.touhou_little_maid.lod_distance").comment("Beyond this distance, selected maid layers are skipped in lod/hybrid mode.").defineInRange("lod_distance", 18.0, 4.0, 256.0);
        TLM_CULLING_DISTANCE = b.translation("brskngs_optimized.configuration.touhou_little_maid.culling_distance").comment("Beyond this distance, maid entities are not rendered in culling/hybrid mode.").defineInRange("culling_distance", 32.0, 8.0, 256.0);
        TLM_LOD_DISABLE_HELD_ITEM_LAYER = b.translation("brskngs_optimized.configuration.touhou_little_maid.lod_disable_held_item_layer").define("lod_disable_held_item_layer", true);
        TLM_LOD_DISABLE_BACK_ITEM_LAYER = b.translation("brskngs_optimized.configuration.touhou_little_maid.lod_disable_back_item_layer").define("lod_disable_back_item_layer", true);
        TLM_LOD_DISABLE_BACKPACK_LAYER = b.translation("brskngs_optimized.configuration.touhou_little_maid.lod_disable_backpack_layer").define("lod_disable_backpack_layer", true);
        TLM_LOD_DISABLE_BANNER_LAYER = b.translation("brskngs_optimized.configuration.touhou_little_maid.lod_disable_banner_layer").define("lod_disable_banner_layer", true);
        TLM_LOD_DISABLE_HEAD_LAYER = b.translation("brskngs_optimized.configuration.touhou_little_maid.lod_disable_head_layer").define("lod_disable_head_layer", false);
        b.pop();

        b.translation("brskngs_optimized.configuration.create").push("create");
        CREATE_ENABLED = b.translation("brskngs_optimized.configuration.create.enabled").comment("Create patch is disabled by default because the current global speed hook can reduce FPS in large bases.").define("enabled", false);
        CREATE_LOD_DISTANCE = b.translation("brskngs_optimized.configuration.create.lod_distance").defineInRange("lod_distance", 16.0, 4.0, 128.0);
        CREATE_CULLING_DISTANCE = b.translation("brskngs_optimized.configuration.create.culling_distance").defineInRange("culling_distance", 64.0, 8.0, 256.0);
        CREATE_LOD_SPEED_MULTIPLIER = b.translation("brskngs_optimized.configuration.create.lod_speed_multiplier").defineInRange("lod_speed_multiplier", 0.25, 0.0, 1.0);
        CREATE_UPDATE_INTERVAL = b.translation("brskngs_optimized.configuration.create.update_interval").defineInRange("update_interval", 2, 1, 20);
        CREATE_VIEW_PRIORITY = b.translation("brskngs_optimized.configuration.create.view_priority").define("view_priority", true);
        CREATE_VIEW_PRIORITY_DOT = b.translation("brskngs_optimized.configuration.create.view_priority_dot").defineInRange("view_priority_dot", 0.86, 0.1, 1.0);
        CREATE_MAX_INSTANCES = b.translation("brskngs_optimized.configuration.create.max_instances").defineInRange("max_instances", 1200, 100, 20000);
        CREATE_AFFECT_SHAFTS = b.translation("brskngs_optimized.configuration.create.affect_shafts").define("affect_shafts", true);
        CREATE_AFFECT_COGWHEELS = b.translation("brskngs_optimized.configuration.create.affect_cogwheels").define("affect_cogwheels", true);
        CREATE_AFFECT_GEARBOXES = b.translation("brskngs_optimized.configuration.create.affect_gearboxes").define("affect_gearboxes", true);
        CREATE_AFFECT_CHAINS = b.translation("brskngs_optimized.configuration.create.affect_chains").define("affect_chains", true);
        CREATE_WHITELIST = b.translation("brskngs_optimized.configuration.create.whitelist").defineListAllowEmpty("whitelist", java.util.List.of("create:mechanical_press"), () -> "", o -> o instanceof String);
        b.pop();

        b.translation("brskngs_optimized.configuration.fps_hud").push("fps_hud");
        FPS_HUD_ENABLED = b.translation("brskngs_optimized.configuration.fps_hud.enabled").comment("Show FPS / average FPS / 1% low FPS HUD.").define("enabled", true);
        FPS_HUD_BACKGROUND = b.translation("brskngs_optimized.configuration.fps_hud.background").comment("Draw a translucent black background behind the FPS HUD.").define("background", true);
        FPS_HUD_X = b.translation("brskngs_optimized.configuration.fps_hud.x").comment("FPS HUD X position.").defineInRange("x", 6, 0, 4096);
        FPS_HUD_Y = b.translation("brskngs_optimized.configuration.fps_hud.y").comment("FPS HUD Y position.").defineInRange("y", 6, 0, 4096);
        FPS_HUD_TEXT_COLOR = b.translation("brskngs_optimized.configuration.fps_hud.text_color").comment("FPS HUD text color in RGB integer format.").defineInRange("text_color", 0xFFFFFF, 0, 0xFFFFFF);
        b.pop();

        SPEC = b.build();
    }

    public static boolean tlmEnabled(){ return ENABLE_TOUHOU_LITTLE_MAID_PATCHES.get() && TLM_MODE.get() != TouhouLittleMaidMode.OFF; }
    public static boolean tlmCullingEnabled(){ return tlmEnabled() && (TLM_MODE.get() == TouhouLittleMaidMode.CULLING || TLM_MODE.get() == TouhouLittleMaidMode.HYBRID); }
    public static boolean tlmLodEnabled(){ return tlmEnabled() && (TLM_MODE.get() == TouhouLittleMaidMode.LOD || TLM_MODE.get() == TouhouLittleMaidMode.HYBRID); }
    private BrskngsOptimizedClientConfig() {}
}
