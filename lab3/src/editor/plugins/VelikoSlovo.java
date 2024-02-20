package editor.plugins;

import editor.*;

import java.util.Iterator;

public class VelikoSlovo implements Plugin {
	
	public VelikoSlovo() {}

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
    	Iterator<String> it = model.allLines();
    	StringBuilder newText = new StringBuilder();
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
            newText.append(output);
        	if(it.hasNext()) {
        		newText.append("\n");
        	}
        }
        model.deleteRange(new LocationRange(new Location(0, 0), model.getEndOfDocument()), false);
        model.insert(newText.toString(), false);
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