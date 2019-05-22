package learning.encontreitroquei.bean;

import java.io.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import learning.encontreitroquei.model.User;
import learning.encontreitroquei.service.UserService;
import learning.encontreitroquei.util.Util;
import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class UserRegisterView implements Serializable {

    public UserRegisterView() {
        userService = new UserService();
    }

    private transient UserService userService;

    private String email;
    private String password;
    private String cpf;
    private String nome;
    private String birthdate;
    private String telefone;
    private String address;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void submit() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        User usercheck = userService.findByEmail(email);

        if (usercheck != null) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário já existe no sistema", "");

            PrimeFaces.current().ajax().addCallbackParam("userEmailAlreadyExists", true);
            PrimeFaces.current().ajax().addCallbackParam("userNameAlreadyExists", true);
        } else {
            User user = new User();
            user.setAddress(address);
            user.setBirthdate(Util.parseDate(birthdate));
            user.setCpf(cpf);
            user.setEmail(email);
            user.setNome(nome);
            user.setPassword(password);
            user.setTelefone(telefone);

            userService = new UserService();
            userService.saveOrUpdate(user);

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário criado com sucesso", "");
        }

        facesContext.addMessage(null, message);
        PrimeFaces.current().ajax().addCallbackParam("userEmailAlreadyExists", false);
        PrimeFaces.current().ajax().addCallbackParam("userNameAlreadyExists", false);
    }
}
