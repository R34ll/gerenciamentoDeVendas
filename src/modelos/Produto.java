package modelos;

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

    @Override 
    public String toString(){
        return this.id+","+this.nome+","+this.preco+","+this.quant+","+this.descricao;
    }
    
}
