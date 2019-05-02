package javaFX2chartBar;

import java.net.URL;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ChartBarController {

	@FXML
	private VBox vbx;

	@FXML
	private Button btn;

	enum BarStyle {
		VNORMAL, HNORMAL, STACKED;
	}

	private BarStyle style = BarStyle.VNORMAL;

	private XYChart.Series<String, Number> b01series01 = new XYChart.Series<>();
	private XYChart.Series<String, Number> b01series02 = new XYChart.Series<>();
	private CategoryAxis b01xAxis = new CategoryAxis();
	private NumberAxis b01yAxis = new NumberAxis();
	private BarChart<String, Number> barc01 = new BarChart<>(b01xAxis, b01yAxis);

	private XYChart.Series<Number, String> b02series01 = new XYChart.Series<>();
	private XYChart.Series<Number, String> b02series02 = new XYChart.Series<>();
	private NumberAxis b02xAxis = new NumberAxis();
	private CategoryAxis b02yAxis = new CategoryAxis();
	private BarChart<Number, String> barc02 = new BarChart<>(b02xAxis, b02yAxis);

	private XYChart.Series<String, Number> sb01series01 = new XYChart.Series<>();
	private XYChart.Series<String, Number> sb01series02 = new XYChart.Series<>();
	private CategoryAxis sb01xAxis = new CategoryAxis();
	private NumberAxis sb01yAxis = new NumberAxis();
	private StackedBarChart<String, Number> sbarc01 = new StackedBarChart<>(sb01xAxis, sb01yAxis);

	@FXML
	void initialize() {
		assert vbx != null : "fx:id=\"vbx\" was not injected: check your FXML file 'BarChart.fxml'.";
		assert btn != null : "fx:id=\"btn\" was not injected: check your FXML file 'BarChart.fxml'.";

		//////////////////////////////
		// Normal Bar Chart (1)
		barc01.prefWidthProperty().bind(vbx.widthProperty());
		barc01.prefHeightProperty().bind(vbx.heightProperty());

		barc01.setTitle("Bar Chart Sample (1)");
		b01xAxis.setLabel("Country");
		b01yAxis.setLabel("Value");

		//////////////////////////////
		// Normal Bar Chart (2)
		barc02.prefWidthProperty().bind(vbx.widthProperty());
		barc02.prefHeightProperty().bind(vbx.heightProperty());

		barc02.setTitle("Bar Chart Sample (2)");
		b02xAxis.setLabel("Value");
		b02yAxis.setLabel("Country");

		//////////////////////////////
		// Stacked Bar Chart
		TreeSet<String> xAxisSet = new TreeSet<>();
		sbarc01.prefWidthProperty().bind(vbx.widthProperty());
		sbarc01.prefHeightProperty().bind(vbx.heightProperty());
		sbarc01.setCategoryGap(50);

		sbarc01.setTitle("Stacked Bar Chart Sample");
		sb01xAxis.setLabel("Cuntory");
		sb01yAxis.setLabel("Value");

		//////////////////////////////
		// set Data
		b01series01.setName("data01");
		b02series01.setName("data01");
		sb01series01.setName("data01");
		{
			URL url = this.getClass().getResource("res/data01.csv");
			OpCsv csv = new OpCsv(url);

			TreeMap<Integer, String[]> map = csv.getSortedCsv(0);
			Iterator<Integer> it = map.keySet().iterator();
			while (it.hasNext()) {
				int no = it.next();
				String[] words = map.get(no);
				String d01 = words[0];
				Double d02 = Double.parseDouble(words[1]);

				xAxisSet.add(d01);

				b01series01.getData().add(new XYChart.Data<String, Number>(d01, d02));
				b02series01.getData().add(new XYChart.Data<Number, String>(d02, d01));
				sb01series01.getData().add(new XYChart.Data<String, Number>(d01, d02));
			}
		}
		barc01.getData().add(b01series01);
		barc02.getData().add(b02series01);
		sbarc01.getData().add(sb01series01);

		b01series02.setName("data02");
		b02series02.setName("data02");
		sb01series02.setName("data02");
		{
			URL url = this.getClass().getResource("res/data02.csv");
			OpCsv csv = new OpCsv(url);

			TreeMap<Integer, String[]> map = csv.getSortedCsv(0);
			Iterator<Integer> it = map.keySet().iterator();
			while (it.hasNext()) {
				int no = it.next();
				String[] words = map.get(no);
				String d01 = words[0];
				Double d02 = Double.parseDouble(words[1]);

				xAxisSet.add(d01);

				b01series02.getData().add(new XYChart.Data<String, Number>(d01, d02));
				b02series02.getData().add(new XYChart.Data<Number, String>(d02, d01));
				sb01series02.getData().add(new XYChart.Data<String, Number>(d01, d02));
			}
		}
		barc01.getData().add(b01series02);
		barc02.getData().add(b02series02);
		sbarc01.getData().add(sb01series02);

		sb01xAxis.setCategories(FXCollections.<String> observableArrayList(xAxisSet));

		setStyleVNormalBar();
	}

	@FXML
	void btnOnAction(ActionEvent event) {
		switch (style) {
		case VNORMAL:
			setStyleHNormalBar();
			break;
		case HNORMAL:
			setStyleStackedBar();
			break;
		case STACKED:
			setStyleVNormalBar();
			break;
		default:
			break;
		}
	}

	private void setStyleVNormalBar() {
		style = BarStyle.VNORMAL;
		vbx.getChildren().clear();
		vbx.getChildren().add(barc01);
	}

	private void setStyleHNormalBar() {
		style = BarStyle.HNORMAL;
		vbx.getChildren().clear();
		vbx.getChildren().add(barc02);
	}

	private void setStyleStackedBar() {
		style = BarStyle.STACKED;
		vbx.getChildren().clear();
		vbx.getChildren().add(sbarc01);
	}
}
