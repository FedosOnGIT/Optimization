#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QTimer>

#include "SecondOrderCurve.h"
#include "qcustomplot.h"

#include <fstream>
#include <vector>
#include <iostream>
#include <string>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
  Q_OBJECT
  
public:
  explicit MainWindow(QWidget *parent = 0);
    ~MainWindow() {

    }
  
  void setupPlayground(QCustomPlot *customPlot);

  void setupEllipseData(QCustomPlot *customPlot);
  
private slots:
  void stackedWidgetGoToPlot();

  void pushButtonResetClicked();

  void pushButtonSettingsClicked();
  void checkBoxLevelLines(int);
  void checkBoxDescentArrows(int);
  void checkBoxX1Axis(int);
  void checkBoxX1AxisName(int);
  void checkBoxX2Axis(int);
  void checkBoxX2AxisName(int);

  void listWidgetChooseFuncItemClicked(QListWidgetItem*);
  void comboBoxChoose2DMethodActivated(int);
  void comboBoxChoose1DMethodActivated(int);
  void stackedWidgetGoToStart();

  void comboBoxTestChosen(int);

  void scrollBarIterationsValueChanged(int);

private:
  std::ifstream* getInput(QString const&);
  void drawMethod(std::ifstream&, SecondOrderCurve&, QPen pen);
  void drawEllipses(QCustomPlot* customPlot, QPen pen, SecondOrderCurve& curve, double f_begin, double f_end, size_t CNT);

  std::vector<std::string> split(std::string const& str, std::string const& delimiter);

  void setChecked(QCheckBox* checkBox) {
      checkBox->setChecked(true);
  }

private:
  Ui::MainWindow *ui;

  int funcType = -1, method2Type = 0, method1Type = 0;
  double x, y, eps;

  int settingsClickedCnt = 0;

  std::vector<QPointF> const MIN_POINTS = {QPointF(0, 0), QPointF(-1, -0.0025), QPointF(1265./127, -1275./127)};
  std::vector<QCPItemLine*> methodLines;
  std::vector<QCPCurve*> levelLines;

  int prev_scroll_value = -1;
  int valueCheckBoxDescentArrows = 2;
};

#endif // MAINWINDOW_H
