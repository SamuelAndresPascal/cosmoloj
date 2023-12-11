package com.cosmoloj.util.java2d;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author Samuel Andrés
 */
public class ImageViewer extends Component {

    private final transient Image image;
    private double zoom;


    public ImageViewer(final Image image) {
        this.image = image;
        this.zoom = 1.;
        addMouseWheelListener((final MouseWheelEvent e) -> {
            if (e.getPreciseWheelRotation() < 0) {
                zoom = zoom * 2;
            } else {
                zoom = zoom / 2;
            }
            repaint();
        });
    }

    @Override
    public synchronized void paint(final Graphics g) {

        final Graphics2D g2D = (Graphics2D) g;
        g2D.scale(zoom, zoom);
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(null), image.getHeight(null));
    }

    public static void view(final List<Map.Entry<String, Image>> images) {

        final JFrame frame = new JFrame("Load Image Sample");
        frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(final WindowEvent e) {
                    System.exit(0);
                }
            });

        final JTabbedPane tabbedPane = new JTabbedPane();

        images.forEach(image -> tabbedPane.addTab(image.getKey(), new JScrollPane(new ImageViewer(image.getValue()))));

        frame.add(tabbedPane);
        frame.pack();
        frame.setVisible(true);
    }
}
