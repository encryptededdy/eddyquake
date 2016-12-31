package zhang.nz;

/**
 * Created by Edward Zhang on 31/12/2016.
 */
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {
    int mmi;
    @FXML
    private ListView list;
    @FXML
    private Label details;
    @FXML
    private Label datainfobox;
    @FXML
    private Slider mmislide;
    public void initialize() {
        // memes
    }
    @FXML protected void showButton(ActionEvent event) throws Exception {
        mmi = (int)mmislide.getValue();
        String datainfo = GeoNet.getData(mmi);
        datainfobox.setText(datainfo);
        String[] quakelist = GeoNet.showQuakes();
        ObservableList<String> items = FXCollections.observableArrayList ();
        for (int i = 0; i < quakelist.length; i++) {
            items.add(quakelist[i]);
        }
        list.setItems(items);
        final int listLength = quakelist.length;
        list.scrollTo(listLength);
        list.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
                    @Override
                    public void onChanged(Change<? extends String> arg0){
                        int currentID = listLength - list.getSelectionModel().getSelectedIndex() - 1;
                        try {
                            String detailinfo = GeoNet.quakeDetail(currentID);
                            details.setText(detailinfo);
                        } catch (Exception e) {}
                    }
        });
    }
}


