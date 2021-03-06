package gamesource.initialinterface;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import java.io.IOException;
import java.nio.file.Paths;

/*
 * the class is used to init the texture, the texture in jsfml is abstracted in th chass
 */
public class Texture_init {
    private String path;
    private Texture texture_return;

    //get the path of the picture and set the picture to the texture.
    public Texture_init(String s) {
        Texture texture = new Texture();
        try {

            Image image2 = new Image();
            image2.loadFromFile(Paths.get(s));
            image2.createMaskFromColor(Color.BLUE);
            texture.loadFromImage(image2);
        } catch (IOException | TextureCreationException ex) {
            System.err.println("Something went wrong:");
            ex.printStackTrace();
        }

        texture_return = texture;
    }

    public Texture get_texture()
    {
        return this.texture_return;
    }
}


