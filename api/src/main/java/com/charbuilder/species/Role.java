package main.java.com.charbuilder.species;

import main.java.com.charbuilder.dice.Die;
import main.java.com.charbuilder.utils.AbilityModifier;

public enum Role {

    BARBARIAN {
        @Override
        public Die hitDie() {
            return Die.D12;
        }
        @Override
        public AbilityModifier abilityModifier() {
            return AbilityModifier.CONSTITUTION;
        }
    };

    public abstract Die hitDie();
    public abstract AbilityModifier abilityModifier();
}
