
import com.mysql.jdbc.PreparedStatement;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Dell
 */
public class AddAlbumForm extends javax.swing.JFrame {

    private String filename = null;
    byte[] person_image = null;
    private Album album = null;
    private final dbConnection db;
    private int editMode = 0;
    private int accountId = 0;
    /**
     * Creates new form AddSingerForm
     */
    public AddAlbumForm() {
        initComponents();
        setSize(500,650);
        db = new dbConnection();
        this.editMode=0;
    }
    public AddAlbumForm(int accountId) {
        this();
        this.accountId=accountId;
        setSingerBox();
    }
    
    public AddAlbumForm(int accountId, Album album) {
        this(accountId);
        this.editMode=1;
        addButton.setText("edit");
        jLabel1.setText("Edit album");
        
        this.album = album;
        this.person_image=this.album.getPhoto();
        String singerName = album.getSinger().getId() + " - " + album.getSinger().getName();
        
        nameField.setText(this.album.getName());
        singerBox.setSelectedItem(singerName);
        labelField.setText(this.album.getLabel());
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(this.person_image).getImage().getScaledInstance(lbl_image.getWidth(),lbl_image.getHeight(),Image.SCALE_SMOOTH));
        lbl_image.setIcon(imageIcon);
    }
    
    public void insertData(){
        String name = nameField.getText();
        String singer = "";
        int singerId = -1;
        if(singerBox.getSelectedItem()!=null){
            singer = singerBox.getSelectedItem().toString();
            singerId = strToInt(singer);
        }
        String label = labelField.getText();
        
        
        if(!name.isEmpty() && !singer.isEmpty() && !label.isEmpty() && !singer.equals("") && person_image!=null){
            String sql = "INSERT INTO album(id_singer,name,label,photo,id_account) VALUES(?,?,?,?,?)";
            
            PreparedStatement pst = db.getPrepStmt(sql);
            try {
                pst.setInt(1,singerId);
                pst.setString(2,name);
                pst.setString(3,label);
                pst.setBytes(4, this.person_image);
                pst.setInt(5,this.accountId);
                pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(AddSingerForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            sql = "UPDATE singer SET album_count=album_count+1 WHERE id = " + singerId;
            db.updateQuery(sql);
            
            HomeForm singerForm = new HomeForm(accountId, "album");
            singerForm.setVisible(true);
            this.setVisible(false);
        }
    }
    
    public void updateData(){
        String name = nameField.getText();
        String singer = singerBox.getSelectedItem().toString();
        int singerId = strToInt(singer);
        String label = labelField.getText();
        if(!name.isEmpty() && !singer.isEmpty() && !label.isEmpty() && person_image!=null){
            String sql = "UPDATE album SET name=?, id_singer=?, label=?, photo=? WHERE id=?";
            
            PreparedStatement pst = db.getPrepStmt(sql);
            try {
                pst.setString(1,name);
                pst.setInt(2,singerId);
                pst.setString(3,label);
                pst.setBytes(4, person_image);
                pst.setInt(5,this.album.getId());
                pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(AddSingerForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            sql = "UPDATE singer SET album_count=album_count+1 WHERE id = "+singerId;
            db.updateQuery(sql);
            sql = "UPDATE singer SET album_count=album_count-1 WHERE id = "+this.album.getId_singer();
            db.updateQuery(sql);
            
            HomeForm singerForm = new HomeForm(accountId,"album");
            singerForm.setVisible(true);
            this.dispose();
        }
    }
    
    public void resetForm(){
        nameField.setText("");
        if(singerBox.getSelectedItem()!=null){
            singerBox.setSelectedIndex(0);
        }
        labelField.setText("");
        lbl_image.setIcon(null);
        this.person_image=null;
    }
    
    public final void setSingerBox(){
        this.singerBox.removeAllItems();
        ResultSet res = db.selectQuery("SELECT id,name FROM singer WHERE id_account="+this.accountId);
        try {
            while(res.next()){
                int id = res.getInt("id");
                String name = res.getString("name");
                this.singerBox.addItem (Integer.toString(id) + " - " + name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int strToInt(String str){
        int num=0;
        int i=0;
        while(Character.isDigit(str.charAt(i))){
            num*=10;
            num+=Character.getNumericValue(str.charAt(i));
            i++;
        }
        return num;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        labelField = new javax.swing.JTextField();
        backButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        lbl_image = new javax.swing.JLabel();
        btn_image = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        singerBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Add New Album");

        jLabel2.setText("Name:");

        jLabel3.setText("Label:");

        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        addButton.setText("Add");
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        btn_image.setText("Choose");
        btn_image.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_imageMouseClicked(evt);
            }
        });

        jLabel4.setText("Singer:");

        jLabel5.setText("Photo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(112, 112, 112)
                                        .addComponent(resetButton))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(43, 43, 43)
                                        .addComponent(lbl_image, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addButton)
                                    .addComponent(btn_image)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(singerBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addComponent(labelField))))))
                .addContainerGap(143, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(backButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(singerBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btn_image))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(2, 2, 2)
                        .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetButton)
                    .addComponent(addButton))
                .addContainerGap(177, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        HomeForm singerForm = new HomeForm(accountId,"album");
        singerForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        switch(this.editMode){
            case 0:
                insertData();
                break;
            case 1:
                updateData();
                break;
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
        resetForm();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void btn_imageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_imageMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int res = chooser.showOpenDialog(null);
        if(res==chooser.APPROVE_OPTION){
            File f = chooser.getSelectedFile();
            filename = f.getAbsolutePath();

            ImageIcon imageIcon;
            imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lbl_image.getWidth(),lbl_image.getHeight(),Image.SCALE_SMOOTH));
            lbl_image.setIcon(imageIcon);
            try{
                File image = new File(filename);
                FileInputStream fis = new FileInputStream(image);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                for(int readNum;(readNum=fis.read(buf))!=-1;){
                    bos.write(buf, 0, readNum);
                }
                person_image = bos.toByteArray();
            }catch(IOException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
    }//GEN-LAST:event_btn_imageMouseClicked

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddSingerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddSingerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddSingerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddSingerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddSingerForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton backButton;
    private javax.swing.JButton btn_image;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField labelField;
    private javax.swing.JLabel lbl_image;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton resetButton;
    private javax.swing.JComboBox<String> singerBox;
    // End of variables declaration//GEN-END:variables
}
