package net.ugi.wildsprout.config;

public class ConfigHandler {
    public int config_version = 2;

    @Comment("Please make an issue/feature request on github if you want to configure something that's not in here\n-----------Enable and disable biomes-----------\n")
    public boolean PlainsEnabled = true;
    public boolean SunFlowerPlainsEnabled = true;
    public boolean SnowyPlainsEnabled = true;

    @Comment("-----------Disable features-----------\n\nEnables or disables Snow being able to generate on ice\nIt is not recommended to disable this after world generation\n")
    public boolean SnowOnIceEnabled = true;
    @Comment("Enables or disables snow generating in layers in the snowy plains")
    public boolean LayeredSnowEnabled = true;
    @Comment("Enables or disables wheat fields generating in the plains and sunflower plains")
    public boolean WheatFieldsEnabled = true;
}
