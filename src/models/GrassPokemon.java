package models;

/**
 * Clase GrassPokemon que extiende la clase abstracta Pokemon.
 * Esta clase representa a un Pokemon de tipo hierba.
 */
public class GrassPokemon extends Pokemon {

    /**
     * Constructor de la clase GrassPokemon.
     *
     * @param name    El nombre del Pokemon.
     * @param health  La salud del Pokemon.
     * @param attack  El ataque del Pokemon.
     * @param defense La defensa del Pokemon.
     * @param speed   La velocidad del Pokemon.
     */
    public GrassPokemon(String name, double health, double attack, double defense, double speed) {
        super(name, health, attack, defense, speed);
    }

    /**
     * Método que obtiene el daño que este Pokemon de tipo hierba puede hacer a otro Pokemon.
     * El daño se ajusta en función del tipo del Pokemon al que se ataca.
     *
     * @param pokemonToAttack El Pokemon al que se ataca.
     * @return El daño que se puede hacer.
     */
    @Override
    public double getDamageAgainst(Pokemon pokemonToAttack) {
        double damage = super.getDamageAgainst(pokemonToAttack);
        double typeMultiplier = 1;

        switch (pokemonToAttack.getClass().getName()) {
            case "WaterPokemon":
                typeMultiplier = 2;
                break;
            case "FirePokemon":
            case "GrassPokemon":
                typeMultiplier = 0.5;
                break;
        }

        return damage * typeMultiplier;
    }
}
