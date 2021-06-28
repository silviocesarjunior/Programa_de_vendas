/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.Clientes;
import br.com.projeto.model.ItemVenda;
import br.com.projeto.model.Produtos;
import br.com.projeto.model.Vendas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Silvio Cesar Junior
 */
public class ItemVendaDAO {
    private Connection con;

    public ItemVendaDAO() {
        this.con = new ConnectionFactory().getConnection();
    }
    
    // metodo que cadastra itens
    public void cadastraItem(ItemVenda obj){
        try {
            String sql = "insert tb_itensvendas (venda_id,produto_id,qtd,subtotal)values (?,?,?,?)";

            //metodo - conectar o banco de dados
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getVendas().getId());
            stmt.setInt(2, obj.getProduto().getId());
            stmt.setInt(3, obj.getQtd());
            stmt.setDouble(4, obj.getSubtotal());

            stmt.execute();
            stmt.close();

          
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }
    }
    
    //metodo que lista itens vendidos de uma venda por id
    public List<ItemVenda> listaItensPorVenda(int venda_id) {
        try {
            //1 passo
            List<ItemVenda> lista = new ArrayList<>();

            //2 passo criar sql, organizar e listar
            String sql = "select p.descricao,i.qtd,p.preco,i.subtotal from tb_itensvendas as i"
                    + " inner join tb_produtos as p on(i.produto_id=p.id)where i.venda_id=?";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,venda_id);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
               ItemVenda item=new ItemVenda();
               Produtos prod=new Produtos();
               
               prod.setDescricao(rs.getString("p.descricao"));
               item.setQtd(rs.getInt("i.qtd"));
               prod.setPreco(rs.getDouble("p.preco"));
               item.setSubtotal(rs.getDouble("i.subtotal"));
              
               item.setProduto(prod);
                lista.add(item);
            }
            return lista;
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
            return null;
        }
        
    }
    
    
}
