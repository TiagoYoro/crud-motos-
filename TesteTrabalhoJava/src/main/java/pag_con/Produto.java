package pag_con;

public class Produto  {
	
	private int id;
	private String nome;
	private String descricao;
	
	//costrutor publico 
	public Produto(int id, String nome, String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	
	
	// 
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	// to string 
	public String Resumo() {
		return "sUPER [id=" + id + ", nome=" + nome + ", descricao=" + descricao + "]";
	}
	
	
	

	
	
	
	

}
