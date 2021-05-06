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
**  Website/Contact: http://www.QCustomPlot.com/                          **
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

#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QDebug>
#if QT_VERSION < QT_VERSION_CHECK(6, 0, 0)
#  include <QDesktopWidget>
#endif
#include <QScreen>
#include <QMessageBox>
#include <QMetaEnum>

MainWindow::MainWindow(QWidget *parent) :
  QMainWindow(parent),
  ui(new Ui::MainWindow)
{
  ui->setupUi(this);
  ui->labelChoose1DFunc->setVisible(false);
  ui->comboBoxChoose1DMethod->setVisible(false);
  ui->scrollArea->setVisible(false);
  setChecked(ui->checkBoxLevelLines);
  setChecked(ui->checkBoxDescentArrows);
  setChecked(ui->checkBoxX1Axis);
  setChecked(ui->checkBoxX1AxisName);
  setChecked(ui->checkBoxX2Axis);
  setChecked(ui->checkBoxX2AxisName);
}

void MainWindow::setupEllipseData(QCustomPlot *customPlot) {

    // QuadraticForm form(64, 126, 64, -10, 30, 13);

    double x_first = 17.459755513012528;
    double y_first = 18.075788301505654;
    // double c_first = form.evaluate(x_first, y_first);

    double x_min = -2.3999995381227683;
    double y_min =  -0.20000054552562138;
    // double c_min = form.evaluate(x_min, y_min);



    // RotatableEllipse* ellipse = new RotatableEllipse(customPlot, 60);
    QCPItemEllipse* ellipse = new QCPItemEllipse(customPlot);

    ellipse->topLeft->setCoords(0, 2);
    ellipse->bottomRight->setCoords(1, 1);

    QCPAxisRect* axisRect = new QCPAxisRect(customPlot);
    axisRect->addAxis(QCPAxis::atLeft, customPlot->xAxis);
    axisRect->addAxis(QCPAxis::atBottom, customPlot->yAxis);

    ellipse->setClipToAxisRect(true);
    ellipse->setClipToAxisRect(axisRect);

    customPlot->xAxis2->setVisible(false);
    customPlot->yAxis2->setVisible(false);

    customPlot->xAxis->setRange(-4, 1);
    customPlot->yAxis->setRange(-4, 1);

    customPlot->setInteractions(QCP::iRangeDrag | QCP::iRangeZoom | QCP::iSelectPlottables);
}

void MainWindow::stackedWidgetGoToPlot() {
    if (funcType == -1) {
        ui->labelError->setText(QString("Choose function"));
        return;
    }

    bool ok;
    x = ui->lineEditX->text().toDouble(&ok);
    if (!ok) {
        ui->labelError->setText("Invalid x");
        return;
    }
    y = ui->lineEditY->text().toDouble(&ok);
    if (!ok) {
        ui->labelError->setText("Invalid y");
        return;
    }
    eps = ui->lineEditEps->text().toDouble(&ok);
    if (!ok || eps < 0) {
        ui->labelError->setText("Invalid eps: eps must be >= 0");
        return;
    }

    ui->labelError->setText("");

    std::string program = "java -jar /Users/aleksandrslastin/Desktop/Studying/Optimization/Lab2/artifacts/Lab2.jar";
    program += " " + std::to_string(funcType);
    program += " " + std::to_string(method2Type);
    program += " " + std::to_string(x);
    program += " " + std::to_string(y);
    program += " " + std::to_string(eps);
    if (method2Type == 1) {
        program += " " + std::to_string(method1Type);
    }

    int returnCode = system(program.data());
    if (returnCode != 0) {
        ui->labelError->setText("Invalid input");
        return;
    }

    comboBoxTestChosen(funcType);

    ui->stackedWidget->setCurrentIndex(1);
}

void MainWindow::stackedWidgetGoToStart() {
    ui->checkBoxLevelLines->setCheckState(Qt::Checked);
    ui->checkBoxX1Axis->setCheckState(Qt::Checked);
    ui->checkBoxDescentArrows->setCheckState(Qt::Checked);
    ui->checkBoxX1AxisName->setCheckState(Qt::Checked);
    ui->checkBoxX2Axis->setCheckState(Qt::Checked);
    ui->checkBoxX2AxisName->setCheckState(Qt::Checked);
    ui->pushButtonSettings->setVisible(false);
    ui->stackedWidget->setCurrentIndex(0);
    ui->widgetPlot->clearItems();
    ui->widgetPlot->clearGraphs();
    ui->widgetPlot->clearPlottables();
    ui->widgetPlot->replot();
}

