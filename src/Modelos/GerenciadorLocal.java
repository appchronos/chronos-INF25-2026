
package Modelos;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GerenciadorLocal {
    private static final Set<Integer> tarefasConcluidasLocalmente = Collections.synchronizedSet(new HashSet<>());
    private static String ultimoUsuarioLogado = "";
    
    public static void marcarComoConcluida(int idTarefa) {
        tarefasConcluidasLocalmente.add(idTarefa);
    }

    public static void desmarcarComoConcluida(int idTarefa) {
        tarefasConcluidasLocalmente.remove(idTarefa);
    }

    public static boolean tarefaConcluidaLocalmente(int idTarefa) {
        return tarefasConcluidasLocalmente.contains(idTarefa);
    }

    public static void limparCache() {
        tarefasConcluidasLocalmente.clear();
        ultimoUsuarioLogado = "";
        System.out.println("Cache local limpo com sucesso!");
    }

    public static void verificarEMudarUsuario(String usuarioAtual) {
        if (usuarioAtual == null || usuarioAtual.trim().isEmpty()) {
            limparCache();
            return;
        }
        if (!usuarioAtual.equals(ultimoUsuarioLogado)) {
            tarefasConcluidasLocalmente.clear();
            ultimoUsuarioLogado = usuarioAtual;
            System.out.println("Usuário mudou! Cache local de tarefas foi resetado.");
        } else {
            System.out.println("Mesmo usuário local. Cache mantido.");
        }
    }
}
