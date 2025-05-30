package MatrizEscalonada;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Interface extends JFrame {
    private JTextField linhasField;
    private JTextField colunasField;
    private JPanel matrizPanel;
    private JScrollPane matrizScroll;
    private JTextField[][] matrizCampos;
    private JTable resultadoTable;
    private double[][] matrizValores;

    public Interface() {
        setTitle("Matriz Escalonada - RREF");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(780, 650);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(700, 600));
        getContentPane().setBackground(new Color(245, 245, 250)); // fundo claro suave

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 250));
        setContentPane(mainPanel);

        // Top panel para configurações (inputs)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBackground(new Color(230, 230, 240));

        JLabel linhasLabel = new JLabel("Linhas:");
        linhasLabel.setForeground(new Color(60, 60, 80));
        linhasLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        linhasField = new JTextField(3);
        linhasField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        linhasField.setHorizontalAlignment(JTextField.CENTER);
        linhasField.setBackground(Color.WHITE);
        linhasField.setForeground(new Color(40, 40, 50));
        linhasField.setCaretColor(new Color(40, 40, 50));

        JLabel colunasLabel = new JLabel("Colunas:");
        colunasLabel.setForeground(new Color(60, 60, 80));
        colunasLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        colunasField = new JTextField(3);
        colunasField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        colunasField.setHorizontalAlignment(JTextField.CENTER);
        colunasField.setBackground(Color.WHITE);
        colunasField.setForeground(new Color(40, 40, 50));
        colunasField.setCaretColor(new Color(40, 40, 50));

        JButton criarMatrizButton = new JButton("Criar Matriz");
        estiloBotaoCriarCalcular(criarMatrizButton);

        topPanel.add(linhasLabel);
        topPanel.add(linhasField);
        topPanel.add(colunasLabel);
        topPanel.add(colunasField);
        topPanel.add(criarMatrizButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Painel da matriz com scroll
        matrizPanel = new JPanel();
        matrizPanel.setBackground(Color.WHITE);
        matrizPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 130, 180), 2, true),
                "Insira os valores da matriz:",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 17),
                new Color(30, 60, 115)));

        matrizScroll = new JScrollPane(matrizPanel);
        matrizScroll.setPreferredSize(new Dimension(750, 310));
        matrizScroll.setBorder(BorderFactory.createLineBorder(new Color(100, 130, 180), 2, true));
        matrizScroll.getVerticalScrollBar().setUnitIncrement(15);

        mainPanel.add(matrizScroll, BorderLayout.CENTER);

        // Painel resultado e botão calcular
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(new Color(245, 245, 250));

        JButton calcularButton = new JButton("Calcular Forma Escalonada Reduzida");
        estiloBotaoCriarCalcular(calcularButton);
        calcularButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
        bottomPanel.add(calcularButton, BorderLayout.NORTH);

        // Tabela resultado
        resultadoTable = new JTable();
        resultadoTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        resultadoTable.setRowHeight(26);  // menor que antes para ficar mais compacto
        resultadoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resultadoTable.setGridColor(new Color(110, 140, 190));
        resultadoTable.setBackground(Color.WHITE);
        resultadoTable.setForeground(new Color(30, 30, 60));
        resultadoTable.setSelectionBackground(new Color(100, 140, 210));
        resultadoTable.setSelectionForeground(Color.WHITE);
        resultadoTable.setFillsViewportHeight(true);

        resultadoTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(isSelected ? new Color(100, 140, 210) : Color.WHITE);
                c.setForeground(isSelected ? Color.WHITE : new Color(30, 30, 60));
                setHorizontalAlignment(CENTER);
                return c;
            }
        });

        JScrollPane resultadoScroll = new JScrollPane(resultadoTable);
        resultadoScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 130, 180), 2, true),
                "Resultado RREF",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 17),
                new Color(30, 60, 115)));
        resultadoScroll.setPreferredSize(new Dimension(750, 220));
        resultadoScroll.getVerticalScrollBar().setUnitIncrement(15);

        bottomPanel.add(resultadoScroll, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        criarMatrizButton.addActionListener(e -> criarCamposMatriz());
        calcularButton.addActionListener(e -> calcularRREF());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarTamanhoCampos();
            }
        });
    }

    private void estiloBotaoCriarCalcular(JButton btn) {
        btn.setBackground(new Color(60, 120, 190));
        btn.setForeground(new Color(25, 35, 55));  // fonte mais escura para melhor contraste
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBorder(BorderFactory.createLineBorder(new Color(30, 80, 150), 2, true));
        btn.setPreferredSize(new Dimension(140, 34));
    }

    private void criarCamposMatriz() {
        matrizPanel.removeAll();

        int linhas, colunas;
        try {
            linhas = Integer.parseInt(linhasField.getText());
            colunas = Integer.parseInt(colunasField.getText());
            if (linhas <= 0 || colunas <= 0) throw new NumberFormatException();
            if (linhas > 20 || colunas > 20) {
                JOptionPane.showMessageDialog(this, "Limite máximo recomendado: 20x20.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite números inteiros positivos válidos para linhas e colunas.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        matrizPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.BOTH;

        matrizCampos = new JTextField[linhas][colunas];
        int width = Math.min(60, 580 / colunas); // reduz tamanho dos campos proporcional ao número de colunas
        int height = 26;

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                gbc.gridx = j;
                gbc.gridy = i;

                JTextField campo = new JTextField();
                campo.setHorizontalAlignment(JTextField.CENTER);
                campo.setToolTipText("Elemento [" + i + "][" + j + "]");
                campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                campo.setBackground(Color.WHITE);
                campo.setForeground(new Color(30, 30, 50));
                campo.setCaretColor(new Color(30, 30, 50));
                campo.setPreferredSize(new Dimension(width, height));

                matrizCampos[i][j] = campo;

                matrizPanel.add(campo, gbc);
            }
        }

        matrizPanel.revalidate();
        matrizPanel.repaint();
    }

    private void ajustarTamanhoCampos() {
        if (matrizCampos == null) return;

        int linhas = matrizCampos.length;
        int colunas = matrizCampos[0].length;

        Dimension panelSize = matrizScroll.getViewport().getSize();

        if (panelSize.width <= 0 || panelSize.height <= 0) return;

        int larguraCampo = Math.min(60, (panelSize.width - (colunas + 1) * 8) / colunas);
        int alturaCampo = 26; // altura fixa menor para mais compactação

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matrizCampos[i][j].setPreferredSize(new Dimension(larguraCampo, alturaCampo));
            }
        }

        matrizPanel.revalidate();
        matrizPanel.repaint();
    }

    private void calcularRREF() {
        if (matrizCampos == null || matrizCampos.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, defina a matriz clicando em 'Criar Matriz' primeiro.",
                    "Matriz Não Definida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int linhas = matrizCampos.length;
        int colunas = matrizCampos[0].length;

        matrizValores = new double[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                String texto = matrizCampos[i][j].getText().trim();
                if (texto.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Preencha todos os campos da matriz.",
                            "Campo Vazio",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    matrizValores[i][j] = Double.parseDouble(texto);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Por favor, insira números válidos na posição [" + i + "][" + j + "].",
                            "Entrada Inválida",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        Algoritimo.transformarParaRREF(matrizValores);

        DefaultTableModel model = new DefaultTableModel(linhas, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                // Formatação para exibir inteiros sem vírgula
                String val;
                if (matrizValores[i][j] == (int) matrizValores[i][j]) {
                    val = String.valueOf((int) matrizValores[i][j]); // inteiro
                } else {
                    val = String.format("%.6f", matrizValores[i][j])
                            .replaceAll("0+$", "")
                            .replaceAll("\\.$", ""); // decimal
                }
                model.setValueAt(val, i, j);
            }
        }

        resultadoTable.setModel(model);

        for (int col = 0; col < resultadoTable.getColumnCount(); col++) {
            resultadoTable.getColumnModel().getColumn(col).setPreferredWidth(70);
        }
    }


}
