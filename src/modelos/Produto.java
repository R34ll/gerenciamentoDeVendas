package modelos;

import java.io.IOException;

import dados.Csv;

public class Produto {
    int id;
    String nome;
    double preco;
    int quant;
    String descricao;

    public Produto(int id, String nome, double preco, int quant, String descricao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quant = quant;
        this.descricao = descricao;
    }

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

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public void update() throws Exception {
        Csv csv = new Csv("src\\dados\\produtos.csv");
        csv.removePorId(this.id);
        if (!this.descricao.trim().isEmpty() && !nome.trim().isEmpty()) {
            this.salvar();
        }
    }

    public void salvar() throws IOException, NumberFormatException {
        Csv csv = new Csv("src\\dados\\produtos.csv");
        csv.adicionar(this.toString());
    }

    @Override
    public String toString() {
        return this.id + "," + this.nome + "," + this.preco + "," + this.quant + "," + this.descricao;
    }

}
