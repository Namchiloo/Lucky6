package lucky6;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TestLucky6 {
	public static void main(String[] args) {
		LuckyFrame frame = new LuckyFrame();
	}
}

class LuckyFrame extends JFrame{
	public LuckyFrame() {
		
		JFrame okvir = new JFrame();
		okvir.setSize(1280, 720);
		okvir.setDefaultCloseOperation(EXIT_ON_CLOSE);
		okvir.setTitle("Lucky 6");
        
		Toolkit tk= Toolkit.getDefaultToolkit();
        int width = tk.getScreenSize().width;
        int height = tk.getScreenSize().height;
        okvir.setLocation(width/2 - okvir.getSize().width/2, height/2 -  okvir.getSize().height/2);
        
		JPanel igraci = new JPanel();
		igraci.setPreferredSize(new Dimension(1280, 60));
		igraci.setLayout(new BorderLayout());
		Igra glavniOkvir = new Igra();
		okvir.add(glavniOkvir);	
		okvir.add(igraci, BorderLayout.NORTH);		
		
		Igrac igrac = new Igrac ();
		igraci.add(igrac, BorderLayout.WEST);
		
		JButton StartBtn = new JButton("Start");
		okvir.add(StartBtn, BorderLayout.SOUTH);
		okvir.setVisible(true);	
		Timer timer;
		final int DELAY = 1200;
		
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
		    	if(glavniOkvir.posljednjaKuglicaIzvucena()) {
		    		System.out.println("Kraj izvlacenja!");	   
		    		igrac.setKraj(true); 
		    		((Timer) event.getSource()).stop();
		    		StartBtn.setVisible(true);
		    		
		    	}
		    	else {
		    		int izvuceniBroj = glavniOkvir.izvuciKuglicu();
		    		for(int i=0; i<6; ++i) {	    					    					    			    			
		    			if(igrac.getListic()[i] == izvuceniBroj) {
	    					igrac.getPolja().get(i).setBackground(Color.GREEN);
	    					igrac.setPogodjenih(igrac.getPogodjenih()+1);
		    				if(igrac.getPogodjenih()==6 && igrac.getDobitak()==0) {
		    					if(glavniOkvir.getSretnaPolja()[0]==glavniOkvir.getBrojIzvucenihKuglica()-6 || glavniOkvir.getSretnaPolja()[1]==glavniOkvir.getBrojIzvucenihKuglica()-6) {
		    						igrac.setDobitak((glavniOkvir.getListaDobitaka()[glavniOkvir.getBrojIzvucenihKuglica()-6]));
		    						igrac.setSretnoPolje(2);
		    					}
		    					igrac.setDobitak(glavniOkvir.getListaDobitaka()[glavniOkvir.getBrojIzvucenihKuglica()-6]);
		    				}
		    	}
		   } 
		}
			}};
		timer = new Timer(DELAY, listener);
		
		StartBtn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		    	glavniOkvir.setBrojIzvucenihKuglica(0);
		    	glavniOkvir.getListaIzvucenihKuglica().clear();		    	    		
		    		igrac.obojiPolja();
		    		igrac.setPogodjenih(0);
		    		igrac.setDobitak(0);
		    		igrac.setKraj(false);
		    		igrac.setSretnoPolje(1);	    	
		    		StartBtn.setVisible(true);		    			    			    		
		            glavniOkvir.postaviSretnePozicije();
		    	    glavniOkvir.setPocetak(true);
		    	    timer.start();
		    	    System.out.println("Sretna polja su:");
		    	    for (int i = glavniOkvir.getListaDobitaka().length; i > 0 ; --i) {
		    	    	if (glavniOkvir.getSretnaPolja()[0] == i || glavniOkvir.getSretnaPolja()[1] == i) {
		    	    		System.out.println(glavniOkvir.getListaDobitaka()[i] + " KM");
		    	    	}
		    	    }
		    	   
		    	
		    }    
		});
		
	}
}
	
	

class Igra extends JPanel{
	
	private int brojIzvucenihKuglica=0;
	private ArrayList<Integer> listaIzvucenihKuglica = new ArrayList<Integer>();
	private int[] listaDobitaka = {10000, 7500, 5000, 2500, 1000, 500, 300, 200, 150, 100, 90, 80,
			70, 60, 50, 40, 30, 25, 20, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	private int[] sretnaPolja = new int[2];
	private boolean pocetak = false;
	public boolean isPocetak() {
		return pocetak;
	}

	public void setPocetak(boolean pocetak) {
		this.pocetak = pocetak;
	}
	

	ImageIcon image;
	JLabel label;
	public int getBrojIzvucenihKuglica() {
		return brojIzvucenihKuglica;
	}

	public void setBrojIzvucenihKuglica(int brojIzvucenihKuglica) {
		this.brojIzvucenihKuglica = brojIzvucenihKuglica;
	}

	public Igra() {
		repaint();
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(40,80,125));
	}
	
