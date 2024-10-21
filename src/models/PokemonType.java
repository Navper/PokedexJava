package models;

/**
 * Enumeración PokemonType, que representa los diferentes tipos de Pokemon en el juego.
 * Cada tipo de Pokemon está asociado con el nombre de la clase correspondiente y un nombre para mostrar.
 */
public enum PokemonType {
    NORMAL(NormalPokemon.class.getName(), "Normal"),
    FIRE(FirePokemon.class.getName(), "Fire"),
    WATER(WaterPokemon.class.getName(), "Water"),
    GRASS(GrassPokemon.class.getName(), "Grass");

    private final String className;
    private final String displayName;

    /**
     * Constructor de la enumeración PokemonType.
     *
     * @param className    El nombre de la clase que representa este tipo de Pokemon.
     * @param displayName  El nombre para mostrar de este tipo de Pokemon.
     */
    PokemonType(String className, String displayName) {
        this.className = className;
        this.displayName = displayName;
    }

    /**
     * Obtiene el nombre de la clase que representa este tipo de Pokemon.
     *
     * @return el nombre de la clase.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Obtiene el nombre para mostrar de este tipo de Pokemon.
     *
     * @return el nombre para mostrar.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Devuelve el tipo de Pokemon asociado con el nombre de la clase dado.
     *
     * @param className El nombre de la clase.
     * @return El tipo de Pokemon correspondiente, o null si no hay ningún tipo de Pokemon asociado con el nombre de la clase.
     */
    public static PokemonType fromClassName(String className) {
        for (PokemonType type : values()) {
            if (type.getClassName().equals(className)) {
                return type;
            }
        }
        return null;
    }
}