package modelos;

import java.io.IOException;

import dados.Csv;

public class Venda {
    int id;
    String produtoId;
    double preco;
    String data;
    int clienteId;
    int funcionarioId;

    public Venda(int id, double preco, String data, String produtoId, int clienteId, int funcionarioId){
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

    public String getProdutoId() {
        return produtoId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLastId() throws IOException{
        Csv csv = new Csv();
        return csv.getLastID("src\\dados\\vendas.csv");

    }

    public void salvar() throws Exception{
        Csv csv = new Csv();


        try {
            // int ultimoID = csv.getLastID("src\\dados\\produtos.csv");

            csv.adicionar(this.toString(), "src\\dados\\vendas.csv");

        } catch (IOException e) {
            // app.mostrarErro("Erro Produto", "Um erro ocorreu ao adicionar novo Produto!");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // app.mostrarErro("Erro Produto", "Por favor, insira valores numéricos válidos para preço e quantidade.");
            e.printStackTrace();
        }
    }
    

    @Override
    public String toString(){
        return this.id+","+this.preco+","+this.data+","+this.produtoId+","+this.clienteId+","+this.funcionarioId;
    }
}
