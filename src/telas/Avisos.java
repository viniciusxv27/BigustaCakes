package telas;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import conexao.ConexaoDAO;

public class Avisos extends JFrame {
    private JPanel mainPanel;

    public Avisos() {
        setTitle("Avisos BigustaCakes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);
        loadNotifications();
    }

    private void loadNotifications() {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            ConexaoDAO conexao = new ConexaoDAO();
            connection = conexao.conectaBD();
            String sql = "SELECT * FROM avisos";
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String conteudo = rs.getString("conteudo");
                String dataCriacao = rs.getString("data_criacao");

                // Criação do painel de aviso
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                panel.setBackground(Color.LIGHT_GRAY);

                // Labels de aviso
                JLabel tituloLabel = new JLabel("Título: " + titulo);
                JLabel conteudoLabel = new JLabel("<html><body style='width: 400px'>" + conteudo + "</body></html>");
                JLabel dataCriacaoLabel = new JLabel("Data de Criação: " + dataCriacao);

                tituloLabel.setFont(new Font("Arial", Font.BOLD, 14));
                conteudoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                dataCriacaoLabel.setFont(new Font("Arial", Font.ITALIC, 12));

                // Adicionar labels ao painel de aviso
                panel.add(tituloLabel, BorderLayout.NORTH);
                panel.add(conteudoLabel, BorderLayout.CENTER);
                panel.add(dataCriacaoLabel, BorderLayout.SOUTH);

                // Adicionar painel de aviso ao painel principal
                mainPanel.add(panel);
                mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adicionar espaço entre os avisos
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Avisos app = new Avisos();
            app.setVisible(true);
        });
    }
}
