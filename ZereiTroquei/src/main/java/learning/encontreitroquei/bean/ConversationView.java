package learning.encontreitroquei.bean;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import learning.encontreitroquei.model.Message;
import learning.encontreitroquei.model.User;
import learning.encontreitroquei.service.MessageService;
import learning.encontreitroquei.service.UserService;

@Named
@SessionScoped
public class ConversationView implements Serializable {

    private transient UserService userService;    
    private transient MessageService messageService;    

    public ConversationView() {
        userService = new UserService();
        messageService = new MessageService();
    }

    private String userWords;
    private User toUser;
    private User fromUser;
    private List<Message> messages;

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public String getUserWords() {
        return userWords;
    }

    public void setUserWords(String userWords) {
        this.userWords = userWords;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void submit() {
        Message msgSent = new Message();
        msgSent.setFromUser(fromUser);
        msgSent.setToUser(toUser);
        msgSent.setMensagem(userWords);

        List<Message> msgs = fromUser.getMessages();
        msgs.add(msgSent);

        fromUser.setMessages(msgs);

        messageService.saveOrUpdate(msgSent);
        userService.saveOrUpdate(fromUser);
        userService.saveOrUpdate(toUser);

        setUserWords("");
        messages.add(msgSent);
    }
}
