package models;

import java.awt.Color;
import java.awt.Graphics2D;

public class Manzana {
	private int posicionX;
	private int posicionY;
	private int lado;
	private int color;
	
	public Manzana() {
		super();
		this.posicionX = 0;
		this.posicionY = 0;
		this.lado = 20;
		this.color = 0;
	}

	public int getPosicionX() {
		return posicionX;
	}

	public int getPosicionY() {
		return posicionY;
	}

	public int getLado() {
		return lado;
	}

	public int getColor() {
		return color;
	}
	
	public void setPosicionX(int posicionX) {
		this.posicionX = posicionX;
	}

	public void setPosicionY(int posicionY) {
		this.posicionY = posicionY;
	}

	public void setLado(int lado) {
		this.lado = lado;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void pintarse(Graphics2D g) {
        
    	g.setColor(new Color(color));
    	//g.drawRect(posX, posY, lado, lado);
    	g.fillRect(posicionX, posicionY, lado, lado);
		//g.fillOval(posX, posY, lado, lado);
		
    }
	
	public boolean estaEncimaDe(Cuadrado otroC) {
    	//en nuestro caso, sólo comprobamos la esquina superior izq 
    	//almacenada en las posiciones X e Y. No hay otra posibilidad.
        return (otroC.getX() == posicionX && otroC.getY() == posicionY);
    }
}
