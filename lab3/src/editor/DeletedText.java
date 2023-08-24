package editor;

public class DeletedText implements EditAction {

    private TextEditorModel model;
    private String text;
    private LocationRange range;

    public DeletedText(TextEditorModel model, String text, LocationRange range) {
        this.model = model;
        this.text = text;
        this.range = range;
    }

    @Override
    public void execute_do() {
    	model.deleteRange(range, false);
    }

    @Override
    public void execute_undo() {
        model.setCursorLocation(range.getStart());
        model.insert(text, false);
    }
}