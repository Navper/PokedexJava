package models;
/**
 * Clase NormalPokemon que extiende la clase abstracta Pokemon.
 * Esta clase representa a un Pokemon de tipo normal.
 */
public class NormalPokemon extends Pokemon {
    /**
     * Constructor de la clase NormalPokemon.
     *
     * @param name    El nombre del Pokemon.
     * @param health  La salud del Pokemon.
     * @param attack  El ataque del Pokemon.
     * @param defense La defensa del Pokemon.
     * @param speed   La velocidad del Pokemon.
     */
    public NormalPokemon(String name, double health, double attack, double defense, double speed) {
        super(name, health, attack, defense, speed);
    }
}
