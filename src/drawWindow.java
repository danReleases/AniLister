import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.event.*;

public class drawWindow extends JComponent{

    private JTextField title;
    private JTextField episodes;
    
    private JComboBox days;
    
    private static String save_path="D://";
    private JTextField save_field;
    private static JLabel save_message;
    
    private DefaultListModel dbList=new DefaultListModel();
    private JScrollPane scrollDB;
    private JScrollBar vertical;
    
    private static JLabel clear_message;
    
    drawWindow()
    {
        draw_frame();
    }
    
    private void draw_frame()
    {
        JFrame window=new JFrame();
        window.setTitle("AniLister");
        window.setSize(640 ,480);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        title=addFields();
        title.addMouseListener(new MouseAdapter()
         {
            @Override
            public void mouseClicked(MouseEvent et)
            {
                if(title.getText().equals("Kindly enter a Name."))
                {
                    title.setText("");
                    title.setForeground(null);
                }           
            }
        });
        
        episodes=addFields();
        episodes.addMouseListener(new MouseAdapter()
         {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(episodes.getText().equals("Kindly enter a Number."))
                {
                    episodes.setText("");
                    episodes.setForeground(null);
                }           
            }
        });
         
        days=addDayList();
        JScrollPane scrolling=new JScrollPane(days);
        
        JList dbView=addDbView();
        scrollDB=new JScrollPane(dbView);
        scrollDB.setPreferredSize(new Dimension(24,42));
        
        vertical = scrollDB.getVerticalScrollBar();
        
        save_field=addFields();
        save_field.setText(save_path);
        save_message=addLabels("");
        save_message.setForeground(Color.BLUE);
        
        clear_message=addLabels("Saving will clear internal database.");
        clear_message.setForeground(Color.RED);
        
        JPanel pan1=new JPanel(new GridLayout(2,3,10,1));
        JPanel pan2=new JPanel(new FlowLayout(2));
        JPanel pan3=new JPanel(new FlowLayout(2));
        JPanel pan4=new JPanel(new GridLayout(2,3));
                
        pan1.add(addLabels("Title"));
        pan1.add(addLabels("Episodes"));
        pan1.add(addLabels("Air-Day")); 
        
        pan1.add(title);
        pan1.add(episodes);
        pan1.add(scrolling);
        
        pan2.add(addInsert());
        
        pan3.add(save_message);
        pan3.add(save_field);
        pan3.add(addSave());
        
        
        pan4.add(new JLabel(" "));
        pan4.add(scrollDB);
        pan4.add(clear_message);
        pan4.add(addClear());
    
        
        window.setLayout(new GridLayout(4,1));
        window.add(pan1);
        window.add(pan2);
        window.add(pan3);
        window.add(pan4);
        window.pack();
    }
    
    private JButton addInsert()
    {
        JButton b1=new JButton("Add to Database");
        
        b1.addActionListener((ActionEvent e) -> {
            
            clear_message.setText("");
            save_message.setText("");
            boolean[] logic=new boolean[2];
            logic[0]=title.getText().trim().isEmpty();
            try{
                Integer.parseInt(episodes.getText());
                logic[1]=true;
            }
            catch(NumberFormatException einput)
                    {
                        logic[1]=false;
                    }
            PROCESS_INPUT(logic);});
        return(b1);
    }
    
    private void PROCESS_INPUT(boolean[] logic){
        
        if(logic[0]==true||logic[1]==false){
            if(logic[0]==true){
                title.setForeground(Color.RED);
                title.setText("Kindly enter a Name."); 
            }
            if(logic[1]==false)
            {
                episodes.setForeground(Color.RED);
                episodes.setText("Kindly enter a Number.");
            }
        }
        else
        {
            databaseManage.AddElement(title.getText().trim(),Integer.parseInt(episodes.getText()),days.getSelectedIndex());
            days.setSelectedIndex(0);
            title.setText("");
            episodes.setText("");
            updateDbView();
        }
    }
    
    private JButton addSave(){
        
        JButton b1=new JButton("Save to /new_export.xlsx");
   
        b1.addActionListener((ActionEvent e) -> {
            clear_message.setText("");
            databaseManage.sort_db();
            save_path=save_field.getText();
            try{
                excel.excel_save_xlsx(databaseManage.database,save_path);
                clearAction();
            }
            catch(Exception ein)
            {
                //
            }          
        });
        
        return(b1);
    }
    
    private JTextField addFields()
    {
        JTextField temp_field=new JTextField();
        temp_field.setPreferredSize(new Dimension(320,32));
        temp_field.setVisible(true);
        return(temp_field);
    } 
    
     private JLabel addLabels(String s)
    {
        JLabel temp_label=new JLabel(s,SwingConstants.CENTER);
        temp_label.setPreferredSize(new Dimension(320,32));
        temp_label.setVisible(true);
        return(temp_label);
    } 
     
     private JComboBox addDayList()
     {
        return(new JComboBox(databaseManage.days.values()));
     }
     
     private JList addDbView()
     {
         
         JList temp_list=new JList(dbList);
         //set list to unselectable
         temp_list.setSelectionModel(new DefaultListSelectionModel(){

                @Override
                public void setSelectionInterval(int index0, int index1) {
                    super.setSelectionInterval(-1, -1);
                }});
         
         return(temp_list);
     }
     
     private void updateDbView()
     {
         dbList.addElement(databaseManage.database.get(databaseManage.database.size()-1).toString());
         scrollDB.validate();
         vertical.setValue(vertical.getMaximum());
     }
     
     public static void saveLabel(Boolean found)
     {
        if(found)
        {
            save_message.setText("Saved to: "+save_path+"new_export.xlsx");
        }
        else{
             save_message.setText("Error, saved to default: D://new_export.xlsx"); 
        }
     }
     private JButton addClear(){
        
        JButton b1=new JButton("Clear internal database");
   
        b1.addActionListener((ActionEvent e) -> {
           clear_message.setText("");
            clearAction();
        });
        
        return(b1);
    }
   
     private void clearAction()
     {
       dbList.clear();
       clear_message.setText("Cleared internal database.");
     }
}
