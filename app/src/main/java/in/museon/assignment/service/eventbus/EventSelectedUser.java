package in.museon.assignment.service.eventbus;

import in.museon.assignment.data.domian.User;

/**
 * @author dev.cobb
 * @version 1.0
 * @since 22 may 2017
 */
public class EventSelectedUser {
    private User user;

    public User getUser() {
        return user;
    }

    public EventSelectedUser(User user) {
        this.user = user;
    }
}