	public int[] getSretnaPolja() {
		return sretnaPolja;
	}

	public void setSretnaPolja(int[] sretnaPolja) {
		this.sretnaPolja = sretnaPolja;
	}

	public void postaviSretnePozicije() {
		int x = getRandomNumber(30);
		sretnaPolja[0] = x;
		x = getRandomNumber(30);
		while(x == sretnaPolja[0])
			x=getRandomNumber(30);
		sretnaPolja[1] = x;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		 g.setColor(Color.GRAY);
		    g.fillRoundRect(700, 120, 120, 260, 100, 100);
			g.drawRoundRect(700, 120, 120, 260, 100, 100);
			
			for(int i=0; i<5; ++i) {
				g.setColor(getColor(i));
				g.fillOval(745, 130+i*50, 40,40);
				g.drawOval(745, 130+i*50, 40,40);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 22)); 
				if(brojIzvucenihKuglica>i)
					g.drawString(listaIzvucenihKuglica.get(i)+"", 754, 154+i*51);
			}
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(470, 120, 260, 260);
			g.drawOval(470, 120, 260, 260);
			g.setColor(getColor(brojIzvucenihKuglica-1));
			g.fillOval(500, 150, 200, 200);
			g.drawOval(500, 150, 200, 200);
			if(brojIzvucenihKuglica>0) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 72)); 
				g.drawString(listaIzvucenihKuglica.get(brojIzvucenihKuglica-1)+"", 559, 274);
			}
				
			g.setFont(new Font("Arial", Font.BOLD, 22)); 
		for(int i=0; i<9; ++i) {
			g.setColor(getColor(i+5));
			g.fillOval(60, 120+i*50, 40,40);
			g.drawOval(60, 120+i*50, 40,40);
			g.setColor(Color.WHITE);
			g.drawString(listaDobitaka[i]+"", 115, 144+i*51);
			if(brojIzvucenihKuglica>i+5)
				g.drawString(listaIzvucenihKuglica.get(i+5)+"", 69, 144+i*51);
		}
		
		for(int i=0; i<4; ++i) {
			for(int j=0; j<3; ++j) {
				g.setColor(getColor(i*3+j+14));
				g.fillOval(260+i*200, 420+j*50, 40,40);
				g.drawOval(260+i*200, 420+j*50, 40,40);				
				g.setColor(Color.WHITE);
				g.drawString(listaDobitaka[i*3+j+9]+"", 315+i*200, 444+j*51);
				if(brojIzvucenihKuglica>(i*3+j+14))
					g.drawString(listaIzvucenihKuglica.get(i*3+j+14)+"", 269+i*200, 444+j*51);
			}
		}
		
		for(int i=0; i<9; ++i) {
			g.setColor(getColor(i+26));
			g.fillOval(1060, 120+i*50, 40,40);
			g.drawOval(1060, 120+i*50, 40,40);			
			g.setColor(Color.WHITE);
			g.drawString(listaDobitaka[i+21]+"", 1115, 144+i*51);
			if(brojIzvucenihKuglica>(i+26))
				g.drawString(listaIzvucenihKuglica.get(i+26)+"", 1069, 144+i*51);
		}	
	}
	
	private Color getColor(int i) {
		if(brojIzvucenihKuglica>i && i>-1)
		{		
		switch(listaIzvucenihKuglica.get(i)) {
		case 1:
		case 8:
		case 15:
		case 22:
		case 29:
		case 36:
		case 43:
			return Color.red;
		
		case 2:
		case 9:
		case 16:
		case 23:
		case 30:
		case 37:
		case 44:
			return Color.cyan;
			
		case 3:
		case 10:
		case 17:
		case 24:
		case 31:
		case 38:
		case 45:
			return Color.blue;
			
		case 4:
		case 11:
		case 18:
		case 25:
		case 32:
		case 39:
		case 46:
			return Color.orange;
			
		case 5:
		case 12:
		case 19:
		case 26:
		case 33:
		case 40:
		case 47:
			return Color.green;
			
		case 6:
		case 13:
		case 20:
		case 27:
		case 34:
		case 41:
		case 48:
			return Color.pink;
			
		case 7:
		case 14:
		case 21:
		case 28:
		case 35:
		case 42:
			return Color.magenta;
					
		default:
			break;
		
		}
	}
		return Color.black;
	
	}
	
	public ArrayList<Integer> getListaIzvucenihKuglica() {
		return listaIzvucenihKuglica;
	}

	public int[] getListaDobitaka() {
		return listaDobitaka;
	}

	public int izvuciKuglicu() {
		
		int br = getRandomNumber(48);
		for(int i = 0 ; i < listaIzvucenihKuglica.size(); ++i) {
			if(listaIzvucenihKuglica.get(i) == br) {
				i=0;
				br=getRandomNumber(48);
			}
		}
		listaIzvucenihKuglica.add(br);
		brojIzvucenihKuglica++;
		repaint();
		return br;
	}
	

