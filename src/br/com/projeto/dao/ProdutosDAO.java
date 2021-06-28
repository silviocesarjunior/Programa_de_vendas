/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.Fornecedores;
import br.com.projeto.model.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Silvio Cesar Junior
 */
public class ProdutosDAO {

    private Connection con;

    public ProdutosDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    //metodo cadastrar produtos
    public void cadastrar(Produtos obj) {
        try {
            String sql = "insert tb_produtos(descricao,preco,qtd_estoque,for_id)values (?,?,?,?)";

            //metodo - conectar o banco de dados
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            stmt.setInt(4, obj.getFornecedor().getId());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Produto cadastrado com Sucesso");

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }
    }

    //metodo alterar produtos
    public void alterar(Produtos obj) {
        try {
            String sql = "update tb_produtos set descricao=?,preco=?,qtd_estoque=?,for_id=? where id=?";
            //2 metodo - conectar o banco de dados
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            stmt.setInt(4, obj.getFornecedor().getId());
            stmt.setInt(5,obj.getId());
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Produto alterado com Sucesso");

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }
    }

    //metodo excluir
    public void excluir(Produtos obj){
        try {
            
            String sql="delete from tb_produtos where id=?";
            //2passo - conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt=con.prepareStatement(sql);
            
            stmt.setInt(1,obj.getId());
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Produto excluído com Sucesso");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }
    }
    //metodo listar produtos
    public List<Produtos> listarProdutos() {
        try {
            //1 passo
            List<Produtos> lista = new ArrayList<>();

            //2 passo criar sql, organizar e listar
            String sql = "select p.id,p.descricao,p.preco,p.qtd_estoque,f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on(p.for_id = f.id)";

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();

                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));

                f.setNome(rs.getString(("f.nome")));
                obj.setFornecedor(f);

                lista.add(obj);
            }
            return lista;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
            return null;
        }

    }
    //metodo listar produtos
    public List<Produtos> listarProdutosPorNome(String nome) {
        try {
            //1 passo
            List<Produtos> lista = new ArrayList<>();

            //2 passo criar sql, organizar e listar
            String sql = "select p.id,p.descricao,p.preco,p.qtd_estoque,f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on(p.for_id = f.id)where p.descricao like?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();

                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));

                f.setNome(rs.getString(("f.nome")));
                obj.setFornecedor(f);

                lista.add(obj);
            }
            return lista;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
            return null;
        }

    }
    
    //metodo objeto do tipo produto
    public Produtos consultaPorNome(String nome) {
        try {
            //1 passo
           
            String sql = "select p.id,p.descricao,p.preco,p.qtd_estoque,f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on(p.for_id = f.id)where p.descricao like =?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            Produtos obj=new Produtos();
            Fornecedores f=new Fornecedores();

            if (rs.next()) {
                
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));

                f.setNome(rs.getString(("f.nome")));
                
                obj.setFornecedor(f);

            }
            return obj;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!" + erro);
            return null;
        }

    }
        //metodo busca produto por código
    public Produtos buscaporcodigo(int id) {
        try {
            //1 passo
           
            String sql = "select * from tb_produtos where id= ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Produtos obj=new Produtos();
            Fornecedores f=new Fornecedores();

            if (rs.next()) {
                
                obj.setId(rs.getInt("id"));
                obj.setDescricao(rs.getString("descricao"));
                obj.setPreco(rs.getDouble("preco"));
                obj.setQtd_estoque(rs.getInt("qtd_estoque"));

            }
            return obj;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!" + erro);
            return null;
        }

    }
    
    //metodo que da baixa no estoque
    public void baixarEstoque(int id,int qtd_nova){
        try {
            String sql="update tb_produtos set qtd_estoque=? where id=?";
            //2 passo - conectar o banco de dados e orgaizar o comando sql
            PreparedStatement stmt=con.prepareStatement(sql);
            
            stmt.setInt(1, qtd_nova);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
            
            //JOptionPane.showMessageDialog(null,"Produto Alterado com Sucesso");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null,"Erro: "+ erro);
        }
        
    }
    
    //metodo que da baixa no estoque
    public void adicionarEstoque(int id,int qtd_nova){
        try {
            String sql="update tb_produtos set qtd_estoque=? where id=?";
            //2 passo - conectar o banco de dados e orgaizar o comando sql
            PreparedStatement stmt=con.prepareStatement(sql);
            
            stmt.setInt(1, qtd_nova);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
            
            //JOptionPane.showMessageDialog(null,"Produto Alterado com Sucesso");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null,"Erro: "+ erro);
        }
        
    }
    
    //metodo que retorna o estoque atual de um produto
    public int retornaEstoqueAtual(int id){
        try {
            int qtd_estoque = 0;
            String sql="SELECT qtd_estoque from tb_produtos where id=?";
            
            PreparedStatement stmt=con.prepareStatement(sql);
            stmt.setInt(1,id);
            
            ResultSet rs=stmt.executeQuery();
            
            if(rs.next()){
                qtd_estoque=(rs.getInt("qtd_estoque"));
            }
            return qtd_estoque;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
