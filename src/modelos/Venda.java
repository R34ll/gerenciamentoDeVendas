package modelos;

import java.io.IOException;

import Uteis.Csv;

public class Venda {
    // Atributos privados da venda
    private int id;                // Identificador único da venda
    private String produtoId;      // Identificador do produto vendido
    private double preco;          // Preço da venda
    private String data;           // Data da venda
    private int clienteId;         // Identificador do cliente
    private int funcionarioId;     // Identificador do funcionário que realizou a venda

    /**
     * Construtor da classe Venda.
     * 
     * @param id Identificador único da venda
     * @param preco Preço da venda
     * @param data Data da venda
     * @param produtoId Identificador do produto vendido
     * @param clienteId Identificador do cliente
     * @param funcionarioId Identificador do funcionário que realizou a venda
     */
    public Venda(int id, double preco, String data, String produtoId, int clienteId, int funcionarioId){
        this.id = id;
        this.produtoId = produtoId;
        this.preco = preco;
        this.data = data;
        this.clienteId = clienteId;
        this.funcionarioId = funcionarioId;
    }

    // Métodos getters para acessar os atributos da venda
    public int getClienteId() {
        return clienteId;
    }

    public String getData() {
        return data;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public int getId() {
        return id;
    }

    public double getPreco() {
        return preco;
    }

    public String getProdutoId() {
        return produtoId;
    }

    // Método setter para modificar o identificador da venda
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o último ID utilizado nas vendas.
     * 
     * @return O último ID utilizado
     * @throws IOException se ocorrer um erro durante a leitura do arquivo CSV
     */
    public int getLastId() throws IOException {
        Csv csv = new Csv("src\\dados\\vendas.csv");
        return csv.getLastID(); // Retorna o último ID registrado no arquivo CSV de vendas
    }

    /**
     * Salva as informações da venda no arquivo CSV.
     * 
     * @throws IOException se ocorrer um erro durante a gravação no arquivo CSV
     */
    public void salvar() throws IOException {
        Csv csv = new Csv("src\\dados\\vendas.csv");
        csv.adicionar(this.toString()); // Adiciona a representação da venda ao arquivo CSV
    }

    /**
     * Retorna a representação da venda como uma string no formato CSV.
     * 
     * @return String representando a venda no formato CSV
     */
    @Override
    public String toString() {
        return this.id + "," + this.preco + "," + this.data + "," + this.produtoId + "," + this.clienteId + "," + this.funcionarioId;
    }
}
