package net.ugi.wildsprout.config;

public class ConfigHandler {
    public int config_version = 2;

    @Comment("-----------Enable and disable biomes-----------\n")
    public boolean PlainsEnabled = true;
    public boolean SunFlowerPlainsEnabled = true;
    public boolean SnowyPlainsEnabled = true;

    @Comment("-----------Disable features-----------\n\nEnables or disables Snow being able to generate on ice\nIt is not recommended to disable this after world generation\n")
    public boolean SnowOnIceEnabled = true;

    @Comment("Enables or disables snow generating in layers in the snowy plains")
    public boolean LayeredSnowEnabled = true;

    @Comment("Enables or disables wheat fields generating in the plains and sunflower plains")
    public boolean WheatFieldsEnabled = true;

    @Comment("Enables or disables berry patches generating in the snowy plains")
    public boolean BerryPatchesEnabled = true;

    @Comment("Enables or disables bushes generating in all the plains biomes")
    public boolean BushesEnabled = true;

    @Comment("Enables or disables dirt patches generating in all the plains biomes")
    public boolean DirtPatchesEnabled = true;

    @Comment("Enables or disables boulders generating all the plains biomes")
    public boolean BouldersEnabled = true;

    @Comment("Enables or disables rocks generating in all the plains biomes")
    public boolean RocksEnabled = true;

    @Comment("Enables or disables random paths generating in the plains and sunflower plains")
    public boolean RandomPathsEnabled = true;

    @Comment("Enables or disables lakes generating in the plains and sunflower plains")
    public boolean LakesEnabled = true;

    @Comment("Enables or disables improved pumpkin patches generating in the plains and sunflower plains")
    public boolean ImprovedPumpkinPatch = true;

    @Comment("Enables or disables lava generating in all the plains biomes (lava lakes and springs)")
    public boolean AllowLavaGenInPlains = false;

    @Comment("Enables or disables flower generation in the snowy plains")
    public boolean AllowFlowerGenInSnowyPlains = false;

}
