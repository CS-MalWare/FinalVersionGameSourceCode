package gamesource.initialinterface;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.ContextActivationException;
import java.io.IOException;

/*
 * The class is used to run the interface
 */
public class Main_test
{
    public static void start() throws InterruptedException {

        //t3 thread is used to run the start interface
        Thread t3 = new Thread(new Runnable(){
            @Override
            public void run()
            {
                try {
                    StartInterface.init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },"t3");

        //t2 is used to run the cartoon in the top of the second interface
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                cartoon(gamesource.initialinterface.Test.texture_cartoon, gamesource.initialinterface.Test.cartoon, gamesource.initialinterface.Test.window);

            }
        },"t2");

        //t1 is used to run the control panel(second interface)
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                {
                    try {
                        gamesource.initialinterface.Test.run();
                    } catch (ContextActivationException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        },"t1");


        t3.run();
        t1.start();
        Thread.sleep(2000);  //wait for thread t1 to load the resources
        t2.run();  //run the cartoon
    }

    /**
     * The main function for the interface
     * Get the parameters from the class User_data and pass them
     */

    public static void start_game() throws InterruptedException, IOException {
        int shadow;
        int alias;
        boolean shadow_bool = true;
        start();
        if(User_data.getShadow_user().equals("high"))
        {
            shadow = 8192;
        }
        else if(User_data.getShadow_user().equals("medium"))
        {
            shadow = 4096;
        }
        else if(User_data.getShadow_user().equals("low"))
        {
            shadow = 1024;
        }
        else
        {
            shadow_bool = false;
            shadow = 0;
        }

        if(User_data.getAliase().equals("16x"))
        {
            alias = 16;
        }
        else if(User_data.getAliase().equals("8x"))
        {
            alias = 8;
        }
        else
        {
            alias = 4;
        }
        gamesource.start st = new gamesource.start();
        /*
        Pass the parameter and stat the game
         */
        if(shadow_bool)
            st.Start(User_data.getFrame_user(), User_data.getChapter(),shadow, 1, alias, User_data.getFull());
        else
            st.Start(User_data.getFrame_user(), User_data.getChapter(),0, 0, alias, User_data.getFull());
    }
    public static void main(String[] argcs) throws InterruptedException, ContextActivationException, IOException {
        /*int shadow;
        int alias;
        boolean shadow_bool = true;
        if(User_data.getShadow_user().equals("high"))
        {
            shadow = 8192;
        }
        else if(User_data.getShadow_user().equals("medium"))
        {
            shadow = 4096;
        }
        else if(User_data.getShadow_user().equals("low"))
        {
            shadow = 1024;
        }
        else
        {
            shadow_bool = false;
            shadow = 0;
        }

        if(User_data.getAliase().equals("16x"))
        {
            alias = 16;
        }
        else if(User_data.getAliase().equals("8x"))
        {
            alias = 8;
        }
        else
        {
            alias = 4;
        }
        start();
        gamesource.start st = new gamesource.start();
        *//*
        Pass the parameter and stat the game
         *//*
        if(shadow_bool)
            st.Start(User_data.getFrame_user(), User_data.getChapter(),shadow, 1, alias, User_data.getFull());
        else
            st.Start(User_data.getFrame_user(), User_data.getChapter(),0, 0, alias, User_data.getFull());
*/
        start_game();
    }

    /*
    It will get the texture and the sprite, then the function will change the picture to make the picture move
     */
    public static void cartoon(Texture[] texture_cartoon, Sprite cartoon, RenderWindow window){
            while(window.isOpen()){
                for (int i = 1; i <= 31; i++) {
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
