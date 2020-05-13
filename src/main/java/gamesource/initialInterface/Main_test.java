package gamesource.initialInterface;

import gamesource.initialInterface.Test;
import org.jsfml.window.ContextActivationException;

import java.io.IOException;

public class Main_test
{
    public static void main(String[] argcs) throws IOException, InterruptedException, ContextActivationException {
        Test test = new Test();
        test.run();

    }
}
