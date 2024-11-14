import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import org.allura.ConexionHttp;
import org.allura.Moneda;
import org.allura.MonedaOmdb;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String simboloInicial = "";
        String simboloFinal = "";
      


        Dotenv dotenv = Dotenv.load();
        String claveApi = dotenv.get("API_KEY");
        final String direccion = "https://v6.exchangerate-api.com/v6/" + claveApi + "/latest/";


        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 7) {
            limpiarPantalla();
            System.out.println("=== Menú de Conversión ===");
            String menu = """
                    ########################################
                    Seleccione la conversión de su interés
                    1. Dólar a pesos Argentinos
                    2. Pesos Argentinos a Dólar
                    3. Real a Dólar
                    4. Dólar a Real
                    5. Dólar a peso Colombiano
                    6. Peso Colombiano a Dólar
                    7. Salir
                    ########################################
                    """;
            System.out.println(menu);

            while (true) {
                System.out.print("Ingrese su opción: ");
                try {
                    opcion = scanner.nextInt();
                    if (opcion < 1 || opcion > 7) {
                        System.out.println("Opción inválida, seleccione un número entero entre 1 y 7 del menú.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Error: Debe ingresar un número entero, no letras ni caracteres especiales!!!");
                    scanner.next();
                }
            }

            if (opcion == 7) {
                System.out.println("|||||||||||||||||||||||||||||||||||||||");
                System.out.println("Gracias por usar Toby959, hasta pronto.");
                System.out.println("|||||||||||||||||||||||||||||||||||||||");
                break;
            }


            double cantConversion = 0;
            while (true) {
                System.out.print("Ingrese el importe a convertir: ");
                try {
                    cantConversion = scanner.nextDouble();
                    if (cantConversion <= 0) {
                        System.out.println("El importe debe ser un número positivo.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Error: Debe ingresar un número válido.");
                    scanner.next();
                }
            }
            switch (opcion) {
                case 1:
                    simboloInicial = "USD";
                    simboloFinal = "ARS";
                    break;
                case 2:
                    simboloInicial = "ARS";
                    simboloFinal = "USD";
                    break;
                case 3:
                    simboloInicial = "BRL";
                    simboloFinal = "USD";
                    break;
                case 4:
                    simboloInicial = "USD";
                    simboloFinal = "BRL";
                    break;
                case 5:
                    simboloInicial = "USD";
                    simboloFinal = "COP";
                    break;
                case 6:
                    simboloInicial = "COP";
                    simboloFinal = "USD";
                    break;
            }

            procesarConversion(simboloInicial, simboloFinal, direccion, cantConversion);
        }

        scanner.close();
    }


    public static void procesarConversion(String simboloInicial, String simboloFinal, String direccion, double cantConversion) {
        ConexionHttp conexion = new ConexionHttp(direccion, simboloInicial);
        MonedaOmdb monedaOmdb = crearMonedaOmdb(conexion.devolverRespuestaJson());
        Moneda moneda = new Moneda(simboloInicial, simboloFinal, monedaOmdb);

// Cambio de color por consola  -- verde --
        DecimalFormat df = new DecimalFormat("#,###.00");
        double resultado =  moneda.calcularConversion(cantConversion, moneda.getConversiones().get(simboloFinal));

        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";

        System.out.println(ANSI_GREEN + cantConversion + " " + simboloInicial + " equivale a " +
                df.format(resultado) + " " + simboloFinal + ANSI_RESET);


    }

    public static MonedaOmdb crearMonedaOmdb(String json) {

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        MonedaOmdb monedaOmdb = gson.fromJson(json, MonedaOmdb.class);

        return monedaOmdb;
    }

    public static void limpiarPantalla() {
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }

    }
}
