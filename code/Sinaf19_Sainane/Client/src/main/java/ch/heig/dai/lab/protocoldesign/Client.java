package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    final String SERVER_ADDRESS = "127.0.0.1";
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in  = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(),
                             StandardCharsets.UTF_8));

             Scanner stdin = new Scanner(System.in);

             BufferedWriter out = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream(),
                             StandardCharsets.UTF_8))) {



            // System.out.println("Please input your calculation: ");

            // Is it the client that should check for the conformity of the user input ?
            // or the server ?

            String userInput;
            Boolean serverCanListen = false;
            String result;
            while ((result = in.readLine()) != null) {
                System.out.println(result);

                if (result.equals("END_OF_OPERATIONS")) {
                    serverCanListen = true;
                }

                if (serverCanListen) {
                    userInput = in.readLine() + "\n";
                    out.write(userInput);
                    out.flush();
                }
            }
        } catch  (IOException e) {
            System.out.println("Client: ex.: " + e);
        }
    }
}