package mainapp;

import java.awt.BorderLayout;
import GUI.*;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import models.TableroJuego;

public class MainApp {

	public static void main(String[] args) throws InterruptedException {
		// TODO code application logic here

		int contador;
		MySnakeFrame frame;
		JPanel mainPanel;
		TableroJuego tablero;
		JPanel botonera;
		JLabel puntos;
		JLabel puntosNum;
		JButton start;
		JButton pause;
		ControlTeclado miControlador;

		// 1. Crear el frame.
		//menu=new MenuDificultad();
		frame=new MySnakeFrame();

		int selecciontamaÒo = JOptionPane.showOptionDialog(
				   frame,
				   "Seleccione tamaÒo", 
				   "Selector del tamaÒo",
				   JOptionPane.YES_NO_CANCEL_OPTION,
				   JOptionPane.QUESTION_MESSAGE,
				   null, 
				   new Object[] { "PequeÒo", "Mediano", "Grande"},
				   "Mediano");
		
		int ancho;
		int alto;
		switch(selecciontamaÒo)
		{
		case 0:
			ancho=300;
			alto=200;
			break;
		case 1:
			ancho=600;
			alto=400;
			break;
		case 2:
			ancho=900;
			alto=600;
			break;
		default:
			ancho=600;
			alto=400;
			break;
		}
		
		frame.setSize(ancho, alto+200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		// 3. Ahora creamos los componentes y los ponemos en la frame (ventana).

		// El panel de fondo. Rellena el frame, y sirve de contenedor del tablero y de
		// la botonera.
		mainPanel = new JPanel(new BorderLayout());

		// Ahora creamos el tablero. Recordamos: no deja de ser un panel un poquito
		// "especial"
		tablero = new TableroJuego();

		// Les damos las propiedades a nuestro tablero. Su color, tama√±o y borde
		tablero.setBorder(BorderFactory.createLineBorder(Color.black));
		tablero.setBackground(new java.awt.Color(255, 255, 255));
		tablero.setSize(ancho, alto);

		// Le damos un enlace al tablero para que sepa qui√©n es su frame (ventana) y as√≠
		// sepa
		// qui√©n contiene la serpiente y qui√©n controla el juego...
		tablero.setSnakeFrame(frame);

		// Ahora el turno de la botonera. Tendr√° los dos botones y las etiquetas de
		// texto
		botonera = new JPanel();
		botonera.setBorder(BorderFactory.createLineBorder(Color.black));
		botonera.setBackground(new java.awt.Color(150, 150, 150));

		// Ahora definimos las dos etiquetas para los puntos.
		puntos = new JLabel();
		puntos.setText("Puntos: ");
		puntos.setBackground(new java.awt.Color(190, 190, 190));

		puntosNum = new JLabel();
		puntosNum.setText("0");
		puntosNum.setBackground(new java.awt.Color(190, 190, 190));

		// turno de los botones de empezar y pausar/continuar
		start = new JButton();
		start.setSize(50, 20);
		start.setText("Start");
		start.addActionListener(new MyButtonListener(frame, tablero));

		pause = new JButton();
		pause.setSize(50, 20);
		pause.setText("Pause");
		pause.addActionListener(new MyButtonListener(frame, tablero));

		// Preparamos el control del teclado
		miControlador = new ControlTeclado();
		miControlador.setSnakeFrame(frame); // le damos al controlador de teclado un enlace el frame principal
		tablero.addKeyListener(miControlador); // le decimos al tablero que el teclado es cosa de nuestro controlador
		tablero.setFocusable(true); // permitimos que el tablero pueda coger el foco.

		// A√±adimos los componentes uno a uno, cada uno en su contenedor, y al final el
		// panel principal
		// se a√±ade al frame principal.
		botonera.add(start);
		botonera.add(pause);
		botonera.add(puntos);
		botonera.add(puntosNum);

		mainPanel.add(botonera, BorderLayout.PAGE_END);
		mainPanel.add(tablero, BorderLayout.CENTER);
		frame.add(mainPanel);

		frame.setVisible(true); // activamos la ventana principal para que sea "pintable"

		contador = 0; // nuestro control de los pasos del tiempo. Cada vez que contador cuenta un
						// paso, pasan 10ms

		int selecciondificultad = JOptionPane.showOptionDialog(
				   frame,
				   "Seleccione dificultad", 
				   "Selector de dificultad",
				   JOptionPane.YES_NO_CANCEL_OPTION,
				   JOptionPane.QUESTION_MESSAGE,
				   null, 
				   new Object[] { "F·cil", "Intermedio", "Dificil","Imposible" },
				   "Intermedio");
		int velocidad;
		switch (selecciondificultad) {
		case 0:
			velocidad=60;
			break;
		case 1:
			velocidad=20;
			break;
		case 2:
			velocidad=5;
			break;
		case 3:
			velocidad=1;
			break;
		default:
			velocidad=20;
			break;
		}
		while (true) { // por siempre jam√°s (hasta que nos cierren la ventana) estamos controlando el
						// juego.

			// actualizamos el estado del juego
			if (contador % velocidad == 0) { // cada 400ms nos movemos o crecemos...
				if (contador == 60) { // Cada 1200ms crecemos y reseteamos el contador
					contador = 0;
					frame.tocaCrecer();
					// hemos crecido... actualizamos puntos.
					puntosNum.setText(Integer.toString(frame.getSerpiente().getPuntos()));
				} else { // a los 200 y 400 ms nos movemos...
					contador++;
					frame.tocaMoverse();
				}
				frame.comprobarEstado(tablero.getHeight(), tablero.getWidth()); // comprobamos si hemos muerto o no.

			} else { // Cada vez que no hay que moverse o crecer, simplemente contamos...
				contador++;
			}

			// hemos terminado?? mostramos msg
			if (frame.mostrarFin()) {
				JOptionPane.showMessageDialog(frame,
						"Se acabo vaquero, has conseguido " + puntosNum.getText() + " puntos");
			}

			// Repintamos
			tablero.repaint();

			// Esperamos para dar tiempo al thread de repintado a pintar.
			Thread.sleep(10);

		}
	}

}
