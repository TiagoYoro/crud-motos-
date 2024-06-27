package pag_con;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Conexao {
	public  static final String DRIVE="com.mysql.cj.jdbc.Driver";
	public  static final String URL="jdbc:mysql://localhost:3306/db_itens";
	public  static final String USER="root";
	public  static final String PASS="Yoroshi666";
	
	//responsavel por conectar os metodos 
	Connection connection = null;//colocanqui fora do escopo evita de repeti lo em todos os metodos 
	
	
	//metodo de conexao 
	public static Connection getConnection() throws ClassNotFoundException {
		//try 
		try {
			Class.forName(DRIVE);
			return DriverManager.getConnection(URL, USER,PASS);
			
		}catch(SQLException e) {
			System.out.println("erro ao conectar "+ e);
		}
		return null; 
		
	}
	
	//CRIANDO METODO PARA FECHAR  O BD 
	public static void fechar(Connection con) {
		try {
			if(con != null) {
				con.close();
				
			}
			}catch(SQLException e) {
				e.printStackTrace();
				
			}
		}
	//....................fexhar  conexao  sobrescrevendo o metodo fechar ......................................
	public static void fechar(Connection con ,PreparedStatement stmt) {
		try {
			if(stmt != null) {
				stmt.close();
			}
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	//...........................................................................................	
	}
	
	
	
		
		




