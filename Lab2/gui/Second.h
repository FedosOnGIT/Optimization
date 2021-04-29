#pragma once

#include <QTransform>
#include <cmath>
#include <utility>
#include <memory>
#include <optional>

// 2*x^2 + 2*y^2 =

// Performs quadratic form : a11*x^2 + a12*x*y + a22*y^2 + a1*x + a2*y + a0
struct QuadraticForm {
    QuadraticForm(double a11, double a12, double a22, double a1, double a2, double a0);

    enum FigureType {
        ELLIPSE,
        HYPERBOLE,
        INTERSECTING_LINES,
        POINT,
        PARALLEL_LINES,
        EMPTY_SET
    };

    std::optional<QTransform> getTransform();
    std::optional<QRectF> getEllipseData();

    double evaluate(double x, double y);

    void dummy() {

    }

private:
    void rotateCoordinates(double& m11, double& m21, double& m12, double& m22,
                           double& a11, double& a22, double& a1, double& a2);

    bool is_zero(double val);

private:
    double a11, a12, a22, a1, a2, a0;

    std::unique_ptr<QuadraticForm> ptrf;
    std::unique_ptr<QTransform> ptr_transfrom;

    static constexpr double EPS = 1e-14;
};
