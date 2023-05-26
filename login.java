import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class login{
    public static void main(String[] args){
        signin obj = new signin();
    }
}

class signin implements ActionListener
{

    static{
        System.loadLibrary("task4");
    }

    private native boolean checkadmin(String username, String password);
    private native boolean checkuser(String username, String password);

    static String usname;
    JLabel success,l1,l2;
    JFrame f;
    JPanel p;
    JTextField tx1;
    JPasswordField tx2;
    JButton button;

    public signin(){
        f = new JFrame();
        p = new JPanel();
        f.setSize(300,200);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
        f.setTitle("Library Management - Login");
        f.add(p);   

        p.setLayout(null);
        
        l1 = new JLabel("User Name:");
        l1.setBounds(10,20,100,25);
        p.add(l1);

        tx1 = new JTextField();
        tx1.setBounds(100,20,150,25);
        p.add(tx1);

        l2 = new JLabel("Password:");
        l2.setBounds(10,50,100,25);
        p.add(l2);

        tx2 = new JPasswordField();
        tx2.setBounds(100,50,150,25);
        p.add(tx2);

        button = new JButton("Sign in");
        button.setBounds(75,80,100,25);
        button.addActionListener(this);
        p.add(button);

        success = new JLabel("");
        success.setBounds(70, 110, 150, 25);
        p.add(success);
 
        f.setVisible(true);
    }

    public static void main(String[] args){
        new login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        usname = tx1.getText();
        String pswd =  tx2.getText();

        boolean admin = checkadmin(usname, pswd);
        boolean user = checkuser(usname,pswd);

        if(admin){
            success.setText("Welcome Admin bhai");
            list ob = new list();

        }
        else if(user){
            success.setText("Welcome");
            userlist bo = new userlist();
        }
        else{
            success.setText("Wrong Credentials");
        }
        f.setVisible(false);        
    }
}

class list
{
    private static JFrame f;
    private static JPanel p;
    private static JButton bt1,bt2,bt3,bt4,bt5,bt6;
    
    public list(){
        f = new JFrame("Choose");
        p = new JPanel();
        p.setLayout(null);
        f.setSize(500,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);

        bt1 = new JButton("Book List");
        bt1.setBounds(75,20,100,25);
        bt1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                viewAll ob0 = new viewAll();
                
            }
            
        });
        p.add(bt1);

        bt2 = new JButton("Update");
        bt2.setBounds(75,50,100,25);
        bt2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                update ob1 = new update(); 
            }
        });
        p.add(bt2);
        
        bt3 = new JButton("ADD");
        bt3.setBounds(75,80,100,25);
        bt3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                add ob2 = new add();
            }
            
        });
        p.add(bt3);

        bt4 = new JButton("Delete");
        bt4.setBounds(75,110,100,25);
        bt4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delete ob3 = new delete();                
            }
            
        });
        p.add(bt4);

        /* bt5 = new JButton("User List");
        bt5.setBounds(75,170,100,25);
        p.add(bt5);
 */
        bt6 = new JButton("Logout");
        bt6.setBounds(75,140,100,25);
        bt6.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                signin ob4 = new signin();   
                f.setVisible(false);             
            }
            
        });
        p.add(bt6);

        f.setVisible(true);
    }
}

class update{

    static{
        System.loadLibrary("task4");
    }

    private native void updateBook(int bid,int q);

    private static JTextField tx2,tx1;
    private static JFrame f;
    private static JPanel p;
    private static JButton bt;
    private static JLabel l1,l2,success;

