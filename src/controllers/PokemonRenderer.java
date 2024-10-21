
package controllers;

import models.Pokemon;

import javax.swing.*;
import java.awt.*;
/**
 * Esta clase implementa la interfaz ListCellRenderer para la representación personalizada de Pokémon en un componente JList.
 * PokemonRenderer extiende JLabel, por lo que cada celda de la lista se representará como una etiqueta con el nombre del Pokémon.
 *
 * @author Tu nombre
 * @version 1.0
 * @see javax.swing.ListCellRenderer
 */
public class PokemonRenderer extends JLabel implements ListCellRenderer<Pokemon> {

    /**
     * Constructor para la clase PokemonRenderer.
     * Establece la opacidad del JLabel para permitir la personalización del color de fondo.
     */
    public PokemonRenderer() {
        setOpaque(true);
    }

    /**
     * Personaliza la representación de las celdas en una lista de Pokémon.
     * Este método se llama cada vez que una celda en la lista necesita ser representada.
     *
     * @param list La lista de Pokémon.
     * @param value El Pokémon que se va a renderizar.
     * @param index El índice de la celda que se está representando.
     * @param isSelected Verdadero si la celda especificada fue seleccionada.
     * @param cellHasFocus Verdadero si la celda especificada tiene el foco.
     * @return Un componente cuyo paint() método se puede llamar para dibujar la celda.
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends Pokemon> list, Pokemon value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.getName());
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