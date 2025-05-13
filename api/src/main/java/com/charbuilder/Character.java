package com.charbuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.charbuilder.dice.D6;
import com.charbuilder.dice.D8;
import com.charbuilder.dice.D12;
import com.charbuilder.dice.D20;
import com.charbuilder.species.Role;
import com.charbuilder.species.Species;
import com.charbuilder.utils.*;
import com.charbuilder.dice.Die;

public class Character {

    public Species species;
    public Role role;
    public Size size;
    public int speed;

    public int level;
    public int baseArmourClass;
    public Die hitDie;
    public int hitDice;
    public int hitPoints;
    public int maxHitPoints;
    public Boolean hidden;
    public Background background;

    public int strength;
    public int dexterity;
    public int constitution;
    public int intelligence;
    public int wisdom;
    public int charisma;

    public int strengthModifier;
    public int dexterityModifier;
    public int constitutionModifier;
    public int intelligenceModifier;
    public int wisdomModifier;
    public int charismaModifier;

    public ArrayList<Proficiencies> skillProficiencies;
    public ArrayList<Proficiencies> armourProficiencies;
    public ArrayList<Proficiencies> weaponProficiencies;
    public ArrayList<SavingThrows> savingThrowProficiencies;

//    public int strengthAthletics;
//    public int dexterityAcrobatics;
//    public int dexteritySleightOfHand;
//    public int dexterityStealth;
//    public int intelligenceArcana;
//    public int intelligenceHistory;
//    public int intelligenceInvestigation;
//    public int intelligenceNature;
//    public int intelligenceReligion;
//    public int wisdomAnimalHandling;
//    public int wisdomInsight;
//    public int wisdomMedicine;
//    public int wisdomPerception;
//    public int wisdomSurvival;
//    public int charismaDeception;
//    public int charismaIntimidation;
//    public int charismaPerformance;
//    public int charismaPersuasion;

    public Character() {
        this.species = getRandomSpecies();
        this.role = getRandomRole();

        new Character(this.species, this.role, getRandomBackground(), getRandomLevel());
    }

    public Character(Species species, Role role, Background background, int level){
        this.species = species;
        this.role = role;
        this.level = level;
        this.background = background;
        this.size = species.size;
        this.speed = species.speed;
        this.strength = creationRoll() + species.baseStrength;
        this.dexterity = creationRoll() + species.baseDexterity;
        this.constitution = creationRoll() + species.baseConstitution;
        this.intelligence = creationRoll() + species.baseIntelligence;
        this.wisdom = creationRoll() + species.baseWisdom;
        this.charisma = creationRoll() + species.baseCharisma;
        this.strengthModifier = getAbilityModifier(strength);
        this.dexterityModifier = getAbilityModifier(dexterity);
        this.constitutionModifier = getAbilityModifier(constitution);
        this.intelligenceModifier = getAbilityModifier(intelligence);
        this.wisdomModifier = getAbilityModifier(wisdom);
        this.charismaModifier = getAbilityModifier(charisma);
        this.hitDie = role.hitDie();
        this.hitDice = level;
        this.hitPoints = getHitPoints(role.hitDie(), level);
        this.maxHitPoints = this.hitPoints;
        this.baseArmourClass = 10 + dexterityModifier;
        this.skillProficiencies = background.skillProficiencies();

        if (this.species == Species.HILL_DWARF) { increaseHitPoints(level); }

        if (this.role == Role.BARBARIAN) { this.baseArmourClass += constitutionModifier; }

        // TODO impose ability limits
        // TODO include half elf 2 random proficiencies
        // TODO name generator
        // TODO halfling luck
        // TODO half orc relentless endurance, savage attacks
        // TODO barbarian reckless attack
        // TODO species resistances / advantages / disadvantages
        // TODO random class skill choice
        // TODO resolve background / class random skill choice clash
        // TODO barbarian unarmoured AC
        // TODO format response json

    }

//    public Character(Species species, Role role, int level, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma){
//        this.species = species;
//        this.role = role;
//        this.level = level;
//        this.size = species.size;
//        this.strength = strength;
//        this.dexterity = dexterity;
//        this.constitution = constitution;
//        this.intelligence = intelligence;
//        this.wisdom = wisdom;
//        this.charisma = charisma;
//        this.strengthModifier = getAbilityModifier(strength);
//        this.dexterityModifier = getAbilityModifier(dexterity);
//        this.constitutionModifier = getAbilityModifier(constitution);
//        this.intelligenceModifier = getAbilityModifier(intelligence);
//        this.wisdomModifier = getAbilityModifier(wisdom);
//        this.charismaModifier = getAbilityModifier(charisma);
//        this.hitDie = role.hitDie();
//        this.hitPoints = getHitPoints(role.hitDie(), level);
//        this.baseArmourClass = 10 + dexterityModifier;
//
//        if (this.species == Species.HILL_DWARF) { increaseHitPoints(level); }
//
//        if (this.role == Role.BARBARIAN) { this.baseArmourClass += constitutionModifier; }
//    }

