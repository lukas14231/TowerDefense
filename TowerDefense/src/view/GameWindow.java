package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.Locale;

import translator.Translator;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.GamePanel;

import model.AOETower;
import model.Bullet;
import model.LaserTower;
import model.Monster;
import model.MonsterTypeHealer;
import model.MonsterTypeNormal;
import model.MonsterTypeRunner;
import model.MonsterTypeSpeed;
import model.MonsterTypeTank;
import model.SlowTower;
import model.SniperTower;
import model.Tower;

public class GameWindow extends JFrame implements GUIInterface {
	private static final long serialVersionUID = -5786119356975506035L;

	/**
	 * translator.jar als external jar dem Projekt hinzufügen Die beiden Dateien
	 * lang.de_DE und lang.en_US in den Projektordner kopieren, wo die Ordner
	 * src, resources und bin sind Bei der deutschen Version müsste die
	 * Turmbeschreibung weiter rechts stehen oder der Turmname muss gekürzt
	 * werden
	 */
	private Translator translator;

	private Vector<JButton> entities = null;
	private GamePanel controller;
	private JLayeredPane layerPane;
	private Vector<JButton> towerButtons;
	private Vector<Monster> givenMonsters;
	private Vector<Bullet> givenBullets;
	private HashMap<Monster, JLabel> monsterLabels;
	private HashMap<Tower, JLabel> rangeLevelLabels;
	private HashMap<Tower, JLabel> efficiencyLevelLabels;
	private Vector<Tower> givenTowers;
	private Vector<drawLine> laserDraws;
	private Vector<Object> deleteObjects;
	private Vector<drawLine> hpLines;
	private Vector<drawBullet> bulletCircles;
	private HashMap<JButton, String> entitiesIcon;
	private JSlider gameSpeed;
	private JLabel moneyLabel;
	private JLabel lifepointsLabel;
	private JLabel infoMenuLabel;
	private JLabel currentWaveInfo;
	private JLabel currentLevel;
	private JLabel pausedLabel;
	private JLabel moneyInfoLabel;
	private JLabel nextWaveInfoLabel;
	private JButton pauseButton;
	private JButton skipButton;
	private JButton aoeTowerButton;
	private JButton slowTowerButton;
	private JButton laserTowerButton;
	private JButton sniperTowerButton;
	private JButton upgradeRangeButton;
	private JButton upgradeEfficiencyButton;
	private JButton saleButton;
	private JLabel languageLabel;
	private JButton quitButton;
	private JLabel speedLabel;
	private JButton generateLevelButton;
	private JLabel lostGameLabel;
	private JLabel gameWonLabel;
	private JRadioButton buttonUS;
	private JRadioButton buttonDE;
	private JLabel skinLabel;
	private JComboBox skinList;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel height;
	private JLabel width;
	private JTextField heightField;
	private JTextField widthField;
	private JFrame generateFrame;
	private JFrame mainFrame;
	private JRadioButton soundON;
	private JRadioButton soundOFF;

	private JPopupMenu towerMenu;
	private JPopupMenu towerOptionMenu;
	private JPanel infoMenu;
	private JButton clickedButton = null;
	private JPanel towerRange = null;
	private JButton clickedOptionTower;
	private int menuIsShown = 0;
	private int towerInfoIsShown = 0;
	private int fieldWidth;
	private int fieldHeight;
	private int windowWidth;
	private int windowHeight;
	private int paused = 0;
	private int moneyLabelShowLength = 0;
	private int moneyLabelCounter;
	private int moneyLabelStatus = 1;
	private int standardTick;
	private char lastChar;
	private ImageIcon afterSpawnIcon = null;
	private int started = 0;
	private int firstStarted = 1;
	private Color laserColor;
	private Color hpLine1Color;
	private Color hpLine2Color;
	private boolean soundStatus;

	private String iconPath;
	private String spawnFile;
	private String freeFile;
	private String takenFile;
	private String targetFile;
	private String aoeTowerFile;
	private String slowTowerFile;
	private String laserTowerFile;
	private String sniperTowerFile;
	private String pathURFile;
	private String pathRDFile;
	private String pathDLFile;
	private String pathLUFile;
	private String pathHFile;
	private String pathVFile;
	private String normalFile;
	private String healerFile;
	private String tankFile;
	private String speedFile;
	private String runnerFile;

	private String buildTowerSoundPath; // Sound
	private String gameLostSoundPath; // Sound
	private String gameWonSoundPath; // Sound
	private String clickSoundPath; // Sound
	private String laserSoundPath; // Sound
	private String sniperSoundPath; // Sound
	private String failSoundPath; // Sound
	private String sellSoundPath; // Sound
	private String upgradeSoundPath; // Sound
	private String looseLiveSoundPath; // Sound

	Thread playBuildTowerSound; // Sound
	Thread playGameLostSound; // Sound
	Thread playGameWonSound; // Sound
	Thread clickSound; // Sound
	Thread laserSound; // Sound
	Thread sniperSound; // Sound
	Thread failSound; // Sound
	Thread sellSound; // Sound
	Thread upgradeSound; // Sound
	Thread looseLiveSound; // Sound

	private ImageIcon spawnIcon;
	private ImageIcon freeIcon;
	private ImageIcon takenIcon;
	private ImageIcon targetIcon;
	private ImageIcon aoeTowerIcon;
	private ImageIcon slowTowerIcon;
	private ImageIcon laserTowerIcon;
	private ImageIcon sniperTowerIcon;
	private ImageIcon pathURIcon;
	private ImageIcon pathRDIcon;
	private ImageIcon pathDLIcon;
	private ImageIcon pathLUIcon;
	private ImageIcon pathHIcon;
	private ImageIcon pathVIcon;
	private ImageIcon normalIcon;
	private ImageIcon healerIcon;
	private ImageIcon tankIcon;
	private ImageIcon speedIcon;
	private ImageIcon runnerIcon;

