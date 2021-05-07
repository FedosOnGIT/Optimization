#include "MainWindow.h"
#include "ui_mainwindow.h"
#include <QDebug>

#if QT_VERSION < QT_VERSION_CHECK(6, 0, 0)
#include <QDesktopWidget>
#endif
#include <QScreen>
#include <QMessageBox>
#include <QMetaEnum>

MainWindow::MainWindow(QWidget *parent) :
  QMainWindow(parent),
  ui(new Ui::MainWindow)
{
  ui->setupUi(this);
  ui->labelAlpha->setVisible(false);
  ui->lineEditAlpha->setVisible(false);
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
    if (!ok || eps <= 0) {
        ui->labelError->setText("Invalid eps: eps must be > 0");
        return;
    }
    double alpha;
    if (ui->lineEditAlpha->isVisible()) {
        alpha = ui->lineEditAlpha->text().toDouble(&ok);
        if (!ok || alpha <= 0) {
            ui->labelError->setText("Invalid alpha: alpha must be > 0");
            return;
        }
    }

    ui->labelError->setText("");

    std::string program = "java -jar " + PATH_JAR;
    addProgramArgs(program, funcType, method2Type, x, y, eps);
    if (method2Type == 1) {
        addProgramArgs(program, method1Type);
    }
    if (ui->lineEditAlpha->isVisible()) {
        addProgramArgs(program, alpha);
    }

    int returnCode = system(program.data());
    if (returnCode != 0) {
        ui->labelError->setText("Invalid input");
        return;
    }

    preparePlot(CURVES[funcType], CURVES_COLOR[funcType]);

    ui->stackedWidget->setCurrentIndex(1);
}

void MainWindow::stackedWidgetGoToStart() {
    ui->checkBoxLevelLines->setCheckState(Qt::Checked);
    ui->checkBoxX1Axis->setCheckState(Qt::Checked);
    ui->checkBoxDescentArrows->setCheckState(Qt::Checked);
    ui->checkBoxX1AxisName->setCheckState(Qt::Checked);
    ui->checkBoxX2Axis->setCheckState(Qt::Checked);
    ui->checkBoxX2AxisName->setCheckState(Qt::Checked);
    ui->comboBoxEllipses->setCurrentIndex(0);

    ui->widgetPlot->clearItems();
    ui->widgetPlot->clearGraphs();
    ui->widgetPlot->clearPlottables();
    ui->widgetPlot->replot();

    levelLinesDelta.clear();
    levelLinesIterations.clear();
    methodIterations.clear();

    ui->stackedWidget->setCurrentIndex(0);
}

void MainWindow::preparePlot(SecondOrderCurve curve, QPen pen) {
    std::ifstream in(LOGS_DEST);
    std::string delimiter = ",";
    std::vector<std::string> tokens;
    std::string str;
    QCustomPlot* plot = ui->widgetPlot;

    in >> str >> str >> str;
    tokens = split(str, delimiter);
    double x_prev = std::stod(tokens[0]), y_prev = std::stod(tokens[1]);
    double f_end = curve.evaluate(x_prev, y_prev);

    in >> str >> str >> str >> str;
    int iterations = std::stoi(str);

    setRanges(plot, curve.getMin()->first, curve.getMin()->second, 20);

    in.ignore();
    std::getline(in, str);
    std::getline(in, str);
    tokens = split(str, delimiter);
    x_prev = std::stod(tokens[1]), y_prev = std::stod(tokens[2]);
    double f_begin = curve.evaluate(x_prev, y_prev);

    levelLinesDelta = drawEllipsesDelta(plot, curve, pen, f_begin, f_end, ELLIPSES_CNT);
    drawMinPoint(plot, curve);

    for (int i = 1; i <= iterations; ++i) {
        std::getline(in, str);
        tokens = split(str, delimiter);
        double x = std::stod(tokens[1]), y = std::stod(tokens[2]);

        QCPItemLine* line = new QCPItemLine(plot);
        line->start->setCoords(x_prev, y_prev);
        line->end->setCoords(x, y);
        line->setHead(QCPLineEnding::esSpikeArrow);
        line->setVisible(false);
        methodIterations.push_back(line);

        x_prev = x, y_prev = y;
    }

    ui->scrollBarIterations->setRange(-1, methodIterations.size() - 1);
    prev_scroll_value = -1;
    ui->labelIterationNumber->setText(QString("Iteration number = ") + QString::fromStdString(std::to_string(prev_scroll_value + 1)));
    ui->scrollBarIterations->setValue(-1);
    valueCheckBoxDescentArrows = 2;

    plot->xAxis->setLabel("x");
    plot->yAxis->setLabel("y");

    plot->setInteractions(QCP::iRangeDrag | QCP::iRangeZoom | QCP::iSelectPlottables);
    plot->rescaleAxes();
    plot->replot();
}

void MainWindow::setRanges(QCustomPlot* plot, double x, double y, double shift) {
    double lowerPixPosX = plot->xAxis->coordToPixel(plot->xAxis->range().lower);
    double upperPixPosX = plot->xAxis->coordToPixel(plot->xAxis->range().upper);
    double lowerPixPosY = plot->yAxis->coordToPixel(plot->yAxis->range().lower);
    double upperPixPosY = plot->yAxis->coordToPixel(plot->yAxis->range().upper);
    double c = (upperPixPosY - lowerPixPosY) / (upperPixPosX - lowerPixPosX);

    plot->xAxis->setRange(x - shift, x + shift);
    plot->yAxis->setRange(y - shift * c, y + shift * c);
}

