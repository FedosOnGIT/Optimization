
QT       += core gui
greaterThan(QT_MAJOR_VERSION, 4): QT += widgets printsupport

greaterThan(QT_MAJOR_VERSION, 4): CONFIG += c++17
lessThan(QT_MAJOR_VERSION, 5): QMAKE_CXXFLAGS += -std=c++17

TARGET = gui
TEMPLATE = app

SOURCES += main.cpp\
           MainWindow.cpp \
           SecondOrderCurve.cpp \
           qcustomplot.cpp

HEADERS += \
           MainWindow.h \
           SecondOrderCurve.h \
           qcustomplot.h

FORMS    += \
    MainWindow.ui

RESOURCES += \
    resources.qrc

