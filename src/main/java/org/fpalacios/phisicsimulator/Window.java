package org.fpalacios.phisicsimulator;

import javax.swing.JFrame;

import java.awt.Container;
import java.awt.BorderLayout;

import org.fpalacios.engine.EnginePanel;

public class Window extends JFrame {

    private static final long serialVersionUID = 1l;

    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container root = getContentPane();
		root.setLayout( new BorderLayout() );
		root.add(new EnginePanel(this), BorderLayout.CENTER);

        pack();

        setVisible(true);
    }

}