std::vector<QCPCurve*> MainWindow::drawEllipsesIterations(QCustomPlot* plot, SecondOrderCurve& curve, QPen pen, std::vector<QCPItemLine*> const&  methodIterations, size_t cnt) {
    std::vector<double> functionValues;

    if (methodIterations.empty()) {
        return {};
    }

    for (int i = 0; i <= cnt; ++i) {
        int index = (int) ((methodIterations.size() - 1) / ((double) cnt) * i);
        QPointF coords = methodIterations[index]->end->coords();
        functionValues.push_back(curve.evaluate(coords.x(), coords.y()));
    }
    QPointF coords = methodIterations[0]->start->coords();
    functionValues.push_back(curve.evaluate(coords.x(), coords.y()));

    return drawEllipses(plot, curve, pen, functionValues);
}

std::vector<QCPCurve*> MainWindow::drawEllipsesDelta(QCustomPlot* plot, SecondOrderCurve& curve, QPen pen, double f_begin, double f_end, size_t cnt) {
    std::vector<double> functionValues;

    for (size_t i = 0; i <= cnt; ++i) {
        double f = f_end + (f_begin - f_end) / cnt * i;
        functionValues.push_back(f);
    }

    return drawEllipses(plot, curve, pen, functionValues);
}

std::vector<QCPCurve*> MainWindow::drawEllipses(QCustomPlot* plot, SecondOrderCurve& curve, QPen pen, std::vector<double> const& functionValues) {
    std::vector<QCPCurve*> levelLines;

    const int pointCount = 500;
    const double a0 = curve.getA0();

    for (double f : functionValues) {
        curve.setA0(a0 - f);
        QCPCurve* parEllipse = new QCPCurve(plot->xAxis, plot->yAxis);

        QVector<QCPCurveData> dataEllipse(pointCount);
        for (int i = 0; i < pointCount; ++i) {
            double phi = i / (pointCount-1.) * 2 * M_PI;
            double x, y;
            curve.getParametrized(phi, x, y);
            dataEllipse[i] = QCPCurveData(i, x, y);
        }

        parEllipse->data()->set(dataEllipse, true);
        parEllipse->setPen(pen);
        levelLines.push_back(parEllipse);
    }

    curve.setA0(a0);

    return levelLines;
}

void MainWindow::drawMinPoint(QCustomPlot* plot, SecondOrderCurve const& curve) {
    plot->addGraph();
    QVector<double> x = {curve.getMin()->first, curve.getMin()->first};
    QVector<double> y = {curve.getMin()->second, curve.getMin()->second};
    QCPGraph* point = plot->graph(plot->graphCount() - 1);
    point->setData(x, y);
    QPen point_pen;
    point_pen.setWidth(5);
    point_pen.setColor(MIN_COLOR);
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

void MainWindow::pushButtonSettingsClicked() {
    ui->scrollArea->setVisible(settingsClickedMod2Cnt == 0);
    settingsClickedMod2Cnt = (settingsClickedMod2Cnt + 1) % 2;
}

void MainWindow::checkBoxLevelLines(int value) {
    setCurvesVisible(isDelta ? levelLinesDelta : levelLinesIterations, value != 0);
    ui->widgetPlot->replot();
}

void MainWindow::checkBoxDescentArrows(int value) {
    valueCheckBoxDescentArrows = value;
    for (int i = 0; i <= prev_scroll_value; ++i) {
        methodIterations[i]->setHead(valueCheckBoxDescentArrows == 0 ? QCPLineEnding::esNone : QCPLineEnding::esSpikeArrow);
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
    ui->labelAlpha->setVisible(type == 1);
    ui->lineEditAlpha->setVisible(type == 1);
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
            methodIterations[prev_scroll_value]->setVisible(true);
            methodIterations[prev_scroll_value]->setHead(valueCheckBoxDescentArrows == 0 ?
                QCPLineEnding::esNone : QCPLineEnding::esSpikeArrow);
        }
    } else {
        while (prev_scroll_value != value) {
            methodIterations[prev_scroll_value]->setVisible(false);
            prev_scroll_value--;
        }
    }
    ui->labelIterationNumber->setText(QString("Iteration number = ") + QString::fromStdString(std::to_string(prev_scroll_value + 1)));
    ui->widgetPlot->replot();
}

void MainWindow::setChecked(QCheckBox* checkBox) {
    checkBox->setChecked(true);
}

void MainWindow::comboBoxEllipsesActivated(int index) {
    if (index == 0) {
        setCurvesVisible(levelLinesIterations, false);
        setCurvesVisible(levelLinesDelta, true);
    } else {
        setCurvesVisible(levelLinesDelta, false);
        if (levelLinesIterations.empty()) {
            SecondOrderCurve curve = CURVES[funcType];
            levelLinesIterations = drawEllipsesIterations(ui->widgetPlot, curve, CURVES_COLOR[funcType], methodIterations, ELLIPSES_CNT);
        }
        setCurvesVisible(levelLinesIterations, true);
    }
    isDelta = index == 0;
    ui->widgetPlot->replot();
}

void MainWindow::setCurvesVisible(std::vector<QCPCurve*>& levelLines, bool isVisible) {
    if (levelLines.empty()) {
        return;
    }
    for (auto& levelLine : levelLines) {
        levelLine->setVisible(isVisible);
    }
}
