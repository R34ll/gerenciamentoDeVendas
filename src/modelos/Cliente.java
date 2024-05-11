package modelos;

public class Cliente {
    int id;
    String nome;
    String cpf;
    String telefone;
    String cadastro;
    String ultima_compra;
    

    public Cliente(int id, String nome, String cpf, String telefone, String cadastro, String ultima_compra ){
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.cadastro = cadastro;
        this.ultima_compra = ultima_compra;
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

    public String getUltima_compra() {
        return ultima_compra;
    }

    @Override 
    public String toString(){
        return this.id+","+this.nome+","+this.cpf+","+this.telefone+","+this.cadastro+","+this.ultima_compra;
    }

}
