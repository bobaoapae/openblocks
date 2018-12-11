package edu.mit.blocks.codeblockutil;

import edu.mit.blocks.codeblockutil.CScrollPane.ScrollPolicy;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class CPopupMenu extends JPopupMenu implements ActionListener {

    private static final long serialVersionUID = 328149080311L;
    private int ITEM_HEIGHT = 14;
    private Color background = CGraphite.white;
    private JComponent scroll;
    private JComponent view;
    private double zoom = 1.0;
    private int items = 0;
    private int minWidth = 0;
    //private int MARGIN = 20;

    CPopupMenu() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(background);
        this.setOpaque(false);
        this.removeAll();
        this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, CGraphite.blue));
        view = new JPanel(new GridLayout(0, 1));
        view.setBackground(background);
        scroll = new CTracklessScrollPane(view,
                ScrollPolicy.VERTICAL_BAR_AS_NEEDED,
                ScrollPolicy.HORIZONTAL_BAR_NEVER,
                9, CGraphite.blue, background);
        this.add(scroll);
    }

    @Override
    public Insets getInsets() {
        return new Insets(5, 5, 5, 5);
    }

    public void add(CMenuItem item) {
        items++;
        item.addActionListener(this);
        view.add(item);
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        int minWidth = (int) (this.getFont().getStringBounds(item.getText(), frc).getWidth());
        if (minWidth > this.minWidth) {
            this.minWidth = minWidth;
        }
    }

    public void setZoomLevel(double zoom) {
        this.zoom = zoom;
        // change the size of the panel and the text
        view.setPreferredSize(new Dimension((int) ((minWidth + 50) * this.zoom), (int) (items * ITEM_HEIGHT * this.zoom)));
        this.setPopupSize((int) ((minWidth + 50) * this.zoom), (int) (Math.min(items * ITEM_HEIGHT + 10, 100) * this.zoom));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.isVisible()) {
            this.setVisible(false);
        }
    }

}
