
package AcessoDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class ModuloDbConecta {    
    public static Connection connector() {   
        java.sql.Connection conexao = null;         
        String driver = "com.mysql.cj.jdbc.Driver";      
        String url = "jdbc:mysql://127.0.0.1:3306/db_chronos";
        String user = "root";
        String senha = "1234";            
        try {          
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, senha);           
            return conexao;
        }catch (Exception varERRO) {            
            System.out.println(" ERRO gerado na conexão ao banco: " + varERRO.toString());
            return null;
        }
    }    
}
