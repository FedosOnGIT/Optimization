QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets printsupport

CONFIG += c++17

QMAKE_MACOSX_DEPLOYMENT_TARGET = 10.15

# You can make your code fail to compile if it uses deprecated APIs.
# In order to do so, uncomment the following line.
# DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

TARGET = gui
TEMPLATE = app


SOURCES += \
    gui/Defs.cpp \
    gui/MainWindow.cpp \
    gui/main.cpp \
    gui/qcustomplot.cpp \
    logger.cpp \

HEADERS += \
    gui/Defs.h \
    gui/MainWindow.h \
    gui/qcustomplot.h \
    MinMethod.h \
    DichotMethod.h \
    GoldenRatioMethod.h \
    FibonacciMethod.h \
    ParabolaMethod.h \
    BrentMethod.h \
    logger.h \

RESOURCES += \
    gui/resources.qrc
