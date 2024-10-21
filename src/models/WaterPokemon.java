package models;

/**
 * Clase WaterPokemon que representa un Pokemon de tipo agua en el juego.
 * Hereda de la clase abstracta Pokemon.
 */
public class WaterPokemon extends Pokemon {

    /**
     * Constructor para un Pokemon de tipo agua.
     *
     * @param name    El nombre del Pokemon.
     * @param health  La salud inicial del Pokemon.
     * @param attack  La potencia de ataque del Pokemon.
     * @param defense La defensa del Pokemon.
     * @param speed   La velocidad del Pokemon.
     */
    public WaterPokemon(String name, double health, double attack, double defense, double speed) {
        super(name, health, attack, defense, speed);
    }

    /**
     * Calcula el daño infligido por este Pokemon a otro Pokemon en función del tipo del Pokemon atacado.
     * El daño se calcula de acuerdo con las reglas del juego, que establecen diferentes multiplicadores de daño según el tipo de Pokemon.
     *
     * @param pokemonToAttack El Pokemon al que se va a atacar.
     * @return El daño infligido al Pokemon atacado.
     */
    @Override
    public double getDamageAgainst(Pokemon pokemonToAttack) {
        double damage = super.getDamageAgainst(pokemonToAttack);
        double typeMultiplier = 1;

        switch (pokemonToAttack.getClass().getName()) {
            case "FirePokemon":
                typeMultiplier = 2;
                break;
            case "WaterPokemon":
            case "GrassPokemon":
                typeMultiplier = 0.5;
                break;
        }

        return damage * typeMultiplier;
    }
}
