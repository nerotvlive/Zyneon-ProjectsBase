package com.zyneonstudios.nerotvlive.projectsbase.objects;

import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class Character {

    private final UUID uuid;
    private String name;
    private String job;
    private final ArrayList<CharacterSkin> outfits = new ArrayList<>();
    private UUID selectedSkin = null;
    private final Config config;

    public Character(UUID uuid) {
        this.uuid = uuid;
        this.config = new Config("plugins/ProjectsBase/characters/"+uuid+".yml");
        config.checkEntry("character.name","Unbekannt");
        config.checkEntry("character.job","Arbeitslos");
        config.checkEntry("character.outfits",new ArrayList<>());
        this.name = config.get("character.name").toString();
        this.job = config.get("character.job").toString();
        initOutfits();
    }

    @SuppressWarnings("unchecked")
    private void initOutfits() {
        if(outfits.isEmpty()) {
            ArrayList<String> outfits = (ArrayList<String>)config.get("character.outfits");
            for(String uuid : outfits) {
                if(new File("plugins/ProjectsBase/characters/outfits/"+uuid+".yml").exists()) {
                    this.outfits.add(new CharacterSkin(UUID.fromString(uuid)));
                    if(this.outfits.getLast().getUUID().equals(selectedSkin)) {
                        selectedSkin = this.outfits.getLast().getUUID();
                    }
                }
            }
            if(outfits.isEmpty()) {
                CharacterSkin outfit = new CharacterSkin(UUID.randomUUID());
                this.outfits.add(outfit);
                outfits.add(outfit.getUUID().toString());
                config.set("character.selectedOutfit", outfit.getUUID().toString());
                config.set("character.outfits", outfits);
                selectedSkin = outfit.getUUID();
            }
            config.checkEntry("character.selectedOutfit", outfits.getFirst());
            selectedSkin = UUID.fromString(config.get("character.selectedOutfit").toString());
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<CharacterSkin> getOutfits() {
        return outfits;
    }

    public String getJob() {
        return job;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
        config.set("character.name",name);
    }

    public void setJob(String job) {
        this.job = job;
        config.set("character.job",job);
    }

    public void setOutfits(ArrayList<CharacterSkin> outfits) {
        this.outfits.clear();
        this.outfits.addAll(outfits);
        config.set("character.outfits",outfits.stream().map(CharacterSkin::getUUID).toList());
        setSelectedSkin(outfits.getFirst().getUUID());
        config.set("character.selectedOutfit",outfits.getFirst().getUUID().toString());
    }

    public void setSelectedSkin(UUID skin) {
        selectedSkin = skin;
        config.set("character.selectedOutfit",skin.toString());
    }

    public void addOutfit(CharacterSkin skin) {
        outfits.add(skin);
    }

    public void removeOutfit(CharacterSkin skin) {
        outfits.remove(skin);
    }

    public void removeOutfit(int index) {
        outfits.remove(index);
    }

    public void removeOutfit(UUID skin) {
        outfits.removeIf(skin1 -> skin1.getUUID().equals(skin));
    }

    public void removeOutfit(String skinName) {
        outfits.removeIf(skin1 -> skin1.getSkinName().equals(skinName));
    }

    public void removeAllOutfits() {
        outfits.clear();
    }

    public CharacterSkin getSelectedSkin() {
        return outfits.stream().filter(skin -> skin.getUUID().equals(selectedSkin)).findFirst().orElse(null);
    }

    public UUID getSelectedSkinUUID() {
        return selectedSkin;
    }
}