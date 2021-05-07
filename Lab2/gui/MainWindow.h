#pragma once

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

class MainWindow : public QMainWindow {
  Q_OBJECT

public:
  explicit MainWindow(QWidget *parent = 0);

private slots:
  void stackedWidgetGoToPlot();

  void pushButtonSettingsClicked();
  void checkBoxLevelLines(int);
  void checkBoxDescentArrows(int);
  void checkBoxX1Axis(int);
  void checkBoxX1AxisName(int);
  void checkBoxX2Axis(int);
  void checkBoxX2AxisName(int);
  void comboBoxEllipsesActivated(int);

  void listWidgetChooseFuncItemClicked(QListWidgetItem*);
  void comboBoxChoose2DMethodActivated(int);
  void comboBoxChoose1DMethodActivated(int);
  void stackedWidgetGoToStart();

  void scrollBarIterationsValueChanged(int);

private:
  std::vector<QCPCurve*> drawEllipsesIterations(QCustomPlot* plot, SecondOrderCurve& curve, QPen pen, std::vector<QCPItemLine*> const&  methodIterations, size_t cnt);
  std::vector<QCPCurve*> drawEllipsesDelta(QCustomPlot* plot, SecondOrderCurve& curve, QPen pen, double f_begin, double f_end, size_t cnt);
  std::vector<QCPCurve*> drawEllipses(QCustomPlot* plot, SecondOrderCurve& curve, QPen pen, std::vector<double> const& functionValues);

  void drawMinPoint(QCustomPlot* plot, SecondOrderCurve const& curve);

  void setRanges(QCustomPlot* plot, double x, double y, double shift);

  std::vector<std::string> split(std::string const& str, std::string const& delimiter);

  void setChecked(QCheckBox* checkBox);

  void preparePlot(SecondOrderCurve curve, QPen pen);

  void setCurvesVisible(std::vector<QCPCurve*>& levelLines, bool isVisible);

  template <typename Arg, typename... Args>
  void addProgramArgs(std::string& program, Arg& arg, Args&... args) {
      program += " " + std::to_string(arg);
      addProgramArgs(program, args...);
  }

  template <typename Arg>
  void addProgramArgs(std::string& program, Arg& arg) {
      program += " " + std::to_string(arg);
  }

private:
  std::vector<SecondOrderCurve> const CURVES = {
      SecondOrderCurve(2, 0, 3, 0, 0, 0, 0, 0),
      SecondOrderCurve(1, 0, 50, 2, 10, 0, -1, -0.1),
      SecondOrderCurve(64, 126, 64, -10, 30, 13, 1265./127, -1275./127)
  };

  std::vector<QPen> const CURVES_COLOR = {
      QPen(Qt::blue),
      QPen(Qt::red),
      QPen(Qt::darkGreen)
  };

  QColor const MIN_COLOR = Qt::darkRed;

  std::string const LOGS_DEST = "./gui_logs.txt";

  size_t const ELLIPSES_CNT = 20;

  std::string const PATH_JAR =
        #ifdef _WIN32
          "..\\artifacts\\Lab2.jar";
        #else
          "/Users/aleksandrslastin/Desktop/Studying/Optimization/Lab2/artifacts/Lab2.jar";
        #endif


private:
  Ui::MainWindow* ui;

  int funcType = -1, method2Type = 0, method1Type = 0;
  double x, y, eps;

  int settingsClickedMod2Cnt = 0;

  std::vector<QCPItemLine*> methodIterations;
  std::vector<QCPCurve*> levelLinesDelta;
  std::vector<QCPCurve*> levelLinesIterations;

  int prev_scroll_value = -1;
  int valueCheckBoxDescentArrows = 2;
  bool isDelta = true;
};
