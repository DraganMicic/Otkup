package BrzUnosUlaza_tab;

import BrzUnosUlaza_tab.Tols_K.*;
import BrzUnosUlaza_tab.UnosUlaza_ASCED.*;
import Main.ComboBoxAutoComplete;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class BrzUnosUlazaTab extends VBox {

    private static BrzUnosUlazaTab instance;

    private boolean unosNovog=false;
    private boolean izmenaUToku = false;

    private HBox unosITabelaHb;

    //polja za unos
    private GridPane unosGp;
    private  TextField tfSifra;
    private DatePicker dpDatum;
    private ComboBox<Proizvodjac> cbProizvodjac;
    private TextField tfSifraPrizvodjaca;
    private boolean setovo = false;
    private ComboBox<Ulaz> cbIUlaz;
    private TextField tfCena;
    private TextField tfUlazGajbe;
    private TextField tfIzlazGajbe;
    private ComboBox<Gajba> cbGajba;
    private TextField tfKolicinaBruto;
    private TextField tfKolicnaNeto;
    private TextField tfTara;
    private ComboBox<Prevoznik> cbPrevoznik;
    private Label lsifra;
    private Label ldatum;
    private Label lproizvodjac;
    private Label lvrstulaza;
    private Label lamblaza;
    private Label lulaz;
    private Label lizlaz;
    private Label lvrstagajbe;
    private Label lkolicina;
    private Label lbruto;
    private Label lneto;
    private Label lprevoznik;
    private Label lcena;
    private Label ltara;
    private Label ldatumobavestenje;

    private Button BDodaj;
    private Button BSacuvaj;
    private Button BPonisti;
    private Button BIzmeni;
    private Button BObrisi;
    private Button BStampaj;
    private Button BSacuvajiStampaj;

    private HBox trakaZaPretragu1HB;
    private HBox trakaZaPretragu2HB;
    private RadioButton RBDanPretraga;
    private RadioButton RBPeriodPretraga;
    private ToggleGroup TGDanPeriodPretraga;
    private DatePicker DPPocetniPretraga;
    private DatePicker DPkrajnjiPretraga;
    private ComboBox<Proizvodjac> CBProizvodjacPretraga;
    private ComboBox<Ulaz> CBUlazPretraga;
    private ComboBox<Prevoznik> CBPrevoznikPretraga;
    private Button BPonistiPretragu1;
    private Button BPonistiPretragu2;
    private Label Lod;
    private Label Ldo;
    private int pozicija = 6;

    private HBox obracunHB;
    private RadioButton RBSviObracun;
    private RadioButton RBFiltriraniObracun;
    private ToggleGroup TGObracun;
    private Button BObracunaj;

    private HBox stampaIzvestajaHB;
    private RadioButton RBDnevniIzvestaj;
    private RadioButton RBPeriodicniIzvestaj;
    private RadioButton RBPeriodicniPoDanimaIzvestaj;
    private ToggleGroup TGIzvestaj;
    private DatePicker DPPocetniIzvestraj;
    private DatePicker DPKrajnjiIzvestaj;
    private Button BStamopajIzvestaj;
    private Label LodIzvestaj;
    private Label ldoIzvestaj;
    private int izvestajTip = 1;

    private HBox stampaHB;
    private Button BStamajPaletniList;
    private ComboBox<String> cbObeleživač;
    private RadioButton RBKartonIndentifikacije;
    private RadioButton RBMikerPapiric;
    private RadioButton RBFertodiPapiric;
    private ToggleGroup TGObelezivaci;

    private TableView<UnosUlaza> tabela;

    private  BrzUnosUlazaTab(){

        setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
        setSpacing(15);
        this.setMinHeight(Screen.getPrimary().getBounds().getHeight()-80);

        //naslov1
        Label naslov = new Label("Unos ulaza (otkupnih listova):");
        naslov.setFont(new Font(35));
        Separator separator = new Separator();
        this.getChildren().addAll(naslov,separator);

        //unos i tabela
        unosITabelaHb = new HBox(20);
        this.getChildren().add(unosITabelaHb);

        //podesavanja grid panea
        unosGp = new GridPane();
        unosGp.setPadding(new Insets(10, 10, 10, 10));
        unosGp.setVgap(10);
        unosGp.setHgap(10);
        unosGp.setAlignment(Pos.BASELINE_LEFT);
        unosGp.setStyle("-fx-font: 20px \"Serif\";");
        unosGp.setMinWidth(690);

        unos();
        tabela();
        tabela.setPrefWidth(1300);
        tabela.setPrefHeight(530);

        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.VERTICAL);
        unosITabelaHb.getChildren().addAll(unosGp,separator2,tabela);

        //naslov2
        Label naslov2 = new Label("Alati:");
        naslov2.setFont(new Font(35));
        Separator separator1 = new Separator();
        this.getChildren().addAll(naslov2,separator1);

        //alati
        trakaZaPretragu();
        trakaZaObracun();
        trakaZaStampuIzvestaja();
        trakaZaStampuPaletnihListova();
        this.getChildren().addAll(stampaHB,trakaZaPretragu1HB,trakaZaPretragu2HB,obracunHB,stampaIzvestajaHB);

        //pocetniupdate
        updateCBProizvodjac();
        updateCBUlaz();
        updateCBPrevoznik();
        updateCBGajba();

        SetUnosDisable();
        getBDodaj().requestFocus();
    }

    private void unos(){

        //DUGMICI///////////////////////////////////////////////////////////////////////////////
        ImageView add = new ImageView(Firma.getInstance().getAddIco());
        BDodaj = new Button("Dodaj",add);
        BDodaj.setPrefWidth(200);
        BDodaj.setOnAction(new DodajKontroler());

        BSacuvaj = new Button("Sačuvaj");
        ImageView save = new ImageView(Firma.getInstance().getSaveIco());
        BSacuvaj.setGraphic(save);
        BSacuvaj.setPrefWidth(200);
        BSacuvaj.setOnAction(new SacuvajKontroler());
        BSacuvaj.setDisable(true);

        BPonisti = new Button("Stop");
        ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
        BPonisti.setGraphic(cancel);
        BPonisti.setPrefWidth(200);
        BPonisti.setOnAction(new PonistiKontroler());
        BPonisti.setDisable(true);

        BIzmeni = new Button("Izmeni");
        BIzmeni.setDisable(true);
        ImageView edit = new ImageView(Firma.getInstance().getEditIco());
        BIzmeni.setGraphic(edit);
        BIzmeni.setPrefWidth(200);
        BIzmeni.setOnAction(new IzmeniKontroler());

        BObrisi = new Button("Obriši");
        BObrisi.setDisable(true);
        BObrisi.setPrefWidth(200);
        ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
        BObrisi.setGraphic(delete);
        BObrisi.setOnAction(new ObrisiKontroler());

        //dodavanje gornjih dugmica
        unosGp.add(BDodaj,0,0,1,1);
        unosGp.add(BSacuvaj,1,0,1,1);
        unosGp.add(BPonisti,2,0,1,1);
        unosGp.add(BIzmeni,3,0,1,1);
        unosGp.add(BObrisi,4,0,1,1);

        tfSifra = new TextField();
        tfSifra.setPrefWidth(200);

        ldatumobavestenje = new Label("");
        dpDatum = new DatePicker();
        dpDatum.setPrefWidth(410);
        dpDatum.valueProperty().addListener(new ChangeListener<LocalDate>() {
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,LocalDate newValue) {
                if(observable.getValue() != null && cbIUlaz.getValue()!= null && cbPrevoznik.getValue() != null && cbProizvodjac.getValue() != null) {
                    if(cbPrevoznik.getValue() == Firma.getInstance().getTrenutnaGodina().getLicno()) {
                        try {
                            tfCena.setText(String.valueOf(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(cbIUlaz.getValue(), dpDatum.getValue()).getCenaSaPrevozom()));
                        } catch (Exception e) {
                            tfCena.clear();
                        }
                    }
                    else {
                        try {
                            tfCena.setText(String.valueOf(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(cbIUlaz.getValue(), dpDatum.getValue()).getCenaBezPrevoza()));
                        } catch (Exception e) {
                            tfCena.clear();
                        }
                    }
                }
                LocalDate currentDate = LocalDate.now();

                if(observable.getValue()==null)
                    ldatumobavestenje.setText("");
                else
                    if(!observable.getValue().isEqual(currentDate))
                        ldatumobavestenje.setText("(nije današnji!)");
                    else
                        ldatumobavestenje.setText("(današnji)");

            }
        });

        cbProizvodjac = new ComboBox<Proizvodjac>();
        cbProizvodjac.setPrefWidth(620);

        tfSifraPrizvodjaca = new TextField();
        tfSifraPrizvodjaca.setPrefWidth(200);
        tfSifraPrizvodjaca.setPromptText("(šifra)");
        tfSifraPrizvodjaca.textProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.equals("")){
                    int sifraProizvodjaca;
                    try {
                        sifraProizvodjaca = Integer.valueOf(newValue);
                    } catch (Exception e) {
                        Alert ada = new Alert(Alert.AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
                        ada.show();
                        Platform.runLater(() -> {
                            tfSifraPrizvodjaca.clear();
                        });
                        return;
                    }
                    Proizvodjac p = Firma.getInstance().getTrenutnaGodina().proizvodjacSaSifrom(sifraProizvodjaca);
                    if(p!=null){
                        cbProizvodjac.getSelectionModel().select(p);
                    }else{
                        Alert ada = new Alert(Alert.AlertType.ERROR, "Ne postoji proizvođač sa izabranom šifrom!");
                        ada.show();
                        Platform.runLater(() -> {
                            tfSifraPrizvodjaca.clear();
                        });
                        cbPrevoznik.getSelectionModel().clearSelection();
                        return;
                    }
                }else{
                    cbProizvodjac.getSelectionModel().clearSelection();
                    cbPrevoznik.getSelectionModel().clearSelection();
                }
            }
        });

        cbIUlaz = new ComboBox<Ulaz>();
        cbIUlaz.setPrefWidth(410);

        tfCena = new TextField();
        tfCena.setPromptText("[din/kg]");
        tfCena.setMaxWidth(200);

        cbGajba = new ComboBox<Gajba>();
        cbGajba.setPrefWidth(410);

        tfKolicinaBruto = new TextField();
        tfKolicinaBruto.setPromptText("[kg]");
        tfKolicinaBruto.setPrefWidth(200);
        tfKolicinaBruto.textProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!(String.valueOf(newValue).equals("")) && cbGajba.getSelectionModel().getSelectedItem()!=null && !(tfUlazGajbe.getText().equals(""))) {
                    int gajbeUlaz = 0;
                    double tezinaGajbve =0;
                    double bruto = 0;
                    double neto = 0;

                    try {
                        gajbeUlaz = Integer.valueOf(tfUlazGajbe.getText());
                        tezinaGajbve = cbGajba.getSelectionModel().getSelectedItem().getTezina();
                        bruto = Double.valueOf(tfKolicinaBruto.getText());
                        neto = bruto - (gajbeUlaz*tezinaGajbve);
                    } catch (Exception e) {
                        Alert ada = new Alert(Alert.AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
                        ada.show();
                        tfKolicinaBruto.clear();
                        return;
                    }

                    tfKolicnaNeto.setText(String.valueOf(neto));
                    tfTara.setText(String.valueOf(tezinaGajbve));
                }else {
                    tfKolicnaNeto.clear();
                    tfTara.clear();
                }
            }
        });

        tfTara = new TextField();
        tfTara.setPromptText("[kg]");
        tfTara.setPrefWidth(200);

        tfKolicnaNeto = new TextField();
        tfKolicnaNeto.setPromptText("[kg]");
        tfKolicnaNeto.setPrefWidth(200);

        tfUlazGajbe = new TextField();
        tfUlazGajbe.setPromptText("[kom.]");
        tfUlazGajbe.setPrefWidth(200);
        tfUlazGajbe.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!(String.valueOf(newValue).equals("")) && cbGajba.getSelectionModel().getSelectedItem()!=null && !(tfKolicinaBruto.getText().equals(""))) {
                    int gajbeUlaz = 0;
                    double tezinaGajbve =0;
                    double bruto = 0;
                    double neto = 0;

                    try {
                        gajbeUlaz = Integer.valueOf(tfUlazGajbe.getText());
                        tezinaGajbve = cbGajba.getSelectionModel().getSelectedItem().getTezina();
                        bruto = Double.valueOf(tfKolicinaBruto.getText());
                        neto = bruto - (gajbeUlaz*tezinaGajbve);
                    } catch (Exception e) {
                        Alert ada = new Alert(Alert.AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
                        ada.show();
                        tfUlazGajbe.clear();
                        return;
                    }

                    tfKolicnaNeto.setText(String.valueOf(neto));
                    tfTara.setText(String.valueOf(tezinaGajbve));
                }
                else if(!(String.valueOf(newValue).equals("")) && cbGajba.getSelectionModel().getSelectedItem()!=null){
                    int gajbeUlaz = Integer.valueOf(newValue);
                    double tezinaGajbve = cbGajba.getSelectionModel().getSelectedItem().getTezina();

                    tfTara.setText(String.valueOf(gajbeUlaz * tezinaGajbve));

                }else {
                    tfKolicnaNeto.clear();
                    tfTara.clear();
                }

            }
        });

        tfIzlazGajbe = new TextField();
        tfIzlazGajbe.setPromptText("[kom.]");
        tfIzlazGajbe.setPrefWidth(200);

        cbPrevoznik = new ComboBox<Prevoznik>();
        cbPrevoznik.setPrefWidth(620);

        //Dodavanje u grid pane///////////////////////////////////////////////////////////////////

        //dodavanje polja za unos
        lsifra = new Label("Šifra unosa:");
        unosGp.add(lsifra,0,2,1,1);
        unosGp.add(tfSifra,1,2,1,1);


        ldatum = new Label("Datum:");
        unosGp.add(ldatum,0,3,1,1);
        unosGp.add(dpDatum,1,3,2,1);
        unosGp.add(ldatumobavestenje,3,3,2,1);
        unosGp.setHalignment(ldatumobavestenje, HPos.LEFT);


        lproizvodjac = new Label("Proizvođač:");
        unosGp.add(lproizvodjac,0,4);
        unosGp.add(cbProizvodjac,1,4,3,1);
        unosGp.add(tfSifraPrizvodjaca,4,4,1,1);

        lvrstulaza = new Label("Vrsta ulaza:");
        unosGp.add(lvrstulaza,0,5,1,1);
        unosGp.add(cbIUlaz,1,5,2,1);

        lcena = new Label("cena:");
        unosGp.add(lcena,3,5,1,1);
        unosGp.setHalignment(lcena, HPos.RIGHT);
        unosGp.add(tfCena,4,5,1,1);

        lamblaza = new Label("Amblaža:");
        unosGp.add(lamblaza,0,6,1,1);
        lulaz = new Label("ulaz:");
        unosGp.add(lulaz,1,6,1,1);
        unosGp.setHalignment(lulaz, HPos.RIGHT);
        unosGp.add(tfUlazGajbe,2,6,1,1);
        lizlaz = new Label("izlaz:");
        unosGp.add(lizlaz,3,6,1,1);
        unosGp.setHalignment(lizlaz, HPos.RIGHT);
        unosGp.add(tfIzlazGajbe,4,6,1,1);

        lvrstagajbe = new Label("Ambalaža:");
        unosGp.add(lvrstagajbe,0,7,1,1);
        unosGp.add(cbGajba,1,7,2,1);
        ltara = new Label("tara:");
        unosGp.add(ltara,3,7,1,1);
        unosGp.setHalignment(ltara, HPos.RIGHT);
        unosGp.add(tfTara,4,7,1,1);

        lkolicina = new Label("Količina:");
        unosGp.add(lkolicina,0,8,1,1);
        lbruto = new Label("bruto:");
        unosGp.add(lbruto,1,8,1,1);
        unosGp.setHalignment(lbruto, HPos.RIGHT);
        unosGp.add(tfKolicinaBruto,2,8,1,1);
        lneto = new Label("neto:");
        unosGp.add(lneto,3,8,1,1);
        unosGp.setHalignment(lneto, HPos.RIGHT);
        unosGp.add(tfKolicnaNeto,4,8,1,1);

        lprevoznik = new Label("Prevoznik:");
        unosGp.add(lprevoznik,0,9,1,1);
        unosGp.add(cbPrevoznik,1,9,3,1);

        //DUGMICI DOLE///////////////////////////////////////////////////////////////////////////////

        BStampaj = new Button("Štampaj");
        BStampaj.setDisable(true);
        BStampaj.setPrefWidth(200);
        ImageView stampaj = new ImageView(Firma.getInstance().getPrintIco());
        BStampaj.setGraphic(stampaj);
        BStampaj.setOnAction(new StampajKontroler());

        BSacuvajiStampaj = new Button("Štampaj i Sačuvaj");
        BSacuvajiStampaj.setDisable(true);
        BSacuvajiStampaj.setPrefWidth(410);
        ImageView stampaj2 = new ImageView(Firma.getInstance().getPrintIco());
        ImageView sacuvaj2 = new ImageView(Firma.getInstance().getSaveIco());
        HBox slikeHB = new HBox(5);
        slikeHB.getChildren().addAll(stampaj2,sacuvaj2);
        BSacuvajiStampaj.setGraphic(slikeHB);
        BSacuvajiStampaj.setOnAction(new SacuvajIStampajKontroler());

        //dodavanje donjih dugmica
        unosGp.add(BStampaj,0,11,1,1);
        unosGp.add(BSacuvajiStampaj,1,11,2,1);

        SetUnosDisable();


    }

    public void updateCBProizvodjac() {

        cbProizvodjac.getItems().clear();
        cbProizvodjac.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getProizvodjaci());
        cbProizvodjac.setTooltip(new Tooltip());
        new ComboBoxAutoComplete<Proizvodjac>(cbProizvodjac);

        cbProizvodjac.valueProperty().addListener(new ChangeListener<Proizvodjac>() {
            public void changed(ObservableValue<? extends Proizvodjac> observable, Proizvodjac oldValue,Proizvodjac newValue) {
                if(newValue != null) {
                    cbPrevoznik.getSelectionModel().select(newValue.getPrevoznik());
                    tfSifraPrizvodjaca.setText(String.valueOf(newValue.getSifra()));
                }
                else
                    tfSifraPrizvodjaca.clear();
            }
        });
    }

    public void updateCBUlaz() {

        cbIUlaz.getItems().clear();
        cbIUlaz.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getUlazi());
        cbIUlaz.setTooltip(new Tooltip());
        new ComboBoxAutoComplete<Ulaz>(cbIUlaz);

        cbIUlaz.valueProperty().addListener(new ChangeListener<Ulaz>() {
            public void changed(ObservableValue<? extends Ulaz> observable, Ulaz oldValue, Ulaz newValue) {
                if(observable != null && dpDatum.getValue()!= null && cbPrevoznik.getValue() != null && cbProizvodjac.getValue() != null) {
                    if(cbPrevoznik.getValue() == Firma.getInstance().getTrenutnaGodina().getLicno()) {
                        try {
                            tfCena.setText(String.valueOf(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(cbIUlaz.getValue(), dpDatum.getValue()).getCenaSaPrevozom()));
                        } catch (Exception e) {
                            tfCena.clear();
                        }
                    }
                    else {
                        try {
                            tfCena.setText(String.valueOf(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(cbIUlaz.getValue(), dpDatum.getValue()).getCenaBezPrevoza()));
                        }catch (Exception e) {
                            tfCena.clear();
                        }
                    }
                }
            }
        });
    }

    public void updateCBPrevoznik() {

        cbPrevoznik.getItems().clear();
        cbPrevoznik.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getPrevoznici());
        cbPrevoznik.setTooltip(new Tooltip());
        new ComboBoxAutoComplete<Prevoznik>(cbPrevoznik);

        cbPrevoznik.valueProperty().addListener(new ChangeListener<Prevoznik>() {
            public void changed(ObservableValue<? extends Prevoznik> observable, Prevoznik oldValue,Prevoznik newValue) {
                if(observable != null && dpDatum.getValue()!= null && cbIUlaz.getValue() != null && cbProizvodjac.getValue() != null) {
                    if(cbPrevoznik.getValue() == Firma.getInstance().getTrenutnaGodina().getLicno()) {
                        tfCena.setText(String.valueOf(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(cbIUlaz.getValue(), dpDatum.getValue()).getCenaSaPrevozom()));
                    }
                    else {
                        tfCena.setText(String.valueOf(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(cbIUlaz.getValue(), dpDatum.getValue()).getCenaBezPrevoza()));
                    }
                }
            }
        });
    }

    public void updateCBGajba() {

        cbGajba.getItems().clear();
        cbGajba.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getGajbe());
        cbGajba.setTooltip(new Tooltip());
        new ComboBoxAutoComplete<Gajba>(cbGajba);

        cbGajba.valueProperty().addListener(new ChangeListener<Gajba>() {
            public void changed(ObservableValue<? extends Gajba> observable, Gajba oldValue, Gajba newValue) {
                if(newValue!=null && !tfUlazGajbe.getText().equals("") && !tfKolicinaBruto.getText().equals("")) {
                    int gajbeUlaz = 0;
                    double tezinaGajbve = 0;
                    double bruto = 0;
                    double neto = 0;

                    try {
                        gajbeUlaz = Integer.valueOf(tfUlazGajbe.getText());
                        tezinaGajbve = newValue.getTezina();
                        bruto = Double.valueOf(tfKolicinaBruto.getText());
                        neto = bruto - (gajbeUlaz * tezinaGajbve);
                    } catch (Exception e) {
                        Alert ada = new Alert(Alert.AlertType.ERROR, "Neispravan format AAAA unosa (slovo/broj)!");
                        ada.show();
                        tfUlazGajbe.clear();
                        return;
                    }
                    tfKolicnaNeto.setText(String.valueOf(neto));
                }
                else if(newValue!=null && !tfUlazGajbe.getText().equals("")){
                    int gajbeUlaz = Integer.valueOf(tfUlazGajbe.getText());
                    double tezinaGajbve = newValue.getTezina();

                    tfTara.setText(String.valueOf(gajbeUlaz * tezinaGajbve));

                }
                else {
                    tfKolicnaNeto.clear();
                    tfTara.clear();
                }
            }
        });
    }

    private void tabela() {

        tabela = new TableView<UnosUlaza>();

        TableColumn <UnosUlaza, Integer> tcSifra = new TableColumn<UnosUlaza,Integer>("šifra");
        tcSifra.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Integer>("sifra"));

        TableColumn <UnosUlaza, Date> tcDatum = new TableColumn<UnosUlaza,Date>("datum:");
        tcDatum.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Date>("datum"));

        TableColumn <UnosUlaza, Proizvodjac> tcProizvodjac = new TableColumn<UnosUlaza,Proizvodjac>("proizvođač");
        tcProizvodjac.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Proizvodjac>("proizvodjac"));

        TableColumn <UnosUlaza, Izlaz> tcUlaz = new TableColumn<UnosUlaza,Izlaz>("vrsta ulaza");
        tcUlaz.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Izlaz>("ulaz"));

        TableColumn <UnosUlaza, Gajba> tcGajba = new TableColumn<UnosUlaza,Gajba>("vrsta gajbe");
        tcGajba.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Gajba>("gajba"));

        TableColumn <UnosUlaza, Integer> tcBrGajbi = new TableColumn<UnosUlaza,Integer>("br. gajbi");
        tcBrGajbi.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Integer>("gajbe"));

        TableColumn <UnosUlaza, Double> tcKolicnaBruto = new TableColumn<UnosUlaza,Double>("količina bruto");
        tcKolicnaBruto.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Double>("kolicinaBruto"));

        TableColumn <UnosUlaza, Double> tcKolicinaNeto = new TableColumn<UnosUlaza,Double>("količina neto");
        tcKolicinaNeto.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Double>("kolicinaNeto"));

        TableColumn <UnosUlaza, Prevoznik> tcPrevoznik = new TableColumn<UnosUlaza,Prevoznik>("prevoznik");
        tcPrevoznik.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Prevoznik>("prevoznik"));

        tabela.getColumns().addAll(tcSifra,tcDatum,tcProizvodjac,tcUlaz,tcBrGajbi,tcKolicnaBruto,tcKolicinaNeto,tcGajba,tcPrevoznik);

        updateTabele();

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {  //kad se selektuje nesto u tabeli da se updatuju dugmici
            if (newSelection != null && unosNovog == false && izmenaUToku == false) {
                BObrisi.setDisable(false);
                BIzmeni.setDisable(false);
                BPonisti.setDisable(false);
                BDodaj.setDisable(true);
                BStampaj.setDisable(false);
            }
        });

        tabela.scrollTo(tabela.getItems().size()-1);

    }

    public void updateTabele () {   				//dodaje djuture u tabelu kao observabl listu
        tabela.getItems().clear();
        tabela.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()));
        tabela.scrollTo(tabela.getItems().size()-1);
    }

    private void trakaZaPretragu() {

        trakaZaPretragu1HB = new HBox(10);
        trakaZaPretragu1HB.setStyle("-fx-font: 17px \"Serif\";");
        trakaZaPretragu1HB.setAlignment(Pos.CENTER_LEFT);
        ImageView search = new ImageView(Firma.getInstance().getSearchIco());
        trakaZaPretragu1HB.getChildren().add(search);
        Label pl = new Label("Pretraga/filtriranje:  ");
        pl.setStyle("-fx-font: 25px \"System\";");
        trakaZaPretragu1HB.getChildren().add(pl);
        Label ldan = new Label("  dan");
        ldan.setPrefWidth(40);
        RBDanPretraga = new RadioButton();
        RBDanPretraga.setGraphic(ldan);
        RBPeriodPretraga = new RadioButton("period");
        TGDanPeriodPretraga = new ToggleGroup();
        RBDanPretraga.setToggleGroup(TGDanPeriodPretraga);
        RBPeriodPretraga.setToggleGroup(TGDanPeriodPretraga);
        trakaZaPretragu1HB.getChildren().addAll(RBDanPretraga,RBPeriodPretraga);
        RBDanPretraga.setSelected(true);
        DPPocetniPretraga = new DatePicker();
        DPPocetniPretraga.setPrefWidth(160);
        DPkrajnjiPretraga = new DatePicker();
        DPkrajnjiPretraga.setPrefWidth(160);
        Lod = new Label("datum:");
        Ldo = new Label("do:");
        trakaZaPretragu1HB.getChildren().addAll(Lod,DPPocetniPretraga);
        BPonistiPretragu1 = new Button("Poništi");
        ImageView close = new ImageView(Firma.getInstance().getCloseIco());
        BPonistiPretragu1.setGraphic(close);
        BPonistiPretragu1.setDisable(true);
        BPonistiPretragu1.setPrefWidth(110);
        trakaZaPretragu1HB.getChildren().add(BPonistiPretragu1);
        BPonistiPretragu1.setOnAction(new PonistiPretraguKontroler1());

        trakaZaPretragu2HB = new HBox(10);
        trakaZaPretragu2HB.setStyle("-fx-font: 17px \"Serif\";");
        trakaZaPretragu2HB.setAlignment(Pos.CENTER_LEFT);
        ImageView search2 = new ImageView(Firma.getInstance().getSearchIco());
        trakaZaPretragu2HB.getChildren().add(search2);
        Label pl2 = new Label("Pretraga/filtriranje:  ");
        pl2.setStyle("-fx-font: 25px \"System\";");
        trakaZaPretragu2HB.getChildren().add(pl2);

        CBProizvodjacPretraga = new ComboBox<Proizvodjac>();
        CBProizvodjacPretraga.setPrefWidth(260);
        Label l1 = new Label("proizvođač:");
        CBUlazPretraga = new ComboBox<Ulaz>();
        CBUlazPretraga.setPrefWidth(260);
        Label l2 = new Label("ulaz:");
        CBPrevoznikPretraga = new ComboBox<Prevoznik>();
        CBPrevoznikPretraga.setPrefWidth(260);
        Label l3 = new Label("prevoznik:");
        trakaZaPretragu2HB.getChildren().addAll(l1,CBProizvodjacPretraga,l2,CBUlazPretraga,l3,CBPrevoznikPretraga);
        BPonistiPretragu2 = new Button("Poništi");
        ImageView close2 = new ImageView(Firma.getInstance().getCloseIco());
        BPonistiPretragu2.setGraphic(close2);
        BPonistiPretragu2.setDisable(true);
        BPonistiPretragu2.setPrefWidth(110);
        trakaZaPretragu2HB.getChildren().add(BPonistiPretragu2);
        BPonistiPretragu2.setOnAction(new PonistiPretraguKontroler2());


        RBDanPretraga.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(RBDanPretraga.isSelected()) {
                    trakaZaPretragu1HB.getChildren().removeAll(Ldo,DPkrajnjiPretraga);
                    DPkrajnjiPretraga.setValue(null);
                    DPPocetniPretraga.setValue(null);
                    pozicija=6;
                    Lod.setText("datum:");;
                }
            }
        });

        RBPeriodPretraga.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(RBPeriodPretraga.isSelected()) {
                    trakaZaPretragu1HB.getChildren().add(pozicija, Ldo);
                    trakaZaPretragu1HB.getChildren().add(pozicija+1, DPkrajnjiPretraga);
                    DPkrajnjiPretraga.setValue(null);
                    DPPocetniPretraga.setValue(null);
                    pozicija=8;
                    Lod.setText("od:");
                }
            }
        });

        DPPocetniPretraga.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                RBFiltriraniObracun.setSelected(true);
                tabela.getItems().clear();
                ArrayList<UnosUlaza> uulist = new ArrayList<UnosUlaza>();
                uulist = null;
                uulist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseUlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPkrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBUlazPretraga.getSelectionModel().getSelectedItem(), CBPrevoznikPretraga.getSelectionModel().getSelectedItem());
                tabela.getItems().addAll(uulist);

                if(DPPocetniPretraga.getValue()!= null)
                    BPonistiPretragu1.setDisable(false);
            }
        });

        DPkrajnjiPretraga.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                RBFiltriraniObracun.setSelected(true);
                tabela.getItems().clear();
                ArrayList<UnosUlaza> uulist = new ArrayList<UnosUlaza>();
                uulist = null;
                uulist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseUlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPkrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBUlazPretraga.getSelectionModel().getSelectedItem(), CBPrevoznikPretraga.getSelectionModel().getSelectedItem());
                tabela.getItems().addAll(uulist);

                if(DPkrajnjiPretraga.getValue()!= null)
                    BPonistiPretragu1.setDisable(false);
            }
        });

        updateCBProizvodjacPretraga();
        updateCBUlazPretraga();
        updateCBPrevoznikPretraga();

    }

    public void updateCBProizvodjacPretraga() {

        CBProizvodjacPretraga.getItems().clear();
        CBProizvodjacPretraga.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getProizvodjaci());
        CBProizvodjacPretraga.setTooltip(new Tooltip());
        new ComboBoxAutoComplete<Proizvodjac>(CBProizvodjacPretraga);
        CBProizvodjacPretraga.valueProperty().addListener(new ChangeListener<Proizvodjac>() {
            public void changed(ObservableValue<? extends Proizvodjac> observable, Proizvodjac oldValue, Proizvodjac newValue) {
                RBFiltriraniObracun.setSelected(true);
                tabela.getItems().clear();
                ArrayList<UnosUlaza> uulist = new ArrayList<UnosUlaza>();
                uulist = null;
                uulist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseUlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPkrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBUlazPretraga.getSelectionModel().getSelectedItem(), CBPrevoznikPretraga.getSelectionModel().getSelectedItem());
                tabela.getItems().addAll(uulist);

                if(newValue != null)
                    BPonistiPretragu2.setDisable(false);
            }
        });
    }

    public void updateCBUlazPretraga() {

        CBUlazPretraga.getItems().clear();
        CBUlazPretraga.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getUlazi());
        CBUlazPretraga.setTooltip(new Tooltip());
        new ComboBoxAutoComplete<Ulaz>(CBUlazPretraga);
        CBUlazPretraga.valueProperty().addListener(new ChangeListener<Ulaz>() {
            public void changed(ObservableValue<? extends Ulaz> observable, Ulaz oldValue, Ulaz newValue) {
                RBFiltriraniObracun.setSelected(true);
                tabela.getItems().clear();
                ArrayList<UnosUlaza> uulist = new ArrayList<UnosUlaza>();
                uulist = null;
                uulist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseUlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPkrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBUlazPretraga.getSelectionModel().getSelectedItem(), CBPrevoznikPretraga.getSelectionModel().getSelectedItem());
                tabela.getItems().addAll(uulist);

                if(newValue != null)
                    BPonistiPretragu2.setDisable(false);
            }
        });
    }

    public void updateCBPrevoznikPretraga() {

        CBPrevoznikPretraga.getItems().clear();
        CBPrevoznikPretraga.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getPrevoznici());
        CBPrevoznikPretraga.setTooltip(new Tooltip());
        new ComboBoxAutoComplete<Prevoznik>(CBPrevoznikPretraga);
        CBPrevoznikPretraga.valueProperty().addListener(new ChangeListener<Prevoznik>() {
            public void changed(ObservableValue<? extends Prevoznik> observable, Prevoznik oldValue,Prevoznik newValue) {
                RBFiltriraniObracun.setSelected(true);
                tabela.getItems().clear();
                ArrayList<UnosUlaza> uulist = new ArrayList<UnosUlaza>();
                uulist = null;
                uulist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseUlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPkrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBUlazPretraga.getSelectionModel().getSelectedItem(), CBPrevoznikPretraga.getSelectionModel().getSelectedItem());
                tabela.getItems().addAll(uulist);

                if(newValue != null)
                    BPonistiPretragu2.setDisable(false);
            }
        });
    }

    public void trakaZaObracun() {

        obracunHB = new HBox(10);
        obracunHB.setStyle("-fx-font: 17px \"Serif\";");
        obracunHB.setAlignment(Pos.BASELINE_LEFT);
        ImageView obracun = new ImageView(Firma.getInstance().getObracunIco());
        obracunHB.getChildren().add(obracun);
        Label ol = new Label("Brz obračun:  ");
        ol.setStyle("-fx-font: 25px \"System\";");
        obracunHB.getChildren().add(ol);
        Label lll = new Label("Obračunaj za:  ");
        obracunHB.getChildren().add(lll);
        RBSviObracun = new RadioButton("sve");
        RBFiltriraniObracun = new RadioButton("filtrirane");
        TGObracun = new ToggleGroup();
        RBSviObracun.setToggleGroup(TGObracun);
        RBFiltriraniObracun.setToggleGroup(TGObracun);
        RBSviObracun.setSelected(true);
        obracunHB.getChildren().addAll(RBSviObracun,RBFiltriraniObracun);
        BObracunaj = new Button("Obračunaj");
        ImageView obracunaj = new ImageView(Firma.getInstance().getObracunIco());
        BObracunaj.setGraphic(obracunaj);
        BObracunaj.setPrefWidth(130);
        BObracunaj.setOnAction(new ObracunajKontroler());
        obracunHB.getChildren().add(BObracunaj);

    }

    public void trakaZaStampuIzvestaja() {

        stampaIzvestajaHB = new HBox(10);
        stampaIzvestajaHB.setStyle("-fx-font: 17px \"Serif\";");
        stampaIzvestajaHB.setAlignment(Pos.BASELINE_LEFT);
        ImageView st = new ImageView(Firma.getInstance().getPrintIco());
        stampaIzvestajaHB.getChildren().add(st);
        Label l1 = new Label("Štampa izveštaja:   ");
        l1.setStyle("-fx-font: 25px \"System\";");
        stampaIzvestajaHB.getChildren().add(l1);
        Label l2 = new Label("Vrsta izveštaja:   ");
        stampaIzvestajaHB.getChildren().add(l2);
        RBDnevniIzvestaj = new RadioButton("dnevni");
        RBPeriodicniIzvestaj = new RadioButton("periodični   ");
        RBPeriodicniPoDanimaIzvestaj = new RadioButton("perioodični po danima");
        TGIzvestaj = new ToggleGroup();
        RBDnevniIzvestaj.setToggleGroup(TGIzvestaj);
        RBPeriodicniIzvestaj.setToggleGroup(TGIzvestaj);
        RBPeriodicniPoDanimaIzvestaj.setToggleGroup(TGIzvestaj);
        RBDnevniIzvestaj.setSelected(true);
        stampaIzvestajaHB.getChildren().addAll(RBDnevniIzvestaj,RBPeriodicniIzvestaj,RBPeriodicniPoDanimaIzvestaj);
        DPPocetniIzvestraj = new DatePicker();
        DPPocetniIzvestraj.setPrefWidth(160);
        DPKrajnjiIzvestaj = new DatePicker();
        DPKrajnjiIzvestaj.setPrefWidth(160);
        LodIzvestaj = new Label("Za datum:");
        ldoIzvestaj = new Label("do:");
        ldoIzvestaj.setFont(new Font(15));
        LodIzvestaj.setFont(new Font(15));
        stampaIzvestajaHB.getChildren().addAll(LodIzvestaj,DPPocetniIzvestraj);
        BStamopajIzvestaj = new Button("Štampaj");
        ImageView ici = new ImageView(Firma.getInstance().getPrintIco());
        BStamopajIzvestaj.setGraphic(ici);
        BStamopajIzvestaj.setPrefWidth(110);
        BStamopajIzvestaj.setOnAction(new StampajIzvestajKontroler());
        stampaIzvestajaHB.getChildren().add(BStamopajIzvestaj);

        RBDnevniIzvestaj.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(RBDnevniIzvestaj.isSelected() && izvestajTip == 2) {
                    izvestajTip = 1;
                    stampaIzvestajaHB.getChildren().removeAll(ldoIzvestaj,DPKrajnjiIzvestaj);
                    DPKrajnjiIzvestaj.setValue(null);
                    DPPocetniIzvestraj.setValue(null);
                    LodIzvestaj.setText("Za datum:");
                }

            }
        });

        RBPeriodicniIzvestaj.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(RBPeriodicniIzvestaj.isSelected() && izvestajTip == 1) {
                    izvestajTip = 2;
                    stampaIzvestajaHB.getChildren().add(8, ldoIzvestaj);
                    stampaIzvestajaHB.getChildren().add(9, DPKrajnjiIzvestaj);
                    DPKrajnjiIzvestaj.setValue(null);
                    DPPocetniIzvestraj.setValue(null);
                    LodIzvestaj.setText("od:");

                }

            }
        });

        RBPeriodicniPoDanimaIzvestaj.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(RBPeriodicniPoDanimaIzvestaj.isSelected() && izvestajTip == 1){
                    izvestajTip = 2;
                    stampaIzvestajaHB.getChildren().add(8, ldoIzvestaj);
                    stampaIzvestajaHB.getChildren().add(9, DPKrajnjiIzvestaj);
                    DPKrajnjiIzvestaj.setValue(null);
                    DPPocetniIzvestraj.setValue(null);
                    LodIzvestaj.setText("od:");
                }
            }
        });

    }

    public void trakaZaStampuPaletnihListova() {

        stampaHB = new HBox(10);
        stampaHB.setStyle("-fx-font: 17px \"Serif\";");
        stampaHB.setAlignment(Pos.BASELINE_LEFT);
        ImageView st = new ImageView(Firma.getInstance().getPrintIco());
        stampaHB.getChildren().add(st);
        Label l1 = new Label("Štampa obeleživača:   ");
        l1.setStyle("-fx-font: 25px \"System\";");
        stampaHB.getChildren().add(l1);
        Label l2 = new Label("Štampaj:   ") ;
        stampaHB.getChildren().add(l2);

        cbObeleživač = new ComboBox<String>();
        cbObeleživač.setPromptText("(izaberi)");
        cbObeleživač.getItems().addAll("Karton identifikacije", "Miker obeleživač", "Fertodi obeleživač", "Enrosadira obeleživač", "Tulamin obeleživač","Polka obeleživač", "Kupina obeleživač" );
        cbObeleživač.setTooltip(new Tooltip());
        new ComboBoxAutoComplete<String>(cbObeleživač);


        BStamajPaletniList = new Button("Štampaj");
        ImageView ici = new ImageView(Firma.getInstance().getPrintIco());
        BStamajPaletniList.setGraphic(ici);
        BStamajPaletniList.setDisable(true);
        BStamajPaletniList.setPrefWidth(110);
        BStamajPaletniList.setOnAction(new StampajKIKontroler());

        cbObeleživač.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                BStamajPaletniList.setDisable(false);
            }
        });

        stampaHB.getChildren().addAll( cbObeleživač, BStamajPaletniList);
    }

    public void ocistiPoljaZaUnos() {   //cisti sva polja za unos

        tfSifra.clear();
        dpDatum.setValue(null);
        cbIUlaz.getSelectionModel().clearSelection();
        updateCBUlaz();
        cbProizvodjac.getSelectionModel().clearSelection();
        updateCBProizvodjac();
        //tfCena.clear();
        tfKolicinaBruto.clear();
        tfKolicnaNeto.clear();
        tfUlazGajbe.clear();
        tfIzlazGajbe.clear();
        tfCena.clear();
        tfTara.clear();
        cbPrevoznik.getSelectionModel().clearSelection();
        updateCBPrevoznik();
        cbGajba.getSelectionModel().clearSelection();
        updateCBGajba();
    }

    public void SetUnosDisable(){

        ldatum.setDisable(true);
        dpDatum.setDisable(true);
        lproizvodjac.setDisable(true);
        cbProizvodjac.setDisable(true);
        tfSifraPrizvodjaca.setDisable(true);
        lvrstulaza.setDisable(true);
        cbIUlaz.setDisable(true);
        lamblaza.setDisable(true);
        lulaz.setDisable(true);
        lizlaz.setDisable(true);
        tfUlazGajbe.setDisable(true);
        tfIzlazGajbe.setDisable(true);
        lvrstagajbe.setDisable(true);
        cbGajba.setDisable(true);
        lkolicina.setDisable(true);
        lbruto.setDisable(true);
        lneto.setDisable(true);
        tfKolicinaBruto.setDisable(true);
        tfKolicnaNeto.setDisable(true);
        lprevoznik.setDisable(true);
        cbPrevoznik.setDisable(true);
        lcena.setDisable(true);
        tfCena.setDisable(true);
        ltara.setDisable(true);
        tfTara.setDisable(true);
        ldatumobavestenje.setDisable(true);
        lsifra.setDisable(true);
        tfSifra.setDisable(true);
    }

    public void SetUnosEnable(){

        ldatum.setDisable(false);
        dpDatum.setDisable(false);
        lproizvodjac.setDisable(false);
        cbProizvodjac.setDisable(false);
        tfSifraPrizvodjaca.setDisable(false);
        lvrstulaza.setDisable(false);
        cbIUlaz.setDisable(false);
        lamblaza.setDisable(false);
        lulaz.setDisable(false);
        lizlaz.setDisable(false);
        tfUlazGajbe.setDisable(false);
        tfIzlazGajbe.setDisable(false);
        lvrstagajbe.setDisable(false);
        cbGajba.setDisable(false);
        lkolicina.setDisable(false);
        lbruto.setDisable(false);
        lneto.setDisable(true);
        tfKolicinaBruto.setDisable(false);
        tfKolicnaNeto.setDisable(true);
        lprevoznik.setDisable(false);
        cbPrevoznik.setDisable(false);
        lcena.setDisable(true);
        tfCena.setDisable(true);
        ltara.setDisable(true);
        tfTara.setDisable(true);
        ldatumobavestenje.setDisable(false);
        lsifra.setDisable(false);
        tfSifra.setDisable(false);
    }

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


    public static BrzUnosUlazaTab getInstance() {
        if(instance == null) {
            instance = new BrzUnosUlazaTab();
        }
        return instance;
    }

    public void setUnosNovog(boolean unosNovog) {
        this.unosNovog = unosNovog;
    }

    public void setIzmenaUToku(boolean izmenaUToku) {
        this.izmenaUToku = izmenaUToku;
    }

    public DatePicker getDpDatum() {
        return dpDatum;
    }

    public ComboBox<Gajba> getCbGajba() {
        return cbGajba;
    }

    public TextField getTfIzlazGajbe() {
        return tfIzlazGajbe;
    }

    public Button getBDodaj() {
        return BDodaj;
    }

    public Button getBSacuvaj() {
        return BSacuvaj;
    }

    public Button getBPonisti() {
        return BPonisti;
    }

    public Button getBSacuvajiStampaj() {
        return BSacuvajiStampaj;
    }

    public Button getBIzmeni() {
        return BIzmeni;
    }

    public Button getBObrisi() {
        return BObrisi;
    }

    public Button getBStampaj() {
        return BStampaj;
    }

    public ComboBox<Proizvodjac> getCbProizvodjac() {
        return cbProizvodjac;
    }

    public ComboBox<Ulaz> getCbIUlaz() {
        return cbIUlaz;
    }

    public ComboBox<Prevoznik> getCbPrevoznik() {
        return cbPrevoznik;
    }

    public ComboBox<String> getCbObeleživač() {
        return cbObeleživač;
    }

    public TextField getTfUlazGajbe() {
        return tfUlazGajbe;
    }

    public TextField getTfKolicinaBruto() {
        return tfKolicinaBruto;
    }

    public TextField getTfKolicnaNeto() {
        return tfKolicnaNeto;
    }

    public boolean isIzmenaUToku() {
        return  izmenaUToku;
    }

    public TableView<UnosUlaza> getTabela() {
        return tabela;
    }

    public boolean isUnosNovog() {
        return unosNovog;
    }

    public RadioButton getRBDnevniIzvestaj() {
        return RBDnevniIzvestaj;
    }

    public DatePicker getDPPocetniIzvestraj() {
        return DPPocetniIzvestraj;
    }

    public RadioButton getRBPeriodicniIzvestaj() {
        return RBPeriodicniIzvestaj;
    }

    public DatePicker getDPKrajnjiIzvestaj() {
        return DPKrajnjiIzvestaj;
    }

    public RadioButton getRBPeriodicniPoDanimaIzvestaj() {
        return RBPeriodicniPoDanimaIzvestaj;
    }

    public DatePicker getDPkrajnjiPretraga() {
        return DPkrajnjiPretraga;
    }

    public DatePicker getDPPocetniPretraga() {
        return DPPocetniPretraga;
    }

    public Button getBPonistiPretragu1() {
        return BPonistiPretragu1;
    }

    public Button getBPonistiPretragu2() {
        return BPonistiPretragu2;
    }

    public RadioButton getRBSviObracun() {
        return RBSviObracun;
    }

    public RadioButton getRBFiltriraniObracun() {
        return RBFiltriraniObracun;
    }

    public TextField getTfSifra() {
        return tfSifra;
    }


}

