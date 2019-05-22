package learning.encontreitroquei.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import learning.encontreitroquei.model.Message;
import learning.encontreitroquei.model.User;
import learning.encontreitroquei.service.UserService;

@Named
@SessionScoped
public class ChatMemberView implements Serializable {

    public ChatMemberView() {
        userService = new UserService();
        chatMembers = userService.findAll(User.class);
    }

    @Inject
    private ConversationView conversationView;

    @Inject
    private UserView userView;

    private transient UserService userService;

    private List<User> chatMembers;

    private User selectedContact;

    public void updateChatMemberList() {
        chatMembers = userService.findAll(User.class);
    }

    public User getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(User selectedContact) {
        this.selectedContact = selectedContact;
    }

    public List<User> getChatMembers() {
        return chatMembers;
    }

    public void setChatMembers(List<User> chatMembers) {
        this.chatMembers = chatMembers;
    }

    public void activeConversation() {
        if (selectedContact != null) {
            List<Message> conversation = new ArrayList<>();
            User currentUserUsingChat = userService.findByEmail(userView.getEmail());
            User otherUserInChat = userService.findByEmail(selectedContact.getEmail());

            List<Message> userMessages = currentUserUsingChat.getMessages();
            List<Message> otherMessages = otherUserInChat.getMessages();

            for (Message m : userMessages) {
                if (m.getToUser().getId().equals(otherUserInChat.getId())) {
                    conversation.add(m);
                }
            }

            for (Message m : otherMessages) {
                if (m.getToUser().getId().equals(currentUserUsingChat.getId())) {
                    conversation.add(m);
                }
            }

            Collections.sort(conversation, new Comparator<Message>() {
                @Override
                public int compare(Message o1, Message o2) {
                    return o1.getCreateDateTime().compareTo(o2.getCreateDateTime());
                }
            });

            conversationView.setMessages(conversation);
            conversationView.setFromUser(currentUserUsingChat);
            conversationView.setToUser(otherUserInChat);
            conversationView.setUserWords("");
        }
    }
}
