#pragma once

#include <QMainWindow>
#include <memory>
#include <cstdint>
#include <QThread>
#include <string>
#include <atomic>
#include <vector>
#include <utility>
#include <mutex>

#include <QTimer>
#include <QListWidget>

#include "Defs.h"

class MainWindow : public QMainWindow {
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow(); 

public slots:
    void functionChosen(int index);
    void methodChosen(int index);

    void findMinPushButtonPressed();
    void findMinPushButtonReleased();
    void findMinPushButtonClicked();

    void backPushButtonPressed();
    void backPushButtonReleased();
    void backPushButtonClicked();

    void resetPushButtonPressed();
    void resetPushButtonReleased();
    void resetPushButtonClicked();

    void listWidgetItemClicked(QListWidgetItem*);
    void realtimeShowIteration();
private:
    // Подключение сигналов/слотов для виджетов программы
    void connectStartWidget();
    void connectMainWidget();

    // Построение и очитска графиков
    void buildMainFunction();
    void makeMethodIterations();
    void clearGraphs();

    constexpr static double MAX_EPS_VALUE = 1;
    constexpr static double MIN_EPS_VALUE = 0.00000000000002;
    constexpr static double MIN_DELTA_VALUE = MIN_EPS_VALUE / 2;
    constexpr static double SHOW_TIME_MS = 10000;

private:
    Ui::MainWindow* ui;

    int functionType = -1;
    int methodType = -1;

    double eps;
    double delta;

    std::atomic<bool> iterationsDrawn = false;
    std::atomic<bool> iterationClicked = false;
    std::mutex m;

    Opt::FunctionData functionData;
    QTimer iterationTimer;
    std::vector<std::string> iterations;
    uint32_t prev_iteration = 0;
};
