package Admin_Panel.Tools_K;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import Admin_Panel.AdminPodesavanjaStage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Potvrdi_AdminSifra_K implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		String sifra = "f371c1e22a57609c9da5193ab56e0263";

		if(!getMD5(AdminPodesavanjaStage.getInstance().getSifraPF().getText()).equals(sifra)) {
			Alert a = new Alert(AlertType.ERROR, "Neispravna šifra!");
			a.show();
			return;
		}else{
			AdminPodesavanjaStage.getInstance().getBPotvrdi().setDisable(true);
			AdminPodesavanjaStage.getInstance().getSifraPF().clear();
			AdminPodesavanjaStage.getInstance().PostaviScenu2();}
		
	}

	public static String getMD5(String msg) {

		byte[] defaultBytes = msg.getBytes();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			return byteToHexString(messageDigest);
		} catch (NoSuchAlgorithmException nsae) {

		}
		return null;
	}

	public static String byteToHexString(byte[] data) {
		StringBuffer results = new StringBuffer();
		for (byte byt : data)
			results.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
		return results.toString();
	}

}
