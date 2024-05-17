
package model;

import java.time.LocalDate;

public class Login {
    private int id;
    private String nomeCompleto;
    private String email;
    private LocalDate dataNascimento;
    private String instrumento;
    private String descricao;

    // Construtores
    public Login() {
        id = 0;
        nomeCompleto = "";
        email = "";
        dataNascimento = LocalDate.now();
        instrumento = "";
        descricao = "";
    }

    public Login(int id, String nomeCompleto, String email, LocalDate dataNascimento, String instrumento,
            String descricao) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.instrumento = instrumento;
        this.descricao = descricao;
    }

    // Métodos de acesso
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
