package modelos;

import java.io.IOException;

import dados.Csv;

public class Produto {
    int id;
    String nome;
    double preco;
    int quant;
    String descricao;

    public Produto(int id, String nome, double preco, int quant, String descricao){
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

    public void update() throws Exception{
        Csv csv = new Csv();

        try {
            csv.removePorId("src\\dados\\produtos.csv", this.id);
        } catch (IOException e) {
            // app.mostrarErro("Erro ao editar produto.", "");
            e.printStackTrace();
            return;
        }

        // // Atualiza o arquivo CSV com os novos dados do produto
        if (!this.descricao.trim().isEmpty() && !nome.trim().isEmpty() ) {
            try {
                Produto produtoAtualizado = this;//new Produto(this.id, this.nome,this.preco, this.quant,this.descricao);
                csv.adicionar(produtoAtualizado.toString(), "src\\dados\\produtos.csv");
            } catch (IOException e) {
                // app.mostrarErro("Erro ao editar produto.", "");
                e.printStackTrace();
            } catch (NumberFormatException e) {
                // app.mostrarErro("Erro Produto", "Por favor, insira valores numéricos válidos para preço e quantidade.");
                e.printStackTrace();
            }
        }
    }

    @Override 
    public String toString(){
        return this.id+","+this.nome+","+this.preco+","+this.quant+","+this.descricao;
    }
    
}
