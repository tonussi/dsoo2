package learning.encontreitroquei.bean;

import java.io.*;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import learning.encontreitroquei.model.User;
import learning.encontreitroquei.service.UserService;
import org.primefaces.PrimeFaces;

@Named
@SessionScoped
public class UserView implements Serializable {

    public UserView() {
        userService = new UserService();
    }

    private transient UserService userService;

    private Integer id;

    private String name;
    
    private Boolean loggedIn;

    private String email;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void login() throws IOException {
        FacesMessage message;
        User user = userService.findByEmail(email);

        if (user != null && email != null && email.equals(user.getEmail()) && password != null && password.equals(user.getPassword())) {
            setId(user.getId());
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem vindo", email);
            setName(user.getNome());
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro ao logar", "Credenciais erradas.");
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
        PrimeFaces.current().ajax().update(":everybodyProductsForm:everybodyProductsDataGrid");
//        PrimeFaces.current().ajax().update(":userActiveTradesForm:userActiveTradesDisplay");

        if (loggedIn) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath());
        }

    }

    public void logout() throws IOException {
        setLoggedIn(false);
        setName("");
        setPassword("");
        setEmail("");

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath());
    }
}
