package com.scalefocus.blogapp.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
@NoArgsConstructor
public class SessionHandler {

    private Long id;

    private String username;

}
