package modelos;

public class Funcionario {
    private int id;
    private String usuario;
    private String nome;
    private String cadastro;
    private String senha;

    public Funcionario(int id,String usuario, String nome, String cadastro, String senha){
        this.id = id;
        this.usuario = usuario;
        this.nome = nome;
        this.cadastro = cadastro;
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
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
    
    @Override
    public String toString(){
        return this.id+","+this.usuario+","+this.nome+","+this.cadastro+","+this.senha;
    } 

}
