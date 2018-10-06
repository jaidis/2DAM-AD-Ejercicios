/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio_05;

/**
 *
 * @author jaidis
 */
import javax.swing.JDialog;
import javax.swing.JLabel;

public class Test extends JDialog {
	
	public static void main(String[] args) {
		try {
			Test dialog = new Test();
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Test() {
		// Constructor
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Test");
		setBounds(100, 100, 359, 174);
		getContentPane().setLayout(null);
		
		// Label
		JLabel label = new JLabel("Probando con codigo plano");
		label.setBounds(86, 37, 175, 29);
		getContentPane().add(label);

	}
}