#pragma once

#include <QMainWindow>
#include <QtMath>

struct SecondOrderCurve {
    SecondOrderCurve() = default;
    SecondOrderCurve(double a11, double a12, double a22, double a1, double a2, double a0);

    double evaluate(double x, double y);
    void getParametrized(double phi, double& x, double& y);

    double get_a11();
    double set_a11(double a11);

    double get_a22();
    double set_a22(double a22);

    double get_a12();
    double set_a12(double a12);

    double get_a1();
    double set_a1(double a1);

    double get_a2();

    double set_a2(double a2);

    double get_a0();
    double set_a0(double a0);

private:
    bool is_zero(double val);

private:
    constexpr static double EPS = 1e-14;

    double a11, a12, a22, a1, a2, a0;
};