    public update(){
        
        f = new JFrame();
        p = new JPanel();
        
        f.setTitle("Update Book");
        l1 = new JLabel("Book id:");

        f.setSize(500,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        p.setLayout(null);

        l1.setBounds(10, 20, 80, 25);
        p.add(l1);

        tx1 = new JTextField();
        tx1.setBounds(100,20,125,25);
        p.add(tx1);

        l2 = new JLabel("Quantity:");
        l2.setBounds(10, 50, 80, 25);
        p.add(l2);

        tx2 = new JTextField();
        tx2.setBounds(100,50,125,25);
        p.add(tx2);

        bt = new JButton("Update");
        bt.setBounds(75,90,100,25);
        bt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int bid = Integer.parseInt(tx1.getText());
                int q = Integer.parseInt(tx2.getText());

                updateBook(bid, q);
                success.setText("Records Updated");
                f.setVisible(false);
            }
            
        });
        p.add(bt);

        success = new JLabel("");
        success.setBounds(75,110,300,100);
        p.add(success);

        f.setVisible(true);
    }
}

class add{
    static{
        System.loadLibrary("task4");
    }
    
    private native void addBook(int bid,String name,String author, String edd, String pub,int q);

    private static JTextField tx2,tx1,tx3,tx4,tx5,tx6;
    private static JFrame f;
    private static JPanel p;
    private static JButton bt;
    private static JLabel l1,l2,l3,l4,l5,l6,success;

    public add()
    {
        f = new JFrame();
        p = new JPanel();
        
        f.setTitle("Add Book");

        f.setSize(500,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        p.setLayout(null);

        l1 = new JLabel("Book id:");
        l1.setBounds(10, 20, 80, 25);
        p.add(l1);

        tx1 = new JTextField();
        tx1.setBounds(100,20,125,25);
        p.add(tx1);

        l2 = new JLabel("Book Name:");
        l2.setBounds(10, 50, 80, 25);
        p.add(l2);

        tx2 = new JTextField();
        tx2.setBounds(100,50,125,25);
        p.add(tx2);

        l3 = new JLabel("Author Name:");
        l3.setBounds(10, 80, 80, 25);
        p.add(l3);

        tx3 = new JTextField();
        tx3.setBounds(100,80,125,25);
        p.add(tx3);

        l4 = new JLabel("Edition:");
        l4.setBounds(10, 110, 80, 25);
        p.add(l4);

        tx4 = new JTextField();
        tx4.setBounds(100,110,125,25);
        p.add(tx4);

        l5 = new JLabel("Publisher:");
        l5.setBounds(10, 140, 80, 25);
        p.add(l5);

        tx5 = new JTextField();
        tx5.setBounds(100,140,125,25);
        p.add(tx5);

        l6 = new JLabel("Quantity:");
        l6.setBounds(10, 170, 80, 25);
        p.add(l6);

        tx6 = new JTextField();
        tx6.setBounds(100,170,125,25);
        p.add(tx6);

        bt = new JButton("Add");
        bt.setBounds(75,200,100,25);
        bt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int bid = Integer.parseInt(tx1.getText());
                String name = tx2.getText();
                String author = tx3.getText();
                String edd = tx4.getText();
                String pub = tx5.getText();
                int q = Integer.parseInt(tx6.getText());

                addBook(bid, name, author, edd, pub, q);
                success.setText("Books Added");
            }
            
        });
        p.add(bt);

        success = new JLabel("");
        success.setBounds(75,230,300,100);
        p.add(success);

        f.setVisible(true);
    }
}

class delete{
    static{
        System.loadLibrary("task4");
    }
    
    private native void deleteBook(int bid);

    private static JTextField tx1;
    private static JFrame f;
    private static JPanel p;
    private static JButton bt;
    private static JLabel l1,success;

    public delete(){
        f = new JFrame();
        p = new JPanel();
        
        f.setTitle("Delete Book");

        f.setSize(500,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        p.setLayout(null);

        l1 = new JLabel("Book id:");
        l1.setBounds(10, 20, 80, 25);
        p.add(l1);

        tx1 = new JTextField();
        tx1.setBounds(100,20,125,25);
        p.add(tx1);

        bt = new JButton("Delete");
        bt.setBounds(75,60,100,25);
        bt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int bid = Integer.parseInt(tx1.getText());
                deleteBook(bid);
                success.setText("Book Deleted");     
                f.setVisible(false);           
            }
        });
        p.add(bt);

        success = new JLabel("");
        success.setBounds(75,110,300,100);
        p.add(success);

        f.setVisible(true);
    }
}

