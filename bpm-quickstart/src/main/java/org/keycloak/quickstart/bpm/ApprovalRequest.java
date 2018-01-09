package org.keycloak.quickstart.bpm;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ApprovalRequest implements java.io.Serializable {

    static final long serialVersionUID = 1L;

    private String keycloakRootUrl;
    private String realm;
    private String approvalId;
    private String accessToken;

    public ApprovalRequest(String keycloakRootUrl, String realm, String approvalId, String accessToken) {
        this.keycloakRootUrl = keycloakRootUrl;
        this.realm = realm;
        this.approvalId = approvalId;
        this.accessToken = accessToken;
    }

    public String getKeycloakRootUrl() {
        return keycloakRootUrl;
    }

    public String getRealm() {
        return realm;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void approve() throws IOException {
        HttpUriRequest request = RequestBuilder.post(getApprovalUrl()).build();
        executeRequest(request);
    }

    public void reject() throws IOException {
        HttpUriRequest request = RequestBuilder.delete(getApprovalUrl()).build();
        executeRequest(request);
    }

    private void executeRequest(HttpUriRequest request) throws IOException {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
        headers.add(new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));

        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultHeaders(headers)
                .build();

        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() >= 300) {
            throw new RuntimeException("Failed to send request to Keycloak; Status: " + response.getStatusLine());
        }
    }

    private URI getApprovalUrl() {
        return URI.create(String.format("%s/admin/%s/approvals/%s", keycloakRootUrl, realm, approvalId));
    }
}