package gamesource.Interface;

import org.jsfml.audio.*;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.ContextActivationException;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import java.nio.file.Paths;
import java.io.IOException;
//import static gamesource.initialinterface.Main_test.start;

/*public class StartInterface {
    public static void main(String[] arcs) throws IOException, ContextActivationException, InterruptedException {
        //int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        //int screenHeight=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        //System.out.println(screenHeight + " " + screenWidth);
        int num = 0;
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(1600,900), "Cholera");
        window.setMouseCursorVisible(true);
        window.setFramerateLimit(30);

        Text text_start = set_text("Choose the chapter and start the game!");

        Texture_init main = new Texture_init("src/main/java/gamesource/Interface/images/main.png");
        Texture background = main.get_texture();

        Texture_init texture = new Texture_init("src/main/java/gamesource/Interface/images/1.png");
        Texture texture_start_released = texture.get_texture();

        texture = new Texture_init("src/main/java/gamesource/Interface/images/2.png");
        Texture texture_start_pressed = texture.get_texture();

        texture = new Texture_init("src/main/java/gamesource/Interface/images/22.png");
        Texture texture_white_pressed = texture.get_texture();

        texture = new Texture_init("src/main/java/gamesource/Interface/images/11.png");
        Texture texture_white_released = texture.get_texture();

        texture = new Texture_init("src/main/java/gamesource/Interface/images/222.png");
        Texture texture_black_released = texture.get_texture();

        texture = new Texture_init("src/main/java/gamesource/Interface/images/111.png");
        Texture texture_black_pressed = texture.get_texture();

        Music music = new Music();
        music.openFromFile (Paths.get("src/main/java/gamesource/Interface/BGM/1.wav"));
        Music click = new Music();
        click.openFromFile (Paths.get("src/main/java/gamesource/Interface/BGM/click.wav"));
        Music move = new Music();
        move.openFromFile (Paths.get("src/main/java/gamesource/Interface/BGM/move.wav"));
        music.setLoop(true);
        music.play();


        Sprite ground = new Sprite(background);
        ground.setScale(1,1);
        Sprite start_button = new Sprite(texture_start_released);
        start_button.setPosition(800,250);
        Sprite white_button = new Sprite(texture_white_released);
        white_button.setPosition(800,400);
        Sprite black_button = new Sprite(texture_black_released);
        black_button.setPosition(800,550);


        //Main loop
        while(window.isOpen()) {
            window.clear(Color.WHITE);
            if(click_compute(texture_start_released, start_button))
            {
                text_start.setString("Start a new adventure");
                if(num<1) {
                    click.play();
                }
                num+=1;
            }
            else if(click_compute(texture_start_released, white_button))
            {
                text_start.setString("Load a past adventure");
                if(num<1) {
                    click.play();
                }
                num+=1;
            }
            else if(click_compute(texture_start_released, black_button))
            {
                text_start.setString("Leave the game");
                if(num<1) {
                    click.play();
                }
                num+=1;
            }
            else{
                text_start.setString("Choose the button and start the game!");
                num=0;
            }

            window.draw(ground);
            window.draw(start_button);
            window.draw(white_button);
            window.draw(black_button);
            window.draw(text_start);
            window.display();


            //Handle events
            for(Event event : window.pollEvents()) {
                if(event.type == Event.Type.CLOSED) {
                    //The user pressed the close button
                    window.close();
                }
                if(event.type == Event.Type.MOUSE_BUTTON_PRESSED){
                    if(click_compute(texture_start_released, start_button))
                    {
                        start_button.setTexture(texture_start_pressed);
                        move.play();
                    }
                    if(click_compute(texture_white_released, white_button))
                    {
                        white_button.setTexture(texture_white_pressed);
                        move.play();
                    }
                    if(click_compute(texture_black_released, black_button))
                    {
                        black_button.setTexture(texture_black_pressed);
                        move.play();
                    }
                }
                if(event.type == Event.Type.MOUSE_BUTTON_RELEASED)
                {
                    if(click_compute(texture_start_released, start_button))
                    {
                        start_button.setTexture(texture_start_released);
                        start();
                        music.pause();
                        window.close();
                    }
                    if(click_compute(texture_white_released, white_button))
                    {
                        white_button.setTexture(texture_white_released);
                    }
                    if(click_compute(texture_black_released, black_button))
                    {
                        black_button.setTexture(texture_black_released);
                        System.exit(0);
                    }
                }
            }
        }
    }
    public static boolean click_compute(Texture texture, Sprite button)
    {
        Vector2i verctor_mouse = Mouse.getPosition();
        Vector2i verctor_start_size = texture.getSize();
        Vector2f vector_start_position = button.getPosition();
        int mouse_x = verctor_mouse.x;
        int mouse_y = verctor_mouse.y;
        int start__size_x = verctor_start_size.x;
        int start__size_y = verctor_start_size.y;
        float start_position_x = vector_start_position.x;
        float start_position_y = vector_start_position.y;
        if(mouse_x >= start_position_x && mouse_x <= start_position_x + start__size_x &&
                mouse_y >= start_position_y && mouse_y <= start_position_y + start__size_y)
        {
            return true;
        }
        return false;
    }

    public static Text set_text(String string)
    {
        Font freeSans = new Font();
        try {
            freeSans.loadFromFile(Paths.get("src/main/java/gamesource/Interface/fonts/LUCON.TTF"));
        } catch (IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }

        Text text_start = new Text(string, freeSans, 20);
        text_start.setPosition(10, 30);
        text_start.setColor(Color.GREEN);
        text_start.setStyle(Text.BOLD);

        return text_start;
    }
}
*/