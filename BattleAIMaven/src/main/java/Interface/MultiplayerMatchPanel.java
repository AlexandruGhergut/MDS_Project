package Interface;

import Client.ConnectionHandler;
import Console.ConsoleFrame;
import Editor.Source;
import Editor.SourceManager;
import Networking.Requests.AddPlayer;
import Networking.Requests.ChatMessage;
import Networking.Requests.RemovePlayer;
import Networking.Requests.Request;
import Networking.Requests.RequestType;
import Networking.Requests.SourceFileTransfer;
import Networking.Server.ClientServerDispatcher;
import Networking.Server.Match;
import Networking.Server.Player;
import Visual.VisualEngine;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Dragos-Alexandru
 */
public class MultiplayerMatchPanel extends javax.swing.JPanel {

    MainFrame rootFrame;
    
    private final List<Source> sourceList;
    private Source selectedSource;
    private final DefaultListModel listModel = new DefaultListModel();
    private final DefaultListModel playerSelectionModel = new DefaultListModel();
    private boolean ready = false;
    
    private Boolean listenForRequests = false;
    private LinkedList<Request> requestsList = new LinkedList<>();
    
    /**
     * Creates new form MultiplayerMatchPanel
     * @param rootFrame
     * @param currentMatch
     */
    public MultiplayerMatchPanel(MainFrame rootFrame, Match currentMatch) {
        initComponents();
        this.selectButton.setFocusable(false);
        this.readyButton.setFocusable(false);
        this.chatOutputArea.setEditable(false);
        this.chatOutputArea.setLineWrap(true);
        this.chatOutputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        DefaultCaret caret = (DefaultCaret)this.chatOutputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        this.serverNameLabel.setText(rootFrame.localServerName);
        this.rootFrame = rootFrame;
        
        listAvailableScripts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sourceList = SourceManager.getInstance().getSourceList();
        if (!ConnectionHandler.getInstance().isHost()) {
            for(String player:currentMatch.getPlayerList()){
                playerSelectionModel.addElement(player);
            }
        }
        listPlayersAndScripts.setModel(playerSelectionModel);
        for(Source source:sourceList){
            listModel.addElement(source.toListString());
        }
        listAvailableScripts.setModel(listModel);
        try{
            listAvailableScripts.setSelectedIndex(0);
        }catch(IndexOutOfBoundsException ex){}
        
        ListenerWorker worker = new ListenerWorker(listenForRequests);
        try {
            worker.execute();
        } catch (Exception ex) {
            ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Failed to listen for requests");
            ConsoleFrame.showError("Failed to listen for requests");
        }
        
        chatInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(e.getKeyChar() == 10){
                    sendButtonActionPerformed(null);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serverNameLabel = new javax.swing.JLabel();
        scrollAvailable = new javax.swing.JScrollPane();
        listAvailableScripts = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        scrollPlayersAndScripts = new javax.swing.JScrollPane();
        listPlayersAndScripts = new javax.swing.JList();
        selectButton = new javax.swing.JButton();
        readyButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        chatOutputScroll = new javax.swing.JScrollPane();
        chatOutputArea = new javax.swing.JTextArea();
        chatInputField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(600, 400));
        setMinimumSize(new java.awt.Dimension(600, 400));

        serverNameLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        serverNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        serverNameLabel.setText("Server name");

        listAvailableScripts.setMaximumSize(new java.awt.Dimension(40, 80));
        listAvailableScripts.setMinimumSize(new java.awt.Dimension(40, 80));
        scrollAvailable.setViewportView(listAvailableScripts);

        jLabel2.setText("Available AI scripts");

        jLabel3.setText("Player status");

        listPlayersAndScripts.setMaximumSize(new java.awt.Dimension(40, 80));
        listPlayersAndScripts.setMinimumSize(new java.awt.Dimension(40, 80));
        scrollPlayersAndScripts.setViewportView(listPlayersAndScripts);

        selectButton.setBackground(new java.awt.Color(102, 255, 102));
        selectButton.setText("Select");
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        readyButton.setBackground(new java.awt.Color(255, 255, 0));
        readyButton.setText("Ready");
        readyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readyButtonActionPerformed(evt);
            }
        });

        startButton.setBackground(new java.awt.Color(153, 153, 255));
        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        chatOutputArea.setColumns(20);
        chatOutputArea.setRows(5);
        chatOutputScroll.setViewportView(chatOutputArea);

        chatInputField.setText("Hello");

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        backButton.setBackground(new java.awt.Color(255, 255, 255));
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(scrollAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chatInputField)
                                .addGap(18, 18, 18)
                                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(chatOutputScroll, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(backButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(selectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(readyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(serverNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 1, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPlayersAndScripts, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollPlayersAndScripts, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(serverNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(selectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(readyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addComponent(chatOutputScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chatInputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sendButton)))
                    .addComponent(scrollAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        int index = listAvailableScripts.getSelectedIndex();
        if(selectedSource != null){
            playerSelectionModel.removeElement(Player.getInstance().getUsername()+" / "+selectedSource.getName());
        }
        try{
            selectedSource = sourceList.get(index);
            playerSelectionModel.addElement(Player.getInstance().getUsername()+" / "+selectedSource.getName());
            listPlayersAndScripts.setModel(playerSelectionModel);
            ConnectionHandler.getInstance().sendToMatch(new SourceFileTransfer(selectedSource));
        }catch(IndexOutOfBoundsException ex){
            ConsoleFrame.showError("Select script, please");
        } catch (IOException ex) {
            ConsoleFrame.showError("Cannot send source file content to server.");
        }
    }//GEN-LAST:event_selectButtonActionPerformed

    private void readyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readyButtonActionPerformed
        if(ready){
            ready = false;
            readyButton.setBackground(Color.YELLOW);
        }else{
            ready = true;
            readyButton.setBackground(Color.BLUE);
        }
    }//GEN-LAST:event_readyButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        
        // Check if the user has selected a source file
        String username = Player.getInstance().getUsername();
        AbstractMap playersSourcesMap = ClientServerDispatcher.getInstance().getSourceFilesMap();
        if (playersSourcesMap.get(username) == null) {
            ConsoleFrame.showError("You must select a source file for your robot.");
            return;
        }
        
        if (ConnectionHandler.getInstance().isHost()) {
            int playersCount = ClientServerDispatcher.getInstance()
                    .getActiveMatch().getNumberOfPlayers();
            
            // Check if all players have selected a source file
            if (playersSourcesMap.size() != playersCount) {
                ConsoleFrame.showError("There are still players with no source file selected.");
                return;
            }
            
            setWorkerStatus(false);
            List<Source> playersSources = new LinkedList(playersSourcesMap.values());
            VisualEngine.getInstance(playersSources).setVisible(true);
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        String input = "";
        
        if (!chatInputField.getText().equals("")) {
            try {
                input = Player.getInstance().getUsername() + ": " + 
                        chatInputField.getText() + "\n";
                ConnectionHandler.getInstance().sendToMatch(new ChatMessage(input));
            } catch (IOException ex) {
                Logger.getLogger(MultiplayerMatchPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            chatInputField.setText("");
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        ConnectionHandler.getInstance().disconnectFromMatch();
        setWorkerStatus(false);
        rootFrame.changePanel(new MultiplayerServerPanel(rootFrame));
    }//GEN-LAST:event_backButtonActionPerformed

    /**
     * This worker listens for requests given by the match server
     */
    public class ListenerWorker extends SwingWorker<Void,List<Request>>{
        
        private final Boolean continueToListen;
        
        public ListenerWorker(Boolean continueToListen){
            this.continueToListen = continueToListen;
        }
        
        /**
         * Continues to listen for requests from match server
         * @return 
         */
        @Override
        protected Void doInBackground(){
            
            boolean listen = true;
            
            while(listen){
                
                try {
                    Request request = (Request)ConnectionHandler.getInstance().readFromMatch();
                    
                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                switch (request.getType()) {
                                    case RequestType.CHAT_MESSAGE:
                                        chatOutputArea.append(((ChatMessage)request).getMessage());
                                        break;
                                    case RequestType.ADD_PLAYER:
                                        playerSelectionModel.addElement(((AddPlayer)request).getUsername());
                                        break;
                                    case RequestType.REMOVE_PLAYER:
                                        playerSelectionModel.removeElement(((RemovePlayer)request).getUsername());
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });
                    
                } catch (IOException | ClassNotFoundException ex) {
                    ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Failed to read from match");
                    listen = false;
                }
            }
            return null;
        }
        
        /**
         * Checks if worker should stop
         * @return 
         */
        private boolean checkStatus(){
            boolean shouldContinue;
            
            synchronized(continueToListen){
                shouldContinue = continueToListen;
            }
            
            return shouldContinue;
        }
    }
    
    /**
     * Sets the worker to stop or continue listening
     * @param value 
     */
    private void setWorkerStatus(boolean value){
        synchronized(listenForRequests){
            listenForRequests = value;
        }
        
    }
    
    /**
     * Returns the next request that must be processed
     * @return
     * @throws NoSuchElementException 
     */
    private Request getRequest() throws NoSuchElementException{
        Request newRequest;
        synchronized(requestsList){
            newRequest = requestsList.pop();
        }
        return newRequest;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JTextField chatInputField;
    private javax.swing.JTextArea chatOutputArea;
    private javax.swing.JScrollPane chatOutputScroll;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList listAvailableScripts;
    private javax.swing.JList listPlayersAndScripts;
    private javax.swing.JButton readyButton;
    private javax.swing.JScrollPane scrollAvailable;
    private javax.swing.JScrollPane scrollPlayersAndScripts;
    private javax.swing.JButton selectButton;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel serverNameLabel;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