public int getRandomNumber(int x){
    Random random = new Random();
    return random.nextInt(x)+1;
}

public boolean posljednjaKuglicaIzvucena() {
	return brojIzvucenihKuglica == 35 ? true : false;

	
}
}

class Igrac extends JPanel{

	private int[] listic = {0,0,0,0,0,0};
	private ArrayList<JTextField> polja = new ArrayList<JTextField>();
	private int uplata = 1;

	public int[] getListic() {
		return listic;
	}


	private int pogodjenih = 0;
	private int dobitak = 0;
	JTextField uplataPolje;
	public int getSretnoPolje() {
		return sretnoPolje;
	}
	

	public void setSretnoPolje(int sretnoPolje) {
		this.sretnoPolje = sretnoPolje;
	}


	private boolean kraj = false;
	private int sretnoPolje = 1;
	
	public boolean isKraj() {
		return kraj;
		
	}

	public void setKraj(boolean kraj) {
		this.kraj = kraj;
		repaint();
	}

	public int getDobitak() {
		return dobitak;
	}

	public void setDobitak(int dobitak) {
		this.dobitak = dobitak;
	}

	public ArrayList<JTextField> getPolja() {
		return polja;
	}

	public int getPogodjenih() {
		return pogodjenih;
	}

	public void setPogodjenih(int pogodjenih) {
		this.pogodjenih = pogodjenih;
	}

	public Igrac() {
		repaint();
		JButton randomButton = new JButton("Random");
		setLayout(new FlowLayout());
		this.add(randomButton);
		
		randomButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				for(int i=0; i<6; ++i) {
					int broj = getRandomNumber();
					for(int k = 0; k<i; ++k) {
						if(listic[k] == broj) {
							k=0;
							broj=getRandomNumber();
						}
					}
					polja.get(i).setText(""+broj); 
					listic[i] = broj;
					obojiPolja();
				}
	        }
		});
		
		for(int i=0;i<6;++i) {
			JTextField novoPolje = new JTextField("", 3);
			novoPolje.getDocument().addDocumentListener(listener);
			this.add(novoPolje);
			polja.add(novoPolje);
			
		}
		uplataPolje = new JTextField("1",5);
		uplataPolje.getDocument().addDocumentListener(listenerUplata);
		this.add(uplataPolje, BorderLayout.SOUTH);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Arial", Font.BOLD, 13)); 
		g.drawString("Uplata", this.getWidth()/2+150,50);
		if(isKraj()) {
			g.drawString("Igrac " , this.getWidth()/2-60,50);
			g.drawString("Dobitak: "+dobitak*sretnoPolje*uplata, this.getWidth()/2,50);
		}
		else {
			g.drawString("Igrac " , this.getWidth()/2-20,50);
		}
	}
	
	public void obojiPolja() {
		for(int i=0;i<6;++i) {
			polja.get(i).setBackground(Color.WHITE);
		}
	}
	
	DocumentListener listenerUplata = new PaymentFieldListener();
	DocumentListener listener = new TicketFieldListener();
	
	class PaymentFieldListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent arg0) {

		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			dodajUplatu();
			
		}
		private void dodajUplatu() {
			uplata = Integer.parseInt(uplataPolje.getText().trim());
			
		}
		@Override
		public void removeUpdate(DocumentEvent arg0) {

		}
		
	}
	
	class TicketFieldListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {

			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			dodajBroj();	
		}


		private void dodajBroj() {

			try {
			for(int i=0; i<polja.size(); ++i) {
				
				if(Integer.parseInt(polja.get(i).getText().trim())<1 || Integer.parseInt(polja.get(i).getText().trim())>48) {				
					JOptionPane.showMessageDialog(null,"Molimo unesite broj izmedju 1 i 48");
				}
				
				if(!polja.get(i).getText().trim().equals("")) {					
					listic[i]=Integer.parseInt(polja.get(i).getText().trim());
					repaint();
				}
			}
			}
			catch(Exception e) {
	
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			izbaciBroj();		
		}

		private void izbaciBroj() {
			for(int i = 0; i<polja.size();++i) {
				if(polja.get(i).getText().equals("")) {
					listic[i]=0;
					repaint();
				}
			}		
		}
		
	}
	public int getRandomNumber(){
	    Random random = new Random();
	    return random.nextInt(48)+1;
	}
	
}
