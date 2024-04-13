package RaccoonRush.game.menu.component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MenuComponentTest {
    @Test
    void testGetComponentPosition() {
        MenuComponent menuComponent = new MenuComponent(0, 0);
        assertEquals(0, menuComponent.getX());
        assertEquals(0, menuComponent.getY());
    }

    @Test
    void testGetComponentType() {
        MenuComponent menuComponent = new MenuComponent(0, 0);
        assertEquals(ComponentType.NONE, menuComponent.getType());
    }
}
