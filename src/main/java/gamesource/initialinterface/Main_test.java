package gamesource.initialinterface;


import gamesource.Interface.StartInterface;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.ContextActivationException;
import org.jsfml.window.Window;

import java.io.IOException;

public class Main_test
{
    //private static gamesource.initialinterface.Test test = new gamesource.initialinterface.Test();
    //private static StartInterface startInterface = new StartInterface();
    public static void start() throws InterruptedException {
        Thread t3 = new Thread(new Runnable(){
            @Override
            public void run()
            {
                try {
                    StartInterface.init();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ContextActivationException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t3");

        //t3.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                cartoon(gamesource.initialinterface.Test.texture_cartoon, gamesource.initialinterface.Test.cartoon, gamesource.initialinterface.Test.window);

            }
        },"t2");

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                {
                    try {
                        gamesource.initialinterface.Test.run();
                    } catch (IOException | ContextActivationException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        },"t1");


        t3.run();
        t1.start();
        Thread.sleep(1000);
        t2.run();
    }
    public static void main(String[] argcs) throws IOException, InterruptedException, ContextActivationException {

        start();

        System.out.println("Interrupt!");

    }
    public static void cartoon(Texture[] texture_cartoon, Sprite cartoon, RenderWindow window){
            while(window.isOpen() && gamesource.initialinterface.Test.init){
                //
                for (int i = 1; i <= 31; i++) {
                    //System.out.println("dd");
                    cartoon.setTexture(texture_cartoon[i]);

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
        }
            Thread.interrupted();
    }
}
