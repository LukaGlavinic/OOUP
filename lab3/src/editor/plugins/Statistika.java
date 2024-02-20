package editor.plugins;

import editor.ClipboardStack;
import editor.Plugin;
import editor.TextEditorModel;
import editor.UndoManager;

import javax.swing.*;
import java.util.Iterator;

public class Statistika implements Plugin {
	
	public Statistika() {}

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
        int brojLinija = 0, brojRijeci = 0, brojSlova = 0;
        Iterator<String> it = model.allLines();
        while(it.hasNext()) {
        	String linija = it.next();
        	brojLinija++;
        	String[] rijeci = linija.split("[ \t]+");
        	brojRijeci += rijeci.length;
        	for(String rijec : rijeci) {
        		brojSlova += (int) rijec.chars().filter(Character::isLetter).count();
        	}
        }
        String statistika = "Ukupno linija: " + brojLinija + "\nBroj riječi: " + brojRijeci + "\nBroj slova: " + brojSlova;
        JOptionPane.showMessageDialog(model.getParentFrame(), statistika, "Status", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public String getDescription() {
        return "Broji koliko ima redaka, riječi i slova u dokumentu i to prikazuje korisniku u dijalogu.";
    }

    @Override
    public String getName() {
        return "Statistika";
    }
}