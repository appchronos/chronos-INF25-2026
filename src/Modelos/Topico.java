
package Modelos;


public class Topico {
    private int id;
    private String nome;

    public Topico(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // O JComboBox usa o método toString() para saber o que vai desenhar na tela.
    // Sobrescrevendo ele, o combo vai mostrar o texto do nome do tópico!
    @Override
    public String toString() {
        return this.nome; 
    }  
}
