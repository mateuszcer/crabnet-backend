package com.mateuszcer.socialmediaapp.registration;

import com.mateuszcer.socialmediaapp.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private String appUrl;

    private Locale locale;

    public OnRegistrationCompleteEvent(User user, String appUrl, Locale locale) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
        this.locale = locale;
    }
}
