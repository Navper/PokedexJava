package controllers.dialogs;

import controllers.PokemonTypeRenderer;
import models.Pokemon;
import models.swing.PokemonTableModel;
import models.PokemonType;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Clase EditPokemonDialog que extiende de JDialog.
 * Esta clase genera un diálogo que permite al usuario editar un Pokémon existente en el modelo de tabla.
 */
public class EditPokemonDialog extends JDialog {

    /**
     * Campos de texto para el nombre, ataque, salud, defensa y velocidad del Pokémon.
     */
    private JTextField nameField;
    private JTextField attackField;
    private JTextField healthField;
    private JTextField defenseField;
    private JTextField speedField;

    /**
     * Selector para el tipo de Pokémon.
     */
    private JComboBox<PokemonType> typeComboBox;

    /**
     * Botones para editar el Pokémon o cancelar la acción.
     */
    private JButton addButton;
    private JButton cancelButton;

    /**
     * El modelo de tabla que contiene el Pokémon que se va a editar.
     */
    private final PokemonTableModel model;

    /**
     * El Pokémon que se va a editar.
     */
    private final Pokemon pokemon;

    /**
     * Constructor que crea una nueva instancia de EditPokemonDialog.
     * Inicializa todos los campos de entrada, botones y configura el layout del diálogo.
     *
     * @param parent La ventana padre del diálogo.
     * @param model El modelo de la tabla de Pokémons.
     * @param pokemon El Pokémon que se va a editar.
     */
    public EditPokemonDialog(JFrame parent, PokemonTableModel model, Pokemon pokemon) {
        super(parent, "Edit Pokemon", true);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        this.model = model;
        this.pokemon = pokemon;

        createNameField(constraints);
        createAttackField(constraints);
        createHealthField(constraints);
        createDefenseField(constraints);
        createSpeedField(constraints);
        createTypeComboBox(constraints);
        createEditButton();
        createCancelButton();
        createButtonPanel(constraints);

        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * Crea y configura el campo de texto para el nombre del Pokémon.
     *
     * @param constraints Las restricciones del layout para posicionar el campo en el diálogo.
     */
    private void createNameField(GridBagConstraints constraints) {
        JLabel nameLabel = new JLabel("Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        add(nameLabel, constraints);

        nameField = new JTextField(20);
        nameField.setText(pokemon.getName());
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(nameField, constraints);
    }

    /**
     * Crea y configura el campo de texto para el ataque del Pokémon.
     *
     * @param constraints Las restricciones del layout para posicionar el campo en el diálogo.
     */
    private void createAttackField(GridBagConstraints constraints) {
        JLabel attackLabel = new JLabel("Attack:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        add(attackLabel, constraints);

        attackField = new JTextField(20);
        attackField.setText(String.valueOf(pokemon.getAttack()));
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(attackField, constraints);
    }

    /**
     * Crea y configura el campo de texto para la salud del Pokémon.
     *
     * @param constraints Las restricciones del layout para posicionar el campo en el diálogo.
     */
    private void createHealthField(GridBagConstraints constraints) {
        JLabel healthLabel = new JLabel("Health:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.NONE;
        add(healthLabel, constraints);

        healthField = new JTextField(20);
        healthField.setText(String.valueOf(pokemon.getHealth()));
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(healthField, constraints);
    }

    /**
     * Crea y configura el campo de texto para la defensa del Pokémon.
     *
     * @param constraints Las restricciones del layout para posicionar el campo en el diálogo.
     */
    private void createDefenseField(GridBagConstraints constraints) {
        JLabel defenseLabel = new JLabel("Defense:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.NONE;
        add(defenseLabel, constraints);

        defenseField = new JTextField(20);
        defenseField.setText(String.valueOf(pokemon.getDefense()));
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(defenseField, constraints);
    }

    /**
     * Crea y configura el campo de texto para la velocidad del Pokémon.
     *
     * @param constraints Las restricciones del layout para posicionar el campo en el diálogo.
     */
    private void createSpeedField(GridBagConstraints constraints) {
        JLabel speedLabel = new JLabel("Speed:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.NONE;
        add(speedLabel, constraints);

        speedField = new JTextField(20);
        speedField.setText(String.valueOf(pokemon.getSpeed()));
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(speedField, constraints);
    }

    /**
     * Crea y configura el comboBox para el tipo del Pokémon.
     *
     * @param constraints Las restricciones del layout para posicionar el campo en el diálogo.
     */
    private void createTypeComboBox(GridBagConstraints constraints) {
        JLabel typeLabel = new JLabel("Type:");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.NONE;
        add(typeLabel, constraints);

        typeComboBox = new JComboBox<>(PokemonType.values());
        typeComboBox.setSelectedItem(pokemon.getType());
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        typeComboBox.setRenderer(new PokemonTypeRenderer());
        add(typeComboBox, constraints);
    }

    /**
     * Crea y configura el botón de editar.
     */
    private void createEditButton() {
        addButton = new JButton("Edit");
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("arial", Font.BOLD, 15));
        addButton.setBackground(Color.LIGHT_GRAY);
        addButton.addActionListener(e -> savePokemon());
    }

    /**
     * Crea y configura el botón de cancelar.
     */
    private void createCancelButton() {
        cancelButton = new JButton("Cancel");
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFont(new Font("arial", Font.BOLD, 15));
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * Crea y configura el panel de botones que contiene los botones de editar y cancelar.
     *
     * @param constraints Las restricciones del layout para posicionar el panel en el diálogo.
     */
    private void createButtonPanel(GridBagConstraints constraints) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.NONE;
        add(buttonPanel, constraints);
    }

    /**
     * Guarda la información ingresada por el usuario como un Pokémon editado en el modelo de la tabla.
     * Se llama cuando se hace clic en el botón "Editar".
     */
    private void savePokemon() {
        try {
            String name = nameField.getText();
            double attack = Double.parseDouble(attackField.getText());
            double health = Double.parseDouble(healthField.getText());
            double defense = Double.parseDouble(defenseField.getText());
            double speed = Double.parseDouble(speedField.getText());
            PokemonType type = (PokemonType) typeComboBox.getSelectedItem();

            if (name.trim().isEmpty() ||
                    attackField.getText().trim().isEmpty() ||
                    healthField.getText().trim().isEmpty() ||
                    defenseField.getText().trim().isEmpty() ||
                    speedField.getText().trim().isEmpty() ||
                    type == null
            ) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pokemon newPokemon = Pokemon.createForClassName(type.getClassName(), name, attack, health, defense, speed);
            int index = model.indexOf(pokemon);
            model.removePokemon(pokemon);
            model.insertPokemonAt(newPokemon, index);
            dispose();

        } catch (NumberFormatException exception) {
            int separatorIndex = exception.getMessage().indexOf(": ");
            String invalidInput = exception.getMessage().substring(separatorIndex + 2);
            JOptionPane.showMessageDialog(this, invalidInput + " is not a valid input.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
