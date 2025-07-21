package net.ugi.wildsprout.world.gen;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.structure.Structure;
import net.ugi.wildsprout.WildSproutPlains;

public class ModStructureKeys {
    public static RegistryKey<Structure> RIVER = of("river");

    private static RegistryKey<Structure> of(String id) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, WildSproutPlains.identifier(id));
    }

    public static void init() {
    }
}
