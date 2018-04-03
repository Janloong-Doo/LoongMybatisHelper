//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.execute;

public class StopContextException extends RuntimeException {
    private boolean shouldNotifyMessage;
    private String message;
    private MessageEnum messageEnum;

    public StopContextException() {
        this.messageEnum = MessageEnum.INFO;
    }

    public boolean isShouldNotifyMessage() {
        return this.shouldNotifyMessage;
    }

    public String getMessage() {
        return this.message;
    }

    public MessageEnum getMessageEnum() {
        return this.messageEnum;
    }

    public void setShouldNotifyMessage(boolean shouldNotifyMessage) {
        this.shouldNotifyMessage = shouldNotifyMessage;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageEnum(MessageEnum messageEnum) {
        this.messageEnum = messageEnum;
    }
}
