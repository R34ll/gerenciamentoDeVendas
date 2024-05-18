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

     public void salvar() throws IOException, NumberFormatException {
        Csv csv = new Csv("src\\dados\\clientes.csv");
        csv.adicionar(this.toString());
    }

    public void update() throws Exception {
        Csv csv = new Csv("src\\dados\\clientes.csv");
        csv.removePorId(this.id);
        this.salvar();
    }

    @Override 
    public String toString(){
        return this.id+","+this.nome+","+this.cpf+","+this.telefone+","+this.cadastro+","+this.ultimaCompra;
    }

}
