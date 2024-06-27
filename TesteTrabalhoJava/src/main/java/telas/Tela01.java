package telas;
import javax.swing.*;
import pag_con.DAO;
import pag_con.Produto;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class Tela01 {
	
	//usado para permitir que os usuários insiram e editem texto.
    private JTextField nomeField;//quandrado onde podemos digitar 
    // esta declara uma variável de instância privada chamada 
    //sendo usada para capturar a descrição do produto que o usuário deseja criar.
    private JTextField descricaoField;
    //CRIA UM BOTAO 	
    private JButton criarButton;
    private JButton mostrarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton cancelarButton;
    //faz uso dos metodos da classe ,atraves desta declaraçao 
    private DAO dao;
    // criando a janela 
    public Tela01(DAO dao) {
        this.dao = dao;//recependo parametros da class dao 
        //titulo da tabela 
        JFrame tabela=new JFrame("Tabela de Produtos ");//estanciando o objeto tabela com o titulo no parametro  
        tabela.setSize(600, 300);//dimensao da tabela /como 300 pixels de largura por 200 pixels de altura.
        tabela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//respossavel por fechar o tabela ao clicar no X 
        //Define o layout da janela como uma grade (grid layout) com 3 linhas e 2 colunas
        tabela.setLayout(new GridLayout(5, 3));
        
        //cria uma label para digitar o nome do produto 
        JLabel nomeLabel=new JLabel("Nome:");
        //Cria um campo de texto vazio onde o usuário poderá inserir o nome do produto.
        nomeField =new JTextField();
        
        tabela.add(nomeLabel);//adiciona o nome escrito 
        tabela.add(nomeField);//usado para permitir que os usuários insiram e editem texto
        
        //..............................................................
        
        //cria uma label para digitar a descriçao 
        JLabel descricaoLabel=new JLabel("Descrição:");
        //Cria um campo de texto vazio onde o usuário poderá inserir o nome do produto.
        descricaoField = new JTextField();//este dado daqui sera passado p/ descricaoField , la no metodo criarProduto()
        //Adiciona o rótulo e o campo de texto à janela
        tabela.add(descricaoLabel);
      //sendo usada para capturar a descrição do produto que o usuário deseja criar.
        tabela.add(descricaoField);
      //........................................................................
       
        
        //......................... botao MOSTRAE ...............................................
        mostrarButton = new JButton("Mostrar Tabela");//criando o botao mostraar 
        mostrarButton.addActionListener(new ActionListener() {//metodo mostrar 
            public void actionPerformed(ActionEvent e) {
                try {
					mostrarTabela();
					//JOptionPane.showMessageDialog(null, "mostrando lista de produtos ");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, " eroo ao chamar lista de prdutos o");
					e1.printStackTrace();
				}
            }
        });
      //............................botao com funçao de criar .......................................
        criarButton = new JButton("Criar produto ");
        criarButton.addActionListener(new ActionListener() {
        	// Quando o botão for clicado, o método criarProduto() será chamado.
            public void actionPerformed(ActionEvent e) {
                criarProduto();
            }
        });
        
      //..........................EXCLUIR ..............................................
        excluirButton =new JButton("excluir");
        excluirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//add metod
				try {
				excluirTB();
				System.out.println("intem excluido ");
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao excluir produto: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
				}
				
			}
		});
        
        //.........................EDITATR PRODUTO  ...............................................
        editarButton =new JButton("editar");
        editarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e ) {
        		try {
        			editar();
        		}catch(Exception e1 ) {
        			JOptionPane.showConfirmDialog(null , "erro ao editar " + e1.getMessage(),
        					"Erro", JOptionPane.ERROR_MESSAGE );
        					e1.printStackTrace();
        		}
        	}
        });
        
      //.........................cancelar  ............................................... 
        cancelarButton=new JButton("cancelar");
        cancelarButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					cancelar();
					JOptionPane.showMessageDialog(null ,"produto cancelado ");
				}catch(Exception ex ) {
					JOptionPane.showConfirmDialog(null , "erro ao  cancelar produto  " + ex.getMessage(),
        					"Erro", JOptionPane.ERROR_MESSAGE );
        					ex.printStackTrace();
				}
				
			}
		} );
        //---------- OBS OS BOTOES DEVEM SER ADICIONADO  A BAIXO DAS ESTANCIAS DO BOTOES , CASO CONTRARIO ERRO !!!
        //Adiciona o botão "Criar" à janela. 
        tabela.add(criarButton);
        tabela.add(mostrarButton);
        tabela.add(excluirButton);
        tabela.add(editarButton);
        tabela.add(cancelarButton);
         //Torna a janela visível, exibindo-a na tela para o usuário.
        tabela.setVisible(true);
        
    }
    
    //;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
  //medodo cria exclusivo para a classe jframe //este metodo pega o metodo criar da clase DAO
    private void criarProduto() { 
    	//Obtém o texto inserido no campo de texto nomeField e armazena na variável nome.
        String nome = nomeField.getText();
        //btém o texto inserido no campo de texto descricaoField e armazena na variável descricao.
        String descricao = descricaoField.getText();    
        try {
        	//metodo criar da DAO // pucha atributos da class Produto id , nome , descricao 
            dao.criar(new Produto(0, nome, descricao)); // 0 para o ID, que geralmente é gerado pelo banco de dados
            JOptionPane.showMessageDialog(null, "Produto criado com sucesso!");
            //para limpar os campos de texto, deixando-os vazios para uma possível criação 
            limparCampos();
        }catch (ClassNotFoundException ex) {
        	
            JOptionPane.showMessageDialog(null, "Erro ao criar produto: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
            //Imprime o rastreamento da pilha da exceção no console.
            ex.printStackTrace();
        }
    }
    // metodo mostrar tb 
    private void mostrarTabela() throws SQLException {
       //try and catch
    	try {
    		//obtendo o lista de de produtos da dao 
    		List<Produto>listProdutos=dao.buscaLista();
    		//criando array bidimensional  /para dados da tabela 
    		//OBS PALAVRA RECERVADA Object
    		Object [][] data =new Object [listProdutos.size()][3];//3 colunas./ i== linhas dos objetos estanciado 
    		//loop para percorre o listProdutos e pegar os atributos dos produtos 
    		for(int i=0; i<listProdutos.size(); ++i) {
    			//pegando posicao de cada produto 
    			Produto produto=listProdutos.get(i);
    			data[i][0]= produto.getId();
    			data[i][1]= produto.getNome();
    			data[i][2]=produto.getDescricao();
    		}
    		//array/vetor  para o nome das colunas 
    		String[] colunas={"id","nome ","descricao "};
    		 // Cria a tabela com os dados e colunas / estanciando objeto 
            JTable tbProduto= new JTable(data, colunas);
            //adicionando um painel de rolagem para scrolar na tb /estanciando objeto 
            JScrollPane scroll =new JScrollPane(tbProduto);
            //estanciando uma janela para exibir a tabela 
            JFrame frame = new JFrame("tabela de produtos ");
            //para fechar clicando no x 
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            //add scroll na tbproduto 
            frame.add(scroll);//barra de scroll
            frame.pack();//Esta linha redimensiona automaticamente a janela
            frame.setVisible(true);//deixa a tbprodutos visivel   
    	}catch(ClassNotFoundException e ) {
    		JOptionPane.showMessageDialog(null,"erro ao chamar tabela ");
        	System.out.println("erro ao chamar tabela " +e);
    	}
    	
    }
    
    //metodo de exluir 
    public void excluirTB() {//ADICIONAR O METODO PARA VERIFICAR SE O ID EXISTE ANTES DE EXCLUIR 
    	//vamos obter o id do produto 
    	String idString  =JOptionPane.showInputDialog("digite o id do produto ");
    	//condicional 
    	if(idString==null) {
    		return;
    	}
    	//vamos converter a String para int 
    	int id =Integer.parseInt(idString);
    	//try and catch 
    	try {
    		if(dao.existeItem(id)) {
        		//chamndo o metodo de ediçao da dao 
    			//chamando o metodo excluir la da DAO
        		dao.excluir(id);
        		JOptionPane.showMessageDialog(null,"produto excluido com sucesso ");	
        	}else {
        		JOptionPane.showMessageDialog(null , "o produto com o iod informado nao existe ");
        	}
    		
    	}catch(ClassNotFoundException e ) {
    		JOptionPane.showMessageDialog(null,"erro ao ecluir produto ");
    		System.out.println("este e o erro "+ e);
    	}
    }
    
    //metodo editar 
    public void editar() {
    	// Obtém os novos dados do produto
    	// showInputDialog gera uma label para digitar  
        String IdAtual = JOptionPane.showInputDialog("Digite o ID do produto a ser editado:");
        String novoNome = JOptionPane.showInputDialog("Digite o novo nome:");
        String novaDescricao = JOptionPane.showInputDialog("Digite a nova descrição:");
     // Verifica se o usuário cancelou a operação
        //caso cancele os atributos continua como null 
        if (IdAtual  == null || novoNome == null || novaDescricao == null){
            return;//caso o usuario acancele a ediçao  da o return 
            // que interrompe a execução do método editar() e retorna sem fazer mais nada na tb
        }
    	//convertendo o id para interger 
        int id =Integer.parseInt(IdAtual );//era string agora é int 
        try {
        	//usando o metodo existeitens da calss dao 
        	//verifica se o item existe antes de edita lo 
        	if(dao.existeItem(id)) {
        		//chamndo o metodo de ediçao da dao 
            	dao.editar(id, novoNome, novaDescricao);
            	JOptionPane.showMessageDialog(null , "editado com sucesso  ");
        	}else {
        		JOptionPane.showMessageDialog(null , "o produto com o iod informado nao existe ");
        	}
        }catch(ClassNotFoundException e ) {
        	JOptionPane.showMessageDialog(null , "erro ao editar produto ");
        	System.out.println("erro ao editar "+e);
        }
    	
    	
    }
    
    //metodo para fechar janela 
    public void cancelar() {
    	JOptionPane.showMessageDialog(null, "o metodo ainda nao foi elaborado completo ");
    }
    

    private void limparCampos() {
    	//Define o texto do campo de texto nomeField como uma string vazia. Isso limpa qualquer texto que
    	//tenha sido inserido anteriormente no campo de nome.
        nomeField.setText("");
        //Define o texto do campo de texto descricaoField como uma string vazia. Isso limpa qualquer texto que 
        //tenha sido inserido anteriormente no campo de descrição.
        descricaoField.setText("");
    }

    public static void main(String[] args) {
    	//Isso garante que o código que atualiza a GUI seja executado de forma segura e na ordem correta.

        SwingUtilities.invokeLater(new Runnable() {
        	//Início da definição do método run do Runnable, que contém o código a ser 
        	//executado no thread de despacho de eventos.
            public void run() {
                DAO dao = new DAO(0, null, null);
                //cria o objeto 
                new Tela01(dao);
            }
        });
    }
}
