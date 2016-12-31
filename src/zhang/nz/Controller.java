package zhang.nz;

/**
 * Created by Edward Zhang on 31/12/2016.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {
    int mmi;
    @FXML
    private TextArea textbox;
    @FXML
    private Slider mmislide;
    public void initialize() {
        // memes
    }
    @FXML protected void showButton(ActionEvent event) throws Exception {
        mmi = (int)mmislide.getValue();
        String quakelist = GeoNet.showQuakes(mmi);
        textbox.setText(quakelist);
    }

}

