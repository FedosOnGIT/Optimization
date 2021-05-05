#
#  QCustomPlot widgetPlot Examples
#

QT       += core gui
greaterThan(QT_MAJOR_VERSION, 4): QT += widgets printsupport

greaterThan(QT_MAJOR_VERSION, 4): CONFIG += c++17
lessThan(QT_MAJOR_VERSION, 5): QMAKE_CXXFLAGS += -std=c++17

TARGET = gui
TEMPLATE = app

SOURCES += main.cpp\
           SecondOrderCurve.cpp \
           mainwindow.cpp \
           qcustomplot.cpp

HEADERS += mainwindow.h \
           SecondOrderCurve.h \
           qcustomplot.h

FORMS    += mainwindow.ui

