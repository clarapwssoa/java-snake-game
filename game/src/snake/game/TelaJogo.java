package snake.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class TelaJogo extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int largura_tela = 1300;
	private static final int altura_tela = 750;
	private static final int tamanho_bloco = 40;
	private static final int unidades = largura_tela * altura_tela / (tamanho_bloco * tamanho_bloco);
	private static final int intervalo = 150;
	private static final String fonte = "Monospaced";
	private final int[] eixoX = new int [unidades];
	private final int[] eixoY = new int [unidades];
	private int corpoCobra = 2;
	private int blocosComidos;
	private int blocoX;
	private int blocoY;
	private char direcao = 'D'; // C - cima, B - baixo, E - esquerda, D - direita
	private boolean estaRodando = false;
	Timer timer;
	Random random;
	
	TelaJogo() {
		random = new Random();
		setPreferredSize(new Dimension(largura_tela, altura_tela));
	    setBackground(Color.black);
	    setFocusable(true);
	    addKeyListener(new LeitorDeTeclasAdapter());
	    iniciarJogo();
	}
	
	public void iniciarJogo() {
		criarBloco();
		estaRodando = true;
		timer = new Timer(intervalo, this);
		timer.start();
		
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		desenharTela(g);
	}
	
	public void desenharTela(Graphics g) {
		if (estaRodando) {
			g.setColor(Color.red);
			g.fillOval(blocoX, blocoY, tamanho_bloco, tamanho_bloco);
			
			for (int i = 0; i < corpoCobra; i++) {
				if (i == 0) {
					g.setColor(Color.green);
					g.fillRect(eixoX[0], eixoY[0], tamanho_bloco, tamanho_bloco);
				} else {
					g.setColor(new Color(45, 180, 0));
					g.fillRect(eixoX[i], eixoY[i], tamanho_bloco, tamanho_bloco);
				}
			}
			g.setColor(Color.white);
			g.setFont(new Font(fonte, Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Pontos: " + blocosComidos, (largura_tela - metrics.stringWidth("Pontuação: " + blocosComidos)) / 2, g.getFont().getSize());
		} else {
			fimDeJogo(g);
		}
	}
	

	private void criarBloco() {
		blocoX = random.nextInt(largura_tela / tamanho_bloco) * tamanho_bloco;
		blocoY = random.nextInt(altura_tela / tamanho_bloco) * tamanho_bloco;
	}
	

	public void fimDeJogo(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font(fonte, Font.BOLD, 40));
		FontMetrics fontePontuacao = getFontMetrics(g.getFont());
		g.drawString("Pontos: " + blocosComidos, (largura_tela - fontePontuacao.stringWidth("Pontuação: " + blocosComidos)) / 2, g.getFont().getSize());
		g.setColor(Color.white);
		g.setFont(new Font(fonte, Font.BOLD, 40));
		FontMetrics fonteFinal = getFontMetrics(g.getFont());
		g.drawString("Fim do Jogo", (largura_tela - fonteFinal.stringWidth("Fim de Jogo")) / 2, altura_tela / 2);
	}

	public void actionPerformed(ActionEvent e) {
		if(estaRodando) {
			andar();
			alcancarBloco();
			validarLimites();
		}
		repaint();	
	}

	private void andar(){
		for (int i = corpoCobra; i > 0; i--) {
			eixoX[i] = eixoX[i - 1];
			eixoY[i] = eixoY[i - 1];
		}
		
		switch(direcao) {
		case 'C':
			eixoY[0] = eixoY[0] - tamanho_bloco;
			break;
		case 'B':
			eixoY[0] = eixoY[0] + tamanho_bloco;
			break;
		case 'E':
			eixoX[0] = eixoX[0] - tamanho_bloco;
			break;
		case 'D':
			eixoX[0] = eixoX[0] + tamanho_bloco;
			break;
		default:
			break;
		}
	}
	
	private void alcancarBloco() {
		if(eixoX[0] == blocoX && eixoY[0] == blocoY) {
			corpoCobra++;
			blocosComidos++;
			criarBloco();
		}
		
	}
	
	private void validarLimites() {
		// A cabeça bateu no corpo?
		for (int i = corpoCobra; i > 0; i--) {
			if (eixoX[0] == eixoX[i] && eixoY[0] == eixoY[i]) {
				estaRodando = false;
				break;
			}
		}
		
		// A cabeça tocou uma das bordas?
		
		if (eixoX[0] < 0 || eixoX[0] > largura_tela) {
			estaRodando = false;
		}
		
		// A cabeça tocou em cima ou em baixo?
		
		if (eixoY[0] < 0 || eixoY[0] > largura_tela) {
			estaRodando = false;
		}
		
		if (!estaRodando) {
			timer.stop();
		}
		
	}
	
	public class LeitorDeTeclasAdapter extends KeyAdapter {
		 @Override
	        public void keyPressed(KeyEvent e) {
	            switch (e.getKeyCode()) {
	                case KeyEvent.VK_LEFT:
	                    if (direcao != 'D') {
	                        direcao = 'E';
	                    }
	                    break;
	                case KeyEvent.VK_RIGHT:
	                    if (direcao != 'E') {
	                        direcao = 'D';
	                    }
	                    break;
	                case KeyEvent.VK_UP:
	                    if (direcao != 'B') {
	                        direcao = 'C';
	                    }
	                    break;
	                case KeyEvent.VK_DOWN:
	                    if (direcao != 'C') {
	                        direcao = 'B';
	                    }
	                    break;
	                default:
	                    break;
	            }
		 }
	}
}
