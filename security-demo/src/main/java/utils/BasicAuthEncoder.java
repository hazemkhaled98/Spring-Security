package utils;

import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BasicAuthEncoder {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        String credentials = username + ":" + password;


        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        System.out.println("Encoded Basic Authentication Credentials: ");
        System.out.println("Authorization: Basic " + encodedCredentials);
    }
}
