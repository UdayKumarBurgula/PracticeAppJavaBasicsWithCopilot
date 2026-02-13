package com.uday.copilot;

// class to calculate factorial of a number
public class Factorial {

    public static long calculate(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static void main(String[] args) {
        int number = -8; // Example input
        long fact = calculate(number);
        System.out.println("Factorial of " + number + " is: " + fact);
    }

}