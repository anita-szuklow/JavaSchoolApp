package schoolapp;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.border.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

class WatekKlientaChat extends Thread {
	
    private BufferedReader in;  // strumień do słuchania serwera
    ChatClient okno;            // okno - właściciel

    public WatekKlientaChat(ChatClient okno) {
        this.okno = okno;
        // kojarzę strumień z gniazdem
        try {
            in = new BufferedReader(
                    new InputStreamReader(okno.socket.getInputStream()));
        } catch (IOException e) {}
    }

    public void run() {
    	String str=null;
        try {
            while ((str = in.readLine()) != null) { //aż nie będzie końca pliku
              // dopisuję co przeczytałem z gniazda
                okno.lista.append(str + "\n");

              // poniższe by widzieć ostatni wpis do JTextArea
                okno.lista.scrollRectToVisible(
                 	new Rectangle(0, okno.lista.getHeight()-2, 1, 1));                 
                okno.gora.repaint();
            }
            in.close(); //Zamykam strumień wejściowy od serwera
        } catch (IOException e) {
            okno.lista.append("Blad: " + e);
        }
    }

}

public class ChatClient extends JFrame implements ActionListener {
    PrintWriter out;
    int portSerwera = 12345; 
    String adresSerwera = "127.0.0.1"; 
    Socket socket = null; 
    String name;
        
    JTextArea lista = new JTextArea();
    JScrollPane gora = new JScrollPane(lista);
    JTextField wpis = new JTextField(27);
    JButton wyslij = new JButton("Send message");

    ChatClient(String name) {
        super("Chat client: " + name);
        this.name = name;
        init();
        polacz();
    }

    void init() {
        setSize(500, 400);
        setResizable(false);
        Container cp = getContentPane();
        
        lista.setEditable(false);
        gora.setBorder(
            new TitledBorder(new LineBorder(Color.red), "Server's message"));        
        cp.add(gora, BorderLayout.CENTER);
        
        JPanel dol = new JPanel();
        dol.setLayout(new FlowLayout());
        wyslij.addActionListener(this);
        dol.add(wyslij);
        wpis.addActionListener(this);
        dol.add(wpis);        
        cp.add(BorderLayout.SOUTH, dol);
        
        Zamykacz stroz = new Zamykacz(this);
        addWindowListener(stroz);
    }

    void polacz() {
        try {
            socket = new Socket(adresSerwera, portSerwera);
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            new WatekKlientaChat(this).start();
            out.println(name); // Send the userName to the server immediately after connecting
        } catch (IOException e) {
            lista.append("Failed to establish connection.\n");
        }
    }

    public void actionPerformed(ActionEvent zdarzenie) {
        out.println(wpis.getText());
        if (wpis.getText().equals("Bye")){
          try {
        	out.close();
        	socket.close();
          } catch(Exception e){}
            //System.exit(0);
        }
        wpis.setText("");
    }
}

// Klasa nasłuchująca zdarzeń w oknie, w szczególności zamykania okna
// Będzie pracować dla klasy ChatKlient (czyli dla okna)
// Jej obiekt będzie zmienną w metodzie main()

class Zamykacz extends WindowAdapter {
    ChatClient okno;
    Zamykacz(ChatClient okno) {
        this.okno = okno;
    }
    public void windowClosing(WindowEvent e) {
      //wysłanie tego tekstu spowoduje przerwanie polaczenia z serwerem
        okno.out.println("Bye");
        okno.dispose();
    }
}

