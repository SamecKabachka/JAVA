package Integral;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntegralCalculatorApp extends JFrame {
    private JTextField lowerBoundField;
    private JTextField upperBoundField;
    private JTextField stepField;
    private JTable table;
    private DefaultTableModel tableModel;

    public IntegralCalculatorApp() {
        // Определение окна
        setTitle("Вычислить интеграл 1/ln(x)");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель для полей ввода и кнопок
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(240, 240, 240)); // Цвет фона панели
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Кнопки
        JButton addButton = new JButton("Добавить");
        addButton.setPreferredSize(new Dimension(100, 25)); // Размер кнопки
        addButton.setBackground(new Color(100, 150, 200)); // Цвет фона кнопки
        addButton.setForeground(Color.WHITE); // Цвет текста кнопки
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 120, 10, 5); // Отступы для кнопок
        inputPanel.add(addButton, gbc);

        JButton removeButton = new JButton("Удалить");
        removeButton.setPreferredSize(new Dimension(100, 25)); // Размер кнопки
        removeButton.setBackground(new Color(200, 100, 100)); // Цвет фона кнопки
        removeButton.setForeground(Color.WHITE); // Цвет текста кнопки
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(removeButton, gbc);

        JButton calculateButton = new JButton("Вычислить");
        calculateButton.setPreferredSize(new Dimension(100, 25)); // Размер кнопки
        calculateButton.setBackground(new Color(100, 200, 100)); // Цвет фона кнопки
        calculateButton.setForeground(Color.WHITE); // Цвет текста кнопки
        gbc.gridx = 2;
        gbc.gridy = 0;
        inputPanel.add(calculateButton, gbc);

        // Нижний предел
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(2, 5, 2, 0); // Отступы для меток
        inputPanel.add(new JLabel("Нижний предел:"), gbc);
        gbc.insets = new Insets(2, 130, 2, 100); // Отступы для полей ввода
        lowerBoundField = new JTextField();
        lowerBoundField.setPreferredSize(new Dimension(150, 25)); // Размер поля ввода
        lowerBoundField.setBackground(new Color(255, 255, 200)); // Цвет фона поля ввода
        gbc.gridx = 0;
        inputPanel.add(lowerBoundField, gbc);

        // Верхний предел
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(2, 5, 2, 0); // Отступы для меток
        inputPanel.add(new JLabel("Верхний предел:"), gbc);
        gbc.insets = new Insets(2, 130, 2, 100); // Отступы для полей ввода
        upperBoundField = new JTextField();
        upperBoundField.setPreferredSize(new Dimension(150, 25)); // Размер поля ввода
        upperBoundField.setBackground(new Color(255, 255, 200)); // Цвет фона поля ввода
        gbc.gridx = 0;
        inputPanel.add(upperBoundField, gbc);

        // Шаг
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(2, 5, 2, 0); // Отступы для меток
        inputPanel.add(new JLabel("Шаг:"), gbc);
        gbc.insets = new Insets(2, 130, 2, 100); // Отступы для полей ввода
        stepField = new JTextField();
        stepField.setPreferredSize(new Dimension(150, 25)); // Размер поля ввода
        stepField.setBackground(new Color(255, 255, 200)); // Цвет фона поля ввода
        gbc.gridx = 0;
        inputPanel.add(stepField, gbc);

        // Таблица результатов
        String[] columnNames = {"Нижний предел", "Верхний предел", "Шаг", "Результат"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // колонки не редактируемы
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setBackground(new Color(240, 240, 240)); // Цвет фона таблицы
        table.setForeground(new Color(0, 0, 0)); // Цвет текста таблицы

        // Добавление компонентов в окно
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Обработчики событий для кнопок
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeRow();
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateIntegral();
            }
        });
    }

    // Метод для добавления строки в таблицу
    private void addRow() {
        double lowerBound = Double.parseDouble(lowerBoundField.getText());
        double upperBound = Double.parseDouble(upperBoundField.getText());
        double step = Double.parseDouble(stepField.getText());

        tableModel.addRow(new Object[]{lowerBound, upperBound, step, null});
    }

    // Метод для удаления выделенной строки из таблицы
    private void removeRow() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        }
    }

    private void calculateIntegral() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            double lowerBound = (double) tableModel.getValueAt(selectedRow, 0);
            double upperBound = (double) tableModel.getValueAt(selectedRow, 1);
            double eps = (double) tableModel.getValueAt(selectedRow, 2);

            double result = trapezoidMethod(lowerBound, upperBound, eps);
            tableModel.setValueAt(result, selectedRow, 3);
        }
    }

    // Метод для вычисления интеграла методом трапеций
    public double trapezoidMethod(double a, double b, double eps) {
        double sum = 0.0;
        double currentX = a;

        while (currentX < b) {
            double nextX = (currentX + eps < b) ? currentX + eps : b; // Проверка на достижение верхнего предела
            sum += (f(currentX) + f(nextX)) * (nextX - currentX) / 2; // Вычисление площади трапеции
            currentX = nextX; // Переход к следующему шагу
        }

        return sum;
    }

    // Определение функции 1/ln(x)
    public double f(double x) {
        return 1 / Math.log(x); // Определение функции 1/ln(x)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new IntegralCalculatorApp().setVisible(true);
            }
        });
    }
}