package org.keycloak.quickstart.bpm;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.keycloak.representations.idm.ApprovalRequestBPMSRepresentation;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ApprovalRequest implements java.io.Serializable {

    static final long serialVersionUID = 1L;

    private ApprovalRequestBPMSRepresentation requestRep;
    private String accessToken;

    public ApprovalRequest(ApprovalRequestBPMSRepresentation requestRep, String accessToken) {
        this.requestRep = requestRep;
        this.accessToken = accessToken;
    }

    public String getKeycloakRootUrl() {
        return requestRep.getAuthServerUrl();
    }

    public String getRealm() {
        return requestRep.getRealm();
    }

    public String getApprovalId() {
        return requestRep.getId();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void approve() throws IOException {
        HttpUriRequest request = RequestBuilder.post().setUri(getApprovalUrl()).build();
        executeRequest(request);
    }

    public void reject() throws IOException {
        HttpUriRequest request = RequestBuilder.delete().setUri(getApprovalUrl()).build();
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
            throw new RuntimeException("Failed to send request to Keycloak; Status: " + response.getStatusLine() + "; Request: " + request);
        }
    }

    private URI getApprovalUrl() {
        return URI.create(String.format("%s/admin/realms/%s/approvals/%s", requestRep.getAuthServerUrl(), requestRep.getRealm(), requestRep.getId()));
    }
}