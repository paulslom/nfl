package com.pas.action;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessages;

/**
 * Title: ActionComposite
 * Project: Claims Replacement System
 * 
 * Description: 
 * 
 * Copyright: Copyright (c) 2003
 * Company: Lincoln Life
 * 
 * @author psinghal
 * @version
 */
public class ActionComposite {

    ActionForward actionForward;
    ActionMessages messages;

    public ActionForward getActionForward() {
        return actionForward;
    }

    public ActionMessages getMessages() {
        return messages;
    }

    public void setActionForward(ActionForward forward) {
        actionForward = forward;
    }

    public void setMessages(ActionMessages messages) {
        this.messages = messages;
    }

}