package editor;

public class DeletedLetter implements EditAction{

    private TextEditorModel model;
    private char letter;
    private Location cursor;

    public DeletedLetter(TextEditorModel model, char letter, Location cursor) {
        this.model = model;
        this.letter = letter;
        this.cursor = cursor;
    }

    @Override
    public void execute_do() {
        model.setCursorLocation(cursor);
        model.deleteAfter(false);
    }

    @Override
    public void execute_undo() {
        model.setCursorLocation(cursor);
        model.insert(letter, false);
    }
}