package com.zyneonstudios.nerotvlive.projectsbase.objects;

import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types.Config;
import net.skinsrestorer.api.property.SkinVariant;

import java.util.UUID;

public class CharacterSkin {

    private final UUID uuid;
    private String skinName = "Neuer Skin";
    private String skinUrl = "https://i.ibb.co/Wvs8RJWw/steve-planetminecraft-com-14481964-e1070.png";
    private SkinVariant skinVariant = SkinVariant.CLASSIC;
    private final Config config;

    public CharacterSkin() {
        this(UUID.randomUUID());
    }

    public CharacterSkin(UUID uuid) {
        this.uuid = uuid;
        this.config = new Config("characters/"+uuid+".yml");
        init();
    }

    public CharacterSkin(String skinName, String skinUrl, SkinVariant skinVariant) {
        this.uuid = UUID.randomUUID();
        this.skinName = skinName;
        this.skinUrl = skinUrl;
        this.skinVariant = skinVariant;
        this.config = new Config("characters/"+uuid+".yml");
        init();
    }

    public CharacterSkin(String skinName, String skinUrl) {
        this.uuid = UUID.randomUUID();
        this.skinName = skinName;
        this.skinUrl = skinUrl;
        this.config = new Config("characters/"+uuid+".yml");
        init();
    }

    public CharacterSkin(UUID uuid, String skinName, String skinUrl, SkinVariant skinVariant) {
        this.uuid = uuid;
        this.skinName = skinName;
        this.skinUrl = skinUrl;
        this.skinVariant = skinVariant;
        this.config = new Config("characters/"+uuid+".yml");
        init();
    }

    public CharacterSkin(UUID uuid, String skinName, String skinUrl) {
        this.uuid = uuid;
        this.skinName = skinName;
        this.skinUrl = skinUrl;
        this.config = new Config("characters/"+uuid+".yml");
        init();
    }

    private void init() {
        if(config.get("outfit.name")!=null) {
            skinName = config.get("outfit.name").toString();
        }
        if(config.get("outfit.variant")!=null) {
            skinVariant = SkinVariant.valueOf(config.get("outfit.variant").toString());
        }
        if(config.get("outfit.url")!=null) {
            skinUrl = config.get("outfit.url").toString();
        }
    }

    public SkinVariant getSkinVariant() {
        return skinVariant;
    }

    public String getSkinName() {
        return skinName;
    }

    public String getSkinUrl() {
        return skinUrl;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
        config.set("outfit.name",skinName);
    }

    public void setSkinUrl(String skinUrl) {
        this.skinUrl = skinUrl;
        config.set("outfit.url",skinUrl);
    }

    public void setSkinVariant(SkinVariant skinVariant) {
        this.skinVariant = skinVariant;
        config.set("outfit.variant",skinVariant.toString());
    }

    public Config getConfig() {
        return config;
    }
}