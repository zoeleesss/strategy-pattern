/**
 * Created by sss on 01/11/2017.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import discount.*;
import model.*;
import java.io.*;
import java.util.*;

public class GUI{

    public GUI(){

        JFrame f=new JFrame("sale system");
        Container c=f.getContentPane();
        c.setLayout(null);
        JButton b1=new JButton("get total price");

        JComboBox models=new JComboBox();
        JComboBox discounts=new JComboBox();

        JLabel quantity=new JLabel("quantity");
        JLabel type=new JLabel("machine type");
        JLabel off=new JLabel("discount");


        //get all models types except the abstract model


        ArrayList<String> model_set=new ArrayList<>();

        findAndAddClassesInPackageByFile("model","/Library/Tomcat/webapps/ROOT/strategy-sale/src/model",false,model_set);

        for (int i=0;i<model_set.size();i++)
        {
            if (!model_set.get(i).equals("Model"))
                    models.addItem(model_set.get(i));
        }

        ArrayList<String> discount_set=new ArrayList<>();


        findAndAddClassesInPackageByFile("discount","/Library/Tomcat/webapps/ROOT/strategy-sale/src/discount",false,discount_set);


        for (int j=0;j<discount_set.size();j++)
        {
            if (!discount_set.get(j).equals("Discount"))
                discounts.addItem(discount_set.get(j));
        }


        JTextPane quantity_input=new JTextPane();


        off.setBounds(160,70,100,50);
        type.setBounds(20,70,100,50);
        quantity.setBounds(400,70,100,50);
        models.setBounds(20,140,100,50);
        discounts.setBounds(160,140,100,50);
        b1.setBounds(200,300,200,50);
        quantity_input.setBounds(400,150,50,30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {

                    Discount discount_item;
                    Model model_item;


                    String model_name = (String)models.getSelectedItem();
                    Class model_i =Class.forName("model."+model_name);
                    model_item=(Model)model_i.newInstance();

                    String dis_name = (String)discounts.getSelectedItem();
                    Class dis_i =Class.forName("discount."+dis_name);
                    discount_item=(Discount)dis_i.newInstance();

                    float price_per=model_item.getPrice();
                    float total_price=0;

                    int num=Integer.parseInt(quantity_input.getText());

                    if (num<=0)
                    {
                        JOptionPane.showMessageDialog(null,"Enter a POSITIVE number: ","error",JOptionPane.ERROR_MESSAGE);
                    }
                    else {

                        total_price = discount_item.getPrice(price_per, num);

                        JOptionPane.showMessageDialog(null, "Total price is: " + total_price, "info", JOptionPane.INFORMATION_MESSAGE);

                    }
                }
                catch (NumberFormatException exn){
                    JOptionPane.showMessageDialog(null,"Enter a num!! ","error",JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }




            }
        });

        c.add(models);
        c.add(quantity_input);
        c.add(discounts);
        c.add(b1);
        c.add(off);
        c.add(type);
        c.add(quantity);



        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600,600);
        f.setVisible(true);



    }





    public static void main(String[] args){


        new GUI();


    }



    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public void findAndAddClassesInPackageByFile(String packageName,
                                                        String packagePath, final boolean recursive, ArrayList<String> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.java结尾的文件
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".java"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "."
                                + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            } else {
                // 如果是java类文件 去掉后面的.java 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 5);
                try {
                    // 添加到集合中去
                    classes.add(className);

                } catch (Exception e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.java文件");
                    e.printStackTrace();
                }
            }
        }
    }




}
