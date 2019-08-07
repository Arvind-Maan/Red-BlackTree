import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RBTreeViewer extends JPanel {

    private static final long serialVersionUID = 1500L;

    private RedBlackTree tree = new RedBlackTree();
    private RBTreePanel treePanel = new RBTreePanel(tree);

    public RBTreeViewer() {
        treePanel.setBackground(new Color(170, 170, 240));
        initViews();
    }

    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        JFrame j = new JFrame();
        j.setTitle("Red Black Tree Viewer");
        try {
            j.setIconImage(ImageIO.read(RBTreeViewer.class
                    .getResource("/resources/ic_binary.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.add(new RBTreeViewer());
        j.pack();
        j.setVisible(true);
    }

    private void setMidPoint(JScrollPane scrollPane) {
        scrollPane.getViewport().setViewPosition(new Point(4100, 0));

    }

    private void setTopPanel() {
        JLabel info = new JLabel("Red Black Tree by Arvind Maan");
        info.setForeground(new Color(150, 150, 150));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(200, 100, 100));
        panel.add(info);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(panel, BorderLayout.NORTH);
    }

    private void setBottomPanel() {
        final JTextField mTextField = new JTextField(5);
        final JButton btn_ins = new JButton();
        setupButton(btn_ins, "add");
        final JButton btn_clr = new JButton();
        setupButton(btn_clr, "clr");
        //add the buttons to the panel
        JPanel panel = new JPanel();
        panel.add(btn_ins);
        panel.add(mTextField);
        panel.add(btn_clr);
        panel.setBackground(Color.WHITE);
        add(panel, BorderLayout.SOUTH);

        btn_ins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (mTextField.getText().equals(""))
                    return;
                Integer toInsert = Integer.parseInt(mTextField.getText());
                if (tree.search(toInsert) != null) {
                    JOptionPane.showMessageDialog(null,
                            "Element is already present in the tree");
                } else {
                    tree.insert(toInsert);
                    treePanel.repaint();
                    mTextField.requestFocus();
                    mTextField.selectAll();
                }
            }
        });

        btn_clr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tree.resetTree();
                treePanel.repaint();
                mTextField.requestFocus();
                mTextField.selectAll();
            }
        });

        mTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btn_ins.doClick();
            }
        });
    }

    private void setScrollPane() {
        treePanel.setPreferredSize(new Dimension(750, 500));

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(treePanel);
        setMidPoint(scroll);
        add(scroll, BorderLayout.CENTER);
    }

    private void initViews() {
        super.setLayout(new BorderLayout());
        setScrollPane();
        setTopPanel();
        setBottomPanel();
    }

    private void setupButton(JButton button, String imgSrc) {
        try {
            Image icon = ImageIO.read(getClass().getResource(
                    "/resources/" + imgSrc + ".png"));
            button.setIcon(new ImageIcon(icon));
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
