package modelos;

import java.io.IOException;

import Uteis.Csv;

public class Produto {
    // Atributos privados do produto
    private int id;               // Identificador único do produto
    private String nome;          // Nome do produto
    private double preco;         // Preço do produto
    private int quant;            // Quantidade do produto em estoque
    private String descricao;     // Descrição do produto

    /**
     * Construtor da classe Produto.
     * 
     * @param id Identificador único do produto
     * @param nome Nome do produto
     * @param preco Preço do produto
     * @param quant Quantidade do produto em estoque
     * @param descricao Descrição do produto
     */
    public Produto(int id, String nome, double preco, int quant, String descricao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quant = quant;
        this.descricao = descricao;
    }

    // Métodos getters para acessar os atributos do produto
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuant() {
        return quant;
    }

    // Método setter para modificar a quantidade do produto
    public void setQuant(int quant) {
        this.quant = quant;
    }

    /**
     * Atualiza as informações do produto no arquivo CSV.
     * 
     * @throws Exception se ocorrer um erro durante a atualização
     */
    public void update() throws Exception {
        Csv csv = new Csv("src\\dados\\produtos.csv");
        csv.removePorId(this.id);  // Remove a entrada do produto com o mesmo ID
        if (!this.descricao.trim().isEmpty() && !nome.trim().isEmpty()) {
            this.salvar();  // Salva as novas informações do produto se os campos de descrição e nome não estiverem vazios
        }
    }

    /**
     * Salva as informações do produto no arquivo CSV.
     * 
     * @throws IOException se ocorrer um erro durante a gravação no arquivo CSV
     * @throws NumberFormatException se o formato dos dados estiver incorreto
     */
    public void salvar() throws IOException, NumberFormatException {
        Csv csv = new Csv("src\\dados\\produtos.csv");
        csv.adicionar(this.toString());  // Adiciona a representação do produto ao arquivo CSV
    }

    /**
     * Retorna a representação do produto como uma string no formato CSV.
     * 
     * @return String representando o produto no formato CSV
     */
    @Override
    public String toString() {
        return this.id + "," + this.nome + "," + this.preco + "," + this.quant + "," + this.descricao;
    }
}
