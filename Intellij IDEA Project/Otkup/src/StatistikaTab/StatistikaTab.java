package StatistikaTab;

import Main.ComboBoxAutoComplete;
import Main.DateAxis310;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.*;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class StatistikaTab extends VBox {

    private static StatistikaTab instance;

    private VBox glavni;

    private HBox gornji;
    private HBox donji;

    private VBox GoreLeviVB;
    private VBox GoreDesniVB;
    private VBox DoleLeviVB;
    private VBox DoleDesniVB;

    private ComboBox<Ulaz> CbUlaziF;
    private DatePicker DpUlaziPocetniF;
    private DatePicker DpUlaziKrajnjiF;
    private Button BPonistiUlazF;
    private HBox HBUlaziF;
    private LineChart<LocalDateTime, Number> UlazLineChart;

    private ComboBox<Izlaz> Cbizlaz;
    private DatePicker DpIzlazzPocetniF;
    private DatePicker DpIzlazKrajnjiF;
    private Button BPonistiIzlazF;
    private HBox HbIzlaziF;
    private LineChart<LocalDateTime, Number> IzlazLineChart;

    private ComboBox<String> cbPitaGornja;
    private HBox HBPitaGore;
    private PieChart pieChart1;

    private ComboBox<String> cbPitaDonja;
    private HBox HBPitaDole;
    private PieChart pieChart2;


    private StatistikaTab(){

        glavni = new VBox(10);

        setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
        setSpacing(15);
        this.setScaleX(1);
        this.setScaleY(1);

        gornji = new HBox(20);
        donji = new HBox(20);
        GoreLeviVB = new VBox(10);
        GoreDesniVB = new VBox(10);
        DoleDesniVB = new VBox(10);
        DoleLeviVB = new VBox(10);
        GoreLeviVB.setMinWidth(900);
        DoleLeviVB.setMinWidth(900);
        Label naslov = new Label("Statistika:");
        naslov.setFont(new Font(35));
        glavni.getChildren().add(naslov);
        GoreDesniVB.setAlignment(Pos.TOP_LEFT);
        DoleDesniVB.setAlignment(Pos.TOP_LEFT);

        GoreDesniVB.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;" + "-fx-border-style: dotted;" + "-fx-border-insets: 5;" + "-fx-padding: 10;");
        GoreLeviVB.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;" + "-fx-border-style: dotted;" + "-fx-border-insets: 5;" + "-fx-padding: 10;");
        DoleDesniVB.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;" + "-fx-border-style: dotted;" + "-fx-border-insets: 5;" + "-fx-padding: 10;");
        DoleLeviVB.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;" + "-fx-border-style: dotted;" + "-fx-border-insets: 5;" + "-fx-padding: 10;");

        gornji.getChildren().addAll(GoreLeviVB,GoreDesniVB);
        donji.getChildren().addAll(DoleLeviVB,DoleDesniVB);
        glavni.getChildren().addAll(gornji,donji);
        getChildren().add(glavni);

        ulazi();
        izlazi();
        pitaGornja();
        pitaDonja();
    }

    private void pitaGornja(){
        HBPitaGore = new HBox(10);
        HBPitaGore.setAlignment(Pos.BASELINE_LEFT);
        Label l0 = new Label("Pita 1:");
        l0.setFont(new Font(20));
        HBPitaGore.getChildren().add(l0);
        cbPitaGornja = new ComboBox<String>();
        cbPitaGornja.getItems().add("odnos ulaza");
        cbPitaGornja.getItems().add("odnos izlaz");
        cbPitaGornja.getSelectionModel().select(0);
        cbPitaGornja.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null)
                updatePitaGore();
        });

        HBPitaGore.getChildren().add(cbPitaGornja);
        GoreDesniVB.getChildren().add(HBPitaGore);

        pieChart1 = new PieChart();
        GoreDesniVB.getChildren().add(pieChart1);

        updatePitaGore();
    }

    private void updatePitaGore(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        if (cbPitaGornja.getSelectionModel().getSelectedItem().equals("odnos ulaza")){
            for(Ulaz u: Firma.getInstance().getTrenutnaGodina().getUlazi()){
                double kolicina = 0.0;
                for(UnosUlaza uu: Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()){
                    if(uu.getUlaz().equals(u))
                        kolicina+= uu.getKolicinaNeto();
                }
                pieChartData.add(new PieChart.Data(u.getNaziv(),kolicina));
            }
        }
        if (cbPitaGornja.getSelectionModel().getSelectedItem().equals("odnos izlaz")){
            for(Izlaz i: Firma.getInstance().getTrenutnaGodina().getIzlazi()){
                double kolicina = 0.0;
                for(UnosIzlaza ui: Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza()){
                    if(ui.getIzlaz().equals(i))
                        kolicina+= ui.getKolicina();
                }
                pieChartData.add(new PieChart.Data(i.getNaziv(), kolicina));
            }
        }

        pieChart1 = new PieChart(pieChartData);
        pieChart1.setLegendVisible(true);
        pieChart1.setLabelsVisible(false);
        pieChart1.setLegendSide(Side.RIGHT);
        pieChart1.setPrefHeight(300);

        GoreDesniVB.getChildren().remove(1);
        GoreDesniVB.getChildren().add(1,pieChart1);

    }

    private void pitaDonja(){
        HBPitaDole = new HBox(10);
        HBPitaDole.setAlignment(Pos.BASELINE_LEFT);
        Label l0 = new Label("Pita 2:");
        l0.setFont(new Font(20));
        HBPitaDole.getChildren().add(l0);
        cbPitaDonja = new ComboBox<String>();
        cbPitaDonja.getItems().add("isplata");
        cbPitaDonja.getItems().add("gajbe");
        cbPitaDonja.getItems().add("prevoz");

        cbPitaDonja.getSelectionModel().select(0);
        cbPitaDonja.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null)
                updatrePtaDonja();
        });

        HBPitaDole.getChildren().add(cbPitaDonja);
        DoleDesniVB.getChildren().add(HBPitaDole);

        pieChart2 = new PieChart();
        DoleDesniVB.getChildren().add(pieChart2);

        updatrePtaDonja();

    }

    private void updatrePtaDonja(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        if (cbPitaDonja.getSelectionModel().getSelectedItem().equals("isplata")){
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
        }

        if (cbPitaDonja.getSelectionModel().getSelectedItem().equals("prevoz")){
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

        }
        if (cbPitaDonja.getSelectionModel().getSelectedItem().equals("gajbe")){
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

        }

        pieChart2 = new PieChart(pieChartData);
        pieChart2.setLegendVisible(false);
        pieChart2.setLabelsVisible(true);
        pieChart2.setLegendSide(Side.RIGHT);
        pieChart2.setPrefHeight(300);

        DoleDesniVB.getChildren().remove(1);
        DoleDesniVB.getChildren().add(1,pieChart2);

    }

    private void ulazi(){

        HBUlaziF = new HBox(10);
        HBUlaziF.setAlignment(Pos.BASELINE_LEFT);
        Label l0 = new Label("Ulaza:");
        l0.setFont(new Font(20));
        HBUlaziF.getChildren().add(l0);
        HBUlaziF.getChildren().add(new Label("od:"));

        DpUlaziPocetniF = new DatePicker();
        if(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().size() != 0)
            DpUlaziPocetniF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().get(0).getDatum());

        DpUlaziPocetniF.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                updateGrafikUlaza();
            }
        });

        HBUlaziF.getChildren().add(DpUlaziPocetniF);
        HBUlaziF.getChildren().add(new Label("do:"));
        DpUlaziKrajnjiF = new DatePicker();
        if(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().size() != 0)
            DpUlaziKrajnjiF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().get(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().size()-1).getDatum());


        DpUlaziKrajnjiF.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                updateGrafikUlaza();
            }
        });

        HBUlaziF.getChildren().add(DpUlaziKrajnjiF);
        HBUlaziF.getChildren().add(new Label("ulaz:"));
        CbUlaziF = new ComboBox<Ulaz>();
        HBUlaziF.getChildren().add(CbUlaziF);
        BPonistiUlazF = new Button("poništi");
        ImageView close = new ImageView(Firma.getInstance().getCloseIco());
        BPonistiUlazF.setGraphic(close);

        BPonistiUlazF.setOnAction(e->{
            DpUlaziKrajnjiF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().get(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().size()-1).getDatum());
            DpUlaziPocetniF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().get(0).getDatum());
            updateCbUlaz();
            updateGrafikUlaza();
        });

        HBUlaziF.getChildren().add(BPonistiUlazF);
        GoreLeviVB.getChildren().add(HBUlaziF);

        DateAxis310 xAxis = new DateAxis310();     ///grafikkk
        NumberAxis yAxis = new NumberAxis();
        UlazLineChart = new LineChart<>(xAxis, yAxis);
        GoreLeviVB.getChildren().add(UlazLineChart);
        updateGrafikUlaza();
        //applyDataPointMouseEvents(series);

        updateCbUlaz();
    }

    private void izlazi(){




            HbIzlaziF = new HBox(10);
            HbIzlaziF.setAlignment(Pos.BASELINE_LEFT);
            Label l0 = new Label("Izlaza:");
            l0.setFont(new Font(20));
            HbIzlaziF.getChildren().add(l0);
            HbIzlaziF.getChildren().add(new Label("od:"));
            DpIzlazzPocetniF = new DatePicker();

            if(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().size() != 0)
                DpIzlazzPocetniF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().get(0).getDatum());

            DpIzlazzPocetniF.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    updateGrafikaIzlaza();
                }
            });
            HbIzlaziF.getChildren().add(DpIzlazzPocetniF);
            HbIzlaziF.getChildren().add(new Label("do:"));
            DpIzlazKrajnjiF = new DatePicker();
            if(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().size() != 0)
                DpIzlazKrajnjiF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().get(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().size() - 1).getDatum());

            DpIzlazKrajnjiF.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    updateGrafikaIzlaza();
                }
            });
            HbIzlaziF.getChildren().add(DpIzlazKrajnjiF);
            HbIzlaziF.getChildren().add(new Label("izlaz:"));
            Cbizlaz = new ComboBox<Izlaz>();
            HbIzlaziF.getChildren().add(Cbizlaz);
            BPonistiIzlazF = new Button("poništi");
            ImageView close = new ImageView(Firma.getInstance().getCloseIco());
            BPonistiIzlazF.setGraphic(close);
            HbIzlaziF.getChildren().add(BPonistiIzlazF);
            BPonistiIzlazF.setOnAction(e -> {
                DpIzlazKrajnjiF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().get(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().size() - 1).getDatum());
                DpIzlazzPocetniF.valueProperty().set(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().get(0).getDatum());
                updateCbIzlaz();
                updateGrafikaIzlaza();
            });

            DoleLeviVB.getChildren().add(HbIzlaziF);

            DateAxis310 xAxis = new DateAxis310();     ///grafikkk
            NumberAxis yAxis = new NumberAxis();
            IzlazLineChart = new LineChart<>(xAxis, yAxis);
            DoleLeviVB.getChildren().add(IzlazLineChart);
            updateGrafikaIzlaza();
            //applyDataPointMouseEvents(series);

            updateCbIzlaz();


    }

    private void updateGrafikUlaza() {
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("kolicina [kg]");
        DateAxis310 xAxis = new DateAxis310();
        xAxis.setTickLabelFormatter(STRING_CONVERTER);
        xAxis.setLabel("dan");
        XYChart.Series series = new XYChart.Series<LocalDateTime, Number>();
        //series.setName("KURAC");

        UlazLineChart = new LineChart<>(xAxis, yAxis);
        UlazLineChart.getData().add(series);
        UlazLineChart.setAnimated(true);
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
        GoreLeviVB.getChildren().remove(1);
        GoreLeviVB.getChildren().add(1,UlazLineChart);

    }

    private void updateGrafikaIzlaza(){
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("kolicina");
        DateAxis310 xAxis = new DateAxis310();
        xAxis.setTickLabelFormatter(STRING_CONVERTER);
        xAxis.setLabel("dan");
        XYChart.Series series = new XYChart.Series<LocalDateTime, Number>();
        //series.setName("KURAC");

        IzlazLineChart = new LineChart<>(xAxis, yAxis);
        IzlazLineChart.getData().add(series);
        IzlazLineChart.setAnimated(true);
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
        DoleLeviVB.getChildren().remove(1);
        DoleLeviVB.getChildren().add(1,IzlazLineChart);

    }

    public void updateCbUlaz(){
        HBUlaziF.getChildren().remove(CbUlaziF);
        CbUlaziF = new ComboBox<Ulaz>();
        HBUlaziF.getChildren().add(6,CbUlaziF);
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
        HbIzlaziF.getChildren().remove(Cbizlaz);
        Cbizlaz = new ComboBox<Izlaz>();
        HbIzlaziF.getChildren().add(6,Cbizlaz);
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
