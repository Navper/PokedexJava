package models;

import java.lang.reflect.InvocationTargetException;

/**
 * Clase abstracta Pokemon que implementa la interfaz Comparable y Cloneable.
 * Esta clase representa un Pokemon con atributos como nombre, salud, ataque, defensa y velocidad.
 */
public abstract class Pokemon implements Comparable<Pokemon>, Cloneable {
    private String name;
    private double health;
    private double attack;
    private double defense;
    private double speed;

    /**
     * Constructor de la clase Pokemon.
     *
     * @param name    El nombre del Pokemon.
     * @param health  La salud del Pokemon.
     * @param attack  El ataque del Pokemon.
     * @param defense La defensa del Pokemon.
     * @param speed   La velocidad del Pokemon.
     */
    public Pokemon(String name, double health, double attack, double defense, double speed) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    /**
     * Método que compara este Pokemon con otro basándose en su salud y la lucha resultante.
     *
     * @param pokemon El Pokemon a comparar.
     * @return un valor entero que representa el resultado de la comparación.
     */
    @Override
    public int compareTo(Pokemon pokemon) {
        double thisPokemonHealth = getHealth();
        double opponentPokemonHealth = pokemon.getHealth();

        return fight(pokemon, thisPokemonHealth, opponentPokemonHealth);
    }

    /**
     * Comprueba si un objeto es igual a este Pokemon.
     * Para que dos Pokemon sean iguales, deben tener el mismo nombre, salud, ataque, defensa y velocidad.
     *
     * @param obj El objeto a comparar.
     * @return true si los objetos son iguales, false de lo contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pokemon)) {
            return false;
        }

        Pokemon pokemon = (Pokemon) obj;

        return getName().equals(pokemon.getName()) &&
                getHealth() == pokemon.getHealth() &&
                getAttack() == pokemon.getAttack() &&
                getDefense() == pokemon.getDefense() &&
                getSpeed() == pokemon.getSpeed() &&
                getClass() == pokemon.getClass();
    }

    /**
     * Calcula el código hash de este Pokemon.
     *
     * @return El código hash.
     */
    @Override
    public int hashCode() {
        return getName().hashCode() +
                Double.hashCode(getHealth()) +
                Double.hashCode(getAttack()) +
                Double.hashCode(getDefense()) +
                Double.hashCode(getSpeed()) +
                getClass().hashCode();
    }

    /**
     * Método que obtiene el daño que este Pokemon puede hacer a otro Pokemon.
     *
     * @param pokemonToAttack El Pokemon al que se ataca.
     * @return El daño que se puede hacer.
     */
    protected double getDamageAgainst(Pokemon pokemonToAttack) {
        return getAttack() / getDefense();
    }

    private int fight(Pokemon opponent, double thisPokemonHealth, double opponentPokemonHealth) {
        double damageAgainstOpponent = getDamageAgainst(opponent);
        double damageAgainstThis = opponent.getDamageAgainst(this);

        if (getSpeed() >= opponent.getSpeed()) {
            if (damageAgainstOpponent >= opponentPokemonHealth) {
                // This Pokémon wins
                return 1;
            } else {
                // Other Pokémon attacks
                opponentPokemonHealth -= damageAgainstOpponent;
                return opponent.fight(opponent, thisPokemonHealth, opponentPokemonHealth);
            }
        } else {
            if (damageAgainstThis >= thisPokemonHealth) {
                // Opponent Pokémon wins
                return -1;
            } else {
                // This Pokémon attacks
                thisPokemonHealth -= damageAgainstThis;
                return fight(opponent, thisPokemonHealth, opponentPokemonHealth);
            }
        }
    }

    /**
     * Devuelve una cadena de caracteres que representa a este Pokemon.
     *
     * @return Una cadena de caracteres que representa a este Pokemon.
     */
    @Override
    public String toString() {
        return String.format("%s (%s)", getName(), getType().toString());
    }

    /**
     * Devuelve el nombre de este Pokemon.
     *
     * @return El nombre de este Pokemon.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre de este Pokemon.
     *
     * @param name El nuevo nombre del Pokemon.
     */
    public void setName(String name) {
        this.name = name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Devuelve el tipo de este Pokemon.
     *
     * @return El tipo de este Pokemon.
     */
    public PokemonType getType() {
        return PokemonType.fromClassName(this.getClass().getName());
    }

    /**
     * Crea un nuevo Pokemon de una clase específica.
     *
     * @param className El nombre de la clase del Pokemon.
     * @param name      El nombre del Pokemon.
     * @param health    La salud del Pokemon.
     * @param attack    El ataque del Pokemon.
     * @param defense   La defensa del Pokemon.
     * @param speed     La velocidad del Pokemon.
     * @return El nuevo Pokemon creado.
     * @throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException si ocurre algún error durante la creación del Pokemon.
     */
    public static Pokemon createForClassName(String className, String name, double health, double attack, double defense, double speed)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> pokemonClass = Class.forName(className);
        return (Pokemon) pokemonClass.getConstructor(String.class, double.class, double.class, double.class, double.class).newInstance(name, health, attack, defense, speed);
    }

    /**
     * Crea un clon de este Pokemon.
     *
     * @return El clon del Pokemon.
     */
    @Override
    public Pokemon clone() {
        try {
           return  (Pokemon) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
