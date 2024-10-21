package controllers;

import models.PokemonType;

import javax.swing.*;
import java.awt.*;

/**
 * Esta clase implementa la interfaz ListCellRenderer para la representación personalizada de los tipos de Pokémon en un componente JList.
 * PokemonTypeRenderer extiende JLabel, por lo que cada celda de la lista se representará como una etiqueta con el nombre del tipo de Pokémon.
 *
 * @author Cristian Navarro Pertegal
 * @version 1.0
 * @see javax.swing.ListCellRenderer
 */
public class PokemonTypeRenderer extends JLabel implements ListCellRenderer<PokemonType> {

    /**
     * Personaliza la representación de las celdas en una lista de tipos de Pokémon.
     * Este método se llama cada vez que una celda en la lista necesita ser representada.
     *
     * @param list La lista de tipos de Pokémon.
     * @param value El tipo de Pokémon que se va a renderizar.
     * @param index El índice de la celda que se está representando.
     * @param isSelected Verdadero si la celda especificada fue seleccionada.
     * @param cellHasFocus Verdadero si la celda especificada tiene el foco.
     * @return Un componente cuyo paint() método se puede llamar para dibujar la celda.
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends PokemonType> list, PokemonType value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.getDisplayName());
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }
}