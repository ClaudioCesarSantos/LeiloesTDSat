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
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES(?,?,?)");
            
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            prep.executeUpdate();
            
        } catch(Exception ex) {
            System.out.println("Erro ao conectar com o banco de dados: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        conn = new conectaDAO().connectDB(); 
        listagem = new ArrayList<>(); 

        String sql = "SELECT id, nome, valor, status FROM produtos";

        try {
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor")); 
            produto.setStatus(resultset.getString("status"));

            listagem.add(produto); 
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
    
    public void venderProduto(int idProduto) {
        conn = new conectaDAO().connectDB();
        
        try {
            String sql = "UPDATE produtos SET status = ? WHERE id = ?";
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            prep.setInt(2, idProduto);
            
            int rowsUpdated = prep.executeUpdate();
            
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            }
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao vender o produto: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        conn = new conectaDAO().connectDB();
        ArrayList<ProdutosDTO> produtosVendidos = new ArrayList<>();
        
        String sql = "SELECT id, nome, valor, status FROM produtos WHERE status = ?";
        
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
               ProdutosDTO produto = new ProdutosDTO();
               produto.setId(resultset.getInt("id"));
               produto.setNome(resultset.getString("nome"));
               produto.setValor(resultset.getInt("valor"));
               produto.setStatus(resultset.getString("status"));
               
               produtosVendidos.add(produto);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + ex.getMessage());
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
        return produtosVendidos;
    }
    
}

