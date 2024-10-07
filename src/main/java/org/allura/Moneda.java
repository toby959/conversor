package org.allura;


import java.util.Map;

public class Moneda {

    private final String simboloInicial;
    private final String simboloFinal;
    private final Map<String,Double> conversiones;

    public Moneda(String siglaBase,String siglaDestino,MonedaOmdb monedaOmdb) {
        this.simboloInicial = siglaBase;
        this.simboloFinal = siglaDestino;
        this.conversiones = monedaOmdb.conversion_rates();
    }


    public Map<String, Double> getConversiones() {
        return conversiones;
    }




    public double calcularConversion(double cantidadMoneda,double conversionRate){
        return cantidadMoneda*conversionRate;
    }

    @Override
    public String toString() {
        return "Moneda{" + "siglaBase=" + simboloInicial + ", siglaDestino=" + simboloFinal + ", conversiones=" + conversiones + '}';
    }

}
