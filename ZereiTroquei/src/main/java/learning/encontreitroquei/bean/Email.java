package learning.encontreitroquei.bean;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class Email {

    @Asynchronous
    public void sendEmail() {
    }

}
