package editor.plugins;

import java.util.Iterator;
import editor.ClipboardStack;
import editor.Location;
import editor.LocationRange;
import editor.Plugin;
import editor.TextEditorModel;
import editor.UndoManager;

public class VelikoSlovo implements Plugin {
	
	public VelikoSlovo() {}

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
    	Iterator<String> it = model.allLines();
    	String newText = "";
        while(it.hasNext()) {
        	String line = it.next();
        	char[] letters = line.toCharArray();
        	StringBuilder output = new StringBuilder();
            boolean capitalize = true;

            for(char c : letters) {
                if(Character.isWhitespace(c)) {
                    capitalize = true;
                    output.append(c);
                }else if(capitalize) {
                    output.append(Character.toUpperCase(c));
                    capitalize = false;
                }else {
                    output.append(c);
                }
            }
            newText += output.toString();
        	if(it.hasNext()) {
        		newText += "\n";
        	}
        }
        model.deleteRange(new LocationRange(new Location(0, 0), model.getEndOfDocument()), false);
        model.insert(newText, false);
    }

    @Override
    public String getDescription() {
        return "Pretvara prva slova svih riječi u velika.";
    }

    @Override
    public String getName() {
        return "VelikoSlovo";
    }
}