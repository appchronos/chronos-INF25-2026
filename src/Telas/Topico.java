
package Telas;


public class Topico {
    private int idTopico;
    private String nmTopico;

    public Topico(int idTopico, String nmTopico) {
        this.idTopico = idTopico;
        this.nmTopico = nmTopico;
    }

    public int getIdTopico() { return idTopico; }
    public void setIdTopico(int idTopico) { this.idTopico = idTopico; }
    public String getNmTopico() { return nmTopico; }
    public void setNmTopico(String nmTopico) { this.nmTopico = nmTopico; }

    // Este método faz o nome do tópico aparecer no ComboBox em vez de códigos estranhos
    @Override
    public String toString() {
        return this.nmTopico;
    }
    
}
