package telas;

import conexao.ConexaoDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Menu extends javax.swing.JFrame {

    private JPanel produtosPanel;

    public Menu() {
        initComponents();
        setTitle("Menu BigustaCakes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Painel para exibir os produtos
        produtosPanel = new JPanel();
        produtosPanel.setLayout(new GridLayout(0, 3, 10, 10)); // Grid com 3 colunas e espaçamento de 10 pixels
        JScrollPane scrollPane = new JScrollPane(produtosPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Carregar produtos do banco de dados e exibir em boxes
        carregarProdutos();
    }

    private void carregarProdutos() {
        ConexaoDAO conexaoDAO = new ConexaoDAO();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conexao = conexaoDAO.conectaBD();
            String sql = "SELECT id_Produto, nome FROM produtos";
            pstmt = conexao.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_Produto");
                String nome = rs.getString("nome");

                // Criar um painel para o produto
                JPanel produtoPanel = criarProdutoPanel(id, nome);
                produtosPanel.add(produtoPanel);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private JPanel criarProdutoPanel(int id, String nome) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel(nome);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(nameLabel, BorderLayout.NORTH);

        JButton detalhesButton = new JButton("Detalhes");
        detalhesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirDetalhesProduto(id);
            }
        });
        panel.add(detalhesButton, BorderLayout.SOUTH);

        return panel;
    }

    private void exibirDetalhesProduto(int idProduto) {
        ConexaoDAO conexaoDAO = new ConexaoDAO();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conexao = conexaoDAO.conectaBD();
            String sql = "SELECT * FROM produtos WHERE id_Produto = ?";
            pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, idProduto);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int quantidadeEstoque = rs.getInt("quantidade_estoque");
                double custo = rs.getDouble("custo");
                double preco = rs.getDouble("preco");
                String descricao = rs.getString("descricao");
                String validade = rs.getString("validade");

                // Exibir detalhes do produto em uma janela de diálogo
                String mensagem = "ID: " + idProduto + "\n"
                        + "Quantidade em Estoque: " + quantidadeEstoque + "\n"
                        + "Custo: $" + custo + "\n"
                        + "Preço: $" + preco + "\n"
                        + "Descrição: " + descricao + "\n"
                        + "Validade: " + validade;

                JOptionPane.showMessageDialog(this, mensagem, "Detalhes do Produto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar detalhes do produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Menu().setVisible(true);
        });
    }
}
