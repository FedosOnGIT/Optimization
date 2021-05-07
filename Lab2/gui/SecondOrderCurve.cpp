#include "SecondOrderCurve.h"

SecondOrderCurve::SecondOrderCurve(double a11, double a12, double a22, double a1, double a2, double a0)
    : a11(a11), a12(a12 / 2), a22(a22), a1(a1), a2(a2), a0(a0), min(std::nullopt)
{}

SecondOrderCurve::SecondOrderCurve(double a11, double a12, double a22, double a1, double a2, double a0, double x_min, double y_min)
    : SecondOrderCurve(a11, a12, a22, a1, a2, a0)
{
    min = {x_min, y_min};
}

void SecondOrderCurve::getParametrized(double phi, double& x, double& y) {
    double tan = 0;
    if (a12 != 0) {
        double t = (a22 - a11) / (2 * a12);
        tan = t + qSqrt(t * t + 1);
    }

    double cos = qSqrt(1 / (1 + tan * tan));
    double sin = tan * cos;

    double cx2 = a11 * cos * cos + 2 * a12 * sin * cos + a22 * sin * sin;
    double cy2 = a11 * sin * sin - 2 * a12 * sin * cos + a22 * cos * cos;
    double cx = a1 * cos + a2 * sin;
    double cy = -a1 * sin + a2 * cos;
    double r = -a0;

    double dx = cx / (2 * cx2);
    r += dx * dx * cx2;
    double dy = cy / (2 * cy2);
    r += dy * dy * cy2;

    cx2 /= r;
    cy2 /= r;

    double tempX = qCos(phi) / qSqrt(cx2) - dx;
    double tempY = qSin(phi) / qSqrt(cy2) - dy;

    x = cos * tempX - sin * tempY;
    y = sin * tempX + cos * tempY;
}

std::optional<std::pair<double, double>> SecondOrderCurve::getMin() const {
    return min;
}

double SecondOrderCurve::getA11() {
    return a11;
}

void SecondOrderCurve::setA11(double a11) {
    this->a11 = a11;
}

double SecondOrderCurve::getA22() {
    return a22;
}

void SecondOrderCurve::setA22(double a22) {
    this->a22 = a22;
}

double SecondOrderCurve::getA12() {
    return a12;
}

void SecondOrderCurve::setA12(double a12) {
    this->a12 = a12;
}

double SecondOrderCurve::getA1() {
    return a1;
}

void SecondOrderCurve::setA1(double a1) {
    this->a1 = a1;
}

double SecondOrderCurve::getA2() {
    return a2;
}

void SecondOrderCurve::setA2(double a2) {
    this->a2 = a2;
}

double SecondOrderCurve::getA0() {
    return a0;
}

void SecondOrderCurve::setA0(double a0) {
    this->a0 = a0;
}

bool SecondOrderCurve::isZero(double val) {
    return (abs(val) <= EPS);
}

double SecondOrderCurve::evaluate(double x, double y) {
    return a11 * x * x + 2 * a12 * x * y + a22 * y * y + a1 * x + a2 * y + a0;
}
