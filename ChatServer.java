package WebSockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer extends JFrame implements ActionListener
{
    static Socket socket;
    static ServerSocket serverSocket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;

    JTextArea chat;
    JTextArea message;
    JButton send;
    JPanel center;
    JPanel south;

    ChatServer()
    {
        setTitle("Text messenger - SERVER");
        setSize(600,600);
        setLayout(new BorderLayout());

        center = new JPanel();
        add(center, BorderLayout.CENTER);
        chat = new JTextArea();
        chat.setPreferredSize(new Dimension(500,500));
        center.add(chat);

        south = new JPanel();
        add(south, BorderLayout.SOUTH);
        message = new JTextArea();
        message.setPreferredSize(new Dimension(300,50));
        south.add(message);
        send = new JButton("Send");
        send.addActionListener(this);
        south.add(send);

        setVisible(true);
        setResizable(false);
    }
    public static void main(String[] args)
    {
        ChatServer server = new ChatServer();
        String msgin = "";

        try
        {
            serverSocket = new ServerSocket(3142);
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (!msgin.equals("exit"))
            {
                msgin = dataInputStream.readUTF();
                server.chat.setText(server.chat.getText().trim() + "\nClient:\t" + msgin);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        if (actionEvent.getSource() == send)
        {
            try
            {
                String msgout = message.getText().trim();
                dataOutputStream.writeUTF(msgout);
                chat.setText(chat.getText().trim() + "\nServer:\t"+msgout);
                message.setText("");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
