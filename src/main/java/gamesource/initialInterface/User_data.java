package gamesource.initialInterface;


public class User_data
{


    public static int chapter = 0;
    public static int screenx = 1920;
    public static int screeny = 1080;
    public static String aliase = "None";
    public static int frame_user = 60;
    public static String shadow_user = "None";

    public static int passData(int chapter_g, int screenx_g, int screeny_g, String aliase_g, int Frame_user_g, String shadow_user_g)
    {
        chapter = chapter_g;
        screenx = screenx_g;
        screeny = screeny_g;
        aliase = aliase_g;
        frame_user = Frame_user_g;
        shadow_user = shadow_user_g;

        return 0;
    }

    public static int getChapter() {
        return chapter;
    }

    public static int getScreenx() {
        return screenx;
    }

    public static int getScreeny() {
        return screeny;
    }

    public static String getAliase() {
        return aliase;
    }

    public static int getFrame_user() {
        return frame_user;
    }

    public static String getShadow_user() {
        return shadow_user;
    }

}
