#pragma once

#include <QMainWindow>
#include <QtMath>

#include <optional>
#include <utility>

struct SecondOrderCurve {
    SecondOrderCurve() = default;
    SecondOrderCurve(double a11, double a12, double a22, double a1, double a2, double a0);
    SecondOrderCurve(double a11, double a12, double a22, double a1, double a2, double a0, double x_min, double y_min);

    double evaluate(double x, double y);

    void getParametrized(double phi, double& x, double& y);

    std::optional<std::pair<double, double>> getMin() const;

    double getA11();
    void setA11(double a11);

    double getA22();
    void setA22(double a22);

    double getA12();
    void setA12(double a12);

    double getA1();
    void setA1(double a1);

    double getA2();
    void setA2(double a2);

    double getA0();
    void setA0(double a0);

private:
    bool isZero(double val);

private:
    constexpr static double EPS = 1e-14;

    double a11, a12, a22, a1, a2, a0;
    std::optional<std::pair<double, double>> min;
};
