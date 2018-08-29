package ChartsUI;

import javafx.fxml.Initializable;

public interface ChartUiController extends Initializable {

    void coordinateCalculation();
    void measurementBtn();
    void addDotToSeries();
    void setStartDistance();
    void loadGraphs();
    void saveGraphs();

}
