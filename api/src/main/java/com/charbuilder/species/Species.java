package main.java.com.charbuilder.species;

import main.java.com.charbuilder.dice.D6;
import main.java.com.charbuilder.utils.Size;

import java.util.Arrays;

public enum Species {

    MOUNTAIN_DWARF(2,0,2,0,0,0, Size.MEDIUM, 25),
    HILL_DWARF(0,0,2,0,1,0, Size.MEDIUM, 25),
    HIGH_ELF(0,2,0,2,0,0, Size.MEDIUM, 30),
    WOOD_ELF(0,2,0,0,1,0, Size.MEDIUM, 35);

    public final int baseStrength;
    public final int baseDexterity;
    public final int baseConstitution;
    public final int baseIntelligence;
    public final int baseWisdom;
    public final int baseCharisma;
    public final int speed;
    public final Size size;

    Species(int baseStrength, int baseDexterity, int baseConstitution, int baseIntelligence, int baseWisdom, int baseCharisma, Size size, int speed) {
        this.baseStrength = creationRoll() + baseStrength;
        this.baseDexterity = creationRoll() + baseDexterity;
        this.baseConstitution = creationRoll() + baseConstitution;
        this.baseIntelligence = creationRoll() + baseIntelligence;
        this.baseWisdom = creationRoll() + baseWisdom;
        this.baseCharisma = creationRoll() + baseCharisma;
        this.size = size;
        this.speed = speed;

    }

    private int creationRoll(){
        int result;
        try {
            result = Arrays.stream(D6.rollHighest(4, 3)).sum();
            if (result < 3) {throw new IllegalStateException();}
            return result;

        } catch (IllegalStateException e){
            System.out.println("Creation Roll can't be less than three: " + e.getMessage());
            System.out.println("Defaulting to 10");
            result = 10;
            return result;
        }
    }
}
