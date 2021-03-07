#include "MainWindow.h"
#include "gui/ui_MainWindow.h"
#include <iostream>
#include <string>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    connectStartWidget();
    connectMainWidget();
    connect(&iterationTimer, SIGNAL(timeout()), this, SLOT(realtimeShowIteration()));

    QCustomPlot* customPlot = ui->plotWidget;
    // График выбранной функции
    customPlot->addGraph();
    customPlot->graph(0)->setName("f(x)");
    // График левой границы
    customPlot->addGraph();
    customPlot->graph(1)->setName("left");
    // График правой границы
    customPlot->addGraph();
    customPlot->graph(2)->setName("right");
}

void MainWindow::functionChosen(int index) {
    functionType = (index == 0) ? -1 : index - 1;
}

void MainWindow::methodChosen(int index) {
    using namespace Opt;

    methodType = (index == 0) ? -1 : index - 1;
    switch (methodType) {
        case MethodType::Dichot: {
            ui->enterInputDataLabel->setVisible(true);
            ui->epsLabel->setVisible(true);
            ui->epsLineEdit->setVisible(true);
            ui->epsLineEdit->setText(ui->EPS_LINE_EDIT_INIT_VALUE);
            ui->deltaLabel->setVisible(true);
            ui->deltaLineEdit->setVisible(true);
            ui->deltaLineEdit->setText(ui->DELTA_LINE_EDIT_INIT_VALUE);
            break;
        }

        case MethodType::GoldenRatio:
        case MethodType::Fibonacci:
        case MethodType::Parabola:
        case MethodType::Brent: {
            ui->enterInputDataLabel->setVisible(true);
            ui->epsLabel->setVisible(true);
            ui->epsLineEdit->setVisible(true);
            ui->epsLineEdit->setText(ui->EPS_LINE_EDIT_INIT_VALUE);
            ui->deltaLabel->setVisible(false);
            ui->deltaLineEdit->setVisible(false);
            break;
        }

        default: {
            ui->enterInputDataLabel->setVisible(false);
            ui->epsLabel->setVisible(false);
            ui->epsLineEdit->setVisible(false);
            ui->deltaLabel->setVisible(false);
            ui->deltaLineEdit->setVisible(false);
        }
    }
}

// Зеленый цвет
void MainWindow::findMinPushButtonPressed() {
    ui->findMinPushButton->setStyleSheet(ui->FIND_MIN_PUSH_BUTTON_SET_STYLE_SHEET_PRESSED);
}

// Черный цвет
void MainWindow::findMinPushButtonReleased() {
    ui->findMinPushButton->setStyleSheet(ui->FIND_MIN_PUSH_BUTTON_SET_STYLE_SHEET_RELEASED);
}

void MainWindow::findMinPushButtonClicked() {
    using namespace Opt;
    // Функция выбрана?
    if (functionType == -1) {
        ui->errorStackedWidget->setCurrentIndex(ErrorType::Function);
        return;
    }
    // Метод выбран?
    if (methodType == -1) {
        ui->errorStackedWidget->setCurrentIndex(ErrorType::Method);
        return;
    }
    // Проверка эпсилон
    bool ok;
    eps = ui->epsLineEdit->text().toDouble(&ok);
    if (!ok || eps > MAX_EPS_VALUE || eps < MIN_EPS_VALUE) {
        ui->errorStackedWidget->setCurrentIndex(ErrorType::Eps);
        return;
    }
    // Проверка дельты
    delta = methodType == MethodType::Dichot
                         ? ui->deltaLineEdit->text().toDouble(&ok)
                         : eps / 2;
    if (!ok || delta > eps / 2 || delta < MIN_DELTA_VALUE) {
        ui->errorStackedWidget->setCurrentIndex(ErrorType::Delta);
        return;
    }
    ui->errorStackedWidget->setCurrentIndex(ErrorType::OK);

    functionData = FUNCTION_DATA_STORAGE[functionType];
    // Прорисовка основной функции
    buildMainFunction();

    // Выполнение всех итераций выбранного метода
    makeMethodIterations();

    // Переход к главному виджету, где уже есть прорисованный график
    ui->stackedWidget->setCurrentIndex(1);

    iterationTimer.start(SHOW_TIME_MS / iterations.size());
}

// Зеленый цвет
void MainWindow::backPushButtonPressed() {
    ui->backPushButton->setStyleSheet(ui->BACK_PUSH_BUTTON_SET_STYLE_SHEET_PRESSED);
}

// Черный цвет
void MainWindow::backPushButtonReleased() {
    ui->backPushButton->setStyleSheet(ui->BACK_PUSH_BUTTON_SET_STYLE_SHEET_RELEASED);
}

