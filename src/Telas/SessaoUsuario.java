
package Telas;


public class SessaoUsuario {
    private static SessaoUsuario instance;
    private int idUsuario;
    private String nomeUsuario;

    // Construtor privado para garantir que ninguém crie outra instância
    private SessaoUsuario() {}

    // Método para pegar a instância única da sessão
    public static synchronized SessaoUsuario getInstance() {
        if (instance == null) {
            instance = new SessaoUsuario();
        }
        return instance;
    }

    // Getters e Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    
    // Método para limpar a sessão no logout
    public void limparSessao() {
        idUsuario = 0;
        nomeUsuario = null;
    }
    
}