void MainWindow::comboBoxTestChosen(int index) {
    std::unique_ptr<SecondOrderCurve> pcurve;
    QPen pen;
    if (index == 0) {
        pcurve = std::make_unique<SecondOrderCurve>(2, 0, 3, 0, 0, 0);
        pen = QPen(Qt::blue);
    } else if (index == 1) {
        pcurve = std::make_unique<SecondOrderCurve>(1, 0, 2000, 2, 10, 0);
        pen = QPen(Qt::red);
    } else {
        pcurve = std::make_unique<SecondOrderCurve>(64, 126, 64, -10, 30, 13);
        pen = QPen(Qt::darkGreen);
    }

    std::unique_ptr<std::ifstream> in = std::unique_ptr<std::ifstream>(new std::ifstream("./gui_logs.txt"));
    drawMethod(in, pcurve, pen);
}

void MainWindow::drawMethod(std::unique_ptr<std::ifstream>& input, std::unique_ptr<SecondOrderCurve>& pcurve, QPen pen) {
    SecondOrderCurve& curve = *pcurve.get();
    std::ifstream& in = *input.get();
    std::string delimiter = ",";
    std::vector<std::string> tokens;
    std::string str;

    in >> str >> str >> str;
    tokens = split(str, delimiter);
    double x_prev = std::stod(tokens[0]), y_prev = std::stod(tokens[1]);
    double f_end = curve.evaluate(x_prev, y_prev);

    in >> str >> str >> str >> str;
    int iterations = std::stoi(str);

    QCustomPlot* customPlot = ui->widgetPlot;
    customPlot->clearPlottables();

    const int SHIFT = 20;

    double lower_pixel_pos_xAxis = customPlot->xAxis->coordToPixel(customPlot->xAxis->range().lower);
    double upper_pixel_pos_xAxis = customPlot->xAxis->coordToPixel(customPlot->xAxis->range().upper);
    double lower_pixel_pos_yAxis = customPlot->yAxis->coordToPixel(customPlot->yAxis->range().lower);
    double upper_pixel_pos_yAxis = customPlot->yAxis->coordToPixel(customPlot->yAxis->range().upper);
    double c = (upper_pixel_pos_yAxis - lower_pixel_pos_yAxis) / (upper_pixel_pos_xAxis - lower_pixel_pos_xAxis);

    customPlot->xAxis->setRange(x_prev - SHIFT, x_prev + SHIFT);
    customPlot->yAxis->setRange(y_prev - SHIFT * c, y_prev + SHIFT * c);

    in.ignore();
    std::getline(in, str);
    std::getline(in, str);
    tokens = split(str, delimiter);
    x_prev = std::stod(tokens[1]), y_prev = std::stod(tokens[2]);
    double f_begin = curve.evaluate(x_prev, y_prev);

    drawEllipses(customPlot, pen, curve, f_begin, f_end, 20);
    methodLines.clear();
    for (int i = 1; i <= iterations; ++i) {
        std::getline(in, str);
        tokens = split(str, delimiter);
        double x = std::stod(tokens[1]), y = std::stod(tokens[2]);
        QCPItemLine* line = new QCPItemLine(customPlot);
        line->start->setCoords(x_prev, y_prev);
        line->end->setCoords(x, y);
        line->setHead(QCPLineEnding::esSpikeArrow);
        line->setVisible(false);
        x_prev = x, y_prev = y;
        methodLines.push_back(line);
    }

    ui->scrollBarIterations->setRange(-1, methodLines.size() - 1);
    prev_scroll_value = -1;
    ui->labelIterationNumber->setText(QString("Iteration number = ") + QString::fromStdString(std::to_string(prev_scroll_value + 1)));
    ui->scrollBarIterations->setValue(-1);
    valueCheckBoxDescentArrows = 2;

    customPlot->xAxis->setLabel("x");
    customPlot->yAxis->setLabel("y");

    customPlot->setInteractions(QCP::iRangeDrag | QCP::iRangeZoom | QCP::iSelectPlottables);
    customPlot->rescaleAxes();
    customPlot->replot();
}