    private int creationRoll(){
        int result;
        try {
            result = Arrays.stream(D6.rollHighest(4, 3)).sum();
            if (result < 3 || result > 18) {throw new IllegalStateException();}
            return result;

        } catch (IllegalStateException e){
            System.out.println("Creation Roll can't be less than 3 or greater than 18: " + e.getMessage());
            System.out.println("Defaulting to 10");
            result = 10;
            return result;
        }
    }

    private Species getRandomSpecies() {
        final Random RANDOM = new Random();
        Species[] speciesArray = Species.values();
        int randomIndex = RANDOM.nextInt(speciesArray.length);
        species = speciesArray[randomIndex];
        return species;
    }

    private Role getRandomRole() {
        final Random RANDOM = new Random();
        Role[] rolesArray = Role.values();
        int randomIndex = RANDOM.nextInt(rolesArray.length);
//        System.out.println(randomIndex);
        role = rolesArray[randomIndex];
        return role;
    }

    private Background getRandomBackground() {
        final Random RANDOM = new Random();
        Background[] backgroundsArray = Background.values();
        int randomIndex = RANDOM.nextInt(backgroundsArray.length);
        background = backgroundsArray[randomIndex];
        return background;
    }

    private int getRandomLevel() {
        level = D20.rollOne();
//        System.out.println("RANDOM LEVEL: " + level);
        return level;
    }

    private int getHitPoints(Die hitDie, int level) {
        switch (hitDie){
            case D6:
                int d6Sum = Arrays.stream(D6.roll(level)).sum();
                return d6Sum + level * this.constitutionModifier;
            case D8:
                int d8Sum = Arrays.stream(D8.roll(level)).sum();
                return d8Sum + level * this.constitutionModifier;
            case D12:
                int d12Sum = Arrays.stream(D12.roll(level)).sum();
                return d12Sum + level * this.constitutionModifier;
            case D20:
                int d20Sum = Arrays.stream(D20.roll(level)).sum();
                return d20Sum + level * this.constitutionModifier;
            default:
                System.out.println("Unknown HitPoint die type, setting HP to zero.");
                return 0;
        }
    }



    public int getAbilityModifier (double modifier) {
        return (int) Math.floor((modifier - 10) / 10);
    }

    public void setInitiative () {

    }

    public void isProficient(Skills proficiency) {
        this.proficiencies.add(proficiency);
    }

    public void levelUp(int level) {
//        TODO ability score level up dependent on level
    }

    public void increaseHitPoints(int hitPoints) {
        this.strength += hitPoints;
    }

    public void increaseStrength(int strength) {
        this.strength += strength;
    }

    public void increaseDexterity(int dexterity) {
        this.dexterity += dexterity;
    }

    public void increaseConstitution (int constitution) {
        this.constitution += constitution;
    }

    public void increaseIntelligence (int intelligence) {
        this.intelligence += intelligence;
    }

    public void increaseWisdom (int wisdom) {
        this.wisdom += wisdom;
    }

    public void increaseCharisma (int charisma) {
        this.charisma += charisma;
    }

    @Override
    public String toString() {
        return "Species: " + this.species + "\n" +
                "Role: " + this.role + "\n" +
                "Level: " + this.level + "\n";
    }

}

