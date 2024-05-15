package modelos;

import java.io.IOException;

import dados.Csv;

public class Cliente {
    int id;
    String nome;
    String cpf;
    String telefone;
    String cadastro;
    String ultimaCompra;
    

    public Cliente(int id, String nome, String cpf, String telefone, String cadastro, String ultima_compra ){
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.cadastro = cadastro;
        this.ultimaCompra = ultima_compra;
    }

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

    public void salvar(){
        Csv csv = new Csv("src\\dados\\clientes.csv");


        try {
            csv.adicionar(this.toString());

        } catch (IOException e) {
            // app.mostrarErro("Erro Produto", "Um erro ocorreu ao adicionar novo Produto!");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // app.mostrarErro("Erro Produto", "Por favor, insira valores numéricos válidos para preço e quantidade.");
            e.printStackTrace();
        }
    }


    public void update() throws Exception{
        Csv csv = new Csv("src\\dados\\clientes.csv");

        try {
            csv.removePorId(this.id);
            Cliente cliente = this;
            cliente.salvar();
        } catch (IOException e) {
            // app.mostrarErro("Erro ao editar produto.", "");
            e.printStackTrace();
            return;
        }

        // // Atualiza o arquivo CSV com os novos dados do produto


    }

    @Override 
    public String toString(){
        return this.id+","+this.nome+","+this.cpf+","+this.telefone+","+this.cadastro+","+this.ultimaCompra;
    }

}
