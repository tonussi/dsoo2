package learning.encontreitroquei.bean;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class EmailManager implements Serializable {

    @EJB
    private Email email;

    public void processForm() {
        email.sendEmail();
    }

}
