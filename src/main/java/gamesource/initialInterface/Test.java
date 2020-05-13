package gamesource.initialInterface;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.*;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;

public class Test {
    private static RenderWindow window = new RenderWindow();
    private static Sprite cartoon = new Sprite();

    private static Sprite resolution1 = new Sprite();
    private static Sprite resolution2 = new Sprite();
    private static Sprite resolution3 = new Sprite();
    private static Sprite resolution4 = new Sprite();
    private static Sprite frame1 = new Sprite();
    private static Sprite frame2 = new Sprite();
    private static Sprite frame3 = new Sprite();
    private static Sprite aliasing1 = new Sprite();
    private static Sprite aliasing2 = new Sprite();
    private static Sprite aliasing3 = new Sprite();
    private static Sprite aliasing4 = new Sprite();
    private static Sprite aliasing5 = new Sprite();
    private static Sprite aliasing6 = new Sprite();
    private static Sprite shadow1 = new Sprite();
    private static Sprite shadow2 = new Sprite();
    private static Sprite shadow3 = new Sprite();
    private static Sprite shadow4 = new Sprite();

    private static Sprite map1 = new Sprite();
    private static Sprite map2 = new Sprite();
    private static Sprite chapter1 = new Sprite();
    private static Sprite chapter2 = new Sprite();
    private static Sprite chapter3 = new Sprite();
    private static Sprite chapter4 = new Sprite();
    private static Sprite chapter5 = new Sprite();
    private static Sprite chapter6 = new Sprite();
    private static Sprite chapter7 = new Sprite();
    private static Sprite character = new Sprite();

    private static int chapter = 0;
    private static int screen_x = 1920;
    private static int screen_y = 1080;
    public static int screenx = 1920;
    public static int screeny = 1080;
    public static String aliase = "None";
    public static int frame_user = 60;
    public static String shadow_user = "None";

