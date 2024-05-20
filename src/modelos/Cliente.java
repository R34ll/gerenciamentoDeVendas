package modelos;

import java.io.IOException;

import Uteis.Csv;

public class Cliente {
    // Atributos privados do cliente
    private int id;                  // Identificador único do cliente
    private String nome;             // Nome do cliente
    private String cpf;              // CPF do cliente
    private String telefone;         // Telefone do cliente
    private String cadastro;         // Data de cadastro do cliente
    private String ultimaCompra;     // Data da última compra do cliente
    
    /**
     * Construtor da classe Cliente.
     * 
     * @param id Identificador único do cliente
     * @param nome Nome do cliente
     * @param cpf CPF do cliente
     * @param telefone Telefone do cliente
     * @param cadastro Data de cadastro do cliente
     * @param ultima_compra Data da última compra do cliente
     */
    public Cliente(int id, String nome, String cpf, String telefone, String cadastro, String ultima_compra) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.cadastro = cadastro;
        this.ultimaCompra = ultima_compra;
    }

    // Métodos getters para acessar os atributos do cliente
    public String getCadastro() {
        return cadastro;
    }

    public String getCpf() {
        return cpf;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getUltimaCompra() {
        return ultimaCompra;
    }

    /**
     * Salva as informações do cliente no arquivo CSV.
     * 
     * @throws IOException se ocorrer um erro durante a gravação no arquivo CSV
     * @throws NumberFormatException se o formato dos dados estiver incorreto
     */
    public void salvar() throws IOException, NumberFormatException {
        Csv csv = new Csv("src\\dados\\clientes.csv");
        csv.adicionar(this.toString());  // Adiciona a representação do cliente ao arquivo CSV
    }

    /**
     * Atualiza as informações do cliente no arquivo CSV.
     * 
     * @throws Exception se ocorrer um erro durante a atualização
     */
    public void update() throws Exception {
        Csv csv = new Csv("src\\dados\\clientes.csv");
        csv.removePorId(this.id);  // Remove a entrada do cliente com o mesmo ID
        this.salvar();  // Salva as novas informações do cliente
    }

    /**
     * Retorna a representação do cliente como uma string no formato CSV.
     * 
     * @return String representando o cliente no formato CSV
     */
    @Override 
    public String toString() {
        return this.id + "," + this.nome + "," + this.cpf + "," + this.telefone + "," + this.cadastro + "," + this.ultimaCompra;
    }
}
