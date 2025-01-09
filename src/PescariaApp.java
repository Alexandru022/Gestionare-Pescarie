import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PescariaApp {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Pescarie";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123321";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestionarea Pescăriei");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(4, 1));

            JButton btnVanzari = new JButton("Vânzări");
            JButton btnComenzi = new JButton("Comenzi");
            JButton btnFurnizori = new JButton("Furnizori");
            JButton btnProduse = new JButton("Produse");

            btnVanzari.addActionListener(e -> deschideTabela("Vanzari"));
            btnComenzi.addActionListener(e -> deschideTabela("Comenzi"));
            btnFurnizori.addActionListener(e -> deschideTabela("Furnizori"));
            btnProduse.addActionListener(e -> deschideTabela("Produse"));

            frame.add(btnVanzari);
            frame.add(btnComenzi);
            frame.add(btnFurnizori);
            frame.add(btnProduse);

            frame.setVisible(true);
        });
    }

    private static void deschideTabela(String tabela) {
        JFrame tabelaFrame = new JFrame("Tabela: " + tabela);
        tabelaFrame.setSize(600, 400);
        tabelaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Caută");
        JButton insertButton = new JButton("Inserare");
        JButton deleteButton = new JButton("Ștergere");

        searchButton.addActionListener(e -> populeazaTabel(model, tabela, searchField.getText()));

        insertButton.addActionListener(e -> {
            adaugaEntitate(model, tabela);
            populeazaTabel(model, tabela, "");
        });

        deleteButton.addActionListener(e -> {
            stergeEntitate(model, tabela);
            populeazaTabel(model, tabela, "");
        });

        tabelaFrame.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Caută: "));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(insertButton);
        bottomPanel.add(deleteButton);

        tabelaFrame.add(topPanel, BorderLayout.NORTH);
        tabelaFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        tabelaFrame.add(bottomPanel, BorderLayout.SOUTH);

        populeazaTabel(model, tabela, "");

        tabelaFrame.setVisible(true);
    }

    private static void populeazaTabel(DefaultTableModel model, String tabela, String criteriu) {
        model.setRowCount(0);
        model.setColumnCount(0);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + tabela;

            if (!criteriu.isEmpty()) {
                query += " WHERE Nume_Produs LIKE '%" + criteriu + "%' OR ID_Produs = '" + criteriu + "'";
            }

            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(rsmd.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eroare la încărcarea datelor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void adaugaEntitate(DefaultTableModel model, String tabela) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tabela + " LIMIT 1")) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            JTextField[] fields = new JTextField[columnCount];
            JPanel panel = new JPanel(new GridLayout(0, 2));
            for (int i = 0; i < columnCount; i++) {
                fields[i] = new JTextField();
                fields[i].setToolTipText(rsmd.getColumnName(i + 1));
                panel.add(new JLabel(rsmd.getColumnName(i + 1)));
                panel.add(fields[i]);
            }

            int result = JOptionPane.showConfirmDialog(null, panel, "Introdu datele",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                StringBuilder query = new StringBuilder("INSERT INTO " + tabela + " VALUES (");
                for (int i = 0; i < columnCount; i++) {
                    query.append("?");
                    if (i < columnCount - 1) query.append(",");
                }
                query.append(")");

                try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
                    for (int i = 0; i < columnCount; i++) {
                        pstmt.setString(i + 1, fields[i].getText());
                    }
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Entitate adăugată cu succes!");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eroare la inserare: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void stergeEntitate(DefaultTableModel model, String tabela) {
        String id = JOptionPane.showInputDialog("Introduceți ID-ul de șters:");
        if (id != null && !id.isEmpty()) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + tabela + " WHERE ID_Produs = ?")) {
                pstmt.setInt(1, Integer.parseInt(id));
                int rowsAffected = pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, rowsAffected > 0 ? "Entitate ștearsă cu succes!" : "Nicio entitate găsită cu acest ID!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Eroare la ștergere: " + e.getMessage());
            }
        }
    }
}
