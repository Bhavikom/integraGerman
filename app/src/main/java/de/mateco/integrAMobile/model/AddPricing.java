package de.mateco.integrAMobile.model;

/**
 * Created by mmehta on 31.05.2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddPricing {

    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("Payload")
    @Expose
    private Payload payload;
    @SerializedName("Status")
    @Expose
    private int status;

    /**
     *
     * @return
     *     The message
     */
    public Object getMessage() {
        return message;
    }

    /**
     *
     * @param message
     *     The Message
     */
    public void setMessage(Object message) {
        this.message = message;
    }

    /**
     *
     * @return
     *     The payload
     */
    public Payload getPayload() {
        return payload;
    }

    /**
     *
     * @param payload
     *     The Payload
     */
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    /**
     *
     * @return
     *     The status
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *     The Status
     */
    public void setStatus(int status) {
        this.status = status;
    }

}