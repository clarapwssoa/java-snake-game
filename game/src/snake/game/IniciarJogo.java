package snake.game;
import javax.swing.*;

public class IniciarJogo extends JFrame {
	public static void main(String[] args) {
		new IniciarJogo();
	}
	
	IniciarJogo () {
		add(new TelaJogo());
		setTitle("Snake Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);		
	}
}
