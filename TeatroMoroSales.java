/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package teatromorosales;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * @author gustavo.dominguez
 */
public class TeatroMoroSales {

    // Static global variables
    static final String THEATER_NAME = "Teatro Moro";
    static final int THEATER_CAPACITY = 100;
    static final double VIP_PRICE = 30000;
    static final double PLATEA_PRICE = 20000;
    static final double BALCONY_PRICE = 15000;

    static int totalTicketsSold = 0;
    static double totalRevenue = 0;

    // Instance variables (persistent sales data)
    static ArrayList<String> locations = new ArrayList<>();
    static ArrayList<Double> finalCosts = new ArrayList<>();
    static ArrayList<Double> appliedDiscounts = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueProgram = true;

        while (continueProgram) {
            System.out.println("\n=== Sistema de Venta de Entradas - " + THEATER_NAME + " ===");
            System.out.println("1. Venta de entradas");
            System.out.println("2. Visualizar resumen de ventas");
            System.out.println("3. Generar boletas");
            System.out.println("4. Calcular ingresos totales");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int option = readInteger(scanner);
            switch (option) {
                case 1:
                    sellTicket(scanner);
                    break;
                case 2:
                    showSalesSummary();
                    break;
                case 3:
                    generateReceipts();
                    break;
                case 4:
                    calculateTotalRevenue();
                    break;
                case 5:
                    continueProgram = false;
                    System.out.println("Gracias por su compra. ¡Vuelva pronto!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }

    static void sellTicket(Scanner scanner) {
        if (totalTicketsSold >= THEATER_CAPACITY) {
            System.out.println("Lo sentimos, no hay más entradas disponibles.");
            return;
        }

        System.out.println("Seleccione la ubicación:");
        System.out.println("1. VIP");
        System.out.println("2. Platea");
        System.out.println("3. Balcón");
        int locationSelected = readInteger(scanner);

        // Local variables used temporarily
        String location = "";
        double basePrice = 0;

        switch (locationSelected) {
            case 1: location = "VIP"; basePrice = VIP_PRICE; break;
            case 2: location = "Platea"; basePrice = PLATEA_PRICE; break;
            case 3: location = "Balcón"; basePrice = BALCONY_PRICE; break;
            default:
                System.out.println("Opción inválida. Se asignará 'Balcón'.");
                location = "Balcón";
                basePrice = BALCONY_PRICE;
        }

        System.out.print("Ingrese su edad: ");
        int age = readInteger(scanner);
        System.out.print("¿Es estudiante? (s/n): ");
        boolean isStudent = scanner.next().equalsIgnoreCase("s");

        double discount = 0;
        if (isStudent) discount = 0.10;
        else if (age >= 65) discount = 0.15;

        double finalPrice = basePrice * (1 - discount);

        locations.add(location);
        finalCosts.add(finalPrice);
        appliedDiscounts.add(basePrice * discount);

        totalTicketsSold++;
        totalRevenue += finalPrice;

        System.out.println("Venta registrada exitosamente. Total a pagar: $" + String.format("%.2f", finalPrice));
    }

    static void showSalesSummary() {
        if (locations.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        System.out.println("\n=== Resumen de Ventas ===");
        for (int i = 0; i < locations.size(); i++) {
            System.out.println((i + 1) + ". Ubicación: " + locations.get(i)
                    + " | Costo final: $" + String.format("%.2f", finalCosts.get(i))
                    + " | Descuento aplicado: $" + String.format("%.2f", appliedDiscounts.get(i)));
        }
    }

    static void generateReceipts() {
       if (locations.isEmpty()) {
           System.out.println("No hay boletas para generar.");
           return;
       }

       for (int i = 0; i < locations.size(); i++) {
           double baseCost = finalCosts.get(i) + appliedDiscounts.get(i);
           double discountPercent = (appliedDiscounts.get(i) / baseCost) * 100;

           System.out.println("----------------------------------------");
           System.out.println("              " + THEATER_NAME);
           System.out.println("----------------------------------------");
           System.out.println("Ubicación: " + locations.get(i));
           System.out.println("Costo Base: $" + String.format("%.0f", baseCost));
           System.out.println("Descuento Aplicado: " + String.format("%.0f", discountPercent) + "%");
           System.out.println("Costo Final: $" + String.format("%.0f", finalCosts.get(i)));
           System.out.println("----------------------------------------");
           System.out.println("Gracias por su visita al " + THEATER_NAME);
           System.out.println("----------------------------------------\n");
       }
   }

    static void calculateTotalRevenue() {
        System.out.println("\nTotal de entradas vendidas: " + totalTicketsSold);
        System.out.println("Ingresos totales: $" + String.format("%.2f", totalRevenue));
    }

    static int readInteger(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Entrada inválida. Se tomará como -1.");
            return -1;
        }
    }
}