	public GameWindow(GamePanel givencontroller) {
		super("Towerdefense");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame = this;
		setResizable(false);
		setLayout(null);

		translator = new Translator("lang", Locale.US);

		controller = givencontroller;
		entities = new Vector<JButton>();
		towerButtons = new Vector<JButton>();

		layerPane = new JLayeredPane();
		layerPane.setLayout(null);
		add(layerPane);

		givenMonsters = controller.getMonsters();
		monsterLabels = new HashMap<Monster, JLabel>();
		givenBullets = controller.getBullets();
		givenTowers = controller.getTowers();
		laserDraws = new Vector<drawLine>();
		hpLines = new Vector<drawLine>();
		bulletCircles = new Vector<drawBullet>();
		rangeLevelLabels = new HashMap<Tower, JLabel>();
		efficiencyLevelLabels = new HashMap<Tower, JLabel>();
		entitiesIcon = new HashMap<JButton, String>();
		lastChar = '0';

		iconPath = "./resources/images/defaultskin/";
		spawnFile = "map/spawn.png";
		freeFile = "map/field.png";
		takenFile = "map/wall.png";
		targetFile = "map/target.png";
		aoeTowerFile = "towers/aoetower.png";
		slowTowerFile = "towers/frosttower.png";
		laserTowerFile = "towers/splashtower.png";
		sniperTowerFile = "towers/tower.png";
		pathURFile = "map/path_tr.png";
		pathRDFile = "map/path_bl.png";
		pathDLFile = "map/path_tl.png";
		pathLUFile = "map/path_br.png";
		pathHFile = "map/path_lr.png";
		pathVFile = "map/path_ud.png";
		normalFile = "monsters/monster.png";
		healerFile = "monsters/monster2.png";
		tankFile = "monsters/monster3.png";
		speedFile = "monsters/monster4.png";
		runnerFile = "monsters/monster5.png";

		spawnIcon = new ImageIcon(iconPath + spawnFile);
		freeIcon = new ImageIcon(iconPath + freeFile);
		takenIcon = new ImageIcon(iconPath + takenFile);
		targetIcon = new ImageIcon(iconPath + targetFile);
		aoeTowerIcon = new ImageIcon(iconPath + aoeTowerFile);
		slowTowerIcon = new ImageIcon(iconPath + slowTowerFile);
		laserTowerIcon = new ImageIcon(iconPath + laserTowerFile);
		sniperTowerIcon = new ImageIcon(iconPath + sniperTowerFile);
		pathURIcon = new ImageIcon(iconPath + pathURFile);
		pathRDIcon = new ImageIcon(iconPath + pathRDFile);
		pathDLIcon = new ImageIcon(iconPath + pathDLFile);
		pathLUIcon = new ImageIcon(iconPath + pathLUFile);
		pathHIcon = new ImageIcon(iconPath + pathHFile);
		pathVIcon = new ImageIcon(iconPath + pathVFile);
		normalIcon = new ImageIcon(iconPath + normalFile);
		healerIcon = new ImageIcon(iconPath + healerFile);
		tankIcon = new ImageIcon(iconPath + tankFile);
		speedIcon = new ImageIcon(iconPath + speedFile);
		runnerIcon = new ImageIcon(iconPath + runnerFile);

		// Soundpfade
		buildTowerSoundPath = "./resources/sounds/build tower.mp3";
		gameLostSoundPath = "./resources/sounds/looser.mp3";
		gameWonSoundPath = "./resources/sounds/winner.mp3";
		clickSoundPath = "./resources/sounds/click.mp3";
		laserSoundPath = "./resources/sounds/laser.mp3";
		sniperSoundPath = "./resources/sounds/sniper.mp3";
		failSoundPath = "./resources/sounds/fail.mp3";
		sellSoundPath = "./resources/sounds/sell.mp3";
		upgradeSoundPath = "./resources/sounds/upgrade.mp3";
		looseLiveSoundPath = "./resources/sounds/loose live.mp3";
		// Soundobjekte
		playBuildTowerSound = new AudioThread(buildTowerSoundPath);
		playGameLostSound = new AudioThread(gameLostSoundPath);
		playGameWonSound = new AudioThread(gameWonSoundPath);
		clickSound = new AudioThread(clickSoundPath);
		laserSound = new AudioThread(laserSoundPath);
		sniperSound = new AudioThread(sniperSoundPath);
		failSound = new AudioThread(failSoundPath);
		sellSound = new AudioThread(sellSoundPath);
		upgradeSound = new AudioThread(upgradeSoundPath);
		looseLiveSound = new AudioThread(looseLiveSoundPath);

		towerMenu = new JPopupMenu();
		towerMenu.setLayout(new GridLayout(4, 0, 0, 0));

		aoeTowerButton = new JButton(translator.translateMessage("aoeTower"),
				aoeTowerIcon);
		aoeTowerButton.addActionListener(new menuItemListener());
		aoeTowerButton.setHorizontalAlignment(JButton.LEFT);
		aoeTowerButton.addMouseListener(new towerInfoMenuListener());
		towerMenu.add(aoeTowerButton);

		slowTowerButton = new JButton(translator.translateMessage("slowTower"),
				slowTowerIcon);
		slowTowerButton.addActionListener(new menuItemListener());
		slowTowerButton.setHorizontalAlignment(JButton.LEFT);
		slowTowerButton.addMouseListener(new towerInfoMenuListener());
		towerMenu.add(slowTowerButton);

		laserTowerButton = new JButton(
				translator.translateMessage("laserTower"), laserTowerIcon);
		laserTowerButton.addActionListener(new menuItemListener());
		laserTowerButton.setHorizontalAlignment(JButton.LEFT);
		laserTowerButton.addMouseListener(new towerInfoMenuListener());
		towerMenu.add(laserTowerButton);

		sniperTowerButton = new JButton(
				translator.translateMessage("sniperTower"), sniperTowerIcon);
		sniperTowerButton.addActionListener(new menuItemListener());
		sniperTowerButton.setHorizontalAlignment(JButton.LEFT);
		sniperTowerButton.addMouseListener(new towerInfoMenuListener());
		towerMenu.add(sniperTowerButton);

		towerOptionMenu = new JPopupMenu();
		towerOptionMenu.setLayout(new GridLayout(3, 0, 0, 0));

		upgradeRangeButton = new JButton(
				translator.translateMessage("upgradeRange"));
		upgradeRangeButton.addMouseListener(new optionMenuInfoListener());
		upgradeRangeButton.addActionListener(new optionMenuItemListener());
		upgradeRangeButton.setHorizontalAlignment(JButton.LEFT);
		towerOptionMenu.add(upgradeRangeButton);

		upgradeEfficiencyButton = new JButton(
				translator.translateMessage("upgradeEfficiency"));
		upgradeEfficiencyButton.addMouseListener(new optionMenuInfoListener());
		upgradeEfficiencyButton.addActionListener(new optionMenuItemListener());
		upgradeEfficiencyButton.setHorizontalAlignment(JButton.LEFT);
		towerOptionMenu.add(upgradeEfficiencyButton);

		saleButton = new JButton(translator.translateMessage("sell"));
		saleButton.addMouseListener(new optionMenuInfoListener());
		saleButton.addActionListener(new optionMenuItemListener());
		saleButton.setHorizontalAlignment(JButton.LEFT);
		towerOptionMenu.add(saleButton);

		infoMenu = new JPanel();
		infoMenu.setLayout(new GridLayout(1, 0, 0, 0));

		infoMenuLabel = new JLabel();
		infoMenuLabel.setVerticalAlignment(JLabel.TOP);
		infoMenuLabel.setForeground(Color.BLACK);
		infoMenu.add(infoMenuLabel);
		infoMenu.setBackground(Color.GRAY);

		layerPane.add(towerMenu, new Integer(150));
		layerPane.add(towerOptionMenu, new Integer(150));
		layerPane.add(infoMenu, new Integer(3000));

		generateFrame = new JFrame();
		generateFrame.addWindowListener(new cancelButtonListener());
		height = new JLabel(translator.translateMessage("height"));
		width = new JLabel(translator.translateMessage("width"));
		generateFrame.setLayout(null);
		okButton = new JButton("OK");
		okButton.addActionListener(new okButtonListener());
		cancelButton = new JButton(translator.translateMessage("cancel"));
		cancelButton.addActionListener(new cancelButtonListener());
		heightField = new JTextField();
		widthField = new JTextField();
		generateFrame.add(heightField);
		generateFrame.add(okButton);
		generateFrame.add(cancelButton);
		generateFrame.add(height);
		generateFrame.add(width);
		generateFrame.add(widthField);
		height.setBounds(50, 30, 50, 10);
		width.setBounds(50, 60, 50, 10);
		heightField.setBounds(100, 25, 50, 20);
		widthField.setBounds(100, 55, 50, 20);
		okButton.setBounds(20, 100, 80, 25);
		cancelButton.setBounds(120, 100, 80, 25);
		generateFrame.setBounds(100, 100, 220, 180);
		generateFrame.setResizable(false);
		generateFrame.setVisible(false);

		standardTick = controller.getTick();

		initGamePanel();
	}