void MainWindow::drawEllipses(QCustomPlot* customPlot, QPen pen, SecondOrderCurve& curve, double f_begin, double f_end, size_t CNT) {
    const int pointCount = 500;
    const double a0 = curve.get_a0();

    levelLines.clear();
    for (size_t i = 0; i <= CNT; ++i) {
        double f = f_end + (f_begin - f_end) * i / CNT;
        curve.set_a0(a0 - f);
        QCPCurve *parEllipse = new QCPCurve(customPlot->xAxis, customPlot->yAxis);

        QVector<QCPCurveData> dataEllipse(pointCount);
        for (int i=0; i<pointCount; ++i) {
          double phi = i/(double)(pointCount-1)*2*M_PI;
          double x, y;
          curve.getParametrized(phi, x, y);
          dataEllipse[i] = QCPCurveData(i, x, y);
        }
        parEllipse->data()->set(dataEllipse, true);
        parEllipse->setPen(pen);
        levelLines.push_back(parEllipse);
    }
    curve.set_a0(a0);

    customPlot->addGraph();
    QVector<double> x = {MIN_POINTS[funcType].x(), MIN_POINTS[funcType].x()};
    QVector<double> y = {MIN_POINTS[funcType].y(), MIN_POINTS[funcType].y()};
    QCPGraph* point = customPlot->graph(customPlot->graphCount() - 1);
    point->setData(x, y);
    QPen point_pen;
    point_pen.setWidth(5);
    point_pen.setColor(Qt::darkRed);
    point->setPen(point_pen);
}

std::vector<std::string> MainWindow::split(std::string const& str, std::string const& delimiter) {
    std::string s = str;
    std::vector<std::string> res;
    size_t pos = 0;
    while ((pos = s.find(delimiter)) != std::string::npos) {
        res.push_back(s.substr(0, pos));
        s.erase(0, pos + delimiter.length());
    }
    res.push_back(s);
    return res;
}

void MainWindow::pushButtonResetClicked() {
    scrollBarIterationsValueChanged(-1);
    ui->scrollBarIterations->setValue(-1);
}

void MainWindow::pushButtonSettingsClicked() {
    ui->scrollArea->setVisible(settingsClickedCnt == 0);
    settingsClickedCnt = (settingsClickedCnt + 1) % 2;
}

void MainWindow::checkBoxLevelLines(int value) {
    for (QCPCurve* curve : levelLines) {
        curve->setVisible(value != 0);
    }
    ui->widgetPlot->replot();
}

void MainWindow::checkBoxDescentArrows(int value) {
    valueCheckBoxDescentArrows = value;
    for (int i = 0; i <= prev_scroll_value; ++i) {
        methodLines[i]->setHead(valueCheckBoxDescentArrows == 0 ? QCPLineEnding::esNone : QCPLineEnding::esSpikeArrow);
    }
    ui->widgetPlot->replot();
}

void MainWindow::checkBoxX1Axis(int value) {
    ui->widgetPlot->xAxis->setVisible(value != 0);
    ui->widgetPlot->replot();
}

void MainWindow::checkBoxX1AxisName(int value) {
    ui->widgetPlot->xAxis->setLabel(value == 0 ? "" : "x");
    ui->widgetPlot->replot();
}

void MainWindow::checkBoxX2Axis(int value) {
    ui->widgetPlot->yAxis->setVisible(value != 0);
    ui->widgetPlot->replot();
}

void MainWindow::checkBoxX2AxisName(int value) {
    ui->widgetPlot->yAxis->setLabel(value == 0 ? "" : "y");
    ui->widgetPlot->replot();
}

void MainWindow::listWidgetChooseFuncItemClicked(QListWidgetItem* item) {
    funcType = ui->listWidget->row(item);
}

void MainWindow::comboBoxChoose2DMethodActivated(int type) {
    method2Type = type;
    ui->labelChoose1DFunc->setVisible(type == 1);
    ui->comboBoxChoose1DMethod->setVisible(type == 1);
}

void MainWindow::comboBoxChoose1DMethodActivated(int index) {
    method1Type = index;
}

void MainWindow::scrollBarIterationsValueChanged(int value) {
    if (value == prev_scroll_value) {
        return;
    }
    if (value > prev_scroll_value) {
        while (prev_scroll_value != value) {
            ++prev_scroll_value;
            methodLines[prev_scroll_value]->setVisible(true);
            methodLines[prev_scroll_value]->setHead(valueCheckBoxDescentArrows == 0 ?
                QCPLineEnding::esNone : QCPLineEnding::esSpikeArrow);
        }
    } else {
        while (prev_scroll_value != value) {
            methodLines[prev_scroll_value]->setVisible(false);
            prev_scroll_value--;
        }
    }
    ui->labelIterationNumber->setText(QString("Iteration number = ") + QString::fromStdString(std::to_string(prev_scroll_value + 1)));
    ui->widgetPlot->replot();
}










