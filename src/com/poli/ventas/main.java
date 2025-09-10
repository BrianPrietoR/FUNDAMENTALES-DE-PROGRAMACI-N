package com.poli.ventas;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.*;

public class main {

    private static final String INPUT_DIR = "input";
    private static final String SALES_DIR = INPUT_DIR + "/sales";
    private static final String PRODUCTS_FILE = INPUT_DIR + "/products.txt";
    private static final String SALESMEN_INFO_FILE = INPUT_DIR + "/salesmen.txt";
    private static final String OUTPUT_DIR = "output";

    public static void main(String[] args) {
        try {
            ensureDirectories();

            // Leer archivos de productos y vendedores
            Map<String, Product> products = readProducts(PRODUCTS_FILE);
            Map<String, Salesman> salesmen = readSalesmenInfo(SALESMEN_INFO_FILE);

            // Acumuladores
            Map<String, BigDecimal> moneyPerSalesman = new HashMap<>(); // key = tipo+numero
            Map<String, Integer> qtyPerProduct = new HashMap<>();       // key = productId

            // Procesar archivos de ventas
            File folder = new File(SALES_DIR);
            File[] salesFiles = folder.listFiles((dir, name) -> name.endsWith(".txt"));
            if (salesFiles != null) {
                for (File f : salesFiles) {
                    processSalesFile(f, products, moneyPerSalesman, qtyPerProduct);
                }
            }

            // Generar reporte de vendedores
            generateSalesmenReport(salesmen, moneyPerSalesman);

            // Generar reporte de productos
            generateProductsReport(products, qtyPerProduct);

            System.out.println("MAIN: Reportes generados correctamente en la carpeta 'output'.");
        } catch (Exception e) {
            System.err.println("MAIN: Error durante la generación de reportes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------------------
    // Métodos auxiliares
    // -----------------------------------------------------------------

    private static void ensureDirectories() throws IOException {
        if (!Files.exists(Paths.get(OUTPUT_DIR))) Files.createDirectories(Paths.get(OUTPUT_DIR));
    }

    private static Map<String, Product> readProducts(String fileName) throws IOException {
        Map<String, Product> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String id = parts[0];
                String name = parts[1];
                BigDecimal price = new BigDecimal(parts[2]);
                map.put(id, new Product(id, name, price));
            }
        }
        return map;
    }

    private static Map<String, Salesman> readSalesmenInfo(String fileName) throws IOException {
        Map<String, Salesman> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String tipo = parts[0];
                String numero = parts[1];
                String nombres = parts[2];
                String apellidos = parts[3];
                String key = tipo + "_" + numero;
                map.put(key, new Salesman(tipo, numero, nombres, apellidos));
            }
        }
        return map;
    }

    private static void processSalesFile(File file,
                                         Map<String, Product> products,
                                         Map<String, BigDecimal> moneyPerSalesman,
                                         Map<String, Integer> qtyPerProduct) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String vendedorLine = br.readLine(); // primera línea: tipo;numero
            if (vendedorLine == null) return;

            String[] vendedorParts = vendedorLine.split(";");
            String key = vendedorParts[0] + "_" + vendedorParts[1];

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 2) continue;

                String productId = parts[0];
                int qty = Integer.parseInt(parts[1]);

                Product p = products.get(productId);
                if (p != null) {
                    BigDecimal totalVenta = p.getPrice().multiply(BigDecimal.valueOf(qty));

                    // acumular por vendedor
                    moneyPerSalesman.put(key,
                            moneyPerSalesman.getOrDefault(key, BigDecimal.ZERO).add(totalVenta));

                    // acumular por producto
                    qtyPerProduct.put(productId, qtyPerProduct.getOrDefault(productId, 0) + qty);
                }
            }
        }
    }

    private static void generateSalesmenReport(Map<String, Salesman> salesmen,
                                               Map<String, BigDecimal> moneyPerSalesman) throws IOException {

        List<Map.Entry<String, BigDecimal>> sorted = new ArrayList<>(moneyPerSalesman.entrySet());
        sorted.sort((a, b) -> b.getValue().compareTo(a.getValue())); // descendente

        try (BufferedWriter w = new BufferedWriter(new FileWriter(OUTPUT_DIR + "/salesmen_report.csv"))) {
            for (Map.Entry<String, BigDecimal> entry : sorted) {
                Salesman s = salesmen.get(entry.getKey());
                if (s != null) {
                    w.write(s.getNombres() + " " + s.getApellidos() + ";" + entry.getValue());
                    w.newLine();
                }
            }
        }
    }

    private static void generateProductsReport(Map<String, Product> products,
                                               Map<String, Integer> qtyPerProduct) throws IOException {

        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(qtyPerProduct.entrySet());
        sorted.sort((a, b) -> b.getValue().compareTo(a.getValue())); // descendente

        try (BufferedWriter w = new BufferedWriter(new FileWriter(OUTPUT_DIR + "/products_report.csv"))) {
            for (Map.Entry<String, Integer> entry : sorted) {
                Product p = products.get(entry.getKey());
                if (p != null) {
                    w.write(p.getName() + ";" + p.getPrice() + ";" + entry.getValue());
                    w.newLine();
                }
            }
        }
    }
}
