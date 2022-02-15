package code.fxutils.support.mvc.view.control;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;

public class SettingsFrame extends Stage {

    TableView<Map<?, ?>> tableView;

    BorderPane borderPane;
    HBox hBox;
    Scene scene;

    public SettingsFrame() {
        borderPane = new BorderPane();
        borderPane.setTop(hBox = new HBox());
        borderPane.setCenter(tableView = new TableView<>());
        setScene(scene = new Scene(borderPane));
        initUI();
    }

    private void initUI() {
        initTableView();
        Button btn = new Button("添加");
        TextField name = new TextField();
        TextField value = new TextField();
        hBox.getChildren().addAll(btn, name, value);
        btn.setOnAction(event -> {

        });
    }

    private void initTableView() {
        tableView.setCenterShape(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //设置自动拉满
        TableColumn<Map<?, ?>, String> name = new TableColumn<>("name");
        TableColumn<Map<?, ?>, String> value = new TableColumn<>("value");
        tableView.getColumns().add(name);
        tableView.getColumns().add(value);
        //Callback<CellDataFeatures<S,T>, ObservableValue<T>>
        //Callback<TableColumn.CellDataFeatures<Map,T>, ObservableValue<T>>
        name.setCellValueFactory(new MapValueFactory("name"));
        value.setCellValueFactory(new MapValueFactory("value"));
    }
}