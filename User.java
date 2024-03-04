



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author name
 */

public class User {

    private int id;
    private String name;
    private String email;
    private String password;

    private static final String DB_HOST = "";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    private static final String TABLENAME = "USUARIO";

    public User() {
        this.id = -1;
        this.name = null;
        this.email = null;
        this.password = null;
    }

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean existsUser(){
        boolean existsUser = true;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT COUNT(*) as COUNT FROM " + TABLENAME + " WHERE username='" + this.email + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                existsUser = (rs.getInt("COUNT") > 0);
            }
            
            return existsUser;            
        } catch (SQLException err) {
            System.out.println(err.getMessage());       
        }
        return existsUser;
    }   


    //TOCHECK TODO: son del github
    public boolean createUser(){
        boolean result = false;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "INSERT INTO " + TABLENAME
                    + "(NAME, SURNAME, EMAIL, USERNAME, PASSWORD)"
                   + " VALUES ('" + this.nombre + "', '" + this.apellidos + "', '" + this.email + "', '" + this.userName + "', '" + this.password + "')";
            System.out.println("Sentencia SQL: " + sql);
            stmt.executeUpdate(sql);
            
            result = true;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return result;
    }
    
    public Usuario getUser(){
        Usuario usuario = null;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT * FROM " + TABLENAME + " WHERE username='" + this.userName + "' AND password='" + this.password + "'";
            System.out.println("Sentencia SQL: " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int ID = rs.getInt("ID");
                String name = rs.getString("NAME");
                String surname = rs.getString("SURNAME");
                String email = rs.getString("EMAIL");
                String userName = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                                
                usuario = new Usuario(ID, name, surname, email, userName, password);
            }            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return usuario;
    }
}
