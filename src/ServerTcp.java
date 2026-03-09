import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import static java.lang.System.out;

public class ServerTcp {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1234);
        out.println("Server avviato");

        HashMap<String, Integer> utenti = new HashMap<>();

        while (true) {
            Socket client = server.accept();
            out.println("Client connesso");
            new Thread(new ClientHandler(client, utenti)).start();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                    String linea = in.readLine();
                    String risposta = gestisciComando(linea, utenti);

                    out.println(risposta);

                    client.close();
        }
    }

    static String gestisciComando(String cmd, HashMap<String, Integer> utenti){
        String[] parti = cmd.split(" ");
        String operazione = parti[0];

        switch(operazione) {
            case "ADD": {
                String nome = parti[1];
                int punteggio = Integer.parseInt (parti[2]);
                utenti.put(nome, punteggio);
                return "OK";
            }
            case "GET": {
                String nome = parti[1];
                Integer p = utenti.get(nome);
                return (p == null) ? "NOT FOUND" : p.toString();
            }
            case "REMOVE": {
                String nome = parti[1];
                utenti.remove(nome);
                return "REMOVED";
            }
            case "ALL": {
                return utenti.toString();
            }
            case "MAX": {
                String maxNome = null;
                int maxVal = Integer.MIN_VALUE;
                for (String n : utenti.keySet()) {
                    int v = utenti.get(n);
                    if (v > maxVal) {
                        maxVal = v;
                        maxNome = n;
                    }
                }
                return (maxNome == null) ? "EMPTY" : maxNome;
            }
            default:
                return "UNKNOWN COMMAND";
        }
    }
}
