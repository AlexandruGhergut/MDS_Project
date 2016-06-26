package Visual;

import Source.Source;
import Engine.GameEntity;
import Engine.IntelligenceControlThread;
import Enums.GameModes;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Liviu
 */
public class VisualEngine extends javax.swing.JFrame {

    /**
     * Creates new form VisualEngine
     */
    private GameModes matchMode = GameModes.SINGLEPLAYER;
    IntelligenceControlThread intelligenceControlThread;
    private List<Source> sursePrimite;

    private static VisualEngine instance;

    private VisualEngine() {
        initComponents();
    }

    private VisualEngine(List<Source> surse) {
        initComponents();
        this.sursePrimite = surse;
    }

    public static VisualEngine getInstance() {
        if (instance == null) {
            instance = new VisualEngine();
            instance.setLocationRelativeTo(null);
        }
        return instance;
    }

    public static VisualEngine getInstance(List<Source> surse) {
        if (instance == null) {
            instance = new VisualEngine(surse);
            instance.setLocationRelativeTo(null);
        }
        return instance;
    }

    public static boolean initialized() {
        return instance != null;
    }

    public void updateEntityList(ArrayList<GameEntity> newList) {
        visualPanel1.entityList = newList;
    }

    public void setMatchMode(GameModes matchMode) {
        this.matchMode = matchMode;
        visualPanel1.setGameMode(matchMode);
    }

    public GameModes getMatchMode() {
        return matchMode;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        visualPanel1 = new Visual.VisualPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(Constants.VisualConstants.ENGINE_TITLE);
        setResizable(false);
        setSize(new Dimension(Constants.VisualConstants.ENGINE_WIDTH, Constants.VisualConstants.ENGINE_HEIGHT));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout visualPanel1Layout = new javax.swing.GroupLayout(visualPanel1);
        visualPanel1.setLayout(visualPanel1Layout);
        visualPanel1Layout.setHorizontalGroup(
            visualPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        visualPanel1Layout.setVerticalGroup(
            visualPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(visualPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(visualPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void closeWindow() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        if (matchMode == GameModes.SINGLEPLAYER
                || matchMode == GameModes.MULTIPLAYER_HOST) {
            if (sursePrimite != null) {
                intelligenceControlThread = new IntelligenceControlThread(sursePrimite);
                intelligenceControlThread.start();
            }
        }
        visualPanel1.animator.start();   //starting the animator when the window is visible
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        visualPanel1.animator.stopAnimation();   //stopping the animator when the window is closing

        //this.sursePrimite.clear();
        if (matchMode != GameModes.MULTIPLAYER_CLIENT && intelligenceControlThread != null) {
            intelligenceControlThread.stopNicely();
        }

        instance = null;    //the form's close operation is DISPOSE, so there's no point in keeping the old instance around

    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Visual.VisualPanel visualPanel1;
    // End of variables declaration//GEN-END:variables
}
