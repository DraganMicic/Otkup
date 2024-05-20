package StatistikaTab;

import Main.ComboBoxAutoComplete;
import Main.DateAxis310;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import model.*;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDate;

public class StatistikaTab extends VBox {

    private static StatistikaTab instance;

    private HBox UlaziHb;
    private HBox IzlaziHb;
    private HBox PiteHb;

    private HBox HBUlaziF;
    private ComboBox<Ulaz> CbUlaziF;
    private DatePicker DpUlaziPocetniF;
    private DatePicker DpUlaziKrajnjiF;
    private Button BPonistiUlazF;
    private LineChart<LocalDateTime, Number> UlazLineChart;

    private HBox HbIzlaziF;
    private ComboBox<Izlaz> Cbizlaz;
    private DatePicker DpIzlazzPocetniF;
    private DatePicker DpIzlazKrajnjiF;
    private Button BPonistiIzlazF;
    private LineChart<LocalDateTime, Number> IzlazLineChart;

    private VBox grafikUlaza;
    private VBox GrafikIzlaza;

    private VBox pitaUlazaHB;
    private VBox pitaIzlazaHB;

    private PieChart pieChart1;

    private PieChart pieChart2;

    private PieChart pieChart3;
    private PieChart pieChart4;
    private PieChart pieChart5;


    private StatistikaTab(){

        setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
        setSpacing(15);
        this.setMinHeight(Screen.getPrimary().getBounds().getHeight()-80);

        //naslov 1
        Label naslov = new Label("Statistika ulaza:");
        naslov.setFont(new Font(35));
        Separator separator = new Separator();
        this.getChildren().addAll(naslov,separator);

        //traka za selekciju ulaza
        HBUlaziF = new HBox(10);
        HBUlaziF.setStyle("-fx-font: 17px \"Serif\";");
        HBUlaziF.setAlignment(Pos.BASELINE_LEFT);
        ulazopcije();

        //grafik ulaza i pita ulaza
        grafikUlaza = new VBox();
        grafikUlaza.getChildren().add(HBUlaziF);
        pitaUlazaHB = new VBox();
        Label l1 = new Label("Odnos svih ulaza:");
        l1.setStyle("-fx-font: 17px \"Serif\";");
        pitaUlazaHB.getChildren().add(l1);

        UlaziHb = new HBox(40);
        UlaziHb.setStyle("-fx-font: 17px \"System\";");
        Separator s1 = new Separator();
        s1.setOrientation(Orientation.VERTICAL);
        UlaziHb.getChildren().addAll(grafikUlaza,s1,pitaUlazaHB);
        this.getChildren().add(UlaziHb);
        tabelaipitaulaza();

        //naslov 2
        Label naslov2 = new Label("Statistika izlaza:");
        naslov2.setFont(new Font(35));
        Separator separator2 = new Separator();
        this.getChildren().addAll(naslov2,separator2);

        //traka za selekciju izlaza
        HbIzlaziF = new HBox(10);
        HbIzlaziF.setStyle("-fx-font: 17px \"Serif\";");
        HbIzlaziF.setAlignment(Pos.BASELINE_LEFT);
        izlaziopcije();

        //grafik ulaza i pita izlaza
        GrafikIzlaza = new VBox();
        GrafikIzlaza.getChildren().add(HbIzlaziF);
        pitaIzlazaHB = new VBox();
        Label l2 = new Label("Odnos svih izlaza:");
        l2.setStyle("-fx-font: 17px \"Serif\";");
        pitaIzlazaHB.getChildren().add(l2);

        IzlaziHb = new HBox(40);
        IzlaziHb.setStyle("-fx-font: 17px \"System\";");
        Separator s2 = new Separator();
        s2.setOrientation(Orientation.VERTICAL);
        IzlaziHb.getChildren().addAll(GrafikIzlaza,s2,pitaIzlazaHB);
        this.getChildren().add(IzlaziHb);
        tabelaipitaIzlaz();

        //naslov 3
        Label naslov3 = new Label("Druge statistike:");
        naslov3.setFont(new Font(35));
        Separator separator3 = new Separator();
        this.getChildren().addAll(naslov3,separator3);

        PiteHb = new HBox(10);
        PiteHb.setStyle("-fx-font: 17px \"System\";");
        this.getChildren().add(PiteHb);
        ostalestatistike();

        //update
        updateCbUlaz();
        updateCbIzlaz();

    }

