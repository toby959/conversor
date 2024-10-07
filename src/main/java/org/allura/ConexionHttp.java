package org.allura;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionHttp {

    private final String direccion;
    private final String sigla;

    public ConexionHttp(String direccion, String sigla) {
        this.direccion = direccion;
        this.sigla = sigla;
    }



    public String devolverRespuestaJson(){
        try {
            HttpClient client=HttpClient.newHttpClient();
            HttpRequest request=HttpRequest.newBuilder().uri(URI.create(this.direccion+this.sigla)).build();
            HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ConexionHttp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ALGO FALLO";
    }
}
