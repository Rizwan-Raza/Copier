package copy;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
class Copy {
	public static JFrame FRAME;
	public static JTextField SOURCE, TARGET;
	public static Font font = new Font("Arial" , Font.PLAIN, 12);
	public static JLabel LABEL = new JLabel("Put the Source Directory and Target Directory in below Fields.");
	JButton copy, cancel;
	Copy() {
		FRAME = new JFrame("File Copier");
		SOURCE = new JTextField();
		TARGET = new JTextField();
		LABEL.setBounds(12, 0, 550, 30);
		JLabel source = new JLabel("Source");
		source.setBounds(12, 30, 50, 30);
		source.setFont(font);
		JLabel target = new JLabel("Target");
		target.setBounds(12, 70, 50, 30);
		target.setFont(font);
		SOURCE.setBounds(62, 30, 515, 30);
		TARGET.setBounds(62, 70, 515, 30);
		LABEL.setFont(font); 
		SOURCE.setFont(font);
		TARGET.setFont(font);
		copy = new JButton("Copy");
		cancel = new JButton("Cancel");
		copy.setBounds(152,110, 120, 30);
		cancel.setBounds(312, 110, 120, 30);
		copy.addActionListener(new CopyAction());
		cancel.addActionListener(new CancelAction());

		FRAME.add(LABEL);
		FRAME.add(SOURCE);
		FRAME.add(TARGET);
		FRAME.add(copy);
		FRAME.add(cancel);
		FRAME.add(source);
		FRAME.add(target);
		FRAME.setLayout(null);
		FRAME.setSize(600,190);
		FRAME.setBackground(Color.LIGHT_GRAY);
		FRAME.setVisible(true);
		// FRAME.setLayout(new BoxLayout(FRAME, BoxLayout.Y_AXIS));
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new Copy();
	}
}
class CopyAction implements ActionListener {
	boolean copying(File f, File target) {
		if (!target.exists()) {
			target.mkdir();
		}
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			br = new BufferedReader(new FileReader(f));
			File newFile = new File(target,f.getName());
			newFile.createNewFile();
			pw = new PrintWriter(newFile);
			char[] buffer = new char[(int)f.length()];
			br.read(buffer);
			pw.write(buffer);
			pw.flush();
			br.close();
			pw.close();
			return true;
		} catch (IOException exc) {
			System.out.println("Exception Found: "+exc);
			return false;
		}
	}
	public void actionPerformed(ActionEvent e) {
		File source = new File(Copy.SOURCE.getText().replace("\"", ""));
		File target = new File(Copy.TARGET.getText().replace("\"", ""));
		if (source.isDirectory()) {
			int filesCountDone = 0;
			int filesCountUnDone = 0;
			File[] sources = source.listFiles();
			for (File f : sources) {
				if (copying(f, target))
					filesCountDone++;
				else 
					filesCountUnDone++;
			}
			if (filesCountDone != 0) {
				Copy.LABEL.setForeground(new Color(0,128,0));
				Copy.LABEL.setText(filesCountDone+" File(s) Copied!");
				if (filesCountUnDone != 0) {
					Copy.LABEL.setForeground(Color.BLUE);
					Copy.LABEL.setText(Copy.LABEL.getText()+" and "+filesCountUnDone+" File(s) are not Copied!");
				}
			} else {
				Copy.LABEL.setForeground(Color.RED);
				Copy.LABEL.setText("Found an Exception in Copying the file, may be the file is not exist or corrupt.");
			}

		} else {
			if (copying(source, target)) {
				Copy.LABEL.setForeground(new Color(0,128,0));
				Copy.LABEL.setText("1 File Copied!");
			} else {
				Copy.LABEL.setForeground(Color.RED);
				Copy.LABEL.setText("Found an Exception in Copying the file, may be the file is not exist or corrupt.");
			}
		}
	}
}
class CancelAction implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}