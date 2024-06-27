package pag_con;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO extends Produto {
    //METODO CONSTRUTO DA MINHA DAO /pis estar herdando os atributos da class Produto 
    public DAO(int id, String nome, String descricao) {
        super(id, nome, descricao);
    }



  //O ATRIBUTO VOLATIO "Produto p" E NECESSARIO PARA RECEBER OS PARAMETROS DA CLASS PRODUTO  
    public void criar(Produto p) throws ClassNotFoundException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tb_itens (nome, descricao) VALUES (?, ?)");
            stmt.setString(1, p.getNome());//PARAMETROS DA CLAS pRODUTO
            stmt.setString(2, p.getDescricao());//PARAMETROS DA CLAS pRODUTO
            stmt.executeUpdate();//FAZ UPCRADE NA TB 
            System.out.println("Item adicionado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao criar item: " + e.getMessage());
        } finally {
            Conexao.fechar(con, stmt);
        }
    } 
    //........................................................................................................
    
  //metodo para mostrar tb no console / lista de itens da tb_itens 
    public List<Produto> buscaLista() throws ClassNotFoundException, SQLException {
        ArrayList<Produto> listaProdutos = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = Conexao.getConnection();
            stmt = con.prepareStatement("SELECT * FROM tb_itens");
            rs = stmt.executeQuery();

            while (rs.next()) {
            	//ESTES VALORES NO PARAMETRO DEVEM ESTA IGUAIS AOS DA TB_ITENS 
                Produto produto = new Produto(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"));
                listaProdutos.add(produto);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao chamar lista: " + e);
        } finally {
            Conexao.fechar(con, stmt);
        }

        return listaProdutos;
    }
    
    
    
    //.........................................................................................................

    public void excluir(int id) throws ClassNotFoundException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM tb_itens WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();//ALTERA  A TB 
            System.out.println("Linha excluída com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir linha: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexao.fechar(con, stmt);
        }
    }
    
//VERIFICA SE O ID EXISTE NA TB_ITENS 
    public boolean existeItem(int id) throws ClassNotFoundException {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
        	// É uma função agregada que conta o número de registros na tabela.
            stmt = con.prepareStatement("SELECT COUNT(*) FROM tb_itens WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            System.out.println("did existe ");
            
            if (rs.next()) {
                //e verifica se é maior que zero. Se for, isso significa que o item com o ID fornecido existe na 
                int count = rs.getInt(1);//tabela, então o método retorna true. Caso contrário, retorna false.
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar se o item existe: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexao.fechar(con, stmt);
        }

        return false;
    }
//..........................................................................................................
    public void editar(int id, String novoNome, String novaDescricao) throws ClassNotFoundException {
        if (existeItem(id)) {//SE O ID EXISTIR NA TB PASSARA NESTA CONDICAO
            Connection con = Conexao.getConnection();
            PreparedStatement stmt = null;

            try {
                stmt = con.prepareStatement("UPDATE tb_itens SET nome = ?, descricao = ? WHERE id = ?");
                stmt.setString(1, novoNome);
                stmt.setString(2, novaDescricao);
                stmt.setInt(3, id);//NAO PRECISA SETAR O ID NA MAIN , POIS JA TA ALTOMATICO NO BD 
                stmt.executeUpdate();
                System.out.println("Objeto editado com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao editar objeto: " + e.getMessage());
                e.printStackTrace();
            } finally {
                Conexao.fechar(con, stmt);
            }
        } else {
            System.out.println("O item com o ID " + id + " não existe na tabela.");
        }
    }
}


	// PARA ENTRAR COM O MAVENS , DEVEMOS IR NAS OPÇOES 
	//NEW / OTHER 
	//MAVEN PROJECT 
	//