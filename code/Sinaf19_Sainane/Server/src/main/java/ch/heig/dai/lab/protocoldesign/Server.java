package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {


    final String[] OPERATIONS = {"ADD", "MUL", "POW", "SOLVE_LINEAR"};
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            while (true) {
                try (Socket socket = serverSocket.accept()) {

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream(), StandardCharsets.UTF_8));

                    BufferedWriter out = new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream(), StandardCharsets.UTF_8));

                    out.write("Welcome! You can use the following commands: \n");
                    out.flush();

                    for (String s : OPERATIONS) {
                        out.write(s + "\n");
                        out.flush();
                    }

                    out.write("END_OF_OPERATIONS\n");
                    out.flush();


                    String line;
                    String answerToSend = "";
                    // out.write("Reading until client closes connection...");
                    //System.out.println(in.readLine());
                    line = in.readLine();
                    answerToSend = answer(line);

                    out.write(answerToSend + '\n');
                    out.flush();

                    out.write("Finishing process...");
                    socket.close();
                    out.close();
                    in.close();

                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }

        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }


    private String answer(String input) {
        //System.out.println("In answer");
        StringBuilder toCheck = new StringBuilder(input);
        String[] element = input.split(" ");
        if(element.length != 3) {
            return "ERROR: Wrong query";
        }
        double operand1;
        double operand2;
        //System.out.println("Parsing the operands");
        try {
            operand1 = Double.parseDouble(element[1]);
            operand2 = Double.parseDouble(element[2]);
        } catch (NumberFormatException n) {
            return "ERROR: The operands are not numbers.";
        }

        //System.out.println("Going in the switch");
        return switch (element[0]) {
            case "ADD" -> Double.toString(operand1 + operand2);
            case "MUL" -> Double.toString(operand1 * operand2);
            case "POW" -> Double.toString(Math.pow(operand1, operand2));
            case "SOLVE_LINEAR" -> {
                if (operand1 == 0) {
                    yield "ERROR: Equation not solvable !";
                }
                yield "x = " + (-operand2) / operand1;
            }
            default -> "ERROR: Operation is not supported.";
        };
    }
}