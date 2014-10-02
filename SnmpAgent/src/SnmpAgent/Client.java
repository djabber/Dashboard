
package SnmpAgent;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;


/*
 * Client.java
 *
 * 
 */

/**
 *
 * @author Manos
 */
public class Client extends javax.swing.JFrame {
    String targetIp;
    String param, value;
    String toSet = "No connection";
    udpc myUdpc = new udpc();

    /** Creates new form Client */
    public Client() {
        initComponents();

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        agentIp = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        systemName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        systemContact = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        interfaceNumber = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tcpIncoming = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tcpRetransmitted = new javax.swing.JTextField();
        tcpEstablished = new javax.swing.JTextField();
        getSysName = new javax.swing.JButton();
        setSysName = new javax.swing.JButton();
        getSysContact = new javax.swing.JButton();
        setSysContact = new javax.swing.JButton();
        getIfNumber = new javax.swing.JButton();
        getTcpInSeg = new javax.swing.JButton();
        getTcpRetrans = new javax.swing.JButton();
        getTcpEstabl = new javax.swing.JButton();
        label = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Snmp Manager");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setText("Agent IP");

        agentIp.setFont(new java.awt.Font("Arial", 1, 14));
        agentIp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agentIpActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel2.setText("System Name");

        systemName.setFont(new java.awt.Font("Arial", 1, 14));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel3.setText("System Contact");

        systemContact.setFont(new java.awt.Font("Arial", 1, 14));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel4.setText("Interface Number");

        interfaceNumber.setEditable(false);
        interfaceNumber.setFont(new java.awt.Font("Arial", 1, 14));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel5.setText("TCP Incoming Segments");

        tcpIncoming.setEditable(false);
        tcpIncoming.setFont(new java.awt.Font("Arial", 1, 14));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel6.setText("TCP Retransmitted Segments");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel7.setText("TCP Established Connections");

        tcpRetransmitted.setEditable(false);
        tcpRetransmitted.setFont(new java.awt.Font("Arial", 1, 14));

        tcpEstablished.setEditable(false);
        tcpEstablished.setFont(new java.awt.Font("Arial", 1, 14));

        getSysName.setFont(new java.awt.Font("Arial", 1, 14));
        getSysName.setText("Get");
        getSysName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getSysNameActionPerformed(evt);
            }
        });

        setSysName.setFont(new java.awt.Font("Arial", 1, 14));
        setSysName.setText("Set");
        setSysName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setSysNameActionPerformed(evt);
            }
        });

        getSysContact.setFont(new java.awt.Font("Arial", 1, 14));
        getSysContact.setText("Get");
        getSysContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getSysContactActionPerformed(evt);
            }
        });

        setSysContact.setFont(new java.awt.Font("Arial", 1, 14));
        setSysContact.setText("Set");
        setSysContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setSysContactActionPerformed(evt);
            }
        });

        getIfNumber.setFont(new java.awt.Font("Arial", 1, 14));
        getIfNumber.setText("Get");
        getIfNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getIfNumberActionPerformed(evt);
            }
        });

        getTcpInSeg.setFont(new java.awt.Font("Arial", 1, 14));
        getTcpInSeg.setText("Get");
        getTcpInSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getTcpInSegActionPerformed(evt);
            }
        });

        getTcpRetrans.setFont(new java.awt.Font("Arial", 1, 14));
        getTcpRetrans.setText("Get");
        getTcpRetrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getTcpRetransActionPerformed(evt);
            }
        });

        getTcpEstabl.setFont(new java.awt.Font("Arial", 1, 14));
        getTcpEstabl.setText("Get");
        getTcpEstabl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getTcpEstablActionPerformed(evt);
            }
        });

        label.setFont(new java.awt.Font("Arial", 2, 14));
        label.setForeground(new java.awt.Color(0, 153, 153));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel8.setText("Notifications about operation");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(agentIp, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(24, 24, 24)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(systemName, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(systemContact, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(interfaceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tcpIncoming, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(getTcpInSeg)
                                    .addComponent(getIfNumber)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(getSysName)
                                        .addGap(18, 18, 18)
                                        .addComponent(setSysName))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(getSysContact)
                                        .addGap(18, 18, 18)
                                        .addComponent(setSysContact))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tcpRetransmitted, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(getTcpRetrans))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tcpEstablished, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(getTcpEstabl))
                            .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(agentIp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(systemName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getSysName)
                    .addComponent(setSysName))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(systemContact, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getSysContact)
                    .addComponent(setSysContact))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(interfaceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getIfNumber))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tcpIncoming, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getTcpInSeg))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tcpRetransmitted, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getTcpRetrans))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tcpEstablished, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getTcpEstabl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void agentIpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agentIpActionPerformed
        
    }//GEN-LAST:event_agentIpActionPerformed

    private void getSysNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getSysNameActionPerformed
        label.setText("");
        targetIp = agentIp.getText();
        param = "sysName";
        try {
            toSet = udpc.getRequest(param, targetIp);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(toSet.equals("No connection")) {
            label.setForeground(Color.red);
            label.setText(toSet);
            systemName.setText("");
        }
        else {
            systemName.setText(toSet);
            label.setForeground(Color.blue);
            label.setText("Value correctly retrieved");
        }
                
        toSet = "No connection";
    }//GEN-LAST:event_getSysNameActionPerformed

    private void getSysContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getSysContactActionPerformed
        label.setText("");
        targetIp = agentIp.getText();
        param = "sysContact";
        try {
            toSet = udpc.getRequest(param, targetIp);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(toSet.equals("No connection")) {
            label.setForeground(Color.red);
            label.setText(toSet);
            systemContact.setText("");
        }
        else {
            systemContact.setText(toSet);
            label.setForeground(Color.blue);
            label.setText("Value correctly retrieved");
        }
        
        toSet = "No connection";
    }//GEN-LAST:event_getSysContactActionPerformed

    private void getIfNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getIfNumberActionPerformed
        label.setText("");
        targetIp = agentIp.getText();
        param = "ifNumber";
        try {
            toSet = udpc.getRequest(param, targetIp);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(toSet.equals("No connection")) {
            label.setForeground(Color.red);
            label.setText(toSet);
            interfaceNumber.setText("");
        }
        else {
            interfaceNumber.setText(toSet);
            label.setForeground(Color.blue);
            label.setText("Value correctly retrieved");
        }
        
        toSet = "No connection";
    }//GEN-LAST:event_getIfNumberActionPerformed

    private void getTcpInSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getTcpInSegActionPerformed
        label.setText("");
        targetIp = agentIp.getText();
        param = "tcpIn";
        try {
            toSet = udpc.getRequest(param, targetIp);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(toSet.equals("No connection")) {
            label.setForeground(Color.red);
            label.setText(toSet);
            tcpIncoming.setText("");
        }
        else{
            tcpIncoming.setText(toSet);
            label.setForeground(Color.blue);
            label.setText("Value correctly retrieved");
        }
        
        toSet = "No connection";
    }//GEN-LAST:event_getTcpInSegActionPerformed

    private void getTcpRetransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getTcpRetransActionPerformed
        label.setText("");
        targetIp = agentIp.getText();
        param = "tcpRetrans";
        try {
            toSet = udpc.getRequest(param, targetIp);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(toSet.equals("No connection")) {
            label.setForeground(Color.red);
            label.setText(toSet);
            tcpRetransmitted.setText("");
        }
        else {
            tcpRetransmitted.setText(toSet);
            label.setForeground(Color.blue);
            label.setText("Value correctly retrieved");
        }
        toSet = "No connection";
    }//GEN-LAST:event_getTcpRetransActionPerformed

    private void getTcpEstablActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getTcpEstablActionPerformed
        label.setText("");
        targetIp = agentIp.getText();
        param = "tcpEstabl";
        try {
            toSet = udpc.getRequest(param, targetIp);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(toSet.equals("No connection")) {
            label.setForeground(Color.red);
            label.setText(toSet);
            tcpEstablished.setText("");
        }
        else {
            tcpEstablished.setText(toSet);
            label.setForeground(Color.blue);
            label.setText("Value correctly retrieved");
        }
        toSet = "No connection";
    }//GEN-LAST:event_getTcpEstablActionPerformed

    private void setSysNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setSysNameActionPerformed
        label.setText("");
        targetIp = agentIp.getText();
        param = "sysName";
        value = systemName.getText();
        try {
            toSet = udpc.setRequest(param, value, targetIp);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(toSet.equals("No connection")) {label.setForeground(Color.red);}
        else{label.setForeground(Color.blue);}
        label.setText(toSet);       
        toSet = "No connection";

    }//GEN-LAST:event_setSysNameActionPerformed

    private void setSysContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setSysContactActionPerformed
        label.setText("");
        targetIp = agentIp.getText();
        param = "sysContact";
        value = systemContact.getText();
        try {
            toSet = udpc.setRequest(param, value, targetIp);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(toSet.equals("No connection")) {label.setForeground(Color.red);}
        else{label.setForeground(Color.blue);}
        label.setText(toSet);
        toSet = "No connection";

    }//GEN-LAST:event_setSysContactActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField agentIp;
    private javax.swing.JButton getIfNumber;
    private javax.swing.JButton getSysContact;
    private javax.swing.JButton getSysName;
    private javax.swing.JButton getTcpEstabl;
    private javax.swing.JButton getTcpInSeg;
    private javax.swing.JButton getTcpRetrans;
    public static javax.swing.JTextField interfaceNumber;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label;
    private javax.swing.JButton setSysContact;
    private javax.swing.JButton setSysName;
    public static javax.swing.JTextField systemContact;
    public static javax.swing.JTextField systemName;
    public static javax.swing.JTextField tcpEstablished;
    public static javax.swing.JTextField tcpIncoming;
    public static javax.swing.JTextField tcpRetransmitted;
    // End of variables declaration//GEN-END:variables

}
