package initialinterface;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;

public class Text_init
{
    private String string;
    private Text text;

    public Text_init(String string) {
        Font font = new Font();
        this.string = string;
        Text text_tmp = new Text(string, font, 20);
        text_tmp.setPosition(10, 30);
        text_tmp.setColor(Color.GREEN);
        text_tmp.setStyle(Text.BOLD);

        text = text_tmp;
    }

    public Text getText()
    {
        return text;
    }
}
