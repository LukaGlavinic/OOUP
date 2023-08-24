package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class TextEditor extends JFrame implements CursorObserver, TextObserver {
 
	private static final long serialVersionUID = 1L;
	
	private TextEditorModel model;
    private Canvas canvas;
    private JLabel statusBar;

    public TextEditor() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(0, 0);
		setSize(500, 500);

        initGUI("aaaa\nbbbb\ncccc\ndddd");
    }

    public static List<Plugin> getPlugins() {
        String path = "C:\\Users\\Luka\\eclipse-workspace\\OOUP_lab3\\src\\editor\\plugins";
        ArrayList<Plugin> listaPlugina = new ArrayList<>();
        File directory = new File(path);
        File[] files = directory.listFiles();
        List<String> javaFileNames = new ArrayList<>();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                javaFileNames.add(file.getName());
            }
        }
        for (String className : javaFileNames) {
            try {
                String packageName = "editor.plugins";
                String fullClassName = packageName + "." + className.substring(0, className.lastIndexOf('.'));
                Class<?> clazz = Class.forName(fullClassName);
                Constructor<?> ctr = clazz.getConstructor();
                Plugin plugin = (Plugin) ctr.newInstance();
                listaPlugina.add(plugin);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return listaPlugina;
    }

    private void initGUI(String text) {
        model = new TextEditorModel(text);
        model.setParentFrame(this);
        model.addCursorObserver(this);
        model.addTextObserver(this);
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.emptySet());

        createListeners();
        createActions();
        createMenus();
        createToolbar();
        createStatusBar();

        canvas = new Canvas();
        getContentPane().add(canvas, BorderLayout.CENTER);

        model.informSelObservers();
        model.getClipboard().informClipboardObservers();
        UndoManager.instanceOf().informUndoObservers();
        UndoManager.instanceOf().informRedoObservers();
        model.informStatusObservers();
    }

    private void newDokument(String text) {
        model = new TextEditorModel(text);
        model.setParentFrame(this);
        model.addCursorObserver(this);
        model.addTextObserver(this);
        model.addStatusObserver(statusBar);

        createMenus();
        createToolbar();

        updateText();
        model.informSelObservers();
        model.getClipboard().informClipboardObservers();
        UndoManager.instanceOf().informUndoObservers();
        UndoManager.instanceOf().informRedoObservers();
        model.informStatusObservers();
    }

    private void createStatusBar() {
        statusBar = new JLabel("", null, SwingConstants.CENTER);
        getContentPane().add(statusBar, BorderLayout.PAGE_END);
        model.addStatusObserver(statusBar);
    }

    private void createListeners() {
        //UP_key
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    LocationRange oldRange = model.getSelectionRange();
                    oldRange = new LocationRange(new Location(oldRange.getStart().getX(), oldRange.getStart().getY()),
                                new Location(oldRange.getFinish().getX(), oldRange.getFinish().getY()));
                    Location oldCursor = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());
                    model.moveCursorUp();
                    LocationRange newRange;
                    if(e.isShiftDown()) {
                        newRange = newRange(oldCursor, oldRange);
                    }else {
                        newRange = new LocationRange(model.getCursorLocation(), model.getCursorLocation());
                    }
                    model.setSelectionRange(newRange);
                }
            }
        });

        //DOWN_key
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    LocationRange oldRange = model.getSelectionRange();
                    oldRange = new LocationRange(new Location(oldRange.getStart().getX(), oldRange.getStart().getY()),
                                new Location(oldRange.getFinish().getX(), oldRange.getFinish().getY()));
                    Location oldCursor = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());
                    model.moveCursorDown();
                    LocationRange newRange;
                    if(e.isShiftDown()) {
                        newRange = newRange(oldCursor, oldRange);
                    }else {
                        newRange = new LocationRange(model.getCursorLocation(), model.getCursorLocation());
                    }
                    model.setSelectionRange(newRange);
                }
            }
        });

        //LEFT_key
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    LocationRange oldRange = model.getSelectionRange();
                    oldRange = new LocationRange(new Location(oldRange.getStart().getX(), oldRange.getStart().getY()),
                                new Location(oldRange.getFinish().getX(), oldRange.getFinish().getY()));
                    Location oldCursor = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());
                    model.moveCursorLeft();
                    LocationRange newRange;
                    if(e.isShiftDown()) {
                        newRange = newRange(oldCursor, oldRange);
                    }else {
                        newRange = new LocationRange(model.getCursorLocation(), model.getCursorLocation());
                    }
                    model.setSelectionRange(newRange);
                }
            }
        });

        //RIGHT_key
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    LocationRange oldRange = model.getSelectionRange();
                    oldRange = new LocationRange(new Location(oldRange.getStart().getX(), oldRange.getStart().getY()),
                                new Location(oldRange.getFinish().getX(), oldRange.getFinish().getY()));
                    Location oldCursor = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());
                    model.moveCursorRight();
                    LocationRange newRange;
                    if(e.isShiftDown()) {
                        newRange = newRange(oldCursor, oldRange);
                    }else {
                        newRange = new LocationRange(model.getCursorLocation(), model.getCursorLocation());
                    }
                    model.setSelectionRange(newRange);
                }
            }
        });

        //HOME_key
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_HOME) {
                    LocationRange oldRange = model.getSelectionRange();
                    oldRange = new LocationRange(new Location(oldRange.getStart().getX(), oldRange.getStart().getY()),
                                new Location(oldRange.getFinish().getX(), oldRange.getFinish().getY()));
                    Location oldCursor = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());
                    model.moveHome();
                    LocationRange newRange;
                    if(e.isShiftDown()) {
                        newRange = newRange(oldCursor, oldRange);
                    }else {
                        newRange = new LocationRange(model.getCursorLocation(), model.getCursorLocation());
                    }
                    model.setSelectionRange(newRange);
                }
            }
        });

        //END_key
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_END) {
                    LocationRange oldRange = model.getSelectionRange();
                    oldRange = new LocationRange(new Location(oldRange.getStart().getX(), oldRange.getStart().getY()),
                                new Location(oldRange.getFinish().getX(), oldRange.getFinish().getY()));
                    Location oldCursor = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());
                    model.moveEnd();
                    LocationRange newRange;
                    if(e.isShiftDown()) {
                        newRange = newRange(oldCursor, oldRange);
                    }else {
                        newRange = new LocationRange(model.getCursorLocation(), model.getCursorLocation());
                    }
                    model.setSelectionRange(newRange);
                }
            }
        });

        //DELETE
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    model.deleteAfter(true);
                }
            }
        });

        //BACKSPACE
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    model.deleteBefore(true);
                }
            }
        });

        //CHARACTER
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(!e.isControlDown()) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                        model.insert('\n', true);
                    }else if(e.getKeyCode() == KeyEvent.VK_TAB) {
                    	model.insert("   ", true);
                    }else if(((int)e.getKeyCode() > 31 && (int)e.getKeyCode() < 127
                            && e.getKeyCode() != KeyEvent.VK_UP
                            && e.getKeyCode() != KeyEvent.VK_DOWN
                            && e.getKeyCode() != KeyEvent.VK_LEFT
                            && e.getKeyCode() != KeyEvent.VK_RIGHT
                            && e.getKeyCode() != KeyEvent.VK_HOME
                            && e.getKeyCode() != KeyEvent.VK_END
                            && e.getKeyCode() != KeyEvent.VK_PAGE_UP
                            && e.getKeyCode() != KeyEvent.VK_PAGE_DOWN
                            && e.getKeyCode() != KeyEvent.VK_INSERT
                            && e.getKeyCode() != KeyEvent.VK_F1
                            && e.getKeyCode() != KeyEvent.VK_F2
                            && e.getKeyCode() != KeyEvent.VK_F3
                            && e.getKeyCode() != KeyEvent.VK_F4
                            && e.getKeyCode() != KeyEvent.VK_F5
                            && e.getKeyCode() != KeyEvent.VK_F6
                            && e.getKeyCode() != KeyEvent.VK_F7
                            && e.getKeyCode() != KeyEvent.VK_F8
                            && e.getKeyCode() != KeyEvent.VK_F9
                            && e.getKeyCode() != KeyEvent.VK_F10
                            && e.getKeyCode() != KeyEvent.VK_F11
                            && e.getKeyCode() != KeyEvent.VK_F12)) {
                        model.insert(e.getKeyChar(), true);
                    }
                }
            }
        });

        //CTRL+C, CTRL+X, CTRL+V, CTRL+SHIFT+V
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.isControlDown()) {
                    if(e.getKeyCode() == KeyEvent.VK_C) {
                        model.copy();
                    }else if(e.getKeyCode() == KeyEvent.VK_X) {
                        model.cut();
                    }else if(e.getKeyCode() == KeyEvent.VK_V) {
                        if(e.isShiftDown()) {
                            model.shiftPaste();
                        }else {
                            model.paste();
                        }
                    }
                }
            }
        });

        //CTRL+Z, CTRL+Y
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.isControlDown()) {
                    if(e.getKeyCode() == KeyEvent.VK_Z) {
                    	UndoManager.instanceOf().undo();
                    }else if(e.getKeyCode() == KeyEvent.VK_Y) {
                    	UndoManager.instanceOf().redo();
                    }
                }
            }
        });
    }

    private void createActions() {
        openAction.putValue(Action.NAME, "Open");
        openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        openAction.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disk.");

        saveAction.putValue(Action.NAME, "Save");
        saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveAction.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disk.");

        exitAction.putValue(Action.NAME, "Exit");
        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit application.");

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        undoAction.putValue(Action.NAME, "Undo");
        undoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Z"));
        undoAction.putValue(Action.SHORT_DESCRIPTION, "Used to undo the last change of the document.");

        redoAction.putValue(Action.NAME, "Redo");
        redoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Y"));
        redoAction.putValue(Action.SHORT_DESCRIPTION, "Used to redo the last change of the document.");

        cutAction.putValue(Action.NAME, "Cut");
        cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        cutAction.putValue(Action.SHORT_DESCRIPTION, "Used to cut the selected range.");

        copyAction.putValue(Action.NAME, "Copy");
        copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        copyAction.putValue(Action.SHORT_DESCRIPTION, "Used to copy the selected range.");

        pasteAction.putValue(Action.NAME, "Paste");
        pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        pasteAction.putValue(Action.SHORT_DESCRIPTION, "Used to paste from clipboard.");

        pasteAndTakeAction.putValue(Action.NAME, "Paste and Take");
        pasteAndTakeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift V"));
        pasteAndTakeAction.putValue(Action.SHORT_DESCRIPTION, "Used to paste and remove from clipboard.");

        deleteSectionAction.putValue(Action.NAME, "Delete selection");
        deleteSectionAction.putValue(Action.SHORT_DESCRIPTION, "Used to delete selected text.");

        clearAction.putValue(Action.NAME, "Clear document");
        clearAction.putValue(Action.SHORT_DESCRIPTION, "Used to clear the document.");

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        cursorToStartAction.putValue(Action.NAME, "Cursor to document start");
        cursorToStartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("HOME"));
        cursorToStartAction.putValue(Action.SHORT_DESCRIPTION, "Used to move cursor to document start.");

        cursorToEndAction.putValue(Action.NAME, "Cursor to document end");
        cursorToEndAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("END"));
        cursorToEndAction.putValue(Action.SHORT_DESCRIPTION, "Used to open move cursor to document end.");
    }

    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFocusable(false);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        fileMenu.add(new JMenuItem(openAction));
		fileMenu.add(new JMenuItem(saveAction));
        fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

        JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);

        JMenuItem undo = new JMenuItem(undoAction);
        UndoManager.instanceOf().addUndoStackObserver(undo);
        JMenuItem redo = new JMenuItem(redoAction);
        UndoManager.instanceOf().addRedoStackObserver(redo);
        JMenuItem cut = new JMenuItem(cutAction);
        model.addSelectionObserver(cut);
        JMenuItem copy = new JMenuItem(copyAction);
        model.addSelectionObserver(copy);
        JMenuItem paste = new JMenuItem(pasteAction);
        model.getClipboard().addClipboardObserver(paste);
        JMenuItem pasteAndTake = new JMenuItem(pasteAndTakeAction);
        model.getClipboard().addClipboardObserver(pasteAndTake);
        JMenuItem deleteSection = new JMenuItem(deleteSectionAction);
        model.addSelectionObserver(deleteSection);
        JMenuItem clear = new JMenuItem(clearAction);

        editMenu.add(undo);
        editMenu.add(redo);
        editMenu.addSeparator();
        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(pasteAndTake);
        editMenu.add(deleteSection);
        editMenu.addSeparator();
        editMenu.add(clear);

        JMenu moveMenu = new JMenu("Move");
		menuBar.add(moveMenu);

        moveMenu.add(new JMenuItem(cursorToStartAction));
        moveMenu.addSeparator();
        moveMenu.add(new JMenuItem(cursorToEndAction));

        JMenu pluginMenu = new JMenu("Plugins");
		menuBar.add(pluginMenu);
		List<Plugin> plugini = getPlugins();
		if(plugini.size() != 0) {
			for(Plugin p : plugini) {
				JMenuItem i = new JMenuItem(createPluginAction(p));
				pluginMenu.add(i);
			}
		}
		
        setJMenuBar(menuBar);
    }
    
    private Action createPluginAction(Plugin p) {
    	Action akcija = new AbstractAction() {
    		
    		private static final long serialVersionUID = 1L;
    		
    		public void actionPerformed(ActionEvent e) {
    			p.execute(model, UndoManager.instanceOf(), model.getClipboard());
    		}
    	};
    	akcija.putValue(Action.NAME, p.getName());
    	akcija.putValue(Action.SHORT_DESCRIPTION, p.getDescription());
    	return akcija;
    }

    private void createToolbar() {
        JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);
        toolBar.setFocusable(false);
		
		JButton undo = new JButton(undoAction);
		UndoManager.instanceOf().addUndoStackObserver(undo);
		JButton redo = new JButton(redoAction);
		UndoManager.instanceOf().addRedoStackObserver(redo);
		JButton cut = new JButton(cutAction);
        model.addSelectionObserver(cut);
        JButton copy = new JButton(copyAction);
        model.addSelectionObserver(copy);
        JButton paste = new JButton(pasteAction);
        model.getClipboard().addClipboardObserver(paste);
		
		toolBar.add(undo);
		toolBar.add(redo);
		toolBar.add(cut);
        toolBar.add(copy);
        toolBar.add(paste);
		toolBar.addSeparator();
		
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    //OTVARANJE DOKUMENTA
    private Action openAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Open file");
			if(fileChooser.showOpenDialog(TextEditor.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fileChooser.getSelectedFile();
			Path filePath = fileName.toPath();
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
                    TextEditor.this, 
						"File " + fileName.getAbsolutePath() + " doesn't exist!", 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				byte[] okteti = Files.readAllBytes(filePath);
                String procitaniTekst = new String(okteti);
                newDokument(procitaniTekst);
                model.setPath(filePath);
				setTitle(filePath.toString() + " - TextEditor");
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(
                    TextEditor.this, 
						"Error while reading file " + fileName.getAbsolutePath() + ".", 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	};

    //SPREMANJE DOKUMENTA
    private Action saveAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			Path openedFilePath = model.getPath();
			if(openedFilePath == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if(jfc.showSaveDialog(TextEditor.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(
                        TextEditor.this, 
							"Nothing has been saved.", 
							"Warning", 
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
                model.setPath(openedFilePath);
			}
            setTitle(openedFilePath.toString() + " - TextEditor");

            Iterator<String> it = model.allLines();
            String savTekst = "";
            while(it.hasNext()) {
                savTekst += it.next();
                if(it.hasNext()) {
                    savTekst += "\n";
                }
            }
			byte[] podatci = savTekst.getBytes(StandardCharsets.UTF_8);

            try {
                Files.write(openedFilePath, podatci);
            }catch (IOException e1) {
                JOptionPane.showMessageDialog(
                    TextEditor.this, 
                        "Error while saving file " + openedFilePath.toFile().getAbsolutePath() + ".\nAttention: The state of the file on disc is not clear!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }

			JOptionPane.showMessageDialog(
                TextEditor.this, 
					"File is saved.", 
					"Information", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

    //IZLAZAK
    private Action exitAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};

    //UNDO
    private Action undoAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			UndoManager.instanceOf().undo();
		}
	};

    //REDO
    private Action redoAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			UndoManager.instanceOf().redo();
		}
	};

    //COPY
    private Action copyAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			model.copy();
		}
	};

    //CUT
    private Action cutAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			model.cut();
		}
	};

    //PASTE
    private Action pasteAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			model.paste();
		}
	};

    //PASTE AND TAKE
    private Action pasteAndTakeAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			model.shiftPaste();
		}
	};

    //DELETE SECTION
    private Action deleteSectionAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			model.deleteRange(model.getSelectionRange(), true);
		}
	};

    //CLEAR
    private Action clearAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			model.deleteRange(new LocationRange(new Location(0, 0), model.getEndOfDocument()), true);
		}
	};

    //CURSOR TO START
    private Action cursorToStartAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			model.setCursorLocation(new Location(0, 0));
		}
	};

    //CURSOR TO END
    private Action cursorToEndAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			model.setCursorLocation(model.getEndOfDocument());
		}
	};

    //CANVAS_FOR_DRAWING
    private class Canvas extends JComponent {
        
		private static final long serialVersionUID = 1L;

		@Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            Font font = new Font("Monospaced", Font.PLAIN, 12);
            g2d.setFont(font);

            FontMetrics fm = g2d.getFontMetrics();

            //draw selection
            g2d.setColor(Color.LIGHT_GRAY);
            LocationRange selection = model.getSelectionRange();
            Location start = selection.getStart();
            Location fin = selection.getFinish();
            if(!(start.equals(fin))) {
                if(start.getY() == fin.getY()) {
                    g2d.fillRect(start.getX() * fm.charWidth(' '), start.getY() * fm.getAscent(), (fin.getX() - start.getX()) * fm.charWidth(' '), fm.getAscent());
                }else {
                    Iterator<String> it = model.linesRange(start.getY(), fin.getY() + 1);
                    int i = 0;
                    while(it.hasNext()) {
                        String trenLinija = it.next();
                        if(i == 0) {//first
                            g2d.fillRect(start.getX() * fm.charWidth(' '), start.getY() * fm.getAscent(), (trenLinija.length() - start.getX()) * fm.charWidth(' '), fm.getAscent());
                        }else if(!it.hasNext()) {//last
                            g2d.fillRect(0, fin.getY() * fm.getAscent(), fin.getX() * fm.charWidth(' '), fm.getAscent());
                        }else {//between
                            g2d.fillRect(0, (start.getY() + i) * fm.getAscent(), trenLinija.length() * fm.charWidth(' '), fm.getAscent());
                        }
                        i++;
                    }
                }
            }

            //draw cursor
            Location cursorLocation = model.getCursorLocation();
            g2d.setColor(Color.RED);
            g2d.drawLine(cursorLocation.getX() * fm.charWidth(' '), cursorLocation.getY() * fm.getAscent(), 
                        cursorLocation.getX() * fm.charWidth(' '), (cursorLocation.getY() + 1) * fm.getAscent());

            //draw text
            g2d.setColor(Color.BLACK);
            Iterator<String> it = model.allLines();
            int i = 1;
            while(it.hasNext()) {
                String line = it.next();
                g2d.drawString(line, 0, 0 + i * fm.getAscent());
                i++;
            }
        }
    }

    public LocationRange newRange(Location oldCursor, LocationRange oldRange) {
        Location oldStart = oldRange.getStart();
        Location oldFin = oldRange.getFinish();
        Location newCursor = model.getCursorLocation();
        Location newStart = null, newFin = null;

        if(oldCursor.equals(oldStart)) {
            //if cursor is on start
            if(newCursor.getY() == oldFin.getY()) {//if in the same line
                if(newCursor.getX() > oldFin.getX()) {
                	newStart = new Location(oldFin.getX(), oldFin.getY());
                	newFin = new Location(newCursor.getX(), newCursor.getY());
                }else {
                	newStart = new Location(newCursor.getX(), newCursor.getY());
                    newFin = new Location(oldFin.getX(), oldFin.getY());
                }
            }else if(newCursor.getY() < oldFin.getY()) {//new start is above fin
            	newStart = new Location(newCursor.getX(), newCursor.getY());
            	newFin = new Location(oldFin.getX(), oldFin.getY());
            }else {//new start is below fin
            	newStart = new Location(oldFin.getX(), oldFin.getY());
            	newFin = new Location(newCursor.getX(), newCursor.getY());
            }
        }else if(oldCursor.equals(oldFin)) {
            //if cursor is on fin
            if(newCursor.getY() == oldStart.getY()) {
                if(newCursor.getX() > oldStart.getX()) {
                	newStart = new Location(oldStart.getX(), oldStart.getY());
                	newFin = new Location(newCursor.getX(), newCursor.getY());
                }else {
                	newStart = new Location(newCursor.getX(), newCursor.getY());
                	newFin = new Location(oldStart.getX(), oldStart.getY());
                }
            }else if(newCursor.getY() < oldStart.getY()) {
            	newStart = new Location(newCursor.getX(), newCursor.getY());
            	newFin = new Location(oldStart.getX(), oldStart.getY());
            }else {
            	newStart = new Location(oldStart.getX(), oldStart.getY());
            	newFin = new Location(newCursor.getX(), newCursor.getY());
            }
        }
        return new LocationRange(newStart, newFin);
    }

    @Override
    public void updateCursorLocation(Location loc) {
    	canvas.repaint();
    }

    @Override
    public void updateText() {
    	canvas.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TextEditor().setVisible(true);
			}
		});
    }
}