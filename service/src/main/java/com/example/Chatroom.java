package com.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Chatroom extends JFrame implements Server.OnServiceListener, ActionListener {
    private JLabel clientLabel;
    private JList clientList;
    private JLabel historyLabel;
    private JScrollPane jScrollPane;
    private JTextArea historyContentLabel;
    private JTextField messageText;
    private JButton sendButton;
    private Server server;
    private StringBuffer buffers;


    public Chatroom() {
        buffers = new StringBuffer();
        clientLabel = new JLabel("�ͻ��б�");
        clientLabel.setBounds(0, 0, 100, 30);
        clientList = new JList<>();
        clientList.setBounds(0, 30, 100, 270);
        historyLabel = new JLabel("�����¼");
        historyLabel.setBounds(100, 0, 500, 30);

        historyContentLabel = new JTextArea();
        jScrollPane=new JScrollPane(historyContentLabel);
        jScrollPane.setBounds(100, 30, 500, 230);
        //�ֱ�����ˮƽ�ʹ�ֱ�������Զ�����
        jScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        messageText = new JTextField();
        messageText.setBounds(100, 270, 440, 30);
        sendButton = new JButton("����");
        sendButton.setBounds(540, 270, 60, 30);


        sendButton.addActionListener(this);
        this.setLayout(null);

        add(clientLabel);
        add(clientList);
        add(historyLabel);
        add(jScrollPane);
        add(messageText);
        add(sendButton);

        //���ô���
        this.setTitle("������");//�����ǩ
        this.setSize(600, 330);//�����С
        this.setLocationRelativeTo(null);//����Ļ�м���ʾ(������ʾ)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�˳��ر�JFrame
        this.setVisible(true);//��ʾ����
        this.setResizable(false);

        server = new Server();
        server.setOnServiceListener(this);
        server.start();
    }

    @Override
    public void onClientChanged(List<Client> clients) {
        // TODO Auto-generated method stub
        clientList.setListData(clients.toArray());
    }


    @Override
    public void onNewMessage(String message, Client client) {
        // TODO Auto-generated method stub
        buffers.append(client.getSocket().getInetAddress().toString()+"\n");
        buffers.append(message+"\n");
        historyContentLabel.setText(buffers.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == sendButton) {
            server.snedMessage("������#"+messageText.getText().toString());
            buffers.append("������"+"\n");
            buffers.append(messageText.getText().toString()+"\n");
            historyContentLabel.setText(buffers.toString());
        }
    }


}