    private void ulazopcije(){

        HBUlaziF.getChildren().add(new Label("Od:"));

        DpUlaziPocetniF = new DatePicker();
        DpUlaziPocetniF.setPrefWidth(160);
        if(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().size() != 0)
            DpUlaziPocetniF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().get(0).getDatum());

        DpUlaziPocetniF.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                updateGrafikUlaza();
            }
        });

        HBUlaziF.getChildren().add(DpUlaziPocetniF);

        HBUlaziF.getChildren().add(new Label("Do:"));
        DpUlaziKrajnjiF = new DatePicker();
        DpUlaziKrajnjiF.setPrefWidth(160);
        if(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().size() != 0)
            DpUlaziKrajnjiF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().get(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().size()-1).getDatum());


        DpUlaziKrajnjiF.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                updateGrafikUlaza();
            }
        });

        HBUlaziF.getChildren().add(DpUlaziKrajnjiF);
        HBUlaziF.getChildren().add(new Label("Ulaz:"));
        CbUlaziF = new ComboBox<Ulaz>();
        CbUlaziF.setPrefWidth(300);
        HBUlaziF.getChildren().add(CbUlaziF);
        BPonistiUlazF = new Button("Poništi");
        BPonistiUlazF.setPrefWidth(110);
        ImageView close = new ImageView(Firma.getInstance().getCloseIco());
        BPonistiUlazF.setGraphic(close);

        BPonistiUlazF.setOnAction(e->{
            DpUlaziKrajnjiF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().get(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().size()-1).getDatum());
            DpUlaziPocetniF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().get(0).getDatum());
            updateCbUlaz();
            updateGrafikUlaza();
        });

        HBUlaziF.getChildren().add(BPonistiUlazF);

    }

    private void tabelaipitaulaza(){

        DateAxis310 xAxis = new DateAxis310();     ///grafikkk
        NumberAxis yAxis = new NumberAxis();
        UlazLineChart = new LineChart<>(xAxis, yAxis);
        UlazLineChart.setAnimated(true);
        UlazLineChart.setPrefWidth(930);
        grafikUlaza.getChildren().add(UlazLineChart);
        updateGrafikUlaza();

        pieChart1 = new PieChart();
        pieChart1.setLegendVisible(true);
        pieChart1.setLabelsVisible(false);
        pieChart1.setLegendSide(Side.RIGHT);
        pieChart1.setPrefHeight(300);
        pitaUlazaHB.getChildren().add(pieChart1);
        updatePitaUlaza();

    }

    private void updatePitaUlaza(){

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for(Ulaz u: Firma.getInstance().getTrenutnaGodina().getUlazi()){
            double kolicina = 0.0;
            for(UnosUlaza uu: Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()){
                if(uu.getUlaz().equals(u))
                    kolicina+= uu.getKolicinaNeto();
            }
            pieChartData.add(new PieChart.Data(u.getNaziv(),kolicina));
        }

        pieChart1.getData().clear();

        FXCollections.sort(pieChartData, Comparator.comparingDouble(PieChart.Data::getPieValue).reversed());

        ObservableList<PieChart.Data> firstNElements = FXCollections.observableArrayList(
                pieChartData.subList(0, Math.min(5, pieChartData.size()))
        );

        ObservableList<PieChart.Data> otherElements = FXCollections.observableArrayList(
                pieChartData.subList(5, Math.max(5, pieChartData.size()))
        );

        double kolicinaostala = 0.0;
        for (PieChart.Data d : otherElements){
            kolicinaostala += d.getPieValue();
        }

        firstNElements.add(new PieChart.Data("Ostalo", kolicinaostala));

        pieChart1.setData(firstNElements);

    }

    private void updateGrafikUlaza() {

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("količina [kg]");
        DateAxis310 xAxis = new DateAxis310();
        xAxis.setTickLabelFormatter(STRING_CONVERTER);
        xAxis.setLabel("dan");
        XYChart.Series series = new XYChart.Series<LocalDateTime, Number>();

        UlazLineChart.getData().clear();
        UlazLineChart.getData().add(series);
        UlazLineChart.legendVisibleProperty().set(false);
        xAxis.setTickLabelFormatter(STRING_CONVERTER);

        List<XYChart.Data<LocalDateTime, Number>> data = new ArrayList<>();
        if(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().size() != 0) {
            for (LocalDate date = DpUlaziPocetniF.getValue(); !date.isEqual(DpUlaziKrajnjiF.getValue().plusDays(1)); date = date.plusDays(1)) {
                if (CbUlaziF.getSelectionModel().getSelectedItem() == null)
                    data.add(new XYChart.Data<>(date.atStartOfDay(), UkupnoSvihUlazaNaDan(date)));
                else
                    data.add(new XYChart.Data<>(date.atStartOfDay(), UkupnoUlazaNaDan(date, CbUlaziF.getSelectionModel().getSelectedItem())));
            }
        }
        series.getData().setAll(data);

    }

    private  void izlaziopcije(){

        HbIzlaziF.getChildren().add(new Label("Od:"));
        DpIzlazzPocetniF = new DatePicker();
        DpIzlazzPocetniF.setPrefWidth(160);

        if(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().size() != 0)
            DpIzlazzPocetniF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().get(0).getDatum());

        DpIzlazzPocetniF.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                updateGrafikaIzlaza();
            }
        });
        HbIzlaziF.getChildren().add(DpIzlazzPocetniF);
        HbIzlaziF.getChildren().add(new Label("Do:"));
        DpIzlazKrajnjiF = new DatePicker();
        DpIzlazKrajnjiF.setPrefWidth(160);
        if(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().size() != 0)
            DpIzlazKrajnjiF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().get(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().size() - 1).getDatum());

        DpIzlazKrajnjiF.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                updateGrafikaIzlaza();
            }
        });
        HbIzlaziF.getChildren().add(DpIzlazKrajnjiF);
        HbIzlaziF.getChildren().add(new Label("Izlaz:"));
        Cbizlaz = new ComboBox<Izlaz>();
        Cbizlaz.setPrefWidth(300);
        HbIzlaziF.getChildren().add(Cbizlaz);
        BPonistiIzlazF = new Button("poništi");
        ImageView close = new ImageView(Firma.getInstance().getCloseIco());
        BPonistiIzlazF.setGraphic(close);
        BPonistiIzlazF.setPrefWidth(110);
        HbIzlaziF.getChildren().add(BPonistiIzlazF);
        BPonistiIzlazF.setOnAction(e -> {
            DpIzlazKrajnjiF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().get(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().size() - 1).getDatum());
            DpIzlazzPocetniF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().get(0).getDatum());
            updateCbIzlaz();
            updateGrafikaIzlaza();
        });

    }

    private void tabelaipitaIzlaz(){

        DateAxis310 xAxis = new DateAxis310();     ///grafikkk
        NumberAxis yAxis = new NumberAxis();
        IzlazLineChart = new LineChart<>(xAxis, yAxis);
        IzlazLineChart.setAnimated(true);
        IzlazLineChart.setPrefWidth(930);
        GrafikIzlaza.getChildren().add(IzlazLineChart);
        updateGrafikaIzlaza();

        pieChart2 = new PieChart();
        pieChart2.setLegendVisible(true);
        pieChart2.setLabelsVisible(false);
        pieChart2.setLegendSide(Side.RIGHT);
        pieChart2.setPrefHeight(300);
        pitaIzlazaHB.getChildren().add(pieChart2);
        updatePitaIzlaza();

    }

    private void updatePitaIzlaza(){

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for(Izlaz i: Firma.getInstance().getTrenutnaGodina().getIzlazi()){
            double kolicina = 0.0;
            for(UnosIzlaza ui: Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza()){
                if(ui.getIzlaz().equals(i))
                    kolicina+= ui.getKolicina();
            }
            pieChartData.add(new PieChart.Data(i.getNaziv(), kolicina));
        }

        pieChart2.getData().clear();

        FXCollections.sort(pieChartData, Comparator.comparingDouble(PieChart.Data::getPieValue).reversed());

        ObservableList<PieChart.Data> firstNElements = FXCollections.observableArrayList(
                pieChartData.subList(0, Math.min(5, pieChartData.size()))
        );

        ObservableList<PieChart.Data> otherElements = FXCollections.observableArrayList(
                pieChartData.subList(5, Math.max(5, pieChartData.size()))
        );

        double kolicinaostala = 0.0;
        for (PieChart.Data d : otherElements){
            kolicinaostala += d.getPieValue();
        }

        firstNElements.add(new PieChart.Data("Ostalo", kolicinaostala));

        pieChart2.setData(firstNElements);


    }

    private void updateGrafikaIzlaza(){

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("količina");
        DateAxis310 xAxis = new DateAxis310();
        xAxis.setTickLabelFormatter(STRING_CONVERTER);
        xAxis.setLabel("dan");
        XYChart.Series series = new XYChart.Series<LocalDateTime, Number>();

        IzlazLineChart.getData().clear();
        IzlazLineChart.getData().add(series);
        IzlazLineChart.legendVisibleProperty().set(false);
        xAxis.setTickLabelFormatter(STRING_CONVERTER);

        List<XYChart.Data<LocalDateTime, Number>> data = new ArrayList<>();
        if(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().size() != 0) {
            for (LocalDate date = DpIzlazzPocetniF.getValue(); !date.isEqual(DpIzlazKrajnjiF.getValue().plusDays(1)); date = date.plusDays(1)) {
                if (Cbizlaz.getSelectionModel().getSelectedItem() == null)
                    data.add(new XYChart.Data<>(date.atStartOfDay(), UkupnoSvihIzlazaNaDan(date)));
                else
                    data.add(new XYChart.Data<>(date.atStartOfDay(), UkupnoIzlazaNaDan(date, Cbizlaz.getSelectionModel().getSelectedItem())));
            }
        }
        series.getData().setAll(data);

    }

    private void ostalestatistike(){

        pieChart3 = new PieChart();
        pieChart3.setLegendVisible(false);
        pieChart3.setLabelsVisible(true);
        pieChart3.setLegendSide(Side.RIGHT);
        pieChart3.setPrefHeight(300);
        pieChart3.setTitle("Isplata:");
        PiteHb.getChildren().add(pieChart3);
        updatePitaIsplata();

        pieChart4 = new PieChart();
        pieChart4.setLegendVisible(false);
        pieChart4.setLabelsVisible(true);
        pieChart4.setLegendSide(Side.RIGHT);
        pieChart4.setPrefHeight(300);
        pieChart4.setTitle("Ambalaža:");
        PiteHb.getChildren().add(pieChart4);
        updatePitaGajbi();

        pieChart5 = new PieChart();
        pieChart5.setLegendVisible(false);
        pieChart5.setLabelsVisible(true);
        pieChart5.setLegendSide(Side.RIGHT);
        pieChart5.setPrefHeight(300);
        pieChart5.setTitle("Prevoz:");
        PiteHb.getChildren().add(pieChart5);
        updatePitaPrevoza();

    }

    private void updatePitaIsplata(){

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        double ukupnoIzlaza = 0.0;
        for(UnosIzlaza ui: Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza()){
            ukupnoIzlaza += (ui.getKolicina() * ui.getIzlaz().getCenaPoKomadu());
        }
        double ukupnoUlaza = 0.0;
        for(UnosUlaza uu: Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()){
            if(uu.getPrevoznik().equals(Firma.getInstance().getTrenutnaGodina().getLicno())) {
                ukupnoUlaza += uu.getKolicinaNeto() * (Firma.getInstance().getTrenutnaGodina().cenaZaDatum(uu.getUlaz(), uu.getDatum()).getCenaSaPrevozom() + uu.getProizvodjac().getCenaPlus());
            }else {
                ukupnoUlaza += uu.getKolicinaNeto() * (Firma.getInstance().getTrenutnaGodina().cenaZaDatum(uu.getUlaz(), uu.getDatum()).getCenaBezPrevoza() + uu.getProizvodjac().getCenaPlus());
            }
        }
        pieChartData.add(new PieChart.Data("isplaćeno", ukupnoIzlaza));
        pieChartData.add(new PieChart.Data("neisplaćeno", ukupnoUlaza-ukupnoIzlaza));

        pieChart3.getData().clear();
        pieChart3.setData(pieChartData);

    }

    private void updatePitaGajbi(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        int ukupnoZaduzenih = 0;
        int ukupnoRazduzenih = 0;
        for(UnosGajbi ug : Firma.getInstance().getTrenutnaGodina().getUnosiGajbi()) {
            ukupnoZaduzenih += ug.getGajbeIzlaz();
            ukupnoRazduzenih += ug.getGajbeUlaz();
        }
        int naTerenu = ukupnoZaduzenih-ukupnoRazduzenih;
        int ukupnoNaStanju =0;
        for(Gajba g: Firma.getInstance().getTrenutnaGodina().getGajbe()) {
            ukupnoNaStanju += g.getUkupnoNaRaspolaganju();
        }
        int naStanju = ukupnoNaStanju-naTerenu;
        pieChartData.add(new PieChart.Data("na stanju", naStanju));
        pieChartData.add(new PieChart.Data("na terenu", naTerenu));

        pieChart4.getData().clear();
        pieChart4.setData(pieChartData);

    }

    private void updatePitaPrevoza(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        double licno = 0.0;
        double naotkupnom = 0.0;
        for (UnosUlaza uu : Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()){
            if(uu.getPrevoznik().equals(Firma.getInstance().getTrenutnaGodina().getLicno()))
                licno += uu.getKolicinaNeto();
            else
                naotkupnom += uu.getKolicinaNeto();
        }
        pieChartData.add(new PieChart.Data("ličnoo", licno));
        pieChartData.add(new PieChart.Data("prevoznici", naotkupnom));

        pieChart5.getData().clear();
        pieChart5.setData(pieChartData);

    }

    public void updateCbUlaz(){

        CbUlaziF.getItems().clear();
        CbUlaziF.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getUlazi());
        CbUlaziF.setTooltip(new Tooltip());
        CbUlaziF.setPromptText("svi ulazi");
        new ComboBoxAutoComplete<Ulaz>(CbUlaziF);

        CbUlaziF.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null)
                updateGrafikUlaza();
        });
    }

    public void updateCbIzlaz(){

        Cbizlaz.getItems().clear();
        Cbizlaz.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getIzlazi());
        Cbizlaz.setTooltip(new Tooltip());
        Cbizlaz.setPromptText("svi izlazi");
        new ComboBoxAutoComplete<Izlaz>(Cbizlaz);

        Cbizlaz.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null)
                updateGrafikaIzlaza();
        });
    }

    final StringConverter<LocalDateTime> STRING_CONVERTER = new StringConverter<LocalDateTime>() {
        @Override public String toString(LocalDateTime localDateTime) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM");
            return dtf.format(localDateTime);
        }
        @Override public LocalDateTime fromString(String s) {
            return LocalDateTime.parse(s);
        }
    };

    public void  setColor(String gore, String dole){

        LinearGradient gradient = new LinearGradient(
                0, 0, 0, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new javafx.scene.paint.Stop(0, Color.web(gore)),
                new javafx.scene.paint.Stop(1, Color.web(dole))
        );

        // Creating a BackgroundFill with the linear gradient
        BackgroundFill backgroundFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);

        // Creating a Background with the BackgroundFill
        Background background = new Background(backgroundFill);

        this.setBackground(background);
    }

    public static StatistikaTab getInstance() {
        if (instance == null) {
            instance = new StatistikaTab();
        }
        return instance;
    }

    private double UkupnoSvihUlazaNaDan(LocalDate dan){
        double ukupno = 0.0;
        for(UnosUlaza uu : Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()){
            if(uu.getDatum().equals(dan))
                ukupno += uu.getKolicinaNeto();
        }
        return ukupno;
    }

    private double UkupnoUlazaNaDan(LocalDate dan, Ulaz u){
        double ukupno = 0.0;
        for(UnosUlaza uu : Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()){
            if(uu.getDatum().equals(dan) && uu.getUlaz().equals(u))
                ukupno += uu.getKolicinaNeto();
        }
        return ukupno;
    }

    private  double UkupnoSvihIzlazaNaDan(LocalDate date){
        double ukupno = 0.0;
        for (UnosIzlaza ui : Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza()){
            if(ui.getDatum().equals(date))
                ukupno += ui.getKolicina();
        }
        return ukupno;
    }

    private double UkupnoIzlazaNaDan(LocalDate dan, Izlaz i){
        double kolicina = 0.0;
        for(UnosIzlaza ui : Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza()){
            if(ui.getDatum().equals(dan) && ui.getIzlaz().equals(i))
                kolicina += ui.getKolicina();
        }
        return kolicina;
    }

}
