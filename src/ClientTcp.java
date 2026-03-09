import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTcp {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Scanner tastiera = new Scanner(System.in);
        System.out.println("Inserisci comand (es: ADD Mario 10):");
        String cmd = tastiera.nextLine();

        out.println(cmd);

        String risposta = in.readLine();
        System.out.println("Risposta: " + risposta);

        socket.close();
    }
}
