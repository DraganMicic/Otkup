package Main;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Firma;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Print {

    private static Print instance;

    private Font mali_F = new Font(12);
    Font mali_F_POS = new Font(10);
    private Font srednji_F = new Font(20);
    private Font veliki_F = new Font(30);
    private Font bold_F = Font.font("Verdana", FontWeight.BOLD, 12);
    private Font bold_Veliki_F = Font.font("Verdana", FontWeight.BOLD, 20);

    private DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
    private DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

    DateTimeFormatter DateFormater = DateTimeFormatter.ofPattern("dd/MM/YY");
    DateTimeFormatter DateFormater2 = DateTimeFormatter.ofPattern("dd/MM/YYYY");



    private Print(){
        symbols.setGroupingSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);
        formatter.setMaximumFractionDigits(1);
        formatter.setMinimumFractionDigits(1);
    }

    public VBox zaglavlje(){

        VBox podaciPreduzecaVB = new VBox(-5);
        podaciPreduzecaVB.setAlignment(Pos.TOP_LEFT);
        HBox nazivBox = new HBox();
        nazivBox.setAlignment(Pos.BASELINE_LEFT);
        Label preduzece = new Label("pp ");
        preduzece.setFont(srednji_F);
        Label naziv = new Label("\"" + Firma.getInstance().getIme() + "\"");
        naziv.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        Label doo = new Label(" d.o.o.");
        doo.setFont(srednji_F);
        nazivBox.getChildren().addAll(preduzece,naziv,doo);
        Label adresa = new Label(Firma.getInstance().getAdresa());
        adresa.setFont(mali_F);
        Label PIB = new Label("PIB: " + Firma.getInstance().getPib());
        PIB.setFont(mali_F);
        Label MB = new Label("M.B.: " + Firma.getInstance().getMaticniBr());
        MB.setFont(mali_F);
        Label tel = new Label("teli: " + Firma.getInstance().getTelefon1() +"; " + Firma.getInstance().getTelefon2());
        tel.setFont(mali_F);
        Label lajna1 = new Label("--------------------------------------------------------------------------------------------------------------");
        podaciPreduzecaVB.getChildren().addAll(nazivBox,adresa,PIB,MB,tel,lajna1);

        return podaciPreduzecaVB;

    }

    public VBox zaglavljePOS(){

        VBox podaciPreduzecaVB = new VBox(-5);
        podaciPreduzecaVB.setAlignment(Pos.TOP_LEFT);
        HBox nazivBox = new HBox();
        nazivBox.setAlignment(Pos.BASELINE_LEFT);
        Label preduzece = new Label("Preduzeće ");
        preduzece.setFont(new Font(12));
        Label naziv = new Label(Firma.getInstance().getIme());
        naziv.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        Label doo = new Label(" d.o.o.");
        doo.setFont(new Font(12));
        nazivBox.getChildren().addAll(preduzece,naziv,doo);
        Label adresa = new Label(Firma.getInstance().getAdresa());
        adresa.setFont(mali_F_POS);
        Label tekuciracun = new Label("Tekući račun: " + Firma.getInstance().getTekuciRacun());
        tekuciracun.setFont( mali_F_POS);
        Label PIB = new Label("PIB: " + Firma.getInstance().getPib());
        PIB.setFont(mali_F_POS);
        Label MB = new Label("M.B.: " + Firma.getInstance().getMaticniBr());
        MB.setFont(mali_F_POS);
        Label RESREG = new Label("RES.REG-OB-" + Firma.getInstance().getRegBroj());
        RESREG.setFont(mali_F_POS);
        Label tel = new Label("telefoni: " + Firma.getInstance().getTelefon1() +"; " + Firma.getInstance().getTelefon2());
        tel.setFont(mali_F_POS);
        Label lajna1 = new Label("-------------------------------------------");
        podaciPreduzecaVB.getChildren().addAll(nazivBox,adresa,tekuciracun,PIB,MB,RESREG,tel,lajna1);

        return podaciPreduzecaVB;
    }

    public static Print getInstance() {
        if (instance == null) {
            instance = new Print();
        }
        return instance;
    }


    public Font getMali_F() { return mali_F; }

    public void setMali_F(Font mali_F) { this.mali_F = mali_F; }

    public Font getSrednji_F() { return srednji_F; }

    public void setSrednji_F(Font srednji_F) { this.srednji_F = srednji_F; }

    public Font getVeliki_F() { return veliki_F; }

    public void setVeliki_F(Font veliki_F) { this.veliki_F = veliki_F; }

    public Font getBold_F() { return bold_F; }

    public void setBold_F(Font bold_F) { this.bold_F = bold_F; }

    public DecimalFormat getFormatter() {
        return formatter;
    }

    public DateTimeFormatter getDateFormater() {
        return DateFormater;
    }

    public Font getBold_Veliki_F() { return bold_Veliki_F; }

    public DateTimeFormatter getDateFormater2() { return DateFormater2; }
}
