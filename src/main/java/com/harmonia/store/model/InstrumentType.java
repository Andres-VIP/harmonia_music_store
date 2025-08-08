package com.harmonia.store.model;

public enum InstrumentType {
    GUITAR("Guitar"),
    PIANO("Piano"),
    DRUMS("Drums"),
    BASS("Bass"),
    VIOLIN("Violin"),
    SAXOPHONE("Saxophone"),
    TRUMPET("Trumpet"),
    FLUTE("Flute"),
    KEYBOARD("Keyboard"),
    ACCORDION("Accordion"),
    HARMONICA("Harmonica"),
    UKULELE("Ukulele"),
    BANJO("Banjo"),
    MANDOLIN("Mandolin"),
    CELLO("Cello"),
    CLARINET("Clarinet"),
    OBOE("Oboe"),
    TROMBONE("Trombone"),
    TUBA("Tuba"),
    PERCUSSION("Percussion");

    private final String displayName;

    InstrumentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 