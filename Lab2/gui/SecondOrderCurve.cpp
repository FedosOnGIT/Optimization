#include "SecondOrderCurve.h"

SecondOrderCurve::SecondOrderCurve(double a11, double a12, double a22, double a1, double a2, double a0)
    : a11(a11), a12(a12 / 2), a22(a22), a1(a1 / 2), a2(a2 / 2), a0(a0)
{}

std::optional<QTransform> SecondOrderCurve::getTransformToCanonicalForm() {
    double a11, a12, a22, a1, a2, a0;
    initSame(a11, a12, a22, a1, a2, a0);
    return getTransformToCanonicalForm(a11, a12, a22, a1, a2, a0);
}

std::optional<QRectF> SecondOrderCurve::getEllipseData() {
    double a11, a12, a22, a1, a2, a0;
    initSame(a11, a12, a22, a1, a2, a0);
    if (!getTransformToCanonicalForm(a11, a12, a22, a1, a2, a0).has_value()) {
        return std::nullopt;
    }
    if (is_zero(a0)) {
        return QRectF(QPointF(0, 0), QPointF(0, 0));
    }
    double x_mul = - a11 / a0;
    double y_mul = - a22 / a0;
    if (x_mul < 0 || y_mul < 0) {
        return std::nullopt;
    }
    double a = sqrt(1 / x_mul);
    double b = sqrt(1 / y_mul);
    return QRectF(QPointF(-a, b), QPointF(a, -b));
}

double SecondOrderCurve::get_a11() {
    return a11;
}

double SecondOrderCurve::set_a11(double a11) {
    this->a11 = a11;
}

double SecondOrderCurve::get_a22() {
    return a22;
}

double SecondOrderCurve::set_a22(double a22) {
    this->a22 = a22;
}

double SecondOrderCurve::get_a12() {
    return a12;
}

double SecondOrderCurve::set_a12(double a12) {
    this->a12 = a12;
}

double SecondOrderCurve::get_a1() {
    return a1;
}

double SecondOrderCurve::set_a1(double a1) {
    this->a1 = a1;
}

double SecondOrderCurve::get_a2() {
    return a2;
}

double SecondOrderCurve::set_a2(double a2) {
    this->a2 = a2;
}

double SecondOrderCurve::get_a0() {
    return a0;
}

double SecondOrderCurve::set_a0(double a0) {
    this->a0 = a0;
}

std::optional<QTransform> SecondOrderCurve::getTransformToCanonicalForm(double& a11, double& a12, double& a22, double& a1, double& a2, double& a0) {
    double m11, m21, m12, m22, dx = 0, dy = 0;
    if (!is_zero(a12)) {
        rotateCoordinates(m11, m21, m12, m22, a11, a12, a22, a1, a2);
    } else {
        m11 = 1;
        m12 = 0;
        m21 = 0;
        m22 = 1;
    }
    QTransform transform = QTransform(m11, m21, m12, m22, 0, 0);
    if (!is_zero(a11) && !is_zero(a22)) {
        dx = -a1 / a11;
        a0 -= (a1 * a1) / a11;
        dy = -a2 / a22;
        a0 -= (a2 * a2) / a22;
        QPointF center_ = QPointF(dx, dy);
        QPointF center = transform.map(center_);
        return QTransform(m11, m12, m21, m22, center.rx(), center.ry());
    }
    return std::nullopt;
}

void SecondOrderCurve::rotateCoordinates(double& m11, double& m21, double& m12, double& m22,
                                         double& a11, double& a12, double& a22, double& a1, double& a2) {
    double tgx = (((a22 - a11) / a12) + sqrt(((a11 - a22) * (a11 - a22)) / (a12 * a12) + 4)) / 2;
    double cosx = sqrt(1 / (1 + tgx * tgx));
    double sinx = tgx * cosx;
    double sinx2 = sinx * sinx;
    double cosx2 = cosx * cosx;
    double sin2x = 2 * sinx * cosx;

    double a11n = a11 * cosx2 + a12 * sin2x + a22 * sinx2;
    double a22n = a11 * sinx2 - a12 * sin2x + a22 * cosx2;
    double a1n = a1 * cosx + a2 * sinx;
    double a2n = a2 * cosx - a1 * sinx;

    m11 = cosx;
    m21 = sinx;
    m12 = -sinx;
    m22 = cosx;

    a11 = a11n;
    a12 = 0;
    a22 = a22n;
    a1 = a1n;
    a2 = a2n;
}

void SecondOrderCurve::initSame(double& a11, double& a12, double& a22, double& a1, double& a2, double& a0) {
    a11 = this->a11;
    a12 = this->a12;
    a22 = this->a22;
    a1 = this->a1;
    a2 = this->a2;
    a0 = this->a0;
}

bool SecondOrderCurve::is_zero(double val) {
    return (abs(val) <= EPS);
}

double SecondOrderCurve::evaluate(double x, double y) {
    return a11 * x * x + 2 * a12 * x * y + a22 * y * y + 2 * a1 * x + 2 * a2 * y + a0;
}
