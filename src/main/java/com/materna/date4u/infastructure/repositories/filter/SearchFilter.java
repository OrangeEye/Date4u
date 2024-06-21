package com.materna.date4u.infastructure.repositories.filter;

import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Objects;

public class SearchFilter {

    private byte myGender;
    private Byte attractedToGender;
    private LocalDate minBirthdate = LocalDate.ofEpochDay(0);
    private LocalDate maxBirthdate = LocalDate.now();
    private short minHornlength;
    private short maxHornlength = Short.MAX_VALUE;

    public byte myGender() {
        return myGender;
    }

    public Byte attractedToGender() {
        return attractedToGender;
    }

    public LocalDate minBirthdate() {
        return minBirthdate;
    }

    public LocalDate maxBirthdate() {
        return maxBirthdate;
    }

    public short minHornlength() {
        return minHornlength;
    }

    public short maxHornlength() {
        return maxHornlength;
    }

    public SearchFilter myGender(int myGender) {
        this.myGender = (byte) myGender;
        return this;
    }

    public SearchFilter attractedToGender(@Nullable Integer attractedToGender) {
        this.attractedToGender = attractedToGender == null ? null : attractedToGender.byteValue();
        return this;
    }

    public SearchFilter minBirthdate(LocalDate date) {
        this.minBirthdate = Objects.requireNonNull(date);
        return this;
    }

    public SearchFilter maxBirthdate(LocalDate date) {
        this.maxBirthdate = Objects.requireNonNull(date);
        return this;
    }

    public SearchFilter minHornlength(int minHornlength) {
        this.minHornlength = (short) minHornlength;
        return this;
    }

    public SearchFilter maxHornlength(int maxHornlength) {
        this.maxHornlength = (short) maxHornlength;
        return this;
    }

}
