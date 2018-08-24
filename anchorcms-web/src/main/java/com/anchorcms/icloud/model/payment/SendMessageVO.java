package com.anchorcms.icloud.model.payment;

/**
 * Created by ldong on 2017/2/22.
 * 调用sendMessageService的对象
 */
public class SendMessageVO {
    private String plainText;
    private String message;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTxCode() {
        return txCode;
    }

    public void setTxCode(String txCode) {
        this.txCode = txCode;
    }

    public String getTxName() {
        return txName;
    }

    public void setTxName(String txName) {
        this.txName = txName;
    }

    private String signature;
    private String txCode;
    private String txName;
    private String flag;



    public SendMessageVO() {
    }

    public SendMessageVO(String plainText, String message, String signature, String txCode, String txName) {
        this.plainText = plainText;
        this.message = message;
        this.signature = signature;
        this.txCode = txCode;
        this.txName = txName;
    }


    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