    public static void run() throws IOException, InterruptedException, ContextActivationException {
        final int screenWidth = 1920;
        int screenHeight = 1080;
        screenHeight -= 150;
        window.create(new VideoMode(screenWidth, screenHeight), "UI test!");
        window.setMouseCursorVisible(true);

        window.setFramerateLimit(30);
        Context context = Context.getContext();
        try {
            context.setActive(true);
        }catch (Exception e)
        {
            System.out.println(e);
        }

        Font freeSans = new Font();
        try {
            freeSans.loadFromFile(Paths.get("src/fonts/LUCON.TTF"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        final Text text_start = set_text("Choose the chapter and start the game!", 1420, 30, 20);
        final Text text_chapter = set_text("Chapter: have not chosen\nIntroduce: NULL", 1420, 250, 20);
        final Text text_chapter_c = set_text("You choose chapter: NULL", 1420, screenHeight - 400, 20);
        final Text resolution_info = set_text("Resolution: " + screenx + "*" +  screeny, 1420, screenHeight - 350, 20);
        final Text frame_info = set_text("Frame: " + frame_user, 1420, screenHeight - 300, 20);
        final Text aliasing_info = set_text("Aliasing: " + aliase, 1420, screenHeight - 250, 20);
        final Text shadow_info = set_text("Shadow: " + shadow_user, 1420, screenHeight - 200, 20);

        final Text text_resolution = set_text("Resolution:", 40, 703, 20);
        final Text text_resolution1 = set_text("1920*1080", 240, 708, 15);
        final Text text_resolution2 = set_text("1680*1050", 440, 708, 15);
        final Text text_resolution3 = set_text("1400*1050", 640, 708, 15);
        final Text text_resolution4 = set_text("800*600", 840, 708, 15);

        final Text text_frame = set_text("Frame:", 40, 763, 20);
        final Text text_frame1 = set_text("30", 240, 768, 15);
        final Text text_frame2 = set_text("60", 340, 768, 15);
        final Text text_frame3 = set_text("120", 440, 768, 15);

        final Text text_aliasing = set_text("Aliasing:", 40, 823, 20);
        final Text text_aliasing1 = set_text("None", 240, 828, 15);
        final Text text_aliasing2 = set_text("2x", 340, 828, 15);
        final Text text_aliasing3 = set_text("4x", 440, 828, 15);
        final Text text_aliasing4 = set_text("6x", 540, 828, 15);
        final Text text_aliasing5 = set_text("8x", 640, 828, 15);
        final Text text_aliasing6 = set_text("16x", 740, 828, 15);

        final Text text_shadow = set_text("Shadow:", 40, 883, 20);
        final Text text_shadow1 = set_text("None", 240, 888, 15);
        final Text text_shadow2 = set_text("low", 440, 888, 15);
        final Text text_shadow3 = set_text("medium", 640, 888, 15);
        final Text text_shadow4 = set_text("high", 840, 888, 15);

        Texture_init texture = new Texture_init("src/images/start1.png");
        final Texture texture_start_released = texture.get_texture();
        texture = new Texture_init("src/images/start2.png");
        final Texture texture_start_pressed = texture.get_texture();
        final Sprite start_button = new Sprite(texture_start_released);
        start_button.setPosition(screenWidth - 200, screenHeight -50);


        Texture_init[] texture_chi = new Texture_init[7];
        final Texture[] texture_cha = new Texture[7];
        for(int i = 0; i <= 6; i++)
        {
            String path = "src/images/" + "character" +String.valueOf(i) + ".png";
            texture_chi[i] = new Texture_init(path);
            texture_cha[i] = texture_chi[i].get_texture();
        }
        character.setPosition(3 * screenWidth / 4, screenHeight - 600);
        character.setTexture(texture_cha[0]);

        Texture_init map1_ini = new Texture_init("src/images/map3.png");
        Texture map1_t = map1_ini.get_texture();
        map1.setTexture(map1_t);
        map1.setPosition(400, 100);

        Texture_init map2_ini = new Texture_init("src/images/666.png");
        Texture map2_t = map2_ini.get_texture();
        map2.setTexture(map2_t);
        map2.setPosition(20, 20);

        Texture_init resolution_ini = new Texture_init("src/images/choose1.png");
        final Texture choose1 = resolution_ini.get_texture();
        resolution1.setTexture(choose1);
        resolution2.setTexture(choose1);
        resolution3.setTexture(choose1);
        resolution4.setTexture(choose1);
        frame1.setTexture(choose1);
        frame2.setTexture(choose1);
        frame3.setTexture(choose1);
        aliasing1.setTexture(choose1);
        aliasing2.setTexture(choose1);
        aliasing3.setTexture(choose1);
        aliasing4.setTexture(choose1);
        aliasing5.setTexture(choose1);
        aliasing6.setTexture(choose1);
        shadow1.setTexture(choose1);
        shadow2.setTexture(choose1);
        shadow3.setTexture(choose1);
        shadow4.setTexture(choose1);

        resolution1.setPosition(200, 700);
        resolution2.setPosition(400, 700);
        resolution3.setPosition(600, 700);
        resolution4.setPosition(800, 700);
        frame1.setPosition(200, 760);
        frame2.setPosition(300, 760);
        frame3.setPosition(400, 760);
        aliasing1.setPosition(200, 820);
        aliasing2.setPosition(300, 820);
        aliasing3.setPosition(400, 820);
        aliasing4.setPosition(500, 820);
        aliasing5.setPosition(600, 820);
        aliasing6.setPosition(700, 820);

        shadow1.setPosition(200, 880);
        shadow2.setPosition(400, 880);
        shadow3.setPosition(600, 880);
        shadow4.setPosition(800, 880);

        Texture_init chapter01_ini = new Texture_init("src/images/chpter1.png");
        final Texture chapter01_t = chapter01_ini.get_texture();
        Texture_init chapter02_ini = new Texture_init("src/images/chpter2.png");
        final Texture chapter02_t = chapter02_ini.get_texture();
        chapter1.setPosition(290, 300);
        chapter2.setPosition(260, 110);
        chapter3.setPosition(320, 210);
        chapter4.setPosition(650, 450);
        chapter5.setPosition(700, 350);
        chapter6.setPosition(830, 400);
        chapter7.setPosition(680, 250);


        Texture_init[] texture_c = new Texture_init[32];
        final Texture[] texture_cartoon = new Texture[32];
        for(int i = 1; i <= 31; i++)
        {
            String path = "src/cartoon/k" + String.valueOf(i) + ".png";
            texture_c[i] = new Texture_init(path);
            texture_cartoon[i] = texture_c[i].get_texture();
        }

        cartoon = new Sprite(texture_cartoon[1]);

        cartoon.setPosition(screenWidth - 400, screenHeight - 600);


        /*final RectangleShape rect = new RectangleShape(new Vector2f(screenWidth, 50));

        //rect.setFillColor(new Color(0, 0, 0, 100));
        rect.setFillColor(Color.BLACK);
        rect.setOutlineColor(Color.WHITE);
        rect.setOutlineThickness(2);
        rect.setPosition(0, screenHeight - 53);*/

        final RectangleShape rect2 = new RectangleShape(new Vector2f(screenWidth, screenHeight));

        rect2.setFillColor(Color.BLACK);

        rect2.setPosition(0, 0);


        background_control(screenWidth, screenHeight,  rect2, texture_start_released,
                texture_start_pressed, text_start, text_chapter_c, text_chapter, start_button,
                cartoon, texture_cha, chapter01_t, chapter02_t, choose1, text_resolution
                ,text_resolution1, text_resolution2, text_resolution3, text_resolution4,
                text_frame, text_frame1, text_frame2, text_frame3,
                text_aliasing, text_aliasing1, text_aliasing2, text_aliasing3, text_aliasing4,
                text_aliasing5, text_aliasing6,
                text_shadow, text_shadow1, text_shadow2, text_shadow3, text_shadow4,
                resolution_info, frame_info, aliasing_info, shadow_info);



        /*Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                cartoon(texture_cartoon, cartoon);

            }
        },"t2");

        t1.start();
        t2.start();*/


    }

    public static void cartoon(Texture[] texture_cartoon, Sprite cartoon){
        while (window.isOpen()) {
            for (int i = 1; i <= 31; i++) {
                //System.out.println("dd");
                cartoon.setTexture(texture_cartoon[i]);
                window.display();
                try {
                    Thread.sleep(20);
                }
                catch (InterruptedException e)
                {
                    ;
                }
            }
        }
    }

    public static void background_control(int screenWidth, int screenHeight, RectangleShape rect2,
                                          Texture texture_start_released, Texture texture_start_pressed,
                                          Text text_start, Text text_chapter_c, Text text_chapter, Sprite start_button,
                                          Sprite cartoon, Texture[] texture_cha,
                                          Texture chapter01, Texture chapter02, Texture choose1, Text text_resolution,
                                          Text text_resolution1, Text text_resolution2, Text text_resolution3,
                                          Text text_resolution4, Text text_frame, Text text_frame1, Text text_frame2,
                                          Text text_frame3, Text text_aliasing, Text text_aliasing1, Text text_aliasing2,
                                          Text text_aliasing3, Text text_aliasing4, Text text_aliasing5, Text text_aliasing6,
                                          Text text_shadow, Text text_shadow1, Text text_shadow2, Text text_shadow3,
                                          Text text_shadow4, Text resolution_info, Text frame_info, Text aliasing_info,
                                          Text shadow_info)
            throws ContextActivationException, InterruptedException {
        {


            while (window.isOpen()) {
                window.clear(Color.WHITE);
                if (click_compute(texture_start_released, start_button)) {
                    text_start.setString("Start the game");
                } else {
                    text_start.setString("Choose the chapter and start the game!");
                }

                if(click_compute(chapter01, chapter1))
                {
                    chapter1.setTexture(chapter02);
                    text_chapter.setString("Chapter: One\nCharacter or boss:\n\tName: queen");
                    character.setTexture(texture_cha[1]);

                }else
                {
                    chapter1.setTexture(chapter01);
                }

                if(click_compute(chapter01, chapter2))
                {
                    chapter2.setTexture(chapter02);
                    text_chapter.setString("Chapter: Two\nCharacter or boss:\n\tName: dryad");
                    character.setTexture(texture_cha[2]);

                }else
                {
                    chapter2.setTexture(chapter01);
                }

                if(click_compute(chapter01, chapter3))
                {
                    // System.out.println("!!!");
                    chapter3.setTexture(chapter02);
                    text_chapter.setString("Chapter: Three\nCharacter or boss:\n\tName: water sprite");
                    character.setTexture(texture_cha[3]);

                }else
                {
                    chapter3.setTexture(chapter01);
                }

                if(click_compute(chapter01, chapter4))
                {
                    // System.out.println("!!!");
                    chapter4.setTexture(chapter02);
                    text_chapter.setString("Chapter: Four\nCCharacter or boss:\n\tName: plague doctor");
                    character.setTexture(texture_cha[4]);

                }else
                {
                    chapter4.setTexture(chapter01);
                }
                if(click_compute(chapter01, chapter5))
                {
                    // System.out.println("!!!");
                    chapter5.setTexture(chapter02);
                    text_chapter.setString("Chapter: Five\nCharacter or boss:\n\tName: kingknight");
                    character.setTexture(texture_cha[5]);

                }else
                {
                    chapter5.setTexture(chapter01);
                }

                if(click_compute(chapter01, chapter6))
                {
                    chapter6.setTexture(chapter02);
                    text_chapter.setString("Chapter: Six\nCharacter or boss:\n\tName: kingskeleton");
                    character.setTexture(texture_cha[6]);
                }else
                {
                    chapter6.setTexture(chapter01);
                }

                if(click_compute(chapter01, chapter7))
                {
                    chapter7.setTexture(chapter02);
                    text_chapter.setString("Chapter: Seven\nIntroduce: NULL");

                }else
                {
                    chapter7.setTexture(chapter01);
                }
                if(click_compute(choose1, resolution1))
                {
                    resolution1.setTexture(chapter01);
                }
                else
                {
                    resolution1.setTexture(choose1);
                }

                if(click_compute(choose1, resolution2))
                {
                    resolution2.setTexture(chapter01);
                }
                else
                {
                    resolution2.setTexture(choose1);
                }

                if(click_compute(choose1, resolution3))
                {
                    resolution3.setTexture(chapter01);
                }
                else
                {
                    resolution3.setTexture(choose1);
                }

                if(click_compute(choose1, resolution4))
                {
                    resolution4.setTexture(chapter01);
                }
                else
                {
                    resolution4.setTexture(choose1);
                }

                if(click_compute(choose1, frame1))
                {
                    frame1.setTexture(chapter01);
                }
                else
                {
                    frame1.setTexture(choose1);
                }
                if(click_compute(choose1, frame2))
                {
                    frame2.setTexture(chapter01);
                }
                else
                {
                    frame2.setTexture(choose1);
                }
                if(click_compute(choose1, frame3))
                {
                    frame3.setTexture(chapter01);
                }
                else
                {
                    frame3.setTexture(choose1);
                }

                if(click_compute(choose1, aliasing1))
                {
                    aliasing1.setTexture(chapter01);
                }
                else
                {
                    aliasing1.setTexture(choose1);
                }
                if(click_compute(choose1, aliasing2))
                {
                    aliasing2.setTexture(chapter01);
                }
                else
                {
                    aliasing2.setTexture(choose1);
                }
                if(click_compute(choose1, aliasing3))
                {
                    aliasing3.setTexture(chapter01);
                }
                else
                {
                    aliasing3.setTexture(choose1);
                }
                if(click_compute(choose1, aliasing4))
                {
                    aliasing4.setTexture(chapter01);
                }
                else
                {
                    aliasing4.setTexture(choose1);
                }
                if(click_compute(choose1, aliasing5))
                {
                    aliasing5.setTexture(chapter01);
                }
                else
                {
                    aliasing5.setTexture(choose1);
                }
                if(click_compute(choose1, aliasing6))
                {
                    aliasing6.setTexture(chapter01);
                }
                else
                {
                    aliasing6.setTexture(choose1);
                }

                if(click_compute(choose1, shadow1))
                {
                    shadow1.setTexture(chapter01);
                }
                else
                {
                    shadow1.setTexture(choose1);
                }
                if(click_compute(choose1, shadow2))
                {
                    shadow2.setTexture(chapter01);
                }
                else
                {
                    shadow2.setTexture(choose1);
                }
                if(click_compute(choose1, shadow3))
                {
                    shadow3.setTexture(chapter01);
                }
                else
                {
                    shadow3.setTexture(choose1);
                }
                if(click_compute(choose1, shadow4))
                {
                    shadow4.setTexture(chapter01);
                }
                else
                {
                    shadow4.setTexture(choose1);
                }

                for (Event event : window.pollEvents()) {
                    if (event.type == Event.Type.CLOSED) {
                        window.close();
                    }


                    if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) {

                        if(click_compute(chapter01, chapter1))
                        {
                            chapter = 1;
                            System.out.println(chapter);
                            text_chapter_c.setString("You choose chapter: "+ chapter + ", start the game");
                        }
                        if(click_compute(chapter01, chapter2))
                        {
                            chapter = 2;
                            System.out.println(chapter);
                            text_chapter_c.setString("You choose chapter: "+ chapter + ", start the game");
                        }
                        if(click_compute(chapter01, chapter3))
                        {
                            chapter = 3;
                            System.out.println(chapter);
                            text_chapter_c.setString("You choose chapter: "+ chapter + ", start the game");
                        }
                        if(click_compute(chapter01, chapter4))
                        {
                            chapter = 4;
                            System.out.println(chapter);
                            text_chapter_c.setString("You choose chapter: "+ chapter + ", start the game");
                        }
                        if(click_compute(chapter01, chapter5))
                        {
                            chapter = 5;
                            System.out.println(chapter);
                            text_chapter_c.setString("You choose chapter: "+ chapter + ", start the game");
                        }
                        if(click_compute(chapter01, chapter6))
                        {
                            chapter = 6;
                            System.out.println(chapter);
                            text_chapter_c.setString("You choose chapter: "+ chapter + ", start the game");
                        }

                        if(click_compute(chapter01, chapter7))
                        {
                            chapter = 7;
                            System.out.println(chapter);
                            text_chapter_c.setString("You choose chapter: "+ chapter + ", start the game");
                        }

                        if(click_compute(choose1, resolution1))
                        {
                            screenx = 1920;
                            screeny = 1080;
                            resolution_info.setString("Resolution: " + screenx + "*" +  screeny);
                        }

                        if(click_compute(choose1, resolution2))
                        {
                            screenx = 1680;
                            screeny = 1050;
                            resolution_info.setString("Resolution: " + screenx + "*" +  screeny);
                        }

                        if(click_compute(choose1, resolution3))
                        {
                            screenx = 1400;
                            screeny = 1050;
                            resolution_info.setString("Resolution: " + screenx + "*" +  screeny);
                        }

                        if(click_compute(choose1, resolution4))
                        {
                            screenx = 800;
                            screeny = 600;
                            resolution_info.setString("Resolution: " + screenx + "*" +  screeny);
                        }


                        if(click_compute(choose1, frame1))
                        {
                            frame_user = 30;
                            frame_info.setString("Frame: " + frame_user);
                        }

                        if(click_compute(choose1, frame2))
                        {
                            frame_user = 60;
                            frame_info.setString("Frame: " + frame_user);
                        }

                        if(click_compute(choose1, frame3))
                        {
                            frame_user = 120;
                            frame_info.setString("Frame: " + frame_user);
                        }

                        if(click_compute(choose1, aliasing1))
                        {
                            aliase = "None";
                            aliasing_info.setString("Aliasing: " + aliase);
                        }

                        if(click_compute(choose1, aliasing2))
                        {
                            aliase = "2x";
                            aliasing_info.setString("Aliasing: " + aliase);
                        }

                        if(click_compute(choose1, aliasing3))
                        {
                            aliase = "4x";
                            aliasing_info.setString("Aliasing: " + aliase);
                        }

                        if(click_compute(choose1, aliasing4))
                        {
                            aliase = "6x";
                            aliasing_info.setString("Aliasing: " + aliase);
                        }

                        if(click_compute(choose1, aliasing5))
                        {
                            aliase = "8x";
                            aliasing_info.setString("Aliasing: " + aliase);
                        }

                        if(click_compute(choose1, aliasing6))
                        {
                            aliase = "16x";
                            aliasing_info.setString("Aliasing: " + aliase);
                        }


                        if(click_compute(choose1, shadow1))
                        {
                            shadow_user = "None";
                            shadow_info.setString("Shadow: " + shadow_user);
                        }

                        if(click_compute(choose1, shadow2))
                        {
                            shadow_user = "low";
                            shadow_info.setString("Shadow: " + shadow_user);
                        }

                        if(click_compute(choose1, shadow3))
                        {
                            shadow_user = "medium";
                            shadow_info.setString("Shadow: " + shadow_user);
                        }

                        if(click_compute(choose1, shadow4))
                        {
                            shadow_user = "high";
                            shadow_info.setString("Shadow: " + shadow_user);
                        }

                        if (click_compute(texture_start_released, start_button)) {
                            start_button.setTexture(texture_start_pressed);
                            User_data user_data = new User_data();
                            user_data.passData(chapter, screenx, screeny, aliase, frame_user, shadow_user);
                            Thread.sleep(2 * 1000);
                            window.setVisible(false);
                            Thread.sleep(1 * 1000);
                            window.close();

                        }

                    }

                    if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
                        if (click_compute(texture_start_released, start_button)) {
                            start_button.setTexture(texture_start_released);
                        }
                    }


                }
                window.draw(rect2);

                window.draw(start_button);
                window.draw(text_start);
                //window.draw(cartoon);
                window.draw(map2);
                window.draw(text_chapter);
                window.draw(text_chapter_c);
                window.draw(text_resolution);
                window.draw(text_resolution1);
                window.draw(text_resolution2);
                window.draw(text_resolution3);
                window.draw(text_resolution4);
                window.draw(text_frame);
                window.draw(text_frame1);
                window.draw(text_frame2);
                window.draw(text_frame3);
                window.draw(text_aliasing);
                window.draw(text_aliasing1);
                window.draw(text_aliasing2);
                window.draw(text_aliasing3);
                window.draw(text_aliasing4);
                window.draw(text_aliasing5);
                window.draw(text_aliasing6);
                window.draw(text_shadow);
                window.draw(text_shadow1);
                window.draw(text_shadow2);
                window.draw(text_shadow3);
                window.draw(text_shadow4);
                window.draw(resolution_info);
                window.draw(frame_info);
                window.draw(aliasing_info);
                window.draw(shadow_info);
                window.draw(chapter1);
                window.draw(chapter2);
                window.draw(chapter3);
                window.draw(chapter4);
                window.draw(chapter5);
                window.draw(chapter6);
                window.draw(chapter7);
                window.draw(character);
                window.draw(resolution1);
                window.draw(resolution2);
                window.draw(resolution3);
                window.draw(resolution4);
                window.draw(frame1);
                window.draw(frame2);
                window.draw(frame3);
                window.draw(aliasing1);
                window.draw(aliasing2);
                window.draw(aliasing3);
                window.draw(aliasing4);
                window.draw(aliasing5);
                window.draw(aliasing6);
                window.draw(shadow1);
                window.draw(shadow2);
                window.draw(shadow3);
                window.draw(shadow4);
                window.display();

            }

        }
    }

    public static boolean click_compute(Texture texture, Sprite button)
    {
        Vector2i v = window.getPosition();
        System.out.println(v);

        Vector2i verctor_mouse = Mouse.getPosition();
        Vector2i verctor_start_size = texture.getSize();
        Vector2f vector_start_position = button.getPosition();
        int mouse_x = verctor_mouse.x;
        int mouse_y = verctor_mouse.y;
        int start__size_x = verctor_start_size.x;
        int start__size_y = verctor_start_size.y;
        float start_position_x = vector_start_position.x;
        float start_position_y = vector_start_position.y;
        if(mouse_x - v.x >= start_position_x && mouse_x - v.x<= start_position_x + start__size_x &&
                mouse_y - v.y - 50 >= start_position_y && mouse_y - v.y  - 50 <= start_position_y + start__size_y)
        {
            return true;
        }
        return false;
    }

    public static Text set_text(String string, int screen_x, int screen_y, int size)
    {
        Font freeSans = new Font();
        try {
            freeSans.loadFromFile(Paths.get("src/fonts/LUCON.TTF"));
        } catch (IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }

        Text text_start = new Text(string, freeSans, size);
        text_start.setPosition(screen_x , screen_y);
        text_start.setColor(Color.WHITE);
        text_start.setStyle(Text.BOLD);

        return text_start;
    }
}

