package org.keycloak.quickstart.bpm;

public class ApprovalRequest implements java.io.Serializable {

    static final long serialVersionUID = 1L;

    private String url;
    private String description;

    public ApprovalRequest(String url, String description) {
        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void approve() {

    }

    public void reject() {

    }
}