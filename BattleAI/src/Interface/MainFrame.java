package Interface;

import Constants.FrameConstants;
import Visual.VisualEngineWrapper;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Dragos-Alexandru
 */
public class MainFrame extends JFrame implements FrameConstants{
    
    public static MainFrame instance;
    
    /**
     * Frame-ul principal al programului
     */
    private MainFrame(){
        super("BattleAI");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        
        this.add(new MainMenuPanel(this));
    }
    
    public static MainFrame getInstance(){
        if(instance == null){
            instance = new MainFrame();
            instance.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    if(VisualEngineWrapper.initialized()){
                        //telling the engine to close with the window
                        VisualEngineWrapper.getInstance(instance).exit();
                    }
                    super.windowClosing(e);
                }

            });  //need to tell the engine the window is closing);
        }
        return instance;
    }
    
    public void changePanel(JPanel panel){
        this.getContentPane().removeAll();
        this.getContentPane().add(panel);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }
}