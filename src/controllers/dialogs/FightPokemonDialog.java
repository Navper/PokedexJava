package controllers.dialogs;

import controllers.PokemonRenderer;
import models.Pokemon;
import models.swing.PokemonTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Clase FightPokemonDialog que extiende de JDialog.
 * Esta clase genera un diálogo que permite al usuario seleccionar un Pokémon para pelear contra el Pokémon actual.
 */
public class FightPokemonDialog extends JDialog {

    /**
     * Modelo que contiene la lista de Pokémon.
     */
    private final DefaultListModel<Pokemon> model;

    /**
     * El Pokémon actual que se está utilizando.
     */
    private final Pokemon currentPokemon;

    /**
     * JList que muestra la lista de Pokémon.
     */
    private JList<Pokemon> pokemonList;

    /**
     * Constructor que crea una nueva instancia de FightPokemonDialog.
     * Inicializa la lista de Pokémon y el botón de pelea.
     *
     * @param parent La ventana padre del diálogo.
     * @param model El modelo de la tabla de Pokémons.
     * @param currentPokemon El Pokémon actual.
     */
    public FightPokemonDialog(JFrame parent, PokemonTableModel model, Pokemon currentPokemon) {
        super(parent, "Fight", true);
        this.currentPokemon = currentPokemon;
        this.model = model.convertToListModel();

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        createPokemonJList(constraints);
        createFightButton(constraints);

        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Crea y configura el botón de pelea.
     *
     * @param constraints Las restricciones del layout para posicionar el botón en el diálogo.
     */
    private void createFightButton(GridBagConstraints constraints) {
        JButton fightButton = new JButton("Fight");
        fightButton.addActionListener(e -> onClickFightButton());
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(fightButton, constraints);
    }

    /**
     * Se ejecuta cuando se hace clic en el botón de pelea.
     * Si no se seleccionó un Pokémon para pelear, se muestra un mensaje de error.
     */
    private void onClickFightButton() {
        if (pokemonList.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "Please select a Pokemon to fight against.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            createFightingDialog(pokemonList.getSelectedValue());
            dispose();
        }
    }

    /**
     * Crea y configura la lista de Pokémon.
     *
     * @param constraints Las restricciones del layout para posicionar la lista en el diálogo.
     */
    private void createPokemonJList(GridBagConstraints constraints) {
        pokemonList = new JList<>(model);
        pokemonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(pokemonList);
        listScrollPane.setPreferredSize(new Dimension(200, 200));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        pokemonList.setCellRenderer(new PokemonRenderer());
        add(listScrollPane, constraints);
    }

    /**
     * Crea y muestra un diálogo de pelea con el Pokémon seleccionado.
     *
     * @param selectedPokemon El Pokémon seleccionado para pelear.
     */
    private void createFightingDialog(Pokemon selectedPokemon) {
        JDialog fightingDialog = new JDialog(this, "Fighting", true);
        JLabel label = new JLabel("Fighting " + currentPokemon.getName() + " against " + selectedPokemon.getName() + "...");
        fightingDialog.add(label);
        fightingDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                startFight(selectedPokemon, fightingDialog, label);
            }
        });
        fightingDialog.pack();
        fightingDialog.setLocationRelativeTo(this);
        fightingDialog.setVisible(true);
    }

    /**
     * Inicia la pelea entre el Pokémon actual y el seleccionado.
     * Después de un tiempo aleatorio, se determina el ganador y se muestra el resultado.
     *
     * @param selectedPokemon El Pokémon seleccionado para pelear.
     * @param fightingDialog El diálogo de pelea.
     * @param label La etiqueta donde se muestra el resultado.
     */
    private void startFight(Pokemon selectedPokemon, JDialog fightingDialog, JLabel label) {
        try {
            Thread.sleep(2000 + (int) (Math.random() * 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int result = currentPokemon.compareTo(selectedPokemon);
        if (result > 0) {
            label.setText(currentPokemon.getName() + " won the fight!");
        } else {
            label.setText(selectedPokemon.getName() + " won the fight!");
        }

        JButton acceptButton = new JButton("Accept");
        acceptButton.addActionListener(e -> {
            fightingDialog.dispose();
            dispose();
        });
        fightingDialog.pack();
    }
}