class userlist{
    
    private static JFrame f;
    private static JPanel p;
    private static JButton bt1,bt2,bt3,bt4,bt5;
    
    public userlist(){
        f = new JFrame("Choose");
        p = new JPanel();
        p.setLayout(null);
        f.setSize(500,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);

        bt1 = new JButton("Book List");
        bt1.setBounds(75,20,100,25);
        bt1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                viewAll ob0 = new viewAll();
            }
            
        });
        p.add(bt1);

        bt2 = new JButton("Borrow");
        bt2.setBounds(75,50,100,25);
        bt2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                borrow ob1 = new borrow(); 
            }
        });
        p.add(bt2);
        
        bt3 = new JButton("Return");
        bt3.setBounds(75,80,100,25);
        bt3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                returnback ob2 = new returnback();
            }
            
        });
        p.add(bt3);

        bt4 = new JButton("My Books");
        bt4.setBounds(75,110,100,25);
        bt4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                myBooks ob3 = new myBooks();                
            }
            
        });
        p.add(bt4);

        bt5 = new JButton("Logout");
        bt5.setBounds(75,140,100,25);
        bt5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                signin ob4 = new signin();   
                f.setVisible(false);             
            }
            
        });
        p.add(bt5);

        f.setVisible(true);
    }
}

class borrow{

    static{
        System.loadLibrary("task4");
    }
    
    private native void borrowBook(int bid,String username);

    private static JTextField tx1;
    private static JFrame f;
    private static JPanel p;
    private static JButton bt;
    private static JLabel l1,success;
    
    public borrow()
    {
        f = new JFrame();
        p = new JPanel();
       
        f.setTitle("Borrow Book");

        f.setSize(500,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        p.setLayout(null);

        l1 = new JLabel("Book id:");
        l1.setBounds(10, 20, 80, 25);
        p.add(l1);

        tx1 = new JTextField();
        tx1.setBounds(100,20,125,25);
        p.add(tx1);

        bt = new JButton("Borrow");
        bt.setBounds(75,60,100,25);
        bt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int bid = Integer.parseInt(tx1.getText());
                String username = signin.usname;
                borrowBook(bid,username);
                success.setText("Book Borrowed");
                f.setVisible(false);
            }
            
        });
        p.add(bt);

        success = new JLabel("");
        success.setBounds(75,110,300,100);
        p.add(success);

        f.setVisible(true);
    }
}

class returnback{
    static{
        System.loadLibrary("task4");
    }
    
    private native void returnBook(int bid,String username);

    private static JTextField tx1;
    private static JFrame f;
    private static JPanel p;
    private static JButton bt;
    private static JLabel l1,success;
    
    public returnback(){
        f = new JFrame();
        p = new JPanel();
       
        f.setTitle("Return Book");

        f.setSize(500,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        p.setLayout(null);

        l1 = new JLabel("Book id:");
        l1.setBounds(10, 20, 80, 25);
        p.add(l1);

        tx1 = new JTextField();
        tx1.setBounds(100,20,125,25);
        p.add(tx1);

        bt = new JButton("Return");
        bt.setBounds(75,60,100,25);
        bt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int bid = Integer.parseInt(tx1.getText());
                String username = signin.usname;
                returnBook(bid,username);
                success.setText("Book Returned");  
                f.setVisible(false);
            }
            
        });
        p.add(bt);

        success = new JLabel("");
        success.setBounds(75,110,300,100);
        p.add(success);

        f.setVisible(true);
    }
}

class viewAll{
     static{
        System.loadLibrary("task4");
    }

    private native String viewBooks();
    private native String searchBook(String bname);

