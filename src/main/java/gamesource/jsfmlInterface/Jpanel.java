package gamesource.jsfmlInterface;

import gamesource.State.worldState.*;
import gamesource.start;
import gamesource.util.Storage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Jpanel extends JFrame implements ActionListener {
    JRadioButton full,half,state1,state2,state3,state4,state5,state6,x1,x4,x8,x16,noneshadow,lowshadow,normalshadow,highshadow;
    JButton start;
    JButton newGame;
    boolean Full=false,shadow=false;
    int State=1;
    int alising=1;
    int sha=1024;
    boolean xuwan[]=new boolean[4];
    gamesource.start s1=new start();
    public Jpanel(){
        // 这里是导入地图存档的,导入人物属性存档在 MainRole类中
        Storage.load();
        for(int i=0;i<4;i++){
            xuwan[i]=false;
        }
        setTitle("Start");
        setSize(700,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JPanel jp=new JPanel(new GridLayout(5, 1));

        JPanel jp1=new JPanel();
        JLabel jl1=new JLabel("Resolution: ");
        half=new JRadioButton("1600*900");
        half.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(half.isSelected()){
                    full.setSelected(false);
                    Full=false;
                    xuwan[0]=true;
                }
            }
        });
        full=new JRadioButton("Full");
        full.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(full.isSelected()){
                    half.setSelected(false);
                    Full=true;
                    xuwan[0]=true;
                }
            }
        });
        jp1.add(jl1);
        jp1.add(half);
        jp1.add(full);
        jp.add(jp1);

        JPanel jp2=new JPanel();
        JLabel jl2=new JLabel("Anti-alising: ");
        x1=new JRadioButton("1x");
        x1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(x1.isSelected()){
                    x4.setSelected(false);
                    x8.setSelected(false);
                    x16.setSelected(false);
                    alising=1;
                    xuwan[1]=true;
                }
            }
        });
        x4=new JRadioButton("4x");
        x4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(x4.isSelected()){
                    x1.setSelected(false);
                    x8.setSelected(false);
                    x16.setSelected(false);
                    alising=4;
                    xuwan[1]=true;
                }
            }
        });
        x8=new JRadioButton("8x");
        x8.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(x8.isSelected()){
                    x1.setSelected(false);
                    x4.setSelected(false);
                    x16.setSelected(false);
                    alising=8;
                    xuwan[1]=true;
                }
            }
        });
        x16=new JRadioButton("16x");
        x16.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(x16.isSelected()){
                    x4.setSelected(false);
                    x8.setSelected(false);
                    x1.setSelected(false);
                    alising=16;
                    xuwan[1]=true;
                }
            }
        });
        jp2.add(jl2);
        jp2.add(x1);
        jp2.add(x4);
        jp2.add(x8);
        jp2.add(x16);
        jp.add(jp2);

        JPanel jp3=new JPanel();
        JLabel jl3=new JLabel("Shadow: ");
        noneshadow=new JRadioButton("None");
        noneshadow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(noneshadow.isSelected()){
                    lowshadow.setSelected(false);
                    normalshadow.setSelected(false);
                    highshadow.setSelected(false);
                    shadow=false;
                    sha=0;
                    xuwan[2]=true;
                }
            }
        });
        lowshadow=new JRadioButton("Low");
        lowshadow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(lowshadow.isSelected()){
                    noneshadow.setSelected(false);
                    normalshadow.setSelected(false);
                    highshadow.setSelected(false);
                    shadow=true;
                    sha=1024;
                    xuwan[2]=true;
                }
            }
        });
        normalshadow=new JRadioButton("Normal");
        normalshadow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(normalshadow.isSelected()){
                    lowshadow.setSelected(false);
                    noneshadow.setSelected(false);
                    highshadow.setSelected(false);
                    shadow=true;

                    sha=4096;
                    xuwan[2]=true;
                }
            }
        });
        highshadow=new JRadioButton("High");
        highshadow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(highshadow.isSelected()){
                    lowshadow.setSelected(false);
                    normalshadow.setSelected(false);
                    noneshadow.setSelected(false);
                    shadow=true;
                    sha=8192*2;
                    xuwan[2]=true;
                }
            }
        });
        jp3.add(jl3);
        jp3.add(noneshadow);
        jp3.add(lowshadow);
        jp3.add(normalshadow);
        jp3.add(highshadow);
        jp.add(jp3);

        JPanel jp4=new JPanel();
        JLabel jl4=new JLabel("Choose a level or Start a new game: ");
        state1=new JRadioButton("level1");
        state1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(state1.isSelected()){
                    state2.setSelected(false);
                    state3.setSelected(false);
                    state4.setSelected(false);
                    state5.setSelected(false);
                    state6.setSelected(false);
                    State=1;
                    xuwan[3]=true;
                }
                if(!FirstState.canGo.equals("can")){
                    state1.setSelected(false);
                    JOptionPane.showMessageDialog(null, "This map you didn't open.", "warning", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        state2=new JRadioButton("level2");
        state2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(state2.isSelected()){
                    state1.setSelected(false);
                    state3.setSelected(false);
                    state4.setSelected(false);
                    state5.setSelected(false);
                    state6.setSelected(false);
                    State=2;
                    xuwan[3]=true;
                }
                if(!SecondState.canGo.equals("can")){
                    JOptionPane.showMessageDialog(null, "This map you didn't open.", "warning", JOptionPane.ERROR_MESSAGE);
                    state2.setSelected(false);
                    State=1;
                    xuwan[3]=true;
                }
            }
        });
        state3=new JRadioButton("level3");
        state3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(state3.isSelected()){
                    state1.setSelected(false);
                    state2.setSelected(false);
                    state4.setSelected(false);
                    state5.setSelected(false);
                    state6.setSelected(false);
                    State=3;
                    xuwan[3]=true;
                }
                if(!ThirdState.canGo.equals("can")){
                    JOptionPane.showMessageDialog(null, "This map you didn't open.", "warning", JOptionPane.ERROR_MESSAGE);
                    state3.setSelected(false);
                    State=1;
                    xuwan[3]=true;
                }
            }
        });
        state4=new JRadioButton("level4");
        state4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(state4.isSelected()){
                    state1.setSelected(false);
                    state3.setSelected(false);
                    state2.setSelected(false);
                    state5.setSelected(false);
                    state6.setSelected(false);
                    State=4;
                    xuwan[3]=true;
                }
                if(!ForthState.canGo.equals("can")){
                    JOptionPane.showMessageDialog(null, "This map you didn't open.", "warning", JOptionPane.ERROR_MESSAGE);
                    state4.setSelected(false);
                    State=1;
                    xuwan[3]=true;
                }
            }
        });
        state5=new JRadioButton("level5");
        state5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(state5.isSelected()){
                    state1.setSelected(false);
                    state3.setSelected(false);
                    state4.setSelected(false);
                    state2.setSelected(false);
                    state6.setSelected(false);
                    State=5;
                    xuwan[3]=true;
                }
                if(!FifthState.canGo.equals("can")){
                    JOptionPane.showMessageDialog(null, "This map you didn't open.", "warning", JOptionPane.ERROR_MESSAGE);
                    state5.setSelected(false);
                    State=1;
                    xuwan[3]=true;
                }
            }
        });
        state6=new JRadioButton("level6");
        state6.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(state6.isSelected()){
                    state1.setSelected(false);
                    state3.setSelected(false);
                    state4.setSelected(false);
                    state5.setSelected(false);
                    state2.setSelected(false);
                    State=6;
                    xuwan[3]=true;
                }
                if(!SixthState.canGo.equals("can")){
                    JOptionPane.showMessageDialog(null, "This map you didn't open.", "warning", JOptionPane.ERROR_MESSAGE);
                    state6.setSelected(false);
                    State=1;
                    xuwan[3]=true;
                }
            }
        });
        jp4.add(jl4);
        jp4.add(state1);
        jp4.add(state2);
        jp4.add(state3);
        jp4.add(state4);
        jp4.add(state5);
        jp4.add(state6);
        jp.add(jp4);

        JPanel jp5=new JPanel();
        start=new JButton("START");
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean xu=true;
                for(int i=0;i<4;i++){
                    if(xuwan[i]=false)
                    xu=false;
                }
                if(xu){
                    if(shadow==true) {
                        try {
                            //setVisible(false);
                            dispose();
                            s1.Start(0,State,sha,1,alising,Full);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }else{
                        try {
                            //setVisible(false);
                            dispose();
                            s1.Start(0,State,sha,0,alising,Full);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        newGame=new JButton("NEW GAME");
        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Storage.reset();
                boolean xu=true;
                for(int i=0;i<4;i++){
                    if(xuwan[i]=false&&i!=3)
                        xu=false;
                }
                if(xu){
                    if(shadow==true) {
                        try {
                            //setVisible(false);
                            dispose();
                            s1.Start(0,State,sha,1,alising,Full);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }else{
                        try {
                            //setVisible(false);
                            dispose();
                            s1.Start(0,State,sha,0,alising,Full);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        jp5.add(start);
        jp5.add(newGame);
        jp.add(jp5);

        add(jp);
    }

    public static void main(String[] args){
        new Jpanel().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
            if(full.isSelected()){
                half.setSelected(false);
                Full=true;
            }
        if(half.isSelected()){
            //half.setBackground(Color.darkGray);
            full.setSelected(false);
            Full=false;
        }
    }
}
