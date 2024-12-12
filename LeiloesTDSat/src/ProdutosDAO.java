/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
    
        conn = new conectaDAO().connectDB();
        
        try { 
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor) VALUES(?,?)");
            
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            
            prep.executeUpdate();
            
        } catch(Exception ex) {
            System.out.println("Erro ao conectar com o banco de dados: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        conn = new conectaDAO().connectDB(); // Conexão com o banco
        listagem = new ArrayList<>(); // Inicializa a lista

        String sql = "SELECT id, nome, valor, status FROM produtos";

        try {
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor")); // Ajuste para Double, se necessário
            produto.setStatus(resultset.getString("status"));

            listagem.add(produto); // Adiciona o produto à lista
        }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
      }
        
        return listagem;
    }
    
}

