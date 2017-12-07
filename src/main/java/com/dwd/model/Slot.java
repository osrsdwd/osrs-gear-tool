package com.dwd.model;

public enum Slot {
    HEAD("Head_slot"),
    CAPE("Cape_slot"),
    AMULET("Neck_slot"),
    AMMO("Ammunition_slot"),
    WEAPON("Weapons"),
    BODY("Body_slot"),
    OFFHAND("Shield_slot"),
    LEGS("Legwear_slot"),
    GLOVES("Hand_slot"),
    BOOTS("Feet_slot"),
    RING("Ring_slot"),
    TWO_HANDED("Two-handed_slot");

    public final String wikiUrl;
    private static final String wikiUrlRoot = "http://oldschoolrunescape.wikia.com/wiki/";
    private static final String wikiUrlSuffix = "_table";

    Slot(String wikiLabel){
        this.wikiUrl = wikiUrlRoot + wikiLabel + wikiUrlSuffix;
    }

}
