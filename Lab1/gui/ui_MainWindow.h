/********************************************************************************
** Form generated from reading UI file 'MainWindow.ui'
**
** Created by: Qt User Interface Compiler version 5.15.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QComboBox>
#include <QtWidgets/QGridLayout>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QStackedWidget>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

#include "gui/qcustomplot.h"

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QWidget *centralwidget;
    QGridLayout *gridLayout_2;
    QStackedWidget *stackedWidget;
    QWidget *startWidget;
    QGridLayout *gridLayout_12;
    QWidget *chooseFuncWidget;
    QGridLayout *gridLayout_8;
    QLabel *chooseFunctionLabel;
    QHBoxLayout *horizontalLayout;
    QVBoxLayout *verticalLayout;
    QSpacerItem *verticalSpacer_3;
    QComboBox *chooseFunctionComboBox;
    QSpacerItem *verticalSpacer_2;
    QLabel *functionsLabel;
    QSpacerItem *horizontalSpacer;
    QSpacerItem *verticalSpacer_4;
    QWidget *chooseMethodWidget;
    QGridLayout *gridLayout;
    QGridLayout *nameGridLayout;
    QSpacerItem *horizontalSpacer_5;
    QLabel *enterInputDataLabel;
    QLabel *chooseMethodLabel;
    QSpacerItem *horizontalSpacer_7;
    QGridLayout *deltaGridLayout;
    QLabel *deltaLabel;
    QSpacerItem *horizontalSpacer_8;
    QSpacerItem *horizontalSpacer_10;
    QLineEdit *deltaLineEdit;
    QGridLayout *epsGridLayout;
    QLineEdit *epsLineEdit;
    QComboBox *comboBox;
    QSpacerItem *horizontalSpacer_6;
    QLabel *epsLabel;
    QSpacerItem *horizontalSpacer_9;
    QSpacerItem *verticalSpacer;
    QStackedWidget *errorStackedWidget;
    QWidget *errorOk;
    QGridLayout *gridLayout_11;
    QSpacerItem *errorOkHorizontalSpacer;
    QWidget *errorFunc;
    QGridLayout *gridLayout_3;
    QLabel *errorFuncInfo;
    QSpacerItem *errorFunchorizontalSpacer;
    QWidget *methodError;
    QGridLayout *gridLayout_9;
    QLabel *errorMethodInfo;
    QSpacerItem *errorMethodhorizontalSpacer;
    QWidget *epsError;
    QGridLayout *gridLayout_7;
    QLabel *errorEpsInfo;
    QSpacerItem *errorEpsHorizontalSpacer;
    QWidget *deltaError;
    QGridLayout *gridLayout_10;
    QLabel *errorDeltaInfo;
    QSpacerItem *errorDeltaHorizontalSpacer;
    QPushButton *findMinPushButton;
    QWidget *mainWidget;
    QGridLayout *gridLayout_6;
    QGridLayout *gridLayout_4;
    QPushButton *backPushButton;
    QPushButton *restartPushButton;
    QSpacerItem *horizontalSpacer_4;
    QCustomPlot *plotWidget;
    QWidget *infoOutputWidget;
    QGridLayout *gridLayout_5;
    QListWidget *listWidget;

    const char* FIND_MIN_PUSH_BUTTON_SET_STYLE_SHEET_RELEASED =
                "font: 75 italic 16pt \"CMU Serif\";\n"
                "color: rgb(35, 35, 255);\n"
                "border-style: outset;\n"
                "border-width: 2px;\n"
                "border-radius: 10px;\n"
                "border-color: black;\n"
                "font: bold 14px;\n"
                "padding: 6px;";

        const char* FIND_MIN_PUSH_BUTTON_SET_STYLE_SHEET_PRESSED =
                "font: 75 italic 16pt \"CMU Serif\";\n"
                "color: rgb(35, 35, 255);\n"
                "border-style: outset;\n"
                "border-width: 2px;\n"
                "border-radius: 10px;\n"
                "border-color: green;\n"
                "font: bold 14px;\n"
                "padding: 6px;";

        const char* BACK_PUSH_BUTTON_SET_STYLE_SHEET_RELEASED =
                "font: 75 italic 16pt \"CMU Serif\";\n"
                "color: rgb(0, 0, 0);\n"
                "border-style: outset;\n"
                "border-width: 2px;\n"
                "border-radius: 10px;\n"
                "border-color: black;\n"
                "font: bold 14px;\n"
                "padding: 6px;";

        const char* BACK_PUSH_BUTTON_SET_STYLE_SHEET_PRESSED =
                "font: 75 italic 16pt \"CMU Serif\";\n"
                "color: rgb(0, 0, 0);\n"
                "border-style: outset;\n"
                "border-width: 2px;\n"
                "border-radius: 10px;\n"
                "border-color: green;\n"
                "font: bold 14px;\n"
                "padding: 6px;";

        const char* RESTART_PUSH_BUTTON_SET_STYLE_SHEET_RELEASED =
                "font: 75 italic 16pt \"CMU Serif\";\n"
                "color: rgb(0, 0, 0);\n"
                "border-style: outset;\n"
                "border-width: 2px;\n"
                "border-radius: 10px;\n"
                "border-color: black;\n"
                "font: bold 14px;\n"
                "padding: 6px;";

        const char* RESTART_PUSH_BUTTON_SET_STYLE_SHEET_PRESSED =
                "font: 75 italic 16pt \"CMU Serif\";\n"
                "color: rgb(0, 0, 0);\n"
                "border-style: outset;\n"
                "border-width: 2px;\n"
                "border-radius: 10px;\n"
                "border-color: green;\n"
                "font: bold 14px;\n"
                "padding: 6px;";

        const char* EPS_LINE_EDIT_INIT_VALUE = "0.000000001";
        const char* DELTA_LINE_EDIT_INIT_VALUE = "0.0000000005";

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName(QString::fromUtf8("MainWindow"));
        MainWindow->resize(772, 610);
        centralwidget = new QWidget(MainWindow);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        gridLayout_2 = new QGridLayout(centralwidget);
        gridLayout_2->setObjectName(QString::fromUtf8("gridLayout_2"));
        stackedWidget = new QStackedWidget(centralwidget);
        stackedWidget->setObjectName(QString::fromUtf8("stackedWidget"));
        QSizePolicy sizePolicy(QSizePolicy::Expanding, QSizePolicy::Preferred);
        sizePolicy.setHorizontalStretch(0);
        sizePolicy.setVerticalStretch(5);
        sizePolicy.setHeightForWidth(stackedWidget->sizePolicy().hasHeightForWidth());
        stackedWidget->setSizePolicy(sizePolicy);
        stackedWidget->setStyleSheet(QString::fromUtf8("background-color: rgb(255, 255, 255);\n"
            "font: 57 16pt \"CMU Serif\";\n""color: rgb(0, 0, 0);"));
        startWidget = new QWidget();
        startWidget->setObjectName(QString::fromUtf8("startWidget"));
        gridLayout_12 = new QGridLayout(startWidget);
        gridLayout_12->setObjectName(QString::fromUtf8("gridLayout_12"));
        chooseFuncWidget = new QWidget(startWidget);
        chooseFuncWidget->setObjectName(QString::fromUtf8("chooseFuncWidget"));
        chooseFuncWidget->setMinimumSize(QSize(531, 200));
        chooseFuncWidget->setMaximumSize(QSize(16777215, 16777215));
        gridLayout_8 = new QGridLayout(chooseFuncWidget);
        gridLayout_8->setObjectName(QString::fromUtf8("gridLayout_8"));
        chooseFunctionLabel = new QLabel(chooseFuncWidget);
        chooseFunctionLabel->setObjectName(QString::fromUtf8("chooseFunctionLabel"));
        chooseFunctionLabel->setMinimumSize(QSize(181, 16));
        chooseFunctionLabel->setMaximumSize(QSize(16777215, 16));

        gridLayout_8->addWidget(chooseFunctionLabel, 0, 0, 1, 1);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName(QString::fromUtf8("horizontalLayout"));
        verticalLayout = new QVBoxLayout();
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        verticalSpacer_3 = new QSpacerItem(20, 10, QSizePolicy::Minimum, QSizePolicy::Fixed);

        verticalLayout->addItem(verticalSpacer_3);

        chooseFunctionComboBox = new QComboBox(chooseFuncWidget);
        chooseFunctionComboBox->addItem(QString());
        chooseFunctionComboBox->addItem(QString());
        chooseFunctionComboBox->addItem(QString());
        chooseFunctionComboBox->addItem(QString());
        chooseFunctionComboBox->setObjectName(QString::fromUtf8("chooseFunctionComboBox"));
        chooseFunctionComboBox->setMinimumSize(QSize(180, 31));
        chooseFunctionComboBox->setMaximumSize(QSize(180, 31));
        chooseFunctionComboBox->setStyleSheet(QString::fromUtf8(""));

        verticalLayout->addWidget(chooseFunctionComboBox);

        verticalSpacer_2 = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout->addItem(verticalSpacer_2);


        horizontalLayout->addLayout(verticalLayout);

        functionsLabel = new QLabel(chooseFuncWidget);
        functionsLabel->setObjectName(QString::fromUtf8("functionsLabel"));
        functionsLabel->setMinimumSize(QSize(481, 151));
        functionsLabel->setMaximumSize(QSize(481, 151));
        functionsLabel->setStyleSheet(QString::fromUtf8("image: url(:/images/functions.png);"));

        horizontalLayout->addWidget(functionsLabel);

        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer);


        gridLayout_8->addLayout(horizontalLayout, 1, 0, 1, 1);

        verticalSpacer_4 = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        gridLayout_8->addItem(verticalSpacer_4, 2, 0, 1, 1);


        gridLayout_12->addWidget(chooseFuncWidget, 0, 0, 1, 2);

        chooseMethodWidget = new QWidget(startWidget);
        chooseMethodWidget->setObjectName(QString::fromUtf8("chooseMethodWidget"));
        gridLayout = new QGridLayout(chooseMethodWidget);
        gridLayout->setObjectName(QString::fromUtf8("gridLayout"));
        nameGridLayout = new QGridLayout();
        nameGridLayout->setObjectName(QString::fromUtf8("nameGridLayout"));
        horizontalSpacer_5 = new QSpacerItem(150, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        nameGridLayout->addItem(horizontalSpacer_5, 0, 1, 1, 1);

        enterInputDataLabel = new QLabel(chooseMethodWidget);
        enterInputDataLabel->setObjectName(QString::fromUtf8("enterInputDataLabel"));
        enterInputDataLabel->setMinimumSize(QSize(221, 16));
        enterInputDataLabel->setMaximumSize(QSize(16777215, 16));

        nameGridLayout->addWidget(enterInputDataLabel, 0, 2, 1, 1);

        chooseMethodLabel = new QLabel(chooseMethodWidget);
        chooseMethodLabel->setObjectName(QString::fromUtf8("chooseMethodLabel"));
        chooseMethodLabel->setMinimumSize(QSize(151, 16));
        chooseMethodLabel->setMaximumSize(QSize(16777215, 16));

        nameGridLayout->addWidget(chooseMethodLabel, 0, 0, 1, 1);

        horizontalSpacer_7 = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        nameGridLayout->addItem(horizontalSpacer_7, 0, 3, 1, 1);


        gridLayout->addLayout(nameGridLayout, 0, 0, 1, 1);

        deltaGridLayout = new QGridLayout();
        deltaGridLayout->setObjectName(QString::fromUtf8("deltaGridLayout"));
        deltaLabel = new QLabel(chooseMethodWidget);
        deltaLabel->setObjectName(QString::fromUtf8("deltaLabel"));
        deltaLabel->setMinimumSize(QSize(51, 31));
        deltaLabel->setMaximumSize(QSize(51, 16777215));
        deltaLabel->setStyleSheet(QString::fromUtf8("image: url(:/images/delta.png);"));

        deltaGridLayout->addWidget(deltaLabel, 0, 1, 1, 1);

        horizontalSpacer_8 = new QSpacerItem(307, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        deltaGridLayout->addItem(horizontalSpacer_8, 0, 0, 1, 1);

        horizontalSpacer_10 = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        deltaGridLayout->addItem(horizontalSpacer_10, 0, 3, 1, 1);

        deltaLineEdit = new QLineEdit(chooseMethodWidget);
        deltaLineEdit->setObjectName(QString::fromUtf8("deltaLineEdit"));
        deltaLineEdit->setMinimumSize(QSize(170, 31));
        deltaLineEdit->setMaximumSize(QSize(341, 16777215));

        deltaGridLayout->addWidget(deltaLineEdit, 0, 2, 1, 1);


        gridLayout->addLayout(deltaGridLayout, 2, 0, 1, 1);

        epsGridLayout = new QGridLayout();
        epsGridLayout->setObjectName(QString::fromUtf8("epsGridLayout"));
        epsLineEdit = new QLineEdit(chooseMethodWidget);
        epsLineEdit->setObjectName(QString::fromUtf8("epsLineEdit"));
        epsLineEdit->setMinimumSize(QSize(170, 31));
        epsLineEdit->setMaximumSize(QSize(341, 16777215));

        epsGridLayout->addWidget(epsLineEdit, 0, 3, 1, 1);

        comboBox = new QComboBox(chooseMethodWidget);
        comboBox->addItem(QString());
        comboBox->addItem(QString());
        comboBox->addItem(QString());
        comboBox->addItem(QString());
        comboBox->addItem(QString());
        comboBox->addItem(QString());
        comboBox->setObjectName(QString::fromUtf8("comboBox"));
        QSizePolicy sizePolicy1(QSizePolicy::Preferred, QSizePolicy::Fixed);
        sizePolicy1.setHorizontalStretch(0);
        sizePolicy1.setVerticalStretch(0);
        sizePolicy1.setHeightForWidth(comboBox->sizePolicy().hasHeightForWidth());
        comboBox->setSizePolicy(sizePolicy1);
        comboBox->setMinimumSize(QSize(281, 31));
        comboBox->setMaximumSize(QSize(281, 16777215));

        epsGridLayout->addWidget(comboBox, 0, 0, 1, 1);

        horizontalSpacer_6 = new QSpacerItem(20, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        epsGridLayout->addItem(horizontalSpacer_6, 0, 1, 1, 1);

        epsLabel = new QLabel(chooseMethodWidget);
        epsLabel->setObjectName(QString::fromUtf8("epsLabel"));
        epsLabel->setMinimumSize(QSize(51, 21));
        epsLabel->setMaximumSize(QSize(51, 16777215));
        epsLabel->setStyleSheet(QString::fromUtf8("image: url(:/images/eps.png);"));

        epsGridLayout->addWidget(epsLabel, 0, 2, 1, 1);

        horizontalSpacer_9 = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        epsGridLayout->addItem(horizontalSpacer_9, 0, 4, 1, 1);


        gridLayout->addLayout(epsGridLayout, 1, 0, 1, 1);


        gridLayout_12->addWidget(chooseMethodWidget, 1, 0, 1, 2);

        verticalSpacer = new QSpacerItem(20, 138, QSizePolicy::Minimum, QSizePolicy::Fixed);

        gridLayout_12->addItem(verticalSpacer, 2, 1, 1, 1);

        errorStackedWidget = new QStackedWidget(startWidget);
        errorStackedWidget->setObjectName(QString::fromUtf8("errorStackedWidget"));
        errorOk = new QWidget();
        errorOk->setObjectName(QString::fromUtf8("errorOk"));
        gridLayout_11 = new QGridLayout(errorOk);
        gridLayout_11->setObjectName(QString::fromUtf8("gridLayout_11"));
        errorOkHorizontalSpacer = new QSpacerItem(715, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        gridLayout_11->addItem(errorOkHorizontalSpacer, 0, 0, 1, 1);

        errorStackedWidget->addWidget(errorOk);
        errorFunc = new QWidget();
        errorFunc->setObjectName(QString::fromUtf8("errorFunc"));
        gridLayout_3 = new QGridLayout(errorFunc);
        gridLayout_3->setObjectName(QString::fromUtf8("gridLayout_3"));
        errorFuncInfo = new QLabel(errorFunc);
        errorFuncInfo->setObjectName(QString::fromUtf8("errorFuncInfo"));
        errorFuncInfo->setMinimumSize(QSize(191, 51));
        errorFuncInfo->setMaximumSize(QSize(191, 51));
        errorFuncInfo->setStyleSheet(QString::fromUtf8("image: url(:/images/funcError.png);"));

        gridLayout_3->addWidget(errorFuncInfo, 0, 0, 1, 1);

        errorFunchorizontalSpacer = new QSpacerItem(518, 28, QSizePolicy::Expanding, QSizePolicy::Minimum);

        gridLayout_3->addItem(errorFunchorizontalSpacer, 0, 1, 1, 1);

        errorStackedWidget->addWidget(errorFunc);
        methodError = new QWidget();
        methodError->setObjectName(QString::fromUtf8("methodError"));
        gridLayout_9 = new QGridLayout(methodError);
        gridLayout_9->setObjectName(QString::fromUtf8("gridLayout_9"));
        errorMethodInfo = new QLabel(methodError);
        errorMethodInfo->setObjectName(QString::fromUtf8("errorMethodInfo"));
        errorMethodInfo->setMinimumSize(QSize(191, 51));
        errorMethodInfo->setMaximumSize(QSize(191, 51));
        errorMethodInfo->setStyleSheet(QString::fromUtf8("image: url(:/images/methodError.png);"));

        gridLayout_9->addWidget(errorMethodInfo, 0, 0, 1, 1);

        errorMethodhorizontalSpacer = new QSpacerItem(353, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        gridLayout_9->addItem(errorMethodhorizontalSpacer, 0, 1, 1, 1);

        errorStackedWidget->addWidget(methodError);
        epsError = new QWidget();
        epsError->setObjectName(QString::fromUtf8("epsError"));
        gridLayout_7 = new QGridLayout(epsError);
        gridLayout_7->setObjectName(QString::fromUtf8("gridLayout_7"));
        errorEpsInfo = new QLabel(epsError);
        errorEpsInfo->setObjectName(QString::fromUtf8("errorEpsInfo"));
        errorEpsInfo->setMinimumSize(QSize(381, 41));
        errorEpsInfo->setMaximumSize(QSize(381, 41));
        errorEpsInfo->setStyleSheet(QString::fromUtf8("image: url(:/images/epsError.png);"));

        gridLayout_7->addWidget(errorEpsInfo, 0, 0, 1, 1);

        errorEpsHorizontalSpacer = new QSpacerItem(328, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        gridLayout_7->addItem(errorEpsHorizontalSpacer, 0, 1, 1, 1);

        errorStackedWidget->addWidget(epsError);
        deltaError = new QWidget();
        deltaError->setObjectName(QString::fromUtf8("deltaError"));
        gridLayout_10 = new QGridLayout(deltaError);
        gridLayout_10->setObjectName(QString::fromUtf8("gridLayout_10"));
        errorDeltaInfo = new QLabel(deltaError);
        errorDeltaInfo->setObjectName(QString::fromUtf8("errorDeltaInfo"));
        errorDeltaInfo->setMinimumSize(QSize(361, 61));
        errorDeltaInfo->setMaximumSize(QSize(361, 61));
        errorDeltaInfo->setStyleSheet(QString::fromUtf8("image: url(:/images/deltaError.png);"));

        gridLayout_10->addWidget(errorDeltaInfo, 0, 1, 1, 1);

        errorDeltaHorizontalSpacer = new QSpacerItem(710, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        gridLayout_10->addItem(errorDeltaHorizontalSpacer, 0, 2, 1, 1);

        errorStackedWidget->addWidget(deltaError);

        gridLayout_12->addWidget(errorStackedWidget, 3, 0, 1, 2);

        findMinPushButton = new QPushButton(startWidget);
        findMinPushButton->setObjectName(QString::fromUtf8("findMinPushButton"));
        findMinPushButton->setMinimumSize(QSize(151, 31));
        findMinPushButton->setMaximumSize(QSize(151, 31));
        findMinPushButton->setStyleSheet(QString::fromUtf8(FIND_MIN_PUSH_BUTTON_SET_STYLE_SHEET_RELEASED));

        gridLayout_12->addWidget(findMinPushButton, 4, 0, 1, 1);

        stackedWidget->addWidget(startWidget);
        mainWidget = new QWidget();
        mainWidget->setObjectName(QString::fromUtf8("mainWidget"));
        mainWidget->setMinimumSize(QSize(754, 0));
        gridLayout_6 = new QGridLayout(mainWidget);
        gridLayout_6->setObjectName(QString::fromUtf8("gridLayout_6"));
        gridLayout_4 = new QGridLayout();
        gridLayout_4->setObjectName(QString::fromUtf8("gridLayout_4"));
        backPushButton = new QPushButton(mainWidget);
        backPushButton->setObjectName(QString::fromUtf8("backPushButton"));
        sizePolicy.setHeightForWidth(backPushButton->sizePolicy().hasHeightForWidth());
        backPushButton->setSizePolicy(sizePolicy);
        backPushButton->setMinimumSize(QSize(0, 0));
        backPushButton->setMaximumSize(QSize(100, 29));
        backPushButton->setStyleSheet(QString::fromUtf8(BACK_PUSH_BUTTON_SET_STYLE_SHEET_RELEASED));

        gridLayout_4->addWidget(backPushButton, 0, 0, 1, 1);

        restartPushButton = new QPushButton(mainWidget);
        restartPushButton->setObjectName(QString::fromUtf8("restartPushButton"));
        sizePolicy.setHeightForWidth(restartPushButton->sizePolicy().hasHeightForWidth());
        restartPushButton->setSizePolicy(sizePolicy);
        restartPushButton->setMaximumSize(QSize(100, 29));
        restartPushButton->setStyleSheet(QString::fromUtf8(RESTART_PUSH_BUTTON_SET_STYLE_SHEET_RELEASED));

        gridLayout_4->addWidget(restartPushButton, 0, 1, 1, 1);

        horizontalSpacer_4 = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        gridLayout_4->addItem(horizontalSpacer_4, 0, 2, 1, 1);


        gridLayout_6->addLayout(gridLayout_4, 0, 0, 1, 1);

        plotWidget = new QCustomPlot(mainWidget);
        plotWidget->setObjectName(QString::fromUtf8("plotWidget"));
        QSizePolicy sizePolicy2(QSizePolicy::Expanding, QSizePolicy::Expanding);
        sizePolicy2.setHorizontalStretch(0);
        sizePolicy2.setVerticalStretch(5);
        sizePolicy2.setHeightForWidth(plotWidget->sizePolicy().hasHeightForWidth());
        plotWidget->setSizePolicy(sizePolicy2);
        plotWidget->setMinimumSize(QSize(736, 430));
        plotWidget->setStyleSheet(QString::fromUtf8("background-color: rgb(124, 222, 255);"));

        gridLayout_6->addWidget(plotWidget, 1, 0, 1, 1);

        infoOutputWidget = new QWidget(mainWidget);
        infoOutputWidget->setObjectName(QString::fromUtf8("infoOutputWidget"));
        infoOutputWidget->setMinimumSize(QSize(738, 101));
        gridLayout_5 = new QGridLayout(infoOutputWidget);
        gridLayout_5->setObjectName(QString::fromUtf8("gridLayout_5"));
        listWidget = new QListWidget(infoOutputWidget);
        listWidget->setObjectName(QString::fromUtf8("listWidget"));
        listWidget->setMinimumSize(QSize(720, 83));
        listWidget->setMaximumSize(QSize(16777215, 150));

        gridLayout_5->addWidget(listWidget, 0, 0, 1, 1);


        gridLayout_6->addWidget(infoOutputWidget, 2, 0, 1, 1);

        stackedWidget->addWidget(mainWidget);

        gridLayout_2->addWidget(stackedWidget, 0, 0, 1, 1);

        MainWindow->setCentralWidget(centralwidget);

        retranslateUi(MainWindow);

        stackedWidget->setCurrentIndex(0);
        errorStackedWidget->setCurrentIndex(0);


        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QCoreApplication::translate("MainWindow", "Прямые методы одномерной минимизации", nullptr));
        chooseFunctionLabel->setText(QCoreApplication::translate("MainWindow", "1.  Выберите функцию:", nullptr));
        chooseFunctionComboBox->setItemText(0, QCoreApplication::translate("MainWindow", "-", nullptr));
        chooseFunctionComboBox->setItemText(1, QCoreApplication::translate("MainWindow", "1", nullptr));
        chooseFunctionComboBox->setItemText(2, QCoreApplication::translate("MainWindow", "2", nullptr));
        chooseFunctionComboBox->setItemText(3, QCoreApplication::translate("MainWindow", "3", nullptr));
        enterInputDataLabel->setText(QCoreApplication::translate("MainWindow", "3.  Введите входные данные:", nullptr));
        chooseMethodLabel->setText(QCoreApplication::translate("MainWindow", "2.  Выберите метод:", nullptr));
        comboBox->setItemText(0, QCoreApplication::translate("MainWindow", "-", nullptr));
        comboBox->setItemText(1, QCoreApplication::translate("MainWindow", "Метод дихотомии", nullptr));
        comboBox->setItemText(2, QCoreApplication::translate("MainWindow", "Метод золотого сечения", nullptr));
        comboBox->setItemText(3, QCoreApplication::translate("MainWindow", "Метод Фибоначчи", nullptr));
        comboBox->setItemText(4, QCoreApplication::translate("MainWindow", "Метод парабол", nullptr));
        comboBox->setItemText(5, QCoreApplication::translate("MainWindow", "Комбинированный метод Брента", nullptr));

        enterInputDataLabel->setVisible(false);
        epsLabel->setVisible(false);
        epsLineEdit->setVisible(false);
        deltaLabel->setVisible(false);
        deltaLineEdit->setVisible(false);

        findMinPushButton->setText(QCoreApplication::translate("MainWindow", "Найти минимум", nullptr));
        backPushButton->setText(QCoreApplication::translate("MainWindow", "Назад", nullptr));
        restartPushButton->setText(QCoreApplication::translate("MainWindow", "Перезапуск", nullptr));
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