// Перейти к стартовому слайду, значения там те же
void MainWindow::backPushButtonClicked() {
    ui->listWidget->clear();
    iterations.clear();
    iterationTimer.stop();
    iterationsDrawn = false;
    prev_iteration = 0;
    clearGraphs();
    ui->stackedWidget->setCurrentIndex(0);
}

// Зеленый цвет
void MainWindow::resetPushButtonPressed() {
    ui->restartPushButton->setStyleSheet(ui->RESTART_PUSH_BUTTON_SET_STYLE_SHEET_PRESSED);
}

// Черный цвет
void MainWindow::resetPushButtonReleased() {
    ui->restartPushButton->setStyleSheet(ui->RESTART_PUSH_BUTTON_SET_STYLE_SHEET_RELEASED);
}

void MainWindow::resetPushButtonClicked() {
    std::lock_guard<std::mutex> lock(m);
    ui->listWidget->clear();
    iterationTimer.stop();
    iterationsDrawn = false;
    prev_iteration = 0;
    QCustomPlot* customPlot = ui->plotWidget;
    QVector<double> x(0), y(0);
    customPlot->graph(1)->setData(x, y);
    customPlot->graph(2)->setData(x, y);
    if (methodType == Opt::MethodType::Parabola) {
        customPlot->removeGraph(3);
    }
    customPlot->replot();
    iterationTimer.start(SHOW_TIME_MS / iterations.size());
}

void MainWindow::listWidgetItemClicked(QListWidgetItem* item) {
    if (iterationsDrawn && !iterationClicked) {
        prev_iteration = ui->listWidget->row(item);
        if (prev_iteration < iterations.size()) {
            iterationClicked = true;
            realtimeShowIteration();
            iterationClicked = false;
        }
    }
}

void MainWindow::connectStartWidget() {
    using namespace Opt;
    // Выбор функции
    connect(ui->chooseFunctionComboBox, SIGNAL(activated(int)),this, SLOT(functionChosen(int)));

    // Выбор метода
    connect(ui->comboBox, SIGNAL(activated(int)),this, SLOT(methodChosen(int)));

    // Кнопка - "Найти минимум"
    connect(ui->findMinPushButton, &QPushButton::pressed, this, &MainWindow::findMinPushButtonPressed);
    connect(ui->findMinPushButton, &QPushButton::released, this, &MainWindow::findMinPushButtonReleased);
    connect(ui->findMinPushButton, &QPushButton::clicked, this, &MainWindow::findMinPushButtonClicked);
}

void MainWindow::connectMainWidget() {
    // При нажатии на соответствующую итерацию будем прорисовать подграфик на данном этапе
    connect(ui->listWidget, &QListWidget::itemClicked, this, &MainWindow::listWidgetItemClicked);

    // Кнопка "Назад"
    connect(ui->backPushButton, &QPushButton::pressed, this, &MainWindow::backPushButtonPressed);
    connect(ui->backPushButton, &QPushButton::released, this, &MainWindow::backPushButtonReleased);
    connect(ui->backPushButton, &QPushButton::clicked, this, &MainWindow::backPushButtonClicked);

    // Кнопка "Перезапуск"
    connect(ui->restartPushButton, &QPushButton::pressed, this, &MainWindow::resetPushButtonPressed);
    connect(ui->restartPushButton, &QPushButton::released, this, &MainWindow::resetPushButtonReleased);
    connect(ui->restartPushButton, &QPushButton::clicked, this, &MainWindow::resetPushButtonClicked);
}

void MainWindow::buildMainFunction() {
    QCustomPlot* customPlot = ui->plotWidget;

    customPlot->legend->setVisible(true);
    customPlot->graph(0)->setName("f(x)");
    customPlot->graph(0)->setPen(QPen(Qt::darkCyan));
    QVector<double> x(functionData.points + 1), y(functionData.points + 1);
    for (uint32_t i = 0; i <= functionData.points; ++i) {
        x[i] = functionData.left + (functionData.right - functionData.left) * i / functionData.points;
        y[i] = functionData.f(x[i]);
    }
    // configure right and top axis to show ticks but no labels:
    // (see QCPAxisRect::setupFullAxesBox for a quicker method to do this)
    customPlot->xAxis2->setVisible(true);
    customPlot->xAxis2->setTickLabels(false);
    customPlot->yAxis2->setVisible(true);
    customPlot->yAxis2->setTickLabels(false);

    // make left and bottom axes always transfer their ranges to right and top axes:
    connect(customPlot->xAxis, SIGNAL(rangeChanged(QCPRange)), customPlot->xAxis2, SLOT(setRange(QCPRange)));
    connect(customPlot->yAxis, SIGNAL(rangeChanged(QCPRange)), customPlot->yAxis2, SLOT(setRange(QCPRange)));
    // pass data points to graphs:
    customPlot->graph(0)->setData(x, y);
    // let the ranges scale themselves so graph 0 fits perfectly in the visible area:
    customPlot->graph(0)->rescaleAxes();
    // Note: we could have also just called customPlot->rescaleAxes(); instead
    // Allow user to drag axis ranges with mouse, zoom with mouse wheel and select graphs by clicking:
    customPlot->setInteractions(QCP::iRangeDrag | QCP::iRangeZoom | QCP::iSelectPlottables);
    customPlot->replot();
}

