package parserapp;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.event.*;

import parserapp.Constants;
import parserapp.FileParser;

public class Menu extends JFrame implements ActionListener {
    private Container c;
    private JLabel title;
    private JLabel delimitador;
    private JTextField tdelimitador;
    private JButton sub;
    private JButton reset;
    private JRadioButton linha;
    private JRadioButton coluna;
    public String opDelimitador;
	public int orientation; // linha / coluna
	
	public Menu() {
		setTitle("Janela Menu");
	    setBounds(150, 45, 450, 300);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setResizable(true);
	    c = getContentPane();
	    c.setBackground(Color.pink); // Outubro Rosa
	    c.setLayout(null);
	 
	    title = new JLabel("Menu");
	    title.setFont(new Font("Arial", Font.PLAIN, 18));
	    title.setSize(80, 50);
	    title.setLocation(200, 5);
	    c.add(title);
	 
	    delimitador = new JLabel("Delimitador");
	    delimitador.setFont(new Font("Arial", Font.PLAIN, 13));
	    delimitador.setSize(100, 20);
	    delimitador.setLocation(90, 50);
	    c.add(delimitador);
	        
	    tdelimitador = new JTextField();
	    tdelimitador.setFont(new Font("Arial", Font.PLAIN, 15));
	    tdelimitador.setSize(50, 20);
	    tdelimitador.setLocation(180, 50);
	    c.add(tdelimitador);
	        
	    linha = new JRadioButton("Linha");
	    linha.setFont(new Font("Arial", Font.PLAIN, 15));
	    linha.setSelected(false);
	    linha.setSize(75, 20);
	    linha.setLocation(90, 125);
		linha.addActionListener(this);
	    c.add(linha);
	 
	    coluna = new JRadioButton("Coluna");
	    coluna.setFont(new Font("Arial", Font.PLAIN, 15));
	    coluna.setSelected(false);
	    coluna.setSize(80, 20);
	    coluna.setLocation(90, 90);
		coluna.addActionListener(this);
	    c.add(coluna);

	    sub = new JButton("Submit");
	    sub.setFont(new Font("Arial", Font.PLAIN, 15));
	    sub.setSize(100, 20);
	    sub.setLocation(100, 220);
	    sub.addActionListener(this);
	    c.add(sub);
	 
	    reset = new JButton("Reset");
	    reset.setFont(new Font("Arial", Font.PLAIN, 15));
	    reset.setSize(100, 20);
	    reset.setLocation(240, 220);
	    reset.addActionListener(this);
	    c.add(reset);

		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sub) {
			opDelimitador = tdelimitador.getText();
		
			try {
				ValidacaoDelimitador(opDelimitador);
			} catch (DelimitadorInvalidoException e1) {
				e1.printStackTrace();
			}
				
			FileParser parser = new FileParser();
			Character delimiter = opDelimitador.charAt(0);
			try {
				parser.parse(Constants.FILE_PATH + "analysisMemory.out", Constants.FILE_PATH + "analysisMemoryTab.out", delimiter, orientation);
				parser.parse(Constants.FILE_PATH + "analysisTime.out", Constants.FILE_PATH + "analysisTimeTab.out", delimiter, orientation);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

        } else if (e.getSource() == reset) {
            String def = "";
            tdelimitador.setText(def);
            coluna.setSelected(false);
            linha.setSelected(false);
        }  else if (e.getSource() == linha) {
			if (linha.isSelected()) {
		        coluna.setSelected(false);
				orientation = Constants.ROW_ORIENTATION;
			}
		} else if (e.getSource() == coluna) {
			if (coluna.isSelected()) {
		        linha.setSelected(false);
				orientation = Constants.COLUMN_ORIENTATION;
			}
		}
	}

	public String ValidacaoDelimitador(String delimitador_informado) throws DelimitadorInvalidoException {
		if (delimitador_informado == "")
			throw new DelimitadorInvalidoException("Delimitador Inválido", null);
			
		if(delimitador_informado.charAt(0) == '\\'){
			delimitador_informado = delimitador_informado.substring(1, delimitador_informado.length());
		}
		
		if (delimitador_informado.length() > 1 ) {
			throw new DelimitadorInvalidoException("Delimitador Inválido", null);
		}
		return delimitador_informado;
	}

	public String retornoDelimitador() {
		return opDelimitador;
	}	

	public int retornoOrientacao() {
		return orientation;
	}
	
	public class DelimitadorInvalidoException extends Exception {
		public DelimitadorInvalidoException(String errorMessage, Throwable err) {
			super(errorMessage, err);
		}
	}
	
}



