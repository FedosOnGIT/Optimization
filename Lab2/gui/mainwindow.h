/***************************************************************************
**                                                                        **
**  QCustomPlot, an easy to use, modern plotting widget for Qt            **
**  Copyright (C) 2011-2021 Emanuel Eichhammer                            **
**                                                                        **
**  This program is free software: you can redistribute it and/or modify  **
**  it under the terms of the GNU General Public License as published by  **
**  the Free Software Foundation, either version 3 of the License, or     **
**  (at your option) any later version.                                   **
**                                                                        **
**  This program is distributed in the hope that it will be useful,       **
**  but WITHOUT ANY WARRANTY; without even the implied warranty of        **
**  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         **
**  GNU General Public License for more details.                          **
**                                                                        **
**  You should have received a copy of the GNU General Public License     **
**  along with this program.  If not, see http://www.gnu.org/licenses/.   **
**                                                                        **
****************************************************************************
**           Author: Emanuel Eichhammer                                   **
**  Website/Contact: http://www.qcustomplot.com/                          **
**             Date: 29.03.21                                             **
**          Version: 2.1.0                                                **
****************************************************************************/

/************************************************************************************************************
**                                                                                                         **
**  This is the example code for QCustomPlot.                                                              **
**                                                                                                         **
**  It demonstrates basic and some advanced capabilities of the widget. The interesting code is inside     **
**  the "setup(...)Demo" functions of MainWindow.                                                          **
**                                                                                                         **
**  In order to see a demo in action, call the respective "setup(...)Demo" function inside the             **
**  MainWindow constructor. Alternatively you may call setupDemo(i) where i is the index of the demo       **
**  you want (for those, see MainWindow constructor comments). All other functions here are merely a       **
**  way to easily create screenshots of all demos for the website. I.e. a timer is set to successively     **
**  setup all the demos and make a screenshot of the window area and save it in the ./screenshots          **
**  directory.                                                                                             **
**                                                                                                         **
*************************************************************************************************************/

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

// QListWidgetItem *itemF, *itemG;
// itemF = __qlistwidgetitem;

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
  void drawMethod(std::unique_ptr<std::ifstream>&, std::unique_ptr<SecondOrderCurve>&, QPen pen);
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
