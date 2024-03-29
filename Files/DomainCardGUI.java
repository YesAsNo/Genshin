package Files;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class DomainCardGUI extends JFrame implements MouseListener {
    private final DomainTabGUI.domainTheme domainTheme;
    private final String domainName;
    private final List<String> domainMaterials;
    private final String dayFilter;
    private final JPanel mainPanel = new JPanel(new GridBagLayout());
    public DomainCardGUI(DomainTabGUI.domainTheme dt, String domainName, List<String> domainMaterials, String dayFilter) {
        domainTheme = dt;
        this.domainName = domainName;
        this.domainMaterials = domainMaterials;
        this.dayFilter = dayFilter;
        setTitle(domainName + " Overview");
        setContentPane(generateDomainCard());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    private JPanel generateDomainCard(){
        return mainPanel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        mousePressed(e);
    }

    @Override
    public void mousePressed(MouseEvent event){
        System.out.println("1235");
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