    JFrame f;    
    JPanel p;
    JButton bt;
    public viewAll(){  
        f=new JFrame(); 
        f.setSize(1000,400);   
        f.setTitle("Books");
        p=new JPanel();
        p.setLayout(null);
        p.setBounds(10,10,900,350);
        f.add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField tx = new JTextField();
        tx.setBounds(10,10,200,25);
        //findbook = Integer.parseInt(tx.getText());
        p.add(tx);
        JButton search = new JButton("Search");
        search.setBounds(210,10,100,25);
        search.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String bname = tx.getText();
                JFrame f1 = new JFrame();
                f1.setSize(800,300);
                //String bookname = viewAll.bname;
                f1.setLayout(null);
                //System.out.println(bname);
                String search = searchBook(bname);
                /* JTextArea jt = new JTextArea(search);
                jt.setBounds(15, 15,400, 250);
                f1.add(jt); */

                String[] table1 = search.split(",");
                int temp = 0;
                String[][] table2 = new String[table1.length/6][6];
                for(int i=0;i<table1.length/6;i++){
                    for(int j=0;j<6;j++){
                        table2[i][j]=table1[temp];
                        temp++;
                    }
                }
                String column[] = {"Book Id","Book Name","Author","Edition","Publisher","Quantity"};
                JTable l = new JTable(table2,column);
                l.setBounds(10,50,800,300);
                f1.add(l);
        
                bt = new JButton("Close");
                bt.setBounds(250,350,100,25);
                bt.addActionListener(new ActionListener() {
        
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        f1.setVisible(false);
                    }
                    
                });
                f1.add(bt);
                f1.setVisible(true);
//                search obj = new search();
            }
        });
        p.add(search);
        String table = viewBooks();
        String[] table1 = table.split(",");
        int temp = 0;
        String[][] table2 = new String[table1.length/6][6];
        for(int i=0;i<table1.length/6;i++){
            for(int j=0;j<6;j++){
                table2[i][j]=table1[temp];
                temp++;
            }
        }
        String column[] = {"Book Id","Book Name","Author","Edition","Publisher","Quantity"};
        JTable l = new JTable(table2,column);
        l.setBounds(10,50,800,300);
        p.add(l);

        bt = new JButton("Close");
        bt.setBounds(250,350,100,25);
        bt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
            }
            
        });
        p.add(bt);
        f.setVisible(true);
    }
}

class myBooks{
    
    static{
        System.loadLibrary("task4");
    }

    private native String BorrowedBooks(String username);

    JFrame f;    
    JPanel p;
    JButton bt;
    public myBooks(){  
        f=new JFrame(); 
        f.setSize(500,400);
        f.setTitle("My Books");
        p=new JPanel();
        p.setLayout(null);
        f.add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String username = signin.usname;
        String table = BorrowedBooks(username);
        
        String[] table1 = table.split(",");
        int temp = 0;
        String[][] table2 = new String[table1.length/7][7];
        for(int i=0;i<table1.length/7;i++){
            for(int j=0;j<7;j++){
                table2[i][j]=table1[temp];
                temp++;
            }
        }
        String column[] = {"Book Id","Book Name","Author","Edition","Publisher","Return date","Borrow date"};
        JTable l = new JTable(table2,column);
        l.setBounds(10,50,800,300);
        p.add(l);
        
        bt = new JButton("Close");
        bt.setBounds(100,320,100,25);
        bt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
            }
            
        });
        p.add(bt);
        f.setVisible(true);
    }
}

/* class search{

    static{
        System.loadLibrary("task4");
    }

    private native String searchBook(String bname);

    public search(){
        JFrame f1 = new JFrame();
        f1.setSize(500,300);
        JPanel p1 = new JPanel();
        p1.setBounds(10,10,500,300);
        p1.setLayout(null);
        f1.add(p1);
        String bookname = viewAll.bname;
        System.out.println(bookname);
        String search = searchBook(bookname);
        JTextArea jt = new JTextArea(search);
        jt.setBounds(15, 15,400, 250);
        p1.add(jt);
        f1.setVisible(true);
    }
} */