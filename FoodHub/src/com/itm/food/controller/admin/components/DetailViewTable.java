package com.itm.food.controller.admin.components;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class DetailViewTable {
	
	private static final Logger log = Logger.getLogger(DetailViewTable.class);
	
	//TABLE VIEW AND DATA
	@SuppressWarnings("rawtypes")
	private ObservableList<ObservableList> data;
	@SuppressWarnings("rawtypes")
	private TableView tableview=new TableView();
	ResultSet rs=null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	 public TableView viewList(ResultSet rs) {
		 data = FXCollections.observableArrayList();
		 
        try{
         
        	this.rs=rs;
          /**********************************
           * TABLE COLUMN ADDED DYNAMICALLY *
           **********************************/
          for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
              //We are using non property style for making dynamic table
              final int j = i;                
              TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
              col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                  
           	   public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                      return new SimpleStringProperty(param.getValue().get(j).toString());                        
                  }                    
              });
             
              tableview.getColumns().addAll(col); 
          }

          /**********************************
           * ROW DATA ADDED TO OBSERVABLELIST*
           **********************************/
          while(rs.next()){
              //Iterate Row
              ObservableList<String> row = FXCollections.observableArrayList();
              for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                  //Iterate Column
                  row.add(rs.getString(i));
              }
              data.add(row);

          }

          //FINALLY ADDED TO TableView
          tableview.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            log.debug("Error on Building Data");             
        }
        tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      
       return tableview;
	 }
}
