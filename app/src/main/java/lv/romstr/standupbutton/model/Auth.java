package lv.romstr.standupbutton.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Roman on 03.04.16..
 */
@Table(name = "Auth")
public class Auth extends Model {

    @Column(name = "Server")
    private String server;

    @Column(name = "Login")
    private String login;

    @Column(name = "Password")
    private String password;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
