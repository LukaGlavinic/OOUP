package editor;

public class InsertedText implements EditAction {
	
	private final TextEditorModel model;
    private final String text;
    private final LocationRange range;

	public InsertedText(TextEditorModel model, String text, LocationRange range) {
		this.model = model;
		this.text = text;
		this.range = range;
	}

	@Override
	public void execute_do() {
		model.setCursorLocation(range.getStart());
		model.insert(text, false);
	}

	@Override
	public void execute_undo() {
		model.deleteRange(range, false);
	}
}