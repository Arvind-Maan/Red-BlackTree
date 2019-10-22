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
        treePanel.setBackground(new Color(100, 100, 100));
        initViews();
    }

    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        } //end of try catch

        //create the viewer
        JFrame j = new JFrame();
        j.setTitle("Red Black Tree Viewer");
        try {
            j.setIconImage(ImageIO.read(RBTreeViewer.class
                    .getResource("/resources/ic_binary.png")));
        } catch (IOException e) {
            e.printStackTrace();
        } //end of try-catch
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.add(new RBTreeViewer());
        j.pack();
        j.setVisible(true);
    } //end of main

    private void setMidPoint(JScrollPane scrollPane) {
        scrollPane.getViewport().setViewPosition(new Point(4100, 0));
    }

    /****************************************************
     * setTopPanel
     * creates the header
     ***************************************************/
    private void setTopPanel() {
        JLabel info = new JLabel("Red Black Tree by Arvind Maan");
        info.setForeground(new Color(82, 11, 0));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0));
        panel.add(info);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(panel, BorderLayout.NORTH);
    } //end of setTopPanel

    /****************************************************
     * setBottomPanel
     * creates the footer
     ***************************************************/
    private void setBottomPanel() {
        //inputs
        final JTextField mTextField = new JTextField(5);
        final JButton btn_ins = new JButton();
        setupButton(btn_ins, "add");
        final JButton btn_clr = new JButton();
        setupButton(btn_clr, "clr");
        //add the buttons to the panel
        JPanel panel = new JPanel();
        panel.add(btn_ins);
        mTextField.setBackground(new Color(82,11,0));
        panel.add(mTextField);
        panel.add(btn_clr);
        panel.setBackground(new Color(60, 60, 60));
        add(panel, BorderLayout.SOUTH);
        //when insert is clicked: check if we can insert.
        btn_ins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (mTextField.getText().equals("")) //if trying to insert nothing, break
                    return;
                Integer toInsert = Integer.parseInt(mTextField.getText()); //get the insert value
                if (tree.search(toInsert) != null) {
                    JOptionPane.showMessageDialog(null,
                            "Element is already present in the tree");
                } else {
                    tree.insert(toInsert);
                    treePanel.repaint();
                    mTextField.requestFocus();
                    mTextField.selectAll();
                } //end of if
            } //end of action performed
        }); //end of insert
        //when clear is clicked
        //clear the tree
        btn_clr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tree.resetTree();
                treePanel.repaint();
                mTextField.requestFocus();
                mTextField.selectAll();
            } //end of actionperformed
        }); //end of clear
        //the text field for the input
        mTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btn_ins.doClick(); //insert it
            } //end of actionperformed
        }); //end of text field
    } //end of bottom panel

    private void setScrollPane() {
        treePanel.setPreferredSize(new Dimension(750, 500));

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(treePanel);
        setMidPoint(scroll);
        add(scroll, BorderLayout.CENTER);
    } //end of scroll pane

    /****************************************************
     * initViews
     * initialize the viewer
     ***************************************************/
    private void initViews() {
        super.setLayout(new BorderLayout());
        setScrollPane();
        setTopPanel();
        setBottomPanel();
    } //end of initViews

    /****************************************************
     * setupButton
     *
     * creates the button
     ***************************************************/
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
        } //end of try catch
    } //end of setUpButton
} //end of viewer
