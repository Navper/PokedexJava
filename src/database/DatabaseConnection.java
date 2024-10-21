package database;

import models.Pokemon;
import models.PokemonType;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:src\\database\\pokemon.db"; //TODO: cambia esta picha
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void SaveAllPokemons(List<Pokemon> pokemons) throws SQLException {
        Connection connection = createNewConnection();
        Statement statement = connection.createStatement();

        statement.executeUpdate("DELETE FROM pokemon");
        for (int i = 0; i < pokemons.size(); i++) {
            statement.executeUpdate("INSERT INTO pokemon (id, name, health, attack, defense, speed, type) VALUES (" +
                    i + ", " +
                    "'" + pokemons.get(i).getName() + "', " +
                    pokemons.get(i).getHealth() + ", " +
                    pokemons.get(i).getAttack() + ", " +
                    pokemons.get(i).getDefense() + ", " +
                    pokemons.get(i).getSpeed() + ", " +
                    "'" + pokemons.get(i).getType().name() + "')");
        }

        connection.commit();
        connection.close();
    }

    public static List<Pokemon> GetAllPokemons() throws SQLException {
        Connection connection = createNewConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM pokemon");
        List<Pokemon> pokemons = new ArrayList<>();

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            double health = resultSet.getDouble("health");
            double attack = resultSet.getDouble("attack");
            double defense = resultSet.getDouble("defense");
            double speed = resultSet.getDouble("speed");
            PokemonType type = PokemonType.valueOf(resultSet.getString("type"));

            try {
                pokemons.add(Pokemon.createForClassName(type.getClassName(), name, health, attack, defense, speed));
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                     InvocationTargetException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }

        connection.commit();
        connection.close();

        return pokemons;
    }

    private static Connection createNewConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }
}