	public void initGamePanel() {
		int fieldSize = controller.getFieldSize();
		char[][] gameField = controller.getGameField();

		int rows = 0;
		int cols = 0;
		for (rows = 0; rows < gameField[0].length; rows++) {
			for (cols = 0; cols < gameField.length; cols++) {

				JButton button = new JButton();
				button.addActionListener(new fieldButtonListener());
				button.setOpaque(false);
				entities.add(button);
				button.setBounds(cols * fieldSize, rows * fieldSize, fieldSize,
						fieldSize);
				button.setBorder(null);

				switch (gameField[cols][rows]) {
				case 'S':
					button.setIcon(spawnIcon);
					if (gameField[cols][rows - 1] == '>')
						afterSpawnIcon = pathURIcon;
					else if (gameField[cols][rows - 1] == '<')
						afterSpawnIcon = pathRDIcon;
					else if (gameField[cols][rows - 1] == '^')
						afterSpawnIcon = pathVIcon;

					else if (gameField[cols + 1][rows] == '^')
						afterSpawnIcon = pathDLIcon;
					else if (gameField[cols + 1][rows] == '>')
						afterSpawnIcon = pathHIcon;
					else if (gameField[cols + 1][rows] == 'v')
						afterSpawnIcon = pathRDIcon;

					else if (gameField[cols][rows + 1] == '>')
						afterSpawnIcon = pathLUIcon;
					else if (gameField[cols][rows + 1] == '<')
						afterSpawnIcon = pathDLIcon;
					else if (gameField[cols][rows + 1] == 'v')
						afterSpawnIcon = pathVIcon;

					else if (gameField[cols - 1][rows] == '<')
						afterSpawnIcon = pathHIcon;
					else if (gameField[cols - 1][rows] == '^')
						afterSpawnIcon = pathLUIcon;
					else if (gameField[cols - 1][rows] == 'v')
						afterSpawnIcon = pathURIcon;

					layerPane.add(button, new Integer(100));
					entitiesIcon.put(button, "S");
					break;
				case 'X':
					button.setIcon(targetIcon);
					entitiesIcon.put(button, "X");
					layerPane.add(button, new Integer(100));
					break;
				case '_':
					button.setIcon(freeIcon);
					entitiesIcon.put(button, "_");
					layerPane.add(button, new Integer(100));
					break;
				case '#':
					button.setIcon(takenIcon);
					entitiesIcon.put(button, "#");
					layerPane.add(button, new Integer(100));
					break;
				default:
					break;
				}
			}
		}

		for (Point point : controller.getPath()) {
			char currentChar = gameField[point.x / controller.getFieldSize()][point.y
					/ controller.getFieldSize()];
			JButton button = new JButton();
			button.setOpaque(false);
			entities.add(button);
			button.setBounds(
					controller.cPointToMPoint(point).x + 1
							- controller.getFieldSize(),
					controller.cPointToMPoint(point).y + 1
							- controller.getFieldSize(), fieldSize, fieldSize);
			button.setBorder(null);

			if ((currentChar == '>' && lastChar == '^')
					|| (currentChar == 'v' && lastChar == '<')) {
				button.setIcon(pathURIcon);
				layerPane.add(button, new Integer(50));
				entitiesIcon.put(button, "UR");
			} else if ((currentChar == 'v' && lastChar == '>')
					|| (currentChar == '<' && lastChar == '^')) {
				button.setIcon(pathRDIcon);
				layerPane.add(button, new Integer(50));
				entitiesIcon.put(button, "RD");
			} else if ((currentChar == '<' && lastChar == 'v')
					|| (currentChar == '^' && lastChar == '>')) {
				button.setIcon(pathDLIcon);
				layerPane.add(button, new Integer(50));
				entitiesIcon.put(button, "DL");
			} else if ((currentChar == '>' && lastChar == 'v')
					|| (currentChar == '^' && lastChar == '<')) {
				button.setIcon(pathLUIcon);
				layerPane.add(button, new Integer(50));
				entitiesIcon.put(button, "LU");
			} else if ((currentChar == '>' && lastChar == '>')
					|| (currentChar == '<' && lastChar == '<')) {
				button.setIcon(pathHIcon);
				layerPane.add(button, new Integer(50));
				entitiesIcon.put(button, "H");
			} else if ((currentChar == '^' && lastChar == '^')
					|| (currentChar == 'v' && lastChar == 'v')) {
				button.setIcon(pathVIcon);
				layerPane.add(button, new Integer(50));
				entitiesIcon.put(button, "V");
			} else {
				layerPane.add(button, new Integer(50));
				button.setIcon(afterSpawnIcon);
				if (afterSpawnIcon == pathURIcon) {
					entitiesIcon.put(button, "UR");
				} else if (afterSpawnIcon == pathRDIcon) {
					entitiesIcon.put(button, "RD");
				} else if (afterSpawnIcon == pathDLIcon) {
					entitiesIcon.put(button, "DL");
				} else if (afterSpawnIcon == pathLUIcon) {
					entitiesIcon.put(button, "LU");
				} else if (afterSpawnIcon == pathHIcon) {
					entitiesIcon.put(button, "H");
				} else if (afterSpawnIcon == pathVIcon) {
					entitiesIcon.put(button, "V");
				}
			}

			lastChar = currentChar;
		}

		fieldWidth = cols * fieldSize;
		fieldHeight = rows * fieldSize;
		windowWidth = fieldWidth + 125;
		windowHeight = fieldHeight + 270;

		setSize(windowWidth, windowHeight);
		layerPane.setBounds(0, 0, windowWidth, windowHeight);

		lifepointsLabel = new JLabel(translator.translateMessage("lives",
				String.valueOf(controller.getPlayer().getLives())));
		moneyLabel = new JLabel(translator.translateMessage("money",
				String.valueOf(controller.getPlayer().getMoney())));

		currentLevel = new JLabel("Level: -/-");
		lifepointsLabel.setBounds(10, fieldHeight + 10, 100, 100);
		moneyLabel.setBounds(10, fieldHeight + 30, 100, 100);
		currentLevel.setBounds(10, fieldHeight + 50, 100, 100);
		lifepointsLabel.setVerticalAlignment(JLabel.TOP);
		moneyLabel.setVerticalAlignment(JLabel.TOP);
		currentLevel.setVerticalAlignment(JLabel.TOP);
		lifepointsLabel.setForeground(Color.BLACK);
		moneyLabel.setForeground(Color.BLACK);
		currentLevel.setForeground(Color.BLACK);
		layerPane.add(lifepointsLabel, new Integer(2000));
		layerPane.add(moneyLabel, new Integer(2000));
		layerPane.add(currentLevel, new Integer(2000));

		currentWaveInfo = new JLabel(
				translator.translateMessage("currentWaveInfoBegin"));

		currentWaveInfo.setForeground(Color.BLACK);
		currentWaveInfo.setBounds(10, fieldHeight + 100, 200, 200);
		currentWaveInfo.setVerticalAlignment(JLabel.TOP);
		layerPane.add(currentWaveInfo, new Integer(2000));

		languageLabel = new JLabel(translator.translateMessage("language"));
		languageLabel.setVerticalAlignment(JLabel.TOP);
		languageLabel.setBounds(fieldWidth + 10, 10, 100, 100);
		layerPane.add(languageLabel, new Integer(2000));

		buttonUS = new JRadioButton(translator.translateMessage("englishLabel"));
		buttonDE = new JRadioButton(translator.translateMessage("germanLabel"));
		buttonUS.addActionListener(new languageButtonListener());
		buttonDE.addActionListener(new languageButtonListener());
		buttonUS.setVerticalAlignment(JRadioButton.TOP);
		buttonDE.setVerticalAlignment(JRadioButton.TOP);
		buttonUS.setSelected(true);
		layerPane.add(buttonUS, new Integer(2000));
		layerPane.add(buttonDE, new Integer(2000));
		ButtonGroup languageButtons = new ButtonGroup();
		languageButtons.add(buttonDE);
		languageButtons.add(buttonUS);
		buttonDE.setBounds(fieldWidth + 10, 30, 200, 20);
		buttonUS.setBounds(fieldWidth + 10, 50, 200, 20);

		speedLabel = new JLabel(translator.translateMessage("speed"));
		speedLabel.setBounds(fieldWidth + 10, 40, 100, 100);
		layerPane.add(speedLabel, new Integer(3000));

		gameSpeed = new JSlider(JSlider.HORIZONTAL, 1, 4, 1);
		gameSpeed.setEnabled(false);
		gameSpeed.setMinorTickSpacing(1);
		gameSpeed.setSnapToTicks(true);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		gameSpeed.setPaintTicks(true);
		labelTable.put(new Integer(1), new JLabel("<html>1</html>"));
		labelTable.put(new Integer(2), new JLabel("<html>2</html>"));
		labelTable.put(new Integer(3), new JLabel("<html>3</html>"));
		labelTable.put(new Integer(4), new JLabel("<html>4</html>"));
		gameSpeed.setLabelTable(labelTable);
		gameSpeed.setPaintLabels(true);
		gameSpeed.addChangeListener(new gameSpeedChangeListener());
		gameSpeed.setBounds(fieldWidth + 10, 100, 100, 50);
		layerPane.add(gameSpeed, new Integer(3000));

		skinLabel = new JLabel(translator.translateMessage("skin"));
		skinLabel.setBounds(fieldWidth + 10, 110, 100, 100);
		layerPane.add(skinLabel, new Integer(3000));

		String[] skinStrings = { translator.translateMessage("skin1"),
				translator.translateMessage("skin2"),
				translator.translateMessage("skin3") };
		skinList = new JComboBox(skinStrings);
		skinList.addActionListener(new skinListener());
		skinList.setBounds(fieldWidth + 10, 170, 100, 25);
		layerPane.add(skinList, new Integer(2000));

		JLabel soundLabel = new JLabel("Sound:");
		soundLabel.setVerticalAlignment(JLabel.TOP);
		soundLabel.setBounds(fieldWidth + 10, 210, 100, 100);
		layerPane.add(soundLabel, new Integer(2000));

		soundON = new JRadioButton(translator.translateMessage("on"));
		soundOFF = new JRadioButton(translator.translateMessage("off"));
		soundON.addActionListener(new soundButtonListener());
		soundOFF.addActionListener(new soundButtonListener());
		soundON.setVerticalAlignment(JRadioButton.TOP);
		soundOFF.setVerticalAlignment(JRadioButton.TOP);
		soundON.setSelected(true);
		soundStatus = true;
		layerPane.add(soundON, new Integer(2000));
		layerPane.add(soundOFF, new Integer(2000));
		ButtonGroup soundButtons = new ButtonGroup();
		soundButtons.add(soundON);
		soundButtons.add(soundOFF);
		soundON.setBounds(fieldWidth + 10, 230, 200, 20);
		soundOFF.setBounds(fieldWidth + 10, 250, 200, 20);

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new startButtonListener());
		startButton.setVerticalAlignment(JButton.TOP);
		layerPane.add(startButton, new Integer(1000));
		startButton.setBounds(fieldWidth + 10, windowHeight - 230, 100, 25);

		pauseButton = new JButton("<html>Pause</html>");
		pauseButton.setEnabled(false);
		pauseButton.addActionListener(new pauseButtonListener());
		pauseButton.setVerticalAlignment(JButton.TOP);
		layerPane.add(pauseButton, new Integer(1000));
		pauseButton.setBounds(fieldWidth + 10, windowHeight - 200, 100, 25);

		skipButton = new JButton(translator.translateMessage("skipSpawn"));
		skipButton.addActionListener(new skipSpawnListener());
		skipButton.setVerticalAlignment(JButton.TOP);
		layerPane.add(skipButton, new Integer(1000));
		skipButton.setBounds(fieldWidth + 10, windowHeight - 170, 100, 44);
		skipButton.setEnabled(false);

		generateLevelButton = new JButton(
				translator.translateMessage("generateLevel"));
		generateLevelButton
				.addActionListener(new generateLevelButtonListener());
		generateLevelButton.setVerticalAlignment(JButton.TOP);
		layerPane.add(generateLevelButton, new Integer(1000));
		generateLevelButton.setBounds(fieldWidth + 10, windowHeight - 120, 100,
				44);

		quitButton = new JButton(translator.translateMessage("quit"));
		quitButton.addActionListener(new quitButtonListener());
		quitButton.setVerticalAlignment(JButton.TOP);
		layerPane.add(quitButton, new Integer(1000));
		quitButton.setBounds(fieldWidth + 10, windowHeight - 70, 100, 25);

		pausedLabel = new JLabel(translator.translateMessage("pausedLabel"));
		pausedLabel.setBackground(Color.GRAY);
		pausedLabel.setOpaque(true);
		pausedLabel.setBounds(0, 0, fieldWidth, fieldHeight);
		pausedLabel.setVerticalAlignment(JLabel.CENTER);
		pausedLabel.setHorizontalAlignment(JLabel.CENTER);
		layerPane.add(pausedLabel, new Integer(3000));
		pausedLabel.setVisible(false);

		moneyInfoLabel = new JLabel(
				translator.translateMessage("notEnoughMoney"));
		moneyInfoLabel.setBounds(0, 0, fieldWidth, fieldHeight);
		moneyInfoLabel.setVerticalAlignment(JLabel.CENTER);
		moneyInfoLabel.setHorizontalAlignment(JLabel.CENTER);
		layerPane.add(moneyInfoLabel, new Integer(3000));
		moneyInfoLabel.setVisible(false);

		gameWonLabel = new JLabel(translator.translateMessage("wonGame"));
		gameWonLabel.setBounds(0, 0, fieldWidth, fieldHeight);
		gameWonLabel.setVerticalAlignment(JLabel.CENTER);
		gameWonLabel.setHorizontalAlignment(JLabel.CENTER);
		layerPane.add(gameWonLabel, new Integer(3000));
		gameWonLabel.setVisible(false);

		lostGameLabel = new JLabel(translator.translateMessage("lostGame"));
		lostGameLabel.setBounds(0, 0, fieldWidth, fieldHeight);
		lostGameLabel.setVerticalAlignment(JLabel.CENTER);
		lostGameLabel.setHorizontalAlignment(JLabel.CENTER);
		layerPane.add(lostGameLabel, new Integer(3000));
		lostGameLabel.setVisible(false);

