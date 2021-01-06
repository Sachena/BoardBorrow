package com.bb.boardborrow.modules.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
@Getter
@RequiredArgsConstructor
public class RequestUpdateEvent  {

    private final Request request;
    private final String message;

}
