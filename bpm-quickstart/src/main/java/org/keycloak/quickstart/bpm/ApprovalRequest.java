package org.keycloak.quickstart.bpm;

public class ApprovalRequest implements java.io.Serializable {

    static final long serialVersionUID = 1L;

    private String url;
    private String accessToken;

    public ApprovalRequest(String url, String accessToken) {
        this.url = url;
        this.accessToken = accessToken;
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
        System.out.println("Request approved; token: " + accessToken);
    }

    public void reject() {
        System.out.println("Request rejected; token: " + accessToken);
    }
}