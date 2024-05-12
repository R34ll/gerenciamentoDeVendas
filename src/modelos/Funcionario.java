package modelos;

public class Funcionario {
    int id;
    String nome;
    String cadastro;
    String senha;

    public Funcionario(int id, String nome, String cadastro, String senha){
        this.id = id;
        this.nome = nome;
        this.cadastro = cadastro;
        this.senha = senha;
    }

    public String getCadastro() {
        return cadastro;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }
    
}
