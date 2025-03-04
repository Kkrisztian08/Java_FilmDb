package hu.petrik.filmdb.controllers;

import hu.petrik.filmdb.Controller;
import hu.petrik.filmdb.FilmDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HozzadController extends Controller {
    @FXML
    private TextField inputCim;
    @FXML
    private TextField inputKategoria;
    @FXML
    private Spinner<Integer> inputHossz;
    @FXML
    private ChoiceBox<Integer> inputErtekeles;

    @FXML
    public void onHozzadButtonClick(ActionEvent actionEvent)  {
        String cim = inputCim.getText().trim();
        String kategoria = inputKategoria.getText().trim();
        int hossz = 0;
        int ertekelesIndex = inputErtekeles.getSelectionModel().getSelectedIndex();
        if (cim.isEmpty()){
            alert("Cím megadása kötelező");
            return;
        }
        if (kategoria.isEmpty()){
            alert("Kategória megadása kötelező");
            return;
        }
        try {
            hossz = inputHossz.getValue();
        } catch (NullPointerException ex){
            alert("A hossz megadása kötelező");
            return;
        } catch (Exception ex){
            System.out.println(ex);
            alert("A hossz csak 1 és 999 közötti szám lehet");
            return;
        }
        if (hossz < 1 || hossz > 999) {
            alert("A hossz csak 1 és 999 közötti szám lehet");
            return;
        }
        if (ertekelesIndex == -1){
            alert("Értékelés kiválasztása köztelező");
            return;
        }
        System.out.println(hossz);
        int ertekeles = inputErtekeles.getValue();

        try {
            FilmDb db=new FilmDb();
            int siker=db.filmhozzaAdasa(cim,kategoria,hossz,ertekeles);
            if (siker==1) {
                alert("A film hozzáadása sikeres!");
            }else {
                alert("A film hozzáadása sikertelen!");
            }
        }catch (Exception e){
            hibaKiir(e);
        }
    }
}
