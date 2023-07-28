package main.java.jp.posl.itemTracker;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicReference;

public class App {
    final public static int width = 1000;
    final public static int height = 800;
    final public static String appTitle = "ItemTracker";
    final public static String[] columnNames = {"ID", "種別", "型番", "使用者/保管場所", "予算", "管理責任者", "資産番号", "備考", "確認日", "確認者"};

    public static void main(String[] args) {
        // Control the screen transitions
        String nextPage = "start";
        while (nextPage != null) {
            JFrame frame = generateFrame();

            switch (nextPage) {
                case "start":
                    System.out.print(">start");
                    nextPage = startPage(frame);
                    break;
                case "top":
                    System.out.print(">top");
                    nextPage = topPage(frame);
                    break;
                case "pc":
                case "display":
                case "server":
                case "other":
                    System.out.print(">edit");
                    nextPage = editPage(frame, nextPage);
                    break;
                default: // "null"
                    System.out.print(">null");
                    nullPage(frame);
                    break;
            }
        }
    }

    public static String startPage(JFrame frame) {
        // Get the content pane and generate GUI elements
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel messagePanel = new JPanel();
        messagePanel.setPreferredSize(new Dimension(width, 25));
        JLabel message = new JLabel("Please input the database information.");
        messagePanel.add(message, BorderLayout.CENTER);

        JPanel addressPanel = new JPanel();
        addressPanel.setPreferredSize(new Dimension(width, 25));
        addressPanel.add(new JLabel("Database"));
        JTextField address = new JTextField(30);
        address.setPreferredSize(new Dimension((int)(0.9*width), 25));
        addressPanel.add(address);

        JPanel userPanel = new JPanel();
        userPanel.setPreferredSize(new Dimension(width, 25));
        userPanel.add(new JLabel("Username"));
        JTextField user = new JTextField(30);
        user.setPreferredSize(new Dimension((int)(0.9*width), 25));
        userPanel.add(user);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setPreferredSize(new Dimension(width, 25));
        passwordPanel.add(new JLabel("Password"));
        JPasswordField passwd = new JPasswordField(30);
        passwd.setPreferredSize(new Dimension((int)(0.9*width), 25));
        passwd.setEchoChar('*');
        passwordPanel.add(passwd);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(width, 25));
        JButton button = new JButton("Connect");
        buttonPanel.add(button, BorderLayout.CENTER);

        JPanel errMessagePanel = new JPanel();
        errMessagePanel.setPreferredSize(new Dimension(width, 25));
        JLabel errMessage = new JLabel("");
        errMessagePanel.add(errMessage, BorderLayout.CENTER);

        contentPane.add(messagePanel);
        contentPane.add(addressPanel);
        contentPane.add(userPanel);
        contentPane.add(passwordPanel);
        contentPane.add(buttonPanel);
        contentPane.add(errMessagePanel);


        // Show the window
        frame.setVisible(true);


        // Action by the button
        AtomicReference<Boolean> Connection = new AtomicReference<>(false);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the input values
                String addressValue = address.getText();
                String userValue = user.getText();
                String passwdValue = new String(passwd.getPassword());
                // Check the input values
                boolean valid = DbControler.setupConnection(addressValue, userValue, passwdValue);
                Connection.set(valid);
                if (!valid) {
                    errMessage.setText("<< Connection failed.>>");
                }
            }
        });

        while(true){
            if(Connection.get() == true) break;
        }
        return "top";
    }

    public static String topPage(JFrame frame) {
        // Get the content pane and generate GUI elements
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel messagePanel = new JPanel();
        messagePanel.setPreferredSize(new Dimension(width, 25));
        JLabel message = new JLabel("物品の種類を選択してください");
        messagePanel.add(message, BorderLayout.CENTER);

        JPanel button1Panel = new JPanel();
        button1Panel.setPreferredSize(new Dimension(width, 25));
        JButton button1 = new JButton("個人PC（PC）");
        button1Panel.add(button1, BorderLayout.CENTER);
        
        JPanel button2Panel = new JPanel();
        button2Panel.setPreferredSize(new Dimension(width, 25));
        JButton button2 = new JButton("ディスプレイ（D）");
        button2Panel.add(button2, BorderLayout.CENTER);

        JPanel button3Panel = new JPanel();
        button3Panel.setPreferredSize(new Dimension(width, 25));
        JButton button3 = new JButton("サーバー（S）");
        button3Panel.add(button3, BorderLayout.CENTER);

        JPanel button4Panel = new JPanel();
        button4Panel.setPreferredSize(new Dimension(width, 25));
        JButton button4 = new JButton("その他（Z）");
        button4Panel.add(button4, BorderLayout.CENTER);

        contentPane.add(messagePanel);
        contentPane.add(button1Panel);
        contentPane.add(button2Panel);
        contentPane.add(button3Panel);
        contentPane.add(button4Panel);

        // Show the window
        frame.setVisible(true);


        // Action by the button
        AtomicReference<String> clickedButton = new AtomicReference<>("null");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clickedButton.set("pc");
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clickedButton.set("display");
            }
        });
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clickedButton.set("server");
            }
        });
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clickedButton.set("other");
            }
        });

        while(true){
            if(clickedButton.get() != "null") break;
        }
        
        return clickedButton.get();
    }

    public static String editPage(JFrame frame, String itemType) {
        // get the items information from the database
        String[][] rowsOfItems = DbControler.getItems(itemType);
        
        // Get the content pane and generate GUI elements
        Container contentPane = frame.getContentPane();
        JTable table = new JTable(rowsOfItems, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(width, height-100));
        contentPane.add(scrollPane);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(width, 25));
        JButton button = new JButton("戻る");
        buttonPanel.add(button, BorderLayout.CENTER);
        contentPane.add(buttonPanel);

        // Show the window
        frame.setVisible(true);

        // Action by the button
        AtomicReference<String> clickedButton = new AtomicReference<>("null");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clickedButton.set("top");
            }
        });

        while(true){
            if(clickedButton.get() != "null") break;
        }

        return clickedButton.get();
    }

    public static void nullPage(JFrame frame) {
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel messagePanel = new JPanel();
        messagePanel.setPreferredSize(new Dimension(width, 25));
        JLabel message = new JLabel("Error! Please restart the application.");
        messagePanel.add(message, BorderLayout.CENTER);

        contentPane.add(messagePanel);

        // Show the window
        frame.setVisible(true);

        // Wait for the user to close the window
        while (frame.isVisible()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static JFrame generateFrame() {
        JFrame frame = new JFrame(appTitle);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        return frame;
    }

}
