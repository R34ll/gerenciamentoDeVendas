package modelos;

public class Venda {
    int id;
    int produtoId;
    double preco;
    String data;
    int clienteId;
    int funcionarioId;

    public Venda(int id, double preco, String data, int produtoId, int clienteId, int funcionarioId){
        this.id = id;
        this.produtoId = produtoId;
        this.preco = preco;
        this.data = data;
        this.clienteId= clienteId;
        this.funcionarioId = funcionarioId;
    }

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

    public int getProdutoId() {
        return produtoId;
    }


    @Override
    public String toString(){
        return this.id+","+this.preco+","+this.data+","+this.produtoId+","+this.clienteId+","+this.funcionarioId;
    }
}
