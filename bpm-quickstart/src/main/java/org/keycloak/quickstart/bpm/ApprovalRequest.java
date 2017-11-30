package org.keycloak.quickstart.bpm;

public class ApprovalRequest implements java.io.Serializable {

    static final long serialVersionUID = 1L;

    private String url;

    public ApprovalRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return "Some description";
    }

    public void approve() {
        System.out.println("Request approved");
    }

    public void reject() {
        System.out.println("Request rejected");
    }
}