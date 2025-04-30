package main.java.com.charbuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import main.java.com.charbuilder.dice.*;
import main.java.com.charbuilder.species.Role;
import main.java.com.charbuilder.species.Species;
import main.java.com.charbuilder.utils.Background;
import main.java.com.charbuilder.utils.Size;

public class Character {

    public Species species;
    public Role role;
    public Size size;

    public int level;
    public int baseArmourClass;
    public Die hitDie;
    public int hitPoints;
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

    public ArrayList<String> proficiencies;
    public ArrayList<String> savingThrows;

//    public int strengthAthletics;
//    public int dexterityAcrobatics;
//    public int dexteritySleightOfHand;
//    public int dexterityStealth;
//    public int intelligenceArcana;
//    public int intelligenceHistory;
//    public int intelligenceInvestigation;
//    public int intelligenceNature = 0;
//    public int intelligenceReligion = 0;
//    public int wisdomAnimalHandling = 0;
//    public int wisdomInsight = 0;
//    public int wisdomMedicine = 0;
//    public int wisdomPerception = 0;
//    public int wisdomSurvival = 0;
//    public int charismaDeception = 0;
//    public int charismaIntimidation = 0;
//    public int charismaPerformance = 0;
//    public int charismaPersuasion = 0;


    public Character() {
        new Character(getRandomSpecies(), getRandomRole(), getRandomBackground(), D20.rollOne());
    }

    public Character(Species species, Role role, Background background, int level){
        this.species = species;
        this.role = role;
        this.level = level;
        this.background = background;
        this.size = species.size;
        this.strength = species.baseStrength;
        this.dexterity = species.baseDexterity;
        this.constitution = species.baseConstitution;
        this.intelligence = species.baseIntelligence;
        this.wisdom = species.baseWisdom;
        this.charisma = species.baseCharisma;
        this.strengthModifier = getAbilityModifier(strength);
        this.dexterityModifier = getAbilityModifier(dexterity);
        this.constitutionModifier = getAbilityModifier(constitution);
        this.intelligenceModifier = getAbilityModifier(intelligence);
        this.wisdomModifier = getAbilityModifier(wisdom);
        this.charismaModifier = getAbilityModifier(charisma);
        this.hitDie = role.hitDie();
        this.hitPoints = getHitPoints(role.hitDie(), level);
        this.baseArmourClass = 10 + dexterityModifier;

        if (this.species == Species.HILL_DWARF) { increaseHitPoints(level); }

        if (this.role == Role.BARBARIAN) { this.baseArmourClass += constitutionModifier; }

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

    private Species getRandomSpecies() {
        final Random RANDOM = new Random();
        Species[] speciesArray = Species.values();
        int randomIndex = RANDOM.nextInt(speciesArray.length);
        return speciesArray[randomIndex];
    }

    private Role getRandomRole() {
        final Random RANDOM = new Random();
        Role[] rolesArray = Role.values();
        int randomIndex = RANDOM.nextInt(rolesArray.length);
        return rolesArray[randomIndex];
    }

    private Background getRandomBackground() {
        final Random RANDOM = new Random();
        Background[] backgroundsArray = Background.values();
        int randomIndex = RANDOM.nextInt(backgroundsArray.length);
        return backgroundsArray[randomIndex];
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

    public void isProficient(String proficiency) {
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


}