		nextWaveInfoLabel = new JLabel("---");
		nextWaveInfoLabel.setBounds(0, 0, fieldWidth, fieldHeight);
		nextWaveInfoLabel.setVerticalAlignment(JLabel.CENTER);
		nextWaveInfoLabel.setHorizontalAlignment(JLabel.CENTER);
		layerPane.add(nextWaveInfoLabel, new Integer(3000));
		nextWaveInfoLabel.setVisible(false);

		skinList.setSelectedIndex(0);

		setVisible(true);
	}

	@Override
	public void updateGUI() {
		if (firstStarted == 1) {
			pauseButton.setEnabled(true);
			gameSpeed.setEnabled(true);
			skipButton.setEnabled(true);
			firstStarted = 0;
		}

		if (controller.getSpawner().getRestOfInitColldownInSec() >= 0) {
			nextWaveInfoLabel
					.setText(translator.translateMessage("nextWaveIn"));

			if (controller.getSpawner().getRestOfInitColldownInSec() < 5) {
				nextWaveInfoLabel
						.setText("<html><div align=\"center\"><font size=\"7\">"
								+ controller.getSpawner()
										.getRestOfInitColldownInSec()
								+ "</font></div></html>");
			}
			if (controller.getSpawner().getRestOfInitColldownInSec() == 0) {
				nextWaveInfoLabel.setVisible(false);
				skipButton.setEnabled(false);
			} else {
				nextWaveInfoLabel.setVisible(true);
				skipButton.setEnabled(true);
			}
		}

		if (controller.gameIsLost() == true) {
			pauseButton.setEnabled(false);
			skipButton.setEnabled(false);
			for (JButton element : entities) {
				for (MouseListener mouseListener : element.getMouseListeners()) {
					if (mouseListener.getClass() == towerInfoListener.class) {
						element.removeMouseListener(mouseListener);
					}
				}
			}
			lostGameLabel.setVisible(true);
			playGameLostSound = new AudioThread(gameLostSoundPath);
			if (soundStatus == true) {
				playGameLostSound.start(); // Sound Spiel verloren
			}
		} else if (controller.gameIsWon() == true) {
			pauseButton.setEnabled(false);
			skipButton.setEnabled(false);
			for (JButton element : entities) {
				for (MouseListener mouseListener : element.getMouseListeners()) {
					if (mouseListener.getClass() == towerInfoListener.class) {
						element.removeMouseListener(mouseListener);
					}
				}
			}
			gameWonLabel.setVisible(true);
			playGameWonSound = new AudioThread(gameWonSoundPath);
			if (soundStatus == true) {
				playGameWonSound.start(); // Sound Spiel gewonnen
			}
		} else {

			if (moneyLabelShowLength == 1) {
				moneyInfoLabel.setVisible(false);
			} else if (moneyLabelShowLength > 0) {
				moneyLabelShowLength--;
				if (moneyLabelCounter == 0) {
					moneyLabelCounter = 5;
					if (moneyLabelStatus == 1) {
						moneyInfoLabel.setVisible(true);
						moneyLabelStatus = 0;
					} else {
						moneyInfoLabel.setVisible(false);
						moneyLabelStatus = 1;
					}
				} else if (moneyLabelCounter > 0) {
					moneyLabelCounter--;
				}
			}

			moneyLabel.setText(translator.translateMessage("money",
					String.valueOf(controller.getPlayer().getMoney())));
			lifepointsLabel.setText(translator.translateMessage("lives",
					String.valueOf(controller.getPlayer().getLives())));
			currentLevel.setText("Level: " + controller.getActualLevel()
					+ "/30");
			if (givenMonsters.isEmpty() != true) {
				Monster monster = givenMonsters.get(0);
				String type = null;
				String info = null;
				if (monster.getClass() == MonsterTypeHealer.class) {
					type = translator.translateMessage("healer");
					info = translator.translateMessage("healerInfo");
				} else if (monster.getClass() == MonsterTypeNormal.class) {
					type = "Normal";
					info = "Normal";
				} else if (monster.getClass() == MonsterTypeRunner.class) {
					type = translator.translateMessage("runner");
					info = translator.translateMessage("runnerInfo");
				} else if (monster.getClass() == MonsterTypeSpeed.class) {
					type = translator.translateMessage("speed");
					info = translator.translateMessage("speedInfo");
				} else if (monster.getClass() == MonsterTypeTank.class) {
					type = translator.translateMessage("tank");
					info = translator.translateMessage("tankInfo");
				}

				double energie = monster.getMaxHP();
				int speed = monster.getMaxSpeed();
				int money = monster.getBounty();
				int armor = (int) ((1 - monster.getDmgMultiplier()) * 100);

				currentWaveInfo.setText(translator.translateMessage(
						"currentWaveInfo", type, String.valueOf((int) energie),
						String.valueOf(armor), String.valueOf(speed),
						String.valueOf(money), info));

			}

			deleteObjects = new Vector<Object>();
			for (Entry<Monster, JLabel> monsterLabel : monsterLabels.entrySet()) {
				if (givenMonsters.contains(monsterLabel.getKey()) == false) {
					monsterLabel.getValue().setVisible(false);
					layerPane.remove(monsterLabel.getValue());
					monsterLabels.remove(monsterLabel);
					deleteObjects.add(monsterLabel.getValue());
				}
			}
			for (Object element : deleteObjects) {
				monsterLabels.remove((JLabel) element);
			}
			for (drawLine line : hpLines) {
				line.setVisible(false);
				layerPane.remove(line);
			}
			hpLines = new Vector<drawLine>();
			for (Monster monster : givenMonsters) {

				int hpPercent = (int) (monster.getActualHP()
						/ monster.getMaxHP() * 100);

				int y = monster.getPosition().y - controller.getFieldSize();
				int x1 = monster.getPosition().x - controller.getFieldSize()
						/ 2;
				int x3 = monster.getPosition().x + controller.getFieldSize()
						/ 2;
				int x2 = (((x3 - x1) * hpPercent) / 100) + x1;

				drawLine hpLine1 = new drawLine(x1, y, x2, y, hpLine1Color);
				hpLines.add(hpLine1);
				layerPane.add(hpLine1, new Integer(1100));
				drawLine hpLine2 = new drawLine(x2, y, x3, y, hpLine2Color);
				hpLines.add(hpLine2);
				layerPane.add(hpLine2, new Integer(1000));

				if (monsterLabels.containsKey(monster) == true) {
					monsterLabels.get(monster).setLocation(
							monster.getPosition().x - 25,
							monster.getPosition().y - 25);
				} else {
					JLabel label = null;
					if (monster.getClass() == MonsterTypeNormal.class) {
						label = new JLabel(normalIcon);
					} else if (monster.getClass() == MonsterTypeHealer.class) {
						label = new JLabel(healerIcon);
					} else if (monster.getClass() == MonsterTypeTank.class) {
						label = new JLabel(tankIcon);
					} else if (monster.getClass() == MonsterTypeSpeed.class) {
						label = new JLabel(speedIcon);
					} else if (monster.getClass() == MonsterTypeRunner.class) {
						label = new JLabel(runnerIcon);
					}

					monsterLabels.put(monster, label);
					label.setOpaque(false);
					label.setBounds(monster.getPosition().x - 25,
							monster.getPosition().y - 25, 50, 50);

					layerPane.add(label, new Integer(200));
				}
			}

			for (drawBullet bullet : bulletCircles) {
				bullet.setVisible(false);
				layerPane.remove(bullet);
			}
			bulletCircles = new Vector<drawBullet>();
			for (Bullet bullet : givenBullets) {
				drawBullet bulletCircle = new drawBullet(
						bullet.getPosition().x, bullet.getPosition().y);
				bulletCircles.add(bulletCircle);
				layerPane.add(bulletCircle, new Integer(1500));
				// Sound Sniper wird abgespielt, wenn kein Sniper sound thread
				// mehr aktiv ist
				if (sniperSound.isAlive()) {

				} else {
					if (soundStatus == true) {
						sniperSound = new AudioThread(sniperSoundPath); // Sound
																		// Sniper
						sniperSound.start(); // Sound Sniper
					}
				}
			}

			for (drawLine draw : laserDraws) {
				draw.setVisible(false);
				layerPane.remove(draw);
			}
			laserDraws = new Vector<drawLine>();
			for (Tower tower : givenTowers) {
				if (tower.getClass() == LaserTower.class) {
					LaserTower laserTower = (LaserTower) tower;
					if (laserTower.getTarget() != null) {
						drawLine laser = new drawLine(
								laserTower.getPosition().x,
								laserTower.getPosition().y, laserTower
										.getTarget().getPosition().x,
								laserTower.getTarget().getPosition().y,
								laserColor);
						laserDraws.add(laser);
						layerPane.add(laser, new Integer(1000));
						// Sound Laser wird abgespielt, wenn kein Laser sound
						// thread mehr aktiv ist
						if (laserSound.isAlive()) // Sound Laser
						{

						} else {
							if (soundStatus == true) {
								laserSound = new AudioThread(laserSoundPath); // Sound
																				// Laser
								laserSound.start();// Sound Laser
							}
						}
					}
				}
			}
		}
	}

	private class fieldButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			ImageIcon icon = (ImageIcon) button.getIcon();
			clickedButton = button;
			if (soundStatus == true) {
				clickSound = new AudioThread(clickSoundPath); // Sound Klick
			}

			if (menuIsShown == 0) {

				if (icon == freeIcon) {
					towerMenu.show(button, controller.getFieldSize(),
							controller.getFieldSize());
					menuIsShown = 1;
					if (soundStatus == true) {
						clickSound.start(); // Sound Klick auf leeres bebaubares
											// Feld
					}
				} else if (icon == aoeTowerIcon || icon == slowTowerIcon
						|| icon == laserTowerIcon || icon == sniperTowerIcon) {
					clickedOptionTower = button;
					towerOptionMenu.show(button, controller.getFieldSize(),
							controller.getFieldSize());
					menuIsShown = 1;
					if (soundStatus == true) {
						clickSound.start(); // Sound Klick auf leeres bebaubares
											// Feld
					}
				}

			} else {
				menuIsShown = 0;
			}
		}
	}

	private class menuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			infoMenu.setVisible(false);
			menuIsShown = 0;
			int notEnoughMoney = 0;
			JButton button = (JButton) e.getSource();
			if (button.getText()
					.equals(translator.translateMessage("aoeTower")) == true
					&& controller.getPlayer().getMoney() >= 75) {
				clickedButton.setIcon(aoeTowerIcon);
				clickedButton.addMouseListener(new towerInfoListener());
				controller.buildTower(
						controller.cPointToMPoint(clickedButton.getLocation()),
						"aoeTower");

				towerButtons.add(clickedButton);
				if (soundStatus == true) {
					playBuildTowerSound = new AudioThread(buildTowerSoundPath); // Sound
																				// Objekt
					playBuildTowerSound.start(); // Sound für erfolgreichen Bau

				}
				for (Entry<JButton, String> entitie : entitiesIcon.entrySet())
					if (entitie.getKey() == clickedButton)
						entitie.setValue("aoe");

			} else if (button.getText().equals(
					translator.translateMessage("slowTower")) == true
					&& controller.getPlayer().getMoney() >= 100) {
				clickedButton.setIcon(slowTowerIcon);
				clickedButton.addMouseListener(new towerInfoListener());
				controller.buildTower(
						controller.cPointToMPoint(clickedButton.getLocation()),
						"slowTower");
				towerButtons.add(clickedButton);
				if (soundStatus == true) {
					playBuildTowerSound = new AudioThread(buildTowerSoundPath); // Sound
																				// Objekt
					playBuildTowerSound.start(); // Sound für erfolgreichen Bau
				}
				for (Entry<JButton, String> entitie : entitiesIcon.entrySet())
					if (entitie.getKey() == clickedButton)
						entitie.setValue("slow");

			} else if (button.getText().equals(
					translator.translateMessage("laserTower")) == true
					&& controller.getPlayer().getMoney() >= 50) {
				clickedButton.setIcon(laserTowerIcon);
				clickedButton.addMouseListener(new towerInfoListener());
				controller.buildTower(
						controller.cPointToMPoint(clickedButton.getLocation()),
						"laserTower");
				towerButtons.add(clickedButton);
				if (soundStatus == true) {
					playBuildTowerSound = new AudioThread(buildTowerSoundPath); // Sound
																				// Objekt
					playBuildTowerSound.start(); // Sound für erfolgreichen Bau
				}
				for (Entry<JButton, String> entitie : entitiesIcon.entrySet())
					if (entitie.getKey() == clickedButton)
						entitie.setValue("laser");

			} else if (button.getText().equals(
					translator.translateMessage("sniperTower")) == true
					&& controller.getPlayer().getMoney() >= 25) {
				clickedButton.setIcon(sniperTowerIcon);
				clickedButton.addMouseListener(new towerInfoListener());
				controller.buildTower(
						controller.cPointToMPoint(clickedButton.getLocation()),
						"sniperTower");
				towerButtons.add(clickedButton);
				if (soundStatus == true) {
					playBuildTowerSound = new AudioThread(buildTowerSoundPath); // Sound
																				// Objekt
					playBuildTowerSound.start(); // Sound für erfolgreichen Bau
				}
				for (Entry<JButton, String> entitie : entitiesIcon.entrySet())
					if (entitie.getKey() == clickedButton)
						entitie.setValue("sniper");
			} else {
				moneyInfoLabel.setVisible(true);
				moneyLabelShowLength = (int) (2000 / controller.getTick());
				moneyLabelStatus = 0;
				moneyLabelCounter = 5;
				notEnoughMoney = 1;
				// Sound Fehler, wenn nicht genug Geld zum Bau vorhanden ist
				if (failSound.isAlive()) {

				} else {
					if (soundStatus == true) {
						failSound = new AudioThread(failSoundPath);
						failSound.start();
					}
				}
			}

			if (notEnoughMoney != 1) {
				moneyLabel.setText(translator.translateMessage("money",
						String.valueOf(controller.getPlayer().getMoney())));

				Point buttonPosition = controller.cPointToMPoint(clickedButton
						.getLocation());
				Tower tower = null;
				for (Tower towerElement : givenTowers) {
					if (towerElement.getPosition().equals(buttonPosition))
						tower = towerElement;
				}

				JLabel towerRangeLabel = new JLabel("<html><font size=\"2\">"
						+ new Integer(tower.getRangeLevel()).toString()
						+ "</font></html>");
				if (skinList.getSelectedIndex() == 1) {
					towerRangeLabel.setForeground(Color.BLACK);
				} else {
					towerRangeLabel.setForeground(Color.WHITE);
				}
				towerRangeLabel.setVerticalAlignment(JLabel.TOP);
				towerRangeLabel.setHorizontalAlignment(JLabel.LEFT);
				towerRangeLabel.setBounds(
						tower.getPosition().x - controller.getFieldSize() / 2,
						tower.getPosition().y, 20, 20);
				rangeLevelLabels.put(tower, towerRangeLabel);
				layerPane.add(towerRangeLabel, new Integer(3000));

				JLabel towerEfficiencyLabel = new JLabel(
						"<html><font size=\"2\">"
								+ new Integer(tower.getEfficiencyLevel())
										.toString() + "</font></html>");
				if (skinList.getSelectedIndex() == 1) {
					towerEfficiencyLabel.setForeground(Color.BLACK);
				} else {
					towerEfficiencyLabel.setForeground(Color.WHITE);
				}
				towerEfficiencyLabel.setVerticalAlignment(JLabel.TOP);
				towerEfficiencyLabel.setHorizontalAlignment(JLabel.LEFT);
				towerEfficiencyLabel.setBounds(tower.getPosition().x
						+ controller.getFieldSize() / 2 - 6,
						tower.getPosition().y, 20, 20);
				efficiencyLevelLabels.put(tower, towerEfficiencyLabel);
				layerPane.add(towerEfficiencyLabel, new Integer(3000));
			}
			if (towerMenu.isVisible() == true) {
				towerMenu.setVisible(false);
			}
		}
	}

	private class towerInfoListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);

			JButton button = (JButton) e.getSource();
			ImageIcon icon = (ImageIcon) button.getIcon();

			if (towerInfoIsShown == 1) {
				layerPane.remove(towerRange);
				towerRange.setVisible(false);
				towerInfoIsShown = 0;
			}

			if (icon == aoeTowerIcon || icon == slowTowerIcon
					|| icon == laserTowerIcon || icon == sniperTowerIcon) {
				Point buttonPosition = controller.cPointToMPoint(button
						.getLocation());
				for (Tower tower : givenTowers) {
					if (tower.getPosition().equals(buttonPosition)) {

						towerRange = new drawRange(buttonPosition.x
								- tower.getRange(), buttonPosition.y
								- tower.getRange(), 2 * tower.getRange());
						layerPane.add(towerRange, new Integer(1000));

						towerInfoIsShown = 1;
					}
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);

			if (towerInfoIsShown == 1) {
				layerPane.remove(towerRange);
				layerPane.repaint();
				towerInfoIsShown = 0;
			}
		}
	}

	private class towerInfoMenuListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);

			JButton button = (JButton) e.getSource();
			if (button.getText()
					.equals(translator.translateMessage("aoeTower"))) {
				double damage = 1000 / controller.getTick() * 1.3;
				infoMenuLabel.setText(translator.translateMessage(
						"aoeTowerInfo", String.valueOf(damage)));
			} else if (button.getText().equals(
					translator.translateMessage("slowTower"))) {
				infoMenuLabel.setText(translator
						.translateMessage("slowTowerInfo"));
			} else if (button.getText().equals(
					translator.translateMessage("sniperTower"))) {
				infoMenuLabel.setText(translator
						.translateMessage("sniperTowerInfo"));
			} else if (button.getText().equals(
					translator.translateMessage("laserTower"))) {
				double damage = 1000 / controller.getTick() * 2;
				infoMenuLabel.setText(translator.translateMessage(
						"laserTowerInfo", String.valueOf(damage)));
			}

			infoMenu.setSize(150, 190);
			if (clickedButton.getLocation().x > fieldWidth / 2) {
				infoMenu.setBounds(
						clickedButton.getLocation().x
								- infoMenu.getSize().width
								+ controller.getFieldSize(),
						clickedButton.getLocation().y
								+ controller.getFieldSize(), 150, 200);

			} else {
				infoMenu.setBounds(
						clickedButton.getLocation().x
								+ towerMenu.getSize().width
								+ controller.getFieldSize(),
						clickedButton.getLocation().y
								+ controller.getFieldSize(), 150, 200);
			}
			infoMenu.setVisible(true);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
			infoMenu.setVisible(false);
		}
	}

	private class optionMenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			towerOptionMenu.setVisible(false);
			infoMenu.setVisible(false);
			menuIsShown = 0;

			Point buttonPosition = controller.cPointToMPoint(clickedOptionTower
					.getLocation());
			JButton removeButton = null;
			Tower sellTower = null;
			for (Tower tower : givenTowers) {
				if (tower.getPosition().equals(buttonPosition)) {
					if (button.getText().equals(
							translator.translateMessage("upgradeRange")) == true) {
						if (controller.getPlayer().getMoney() >= tower
								.getUpgradeRangeCost()) {
							// Wenn noch nicht die Höchste Turmausbaustufe
							// erreicht ist wird der upgrade sound abgespielt
							// ansonsten der fail sound
							if (tower.getRangeLevel() != tower.maxRangeLevel) {
								if (soundStatus == true) {
									upgradeSound = new AudioThread(
											upgradeSoundPath); // Sound
																// Upgrade
									upgradeSound.start(); // Sound Upgrade
								}
							} else {
								failSound = new AudioThread(failSoundPath);
								failSound.start();
							}
							controller.upgradTowerRange(tower);
							moneyLabel.setText(translator.translateMessage(
									"money", String.valueOf(controller
											.getPlayer().getMoney())));
							for (Entry<Tower, JLabel> entry : rangeLevelLabels
									.entrySet()) {
								if (entry.getKey() == tower) {
									entry.getValue().setText(
											"<html><font size=\"2\">"
													+ new Integer(tower
															.getRangeLevel())
															.toString()
													+ "</font></html>");
								}
							}
						} else {
							if (tower.getRangeLevel() != 5) {
								moneyInfoLabel.setVisible(true);
								moneyLabelShowLength = (int) (2000 / controller
										.getTick());
								moneyLabelStatus = 0;
								moneyLabelCounter = 5;
							}
							// Sound Fehler, wenn nicht genug Geld zum Upgrade
							// vorhanden ist
							if (failSound.isAlive()) {

							} else {
								if (soundStatus == true) {
									failSound = new AudioThread(failSoundPath);
									failSound.start();
								}
							}
						}
					} else if (button.getText().equals(
							translator.translateMessage("upgradeEfficiency")) == true) {
						if (controller.getPlayer().getMoney() >= tower
								.getUpgradeEfficiencyCost()) {
							// Wenn noch nicht die Höchste Turmausbaustufe
							// erreicht ist wird der upgrade sound abgespielt
							// ansonsten der fail sound
							if (tower.getRangeLevel() != tower.maxRangeLevel) {
								if (soundStatus == true) {
									upgradeSound = new AudioThread(
											upgradeSoundPath); // Sound
																// Upgrade
									upgradeSound.start(); // Sound Upgrade
								}
							} else {
								failSound = new AudioThread(failSoundPath);
								failSound.start();
							}
							controller.upgradTowerEfficiency(tower);
							moneyLabel.setText(translator.translateMessage(
									"money", String.valueOf(controller
											.getPlayer().getMoney())));
							for (Entry<Tower, JLabel> entry : efficiencyLevelLabels
									.entrySet()) {
								if (entry.getKey() == tower) {
									entry.getValue()
											.setText(
													"<html><font size=\"2\">"
															+ new Integer(
																	tower.getEfficiencyLevel())
																	.toString()
															+ "</font></html>");
								}
							}
						} else {
							if (tower.getEfficiencyLevel() != 5) {
								moneyInfoLabel.setVisible(true);
								moneyLabelShowLength = (int) (2000 / controller
										.getTick());
								moneyLabelStatus = 0;
								moneyLabelCounter = 5;
							}
							// Sound Fehler, wenn nicht genug Geld zum Upgrade
							// vorhanden ist
							if (failSound.isAlive()) {

							} else {
								if (soundStatus == true) {
									failSound = new AudioThread(failSoundPath);
									failSound.start();
								}
							}
						}
					} else if (button.getText().equals(
							translator.translateMessage("sell")) == true) {
						for (JButton tempButton : towerButtons) {
							if (controller.cPointToMPoint(
									tempButton.getLocation()).equals(
									tower.getPosition())) {
								tempButton.setIcon(freeIcon);
								removeButton = tempButton;
								sellTower = tower;

								for (Entry<Tower, JLabel> entry : rangeLevelLabels
										.entrySet()) {
									if (entry.getKey() == tower) {
										layerPane.remove(entry.getValue());
										entry.getValue().setVisible(false);
										rangeLevelLabels.remove(entry);
									}
								}
								for (Entry<Tower, JLabel> entry : efficiencyLevelLabels
										.entrySet()) {
									if (entry.getKey() == tower) {
										layerPane.remove(entry.getValue());
										entry.getValue().setVisible(false);
										rangeLevelLabels.remove(entry);
									}
								}
							}
						}
					}
				}
			}
			if (removeButton != null) {
				towerButtons.remove(removeButton);
				controller.sellTower(sellTower);
				for (Entry<JButton, String> entitie : entitiesIcon.entrySet())
					if (entitie.getKey() == removeButton)
						entitie.setValue("_");
				moneyLabel.setText(translator.translateMessage("money",
						String.valueOf(controller.getPlayer().getMoney())));
				removeButton = null;
				sellTower = null;
				if (soundStatus == true) {
					sellSound = new AudioThread(sellSoundPath); // Sound beim
																// Verkaufen
					sellSound.start(); // Sound beim Verkaufen
				}
			}
		}
	}

	private class languageButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton button = (JRadioButton) e.getSource();
			if (button.getText().equals(
					translator.translateMessage("germanLabel")) == true) {
				translator.setTranslatorLocale(Locale.GERMANY);
			} else {
				translator.setTranslatorLocale(Locale.US);
			}

			aoeTowerButton.setText(translator.translateMessage("aoeTower"));
			slowTowerButton.setText(translator.translateMessage("slowTower"));
			laserTowerButton.setText(translator.translateMessage("laserTower"));
			sniperTowerButton.setText(translator
					.translateMessage("sniperTower"));
			upgradeRangeButton.setText(translator
					.translateMessage("upgradeRange"));
			upgradeEfficiencyButton.setText(translator
					.translateMessage("upgradeEfficiency"));
			saleButton.setText(translator.translateMessage("sell"));
			languageLabel.setText(translator.translateMessage("language"));
			generateLevelButton.setText(translator
					.translateMessage("generateLevel"));
			quitButton.setText(translator.translateMessage("quit"));
			skipButton.setText(translator.translateMessage("skipSpawn"));
			speedLabel.setText(translator.translateMessage("speed"));

			moneyLabel.setText(translator.translateMessage("money",
					String.valueOf(controller.getPlayer().getMoney())));
			lifepointsLabel.setText(translator.translateMessage("lives",
					String.valueOf(controller.getPlayer().getLives())));
			currentLevel.setText("Level: " + controller.getActualLevel()
					+ "/30");

			nextWaveInfoLabel
					.setText(translator.translateMessage("nextWaveIn"));
			nextWaveInfoLabel.setVisible(false);
			lostGameLabel.setText(translator.translateMessage("lostGame"));
			gameWonLabel.setText(translator.translateMessage("wonGame"));
			moneyInfoLabel.setText(translator
					.translateMessage("notEnoughMoney"));
			pausedLabel.setText(translator.translateMessage("pausedLabel"));
			buttonUS.setText(translator.translateMessage("englishLabel"));
			buttonDE.setText(translator.translateMessage("germanLabel"));
			if (paused == 1)
				pauseButton.setText(translator.translateMessage("resume"));

			int oldIndex = skinList.getSelectedIndex();
			String[] skinStrings = { translator.translateMessage("skin1"),
					translator.translateMessage("skin2"),
					translator.translateMessage("skin3"), };
			skinList.setModel(new javax.swing.DefaultComboBoxModel(skinStrings));
			skinList.setSelectedIndex(oldIndex);

			soundON.setText(translator.translateMessage("on"));
			soundOFF.setText(translator.translateMessage("off"));

			if (givenMonsters.isEmpty() != true) {
				Monster monster = givenMonsters.get(0);
				String type = null;
				String info = null;
				if (monster.getClass() == MonsterTypeHealer.class) {
					type = translator.translateMessage("healer");
					info = translator.translateMessage("healerInfo");
				} else if (monster.getClass() == MonsterTypeNormal.class) {
					type = "Normal";
					info = "Normal";
				} else if (monster.getClass() == MonsterTypeRunner.class) {
					type = translator.translateMessage("runner");
					info = translator.translateMessage("runnerInfo");
				} else if (monster.getClass() == MonsterTypeSpeed.class) {
					type = translator.translateMessage("speed");
					info = translator.translateMessage("speedInfo");
				} else if (monster.getClass() == MonsterTypeTank.class) {
					type = translator.translateMessage("tank");
					info = translator.translateMessage("tankInfo");
				}

				double energie = monster.getMaxHP();
				int speed = monster.getMaxSpeed();
				int money = monster.getBounty();

				currentWaveInfo.setText(translator.translateMessage(
						"currentWaveInfo", type, String.valueOf((int) energie),
						String.valueOf(speed), String.valueOf(money), info));
			} else {
				currentWaveInfo.setText(translator
						.translateMessage("currentWaveInfoBegin"));
			}
		}
	}

	private class optionMenuInfoListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);

			JButton button = (JButton) e.getSource();

			Tower tower = null;
			for (Tower element : givenTowers) {
				if (element.getPosition().equals(
						controller.cPointToMPoint(clickedButton.getLocation())) == true) {
					tower = element;
				}
			}

			int rangeLevel = tower.getRangeLevel();
			int nextRangeLevel = tower.getRangeLevel() + 1;
			int rangeValue = tower.getRange();
			int nextRangeValue = tower.getRangeByLevel(nextRangeLevel);
			int efficiencyLevel = tower.getEfficiencyLevel();
			int nextEfficiencyLevel = efficiencyLevel + 1;
			int rangeCost = tower.getUpgradeRangeCost();
			int efficiencyCost = tower.getUpgradeEfficiencyCost();
			int investment = tower.getInvestment();
			int newMoney = controller.getPlayer().getMoney() + investment;

			if (button.getText().equals(
					translator.translateMessage("upgradeRange")) == true) {
				if (rangeLevel != 5) {
					infoMenuLabel.setText(translator.translateMessage(
							"upgradeRangeLabel", String.valueOf(rangeLevel),
							String.valueOf(nextRangeLevel),
							String.valueOf(rangeValue),
							String.valueOf(nextRangeValue),
							String.valueOf(rangeCost)));
				} else {
					infoMenuLabel.setText(translator
							.translateMessage("fullUpgraded"));
				}
			}

			String efficiency = null;
			if (button.getText().equals(
					translator.translateMessage("upgradeEfficiency")) == true) {

				if (efficiencyLevel != 5) {
					int slowLabelFullUpgraded = 0;
					if (tower.getClass() == AOETower.class) {
						AOETower aoeTower = (AOETower) tower;
						double oldDamage = Math.round(1000
								/ controller.getTick()
								* aoeTower.getDptByLevel(efficiencyLevel));
						double newDamage = Math.round(1000
								/ controller.getTick()
								* aoeTower.getDptByLevel(nextEfficiencyLevel));

						efficiency = translator.translateMessage(
								"efficiencyTemp1", String.valueOf(oldDamage),
								String.valueOf(newDamage));

					} else if (tower.getClass() == LaserTower.class) {
						LaserTower laserTower = (LaserTower) tower;
						double oldDamage = Math.round(1000
								/ controller.getTick()
								* laserTower.getDptByLevel(efficiencyLevel));
						double newDamage = Math
								.round(1000
										/ controller.getTick()
										* laserTower
												.getDptByLevel(nextEfficiencyLevel));
						efficiency = translator.translateMessage(
								"efficiencyTemp2", String.valueOf(oldDamage),
								String.valueOf(newDamage));

					} else if (tower.getClass() == SlowTower.class) {
						if (efficiencyLevel != 2) {
							SlowTower slowTower = (SlowTower) tower;
							String percent = null;
							String newPercent = null;
							percent = "33%";
							newPercent = "67%";

							efficiency = translator.translateMessage(
									"efficiencyTemp3", String.valueOf(percent),
									String.valueOf(newPercent));
						} else {
							infoMenuLabel.setText(translator
									.translateMessage("fullUpgraded"));
							slowLabelFullUpgraded = 1;
						}

					} else if (tower.getClass() == SniperTower.class) {

						SniperTower sniperTower = (SniperTower) tower;
						double oldCD = (Math.round(sniperTower
								.getCdTimeByLevel(efficiencyLevel)
								* controller.getTick() / 100)) / new Double(10);
						double newCD = (Math.round(sniperTower
								.getCdTimeByLevel(nextEfficiencyLevel)
								* controller.getTick() / 100)) / new Double(10);

						efficiency = translator.translateMessage(
								"efficiencyTemp4", String.valueOf(sniperTower
										.getDmgByLevel(efficiencyLevel)),
								String.valueOf(sniperTower
										.getDmgByLevel(nextEfficiencyLevel)),
								String.valueOf(oldCD), String.valueOf(newCD));
					}

					if (slowLabelFullUpgraded != 1) {
						infoMenuLabel.setText(translator.translateMessage(
								"upgradeEfficiencyLabel",
								String.valueOf(efficiencyLevel),
								String.valueOf(nextEfficiencyLevel),
								String.valueOf(efficiency),
								String.valueOf(efficiencyCost)));
					}

				} else {
					infoMenuLabel.setText(translator
							.translateMessage("fullUpgraded"));
				}
			}

			if (button.getText().equals(translator.translateMessage("sell")) == true) {
				infoMenuLabel.setText(translator.translateMessage("sellLabel",
						String.valueOf(investment),
						String.valueOf(controller.getPlayer().getMoney()),
						String.valueOf(newMoney)));
			}

			infoMenu.setSize(140, 100);
			if (clickedButton.getLocation().x > fieldWidth / 2) {
				infoMenu.setBounds(
						clickedButton.getLocation().x
								- infoMenu.getSize().width
								+ controller.getFieldSize(),
						clickedButton.getLocation().y
								+ controller.getFieldSize(), 160, 100);

			} else {
				infoMenu.setBounds(
						clickedButton.getLocation().x
								+ towerOptionMenu.getSize().width
								+ controller.getFieldSize(),
						clickedButton.getLocation().y
								+ controller.getFieldSize(), 160, 100);
			}
			infoMenu.setVisible(true);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);

			infoMenu.setVisible(false);
		}
	}

	private class pauseButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (paused == 0) {

				pauseButton.setText(translator.translateMessage("resume"));

				for (JButton element : entities)
					element.setEnabled(false);

				pausedLabel.setVisible(true);
				controller.getTimer().stop();

				paused = 1;
			} else {

				pauseButton.setText("Pause");

				for (JButton element : entities)
					element.setEnabled(true);

				pausedLabel.setVisible(false);
				controller.getTimer().start();

				paused = 0;
			}
		}
	}

	private class quitButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	private class gameSpeedChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent arg0) {
			JSlider slider = (JSlider) arg0.getSource();
			double percent = 0;
			switch (slider.getValue()) {
			case 1:
				percent = 1;
				break;
			case 2:
				percent = 2;
				break;
			case 3:
				percent = 3;
				break;
			case 4:
				percent = 4;
				break;
			}

			int newTick = (int) (standardTick / percent);
			controller.setTick(newTick);
			controller.getTimer().setDelay(newTick);

		}
	}

	private class skipSpawnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.skipSpawnTimer();
			if (paused == 1)
				pauseButton.doClick();
		}
	}

	private class startButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			if (started == 0) {
				controller.startTimer();
				button.setText(translator.translateMessage("start"));
				started = 1;
			} else {
				for (Entry<Monster, JLabel> monster : monsterLabels.entrySet()) {
					monster.getValue().setVisible(false);
					layerPane.remove(monster.getValue());
				}
				for (Entry<Tower, JLabel> efficiencyLevel : efficiencyLevelLabels
						.entrySet()) {
					efficiencyLevel.getValue().setVisible(false);
					layerPane.remove(efficiencyLevel.getValue());
				}
				for (Entry<Tower, JLabel> rangeLevel : rangeLevelLabels
						.entrySet()) {
					rangeLevel.getValue().setVisible(false);
					layerPane.remove(rangeLevel.getValue());
				}
				for (drawLine laserDraw : laserDraws) {
					laserDraw.setVisible(false);
					layerPane.remove(laserDraw);
				}
				for (drawBullet bullet : bulletCircles) {
					bullet.setVisible(false);
					layerPane.remove(bullet);
				}
				for (JButton towerButton : towerButtons) {
					towerButton.setIcon(freeIcon);
					for (MouseListener mouseListener : towerButton
							.getMouseListeners()) {
						if (mouseListener.getClass() == towerInfoListener.class) {
							towerButton.removeMouseListener(mouseListener);
						}
					}
				}
				for (drawLine hpLine : hpLines) {
					hpLine.setVisible(false);
					layerPane.remove(hpLine);
				}

				gameWonLabel.setVisible(false);
				lostGameLabel.setVisible(false);

				if (pauseButton.isEnabled() == false)
					pauseButton.setEnabled(true);
				if (skipButton.isEnabled() == false)
					skipButton.setEnabled(true);

				if (towerInfoIsShown == 1) {
					layerPane.remove(towerRange);
					towerRange.setVisible(false);
					towerInfoIsShown = 0;
				}

				controller.resetGame();

				rangeLevelLabels = new HashMap<Tower, JLabel>();
				efficiencyLevelLabels = new HashMap<Tower, JLabel>();
				laserDraws = new Vector<drawLine>();
				bulletCircles = new Vector<drawBullet>();
				hpLines = new Vector<drawLine>();

				Vector<JButton> deleteButtons = new Vector<JButton>();
				for (Entry<JButton, String> buttonEntry : entitiesIcon
						.entrySet()) {
					if (buttonEntry.getValue().equals("aoe")) {
						deleteButtons.add(buttonEntry.getKey());
					} else if (buttonEntry.getValue().equals("sniper")) {
						deleteButtons.add(buttonEntry.getKey());
					} else if (buttonEntry.getValue().equals("laser")) {
						deleteButtons.add(buttonEntry.getKey());
					} else if (buttonEntry.getValue().equals("slow")) {
						deleteButtons.add(buttonEntry.getKey());
					}
				}
				for (JButton deleteButton : deleteButtons) {
					entitiesIcon.remove(deleteButton);
					entitiesIcon.put(deleteButton, "_");
				}

				monsterLabels = new HashMap<Monster, JLabel>();
				givenMonsters = controller.getMonsters();
				givenTowers = controller.getTowers();
				givenBullets = controller.getBullets();

				if (paused == 1)
					pauseButton.doClick();
			}
		}
	}

	private class skinListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox comboBox = (JComboBox) e.getSource();
			String skin = (String) comboBox.getSelectedItem();
			if (skin.equals(translator.translateMessage("skin1")) == true) {
				iconPath = "./resources/images/defaultskin/";
				for (Entry<Tower, JLabel> efficiencyLevel : efficiencyLevelLabels
						.entrySet()) {
					efficiencyLevel.getValue().setForeground(Color.WHITE);
				}
				for (Entry<Tower, JLabel> rangeLevel : rangeLevelLabels
						.entrySet()) {
					rangeLevel.getValue().setForeground(Color.WHITE);
				}

				moneyInfoLabel.setForeground(Color.RED);
				gameWonLabel.setForeground(Color.RED);
				lostGameLabel.setForeground(Color.RED);
				pausedLabel.setForeground(Color.RED);
				nextWaveInfoLabel.setForeground(Color.RED);

				laserColor = Color.RED;
				hpLine1Color = Color.GREEN;
				hpLine2Color = Color.RED;

			} else if (skin.equals(translator.translateMessage("skin2")) == true) {
				iconPath = "./resources/images/skin1/";
				for (Entry<Tower, JLabel> efficiencyLevel : efficiencyLevelLabels
						.entrySet()) {
					efficiencyLevel.getValue().setForeground(Color.BLACK);
				}
				for (Entry<Tower, JLabel> rangeLevel : rangeLevelLabels
						.entrySet()) {
					rangeLevel.getValue().setForeground(Color.BLACK);
				}

				moneyInfoLabel.setForeground(Color.BLACK);
				gameWonLabel.setForeground(Color.BLACK);
				lostGameLabel.setForeground(Color.BLACK);
				pausedLabel.setForeground(Color.BLACK);
				nextWaveInfoLabel.setForeground(Color.BLACK);

				laserColor = Color.BLACK;
				hpLine1Color = Color.BLACK;
				hpLine2Color = new Color(220, 220, 220);

			} else if (skin.equals(translator.translateMessage("skin3")) == true) {
				iconPath = "./resources/images/skin2/";
				for (Entry<Tower, JLabel> efficiencyLevel : efficiencyLevelLabels
						.entrySet()) {
					efficiencyLevel.getValue().setForeground(Color.WHITE);
				}
				for (Entry<Tower, JLabel> rangeLevel : rangeLevelLabels
						.entrySet()) {
					rangeLevel.getValue().setForeground(Color.WHITE);
				}

				moneyInfoLabel.setForeground(Color.RED);
				gameWonLabel.setForeground(Color.RED);
				lostGameLabel.setForeground(Color.RED);
				pausedLabel.setForeground(Color.RED);
				nextWaveInfoLabel.setForeground(Color.RED);

				laserColor = Color.RED;
				hpLine1Color = Color.GREEN;
				hpLine2Color = Color.RED;

			}
			spawnIcon = new ImageIcon(iconPath + spawnFile);
			freeIcon = new ImageIcon(iconPath + freeFile);
			takenIcon = new ImageIcon(iconPath + takenFile);
			targetIcon = new ImageIcon(iconPath + targetFile);
			aoeTowerIcon = new ImageIcon(iconPath + aoeTowerFile);
			slowTowerIcon = new ImageIcon(iconPath + slowTowerFile);
			laserTowerIcon = new ImageIcon(iconPath + laserTowerFile);
			sniperTowerIcon = new ImageIcon(iconPath + sniperTowerFile);
			pathURIcon = new ImageIcon(iconPath + pathURFile);
			pathRDIcon = new ImageIcon(iconPath + pathRDFile);
			pathDLIcon = new ImageIcon(iconPath + pathDLFile);
			pathLUIcon = new ImageIcon(iconPath + pathLUFile);
			pathHIcon = new ImageIcon(iconPath + pathHFile);
			pathVIcon = new ImageIcon(iconPath + pathVFile);
			normalIcon = new ImageIcon(iconPath + normalFile);
			healerIcon = new ImageIcon(iconPath + healerFile);
			tankIcon = new ImageIcon(iconPath + tankFile);
			speedIcon = new ImageIcon(iconPath + speedFile);
			runnerIcon = new ImageIcon(iconPath + runnerFile);

			for (Entry<JButton, String> button : entitiesIcon.entrySet()) {
				if (button.getValue().equals("UR") == true) {
					button.getKey().setIcon(pathURIcon);
				} else if (button.getValue().equals("RD") == true) {
					button.getKey().setIcon(pathRDIcon);
				} else if (button.getValue().equals("DL") == true) {
					button.getKey().setIcon(pathDLIcon);
				} else if (button.getValue().equals("LU") == true) {
					button.getKey().setIcon(pathLUIcon);
				} else if (button.getValue().equals("H") == true) {
					button.getKey().setIcon(pathHIcon);
				} else if (button.getValue().equals("V") == true) {
					button.getKey().setIcon(pathVIcon);
				} else if (button.getValue().equals("X") == true) {
					button.getKey().setIcon(targetIcon);
				} else if (button.getValue().equals("S") == true) {
					button.getKey().setIcon(spawnIcon);
				} else if (button.getValue().equals("_") == true) {
					button.getKey().setIcon(freeIcon);
				} else if (button.getValue().equals("#") == true) {
					button.getKey().setIcon(takenIcon);
				} else if (button.getValue().equals("aoe") == true) {
					button.getKey().setIcon(aoeTowerIcon);
				} else if (button.getValue().equals("slow") == true) {
					button.getKey().setIcon(slowTowerIcon);
				} else if (button.getValue().equals("sniper") == true) {
					button.getKey().setIcon(sniperTowerIcon);
				} else if (button.getValue().equals("laser") == true) {
					button.getKey().setIcon(laserTowerIcon);
				}
			}

			for (Entry<Monster, JLabel> monster : monsterLabels.entrySet()) {
				if (monster.getKey().getClass() == MonsterTypeNormal.class) {
					monster.getValue().setIcon(normalIcon);
				} else if (monster.getKey().getClass() == MonsterTypeHealer.class) {
					monster.getValue().setIcon(healerIcon);
				} else if (monster.getKey().getClass() == MonsterTypeTank.class) {
					monster.getValue().setIcon(tankIcon);
				} else if (monster.getKey().getClass() == MonsterTypeSpeed.class) {
					monster.getValue().setIcon(speedIcon);
				} else if (monster.getKey().getClass() == MonsterTypeRunner.class) {
					monster.getValue().setIcon(runnerIcon);
				}
			}

			aoeTowerButton.setIcon(aoeTowerIcon);
			slowTowerButton.setIcon(slowTowerIcon);
			laserTowerButton.setIcon(laserTowerIcon);
			sniperTowerButton.setIcon(sniperTowerIcon);
		}
	}

	private class okButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int error = 0;
			if (heightField.getText().equals("") == false
					&& widthField.getText().equals("") == false) {

				for (char myChar : heightField.getText().toCharArray()) {
					if (Character.isDigit(myChar) == false) {
						error = 1;
						break;
					}
				}
				for (char myChar : widthField.getText().toCharArray()) {
					if (Character.isDigit(myChar) == false) {
						error = 1;
						break;
					}
				}

				if (error == 1) {
					JOptionPane.showMessageDialog(generateFrame,
							translator.translateMessage("onlyNumbers"));
				} else {
					int height = Integer.parseInt(heightField.getText());
					int width = Integer.parseInt(widthField.getText());

					if (width >= 10 && height >= 10 && width < 21
							&& height < 21) {
						generateFrame.setVisible(false);
						mainFrame.setEnabled(true);
						mainFrame.toFront();
						mainFrame.dispose();

						controller.createNewLevel(width, height);
					} else {
						JOptionPane.showMessageDialog(generateFrame,
								translator.translateMessage("leastInfo"));
					}
				}
			} else {
				JOptionPane.showMessageDialog(generateFrame,
						translator.translateMessage("leastInfo"));
			}
		}
	}

	private class cancelButtonListener extends WindowAdapter implements
			ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			generateFrame.setVisible(false);
			mainFrame.setEnabled(true);
			mainFrame.toFront();
		}

		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			mainFrame.setEnabled(true);
			mainFrame.toFront();
		}
	}

	private class generateLevelButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			height.setText(translator.translateMessage("height"));
			width.setText(translator.translateMessage("width"));
			cancelButton.setText(translator.translateMessage("cancel"));

			generateFrame.setTitle(translator
					.translateMessage("generateLevelTitle"));

			mainFrame.setEnabled(false);
			if (paused == 0)
				pauseButton.doClick();
			heightField.setText("");
			widthField.setText("");
			generateFrame.setVisible(true);
			generateFrame.toFront();
		}
	}

	private class soundButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton button = (JRadioButton) e.getSource();
			if (button.getText().equals(translator.translateMessage("on")) == true) {
				soundStatus = true;
			} else {
				soundStatus = false;
			}
		}
	}

	private class drawLine extends JPanel {
		private static final long serialVersionUID = 3331988743152790934L;
		int x1;
		int y1;
		int x2;
		int y2;
		Color color;

		public drawLine(int arg1, int arg2, int arg3, int arg4, Color givenColor) {
			setSize(fieldWidth, fieldHeight);
			setOpaque(false);
			x1 = arg1;
			y1 = arg2;
			x2 = arg3;
			y2 = arg4;
			color = givenColor;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3));
			g2.setColor(color);
			g2.drawLine(x1, y1, x2, y2);
		}
	}

	private class drawRange extends JPanel {
		private static final long serialVersionUID = 3331988743152790934L;
		int x;
		int y;
		int size;

		public drawRange(int arg1, int arg2, int arg3) {
			setSize(fieldWidth, fieldHeight);
			setOpaque(false);
			x = arg1;
			y = arg2;
			size = arg3;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			if (skinList.getSelectedIndex() == 0) {
				g2.drawOval(x, y, size, size);
				g2.setPaint(new Color(0, 0, 1, new Float(0.1f)));
				g2.fillOval(x, y, size, size);
			} else if (skinList.getSelectedIndex() == 1) {
				g2.setColor(Color.BLACK);
				g2.drawOval(x, y, size, size);
			} else {
				g2.setColor(Color.RED);
				g2.drawOval(x, y, size, size);
			}
		}
	}

	private class drawBullet extends JPanel {
		private static final long serialVersionUID = 3331988743152790934L;
		int x;
		int y;

		public drawBullet(int arg1, int arg2) {
			setSize(fieldWidth, fieldHeight);
			setOpaque(false);
			x = arg1;
			y = arg2;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(1));
			g2.setColor(Color.BLACK);
			g2.fillOval(x, y, 8, 8);
		}
	}

}
