package genesis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BancoConexao {

    private String banco = "genesis";
    private String usuario = "root";
    private String senha = "123456";
    private String drive = "com.mysql.jdbc.Driver";

    private Connection connection;
    private ResultSet resultSet;
    private Statement statement;

    public boolean conectarBanco() {
        String caminhoBanco = "jdbc:mysql://localhost:3306/" + banco;

//        registrar drive mysql
        try {
            Class.forName(drive);
        } catch (ClassNotFoundException ex) {
            System.out.println("Não conseguiu registrar o drive do MySql.\n" + ex);
        }

//  criação da conexão        
        try {
            this.connection = DriverManager.getConnection(caminhoBanco, usuario, senha);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public void desconectaBanco() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("Não conseguiu desconectar o banco.\n" + ex);
        }
    }
    
    public void execultarSql(String sql){        
        try {
            this.statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.resultSet = this.statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(" Não foi possivel execultar a consulta.\n" + ex);
        }
    }
    
    

    public Connection getConnection() {
        return connection;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

}
