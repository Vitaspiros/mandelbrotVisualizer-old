import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        App.plotter.zoom(e);
    }
}
