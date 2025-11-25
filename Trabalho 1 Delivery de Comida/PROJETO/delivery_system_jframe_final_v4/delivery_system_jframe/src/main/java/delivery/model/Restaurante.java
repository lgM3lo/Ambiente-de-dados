package delivery.model;

public class Restaurante {
    private int idRestaurante;
    private String nome;
    private String tipoCozinha;
    private String telefone;

    // Construtor completo
    public Restaurante(int idRestaurante, String nome, String tipoCozinha, String telefone) {
        this.idRestaurante = idRestaurante;
        this.nome = nome;
        this.tipoCozinha = tipoCozinha;
        this.telefone = telefone;
    }

    // Construtor para inserção (sem ID)
    public Restaurante(String nome, String tipoCozinha, String telefone) {
        this.nome = nome;
        this.tipoCozinha = tipoCozinha;
        this.telefone = telefone;
    }

    // Getters e Setters
    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "idRestaurante=" + idRestaurante +
                ", nome='" + nome + '\'' +
                ", tipoCozinha='" + tipoCozinha + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
