package schoolapp;

import java.io.*;
import java.net.*;
import java.util.*;

class OpisKlienta {
    PrintWriter wyjscie;
    String name;
    
    OpisKlienta(String name, PrintWriter wyjscie) {
        this.name = name;
        this.wyjscie = wyjscie;
    }

    //przesyłanie informacji do innego klienta
    synchronized void napiszDoMnie(String nadawca, String wiadomosc) {
        wyjscie.println(nadawca + ": " + wiadomosc);
    }
}

//klasa będąca wątkiem obsługującym konkretnego klienta
class ObslugaKlienta extends Thread {
    private Socket socket;
    private BufferedReader wejscie;
    private PrintWriter wyjscie;	
    private OpisKlienta opis;

    static HashSet<OpisKlienta> klienci = new HashSet<OpisKlienta>();    

    public ObslugaKlienta(Socket socket) throws IOException {
        this.socket = socket;
        wejscie = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        wyjscie = new PrintWriter(
                    new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                            true); //z automatycznym opróżnianiem bufora
    }
    
    public void powiedzWszystkim(String name, String wiadomosc){
//            Iterator iterator = klienci.iterator();
//            OpisKlienta nastepny;
//            while (iterator.hasNext()) {
//                nastepny = (OpisKlienta)iterator.next();
//                nastepny.napiszDoMnie(nazwa, wiadomosc);
//            }

          // albo po prostu tak
            for (OpisKlienta klient : klienci)
                klient.napiszDoMnie(name, wiadomosc);
    }

    void wypiszKlientow() {
        Iterator iterator = klienci.iterator();
        OpisKlienta nastepny;
        wyjscie.println("At the moment we have " + klienci.size() + " people connected");

        while (iterator.hasNext()) {
            nastepny = (OpisKlienta) iterator.next();
            wyjscie.println(nastepny.name);
        }
        wyjscie.println("--------------");
    }

    public void run() {        
        try {
            String name = wejscie.readLine();
            System.out.println("Joined us: " + name);
            powiedzWszystkim("Joined us: ", name);

            //zapisuję tego klienta do kontenera i wypisuję jego zawartość
            opis = new OpisKlienta(name, wyjscie);
            klienci.add(opis);
            wypiszKlientow();

            //czytam info od klienta i przesyłam innym
            while (true) {
                String info = wejscie.readLine();
                if (info.equals("Bye")) {
                    System.out.println("Logged out: " + name);
                    powiedzWszystkim("Logged out", name);
                    klienci.remove(opis);
                    wejscie.close();
                    wyjscie.close();
                    socket.close();
                    break;
                }
               	powiedzWszystkim(name, info);                
            }
        } catch (Exception e) { }
    } 
}


public class ChatServer {
    static final int PORT = 12345;
    public static void main(String[] args) {
        ServerSocket s = null;
        Socket socket = null;
        try {
            s = new ServerSocket(PORT);
            System.out.println("Server started");
            while (true) {
                socket = s.accept();
                System.out.println("New client arrived");
                
                new ObslugaKlienta(socket).start();                
            }
        } catch (IOException e) {
        } finally {
            try {
                socket.close();
                s.close();
            } catch (IOException e) {}
        }
    }
}