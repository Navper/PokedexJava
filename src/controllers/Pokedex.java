package controllers;

import controllers.dialogs.AddPokemonDialog;
import controllers.dialogs.EditPokemonDialog;
import controllers.dialogs.FightPokemonDialog;
import database.DatabaseConnection;
import models.*;
import models.swing.PokemonTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase principal Pokedex se extiende de JFrame, representando la ventana principal de la aplicación.
 */
public class Pokedex extends JFrame {
    PokemonTableModel model;
    private JTable pokemonJTable;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton fightButton;
    private JButton cloneButton;
    private JMenuBar menuBar;

    /**
     * El constructor configura la ventana principal, incluyendo todos los componentes de la interfaz de usuario y escuchadores.
     */
    public Pokedex() {
        super("Epic Pokedex");
        setLayout(new BorderLayout());
        createPokemonsJTable();
        createAddButton();
        createEditButton();
        createRemoveButton();
        createFightButton();
        createCloneButton();
        createMenuBar();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.add(new JScrollPane(pokemonJTable), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(fightButton);
        buttonPanel.add(cloneButton);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        pack();

    }

    /**
     * Este método crea y configura la JTable principal que muestra los Pokémon.
     */
    private void createPokemonsJTable() {
        model = new PokemonTableModel();
        model.addPokemon(new GrassPokemon("Bulbasaur", 1, 1, 1, 1));
        model.addPokemon(new FirePokemon("Charmander", 1, 1, 1, 1));
        model.addPokemon(new WaterPokemon("Squirtle", 1, 1, 1, 1));

        pokemonJTable = new JTable(model);
        pokemonJTable.setRowHeight(25);
        pokemonJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableRowSorter<PokemonTableModel> sorter = createTableRowSorter();

        pokemonJTable.setRowSorter(sorter);
    }

    /**
     * Este método crea y devuelve un TableRowSorter para ordenar la JTable.
     * @return El TableRowSorter creado.
     */
    private TableRowSorter<PokemonTableModel> createTableRowSorter() {
        TableRowSorter<PokemonTableModel> sorter = new TableRowSorter<>(model);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        pokemonJTable.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = pokemonJTable.getTableHeader().columnAtPoint(e.getPoint());

                if (column >= 0) {
                    List<RowSorter.SortKey> sortKeys = new ArrayList<>(sorter.getSortKeys());
                    RowSorter.SortKey sortKey = sortKeys.isEmpty() ? null : sortKeys.get(0);

                    if (sortKey != null && sortKey.getColumn() == column && sortKey.getSortOrder() == SortOrder.ASCENDING) {
                        setSortOrderByColumn(column, sortKeys, sorter, SortOrder.DESCENDING);
                    } else {
                        setSortOrderByColumn(column, sortKeys, sorter, SortOrder.ASCENDING);
                    }
                }
            }
        });
        return sorter;
    }

    /**
     * Este método estático actualiza el orden de clasificación del TableRowSorter en función de la columna clicada.
     * @param column La columna clicada.
     * @param sortKeys Las claves de ordenación actuales.
     * @param sorter El TableRowSorter.
     * @param sortOrder El nuevo orden de clasificación.
     */
    private static void setSortOrderByColumn(int column, List<RowSorter.SortKey> sortKeys, TableRowSorter<PokemonTableModel> sorter, SortOrder sortOrder) {
        sortKeys.clear();
        sortKeys.add(new RowSorter.SortKey(column, sortOrder));
        sorter.setSortKeys(sortKeys);
    }


    // Los siguientes métodos crean y configuran cada botón individual, incluyendo escuchadores y configuración de iconos:
    private void createAddButton() {
        addButton = new JButton("Add");
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("arial", Font.BOLD, 15));
        addButton.setBackground(Color.LIGHT_GRAY);
        ImageIcon addImageIcon = new ImageIcon("src/controllers/Pictures/anadir.png");
        addButton.setIcon(new ImageIcon(addImageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        addButton.addActionListener(e -> new AddPokemonDialog(Pokedex.this, model).setVisible(true));
    }
    private void createEditButton() {
        editButton = new JButton("Edit");
        editButton.setForeground(Color.BLACK);
        editButton.setFont(new Font("arial", Font.BOLD, 15));
        editButton.setBackground(Color.LIGHT_GRAY);
        ImageIcon editImageIcon = new ImageIcon("src/controllers/Pictures/edit.png");
        editButton.setIcon(new ImageIcon(editImageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        editButton.addActionListener(e -> onClickEditButton());
    }
    private void createRemoveButton() {
        removeButton = new JButton("Remove");
        removeButton.setForeground(Color.BLACK);
        removeButton.setFont(new Font("arial", Font.BOLD, 15));
        removeButton.setBackground(Color.LIGHT_GRAY);
        ImageIcon removeImageIcon = new ImageIcon("src/controllers/Pictures/borrar.png");
        removeButton.setIcon(new ImageIcon(removeImageIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        removeButton.addActionListener(e -> onClickRemoveButton());
    }
    private void createFightButton() {
        fightButton = new JButton("Fight");
        fightButton.setForeground(Color.BLACK);
        fightButton.setFont(new Font("arial", Font.BOLD, 15));
        fightButton.setBackground(Color.LIGHT_GRAY);
        ImageIcon fightImageIcon = new ImageIcon("src/controllers/Pictures/fight.png");
        fightButton.setIcon(new ImageIcon(fightImageIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        fightButton.addActionListener(e -> onClickFightButton());

    }
    private void createCloneButton() {
        cloneButton = new JButton("Clone");
        cloneButton.setForeground(Color.BLACK);
        cloneButton.setFont(new Font("arial", Font.BOLD, 15));
        cloneButton.setBackground(Color.LIGHT_GRAY);
        ImageIcon cloneImageIcon = new ImageIcon("src/controllers/Pictures/clone.png");
        cloneButton.setIcon(new ImageIcon(cloneImageIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        cloneButton.addActionListener(e -> onClickCloneButton());

    }

    /**
     * Este método crea y configura la barra de menú, incluyendo todos los menús y elementos.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.addActionListener(e -> onClickLoadMenuItem());
        fileMenu.add(loadMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(e -> onClickSaveMenuItem());
        fileMenu.add(saveMenuItem);
    }

    // Los siguientes métodos son llamados cuando cada botón correspondiente o elemento de menú es clicado:
    private void onClickLoadMenuItem() {
        try {
            model.setPokemonList(DatabaseConnection.GetAllPokemons());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(Pokedex.this,
                    "Error loading pokemon list: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void onClickSaveMenuItem() {
        try {
            DatabaseConnection.SaveAllPokemons(model.getPokemonList());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(Pokedex.this,
                    "Error saving pokemon list: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
    private void onClickEditButton() {
        int selectedRow = pokemonJTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(Pokedex.this,
                    "Please select a pokemon to edit.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedModelRowIndex = pokemonJTable.convertRowIndexToModel(selectedRow);
        Pokemon selectedPokemon = model.getPokemon(selectedModelRowIndex);
        new EditPokemonDialog(Pokedex.this, model, selectedPokemon).setVisible(true);

    }
    private void onClickRemoveButton() {
        int selectedRow = pokemonJTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(Pokedex.this,
                    "Please select a pokemon to remove.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(Pokedex.this,
                "Are you sure you want to delete this pokemon?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            int selectedModelRowIndex = pokemonJTable.convertRowIndexToModel(selectedRow);
            model.removePokemon(selectedModelRowIndex);
        }

    }
    private void onClickFightButton() {
        int selectedRow = pokemonJTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(Pokedex.this,
                    "Please select a pokemon to fight.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedModelRowIndex = pokemonJTable.convertRowIndexToModel(selectedRow);
        Pokemon selectedPokemon = model.getPokemon(selectedModelRowIndex);
        new FightPokemonDialog(Pokedex.this, model, selectedPokemon).setVisible(true);

    }
    private void onClickCloneButton() {
        int selectedRow = pokemonJTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(Pokedex.this,
                    "Please select a pokemon to clone.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedModelRowIndex = pokemonJTable.convertRowIndexToModel(selectedRow);
        Pokemon selectedPokemon = model.getPokemon(selectedModelRowIndex);
        model.addPokemon(selectedPokemon.clone());

    }

    /**
     * El método principal crea y muestra la ventana de Pokedex.
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
            Pokedex pokedex = new Pokedex();
            pokedex.setSize(800, 600);
            pokedex.setResizable(false);
            pokedex.setVisible(true);
        }
}
