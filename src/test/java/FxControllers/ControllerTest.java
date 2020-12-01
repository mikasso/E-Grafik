package FxControllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerTest {
    private Controller controller;

     public  ControllerTest(){
        controller = new Controller();
    }


    @Test
    void displayNewFXWindow() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> controller.displayNewFXWindow(""),
                "Expected doThing() to throw, but it didn't"
        );
        assertTrue(thrown != null);
    }
}