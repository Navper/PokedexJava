package models.swing;

import models.Pokemon;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PokemonTableModel extends AbstractTableModel {

    private final List<Pokemon> pokemonList = new ArrayList<>();
    private final String[] columnNames = {"Name", "Health", "Attack", "Defense", "Speed", "Type"};

    /**
     * Obtiene el número de filas en la tabla, que es igual al tamaño de la lista de Pokemon.
     *
     * @return El número de filas en la tabla.
     */
    @Override
    public int getRowCount() {
        return pokemonList.size();
    }

    /**
     * Obtiene el número de columnas en la tabla, que es igual al tamaño del array de nombres de las columnas.
     *
     * @return El número de columnas en la tabla.
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Obtiene el nombre de una columna en la tabla.
     *
     * @param column El índice de la columna.
     * @return El nombre de la columna.
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * Obtiene el valor de una celda específica en la tabla.
     *
     * @param rowIndex El índice de la fila de la celda.
     * @param columnIndex El índice de la columna de la celda.
     * @return El objeto en la celda especificada.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pokemon pokemon = pokemonList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return pokemon.getName();
            case 1:
                return pokemon.getHealth();
            case 2:
                return pokemon.getAttack();
            case 3:
                return pokemon.getDefense();
            case 4:
                return pokemon.getSpeed();
            case 5:
                return pokemon.getType().getDisplayName();
            default:
                return null;
        }
    }


    /**
     * Añade un Pokemon a la lista de Pokemon y actualiza la tabla.
     *
     * @param pokemon El Pokemon a añadir.
     */
    public void addPokemon(Pokemon pokemon) {
        pokemonList.add(pokemon);
        fireTableRowsInserted(pokemonList.size() - 1, pokemonList.size() - 1);
    }
    /**
     * Elimina un Pokemon específico de la lista de Pokemon y actualiza la tabla.
     *
     * @param pokemon El Pokemon a eliminar.
     */
    public void removePokemon(Pokemon pokemon) {
        int index = pokemonList.indexOf(pokemon);
        pokemonList.remove(pokemon);
        fireTableRowsDeleted(index, index);
    }
    /**
     * Elimina un Pokemon en un índice específico de la lista de Pokemon y actualiza la tabla.
     *
     * @param index El índice del Pokemon a eliminar.
     */
    public void removePokemon(int index) {
        pokemonList.remove(index);
        fireTableRowsDeleted(index, index);
    }
    /**
     * Obtiene un Pokemon en un índice específico de la lista de Pokemon.
     *
     * @param index El índice del Pokemon a obtener.
     * @return El Pokemon en el índice especificado.
     */
    public Pokemon getPokemon(int index) {
        return pokemonList.get(index);
    }
    /**
     * Convierte la lista de Pokemon en un DefaultListModel.
     *
     * @return Un DefaultListModel que contiene todos los Pokemon en la lista.
     */
    public DefaultListModel<Pokemon> convertToListModel() {
        DefaultListModel<Pokemon> listModel = new DefaultListModel<>();
        for (Pokemon pokemon : pokemonList) {
            listModel.addElement(pokemon);
        }

        return listModel;
    }
    /**
     * Obtiene el índice de un Pokemon específico en la lista de Pokemon.
     *
     * @param pokemon El Pokemon cuyo índice se quiere obtener.
     * @return El índice del Pokemon especificado, o -1 si el Pokemon no se encuentra en la lista.
     */
    public int indexOf(Pokemon pokemon) {
        return pokemonList.indexOf(pokemon);
    }
    /**
     * Inserta un Pokemon en un índice específico en la lista de Pokemon y actualiza la tabla.
     *
     * @param pokemon El Pokemon a insertar.
     * @param index El índice en el que se debe insertar el Pokemon.
     */
    public void insertPokemonAt(Pokemon pokemon, int index) {
        pokemonList.add(index, pokemon);
        fireTableRowsInserted(index, index);
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList.clear();
        this.pokemonList.addAll(pokemonList);
        fireTableDataChanged();
    }
}
