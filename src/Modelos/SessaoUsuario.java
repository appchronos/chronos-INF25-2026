package Modelos;

public class SessaoUsuario {

    private static SessaoUsuario instance;
    private int idUsuario;
    private String nomeUsuario;
    private String emailUsuario; // ⬅️ ADICIONADO: Atributo para o e-mail

    private SessaoUsuario() {
    }

    public static synchronized SessaoUsuario getInstance() {
        if (instance == null) {
            instance = new SessaoUsuario();
        }
        return instance;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    // ⬅️ ADICIONADO: Getter e Setter para o e-mail
    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public void limparSessao() {
        idUsuario = 0;
        nomeUsuario = null;
        emailUsuario = null; // ⬅️ ADICIONADO: Limpar o e-mail no logout
    }
}
