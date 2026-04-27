package net.ugi.wildsprout_plains.world.gen;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.structure.Structure;
import net.ugi.wildsprout_plains.WildSproutPlains;

public class ModStructureKeys {
    public static RegistryKey<Structure> RIVER = of("river");
    public static RegistryKey<Structure> FLOATING_ISLAND = of("floating_island");
    public static RegistryKey<Structure> GLASS_BALL_CLUSTER = of("glass_ball_cluster");

    private static RegistryKey<Structure> of(String id) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, WildSproutPlains.identifier(id));
    }

    public static void init() {
    }
}
