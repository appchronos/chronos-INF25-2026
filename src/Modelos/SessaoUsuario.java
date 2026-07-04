
package Modelos;


public class SessaoUsuario {
    private static SessaoUsuario instance;
    private int idUsuario;
    private String nomeUsuario;

    private SessaoUsuario() {}

    public static synchronized SessaoUsuario getInstance() {
        if (instance == null) {
            instance = new SessaoUsuario();
        }
        return instance;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    
    public void limparSessao() {
        idUsuario = 0;
        nomeUsuario = null;
    }
    
}
