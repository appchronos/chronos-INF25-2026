
package AcessoDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class ModuloDbConecta {
    
    // 1o. - Passo: definir as variáveis da String de Conexão
    public static Connection connector() {
        // Definindi as variáveis
        java.sql.Connection conexao = null; // Recebe o resultado da conexão. 
        // Definir o Driver para o SGBD a ser conectado
        String driver = "com.mysql.cj.jdbc.Driver";
        // Definir as demais variáveis da String de conexão
        String url = "jdbc:mysql://127.0.0.1:3306/db_chronos";
        String user = "root";
        String senha = "1234";
        
        // 2 - Fazer a conexao ao banco
        try {
            // Acionando/chamando o driver para fazer a conexão
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, senha);
            // Se conectou OK!!
            // retornar o resultado da conexão!!!
            return conexao;
        }catch (Exception varERRO) {
            // caso haja erro, retorna com "null"
            System.out.println(" ERRO gerado na conexão ao banco: " + varERRO.toString());
            return null;
        }
    }
    
}
