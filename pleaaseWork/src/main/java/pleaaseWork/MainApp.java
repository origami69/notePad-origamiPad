package pleaaseWork;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
//origami
public class MainApp {
	private static File cool = null;
	public static void main(String[] args) {
		if(args.length>0) {
		try {
		File read = new File(args[0]);
		String bruh;
		bruh = Files.probeContentType(read.toPath());
		if(bruh.equals("text/plain")) {
			cool = read;
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		Application.launch(RealApp.class);
	}
public static class RealApp extends Application {
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle("Untitled - origamiPad");
		 Menu menu1 = new Menu("File");
		 MenuBar menuBar = new MenuBar();
		 HBox lowerBar = new HBox(10);
		 lowerBar.setPadding(new Insets(5,0,5,0));
		 Label line = new Label("Ln: 1");
		 line.setPadding(new Insets(0,0,0,10));
		 Label column = new Label("Col: 1");
		 lowerBar.getChildren().addAll(line,column);
		 TextArea textArea = new TextArea();
		 URL lll = RealApp.class.getClassLoader().getResource("help.css");
		 textArea.getStylesheets().add(lll.toString());
		 textArea.setPrefHeight(400);  
		 menuBar.getMenus().add(menu1);
		 MenuItem files1 = new MenuItem("New");
		 MenuItem files2 = new MenuItem("Open");
		 MenuItem files3 = new MenuItem("Save");
		 MenuItem files4 = new MenuItem("Print");
		 menu1.getItems().addAll(files1,files2,files3,files4);
		// source for image: https://www.pixiv.net/en/artworks/87112224
		URL k = RealApp.class.getClassLoader().getResource("cute.jpg");
		Image image = new Image(k.toString());
		primaryStage.getIcons().add(image);
		 VBox alignShit = new VBox();
		 alignShit.getChildren().addAll(menuBar,textArea, lowerBar);
		StackPane layout =  new StackPane();
		layout.getChildren().add(alignShit);
		Scene scene = new Scene(layout, 600, 300);
		primaryStage.setScene(scene);
		if(cool != null) {
			try {
				primaryStage.setTitle(cool.getName() + " - origamiPad");
				Scanner s = new Scanner(cool);
				while (s.hasNextLine()) {
		                textArea.appendText(s.nextLine()+"\n"); 	                
				}
				s.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		//cancel close request
		primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e->{
		    e.consume();
		    if(textArea.getText() != ""){
		    Stage win = new Stage();
			win.getIcons().add(image);
		    win.initModality(Modality.APPLICATION_MODAL);
		    win.setTitle("OwO No Save!!");
		     win.setMinHeight(150);
		     win.setMinWidth(250);
		    Label sure = new Label();
		    sure.setText("Are you sure you want to exit");
		    HBox buttons = new HBox();
		    Button close = new Button("close");
		    Button cancel = new Button("cancel");
		    Button save = new Button("Save");
		    buttons.getChildren().addAll(close,cancel,save);
		    buttons.setAlignment(Pos.CENTER);
		    buttons.setPadding(new Insets(5,5,5,5));
		    HBox.setMargin(close, new Insets(2,0,0,2));
		    HBox.setMargin(cancel, new Insets(2,0,0,2));
		    HBox.setMargin(save, new Insets(2,0,0,2));
		    VBox lays= new VBox();
		    lays.getChildren().addAll(sure,buttons);
		    lays.setAlignment(Pos.CENTER);
			Scene scenes = new Scene(lays);
			win.setScene(scenes);
			win.show();
			close.setOnAction(ex -> {
		        Platform.exit();
		    });
		    cancel.setOnAction(ec -> {
		        win.close();
		    });
		    save.setOnAction(ed->{
		    	FileChooser fileChooser = new FileChooser();
		    	 fileChooser.setTitle("Save");
		         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
		         fileChooser.getExtensionFilters().add(extFilter);

		         File file = fileChooser.showSaveDialog(primaryStage);
		 
		         if (file != null) {
		           saver(textArea.getText(), file);
		           Platform.exit();
		         }
		         
		    });
		    }else {
		    	Platform.exit();
		    }
		});
		primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
			double lol = newVal.doubleValue()*0.9;
		    textArea.setPrefHeight(lol);
		});
		files2.setOnAction(e->{
			Stage setCh = new Stage();
			FileChooser fil_chooser = new FileChooser();
			fil_chooser.setTitle("Open a Text File");
			fil_chooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
			File file = fil_chooser.showOpenDialog(setCh);
			 if (file != null) {
				 try {
					primaryStage.setTitle(file.getName() + " - origamiPad");
					textArea.setText("");
					Scanner s = new Scanner(file);
					while (s.hasNextLine()) {
			                textArea.appendText(s.nextLine()+"\n"); 	                
					}
					s.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				 }
		});
		files1.setOnAction(e->{
			if(textArea.getText() != "") {
				Stage win = new Stage();
				win.getIcons().add(image);
			    win.initModality(Modality.APPLICATION_MODAL);
			    win.setTitle("OwO No Save!!");
			     win.setMinHeight(150);
			     win.setMinWidth(250);
			    Label sure = new Label();
			    sure.setText("Are you sure you dont want a save");
			    HBox buttons = new HBox();
			    Button close = new Button("Dont Save");
			    Button cancel = new Button("Cancel");
			    Button save = new Button("Save");
			    buttons.getChildren().addAll(close,cancel,save);
			    buttons.setAlignment(Pos.CENTER);
			    buttons.setPadding(new Insets(5,5,5,5));
			    HBox.setMargin(close, new Insets(2,0,0,2));
			    HBox.setMargin(cancel, new Insets(2,0,0,2));
			    HBox.setMargin(save, new Insets(2,0,0,2));
			    VBox lays= new VBox();
			    lays.getChildren().addAll(sure,buttons);
			    lays.setAlignment(Pos.CENTER);
				Scene scenes = new Scene(lays);
				win.setScene(scenes);
				win.show();
				close.setOnAction(ex -> {
					primaryStage.setTitle("Untitled - origamiPad");
					textArea.setText("");
			        win.close();
			    });
			    cancel.setOnAction(ec -> {
			        win.close();
			    });
			    save.setOnAction(ek->{
			    	 FileChooser fileChooser = new FileChooser();
			    	 fileChooser.setTitle("Save");
			         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
			         fileChooser.getExtensionFilters().add(extFilter);

			         File file = fileChooser.showSaveDialog(primaryStage);
			 
			         if (file != null) {
			           saver(textArea.getText(), file);
			         }
			         textArea.setText("");
			         primaryStage.setTitle("Untitled - origamiPad");
			         win.close();
			    });
			    			    
			}
		});
		files3.setOnAction(e->{
			FileChooser fileChooser = new FileChooser();
	    	 fileChooser.setTitle("Save");
	         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
	         fileChooser.getExtensionFilters().add(extFilter);

	         File file = fileChooser.showSaveDialog(primaryStage);
	 
	         if (file != null) {
	           primaryStage.setTitle(file.getName() + " - origamiPad");
	           saver(textArea.getText(), file);
	         }
		});
		files4.setOnAction(e->{
			 PrinterJob job = PrinterJob.createPrinterJob();
			 if (job != null) {
			    boolean success = job.printPage(textArea);
			    if (success) {
			        job.endJob();
			    }
			 }
		});
		textArea.caretPositionProperty().addListener(e->{
			ObservableList<CharSequence> li = textArea.getParagraphs(); 
			int caretPos = textArea.getCaretPosition();
			String subText = textArea.getText(0, caretPos);
			int rowNum = (int) subText.chars().filter(car -> car == '\n').count();
			int num1= rowNum+1;
			int num2 = li.get(rowNum).length()+1;
			line.setText("Ln: " + num1);
			column.setText("Col: " + num2);
		});

		primaryStage.show();
	}
	
	void saver(String content, File file) {
		try {
        FileWriter fileWriter;
        fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    } catch (IOException ex) {
        ex.fillInStackTrace();
    }
}
}
}
