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

             PrintWriter out = new PrintWriter(socket.getOutputStream())) {

            String userInput;
            Boolean serverCanListen = false;
            String result;
            while ((result = in.readLine()) != null) {
                System.out.println(result);

                if (result.equals("END_OF_OPERATIONS")) {
                    serverCanListen = true;
                }

                if (serverCanListen) {
                    userInput = stdin.nextLine();
                    out.println(userInput);
                    out.flush();
                    serverCanListen = false;
                }
            }
            out.close();
            stdin.close();
            out.flush();
            in.close();
            socket.close();

        } catch  (IOException e) {
            System.out.println("Client: ex.: " + e);
        }
    }
}