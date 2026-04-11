package com.zyneonstudios.nerotvlive.projectsbase.objects;

import net.skinsrestorer.api.property.SkinVariant;

import java.util.UUID;

public class CharacterSkin {

    private final UUID uuid;
    private String skinName = "Neuer Skin";
    private String skinUrl = "https://i.ibb.co/6RSdt5hm/paulprimal4.png";
    private SkinVariant skinVariant = SkinVariant.CLASSIC;

    public CharacterSkin() {
        this(UUID.randomUUID());
    }

    public CharacterSkin(UUID uuid) {
        this.uuid = uuid;
    }

    public CharacterSkin(String skinName, String skinUrl, SkinVariant skinVariant) {
        this.uuid = UUID.randomUUID();
        this.skinName = skinName;
        this.skinUrl = skinUrl;
        this.skinVariant = skinVariant;
    }

    public CharacterSkin(String skinName, String skinUrl) {
        this.uuid = UUID.randomUUID();
        this.skinName = skinName;
        this.skinUrl = skinUrl;
    }

    public CharacterSkin(UUID uuid, String skinName, String skinUrl, SkinVariant skinVariant) {
        this.uuid = uuid;
        this.skinName = skinName;
        this.skinUrl = skinUrl;
        this.skinVariant = skinVariant;
    }

    public CharacterSkin(UUID uuid, String skinName, String skinUrl) {
        this.uuid = uuid;
        this.skinName = skinName;
        this.skinUrl = skinUrl;
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
    }

    public void setSkinUrl(String skinUrl) {
        this.skinUrl = skinUrl;
    }

    public void setSkinVariant(SkinVariant skinVariant) {
        this.skinVariant = skinVariant;
    }
}