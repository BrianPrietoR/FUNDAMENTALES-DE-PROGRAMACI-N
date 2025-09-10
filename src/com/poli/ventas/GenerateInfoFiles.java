package com.poli.ventas;

import java.io.*;
import java.util.*;

public class GenerateInfoFiles {

    private static final String INPUT_DIR = "input";
    private static final String SALES_DIR = INPUT_DIR + "/sales";
    private static final String PRODUCTS_FILE = INPUT_DIR + "/products.txt";
    private static final String SALESMEN_FILE = INPUT_DIR + "/salesmen.txt";
    private static final Random RNG = new Random();

    public static void main(String[] args) {
        try {
            ensureDirectories();

            int productsCount = 20;  // número de productos a generar
            int salesmanCount = 10;  // número de vendedores a generar

            createProductsFile(productsCount);
            createSalesManInfoFile(salesmanCount);

            // Leemos vendedores creados para asignarles ventas
            List<String> salesmenKeys = readGeneratedSalesmenKeys();

            for (String key : salesmenKeys) {
                // key tiene formato: TipoDocumento;NumeroDocumento;Nombres;Apellidos
                String[] parts = key.split(";");
                String tipo = parts[0];
                String numero = parts[1];
                String nombreCompleto = parts[2] + " " + parts[3];

                int filesForThisSalesman = 1 + RNG.nextInt(3); // entre 1 y 3 archivos de ventas
                for (int f = 0; f < filesForThisSalesman; f++) {
                    int randomSalesCount = 5 + RNG.nextInt(21); // entre 5 y 25 ventas
                    createSalesMenFile(tipo, randomSalesCount, nombreCompleto, Long.parseLong(numero));
                }
            }

            System.out.println("GENERATEINFOFILES: Archivos de prueba generados correctamente en la carpeta 'input'.");
        } catch (Exception e) {
            System.err.println("GENERATEINFOFILES: Error durante la generación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------------------
    // MÉTODOS AUXILIARES
    // --------------------------------------------------------------------

    private static void ensureDirectories() throws IOException {
        if (!new File(INPUT_DIR).exists()) new File(INPUT_DIR).mkdirs();
        if (!new File(SALES_DIR).exists()) new File(SALES_DIR).mkdirs();
    }

    /**
     * Crea archivo de ventas para un vendedor
     */
    private static void createSalesMenFile(String tipoDocumento, int randomSalesCount, String name, long id) throws IOException {
        String fileName = SALES_DIR + "/ventas_" + id + "_" + System.currentTimeMillis() + ".txt";
        try (BufferedWriter w = new BufferedWriter(new FileWriter(fileName))) {
            // Primera línea: tipoDocumento;numeroDocumento
            w.write(tipoDocumento + ";" + id);
            w.newLine();

            // Generar ventas aleatorias
            for (int i = 0; i < randomSalesCount; i++) {
                int productoId = RNG.nextInt(20) + 1; // entre 1 y 20 productos
                int cantidad = RNG.nextInt(5) + 1;   // cantidad entre 1 y 5
                w.write(productoId + ";" + cantidad + ";");
                w.newLine();
            }
        }
    }

    /**
     * Crea archivo de productos pseudoaleatorios
     */
    private static void createProductsFile(int productsCount) throws IOException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(PRODUCTS_FILE))) {
            for (int i = 1; i <= productsCount; i++) {
                String nombreProducto = "Producto" + i;
                double precio = 1000 + RNG.nextInt(9000); // precios entre 1000 y 10000
                w.write(i + ";" + nombreProducto + ";" + precio);
                w.newLine();
            }
        }
    }

    /**
     * Crea archivo de información de vendedores pseudoaleatoria
     */
    private static void createSalesManInfoFile(int salesmanCount) throws IOException {
        String[] nombres = {"Juan", "Maria", "Pedro", "Ana", "Luis", "Carla", "Andrés", "Sofia", "Miguel", "Laura"};
        String[] apellidos = {"Gomez", "Perez", "Rodriguez", "Martinez", "Fernandez", "Lopez", "Diaz", "Sanchez", "Ramirez", "Torres"};
        String[] tiposDoc = {"CC", "TI"};

        try (BufferedWriter w = new BufferedWriter(new FileWriter(SALESMEN_FILE))) {
            for (int i = 0; i < salesmanCount; i++) {
                String tipo = tiposDoc[RNG.nextInt(tiposDoc.length)];
                long numero = 10000000L + RNG.nextInt(90000000); // documento 8 dígitos
                String nombre = nombres[RNG.nextInt(nombres.length)];
                String apellido = apellidos[RNG.nextInt(apellidos.length)];

                w.write(tipo + ";" + numero + ";" + nombre + ";" + apellido);
                w.newLine();
            }
        }
    }

    /**
     * Lee vendedores creados desde el archivo de info
     */
    private static List<String> readGeneratedSalesmenKeys() throws IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(SALESMEN_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        }
        return result;
    }
}
