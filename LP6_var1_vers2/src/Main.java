import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.util.ArrayList;

public class Main extends JComponent implements DragGestureListener, DragSourceListener, DropTargetListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    ArrayList<MyShape> leafs = new ArrayList<MyShape>();
    MyShape currentScribble;
    MyShape beingDragged;
    DragSource dragSource;
    boolean dragMode;

    static final int LINEWIDTH = 10;
    static final BasicStroke linestyle = new BasicStroke(LINEWIDTH);
    static final Border normalBorder = new BevelBorder(BevelBorder.LOWERED);
    static final Border dropBorder = new BevelBorder(BevelBorder.RAISED);


    public Main() {
        setBorder(normalBorder);

        addMouseListener(this);
        addMouseMotionListener(this);

        dragSource = DragSource.getDefaultDragSource();
        dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, this);

        DropTarget dropTarget = new DropTarget(this, this);
        this.setDropTarget(dropTarget);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new MyStroke(2));
        int numScribbles = leafs.size();
        for (int i = 0; i < numScribbles; ++i) {
            MyShape s = leafs.get(i);
            g2.draw(s);
        }
        g2.setStroke(linestyle);
    }

    public void setDragMode(boolean dragMode) {
        this.dragMode = dragMode;
    }

    public boolean getDragMode() {
        return dragMode;
    }

    public void mousePressed(MouseEvent e){
        if (dragMode) return;
        currentScribble = new MyShape(200, e.getX(), e.getY() + 200, 5 ,3);
        leafs.add(currentScribble);
        repaint();
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {
        if (dragMode) return;
    }

    public void mouseMoved(MouseEvent e) {}

    public void dragGestureRecognized(DragGestureEvent e) {
        if (!dragMode)
            return;

        MouseEvent inputEvent = (MouseEvent) e.getTriggerEvent();
        int x = inputEvent.getX();
        int y = inputEvent.getY();

        Rectangle r = new Rectangle(x - LINEWIDTH, y - LINEWIDTH, LINEWIDTH * 2, LINEWIDTH * 2);
        int numScribbles = leafs.size();
        for (int i = 0; i < numScribbles; i++) {
            MyShape s =  leafs.get(i);
            if (s.intersects(r)) {
                beingDragged = s;

                MyShape dragScribble = (MyShape)s.clone();
                dragScribble.translate(-x, -y);
                Cursor cursor;
                switch (e.getDragAction()) {
                    case DnDConstants.ACTION_COPY:
                        cursor = DragSource.DefaultCopyDrop;
                        break;
                    case DnDConstants.ACTION_MOVE:
                        cursor = DragSource.DefaultMoveDrop;
                        break;
                    default:
                        return;
                }
                e.startDrag(cursor, dragScribble, this);
                return;
            }
        }
    }

    public void dragDropEnd(DragSourceDropEvent e) {
        if (!e.getDropSuccess())
            return;
        int action = e.getDropAction();
        if (action == DnDConstants.ACTION_MOVE) {
            leafs.remove(beingDragged);
            beingDragged = null;
            repaint();
        }
    }

    public void dragEnter(DragSourceDragEvent e) {}
    public void dragExit(DragSourceEvent e) {}
    public void dropActionChanged(DragSourceDragEvent e) {}
    public void dragOver(DragSourceDragEvent e) {}

    public void dragEnter(DropTargetDragEvent e) {
        if (e.isDataFlavorSupported(MyShape.qDataFlavor) || e.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            e.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
            this.setBorder(dropBorder);
        }
    }

    public void dragExit(DropTargetEvent e)
    {
        this.setBorder(normalBorder);
    }

    public void drop(DropTargetDropEvent e) {
        this.setBorder(normalBorder);
        if (e.isDataFlavorSupported(MyShape.qDataFlavor) || e.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        } else {
            e.rejectDrop();
            return;
        }

        Transferable t = e.getTransferable();
        MyShape droppedScribble;

        try {
            droppedScribble = (MyShape) t.getTransferData(MyShape.qDataFlavor);
        } catch (Exception ex) {
            try {
                String s = (String) t.getTransferData(DataFlavor.stringFlavor);
                droppedScribble = MyShape.getFromString(s);
            } catch (Exception ex2) {
                e.dropComplete(false);
                return;
            }
        }

        Point p = e.getLocation();
        droppedScribble.translate((int) p.getX(), (int) p.getY());
        leafs.add(droppedScribble);
        repaint();
        e.dropComplete(true);
    }

    public void dragOver(DropTargetDragEvent e) {}

    public void dropActionChanged(DropTargetDragEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Frame 1");
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        final Main scribblePane = new Main();
        frame.getContentPane().add(scribblePane, BorderLayout.CENTER);

        JToolBar toolbar = new JToolBar();
        ButtonGroup group = new ButtonGroup();
        JToggleButton draw = new JToggleButton("Draw");
        JToggleButton drag = new JToggleButton("Drag");
        draw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                scribblePane.setDragMode(false);
            }
        });
        drag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                scribblePane.setDragMode(true);
            }
        });
        group.add(draw);
        group.add(drag);
        toolbar.add(draw);
        toolbar.add(drag);
        frame.getContentPane().add(toolbar, BorderLayout.NORTH);

        draw.setSelected(true);
        scribblePane.setDragMode(false);

        frame.setSize(640, 480);
        frame.setVisible(true);

        JFrame frame1 = new JFrame("Frame 2");
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        final Main scribblePane1 = new Main();
        frame1.getContentPane().add(scribblePane1, BorderLayout.CENTER);

        JToolBar toolbar1 = new JToolBar();
        ButtonGroup group1 = new ButtonGroup();
        JToggleButton draw1 = new JToggleButton("Draw");
        JToggleButton drag1 = new JToggleButton("Drag");
        draw1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scribblePane1.setDragMode(false);
            }
        });
        drag1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scribblePane1.setDragMode(true);
            }
        });
        group1.add(draw1);
        group1.add(drag1);
        toolbar1.add(draw1);
        toolbar1.add(drag1);
        frame1.getContentPane().add(toolbar1, BorderLayout.NORTH);

        draw1.setSelected(true);
        scribblePane1.setDragMode(false);

        frame1.setSize(640, 480);
        frame1.setVisible(true);
    }
}