void MainWindow::makeMethodIterations() {
    std::stringstream strStream;
    Opt::evaluate(static_cast<Opt::MethodType>(methodType), functionData, eps, delta, strStream);
    std::string str;

    // Добавляем шапку
    getline(strStream, str);
    iterations.push_back(Opt::HEAD_BY_METHOD.at(methodType));

    while (getline(strStream, str)) {
        iterations.push_back(str);
    }
}

void MainWindow::realtimeShowIteration() {
    std::lock_guard<std::mutex> lock(m);
    if (!iterationClicked && iterationsDrawn) {
        return;
    }

    QCustomPlot* customPlot = ui->plotWidget;

    if (prev_iteration != 0) {
        std::string val;
        QPen pen;
        pen.setWidth(1);
        pen.setColor(Qt::red);
        std::stringstream tmp_stream(iterations[prev_iteration]);
        tmp_stream >> val >> val;
        double l = std::stod(val);
        tmp_stream >> val;
        double r = std::stod(val);

        QVector<double> xl(2), yl(2);
        xl[0] = l, yl[0] = -100;
        xl[1] = l, yl[1] = 100;
        customPlot->graph(1)->setData(xl, yl);
        customPlot->graph(1)->setPen(pen);

        QVector<double> xr(2), yr(2);
        xr[0] = r, yr[0] = 100;
        xr[1] = r, yr[1] = -100;
        customPlot->graph(2)->setData(xr, yr);
        customPlot->graph(2)->setPen(pen);

        if (methodType == Opt::MethodType::Parabola) {
            if (customPlot->graphCount() == 3) {
                customPlot->addGraph();
            }
            double x1 = l, x3 = r;
            tmp_stream >> val;
            double f1 = std::stod(val);
            tmp_stream >> val;
            double f3 = std::stod(val);
            tmp_stream >> val;
            double x2 = std::stod(val);
            tmp_stream >> val;
            double f2 = std::stod(val);

            double a0 = f1;
            double a1 = (f2 - f1) / (x2 - x1);
            double a2 = ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1)) / (x3 - x2);

            std::function<double(double)> parabola = [&a0, &a1, &a2, &x1, &x2, &x3](double x) {
                return a0 + a1 * (x - x1) + a2 * (x - x1) * (x - x2);
            };

            QVector<double> xp(functionData.points / 100),  yp(functionData.points / 100);
            for (int i = 0; i < functionData.points / 100; ++i) {
                xp[i] = functionData.left + ((functionData.right - functionData.left) * i) / (functionData.points / 100);
                yp[i] = parabola(xp[i]);
            }
            pen.setStyle(Qt::DashLine);
            pen.setWidth(2);
            pen.setColor(Qt::darkRed);
            customPlot->graph(3)->setPen(pen);
            customPlot->graph(3)->setName("parabola");
            customPlot->graph(3)->setData(xp, yp);
        }
    } else {
        QVector<double> x(0), y(0);
        customPlot->graph(1)->setData(x, y);
        customPlot->graph(2)->setData(x, y);
        if (methodType == Opt::MethodType::Parabola && customPlot->graphCount() == 4) {
            customPlot->graph(3)->setData(x, y);
        }
    }

    customPlot->replot();
    if (!iterationClicked) {
        new QListWidgetItem(ui->listWidget);
        ui->listWidget->item(ui->listWidget->count() - 1)->setText(
            QCoreApplication::translate("MainWindow", iterations[prev_iteration].c_str(), nullptr));
        ++prev_iteration;
        if (prev_iteration == iterations.size()) {
            iterationsDrawn = true;
            iterationTimer.stop();
            new QListWidgetItem(ui->listWidget);
            ui->listWidget->item(ui->listWidget->count() - 1)->setText(
                    QCoreApplication::translate("MainWindow", "", nullptr));
        }
    }
}

void MainWindow::clearGraphs() {
    QCustomPlot* customPlot = ui->plotWidget;
    QVector<double> x(0), y(0);
    customPlot->graph(0)->setData(x, y);
    customPlot->graph(1)->setData(x, y);
    customPlot->graph(2)->setData(x, y);
    if (methodType == Opt::MethodType::Parabola) {
        customPlot->removeGraph(3);
    }
    customPlot->replot();
}

MainWindow::~MainWindow() {
    iterationTimer.stop();
    delete ui;
}
